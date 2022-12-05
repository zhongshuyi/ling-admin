package com.ling.system.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ling.common.core.validate.ValidationGroups;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

/**
 * 角色操作Bo.
 *
 * @author 钟舒艺
 **/

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SysRoleDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 角色id.
     */
    @NotNull(
            message = "主键不能为空",
            groups = {ValidationGroups.EDIT})
    @Min(value = 0, message = "id最低为0", groups = {ValidationGroups.EDIT})
    private Long id;

    /**
     * 角色名称.
     */
    @NotNull(
            message = "角色名不能为空",
            groups = {ValidationGroups.ADD, ValidationGroups.EDIT})
    private String roleName;

    /**
     * 角色权限字符串.
     */
    @NotNull(
            message = "角色权限字符串不能为空",
            groups = {ValidationGroups.ADD, ValidationGroups.EDIT})
    private String roleKey;

    /**
     * 显示顺序.
     */
    @NotNull(
            message = "显示顺序不能为空",
            groups = {ValidationGroups.ADD, ValidationGroups.EDIT})
    @Min(
            value = 0,
            message = "显示顺序最低为0",
            groups = {ValidationGroups.ADD, ValidationGroups.EDIT})
    private Integer orderNo;

    /**
     * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限 5:仅本人数据权限）.
     */
    @Range(min = 1, max = 5, message = "数据范围,只能为1,2,3,4,5", groups = {ValidationGroups.EDIT})
    private Integer dataScope;


    /**
     * 角色状态（0正常 1停用）.
     */
    @NotNull(
            message = "角色状态不能为空",
            groups = {ValidationGroups.ADD, ValidationGroups.EDIT})
    @Max(
            value = 1,
            message = "角色状态,只能为0,1",
            groups = {ValidationGroups.ADD, ValidationGroups.EDIT})
    @Min(
            value = 0,
            message = "角色状态,只能为0,1",
            groups = {ValidationGroups.ADD, ValidationGroups.EDIT})
    private Integer status;

    /**
     * 备注.
     */
    @Size(max = 500, message = "最大是500")
    private String remark;
}
