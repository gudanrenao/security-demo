package com.zhangwenit.security.demo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 自定义 Authentication details:用于数据权限
 * @Author ZWen
 * @Date 2019/9/25 10:20 AM
 * @Version 1.0
 **/
@Data
@Accessors(chain = true)
public class AuthenticationDetail implements Serializable {
    private static final long serialVersionUID = 42L;

    private List<String> resourceList;

    @ApiModelProperty(value = "账号类型 1=超级管理员 2=普通账号")
    private Integer type;

    private String userId;

    @ApiModelProperty(value = "商户Id")
    private String merchantId;
}