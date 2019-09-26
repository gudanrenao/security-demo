package com.zhangwenit.security.demo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author by zw
 */
@Data
public class SysRole implements Serializable {

    private static final long serialVersionUID = 42L;

    /**
     * 角色Id
     */
    private Integer id;
    /**
     * 角色名;ROLE_开头的英文字符
     */
    private String name;
    /**
     * 角色描述
     */
    private String description;
}
