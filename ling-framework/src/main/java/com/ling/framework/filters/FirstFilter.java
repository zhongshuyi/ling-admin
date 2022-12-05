package com.ling.framework.filters;

import cn.hutool.extra.servlet.ServletUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 首个过滤器.
 *
 * @author 钟舒艺
 * @since 2022-10-28 20:00
 **/
@Slf4j
@Component
@Order(-1000)
@RequiredArgsConstructor
public class FirstFilter extends OncePerRequestFilter {

    /**
     * Jackson序列化.
     */
    private final ObjectMapper objectMapper;

    @Value("${server.error.path:${error.path:/error}}")
    private String errorPath;


    /**
     * 过滤器实现.
     *
     * @param request     请求
     * @param response    响应
     * @param filterChain 过滤器链
     * @throws ServletException 异常
     * @throws IOException      IO异常
     */
    @Override
    protected final void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain) throws ServletException, IOException {
        log.info("IP:{} 访问：\"{}\"", ServletUtil.getClientIP(request), request.getRequestURI());
        filterChain.doFilter(request, response);

    }
}
