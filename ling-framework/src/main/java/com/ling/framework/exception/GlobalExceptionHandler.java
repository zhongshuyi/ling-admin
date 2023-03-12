package com.ling.framework.exception;

import com.ling.common.core.domain.CommonResult;
import com.ling.common.exception.BusinessErrorException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 全局异常处理程序.
 *
 * @author 钟舒艺
 */
@Slf4j
@ResponseBody
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 缺少请求参数异常.
     *
     * @param ex HttpMessageNotReadableException
     * @return 通用返回类
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public CommonResult<Void> handleHttpMessageNotReadableException(
            final MissingServletRequestParameterException ex) {
        log.error(ex.getMessage(), ex);
        log.error("缺少请求参数，{}", ex.getMessage());
        return CommonResult.failed(cn.hutool.http.HttpStatus.HTTP_BAD_REQUEST, "缺少必要的请求参数");
    }

    /**
     * 请求方法不支持异常.
     *
     * @param ex HttpRequestMethodNotSupportedException
     * @return 通用返回类
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public CommonResult<Void> handleHttpRequestMethodNotSupportedException(
            final HttpRequestMethodNotSupportedException ex) {
        log.error(ex.getMessage(), ex);
        return CommonResult.failed(cn.hutool.http.HttpStatus.HTTP_BAD_METHOD, "请求方法不支持");
    }

    /**
     * 参数验证异常.
     *
     * @param ex MethodArgumentNotValidException
     * @return 通用返回类
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public CommonResult<Void> parameterValidationException(
            final MethodArgumentNotValidException ex
    ) {
        log.error(ex.getMessage(), ex);
        final BindingResult result = ex.getBindingResult();
        final StringBuilder errorMsg = new StringBuilder();
        if (result.hasErrors()) {
            final List<FieldError> fieldErrors = result.getFieldErrors();
            fieldErrors.forEach(error -> errorMsg.append(error.getDefaultMessage()).append("! ;"));
        }
        log.error("参数验证异常,{}", ex.getMessage());
        return CommonResult.failed(cn.hutool.http.HttpStatus.HTTP_BAD_REQUEST, errorMsg.toString());
    }

    /**
     * 空指针异常.
     *
     * @param ex NullPointerException
     * @return 通用返回类
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult<Void> handleTypeMismatchException(final NullPointerException ex) {
        log.error(ex.getMessage(), ex);
        log.error("空指针异常，{}", ex.getMessage());
        return CommonResult.failed(cn.hutool.http.HttpStatus.HTTP_INTERNAL_ERROR, "服务器错误");
    }

    /**
     * 拦截业务异常,返回业务异常信息.
     *
     * @param ex 异常
     * @return 通用返回
     */
    @ExceptionHandler(BusinessErrorException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public CommonResult<Void> handleBusinessError(final BusinessErrorException ex) {
        log.error(ex.getMessage(), ex.getException());
        return CommonResult.failed(ex.getCode(), ex.getMessage());
    }

    /**
     * 拦截业务异常,返回业务异常信息.
     *
     * @param ex 异常
     * @return 通用返回
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.OK)
    public CommonResult<Void> Error(final Exception ex) {
        log.error(ex.getMessage(), ex);
        return CommonResult.failed("服务器错误");
    }
}
