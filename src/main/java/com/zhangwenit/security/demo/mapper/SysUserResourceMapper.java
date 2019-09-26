package com.zhangwenit.security.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhangwenit.security.demo.entity.SysUserResourceEntity;

import java.util.List;

/**
 * <p>
 * 资源权限表 Mapper 接口
 * </p>
 *
 * @author zw
 * @since 2019-09-22
 */
public interface SysUserResourceMapper extends BaseMapper<SysUserResourceEntity> {

    /**
     * 获取用户资源权限列表
     *
     * @param userId
     * @return
     */
    List<String> selectResourceByUserId(String userId);
}
