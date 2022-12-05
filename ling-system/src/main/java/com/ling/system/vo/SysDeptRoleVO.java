package com.ling.system.vo;

import com.ling.common.core.domain.BaseVO;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门角色.
 *
 * @author 钟舒艺
 * @since 2022-04-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDeptRoleVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 部门角色id.
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
     * 角色权限字符串.
     */
    private String roleKey;

    /**
     * 备注.
     */
    private String remark;
}
