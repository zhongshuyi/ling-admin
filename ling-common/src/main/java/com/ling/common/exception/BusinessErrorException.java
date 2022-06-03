package com.ling.common.exception;

import cn.hutool.http.HttpStatus;
import com.ling.common.enums.BusinessExceptionMsgEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义业务异常.
 *
 * @author 钟舒艺
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@SuppressWarnings("unused")
@Slf4j
public class BusinessErrorException extends RuntimeException {
    private static final long serialVersionUID = -7480022450501760611L;

    /**
     * 异常码.
     */
    private final int code;

    /**
     * 异常提示信息.
     */
    private final String message;

    /**
     * 异常信息.
     */
    private final Exception exception;

    /**
     * 自定义业务异常.
     *
     * @param businessExceptionMsgEnum 异常枚举
     */
    public BusinessErrorException(final BusinessExceptionMsgEnum businessExceptionMsgEnum) {
        this.code = businessExceptionMsgEnum.getCode();
        this.message = businessExceptionMsgEnum.getMsg();
        this.exception = null;
    }

    /**
     * 自定义业务异常.
     *
     * @param message 异常信息.
     */
    public BusinessErrorException(final String message) {
        this.message = message;
        this.code = HttpStatus.HTTP_INTERNAL_ERROR;
        this.exception = null;
    }

    /**
     * 传入本身异常信息.
     *
     * @param e 异常
     */
    public BusinessErrorException(final Exception e) {
        this.code = HttpStatus.HTTP_INTERNAL_ERROR;
        this.message = "系统异常";
        this.exception = e;
    }

    /**
     * 传入本身异常信息,自定义提示.
     *
     * @param message 提示信息
     * @param e       异常
     */
    public BusinessErrorException(
            final String message,
            final Exception e
    ) {
        this.code = HttpStatus.HTTP_INTERNAL_ERROR;
        this.message = message;
        this.exception = e;
    }


    /**
     * 初始化全部参数.
     *
     * @param code    自定义状态码
     * @param message 提示信息
     * @param e       异常
     */
    public BusinessErrorException(
            final int code,
            final String message,
            final Exception e
    ) {
        this.code = code;
        this.message = message;
        this.exception = e;
    }


    /**
     * 业务异常构造方法.
     *
     * @param code    状态码.
     * @param message 异常信息.
     */
    public BusinessErrorException(
            final int code,
            final String message
    ) {
        this.message = message;
        this.code = code;
        this.exception = null;
    }
}
