package com.mall.framework.security.service;

import com.mall.common.exception.BusinessErrorException;
import com.mall.framework.model.AdminUserDetails;
import com.mall.framework.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 钟舒艺
 * @date 2021-07-07-17:23
 **/
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LoginService {

    @Resource
    private AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;


    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @return token
     */
    public String login(String username, String password) {

        // 用户验证
        Authentication authentication;

        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                throw new BusinessErrorException(403, "用户名或密码错误", e);
            } else {
                throw new BusinessErrorException(e);
            }
        }
        AdminUserDetails adminUserDetails = (AdminUserDetails) authentication.getPrincipal();

        return jwtTokenUtil.generateToken(adminUserDetails);
    }
}
