package com.mall.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义业务异常
 * @author 钟舒艺
 * @date 2021-07-06-11:27
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessErrorException extends RuntimeException{
    private static final long serialVersionUID = -7480022450501760611L;

    /**
     * 异常码
     */
    private int code;

    /**
     * 异常提示信息
     */
    private String message;

    public BusinessErrorException(BusinessMsgEnum businessMsgEnum) {
        this.code = businessMsgEnum.getCode();
        this.message = businessMsgEnum.getMsg();
    }
}
