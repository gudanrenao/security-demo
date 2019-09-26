package com.zhangwenit.security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Description
 * @Author ZWen
 * @Date 2019/9/22 7:47 PM
 * @Version 1.0
 **/
@RestController
public class CookieController {

    @Autowired
    FindByIndexNameSessionRepository<? extends Session> sessionRepository;

    @GetMapping("/cookie/findByUsername")
    public Map findByUsername(@RequestParam String username) {
        Map<String, ? extends Session> usersSessions = sessionRepository.findByIndexNameAndIndexValue(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, username);
        return usersSessions;
    }
}