package com.ling.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ling.common.core.domain.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 用户信息表.
 *
 * @author 钟舒艺
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysAdmin extends User {

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
     * 头像文件的id.
     */
    private Long avatarFileId;

    /**
     * 头像地址.
     */
    @TableField(exist = false)
    private String avatar;

    /**
     * password.
     */
    private String password;


    /**
     * 账号状态（1正常 0停用）.
     */
    private Byte status;

    /**
     * 用户身份(0: 普通员工 ; 1: 上级).
     */
    private Integer userIdentity;

    /**
     * 负责的部门.
     */
    private String departIds;
}
