package com.mall.framework.security.service;

import com.mall.common.enums.BusinessMsgEnum;
import com.mall.common.exception.BusinessErrorException;
import com.mall.framework.model.AdminUserDetails;
import com.mall.framework.util.JwtTokenUtil;
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
public class LoginService {

    @Resource
    private AuthenticationManager authenticationManager;


    final
    JwtTokenUtil jwtTokenUtil;

    public LoginService(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

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

        System.out.println(username + " , " + password);

        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                throw new BusinessErrorException(BusinessMsgEnum.USER_PASSWORD_NOT_MATCH);
            } else {
                throw new BusinessErrorException(BusinessMsgEnum.UNEXPECTED_EXCEPTION);
            }
        }
        AdminUserDetails adminUserDetails = (AdminUserDetails) authentication.getPrincipal();

        return jwtTokenUtil.generateToken(adminUserDetails);
    }
}
