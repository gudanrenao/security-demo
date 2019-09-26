package com.zhangwenit.security.demo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangwenit.security.demo.dto.Menu;
import com.zhangwenit.security.demo.dto.Permission;
import com.zhangwenit.security.demo.dto.SysUser;
import com.zhangwenit.security.demo.entity.SysPermissionEntity;
import com.zhangwenit.security.demo.mapper.SysPermissionMapper;
import com.zhangwenit.security.demo.mapper.SysUserMapper;
import com.zhangwenit.security.demo.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author ZWen
 * @Date 2019/9/25 6:45 PM
 * @Version 1.0
 **/
@Service
@Slf4j
public class SysPermissionService extends ServiceImpl<SysPermissionMapper, SysPermissionEntity> {

    private final SysPermissionMapper sysPermissionMapper;
    private final SysUserMapper sysUserMapper;
    private static List<Permission> permissionList;

    public SysPermissionService(SysPermissionMapper sysPermissionMapper, SysUserMapper sysUserMapper) {
        this.sysPermissionMapper = sysPermissionMapper;
        this.sysUserMapper = sysUserMapper;
        loadAllMenuPermission();
    }

    private void loadAllMenuPermission() {
        permissionList = sysPermissionMapper.findAll();
    }

    /**
     * 获取所有的菜单列表
     * <p>
     * 另一种sql方案：
     * <resultMap id="BaseResultMap" type="org.sang.bean.Department">
     * <id property="id" column="id"/>
     * <result column="name" property="name"/>
     * <result column="parentId" property="parentId"/>
     * <result column="isParent" property="isParent"/>
     * <collection property="children" ofType="org.sang.bean.Department" select="org.sang.mapper.DepartmentMapper.getDepByPid" column="id">
     * </collection>
     * </resultMap>
     * <select id="getDepByPid" resultMap="BaseResultMap">
     * select d1.*from department d1 where d1.`parentId`=#{pid} AND d1.enabled=true;
     * </select>
     *
     * @return
     */
    public List<Menu> menuTree() {
        //去除非菜单权限&排序
        List<Permission> resultList = permissionList.stream().filter(e -> e.getIsMenu() == 1).sorted(Comparator.comparingInt(Permission::getSid)).collect(Collectors.toList());
        return tree(resultList);
    }

    /**
     * 获取用户的所有的菜单列表
     * todo:缓存
     *
     * @return
     */
    public List<Menu> userMenuTree() {
        List<Permission> permissions = baseMapper.findAllPermissionByUserId(SecurityUtils.getUserId());
        Map<String, Permission> beforeMap = toMap(permissions);
        Map<String, Permission> permissionMap = toMap(permissionList);
        final String rootId = "0";
        Map<String, Permission> needAddMap = new HashMap<>(4);
        beforeMap.forEach((k, v) -> {
            Permission permission = v;
            //需要添加所拥有菜单的所有的父菜单-递归
            while ((!rootId.equals(permission.getPid())) && (!needAddMap.containsKey(permission.getPid()))) {
                permission = permissionMap.get(permission.getPid());
                needAddMap.put(permission.getId(), permission);
            }
        });
        //合并
        beforeMap.putAll(needAddMap);
        //去除非菜单权限&排序
        List<Permission> resultList = new ArrayList<>(beforeMap.values()).stream().filter(e -> e.getIsMenu() == 1).sorted(Comparator.comparingInt(Permission::getSid)).collect(Collectors.toList());
        return tree(resultList);
    }

    private Map<String, Permission> toMap(List<Permission> permissionList) {
        return permissionList.stream().collect(Collectors.toMap(Permission::getId, e -> e));
    }

    private List<Menu> tree(List<Permission> list) {
        List<Menu> resultList = new ArrayList<>();
        //构建菜单属性结构
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, Menu> parentMap = new HashMap<>(16);
            //先添加一级目录
            for (Permission p : list) {
                if (1 == p.getCurrLevel()) {
                    Menu dto = new Menu(p);
                    parentMap.put(p.getId(), dto);
                    resultList.add(dto);
                }
            }
            //再添加子类
            int index = 2;
            while (true) {
                //如果某个层级个数为0，那么循环结束
                int count = 0;
                for (Permission p : list) {
                    if (index == p.getCurrLevel()) {
                        count++;
                        Menu listDto = new Menu(p);
                        parentMap.put(p.getId(), listDto);
                        if (StringUtils.isNotEmpty(p.getPid())) {
                            Menu dto = parentMap.get(p.getPid());
                            if (dto != null) {
                                dto.getChildren().add(listDto);
                            }
                        } else {
                            throw new RuntimeException("数据异常");
                        }
                    }
                }
                if (count == 0) {
                    break;
                }
                index++;
            }
        }
        return resultList;
    }

    /**
     * 根据名称匹配搜索用户列表
     *
     * @param keywords
     * @return
     */
    public List<SysUser> userSearchByKeywords(String keywords) {
        return sysUserMapper.userSearchByKeywords(keywords, SecurityUtils.getMerchantId());
    }

    /**
     * 更新用户信息
     *
     * @param sysUser
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public int updateUser(SysUser sysUser) {
        return sysUserMapper.updateUser(sysUser);
    }

    /**
     * 根据Id查询账号信息
     *
     * @param userId
     * @return
     */
    public SysUser getUserById(String userId) {
        return sysUserMapper.getUserById(userId);
    }
}