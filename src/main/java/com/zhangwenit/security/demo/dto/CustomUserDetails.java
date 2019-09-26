package com.zhangwenit.security.demo.dto;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

/**
 * @Description
 * @Author ZWen
 * @Date 2019/9/25 10:27 AM
 * @Version 1.0
 **/
public class CustomUserDetails extends User {

    private final AuthenticationDetail authenticationDetail;

    private final List<SysRole> roles;

    /**
     * 验证前构建方法
     * Calls the more complex constructor with all boolean arguments set to {@code true}.
     *
     * @param username
     * @param password
     * @param authorities
     */
    public CustomUserDetails(String username, String password, boolean enabled, Collection<? extends GrantedAuthority> authorities, AuthenticationDetail authenticationDetail, List<SysRole> roles) {
        super(username, password, enabled, true, true, true, authorities);
        this.authenticationDetail = authenticationDetail;
        this.roles = roles;
    }

    /**
     * 验证后构建方法
     * Construct the <code>User</code> with the details required by
     * {@link DaoAuthenticationProvider}.
     *
     * @param username              the username presented to the
     *                              <code>DaoAuthenticationProvider</code>
     * @param password              the password that should be presented to the
     *                              <code>DaoAuthenticationProvider</code>
     * @param enabled               set to <code>true</code> if the user is enabled
     * @param accountNonExpired     set to <code>true</code> if the account has not expired
     * @param credentialsNonExpired set to <code>true</code> if the credentials have not
     *                              expired
     * @param accountNonLocked      set to <code>true</code> if the account is not locked
     * @param authorities           the authorities that should be granted to the caller if they
     *                              presented the correct username and password and the user is enabled. Not null.
     * @param authenticationDetail
     * @throws IllegalArgumentException if a <code>null</code> value was passed either as
     *                                  a parameter or as an element in the <code>GrantedAuthority</code> collection
     */
    public CustomUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, AuthenticationDetail authenticationDetail, List<SysRole> roles) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.authenticationDetail = authenticationDetail;
        this.roles = roles;
    }

    public AuthenticationDetail getAuthenticationDetail() {
        return authenticationDetail;
    }

    public List<SysRole> getRoles() {
        return roles;
    }
}