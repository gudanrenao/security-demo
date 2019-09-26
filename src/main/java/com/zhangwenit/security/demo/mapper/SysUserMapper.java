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
     * 根据名称匹配搜索用户列表
     *
     * @param keywords
     * @param merchantId
     * @return
     */
    List<SysUser> userSearchByKeywords(@Param("keywords") String keywords, @Param("merchantId") String merchantId);

    /**
     * 更新用户信息
     *
     * @param sysUser
     * @return
     */
    int updateUser(SysUser sysUser);

    /**
     * 根据Id查询账号信息
     *
     * @param userId
     * @return
     */
    SysUser getUserById(@Param("userId")String userId);
}
