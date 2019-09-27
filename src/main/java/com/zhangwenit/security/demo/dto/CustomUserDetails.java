package com.zhangwenit.security.demo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

/**
 * @Description
 * @Author ZWen
 * @Date 2019/9/25 10:27 AM
 * @Version 1.0
 **/
@Data
@Accessors(chain = true)
public class CustomUserDetails extends User {

    @ApiModelProperty(value = "账号类型 1=超级管理员 2=普通账号")
    private Integer type;

    @ApiModelProperty("账号Id")
    private String userId;

    @ApiModelProperty(value = "商户Id")
    private String merchantId;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "用户真实姓名")
    private String name;

    @ApiModelProperty(value = "头像地址")
    private String headUrl;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty("角色列表")
    private List<SysRole> roles;

    @ApiModelProperty("资源列表")
    private List<Resource> resourceList;

    /**
     * 验证前构建方法
     * Calls the more complex constructor with all boolean arguments set to {@code true}.
     *
     * @param username
     * @param password
     * @param authorities
     */
    public CustomUserDetails(String username, String password, boolean enabled, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, true, true, true, authorities);
    }

    /**
     * 验证后构建方法
     * Construct the <code>User</code> with the details required by
     * {@link DaoAuthenticationProvider}.
     *
     * @param username              the username presented to the
     *                              <code>DaoAuthenticationProvider</code>
     * @param password              the password that should be presented to the
     *                              <code>DaoAuthenticationProvider</code>
     * @param enabled               set to <code>true</code> if the user is enabled
     * @param accountNonExpired     set to <code>true</code> if the account has not expired
     * @param credentialsNonExpired set to <code>true</code> if the credentials have not
     *                              expired
     * @param accountNonLocked      set to <code>true</code> if the account is not locked
     * @param authorities           the authorities that should be granted to the caller if they
     *                              presented the correct username and password and the user is enabled. Not null.
     * @throws IllegalArgumentException if a <code>null</code> value was passed either as
     *                                  a parameter or as an element in the <code>GrantedAuthority</code> collection
     */
    public CustomUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    /**
     * 填充账户基本信息
     *
     * @param user
     * @param resourceList
     */
    public void fillUserInfo(SysUser user, List<Resource> resourceList) {
        this.
                setResourceList(resourceList)
                .setUserId(user.getId())
                .setMerchantId(user.getMerchantId())
                .setType(user.getType() != null ? user.getType() : 2)
                .setHeadUrl(user.getHeadUrl())
                .setPhone(user.getPhone())
                .setRemark(user.getRemark())
                .setName(user.getName());
    }

}