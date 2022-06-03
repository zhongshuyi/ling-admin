package com.ling.framework.model;

import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ling.common.constant.AppConstants;
import com.ling.system.entity.SysAdmin;
import com.ling.system.entity.SysDept;
import com.ling.system.entity.SysMenu;
import com.ling.system.entity.SysPermissionUrl;
import com.ling.system.entity.SysRole;
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
    private SysAdmin sysAdmin;

    /**
     * 角色代码.
     */
    private Set<String> roleNameSet;

    /**
     * 权限列表.
     */
    private List<SysMenu> permissionList;

    /**
     * 数据权限code列表.
     */
    private List<Integer> dataScopeTypeEnumCodeList;

    /**
     * 有权限的url列表.
     */
    private List<SysPermissionUrl> urlList;

    /**
     * 角色信息.
     */
    private List<SysRole> roles;

    /**
     * 部门信息.
     */
    private List<SysDept> depts;

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
     * @param sysAdmin       用户信息
     * @param permissionList 权限集合
     * @param urlList        可访问的url列表
     * @param sysRoles       用户角色组
     * @param depts          用户部门组
     */
    public AdminUserDetails(
            final SysAdmin sysAdmin,
            final List<SysMenu> permissionList,
            final List<SysPermissionUrl> urlList,
            final List<SysRole> sysRoles,
            final List<SysDept> depts
    ) {
        this.sysAdmin = sysAdmin;
        this.permissionList = permissionList;
        this.roles = sysRoles;
        this.roleNameSet = CollStreamUtil.toSet(sysRoles, SysRole::getRoleName);
        this.depts = depts;
        this.urlList = urlList;
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
        for (final SysRole role : roles) {
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
        return this.permissionList
                .stream()
                .filter(
                        p -> CharSequenceUtil.isNotEmpty(p.getPerms())
                )
                .map(
                        p -> new SimpleGrantedAuthority(p.getPerms())
                )
                .collect(Collectors.toList());
    }

    @Override
    public final String getPassword() {
        return sysAdmin.getPassword();
    }

    @Override
    public final String getUsername() {
        return sysAdmin.getUsername();
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
        return sysAdmin.getStatus().equals(AppConstants.ENABLE);
    }
}
