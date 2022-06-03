package com.ling.framework.config.custom;

import java.io.Serializable;
import lombok.Data;

/**
 * 系统配置信息.
 *
 * @author 钟舒艺
 **/
@Data
public class AppConfig implements Serializable {


    private static final long serialVersionUID = 7998756149861284263L;

    /**
     * 超级管理员的角色id.
     */
    private Long superAdminRoleId;

    /**
     * 当没有设置角色时默认的角色.
     */
    private Long defaultRoleId;

    /**
     * 当没有设置部门时默认的部门.
     */
    private Long defaultDeptId;

    /**
     * 头像允许的后缀.
     */
    private String avatarPostfix;

    /**
     * 头像储存的地址.
     */
    private String avatarPath;

}
