package com.ling.system.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ling.common.core.domain.base.BaseVO;
import java.util.Date;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户.
 *
 * @author 钟舒艺
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class SysAdminVO extends BaseVO {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID.
     */
    private Long id;

    /**
     * 用户账号.
     */
    private String username;

    /**
     * 用户昵称.
     */
    private String realName;

    /**
     * 用户邮箱.
     */
    private String email;

    /**
     * 手机号码.
     */
    private String tel;

    /**
     * 用户性别（0男 1女 2未知）.
     */
    private Integer sex;

    /**
     * 头像地址.
     */
    private String avatar;

    /**
     * 账号状态（0正常 1停用）.
     */
    private Integer status;

    /**
     * 删除标志（0 代表存在 2 代表删除）.
     */
    private Integer delFlag;

    /**
     * 用户身份(0: 普通员工 ; 1: 上级).
     */
    private Integer userIdentity;

    /**
     * 负责的部门.
     */
    private String departIds;

    /**
     * 角色id集合.
     */
    private Set<Long> roleIds;

    /**
     * 部门id集合.
     */
    private Set<Long> deptIds;

    /**
     * 最后登录IP.
     */
    private Integer loginIp;

    /**
     * 最后登录时间.
     */
    private Date loginDate;

    /**
     * 最后登录地点.
     */
    private String loginAddress;
}
