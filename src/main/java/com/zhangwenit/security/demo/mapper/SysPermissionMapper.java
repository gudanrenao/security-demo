package com.zhangwenit.security.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhangwenit.security.demo.dto.Permission;
import com.zhangwenit.security.demo.dto.RolePermission;
import com.zhangwenit.security.demo.dto.SysRole;
import com.zhangwenit.security.demo.entity.SysPermissionEntity;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author zw
 * @since 2019-09-22
 */
public interface SysPermissionMapper extends BaseMapper<SysPermissionEntity> {

    /**
     * 获取所有权限
     * 优先级:比如我的地址是/employee/basic/hello,这个地址既能被/employee/**匹配，也能被/employee/basic/**匹配，
     * 这就要求我们从数据库查询的时候对数据进行排序，将/employee/basic/**类型的url pattern放在集合的前面去比较
     * order by curr_level desc
     *
     * @return
     */
    List<Permission> findAll();

    /**
     * 获取某个用户的所有角色
     *
     * @param userId
     * @return
     */
    List<SysRole> findAllRoleByUserId(String userId);

    /**
     * 获取某个用户的所有权限
     *
     * @param userId
     * @return
     */
    List<Permission> findAllPermissionByUserId(String userId);

    /**
     * 获取所有菜单权限
     *
     * @return
     */
    List<Permission> findAllMenuPermission();

    /**
     * 获取所有角色权限关系
     *
     * @return
     */
    List<RolePermission> findAllRolePermission();
}
