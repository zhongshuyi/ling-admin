package com.mall.framework.security.filter;

import com.mall.framework.model.AdminUserDetails;
import com.mall.framework.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT登录授权过滤器 在用户名和密码校验前添加的过滤器，如果请求中有jwt的token且有效，会取出token中uuid，然后取出redis中的用户信息进行登录操作
 * 文档记录 : https://www.yuque.com/rookieteam/bvc9h3/gbvtga#PAnLH
 *
 * @author 钟舒艺
 * @date 2021-07-04-12:42
 **/
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 从token中获取用户信息
        AdminUserDetails user = jwtTokenUtil.getAdminUserDetails(request);
        // SecurityContextHolder.getContext().getAuthentication()获取当前用户权限
        if (user!=null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // 使用用户信息与用户权限构建 认证用户
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                log.info("jwt验证成功 user:{}", user.getUsername());
                // 放入安全上下文中,就相当于登录了
                SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request,response);
}
}
