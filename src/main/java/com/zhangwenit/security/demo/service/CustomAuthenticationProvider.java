//package com.zhangwenit.security.demo.service;
//
//import com.zhangwenit.security.demo.dto.CustomUserDetails;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//
///**
// * @Description
// * @Author ZWen
// * @Date 2019/9/25 10:45 AM
// * @Version 1.0
// **/
//public class CustomAuthenticationProvider extends DaoAuthenticationProvider {
//
//    @Override
//    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
//        Authentication successAuthentication = super.createSuccessAuthentication(principal, authentication, user);
//        if (successAuthentication instanceof UsernamePasswordAuthenticationToken && user instanceof CustomUserDetails) {
//            UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) successAuthentication;
//            token.setDetails(((CustomUserDetails) user).getAuthenticationDetail());
//            return token;
//        }
//        return successAuthentication;
//    }
//}