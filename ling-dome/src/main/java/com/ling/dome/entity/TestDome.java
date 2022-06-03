package com.ling.dome.entity;

import com.ling.common.core.domain.BaseEntity;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 测试表.
 *
 * @author 钟舒艺
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestDome extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id.
     */
    private Long id;

    /**
     * 学生学号.
     */
    private String studentNo;

    /**
     * 学生姓名.
     */
    private String name;

    /**
     * 性别.
     */
    private Integer sex;

    /**
     * 电话号码.
     */
    private String tel;

    /**
     * 班级.
     */
    private String classNo;

    /**
     * 部门id.
     */
    private Long deptId;

    /**
     * 用户id.
     */
    private Long userId;

}
