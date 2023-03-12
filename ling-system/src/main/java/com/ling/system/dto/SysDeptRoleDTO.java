package com.ling.system.dto;

import com.ling.common.core.validate.ValidationGroups;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 角色操作Bo.
 *
 * @author 钟舒艺
 **/

@Data
@Accessors(chain = true)
public class SysDeptRoleDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 角色id.
     */
    @NotNull(
            message = "主键不能为空",
            groups = {ValidationGroups.Edit.class})
    @Min(value = 0, message = "id最低为0", groups = {ValidationGroups.Edit.class})
    private Long id;

    /**
     * 部门id.
     */
    private Long deptId;

    /**
     * 角色名称.
     */
    @NotNull(
            message = "角色名不能为空",
            groups = {ValidationGroups.Add.class, ValidationGroups.Edit.class})
    private String roleName;

    /**
     * 角色权限字符串.
     */
    @NotNull(
            message = "角色权限字符串不能为空",
            groups = {ValidationGroups.Add.class, ValidationGroups.Edit.class})
    private String roleKey;

    /**
     * 备注.
     */
    @Size(max = 500, message = "最大是500")
    private String remark;
}
