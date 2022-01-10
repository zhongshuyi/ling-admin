package com.mall.framework.security.service;

import cn.hutool.http.HttpStatus;
import com.mall.common.exception.BusinessErrorException;
import com.mall.framework.model.AdminUserDetails;
import com.mall.framework.util.JwtTokenUtil;
import javax.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * 登录服务层.
 *
 * @author 钟舒艺
 **/
@Service
@RequiredArgsConstructor
public class LoginService {
    /**
     * jwt工具类.
     */
    private final JwtTokenUtil jwtTokenUtil;

    /**
     * 认证管理器.
     */
    @Resource
    private AuthenticationManager authenticationManager;

    /**
     * 登录验证.
     *
     * @param username 用户名
     * @param password 密码
     * @return token
     */
    public String login(final String username, final String password) {

        // 用户验证
        final Authentication authentication;

        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (final BadCredentialsException e) {
            throw new BusinessErrorException(HttpStatus.HTTP_FORBIDDEN, "用户名或密码错误", e);
        } catch (final Exception e) {
            throw new BusinessErrorException(e);
        }
        final AdminUserDetails adminUserDetails = (AdminUserDetails) authentication.getPrincipal();

        return jwtTokenUtil.generateToken(adminUserDetails);
    }
}
