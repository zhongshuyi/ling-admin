package com.mall.common.core.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.mall.common.core.domain.BaseEntity;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 部门表.
 *
 * @author 钟舒艺
 * @since 2021-10-08
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class UmsDept extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 部门id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父id.
     */
    private Long parentId;

    /**
     * 部门名称.
     */
    private String deptName;

    /**
     * 显示顺序.
     */
    private Integer orderNo;

    /**
     * 负责人.
     */
    private String leader;

    /**
     * 联系电话.
     */
    private String phone;

    /**
     * 邮箱.
     */
    private String email;
}