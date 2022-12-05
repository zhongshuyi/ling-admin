package com.ling.system.dto;

import com.ling.common.constant.Regular;
import com.ling.common.core.validate.ValidationGroups;
import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 部门添加对象.
 *
 * @author 钟舒艺
 * @since 2021-10-08
 */

@Data
@Accessors(chain = true)
public class SysDeptDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 部门id.
     */
    @NotNull(message = "主键不能为空", groups = {ValidationGroups.EDIT})
    @Min(value = 0, message = "id最低为0", groups = {ValidationGroups.EDIT})
    private Long id;

    /**
     * 父id.
     */
    @NotNull(
            message = "父部门id不能为空",
            groups = {ValidationGroups.EDIT, ValidationGroups.ADD})
    @Min(value = 0,
            message = "父部门id最低为0",
            groups = {ValidationGroups.EDIT, ValidationGroups.ADD})
    private Long parentId;

    /**
     * 部门名称.
     */
    @NotNull(message = "部门id不能为空",
            groups = {ValidationGroups.EDIT, ValidationGroups.ADD})
    private String deptName;

    /**
     * 显示顺序.
     */
    @NotNull(message = "显示顺序不能为空",
            groups = {ValidationGroups.EDIT, ValidationGroups.ADD})
    @Min(value = 0,
            message = "显示顺序最低为0",
            groups = {ValidationGroups.ADD, ValidationGroups.EDIT})
    private Integer orderNo;

    /**
     * 负责人.
     */
    @Size(max = 30,
            message = "名字长度必须在30个字符以内",
            groups = {ValidationGroups.ADD, ValidationGroups.EDIT})
    private String leader;

    /**
     * 联系电话.
     */
    @Pattern(
            message = "必须是中国大陆电话号码",
            regexp = Regular.CHINA_TEL_NO,
            groups = {ValidationGroups.ADD, ValidationGroups.EDIT})
    private String phone;

    /**
     * 邮箱.
     */
    @Email(message = "必须是邮箱",
            groups = {ValidationGroups.ADD, ValidationGroups.EDIT})
    private String email;
}
