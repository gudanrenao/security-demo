package com.zhangwenit.security.demo.dto.req;

import com.titan.common.util.FieldChecker;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Description
 * @Author ZWen
 * @Date 2019/9/27 10:23 AM
 * @Version 1.0
 **/
@Data
@Accessors(chain = true)
@ApiModel("更新账号角色入参")
public class UpdateUserRole {

    @ApiModelProperty(value = "账号Id")
    private String userId;

    @ApiModelProperty("角色Id列表")
    private List<String> rids;

    public void checkParams() {
        FieldChecker.assertNotNull(this.userId, "账号Id不能为空");
    }
}