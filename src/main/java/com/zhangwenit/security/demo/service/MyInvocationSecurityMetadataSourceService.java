package com.zhangwenit.security.demo.service;

import com.zhangwenit.security.demo.dto.Permission;
import com.zhangwenit.security.demo.dto.RolePermission;
import com.zhangwenit.security.demo.mapper.SysPermissionMapper;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author zw
 */
public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {

    private final SysPermissionMapper sysPermissionMapper;

    /**
     * key为权限表中url.value=拥有该权限的角色列表
     */
    private static ConcurrentHashMap<String, Collection<ConfigAttribute>> map = null;
    private static Set<String> withoutRoleUriSet = new HashSet<>(4);

    public MyInvocationSecurityMetadataSourceService(SysPermissionMapper sysPermissionMapper) {
        this.sysPermissionMapper = sysPermissionMapper;
        loadResourceDefine();
        loadWithoutRoleUriSet();
    }

    /**
     * 加载权限表中所有权限
     */
    private void loadResourceDefine() {
        map = new ConcurrentHashMap<>(32);
        Collection<ConfigAttribute> array;
        ConfigAttribute cfg;
        //权限需要从子权限到父权限排序，为了处理多个权限可以匹配同一个url的情况
        List<Permission> permissions = sysPermissionMapper.findAll();
        //获取所有的权限角色对应关系
        List<RolePermission> rolePermissionList = sysPermissionMapper.findAllRolePermission();
        Map<String, List<RolePermission>> roleMap = rolePermissionList.stream().collect(Collectors.groupingBy(RolePermission::getPermissionId));
        for (Permission permission : permissions) {
            array = new ArrayList<>();
            List<RolePermission> roleList = roleMap.get(permission.getId());
            if (!CollectionUtils.isEmpty(roleList)) {
                for (RolePermission rolePermission : roleList) {
                    //此处只添加了用户的名字，其实还可以添加更多权限的信息，例如请求方法到ConfigAttribute的集合中去。
                    // 此处添加的信息将会作为MyAccessDecisionManager类的decide的第三个参数。
                    cfg = new SecurityConfig(rolePermission.getRoleName());
                    array.add(cfg);
                }
                //用权限的getUrl() 作为map的key，用ConfigAttribute的集合作为value
                map.put(permission.getUrl(), array);
            }
        }
    }

    /**
     * 加载无需权鉴的uri列表
     */
    private void loadWithoutRoleUriSet() {
        withoutRoleUriSet.add("/permission/userMenuTree");
    }

    /**
     * 此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。如果不在权限表中则放行。
     *
     * @param object
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        //object 中包含用户请求的request 信息
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        //部分请求所有用户都可以访问
        String uri = request.getRequestURI();
        if (withoutRoleUriSet.contains(uri)) {
            return null;
        }
        AntPathRequestMatcher matcher;
        String resUrl;
        for (String s : map.keySet()) {
            resUrl = s;
            //todo:循环创建，是不是可以优化
            matcher = new AntPathRequestMatcher(resUrl);
            if (matcher.matches(request)) {
                return map.get(resUrl);
            }
        }
        //不需要任何权限可访问
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
