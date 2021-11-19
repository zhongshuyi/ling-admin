package com.mall.system.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.mall.common.constant.Regular;
import com.mall.common.core.validate.ValidationGroups;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 用户信息操作类.
 *
 * @author 钟舒艺
 * @date 2021-11-16-11:00
 **/
@Data
@ApiModel("用户信息")
public class UserBo {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID.
     */
    @ApiModelProperty(value = "用户ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户账号.
     */
    @ApiModelProperty(value = "用户账号")
    @NotNull(message = "用户账号", groups = {ValidationGroups.Edit.class, ValidationGroups.Add.class})
    private String username;

    /**
     * 用户昵称.
     */
    @ApiModelProperty(value = "用户昵称")
    @NotNull(message = "用户昵称", groups = {ValidationGroups.Edit.class, ValidationGroups.Add.class})
    private String realName;

    /**
     * 用户邮箱.
     */
    @ApiModelProperty(value = "用户邮箱")
    @Email(groups = {ValidationGroups.Edit.class, ValidationGroups.Add.class})
    private String email;

    /**
     * 手机号码.
     */
    @ApiModelProperty(value = "手机号码")
    @Pattern(
            message = "必须是中国大陆电话号码",
            regexp = Regular.CHINA_TEL_NO,
            groups = {ValidationGroups.Add.class, ValidationGroups.Edit.class})
    private String tel;

    /**
     * 用户性别（0男 1女 2未知）.
     */
    @ApiModelProperty(value = "用户性别")
    private Integer sex;

    /**
     * 头像地址.
     */
    @ApiModelProperty(value = "头像地址")
    private String avatar;

    /**
     * password.
     */
    @ApiModelProperty(value = "用户密码")
    private String password;


    /**
     * 账号状态（0正常 1停用）.
     */
    @ApiModelProperty(value = "账号状态")
    private Integer status;


    /**
     * 用户身份(0: 普通员工 ; 1: 上级).
     */
    @ApiModelProperty(value = "用户身份")
    private Integer userIdentity;

    /**
     * 负责的部门.
     */
    @ApiModelProperty(value = "负责的部门")
    private String departIds;
}
