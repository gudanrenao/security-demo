package com.zhangwenit.security.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.titan.common.util.FieldChecker;
import com.zhangwenit.security.demo.dto.*;
import com.zhangwenit.security.demo.dto.db.PageInfo;
import com.zhangwenit.security.demo.dto.req.SysUserModifyReq;
import com.zhangwenit.security.demo.dto.req.SysUserSearch;
import com.zhangwenit.security.demo.dto.req.UpdateUserRole;
import com.zhangwenit.security.demo.entity.SysPermissionEntity;
import com.zhangwenit.security.demo.entity.SysUserEntity;
import com.zhangwenit.security.demo.entity.SysUserRoleEntity;
import com.zhangwenit.security.demo.enums.DictEnum;
import com.zhangwenit.security.demo.mapper.SysPermissionMapper;
import com.zhangwenit.security.demo.mapper.SysRoleMapper;
import com.zhangwenit.security.demo.mapper.SysUserMapper;
import com.zhangwenit.security.demo.mapper.SysUserRoleMapper;
import com.zhangwenit.security.demo.utils.DatabaseUtils;
import com.zhangwenit.security.demo.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author ZWen
 * @Date 2019/9/25 6:45 PM
 * @Version 1.0
 **/
@Service
@Slf4j
public class SysPermissionService extends ServiceImpl<SysPermissionMapper, SysPermissionEntity> {

    private final SysPermissionMapper sysPermissionMapper;
    private final SysUserMapper sysUserMapper;
    private final SysRoleMapper sysRoleMapper;
    private final SysUserRoleMapper sysUserRoleMapper;

    private static List<Permission> permissionList;

    public SysPermissionService(SysPermissionMapper sysPermissionMapper, SysUserMapper sysUserMapper, SysRoleMapper sysRoleMapper, SysUserRoleMapper sysUserRoleMapper) {
        this.sysPermissionMapper = sysPermissionMapper;
        this.sysUserMapper = sysUserMapper;
        this.sysRoleMapper = sysRoleMapper;
        this.sysUserRoleMapper = sysUserRoleMapper;
        loadAllMenuPermission();
    }

    private void loadAllMenuPermission() {
        permissionList = sysPermissionMapper.findAll();
    }

    /**
     * 获取所有的权限树列表
     * <p>
     * 另一种sql方案：
     * <resultMap id="BaseResultMap" type="org.sang.bean.Department">
     * <id property="id" column="id"/>
     * <result column="name" property="name"/>
     * <result column="parentId" property="parentId"/>
     * <result column="isParent" property="isParent"/>
     * <collection property="children" ofType="org.sang.bean.Department" select="org.sang.mapper.DepartmentMapper.getDepByPid" column="id">
     * </collection>
     * </resultMap>
     * <select id="getDepByPid" resultMap="BaseResultMap">
     * select d1.*from department d1 where d1.`parentId`=#{pid} AND d1.enabled=true;
     * </select>
     *
     * @return
     */
    public List<Menu> menuTree() {
        //排序
        List<Permission> resultList = permissionList.stream().sorted(Comparator.comparingInt(Permission::getSid)).collect(Collectors.toList());
        return Menu.tree(resultList);
    }

    /**
     * 获取用户的所有的菜单&Component列表
     * todo:缓存
     *
     * @return
     */
    public MenuAndComponent userMenuTree() {
        List<Permission> permissions = baseMapper.findAllPermissionByUserId(SecurityUtils.getUserId());
        Map<String, Permission> beforeMap = permissions.stream().collect(Collectors.toMap(Permission::getId, e -> e));
        Map<String, Permission> permissionMap = permissionList.stream().collect(Collectors.toMap(Permission::getId, e -> e));
        final String rootId = "0";
        Map<String, Permission> needAddMap = new HashMap<>(4);
        beforeMap.forEach((k, v) -> {
            Permission permission = v;
            //需要添加所拥有菜单的所有的父菜单-递归
            while ((!rootId.equals(permission.getPid())) && (!needAddMap.containsKey(permission.getPid()))) {
                permission = permissionMap.get(permission.getPid());
                needAddMap.put(permission.getId(), permission);
            }
        });
        //合并
        beforeMap.putAll(needAddMap);
        //前端目前只是2级菜单，只需要去除非Component&排序
        List<Permission> resultList = new ArrayList<>(beforeMap.values()).stream().filter(e -> e.getIsPage() == 1).sorted(Comparator.comparingInt(Permission::getSid)).collect(Collectors.toList());
        MenuAndComponent result = new MenuAndComponent();
        result.setMenus(Menu.tree(resultList));
        //非菜单的Component需要合并到二级菜单上
        result.setComponents(Menu.component(resultList));
        return result;
    }

    /**
     * 根据名称匹配搜索用户列表
     *
     * @param keywords
     * @return
     */
    public List<SysUser> userSearchByKeywords(final String keywords) {
        return sysUserMapper.userSearchByKeywords(keywords, SecurityUtils.getMerchantId(), SecurityUtils.getUserId());
    }

    /**
     * 条件查询账号列表
     *
     * @param criteria
     * @return
     */
    public IPage<SysUser> userSearch(final SysUserSearch criteria) {
        IPage<SysUserEntity> page = new PageInfo<>(criteria.getCurrentPage(), criteria.getPageSize(), criteria);
        LambdaQueryWrapper<SysUserEntity> qw = Wrappers.<SysUserEntity>lambdaQuery()
                .eq(SysUserEntity::getMerchantId, SecurityUtils.getMerchantId())
                .ne(SysUserEntity::getId, SecurityUtils.getUserId())
                .like(StringUtils.isNotEmpty(criteria.getPhone()), SysUserEntity::getPhone, criteria.getPhone())
                .eq(criteria.getEnabled() != null, SysUserEntity::getEnabled, criteria.getEnabled())
                .and(StringUtils.isNotEmpty(criteria.getName()), e -> e.like(SysUserEntity::getName, criteria.getName()).or().like(SysUserEntity::getUsername, criteria.getName()))
                .eq(SysUserEntity::getType, DictEnum.SysUserType.COMMON);
        return sysUserMapper.selectPage(page, qw).convert(SysUser::new);
    }

    /**
     * 更新用户信息
     *
     * @param sysUser
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateUser(SysUser sysUser) {
        FieldChecker.assertNotEmpty(sysUser.getId(), "账号Id不能为空");
        SysUserEntity sysUserEntity = sysUserMapper.selectById(sysUser.getId());
        if (sysUser.getEnabled() != null) {
            if (sysUser.getEnabled() != DictEnum.SysUserEnabled.YES && sysUser.getEnabled() != DictEnum.SysUserEnabled.NO) {
                throw new IllegalArgumentException("状态值设置有误");
            }
            sysUserEntity.setEnabled(sysUser.getEnabled());
        }
        int update = sysUserMapper.updateById(sysUserEntity);
        DatabaseUtils.checkModifyOne(update);
    }

    /**
     * 根据Id查询账号信息
     *
     * @param userId
     * @return
     */
    public SysUser getUserById(final String userId) {
        return sysUserMapper.getUserById(userId);
    }

    /**
     * 获取所有角色列表
     *
     * @return
     */
    public List<SysRole> roles() {
        return sysRoleMapper.roles();
    }

    /**
     * 更新账号角色
     *
     * @param updateUserRole
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateUserRoles(UpdateUserRole updateUserRole) {
        //首先删除用户所有角色
        int delete = sysUserRoleMapper.delete(Wrappers.<SysUserRoleEntity>lambdaQuery().eq(SysUserRoleEntity::getSysUserId, updateUserRole.getUserId()));
        log.debug("updateUserRoles 已删除用户角色数量:[{}]", delete);
        int add = 0;
        if (!CollectionUtils.isEmpty(updateUserRole.getRids())) {
            //添加用户新的角色
            SysUserRoleEntity userRoleEntity;
            for (String rid : updateUserRole.getRids()) {
                userRoleEntity = new SysUserRoleEntity();
                userRoleEntity.setId(UUID.randomUUID().toString()).setSysUserId(updateUserRole.getUserId()).setSysRoleId(rid);
                int insert = sysUserRoleMapper.insert(userRoleEntity);
                DatabaseUtils.checkModifyOne(insert);
                add += 1;
            }
        }
        log.debug("updateUserRoles 已添加用户角色数量:[{}]", add);
    }

    /**
     * 新建或修改账号
     *
     * @param sysUserModifyReq
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void user(SysUserModifyReq sysUserModifyReq) {
        SysUserEntity sysUserEntity = null;
        boolean isNew = false;
        if (StringUtils.isEmpty(sysUserModifyReq.getId())) {
            isNew = true;
            sysUserEntity = new SysUserEntity().setId(UUID.randomUUID().toString())
                    .setMerchantId(SecurityUtils.getMerchantId())
                    .setEnabled(DictEnum.SysUserEnabled.YES).setType(DictEnum.SysUserType.COMMON);
        } else {
            sysUserEntity = sysUserMapper.selectById(sysUserModifyReq.getId());
            FieldChecker.assertNotNull(sysUserEntity, "未查询到该账号");
        }
        //更新基本信息
        sysUserEntity.setName(sysUserModifyReq.getName()).setUsername(sysUserModifyReq.getUsername()).setPhone(sysUserModifyReq.getPhone()).setHeadUrl(sysUserModifyReq.getHeadUrl()).setRemark(sysUserModifyReq.getRemark());
        //密码是否更改
        if (!sysUserModifyReq.getPassword().equals(sysUserEntity.getPassword())) {
            sysUserEntity.setPassword(new BCryptPasswordEncoder().encode(sysUserModifyReq.getPassword()));
        }
        int update = 0;
        if (isNew) {
            update = sysUserMapper.insert(sysUserEntity);
        } else {
            update = sysUserMapper.updateById(sysUserEntity);
        }
        DatabaseUtils.checkModifyOne(update);
    }
}