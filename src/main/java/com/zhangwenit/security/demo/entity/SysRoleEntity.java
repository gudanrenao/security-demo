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
 * 角色表
 * </p>
 *
 * @author zw
 * @since 2019-09-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_role")
@ApiModel(value = "SysRoleEntity对象", description = "角色表")
public class SysRoleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Id")
    @TableId(value = "id")
    private String id;

    @ApiModelProperty(value = "角色名;ROLE_开头的英文字符")
    private String name;

    @ApiModelProperty(value = "角色描述")
    private String description;

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
