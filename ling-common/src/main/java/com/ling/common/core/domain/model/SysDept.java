package com.ling.common.core.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ling.common.core.domain.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 部门父类.
 *
 * @author 钟舒艺
 * @since 2023-01-02 20:39
 **/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysDept extends BaseEntity {
    private static final long serialVersionUID = 3055928702625291382L;

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

    /**
     * 父级列表.
     */
    private String parentList;
}
