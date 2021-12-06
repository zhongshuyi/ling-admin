package com.mall.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.mall.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 用户信息表.
 *
 * @author 钟舒艺
 * @since 2021-07-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Accessors(chain = true)
public class UmsAdmin extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID.
     */
    @TableId(value = "id", type = IdType.AUTO)
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
     * password.
     */
    private String password;


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
     * 最后登录IP.
     */
    private Integer loginIp;

    /**
     * 最后登录时间.
     */
    private Date loginDate;

    /**
     * 是否是超级管理员.
     */
    @TableField(exist = false)
    private Boolean isAdmin;

    /**
     * 最后登录地点.
     */
    @ApiModelProperty("最后登录地点")
    private String loginAddress;
}
