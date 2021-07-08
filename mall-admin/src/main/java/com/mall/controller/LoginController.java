package com.mall.controller;

import com.mall.common.core.domain.CommonResult;
import com.mall.framework.model.AdminUserDetails;
import com.mall.framework.model.LoginBody;
import com.mall.framework.security.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 钟舒艺
 * @date 2021-07-07-21:09
 **/
@RestController
@Api(tags = "登录注册等")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    @ApiOperation("登录接口")
    public CommonResult login(@RequestBody LoginBody user){
        String token = loginService.login(user.getUsername(),user.getPassword());
        Map<String, String> map = new HashMap<>(1);
        map.put("token",token);
        return CommonResult.success(map);
    }
}
