package com.ling.common.enums;

import lombok.Getter;

/**
 * 菜单类型.
 *
 * @author 钟舒艺
 * @since 2022-05-11-18:05
 **/
@Getter
public enum MenuType {
    /**
     * 目录.
     */
    DIRECTORY((byte) 0, "目录"),
    /**
     * 菜单.
     */
    MENU((byte) 1, "菜单"),
    /**
     * 菜单.
     */
    BUTTON((byte) 2, "按钮,权限");


    /**
     * 代码.
     */
    private final Byte code;
    /**
     * 备注.
     */
    private final String remark;

    MenuType(
            final Byte code,
            final String remark) {
        this.code = code;
        this.remark = remark;
    }
}
