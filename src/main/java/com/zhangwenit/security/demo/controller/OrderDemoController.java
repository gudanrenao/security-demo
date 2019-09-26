package com.zhangwenit.security.demo.controller;

import com.alibaba.fastjson.JSON;
import com.zhangwenit.security.demo.utils.SecurityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author ZWen
 * @Date 2019/9/22 3:22 PM
 * @Version 1.0
 **/
@RestController
@RequestMapping("/order")
public class OrderDemoController {

    @GetMapping("/list")
    public String list() {
        System.out.println(JSON.toJSONString(SecurityUtils.getUserDetails()));
        return "order list";
    }

    @PostMapping("/list")
    public String list2() {
        return "order list post";
    }

    @GetMapping("/del")
    public String del() {
        return "order del";
    }
}