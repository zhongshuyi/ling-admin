package com.mall.system.bo;

import com.mall.common.constant.Regular;
import com.mall.common.core.validate.ValidationGroups;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 部门添加对象
 *
 * @author 钟舒艺
 * @since 2021-10-08
 */

@Data
@Accessors(chain = true)
@ApiModel(value = "部门表")
public class DeptBo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门id")
    @NotNull(message = "主键不能为空", groups = {ValidationGroups.Edit.class})
    private Long id;

    /**
     * 父id
     */
    @ApiModelProperty(value = "父id")
    @NotNull(message = "部门id不能为空", groups = {ValidationGroups.Edit.class, ValidationGroups.Add.class})
    private Long parentId;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称")
    @NotNull(message = "部门id不能为空", groups = {ValidationGroups.Edit.class, ValidationGroups.Add.class})
    private String deptName;

    /**
     * 显示顺序
     */
    @ApiModelProperty(value = "显示顺序")
    @NotNull(message = "部门id不能为空", groups = {ValidationGroups.Edit.class, ValidationGroups.Add.class})
    private Integer orderNo;

    /**
     * 负责人
     */
    @ApiModelProperty(value = "负责人")
    @Size(max = 30,message="名字长度必须在30个字符以内")
    private String leader;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    @Pattern(message = "必须是中国大陆电话号码", regexp = Regular.CHINA_TEL_NO)
    private String phone;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    @Email(message = "必须是邮箱")
    private String email;
}
