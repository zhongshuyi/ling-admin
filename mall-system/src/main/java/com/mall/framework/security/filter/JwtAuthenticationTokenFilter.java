package com.mall.framework.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.common.core.domain.CommonResult;
import com.mall.common.util.ServletUtils;
import com.mall.framework.config.CustomConfig;
import com.mall.framework.model.AdminUserDetails;
import com.mall.framework.util.JwtTokenUtil;
import com.mall.framework.util.RequestUtil;
import com.mall.framework.util.SecurityUtils;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * JWT登录授权过滤器 在用户名和密码校验前添加的过滤器，如果请求中有jwt的token且有效，会取出token中uuid，然后取出redis中的用户信息进行登录操作.
 *
 * @author 钟舒艺
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    /**
     * jwt工具类.
     */
    private final JwtTokenUtil jwtTokenUtil;

    /**
     * 配置信息.
     */
    private final CustomConfig customConfig;

    /**
     * Jackson序列化.
     */
    private final ObjectMapper objectMapper;

    /**
     * 请求校验.
     */
    private final RequestUtil requestUtil;

    @Override
    protected final void doFilterInternal(
            @NonNull final HttpServletRequest request,
            @NonNull final HttpServletResponse response,
            @NonNull final FilterChain filterChain
    ) throws ServletException, IOException {

        if (requestUtil.checkIgnores(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 检查地址是否存在
        if (!requestUtil.checkRequest(request, response)) {
            return;
        }

        // 从token中获取用户信息
        final AdminUserDetails user = jwtTokenUtil.getAdminUserDetails(request);
        if (user != null && SecurityUtils.getAuthentication() == null) {
            log.info("使用用户信息与用户权限构建 认证用户");
            jwtTokenUtil.refreshToken(user);
            // 使用用户信息与用户权限构建 认证用户
            final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user, null, user.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            log.info("jwt验证成功 user:{}", user.getUsername());
            // 放入安全上下文中,就相当于登录了
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } else {
            ServletUtils.renderString(response, objectMapper.writeValueAsString(CommonResult.unauthorized()));
        }
    }
}
