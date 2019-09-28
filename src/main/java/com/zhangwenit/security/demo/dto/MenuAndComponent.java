package com.zhangwenit.security.demo.dto;

import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author ZWen
 * @Date 2019/9/28 4:00 PM
 * @Version 1.0
 **/
@Data
public class MenuAndComponent {

    private List<Menu> menus;
    private List<Menu> components;
}