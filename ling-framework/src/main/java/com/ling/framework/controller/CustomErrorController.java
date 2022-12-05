package com.ling.framework.controller;

import com.ling.common.core.domain.CommonResult;
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
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomErrorController implements ErrorController {

    @RequestMapping
    public CommonResult<Void> doHandleError() {
        return CommonResult.failed(HttpStatus.NOT_FOUND.value(), "路径不存在");
    }
}
