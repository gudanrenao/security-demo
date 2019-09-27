package com.zhangwenit.security.demo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description
 * @Author ZWen
 * @Date 2019/9/27 1:18 PM
 * @Version 1.0
 **/
@Data
@Accessors(chain = true)
@ApiModel("数据资源")
public class Resource implements Serializable {
    private static final long serialVersionUID = 42L;

    @ApiModelProperty("可管理的资源Id")
    private String resourceId;

    public Resource() {
    }

    public Resource(String resourceId) {
        this.resourceId = resourceId;
    }
}