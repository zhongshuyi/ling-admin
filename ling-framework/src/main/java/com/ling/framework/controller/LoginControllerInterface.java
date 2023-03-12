package com.ling.framework.controller;

import com.ling.common.core.domain.CommonResult;
import com.ling.common.core.domain.model.LoginBody;
import java.util.Map;
import java.util.Set;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 登录与信息.
 *
 * @author 钟舒艺
 * @since 2022-10-24 12:24
 **/
public interface LoginControllerInterface {


    /**
     * 用户登录.
     *
     * @param loginBody 登录用户接收类
     * @return token
     */
    @PostMapping("/login")
    CommonResult<Map<String, String>> login(
            @RequestBody @Validated LoginBody loginBody
    );

    /**
     * 用户登出.
     *
     * @return 是否成功
     */
    @GetMapping("/logout")
    CommonResult<Void> logout();


    /**
     * 获取用户信息接口.
     * <p>获取用户的权限,角色信息,以及基本信息</p>
     *
     * @return 信息集合.
     */
    @GetMapping("/getUserInfo")
    CommonResult<Map<String, Object>> getUserInfo();

    /**
     * 获取权限键列表.
     *
     * @return 权限键列表
     */
    @GetMapping("/getPermCode")
    CommonResult<Set<String>> getPermCode();


}

