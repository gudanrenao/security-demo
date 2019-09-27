package com.zhangwenit.security.demo.utils;

import com.zhangwenit.security.demo.dto.CustomUserDetails;
import com.zhangwenit.security.demo.dto.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.List;

/**
 * @Description
 * @Author ZWen
 * @Date 2019/9/25 11:35 AM
 * @Version 1.0
 **/
public class SecurityUtils {

    public static List<Resource> getResourceList() {
        return getUserDetails().getResourceList();
    }

    public static String getUsername() {
        return getUserDetails().getUsername();
    }

    public static String getUserId() {
        return getUserDetails().getUserId();
    }

    public static String getMerchantId() {
        return getUserDetails().getMerchantId();
    }

    public static Collection<GrantedAuthority> getAuthorities() {
        return getUserDetails().getAuthorities();
    }

    public static CustomUserDetails getUserDetails() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}