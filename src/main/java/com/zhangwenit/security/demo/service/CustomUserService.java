package com.zhangwenit.security.demo.service;

import com.zhangwenit.security.demo.dto.CustomUserDetails;
import com.zhangwenit.security.demo.dto.Resource;
import com.zhangwenit.security.demo.dto.SysRole;
import com.zhangwenit.security.demo.dto.SysUser;
import com.zhangwenit.security.demo.mapper.SysPermissionMapper;
import com.zhangwenit.security.demo.mapper.SysUserMapper;
import com.zhangwenit.security.demo.mapper.SysUserResourceMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * /自定义UserDetailsService 接口
 *
 * @author zw
 */
@Service
public class CustomUserService implements UserDetailsService {

    private final SysUserMapper sysUserMapper;
    private final SysPermissionMapper sysPermissionMapper;
    private final SysUserResourceMapper sysUserResourceMapper;

    public CustomUserService(SysUserMapper sysUserMapper, SysPermissionMapper sysPermissionMapper, SysUserResourceMapper sysUserResourceMapper) {
        this.sysUserMapper = sysUserMapper;
        this.sysPermissionMapper = sysPermissionMapper;
        this.sysUserResourceMapper = sysUserResourceMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        SysUser user = sysUserMapper.findByUserName(username);
        if (user != null) {
            List<SysRole> roleList = sysPermissionMapper.findAllRoleByUserId(user.getId());
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            for (SysRole role : roleList) {
                if (role != null) {
                    //每个grantedAuthority表示一个角色
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getName());
                    //1：此处将权限信息添加到 GrantedAuthority对象中，在后面进行全权限验证时会使用GrantedAuthority对象。
                    grantedAuthorities.add(grantedAuthority);
                }
            }
            CustomUserDetails userDetails = new CustomUserDetails(user.getUsername(), user.getPassword(), user.getEnabled() == 1, grantedAuthorities);
            //查询出资源信息,后面请求认证后以便过滤数据
            List<String> resourceList = sysUserResourceMapper.selectResourceByUserId(user.getId());
            //将账号基本信息和资源信息放入UserDetails
            userDetails.fillUserInfo(user,resourceList.stream().map(Resource::new).collect(Collectors.toList()));
            return userDetails;
        } else {
            throw new UsernameNotFoundException("admin: " + username + " do not exist!");
        }
    }

}
