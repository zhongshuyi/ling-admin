package com.mall.framework.model;

import com.mall.model.UmsAdmin;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * 用户信息类
 * @author 钟舒艺
 * @date 2021-07-03-20:02
 **/
@SuppressWarnings("unused")
@Data
public class AdminUserDetails implements UserDetails {

    /**
     * 数据库中存储的用户基本信息
     */
    private final UmsAdmin umsAdmin;

    /**
     * 权限信息
     */
    private final Set<String> permissionList;

    /**
     * uuid
     */
    private String uuid;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;




    public AdminUserDetails(UmsAdmin umsAdmin, Set<String> permissionList) {
        this.umsAdmin = umsAdmin;
        this.permissionList = permissionList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的权限
        return null;
    }

    @Override
    public String getPassword() {
        return umsAdmin.getPassword();
    }

    @Override
    public String getUsername() {
        return umsAdmin.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return "0".equals(umsAdmin.getStatus());
    }
}
