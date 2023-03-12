package com.ling.system.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ling.common.constant.Regular;
import com.ling.common.core.domain.base.BaseDTO;
import com.ling.common.core.validate.ValidationGroups;
import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户信息操作类.
 *
 * @author 钟舒艺
 **/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SysAdminDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;


    /**
     * 查询时的部门id.
     */
    private Long deptId;

    /**
     * 新增时的部门id.
     */
    private Set<Long> deptIds;

    /**
     * 新增时用户的角色id集合.
     */
    private Set<Long> roleIds;

    /**
     * 用户账号.
     */
    @NotNull(message = "用户账号不能为空", groups = {ValidationGroups.Edit.class, ValidationGroups.Add.class})
    private String username;

    /**
     * 用户昵称.
     */
    @NotNull(message = "用户昵称不能为空", groups = {ValidationGroups.Edit.class, ValidationGroups.Add.class})
    private String realName;

    /**
     * 用户邮箱.
     */
    @Email(message = "用户邮箱必须是邮箱格式", groups = {ValidationGroups.Edit.class, ValidationGroups.Add.class})
    private String email;

    /**
     * 手机号码.
     */
    @Pattern(
            message = "必须是中国大陆电话号码",
            regexp = Regular.CHINA_TEL_NO,
            groups = {ValidationGroups.Add.class, ValidationGroups.Edit.class}
    )
    private String tel;

    /**
     * 用户性别（0男 1女 2未知）.
     */
    private Integer sex;

    /**
     * password.
     */
    private String password;

    /**
     * 确认密码.
     */
    private String passwordRepeat;


    /**
     * 账号状态（0正常 1停用）.
     */
    private Integer status;


    /**
     * 用户身份(0: 普通员工 ; 1: 上级).
     */
    private Integer userIdentity;

    /**
     * 负责的部门.
     */
    private String departIds;
}
