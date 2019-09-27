package com.zhangwenit.security.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author zw
 * @date 2019-09-25
 */
@Data
public class SysUser {

    private String id;

    @ApiModelProperty(value = "商户Id")
    private String merchantId;

    @ApiModelProperty(value = "账号是否启用 1=启用 0=禁用")
    private Integer enabled;

    @ApiModelProperty(value = "账号类型 1=超级管理员 2=普通账号")
    private Integer type;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "用户真实姓名")
    private String name;

    @ApiModelProperty(value = "头像地址")
    private String headUrl;

    @ApiModelProperty(value = "备注")
    private String remark;

    private String username;

    @JsonIgnore
    private String password;

    private List<SysRole> roles;

}
