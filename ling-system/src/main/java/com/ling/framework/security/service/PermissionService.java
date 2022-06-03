package com.ling.framework.security.service;

import com.ling.system.entity.SysAdmin;
import com.ling.system.entity.SysMenu;
import com.ling.system.mapper.SysMenuMapper;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 用户权限处理.
 *
 * @author 钟舒艺
 **/
@Service
@RequiredArgsConstructor
class PermissionService {

    /**
     * 菜单服务.
     */
    private final SysMenuMapper sysMenuMapper;

    /**
     * 通过用户信息获取权限列表.
     *
     * @param sysAdmin 用户信息
     * @return 权限列表
     */
    List<SysMenu> getPermissionList(final SysAdmin sysAdmin) {

        final List<SysMenu> perms = new ArrayList<>();

        if (Boolean.FALSE.equals(sysAdmin.getIsAdmin())) {
            perms.addAll(sysMenuMapper.selectUserPermsById(sysAdmin.getId()));
            perms.addAll(sysMenuMapper.selectDeptRolePermsByUserId(sysAdmin.getId()));
        }
        return perms;
    }
}
