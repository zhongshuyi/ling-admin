package com.mall.system.bo.add;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 部门添加对象
 * @author 钟舒艺
 * @since 2021-10-08
 */

@Data
@Accessors(chain = true)
@ApiModel(value = "部门表")
public class DeptAddBo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门id")
    private Long id;

    /**
     * 父id
     */
    @ApiModelProperty(value = "父id")
    private Long parentId;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称")
    private String deptName;

    /**
     * 显示顺序
     */
    @ApiModelProperty(value = "显示顺序")
    private Integer orderNum;

    /**
     * 负责人
     */
    @ApiModelProperty(value = "负责人")
    private String leader;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String phone;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;
}
