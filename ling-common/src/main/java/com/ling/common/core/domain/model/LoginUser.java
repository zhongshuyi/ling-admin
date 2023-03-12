package com.ling.common.core.domain.model;

import java.util.List;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * new class.
 *
 * @author 钟舒艺
 * @since 2023-01-08 22:52
 **/
@Data
@Accessors(chain = true)
public class LoginUser {

    /**
     * 用户信息.
     */
    User user;

    /**
     * 角色代码.
     */
    private Set<String> roleNameSet;

    /**
     * 菜单列表.
     */
    private List<SysMenu> sysMenuAndPermissionList;

    /**
     * 权限列表.
     */
    private Set<String> permissionList;

    /**
     * 数据权限code列表.
     */
    private List<Integer> dataScopeTypeEnumCodeList;

    /**
     * 角色信息.
     */
    private List<SysRole> sysRoles;

    /**
     * 部门信息.
     */
    private List<SysDept> sysDepts;

    /**
     * 是否为管理员.
     */
    private Boolean isAdmin;
}
