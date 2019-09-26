package com.zhangwenit.security.demo.controller;

import com.titan.common.result.ResultVO;
import com.zhangwenit.security.demo.dto.Menu;
import com.zhangwenit.security.demo.dto.SysUser;
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
    public ResultVO<List<Menu>> userMenuTree() {
        return ResultVO.buildSuccess(sysPermissionService.userMenuTree());
    }

    @ApiOperation("根据名称匹配搜索用户列表")
    @GetMapping("/userSearchByKeywords")
    public ResultVO<List<SysUser>> userSearchByKeywords(@RequestParam String keywords) {
        List<SysUser> userList = sysPermissionService.userSearchByKeywords(keywords);
        log.debug("userSearch response:[{}]", userList);
        return ResultVO.buildSuccess(userList);
    }

    @ApiOperation("更新用户信息")
    @PutMapping(value = "/updateUser")
    public ResultVO updateUser(@RequestBody SysUser sysUser) {
        if (sysPermissionService.updateUser(sysUser) == 1) {
            return ResultVO.buildSuccess("更新成功!");
        }
        return ResultVO.buildError(-1, "更新失败!");
    }

    @ApiOperation("根据Id查询账号信息")
    @GetMapping("/getUserById")
    public ResultVO<SysUser> getUserById(@RequestParam String id) {
        return ResultVO.buildSuccess(sysPermissionService.getUserById(id));
    }
}