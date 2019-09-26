package com.zhangwenit.security.demo.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description 角色权限关系
 * @Author ZWen
 * @Date 2019/9/24 10:24 PM
 * @Version 1.0
 **/
@Data
@Accessors(chain = true)
public class RolePermission {

    private String roleId;
    private String permissionId;
    private String roleName;
}