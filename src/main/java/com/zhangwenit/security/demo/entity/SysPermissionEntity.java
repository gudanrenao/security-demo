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
 * 权限表
 * </p>
 *
 * @author zw
 * @since 2019-09-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_permission")
@ApiModel(value = "SysPermissionEntity对象", description = "权限表")
public class SysPermissionEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Id")
    @TableId(value = "id")
    private String id;

    @ApiModelProperty(value = "权限名")
    private String name;

    @ApiModelProperty(value = "权限描述")
    private String description;

    @ApiModelProperty(value = "接口url")
    private String url;

    @ApiModelProperty(value = "前端路由地址")
    private String path;

    @ApiModelProperty(value = "前端模块")
    private String component;

    @ApiModelProperty(value = "前端:父节点Id")
    private String pid;

    @ApiModelProperty(value = "前端图标iconCls")
    private String iconCls;

    @ApiModelProperty(value = "前端keep_alive属性")
    private Integer keepAlive;

    @ApiModelProperty(value = "是否需要权限 1=需要 2=不需要")
    private Integer requireAuth;

    @ApiModelProperty(value = "同级排序，最小为1，越小越靠前")
    private Integer sid;

    @ApiModelProperty(value = "是否是菜单项 1=是 0=不是")
    private Integer isMenu;

    @ApiModelProperty(value = "是否跳转页面 1=是 0=否")
    private Integer isPage;

    @ApiModelProperty(value = "当前层级 最小为1")
    private Integer currLevel;

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
