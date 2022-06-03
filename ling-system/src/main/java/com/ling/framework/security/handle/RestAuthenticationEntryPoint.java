package com.ling.framework.security.handle;

import cn.hutool.core.text.CharSequenceUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ling.common.core.domain.CommonResult;
import com.ling.common.util.ServletUtils;
import com.ling.common.util.ip.IpUtils;
import java.io.IOException;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * 当未登录或者token失效访问接口时，自定义的返回结果.
 *
 * @author 钟舒艺
 **/

@Slf4j
@Component
@RequiredArgsConstructor
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -8970718410437077606L;

    /**
     * Jackson序列化.
     */
    private final ObjectMapper objectMapper;

    @Override
    public final void commence(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final AuthenticationException e
    )
            throws IOException {
        final String msg = CharSequenceUtil.format("请求访问：{},未登录或token已过期", request.getRequestURI());
        ServletUtils.renderString(
                response,
                objectMapper.writeValueAsString(CommonResult.unauthorized(msg))
        );
        log.warn("未登录或已过期 : " + "ip: "
                + IpUtils.getIpAdder(request)
                + "    请求地址: " + request.getRequestURI());
    }
}
