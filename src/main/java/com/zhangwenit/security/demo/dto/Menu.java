package com.zhangwenit.security.demo.dto;

import com.zhangwenit.security.demo.entity.SysPermissionEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单树列表
 *
 * @author zw
 */
@Data
public class Menu implements Serializable {
    private static final long serialVersionUID = 42L;

    @ApiModelProperty(value = "Id")
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

    @ApiModelProperty(value = "是否是菜单项 1=是 0=不是")
    private Integer isMenu;

    @ApiModelProperty(value = "当前层级 最小为1")
    private Integer currLevel;

    @ApiModelProperty("权限菜单的子权限列表")
    private List<Menu> children = new ArrayList<>();

    public Menu(Permission p) {
        this.id = p.getId();
        this.name = p.getName();
        this.description = p.getDescription();
        this.url = p.getUrl();
        this.path = p.getPath();
        this.component = p.getComponent();
        this.pid = p.getPid();
        this.iconCls = p.getIconCls();
        this.keepAlive = p.getKeepAlive();
        this.requireAuth = p.getRequireAuth();
        this.isMenu = p.getIsMenu();
        this.currLevel = p.getCurrLevel();
    }
}
