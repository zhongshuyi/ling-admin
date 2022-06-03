package com.ling.dome.dto;

import com.ling.common.constant.Regular;
import com.ling.common.core.validate.ValidationGroups;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 * 数据示例传输对象.
 *
 * @author 钟舒艺
 * @date 2022-05-16-19:59
 **/
@Data
@ApiModel(value = "数据示例传输对象")
public class TestDomeDTO implements Serializable {

    private static final long serialVersionUID = 5592996817367705586L;

    /**
     * 主键id.
     */
    @ApiModelProperty(value = "id")
    @NotNull(message = "主键不能为空", groups = {ValidationGroups.Edit.class})
    @Min(value = 0, message = "id最低为0", groups = {ValidationGroups.Edit.class})
    private Long id;

    /**
     * 学生学号.
     */
    @ApiModelProperty(value = "学生学号")
    private String studentNo;

    /**
     * 学生姓名.
     */
    @ApiModelProperty(value = "学生姓名")
    @NotNull(message = "学生姓名", groups = {ValidationGroups.Edit.class, ValidationGroups.Add.class})
    private String name;

    /**
     * 性别.
     */
    @ApiModelProperty(value = "性别")
    @Range(min = 0, max = 1, message = "性别只能为1或0", groups = {ValidationGroups.Edit.class, ValidationGroups.Add.class})
    private Integer sex;

    /**
     * 电话号码.
     */
    @ApiModelProperty(value = "电话号码")
    @Pattern(
            message = "必须是中国大陆电话号码",
            regexp = Regular.CHINA_TEL_NO,
            groups = {ValidationGroups.Add.class, ValidationGroups.Edit.class})
    private String tel;

    /**
     * 班级.
     */
    @ApiModelProperty(value = "班级")
    private String classNo;

    /**
     * 数据所属部门.
     */
    @ApiModelProperty(value = "数据所属部门")
    private Long deptId;
}
