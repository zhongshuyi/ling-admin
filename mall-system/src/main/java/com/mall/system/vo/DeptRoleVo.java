package com.mall.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * 部门角色.
 *
 * @author 钟舒艺
 * @date 2022-04-24
 */
@Data
@ApiModel("ums_dept_role")
public class DeptRoleVo implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 部门id.
     */
    @ApiModelProperty("部门id")
    private Long deptId;

    /**
     * 部门角色名.
     */
    @ApiModelProperty("部门角色名")
    private String roleName;

    /**
     * 备注.
     */
    @ApiModelProperty("备注")
    private String remark;
}