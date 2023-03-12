package com.ling.dome.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ling.common.core.domain.base.BaseDTO;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 测试表 操作类.
 * </p>
 *
 * @author 钟舒艺
 * @since 2023-03-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestDomeDTO extends BaseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

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
    private Byte sex;

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
