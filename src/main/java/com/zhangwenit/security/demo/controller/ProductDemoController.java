package com.zhangwenit.security.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description
 * @Author ZWen
 * @Date 2019/9/22 3:22 PM
 * @Version 1.0
 **/
@RestController
@RequestMapping("/product")
public class ProductDemoController {

    @GetMapping("/list")
    public String list() {
        return "product list";
    }

    @GetMapping("/del")
    public String del(HttpServletRequest request) {
        request.isUserInRole("aa");
        return "product del";
    }
}