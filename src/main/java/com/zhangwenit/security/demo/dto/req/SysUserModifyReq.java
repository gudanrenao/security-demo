package com.zhangwenit.security.demo.dto.req;

import com.titan.common.util.FieldChecker;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description
 * @Author ZWen
 * @Date 2019/9/27 6:46 PM
 * @Version 1.0
 **/
@Data
@Accessors(chain = true)
@ApiModel("新建或修改账号入参")
public class SysUserModifyReq {

    @ApiModelProperty(value = "Id")
    private String id;

    @ApiModelProperty(value = "用户真实姓名")
    private String name;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "明文密码")
    private String password;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "头像地址")
    private String headUrl;

    @ApiModelProperty(value = "备注")
    private String remark;

    public void checkParams() {
        FieldChecker.assertNotEmpty(this.username, "账号名不能为空");
        FieldChecker.assertNotEmpty(this.password, "密码不能为空");
    }
}