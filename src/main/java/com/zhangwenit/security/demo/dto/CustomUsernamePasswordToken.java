//package com.zhangwenit.security.demo.dto;
//
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//
//import java.util.Collection;
//
///**
// * @Description
// * @Author ZWen
// * @Date 2019/9/25 10:46 AM
// * @Version 1.0
// **/
//public class CustomUsernamePasswordToken extends UsernamePasswordAuthenticationToken {
//
//    private final AuthenticationDetail authenticationDetail;
//
//    /**
//     * This constructor can be safely used by any code that wishes to create a
//     * <code>UsernamePasswordAuthenticationToken</code>, as the {@link #isAuthenticated()}
//     * will return <code>false</code>.
//     *
//     * @param principal
//     * @param credentials
//     */
//    public CustomUsernamePasswordToken(Object principal, Object credentials) {
//        super(principal, credentials);
//        this.authenticationDetail = null;
//    }
//
//    /**
//     * This constructor should only be used by <code>AuthenticationManager</code> or
//     * <code>AuthenticationProvider</code> implementations that are satisfied with
//     * producing a trusted (i.e. {@link #isAuthenticated()} = <code>true</code>)
//     * authentication token.
//     *
//     * @param principal
//     * @param credentials
//     * @param authorities
//     * @param authenticationDetail
//     */
//    public CustomUsernamePasswordToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, AuthenticationDetail authenticationDetail) {
//        super(principal, credentials, authorities);
//        this.authenticationDetail = authenticationDetail;
//    }
//}