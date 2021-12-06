package com.mall.dome.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 测试表.
 *
 * @author 钟舒艺
 */
@Data
public class TestDome implements Serializable {

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
     * 创建者.
     */
    private String createBy;

    /**
     * 创建时间.
     */
    private Date createTime;

    /**
     * 修改者.
     */
    private String updateBy;

    /**
     * 修改时间.
     */
    private Date updateTime;


}