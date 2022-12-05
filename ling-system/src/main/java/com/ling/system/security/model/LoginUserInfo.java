package com.ling.system.security.model;

import com.ling.framework.model.User;
import com.ling.system.entity.SysDept;
import com.ling.system.entity.SysMenu;
import com.ling.system.entity.SysRole;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import lombok.Data;

/**
 * .
 *
 * @author 钟舒艺
 * @since 2022-10-26 19:22
 **/
@Data
public class LoginUserInfo implements Serializable {

    private static final long serialVersionUID = 4778465245152238732L;


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
    private List<SysMenu> menuAndPermissionList;

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
    private List<SysRole> roles;

    /**
     * 部门信息.
     */
    private List<SysDept> depts;

    /**
     * 是否为管理员.
     */
    private Boolean isAdmin;

}
