package com.ling.system.vo;

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
@ApiModel("部门角色")
public class DeptRoleVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 部门角色id.
     */
    @ApiModelProperty("部门角色id")
    private Long id;


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
     * 角色权限字符串.
     */
    @ApiModelProperty("角色权限字符串")
    private String roleKey;

    /**
     * 备注.
     */
    @ApiModelProperty("备注")
    private String remark;
}
