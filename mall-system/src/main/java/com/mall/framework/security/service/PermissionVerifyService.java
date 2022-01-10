package com.mall.framework.security.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.mall.common.util.ServletUtils;
import com.mall.framework.model.AdminUserDetails;
import com.mall.framework.util.JwtTokenUtil;
import com.mall.system.entity.UmsRole;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 参照若依的权限实现.
 *
 * @author 钟舒艺
 **/
@Service("ss")
@SuppressWarnings("unused")
@RequiredArgsConstructor
public class PermissionVerifyService {
    /**
     * 拥有所有权限标识符.
     */
    private static final String ALL_PERMISSION = "*:*:*";

    /**
     * 管理员角色权限标识符.
     */
    private static final String SUPER_ADMIN = "admin";

    /**
     * 角色分隔符.
     */
    private static final String ROLE_DELIMETER = ",";

    /**
     * 权限分隔符.
     */
    private static final String PERMISSION_DELIMETER = ",";

    /**
     * jwt工具类.
     */
    private final JwtTokenUtil jwtTokenUtil;


    /**
     * 验证用户是否具备某权限.
     *
     * @param permission 权限字符串
     * @return 是否有权限
     */
    private boolean hasPermi(final String permission) {
        if (CharSequenceUtil.isEmpty(permission)) {
            return false;
        }
        final AdminUserDetails userDetails = jwtTokenUtil.getAdminUserDetails(ServletUtils.getRequest());

        if (userDetails == null || CollUtil.isEmpty(userDetails.getPermissionList())) {
            return false;
        }
        return hasPermissions(userDetails.getPermissionCodeSet(), permission);
    }

    /**
     * 验证用户是否不具备某权限.
     *
     * @param permission 权限字符串
     * @return 是否不具备该权限
     */
    public boolean lacksPermi(final String permission) {
        return !hasPermi(permission);
    }

    /**
     * 验证用户是否具有以下任意一个权限.
     *
     * @param permissions 以 PERMISSION_NAMES_DELIMETER 为分隔符的权限列表
     * @return 用户是否具有以下任意一个权限
     */
    public boolean hasAnyPermi(final String permissions) {
        if (CharSequenceUtil.isEmpty(permissions)) {
            return false;
        }
        final AdminUserDetails userDetails = jwtTokenUtil.getAdminUserDetails(ServletUtils.getRequest());
        if (userDetails == null || CollUtil.isEmpty(userDetails.getPermissionList())) {
            return false;
        }
        final Set<String> authorities = userDetails.getPermissionCodeSet();
        for (final String permission : permissions.split(PERMISSION_DELIMETER)) {
            if (permission != null && hasPermissions(authorities, permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 验证登录用户是否属于该角色.
     *
     * @param role 角色键名
     * @return 是否属于该角色
     */
    private boolean hasRole(final String role) {
        if (CharSequenceUtil.isEmpty(role)) {
            return false;
        }
        final AdminUserDetails userDetails = jwtTokenUtil.getAdminUserDetails(ServletUtils.getRequest());
        if (userDetails == null || CollUtil.isEmpty(userDetails.getRoles())) {
            return false;
        }
        for (final UmsRole umsRole : userDetails.getRoles()) {
            final String roleKey = umsRole.getRoleKey();
            if (SUPER_ADMIN.equals(roleKey) || roleKey.equals(CharSequenceUtil.trim(role))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 验证用户是否不具备某角色，与 isRole逻辑相反。.
     *
     * @param role 角色名称
     * @return 用户是否不具备某角色
     */
    public boolean lacksRole(final String role) {
        return !hasRole(role);
    }


    /**
     * 验证用户是否具有以下任意一个角色.
     *
     * @param roles 以 ROLE_NAMES_DELIMETER 为分隔符的角色列表
     * @return 用户是否具有以下任意一个角色
     */
    public boolean hasAnyRoles(final String roles) {
        if (CharSequenceUtil.isEmpty(roles)) {
            return false;
        }
        final AdminUserDetails userDetails = jwtTokenUtil.getAdminUserDetails(ServletUtils.getRequest());
        if (userDetails == null || CollUtil.isEmpty(userDetails.getRoles())) {
            return false;
        }
        for (final String role : roles.split(ROLE_DELIMETER)) {
            if (hasRole(role)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 判断是否包含某权限.
     *
     * @param permissions 权限集合
     * @param permission  单个权限
     * @return 是否包含权限
     */
    private boolean hasPermissions(final Set<String> permissions, final String permission) {
        return permissions.contains(ALL_PERMISSION)
                || permissions.contains(CharSequenceUtil.trim(permission));
    }


}
