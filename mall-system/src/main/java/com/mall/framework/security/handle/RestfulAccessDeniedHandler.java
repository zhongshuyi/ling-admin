package com.mall.framework.security.handle;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.common.core.domain.CommonResult;
import com.mall.common.util.ServletUtils;
import com.mall.common.util.ip.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * 认证失败处理类 返回未授权.
 *
 * @author 钟舒艺
 * @date 2021-07-02-17:20
 **/
@Component
@Slf4j
public class RestfulAccessDeniedHandler implements AccessDeniedHandler, Serializable {

    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException e)
            throws IOException {
        String msg = StrUtil.format("请求访问：{},认证失败,你没有权限", request.getRequestURI());
        ServletUtils.renderString(response,
                new ObjectMapper().writeValueAsString(CommonResult.forbidden(msg)));
        log.warn("没有权限 : "
                + "ip: " + IpUtils.getIpAdder(request)
                + "   请求地址: " + request.getRequestURI());
    }
}
