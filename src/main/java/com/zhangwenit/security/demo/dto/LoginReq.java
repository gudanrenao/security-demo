package com.zhangwenit.security.demo.dto;

import lombok.Data;

/**
 * @Description
 * @Author ZWen
 * @Date 2019/9/27 10:03 AM
 * @Version 1.0
 **/
@Data
public class LoginReq {

    private String username;

    private String password;
}