package com.mall.framework.model;

import cn.hutool.core.collection.CollUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mall.common.core.domain.entity.UmsAdmin;
import com.mall.common.core.domain.entity.UmsDept;
import com.mall.common.core.domain.entity.UmsMenu;
import com.mall.common.core.domain.entity.UmsRole;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户信息类
 *
 * @author 钟舒艺
 * @date 2021-07-03-20:02
 **/
@SuppressWarnings("unused")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminUserDetails implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据库中存储的用户基本信息
     */
    private UmsAdmin umsAdmin;

    /**
     * 权限代码
     */
    private Set<String> permissionCodeSet;

    /**
     * 权限列表
     */
    private List<UmsMenu> permissionList;

    /**
     * 角色信息
     */
    private List<UmsRole> roles;

    /**
     * 部门信息
     */
    private List<UmsDept> depts;

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

    public AdminUserDetails() {
    }

    /**
     * 构造方法
     *
     * @param umsAdmin       用户信息
     * @param permissionList 权限集合
     * @param umsRoles       用户角色组
     * @param depts          用户部门组
     */
    public AdminUserDetails(
            UmsAdmin umsAdmin,
            List<UmsMenu> permissionList,
            List<UmsRole> umsRoles, List<UmsDept> depts) {
        this.umsAdmin = umsAdmin;
        this.permissionList = permissionList;
        this.roles = umsRoles;
        this.depts = depts;
        if (umsAdmin.getIsAdmin()) {
            this.permissionCodeSet = new HashSet<>(1);
            this.permissionCodeSet.add("*:*:*");
        } else {
            this.permissionCodeSet =
                    permissionList.stream().map(UmsMenu::getPerms).collect(Collectors.toSet());
        }
    }

    /**
     * 获取角色的名称与键名
     *
     * @return 名称与键名集合
     */
    @JsonIgnore
    public List<Map<String, String>> getRoleKey() {
        if (CollUtil.isEmpty(roles)) {
            return null;
        }
        List<Map<String, String>> list = new ArrayList<>();
        for (UmsRole role : roles) {
            Map<String, String> map = new HashMap<>(2);
            map.put("roleName", role.getRoleName());
            map.put("value", role.getRoleKey());
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
        return umsAdmin.getUsername();
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
        return umsAdmin.getStatus() == 0;
    }
}
