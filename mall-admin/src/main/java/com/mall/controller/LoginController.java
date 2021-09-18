package com.mall.controller;

import com.mall.common.core.domain.CommonResult;
import com.mall.common.core.util.ServletUtils;
import com.mall.framework.model.AdminUserDetails;
import com.mall.framework.model.LoginBody;
import com.mall.framework.security.service.LoginService;
import com.mall.framework.util.JwtTokenUtil;
import com.mall.service.IUmsMenuService;
import com.mall.util.MenuUtil;
import com.mall.vo.RouterVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 钟舒艺
 * @date 2021-07-07-21:09
 **/
@RestController
@Api(tags = "登录注册等")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LoginController {

    private final LoginService loginService;

    private final JwtTokenUtil jwtTokenUtil;

    private final IUmsMenuService umsMenuService;

    /**
     * 登录接口
     *
     * @param user 登录用户接收类
     * @return token
     */
    @PostMapping("/login")
    @ApiOperation("登录接口")
    public CommonResult<? extends Map<String, String>> login(@RequestBody LoginBody user) {
        String token = loginService.login(user.getUsername(), user.getPassword());
        Map<String, String> map = new HashMap<>(1);
        map.put("token", token);
        return CommonResult.success(map);
    }

    /**
     * 获取用户信息接口,获取用户的权限,角色信息,以及基本信息
     *
     * @return 信息集合
     */
    @GetMapping("getInfo")
    @ApiOperation("获取用户信息")
    public CommonResult<Map<String, Object>> getInfo() {
        AdminUserDetails adminUserDetails = jwtTokenUtil.getAdminUserDetails(ServletUtils.getRequest());
        Map<String, Object> map = new HashMap<>(3);
        map.put("user", adminUserDetails.getUmsAdmin());
        map.put("roles", adminUserDetails.getRoleKey());
        map.put("permissions", adminUserDetails.getPermissionList());
        return CommonResult.success(map);
    }

    @GetMapping("getMenuList")
    @ApiOperation("获取菜单列表")
    public CommonResult<List<RouterVo>> getMenuList() {
        AdminUserDetails adminUserDetails = jwtTokenUtil.getAdminUserDetails(ServletUtils.getRequest());
        if (adminUserDetails.getUmsAdmin().getUserId() == 1L) {
            return CommonResult.success(MenuUtil.buildMenus(umsMenuService.selectMenuListAll(), 0L));
        } else {
            return CommonResult.success(MenuUtil.buildMenus(umsMenuService.selectMenuListByUserId(adminUserDetails.getUmsAdmin().getUserId()), 0L));
        }
    }
}
