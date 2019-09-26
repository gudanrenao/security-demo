package com.zhangwenit.security.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author ZWen
 * @Date 2019/9/22 3:33 PM
 * @Version 1.0
 **/
@RestController
@RequestMapping("/permission")
public class PermissionDemoController {

    @GetMapping("/list")
    public String list() {
        return "permission list";
    }

    @GetMapping("/del")
    public String del() {
        return "permission del";
    }
}