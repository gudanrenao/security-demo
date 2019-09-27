package com.zhangwenit.security.demo.dto.req;

import com.zhangwenit.security.demo.dto.db.BaseCriteria;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description
 * @Author ZWen
 * @Date 2019/9/27 4:54 PM
 * @Version 1.0
 **/
@Data
@Accessors(chain = true)
@ApiModel("条件查询账号列表入参")
public class SysUserSearch extends BaseCriteria {

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "用户名")
    private String name;

    @ApiModelProperty(value = "账号是否启用 1=启用 0=禁用")
    private Integer enabled;
}