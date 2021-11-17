package com.mall.common.enums;

import lombok.Getter;

/**
 * 系统用户状态
 *
 * @author 钟舒艺
 * @date 2021-07-07-17:43
 **/
@Getter
public enum Status {

    /**
     * 正常
     */
    OK(0, "正常"),
    /**
     * 停用
     */
    DISABLE(1, "停用"),
    /**
     * 删除
     */
    DELETED(2, "删除");

    private final Integer code;
    private final String info;

    Status(Integer code, String info) {
        this.code = code;
        this.info = info;
    }
}
