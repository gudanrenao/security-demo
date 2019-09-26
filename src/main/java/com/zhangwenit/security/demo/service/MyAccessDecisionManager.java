package com.zhangwenit.security.demo.service;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author zw
 */
public class MyAccessDecisionManager implements AccessDecisionManager {

    /**
     * decide 方法是判定是否拥有权限的决策方法
     *
     * @param authentication   CustomUserService中循环添加到 GrantedAuthority 对象中的权限信息集合
     * @param object           客户端发起的请求的request信息，可转换为 HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
     * @param configAttributes MyInvocationSecurityMetadataSource的getAttributes(Object object)这个方法返回的结果，此方法是为了判定用户请求的url
     *                         是否在权限表中，如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。如果不在权限表中则放行
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {

        if (null == configAttributes || configAttributes.size() <= 0) {
            return;
        }
        ConfigAttribute c;
        String needRole;
        for (ConfigAttribute configAttribute : configAttributes) {
            c = configAttribute;
            needRole = c.getAttribute();
            for (GrantedAuthority ga : authentication.getAuthorities()) {
                //authentication 为在CustomUserService循环添加到 GrantedAuthority 对象中的权限信息集合
                if (needRole.trim().equals(ga.getAuthority())) {
                    System.out.println("needRole:" + needRole + "---currRole:" + ga.getAuthority());
                    return;
                }
            }
        }
        throw new AccessDeniedException("no right");
    }


    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
