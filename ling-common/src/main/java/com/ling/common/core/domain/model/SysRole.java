package com.ling.common.core.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ling.common.core.domain.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 角色父类.
 *
 * @author 钟舒艺
 * @since 2023-01-08 22:45
 **/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysRole extends BaseEntity {
    private static final long serialVersionUID = 4212335086424528056L;

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
     * 角色状态（1正常 0停用）.
     */
    private Byte status;

    /**
     * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）.
     */
    private Integer dataScope;


}
