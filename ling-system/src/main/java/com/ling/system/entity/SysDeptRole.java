package com.ling.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ling.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 部门角色.
 *
 * @author 钟舒艺
 * @date 2022-04-24
 */
@Data
@TableName(value = "sys_dept_role")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysDeptRole extends BaseEntity {

    private static final long serialVersionUID = -6421801012286290409L;

    /**
     * 主键id.
     */
    private Long id;

    /**
     * 部门id.
     */
    private Long deptId;

    /**
     * 部门角色名.
     */
    private String roleName;

    /**
     * 部门角色键名.
     */
    private String roleKey;

    /**
     * 备注.
     */
    private String remark;
}
