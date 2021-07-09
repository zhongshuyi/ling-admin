package com.mall.framework.security.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.mall.common.core.util.ServletUtils;
import com.mall.framework.model.AdminUserDetails;
import com.mall.framework.util.JwtTokenUtil;
import com.mall.model.UmsRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 参照若依的权限实现
 * @author 钟舒艺
 * @date 2021-07-07-16:22
 **/
@Service("ss")
@SuppressWarnings("unused")
public class PermissionVerifyService {
    /**
     * 拥有所有权限标识符
     */
    private static final String ALL_PERMISSION = "*:*:*";

    /**
     * 管理员角色权限标识符
     */
    private static final String SUPER_ADMIN = "admin";

    /**
     * 角色分隔符
     */
    private static final String ROLE_DELIMETER = ",";

    /**
     * 权限分隔符
     */
    private static final String PERMISSION_DELIMETER = ",";

    final
    JwtTokenUtil jwtTokenUtil;

    public PermissionVerifyService(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    /**
     * 验证用户是否具备某权限
     * @param permission 权限字符串
     * @return 是否有权限
     */
    public boolean hasPermi(String permission){
        if (StrUtil.isEmpty(permission)){
            return false;
        }
        AdminUserDetails userDetails = jwtTokenUtil.getAdminUserDetails(ServletUtils.getRequest());

        if ( userDetails == null || CollUtil.isEmpty(userDetails.getPermissionList())){
            return false;
        }
        return hasPermissions(userDetails.getPermissionList(),permission);
    }

    /**
     * 验证用户是否不具备某权限
     * @param permission 权限字符串
     * @return 是否不具备该权限
     */
    public boolean lacksPermi(String permission){
        return !hasPermi(permission);
    }

    /**
     * 验证用户是否具有以下任意一个权限
     *
     * @param permissions 以 PERMISSION_NAMES_DELIMETER 为分隔符的权限列表
     * @return 用户是否具有以下任意一个权限
     */
    public boolean hasAnyPermi(String permissions){
        if (StrUtil.isEmpty(permissions)){
            return false;
        }
        AdminUserDetails userDetails = jwtTokenUtil.getAdminUserDetails(ServletUtils.getRequest());
        if ( userDetails == null || CollUtil.isEmpty(userDetails.getPermissionList())){
            return false;
        }
        Set<String> authorities = userDetails.getPermissionList();
        for(String permission : permissions.split(PERMISSION_DELIMETER)){
            if (permission != null && hasPermissions(authorities, permission))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 验证登录用户是否属于该角色
     * @param role 角色键名
     * @return 是否属于该角色
     */
    public boolean hasRole(String role){
        if(StrUtil.isEmpty(role)){
            return false;
        }
        AdminUserDetails userDetails = jwtTokenUtil.getAdminUserDetails(ServletUtils.getRequest());
        if (userDetails==null||CollUtil.isEmpty(userDetails.getRoles())){
            return false;
        }
        for (UmsRole umsRole: userDetails.getRoles()){
            String roleKey = umsRole.getRoleKey();
            if (SUPER_ADMIN.equals(roleKey) || roleKey.equals(StringUtils.trim(role)))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 验证用户是否不具备某角色，与 isRole逻辑相反。
     *
     * @param role 角色名称
     * @return 用户是否不具备某角色
     */
    public boolean lacksRole(String role)
    {
        return !hasRole(role);
    }


    /**
     * 验证用户是否具有以下任意一个角色
     *
     * @param roles 以 ROLE_NAMES_DELIMETER 为分隔符的角色列表
     * @return 用户是否具有以下任意一个角色
     */
    public boolean hasAnyRoles(String roles)
    {
        if (StrUtil.isEmpty(roles))
        {
            return false;
        }
        AdminUserDetails userDetails = jwtTokenUtil.getAdminUserDetails(ServletUtils.getRequest());
        if (userDetails ==null || CollUtil.isEmpty(userDetails.getRoles()))
        {
            return false;
        }
        for (String role : roles.split(ROLE_DELIMETER))
        {
            if (hasRole(role))
            {
                return true;
            }
        }
        return false;
    }


    /**
     * 判断是否包含某权限
     * @param permissions 权限集合
     * @param permission 单个权限
     * @return 是否包含权限
     */
    private boolean hasPermissions(Set<String> permissions, String permission)
    {
        return permissions.contains(ALL_PERMISSION) || permissions.contains(StrUtil.trim(permission));
    }


}
