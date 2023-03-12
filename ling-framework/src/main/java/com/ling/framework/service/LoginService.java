package com.ling.framework.service;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.http.HttpStatus;
import com.ling.common.constant.AppConstants;
import com.ling.common.core.domain.model.LoginBody;
import com.ling.common.core.domain.model.User;
import com.ling.common.exception.BusinessErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * new class.
 *
 * @author 钟舒艺
 * @since 2022-10-26 19:51
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {
    /**
     * 用户服务.
     */
    private final UserService userService;


    /**
     * 登录操作.
     *
     * @param loginBody 用户名与密码信息.
     */
    public void login(LoginBody loginBody) {

        User user = userService.getUserByUsername(loginBody.getUsername());
        // 用户名与密码校验
        if (user == null || !BCrypt.checkpw(loginBody.getPassword(), user.getPassword())) {
            throw new BusinessErrorException(HttpStatus.HTTP_UNAUTHORIZED, "用户名或密码错误");
        }
        if (user.getIsDeleted().equals(AppConstants.DELETE)) {
            throw new BusinessErrorException(HttpStatus.HTTP_UNAUTHORIZED, "用户已删除");
        }
        if (user.getStatus().equals(AppConstants.DISABLE)) {
            throw new BusinessErrorException(HttpStatus.HTTP_UNAUTHORIZED, "用户已禁用");
        }

        // 登录操作
        StpUtil.login(user.getId());
        // 登录后需要执行的操作
        userService.afterLogin(user);
    }


}
