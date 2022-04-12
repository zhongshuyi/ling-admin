package com.mall.framework.model;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mall.system.entity.UmsAdmin;
import com.mall.system.entity.UmsDept;
import com.mall.system.entity.UmsMenu;
import com.mall.system.entity.UmsPermissionUrl;
import com.mall.system.entity.UmsRole;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户信息类.
 *
 * @author 钟舒艺
 **/
@SuppressWarnings("unused")
@Data
@Slf4j
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserDetails implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据库中存储的用户基本信息.
     */
    private UmsAdmin umsAdmin;

    /**
     * 权限代码.
     */
    private List<GrantedAuthority> permissionCodeList;

    /**
     * 角色代码.
     */
    private Set<String> roleNameSet;

    /**
     * 权限列表.
     */
    private List<UmsMenu> permissionList;

    /**
     * 有权限的url列表.
     */
    private List<UmsPermissionUrl> urlList;

    /**
     * 角色信息.
     */
    private List<UmsRole> roles;

    /**
     * 部门信息.
     */
    private List<UmsDept> depts;

    /**
     * uuid.
     */
    private String uuid;

    /**
     * 登录时间.
     */
    private Long loginTime;

    /**
     * 过期时间.
     */
    private Long expireTime;


    /**
     * 构造方法.
     *
     * @param umsAdmin       用户信息
     * @param permissionList 权限集合
     * @param urlList        可访问的url列表
     * @param umsRoles       用户角色组
     * @param depts          用户部门组
     */
    public AdminUserDetails(
            final UmsAdmin umsAdmin,
            final List<UmsMenu> permissionList,
            final List<UmsPermissionUrl> urlList,
            final List<UmsRole> umsRoles,
            final List<UmsDept> depts
    ) {
        this.umsAdmin = umsAdmin;
        this.permissionList = permissionList;
        this.roles = umsRoles;
        this.roleNameSet = umsRoles.stream().map(UmsRole::getRoleName).collect(Collectors.toSet());
        this.depts = depts;
        this.urlList = urlList;
        this.permissionCodeList =
                permissionList
                        .stream()
                        .filter(
                                p -> CharSequenceUtil.isNotEmpty(p.getPerms())
                        )
                        .map(
                                p -> new SimpleGrantedAuthority(p.getPerms())
                        )
                        .collect(Collectors.toList());
    }

    /**
     * 获取角色的名称与键名.
     *
     * @return 名称与键名集合
     */
    @JsonIgnore
    public List<Map<String, String>> getRoleKey() {
        if (CollUtil.isEmpty(roles)) {
            return Collections.emptyList();
        }
        final List<Map<String, String>> list = new ArrayList<>();
        for (final UmsRole role : roles) {
            final Map<String, String> map = new HashMap<>(2);
            map.put("roleName", role.getRoleName());
            map.put("value", role.getRoleKey());
            list.add(map);
        }
        return list;
    }

    @Override
    public final Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的权限
        return permissionCodeList;
    }

    @Override
    public final String getPassword() {
        return umsAdmin.getPassword();
    }

    @Override
    public final String getUsername() {
        return umsAdmin.getUsername();
    }

    @Override
    public final boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public final boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public final boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public final boolean isEnabled() {
        return umsAdmin.getStatus() == 0;
    }
}
