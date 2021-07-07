package com.mall.framework.security.service;

import cn.hutool.core.util.StrUtil;
import com.mall.mapper.UmsMenuMapper;
import com.mall.mapper.UmsRoleMapper;
import com.mall.model.UmsAdmin;
import com.mall.model.UmsRole;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户权限处理
 *
 * @author 钟舒艺
 * @date 2021-07-07-11:20
 **/
@Service
public class PermissionService {

    final
    UmsMenuMapper umsMenuMapper;

    final
    UmsRoleMapper umsRoleMapper;

    public PermissionService(UmsMenuMapper umsMenuMapper, UmsRoleMapper umsRoleMapper) {
        this.umsMenuMapper = umsMenuMapper;
        this.umsRoleMapper = umsRoleMapper;
    }

    /**
     * 根据用户信息获取权限键名
     * @param umsAdmin 用户信息
     * @return 角色标识列表
     */
    public Set<String> getRoleKeyList(UmsAdmin umsAdmin) {
        Set<String> roles = new HashSet<>();
        if (umsAdmin.getUserId()==1L){
            roles.add("admin");
        }else {
            List<UmsRole> umsRoles = umsRoleMapper.selectRoleListByUserId(umsAdmin.getUserId());
            for(UmsRole role : umsRoles){
                roles.add(StrUtil.trim(role.getRoleKey()));
            }
            roles.remove(null);
            roles.remove("");
        }
        return roles;
    }

    /**
     * 通过用户信息获取权限列表
     *
     * @param umsAdmin 用户信息
     * @return 权限列表
     */
    public Set<String> getPermissionList(UmsAdmin umsAdmin) {

        Set<String> perms = new HashSet<>();

        if (umsAdmin.getUserId() == 1L) {
            perms.add("*:*:*");
        } else {
            Set<String> p = umsMenuMapper.selectPermsByUserId(umsAdmin.getUserId());
            p.remove("");
            p.remove(null);
            for (String s : p) {
                perms.add(StrUtil.trim(s));
            }
        }
        return perms;
    }
}
