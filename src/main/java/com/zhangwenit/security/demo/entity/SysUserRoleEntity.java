package com.zhangwenit.security.demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 账号角色关联表
 * </p>
 *
 * @author zw
 * @since 2019-09-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user_role")
@ApiModel(value="SysUserRoleEntity对象", description="账号角色关联表")
public class SysUserRoleEntity implements Serializable {

private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "Id")
    @TableId(value = "id")
    private String id;

    @ApiModelProperty(value = "账号Id")
    private String sysUserId;

    @ApiModelProperty(value = "角色Id")
    private String sysRoleId;

    @ApiModelProperty(value = "是否删除 0否1是")
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer isDelete;

    @ApiModelProperty(value = "创建人ID;")
    @TableField(fill = FieldFill.INSERT)
    private String creatorId;

    @ApiModelProperty(value = "创建时间;")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "修改人ID;")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String modifyUserId;

    @ApiModelProperty(value = "更新时间;")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;


}
