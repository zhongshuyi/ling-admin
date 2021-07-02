package com.mall.framework.security.handle;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.common.core.domain.CommonResult;
import com.mall.common.core.util.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * 认证失败处理类 返回未授权
 *
 * @author 钟舒艺
 * @date 2021-07-02-17:20
 **/
@Component
@Slf4j
public class RestfulAccessDeniedHandler implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws JsonProcessingException {
        String msg = StrUtil.format("请求访问：{},认证失败,你没有权限", request.getRequestURI());
        ServletUtils.renderString(response, new ObjectMapper().writeValueAsString(CommonResult.forbidden(msg)));
        log.warn("没有权限 : " + "ip: " + request.getHeader("x-forwarded-for") + "请求地址: " + request.getRequestURI());
    }
}
