package com.mall.framework.model;

import cn.hutool.core.collection.CollUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mall.model.UmsAdmin;
import com.mall.model.UmsRole;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * 用户信息类
 *
 * @author 钟舒艺
 * @date 2021-07-03-20:02
 **/
@SuppressWarnings("unused")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    /**
     * 数据库中存储的用户基本信息
     */
    private UmsAdmin umsAdmin;

    /**
     * 权限信息
     */
    private Set<String> permissionList;

    /**
     * 角色信息
     */
    private List<UmsRole> roles;


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

    public AdminUserDetails(){}

    public AdminUserDetails(UmsAdmin umsAdmin, Set<String> permissionList, List<UmsRole> umsRoles) {
        this.umsAdmin = umsAdmin;
        this.permissionList = permissionList;
        this.roles = umsRoles;
    }

    public List<Map<String, String>> getRoleKey(){
        if(CollUtil.isEmpty(roles)) {
            return null;
        }
        List<Map<String,String>> list = new ArrayList<>();
        for(UmsRole role : roles){
            Map<String,String> map = new HashMap<>(2);
            map.put("roleName",role.getRoleName());
            map.put("value",role.getRoleKey());
            list.add(map);
        }
        return list;
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
        return umsAdmin.getStatus()==0;
    }
}
