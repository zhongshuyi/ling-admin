package com.ling.system.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ling.common.core.validate.ValidationGroups;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "角色")
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 角色id.
     */
    @ApiModelProperty(value = "角色id")
    @NotNull(
            message = "主键不能为空",
            groups = {ValidationGroups.Edit.class})
    @Min(value = 0, message = "id最低为0", groups = {ValidationGroups.Edit.class})
    private Long id;

    /**
     * 角色名称.
     */
    @ApiModelProperty(value = "角色名称")
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
    @ApiModelProperty(value = "角色权限字符串")
    private String roleKey;

    /**
     * 显示顺序.
     */
    @NotNull(
            message = "显示顺序不能为空",
            groups = {ValidationGroups.Add.class, ValidationGroups.Edit.class})
    @Min(
            value = 0,
            message = "显示顺序最低为0",
            groups = {ValidationGroups.Add.class, ValidationGroups.Edit.class})
    @ApiModelProperty(value = "显示顺序")
    private Integer orderNo;

    /**
     * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限 5:仅本人数据权限）.
     */
    @ApiModelProperty(value = "数据范围")
    @Range(min = 1, max = 5, message = "数据范围,只能为1,2,3,4,5", groups = {ValidationGroups.Edit.class})
    private Integer dataScope;


    /**
     * 角色状态（0正常 1停用）.
     */
    @NotNull(
            message = "角色状态不能为空",
            groups = {ValidationGroups.Add.class, ValidationGroups.Edit.class})
    @Max(
            value = 1,
            message = "角色状态,只能为0,1",
            groups = {ValidationGroups.Add.class, ValidationGroups.Edit.class})
    @Min(
            value = 0,
            message = "角色状态,只能为0,1",
            groups = {ValidationGroups.Add.class, ValidationGroups.Edit.class})
    @ApiModelProperty(value = "角色状态")
    private Integer status;

    /**
     * 备注.
     */
    @ApiModelProperty(value = "备注")
    @Size(max = 500, message = "最大是500")
    private String remark;
}
