package com.zhangwenit.security.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhangwenit.security.demo.dto.SysRole;
import com.zhangwenit.security.demo.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author zw
 * @since 2019-09-22
 */
public interface SysRoleMapper extends BaseMapper<SysRoleEntity> {

    /**
     * 获取所有角色列表
     *
     * @return
     */
    List<SysRole> roles();

    /**
     * 获取某账号所有角色列表
     *
     * @param userId 账号Id
     * @return
     */
    List<SysRole> findRolesByUserId(@Param("userId") String userId);

}
