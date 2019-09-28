package com.zhangwenit.security.demo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 权限信息
 *
 * @author zw
 */
@Data
public class Permission implements Comparable, Serializable {

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

    @ApiModelProperty(value = "同级排序，最小为1，越小越靠前")
    private Integer sid;

    @ApiModelProperty(value = "前端图标iconCls")
    private String iconCls;

    @ApiModelProperty(value = "前端keep_alive属性")
    private Integer keepAlive;

    @ApiModelProperty(value = "是否需要权限 1=需要 2=不需要")
    private Integer requireAuth;

    @ApiModelProperty(value = "是否是菜单项 1=是 0=不是")
    private Integer isMenu;

    @ApiModelProperty(value = "是否跳转页面 1=是 0=否")
    private Integer isPage;

    @ApiModelProperty(value = "当前层级 最小为1")
    private Integer currLevel;

    @Override
    public int compareTo(Object o) {
        if (o == null) {
            return -1;
        }
        if (!(o instanceof Permission)) {
            return -1;
        }
        Permission other = (Permission) o;
        if (this.id.equals(other.getId())) {
            return 0;
        }
        return -1;
    }
}
