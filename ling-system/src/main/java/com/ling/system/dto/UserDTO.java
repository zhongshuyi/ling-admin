package com.ling.system.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ling.common.constant.Regular;
import com.ling.common.core.validate.ValidationGroups;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;

/**
 * 用户信息操作类.
 *
 * @author 钟舒艺
 **/
@Data
@ApiModel("用户信息")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID.
     */
    @ApiModelProperty(value = "用户ID")
    @NotNull(message = "主键不能为空", groups = {ValidationGroups.Edit.class})
    @Min(value = 0, message = "id最低为0", groups = {ValidationGroups.Edit.class})
    private Long id;


    /**
     * 查询时的部门id.
     */
    @ApiModelProperty(value = "查询时的用户的部门id")
    private Long deptId;

    /**
     * 新增时的部门id.
     */
    @ApiModelProperty(value = "新增时用户的部门id集合")
    private Set<Long> deptIds;

    /**
     * 新增时用户的角色id集合.
     */
    @ApiModelProperty(value = "新增时用户的角色id集合")
    private Set<Long> roleIds;

    /**
     * 用户账号.
     */
    @ApiModelProperty(value = "用户账号")
    @NotNull(message = "用户账号不能为空", groups = {ValidationGroups.Edit.class, ValidationGroups.Add.class})
    private String username;

    /**
     * 用户昵称.
     */
    @ApiModelProperty(value = "用户昵称")
    @NotNull(message = "用户昵称不能为空", groups = {ValidationGroups.Edit.class, ValidationGroups.Add.class})
    private String realName;

    /**
     * 用户邮箱.
     */
    @ApiModelProperty(value = "用户邮箱")
    @Email(message = "用户邮箱必须是邮箱格式", groups = {ValidationGroups.Edit.class, ValidationGroups.Add.class})
    private String email;

    /**
     * 手机号码.
     */
    @ApiModelProperty(value = "手机号码")
    @Pattern(
            message = "必须是中国大陆电话号码",
            regexp = Regular.CHINA_TEL_NO,
            groups = {ValidationGroups.Add.class, ValidationGroups.Edit.class}
    )
    private String tel;

    /**
     * 用户性别（0男 1女 2未知）.
     */
    @ApiModelProperty(value = "用户性别")
    private Integer sex;

    /**
     * password.
     */
    @ApiModelProperty(value = "用户密码")
    private String password;

    /**
     * 确认密码.
     */
    @ApiModelProperty(value = "确认密码")
    private String passwordRepeat;


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
