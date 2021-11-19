package com.mall.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 用户.
 *
 * @author 钟舒艺
 * @date 2021-11-16-8:56
 **/
@ApiModel("用户信息")
@Data
public class UserVo {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID.
     */
    @ApiModelProperty("用户ID")
    private Long id;

    /**
     * 用户账号.
     */
    @ApiModelProperty("用户账号")
    private String username;

    /**
     * 用户昵称.
     */
    @ApiModelProperty("用户昵称")
    private String realName;

    /**
     * 用户邮箱.
     */
    @ApiModelProperty("用户邮箱")
    private String email;

    /**
     * 手机号码.
     */
    @ApiModelProperty("手机号码")
    private String tel;

    /**
     * 用户性别（0男 1女 2未知）.
     */
    @ApiModelProperty("用户性别")
    private Integer sex;

    /**
     * 头像地址.
     */
    @ApiModelProperty("头像地址")
    private String avatar;

    /**
     * 账号状态（0正常 1停用）.
     */
    @ApiModelProperty("账号状态")
    private Integer status;

    /**
     * 删除标志（0 代表存在 2 代表删除）.
     */
    @ApiModelProperty("删除标志")
    private Integer delFlag;

    /**
     * 用户身份(0: 普通员工 ; 1: 上级).
     */
    @ApiModelProperty("用户身份")
    private Integer userIdentity;

    /**
     * 负责的部门.
     */
    @ApiModelProperty("负责的部门")
    private String departIds;

    /**
     * 最后登录IP.
     */
    @ApiModelProperty("最后登录IP")
    private Integer loginIp;

    /**
     * 最后登录时间.
     */
    @ApiModelProperty("最后登录时间")
    private Date loginDate;
}
