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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * 认证失败处理类 返回未授权.
 *
 * @author 钟舒艺
 **/
@Component
@Slf4j
public class RestfulAccessDeniedHandler implements AccessDeniedHandler, Serializable {

    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public final void handle(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final AccessDeniedException e
    )
            throws IOException {
        final String msg = CharSequenceUtil.format("请求访问：{},认证失败,你没有权限", request.getRequestURI());
        ServletUtils.renderString(
                response,
                new ObjectMapper().writeValueAsString(CommonResult.forbidden(msg))
        );
        log.warn("没有权限 : "
                + "ip: " + IpUtils.getIpAdder(request)
                + "   请求地址: " + request.getRequestURI());
    }
}
