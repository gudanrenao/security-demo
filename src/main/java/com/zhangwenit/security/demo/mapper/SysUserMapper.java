package com.zhangwenit.security.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhangwenit.security.demo.dto.SysUser;
import com.zhangwenit.security.demo.entity.SysUserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 登录账号表 Mapper 接口
 * </p>
 *
 * @author zw
 * @since 2019-09-22
 */
public interface SysUserMapper extends BaseMapper<SysUserEntity> {

    /**
     * 获取用户及其角色信息
     *
     * @param username
     * @return
     */
    SysUser findByUserName(String username);

    /**
     * 获取账号的角色列表
     *
     * @param userList 账号Id列表(已排除当前账号)
     * @return
     */
    List<SysUser> findRolesGroupByUserIdList(@Param("userList") List<String> userList);

    /**
     * 根据Id查询账号信息
     *
     * @param userId
     * @return
     */
    SysUser getUserById(@Param("userId")String userId);
}
