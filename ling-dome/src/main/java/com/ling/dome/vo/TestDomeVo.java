package com.ling.dome.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 测试视图层.
 *
 * @author 钟舒艺
 **/
@Data
public class TestDomeVo {
    private static final long serialVersionUID = 5592996817367705586L;

    /**
     * 主键id.
     */
    @ApiModelProperty(value = "id")
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
    private String name;

    /**
     * 性别.
     */
    @ApiModelProperty(value = "性别")
    private Integer sex;

    /**
     * 电话号码.
     */
    @ApiModelProperty(value = "电话号码")
    private String tel;

    /**
     * 班级.
     */
    @ApiModelProperty(value = "班级")
    private String classNo;

    /**
     * 数据所属id.
     */
    @ApiModelProperty(value = "数据所属部门id")
    private Long deptId;

}
