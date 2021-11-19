package com.mall.framework.exception;

import com.mall.common.core.domain.CommonResult;
import com.mall.common.exception.BusinessErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;


/**
 * 全局异常处理程序.
 *
 * @author 钟舒艺
 * @date 2021/07/15
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
            MissingServletRequestParameterException ex) {
        log.error("错误" + ex);
        log.error("缺少请求参数，{}", ex.getMessage());
        return CommonResult.failed(400, "缺少必要的请求参数");
    }

    /**
     * 参数验证异常.
     *
     * @param ex MethodArgumentNotValidException
     * @return 通用返回类
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public CommonResult<Void> parameterValidationException(MethodArgumentNotValidException ex) {
        log.error("错误" + ex);
        BindingResult result = ex.getBindingResult();
        StringBuilder errorMsg = new StringBuilder();
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            fieldErrors.forEach(error -> errorMsg.append(error.getDefaultMessage()).append("!"));
        }
        log.error("参数验证异常,{}", ex.getMessage());
        return CommonResult.failed(400, errorMsg.toString());
    }

    /**
     * 空指针异常.
     *
     * @param ex NullPointerException
     * @return 通用返回类
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult<Void> handleTypeMismatchException(NullPointerException ex) {
        log.error("错误" + ex);
        log.error("空指针异常，{}", ex.getMessage());
        return CommonResult.failed(500, "空指针异常了");
    }

    /**
     * 拦截业务异常,返回业务异常信息.
     *
     * @param ex 异常
     * @return 通用返回
     */
    @ExceptionHandler(BusinessErrorException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult<Void> handleBusinessError(BusinessErrorException ex) {
        log.error("错误" + ex);
        return CommonResult.failed(ex.getCode(), ex.getMessage());
    }

}
