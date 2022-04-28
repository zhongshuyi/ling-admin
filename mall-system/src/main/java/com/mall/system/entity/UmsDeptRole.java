package com.mall.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mall.common.core.domain.BaseEntity;
import com.mall.common.core.validate.ValidationGroups;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 部门角色.
 *
 * @author 钟舒艺
 * @date 2022-04-24
 */
@Data
@TableName(value = "ums_dept_role")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class UmsDeptRole extends BaseEntity {

    private static final long serialVersionUID = -6421801012286290409L;

    /**
     * 主键id.
     */
    @ApiModelProperty(value = "部门角色id")
    @NotNull(
            message = "主键不能为空",
            groups = {ValidationGroups.Edit.class})
    @Min(value = 0, message = "id最低为0", groups = {ValidationGroups.Edit.class})
    private Long id;

    /**
     * 部门id.
     */
    @ApiModelProperty(value = "部门id")
    @NotNull(
            message = "部门id不能为空",
            groups = {ValidationGroups.Edit.class})
    @Min(value = 0, message = "id最低为0", groups = {ValidationGroups.Edit.class})
    private Long deptId;

    /**
     * 部门角色名.
     */
    @NotNull(
            message = "部门角色名不能为空",
            groups = {ValidationGroups.Edit.class, ValidationGroups.Add.class})
    private String roleName;

    /**
     * 备注.
     */
    private String remark;
}