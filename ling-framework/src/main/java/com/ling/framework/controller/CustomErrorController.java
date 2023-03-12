package com.ling.framework.controller;

import com.ling.common.core.domain.CommonResult;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自定义错误处理.
 *
 * @author 钟舒艺
 * @since 2022-10-23 14:35
 **/
@Slf4j
@RestController
public class CustomErrorController implements ErrorController {

    /**
     * 自定义 error 处理.
     *
     * @param request 请求信息
     * @return 通用返回
     */
    @RequestMapping("/error")
    public CommonResult<Void> doHandleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            log.error(String.valueOf(statusCode));
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return CommonResult.failed(statusCode, "路径不存在");
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return CommonResult.failed(statusCode, "服务器错误");
            } else {
                return CommonResult.failed(statusCode, HttpStatus.valueOf(statusCode).getReasonPhrase());
            }
        }
        return CommonResult.failed(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器错误");
    }
}
