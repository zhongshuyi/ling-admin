package com.ling.dome.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ling.common.core.domain.base.BaseVO;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 测试表 视图类.
 * </p>
 *
 * @author 钟舒艺
 * @since 2023-03-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class TestDomeVO extends BaseVO implements Serializable {

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
