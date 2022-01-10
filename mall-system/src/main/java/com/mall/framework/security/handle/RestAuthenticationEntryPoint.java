package com.mall.framework.security.handle;

import cn.hutool.core.text.CharSequenceUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.common.core.domain.CommonResult;
import com.mall.common.util.ServletUtils;
import com.mall.common.util.ip.IpUtils;
import java.io.IOException;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * 当未登录或者token失效访问接口时，自定义的返回结果.
 *
 * @author 钟舒艺
 **/
@Component
@Slf4j
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public final void commence(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final AuthenticationException e)
            throws IOException {
        final String msg = CharSequenceUtil.format("请求访问：{},未登录或token已过期", request.getRequestURI());
        ServletUtils.renderString(response,
                new ObjectMapper().writeValueAsString(CommonResult.unauthorized(msg)));
        log.warn("未登录或已过期 : " + "ip: "
                + IpUtils.getIpAdder(request)
                + "    请求地址: " + request.getRequestURI());
    }
}
