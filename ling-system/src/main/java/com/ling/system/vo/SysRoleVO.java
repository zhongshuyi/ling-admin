package com.ling.system.vo;

import com.ling.common.core.domain.base.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色信息视图.
 *
 * @author 钟舒艺
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRoleVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id.
     */
    private Long id;

    /**
     * 角色名称.
     */
    private String roleName;

    /**
     * 角色权限字符串.
     */
    private String roleKey;

    /**
     * 显示顺序.
     */
    private Integer orderNo;

    /**
     * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）.
     */
    private Integer dataScope;


    /**
     * 角色状态（0正常 1停用）.
     */
    private Integer status;

    /**
     * 备注.
     */
    private String remark;

}
