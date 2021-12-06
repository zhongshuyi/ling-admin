package com.mall.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据权限枚举.
 *
 * @author 钟舒艺
 **/
@Getter
@AllArgsConstructor
public enum DataScopeTypeEnum {

    /**
     * 全部数据权限.
     */
    ALL(1, "全部数据权限"),
    /**
     * 自定数据权限.
     */
    CUSTOM(2, "自定数据权限"),
    /**
     * 本部门数据权限.
     */
    DEPT(3, "本部门数据权限"),
    /**
     * 本部门及下级部门数据权限.
     */
    DEPT_AND_SUB(4, "本部门及下级部门数据权限"),
    /**
     * 仅本人数据权限.
     */
    ME(5, "仅本人数据权限");
    /**
     * 代码.
     */
    private final Integer code;
    /**
     * 备注.
     */
    private final String remark;
}
