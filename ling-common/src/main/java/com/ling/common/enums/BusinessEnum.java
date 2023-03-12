package com.ling.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 业务枚举.
 *
 * @author zsy
 * @date 2023/03/09
 */
@Getter
@AllArgsConstructor
public enum BusinessEnum {
    /**
     * 用户管理业务.
     */
    SYS_USER(ModuleEnum.SYSTEM_MODULE, "user", "用户管理"),
    /**
     * 角色管理业务.
     */
    SYS_ROLE(ModuleEnum.SYSTEM_MODULE, "role", "角色管理"),
    /**
     * 部门管理业务.
     */
    SYS_DEPT(ModuleEnum.SYSTEM_MODULE, "dept", "部门管理"),
    /**
     * 部门管理业务.
     */
    SYS_SUB_DEPT(ModuleEnum.SYSTEM_MODULE, "subDept", "下级部门管理"),
    /**
     * 菜单管理业务.
     */
    SYS_MENU(ModuleEnum.SYSTEM_MODULE, "menu", "菜单管理");


    /**
     * 业务所在模块.
     */
    private final ModuleEnum module;

    /**
     * 业务代码.
     */
    private final String code;

    /**
     * 业务名称.
     */
    private final String businessName;
}
