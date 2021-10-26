package com.mall.common.enums;

import lombok.Getter;

/**
 * 业务异常提示信息枚举类
 *
 * @author 钟舒艺
 * @date 2021-07-06-11:19
 **/
@Getter
public enum BusinessMsgEnum {

    /**
     * 系统异常
     */
    UNEXPECTED_EXCEPTION(500, "系统发生异常，请联系管理员！"),
    /**
     * 用户名或密码错误
     */
    USER_PASSWORD_NOT_MATCH(403, "用户名或密码错误"),
    /**
     * 账户已被删除
     */
    ACCOUNT_DELETED(401, "账户已被删除"),
    /**
     * 账户已被禁用
     */
    ACCOUNT_DISABLE(401, "账户已被禁用"),
    /**
     * 部门被禁用
     */
    DEPT_DISABLE(401, "部门已被禁用");

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
