package com.zhangwenit.security.demo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.*;

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

    @ApiModelProperty(value = "是否跳转页面 1=是 0=否")
    private Integer isPage;

    @ApiModelProperty(value = "当前层级 最小为1")
    private Integer currLevel;

    @ApiModelProperty("子菜单列表")
    private List<Menu> children;

    public Menu(String id) {
        this.id = id;
        this.children = new ArrayList<>();
    }

    public Menu(Permission p, boolean hasChildren) {
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
        this.isPage = p.getIsPage();
        this.currLevel = p.getCurrLevel();
        if (hasChildren) {
            this.children = new ArrayList<>();
        }
    }

    /**
     * 构建菜单属性结构list（之前的算法，很low）
     *
     * @param list
     * @return
     */
    @Deprecated
    public static List<Menu> tree(List<Permission> list) {
        List<Menu> resultList = new ArrayList<>();
        //构建菜单属性结构
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, Menu> parentMap = new HashMap<>(16);
            //先添加一级目录
            for (Permission p : list) {
                if (1 == p.getCurrLevel()) {
                    Menu dto = new Menu(p, true);
                    parentMap.put(p.getId(), dto);
                    resultList.add(dto);
                }
            }
            //再添加子类
            int index = 2;
            //添加子菜单
            while (true) {
                //如果某个层级个数为0，那么循环结束
                int count = 0;
                for (Permission p : list) {
                    if (index == p.getCurrLevel()) {
                        count++;
                        Menu listDto = new Menu(p, true);
                        parentMap.put(p.getId(), listDto);
                        if (StringUtils.isNotEmpty(p.getPid())) {
                            Menu dto = parentMap.get(p.getPid());
                            if (dto != null) {
                                dto.getChildren().add(listDto);
                            }
                        } else {
                            throw new RuntimeException("数据异常");
                        }
                    }
                }
                if (count == 0) {
                    break;
                }
                index++;
            }
        }
        return resultList;
    }

    /**
     * 构建菜单属性结构list:优化算法
     *
     * @param list
     * @return
     */
    public static List<Menu> tree2(List<Permission> list) {
        // 检查列表为空
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        //跟阶段列表
        List<Menu> rootList = new ArrayList<>();
        Map<String, Menu> menuMap = new HashMap<>(list.size());
        for (Permission permission : list) {
            //构造菜单VO
            String permissionId = permission.getId();
            Menu menu = menuMap.get(permissionId);
            if (Objects.isNull(menu)) {
                menu = new Menu(permission, true);
                menuMap.put(permissionId, menu);
            } else {
                //补全菜单信息
                menu.fullInfo(permission);
            }
            //判断是否有父菜单
            String pid = permission.getPid();
            if (StringUtils.isEmpty(pid) || "0".equals(pid)) {
                //当前节点为根阶段
                rootList.add(menu);
            } else {
                //判断menuMap中是否有该父节点
                Menu parentMenu = menuMap.get(pid);
                if (Objects.isNull(parentMenu)) {
                    //构建父节点
                    parentMenu = new Menu(pid);
                    //将父节点添加到menuMap
                    menuMap.put(pid, parentMenu);
                }
                //将当前阶段添加到父节点下
                parentMenu.getChildren().add(menu);
            }
        }
        //返回根节点列表
        return rootList;
    }

    private void fullInfo(Permission p) {
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
        this.isPage = p.getIsPage();
        this.currLevel = p.getCurrLevel();
    }

    /**
     * 构建Component结构
     *
     * @param list
     * @return
     */
    public static List<Menu> component(List<Permission> list) {
        List<Menu> result = new ArrayList<>();
        for (Permission permission : list) {
            if (permission.getIsPage() == 1 && permission.getIsMenu() == 0) {
                result.add(new Menu(permission, false));
            }
        }
        return result;
    }


}
