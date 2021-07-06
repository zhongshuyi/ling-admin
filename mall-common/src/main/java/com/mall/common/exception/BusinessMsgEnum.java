package com.mall.common.exception;

import lombok.Getter;

/**
 * 业务异常提示信息枚举类
 * @author 钟舒艺
 * @date 2021-07-06-11:19
 **/
@Getter
public enum BusinessMsgEnum {

    /** 500 : 系统其他异常 */
    UNEXPECTED_EXCEPTION(500, "系统发生异常，请联系管理员！");

    /**
     * 消息码
     */
    private final int code;
    /**
     * 消息内容
     */
    private final String msg;

     BusinessMsgEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
