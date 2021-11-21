package com.mall.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色信息视图.
 *
 * @author 钟舒艺
 **/
@Data
@ApiModel("角色信息")
public class RoleVo {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id.
     */
    @ApiModelProperty("角色id")
    private Long id;

    /**
     * 角色名称.
     */
    @ApiModelProperty("角色名称")
    private String roleName;

    /**
     * 角色权限字符串.
     */
    @ApiModelProperty("角色权限字符串")
    private String roleKey;

    /**
     * 显示顺序.
     */
    @ApiModelProperty("显示顺序")
    private Integer orderNo;

    /**
     * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）.
     */
    @ApiModelProperty("数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）")
    private Integer dataScope;


    /**
     * 角色状态（0正常 1停用）.
     */
    @ApiModelProperty("角色状态（0正常 1停用）")
    private Integer status;

    /**
     * 备注.
     */
    @ApiModelProperty("备注")
    private String remark;

}
