package com.mall.framework.exception;

import com.mall.common.core.domain.CommonResult;
import com.mall.common.exception.BusinessErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * @author 钟舒艺
 * @date 2021-07-06-10:52
 **/
@Slf4j
@ResponseBody
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 缺少请求参数异常
     * @param ex HttpMessageNotReadableException
     * @return 通用返回类
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public CommonResult handleHttpMessageNotReadableException(
            MissingServletRequestParameterException ex) {
        log.error("缺少请求参数，{}", ex.getMessage());
        return  CommonResult.failed(400, "缺少必要的请求参数");
    }

    /**
     * 空指针异常
     * @param ex NullPointerException
     * @return 通用返回类
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult handleTypeMismatchException(NullPointerException ex) {
        log.error("空指针异常，{}", ex.getMessage());
        return  CommonResult.failed(500, "空指针异常了");
    }

    /**
     * 拦截业务异常,返回业务异常信息
     * @param ex 异常
     * @return 通用返回
     */
    @ExceptionHandler(BusinessErrorException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult handleBusinessError(BusinessErrorException ex) {
        return CommonResult.failed(ex.getCode(), ex.getMessage());
    }

}
