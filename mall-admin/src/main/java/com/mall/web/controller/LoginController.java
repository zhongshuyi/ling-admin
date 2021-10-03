package com.mall.web.controller;

import cn.hutool.core.bean.BeanUtil;
import com.mall.common.core.domain.CommonResult;
import com.mall.common.core.util.ServletUtils;
import com.mall.framework.model.AdminUserDetails;
import com.mall.framework.model.LoginBody;
import com.mall.framework.security.service.LoginService;
import com.mall.framework.util.JwtTokenUtil;
import com.mall.system.bo.query.MenuQueryBo;
import com.mall.system.service.IUmsMenuService;
import com.mall.system.util.MenuUtil;
import com.mall.system.vo.RouterVo;
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
import java.util.Set;

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
    @GetMapping("/getUserInfo")
    @ApiOperation("获取用户信息")
    public CommonResult<Map<String, Object>> getInfo() {
        AdminUserDetails adminUserDetails = jwtTokenUtil.getAdminUserDetails(ServletUtils.getRequest());
        Map<String, Object> map = BeanUtil.beanToMap(adminUserDetails.getUmsAdmin());
        map.put("roles", adminUserDetails.getRoleKey());
        return CommonResult.success(map);
    }

    @GetMapping("/getPermCode")
    @ApiOperation("获取权限列表")
    public CommonResult<Set<String>> getPermCode() {
        AdminUserDetails adminUserDetails = jwtTokenUtil.getAdminUserDetails(ServletUtils.getRequest());
        return CommonResult.success(adminUserDetails.getPermissionList());

    }

    /**
     * 获取菜单
     *
     * @return 菜单列表
     */
    @GetMapping("/getRouterList")
    @ApiOperation("获取路由")
    public CommonResult<List<RouterVo>> getMenuList() {
        AdminUserDetails adminUserDetails = jwtTokenUtil.getAdminUserDetails(ServletUtils.getRequest());
        if (adminUserDetails.getUmsAdmin().getUserId() == 1L) {
            return CommonResult.success(MenuUtil.getRouter(umsMenuService.selectRouterListAll()));
        } else {
            return CommonResult.success(MenuUtil.getRouter(umsMenuService.selectMenuListByUserId(adminUserDetails.getUmsAdmin().getUserId())));
        }
    }


}
