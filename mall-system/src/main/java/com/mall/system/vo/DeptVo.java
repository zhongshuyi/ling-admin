package com.mall.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;

/**
 * 部门信息vo.
 *
 * @author 钟舒艺
 **/
@Data
@ApiModel("部门信息")
public
class DeptVo {

    private static final long serialVersionUID = 1L;

    /**
     * 部门id.
     */
    @ApiModelProperty("部门id")
    private Long id;

    /**
     * 父id.
     */
    @ApiModelProperty("父id")
    private Long parentId;

    /**
     * 部门名称.
     */
    @ApiModelProperty("部门名称")
    private String deptName;

    /**
     * 显示顺序.
     */
    @ApiModelProperty("显示顺序")
    private Integer orderNo;

    /**
     * 负责人.
     */
    @ApiModelProperty("负责人")
    private String leader;

    /**
     * 联系电话.
     */
    @ApiModelProperty("联系电话")
    private String phone;

    /**
     * 邮箱.
     */
    @ApiModelProperty("邮箱")
    private String email;

    /**
     * 父级列表.
     */
    @ApiModelProperty("父级列表")
    private String parentList;

    /**
     * 子级部门.
     */
    @ApiModelProperty("子级部门")
    private List<DeptVo> children;
}
