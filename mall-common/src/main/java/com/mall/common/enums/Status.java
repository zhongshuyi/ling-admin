package com.mall.common.enums;

import lombok.Getter;

/**
 * 系统用户状态.
 *
 * @author 钟舒艺
 **/
@Getter
public enum Status {

    /**
     * 正常.
     */
    OK(0, "正常"),
    /**
     * 停用.
     */
    DISABLE(1, "停用"),
    /**
     * 删除.
     */
    DELETED(2, "删除");

    /**
     * 状态码.
     */
    private final Integer code;

    /**
     * 描述.
     */
    private final String info;

    Status(
            final Integer code,
            final String info
    ) {
        this.code = code;
        this.info = info;
    }
}
