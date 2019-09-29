package com.zhangwenit.security.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.titan.common.result.ResultVO;
import com.zhangwenit.security.demo.dto.Menu;
import com.zhangwenit.security.demo.dto.MenuAndComponent;
import com.zhangwenit.security.demo.dto.SysRole;
import com.zhangwenit.security.demo.dto.SysUser;
import com.zhangwenit.security.demo.dto.db.PageInfo;
import com.zhangwenit.security.demo.dto.req.SysUserModifyReq;
import com.zhangwenit.security.demo.dto.req.SysUserSearch;
import com.zhangwenit.security.demo.dto.req.UpdateUserRole;
import com.zhangwenit.security.demo.service.MyInvocationSecurityMetadataSourceService;
import com.zhangwenit.security.demo.service.SysPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description
 * @Author ZWen
 * @Date 2019/9/25 6:44 PM
 * @Version 1.0
 **/
@Slf4j
@RestController
@RequestMapping("/permission")
@Api(tags = "权限相关")
public class SecurityController {

    private final SysPermissionService sysPermissionService;

    public SecurityController(SysPermissionService sysPermissionService) {
        this.sysPermissionService = sysPermissionService;
    }

    @ApiOperation("获取全部的菜单树列表")
    @GetMapping("/menuTree")
    public ResultVO<List<Menu>> menuTree() {
        return ResultVO.buildSuccess(sysPermissionService.menuTree());
    }

    /**
     * 该请求任何登录用户都可以访问
     *
     * @return
     * @see MyInvocationSecurityMetadataSourceService#loadWithoutRoleUriSet()
     */
    @ApiOperation("获取登录账号拥有的菜单列表")
    @GetMapping("/userMenuTree")
    public ResultVO<MenuAndComponent> userMenuTree() {
        return ResultVO.buildSuccess(sysPermissionService.userMenuTree());
    }

    @ApiOperation("根据名称匹配搜索用户列表")
    @GetMapping("/userSearchByKeywords")
    public ResultVO<List<SysUser>> userSearchByKeywords(@RequestParam String keywords) {
        List<SysUser> userList = sysPermissionService.userSearchByKeywords(keywords);
        log.debug("userSearchByKeywords response:[{}]", userList);
        return ResultVO.buildSuccess(userList);
    }

    @ApiOperation("条件查询账号列表")
    @PostMapping("/userSearch")
    public ResultVO<PageInfo<SysUser>> userSearch(@RequestBody SysUserSearch criteria) {
        IPage<SysUser> userList = sysPermissionService.userSearch(criteria);
        log.debug("userSearch response:[{}]", userList);
        return ResultVO.buildSuccess((PageInfo<SysUser>) userList);
    }

    @ApiOperation("更新用户信息")
    @PutMapping(value = "/updateUser")
    public ResultVO updateUser(@RequestBody SysUser sysUser) {
        sysPermissionService.updateUser(sysUser);
        return ResultVO.buildSuccess();
    }

    @ApiOperation("根据Id查询账号基本信息及角色信息")
    @GetMapping("/getUserById")
    public ResultVO<SysUser> getUserById(@RequestParam String id) {
        return ResultVO.buildSuccess(sysPermissionService.getUserById(id));
    }

    @ApiOperation("获取所有角色列表")
    @GetMapping(value = "/roles")
    public ResultVO<List<SysRole>> allRoles() {
        return ResultVO.buildSuccess(sysPermissionService.roles());
    }

    @ApiOperation("更新账号角色")
    @PutMapping(value = "/updateUserRoles")
    public ResultVO updateUserRoles(@RequestBody UpdateUserRole updateUserRole) {
        updateUserRole.checkParams();
        sysPermissionService.updateUserRoles(updateUserRole);
        return ResultVO.buildSuccess();
    }

    @ApiOperation("新建或修改账号")
    @PostMapping(value = "/user")
    public ResultVO user(@RequestBody SysUserModifyReq sysUserModifyReq) {
        sysUserModifyReq.checkParams();
        sysPermissionService.user(sysUserModifyReq);
        return ResultVO.buildSuccess();
    }
}