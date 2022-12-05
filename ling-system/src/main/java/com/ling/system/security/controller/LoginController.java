package com.ling.system.security.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.ling.common.core.domain.CommonResult;
import com.ling.framework.controller.LoginControllerInterface;
import com.ling.framework.model.LoginBody;
import com.ling.framework.service.LoginService;
import com.ling.system.security.model.LoginUserInfo;
import com.ling.system.service.ISysMenuService;
import com.ling.system.utils.MenuUtil;
import com.ling.system.utils.SecurityUtils;
import com.ling.system.vo.RouterVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录操作.
 *
 * @author 钟舒艺
 * @since 2022-10-28 14:54
 **/
@RestController
@Tag(name = "登录操作")
@RequiredArgsConstructor
public class LoginController implements LoginControllerInterface {

    /**
     * 登录服务.
     */
    private final LoginService loginService;

    /**
     * 菜单服务.
     */
    private final ISysMenuService sysMenuService;

    /**
     * 用户登录.
     *
     * @param loginBody 登录用户接收类
     * @return token
     */
    @Override
    public CommonResult<Map<String, String>> login(
            @RequestBody @Validated final LoginBody loginBody
    ) {
        loginService.login(loginBody);
        return CommonResult.success(
                MapUtil.builder(new HashMap<String, String>(1))
                        .put("token", StpUtil.getTokenValue())
                        .build()
        );
    }


    /**
     * 用户登出.
     *
     * @return 是否成功
     */
    @Override
    public CommonResult<Void> logout() {
        StpUtil.logout();
        return CommonResult.success();
    }


    /**
     * 获取用户信息.
     * <p>获取用户的权限,角色信息,以及基本信息</p>
     *
     * @return 信息集合
     */
    @Override
    public CommonResult<Map<String, Object>> getUserInfo() {

        LoginUserInfo userInfo = SecurityUtils.getLoginUserInfo();
        final Map<String, Object> map = BeanUtil.beanToMap(userInfo.getUser());
        map.put("password", "");
        map.put("roles", userInfo.getRoles());
        map.put("depts", userInfo.getDepts());

        return CommonResult.success(map);
    }

    /**
     * 获取权限键列表.
     *
     * @return 权限键列表
     */
    @Override
    public CommonResult<Set<String>> getPermCode() {
        return CommonResult.success(SecurityUtils.getLoginUserInfo().getPermissionList());
    }

    /**
     * 获取路由.
     *
     * @return 菜单列表
     */
    @GetMapping("/getRouterList")
    public CommonResult<List<RouterVO>> getMenuList() {
        if (Boolean.TRUE.equals(SecurityUtils.getLoginUserInfo().getIsAdmin())) {
            return CommonResult.success(MenuUtil.getRouter(sysMenuService.listMenuAll()));
        } else {
            return CommonResult.success(MenuUtil.getRouter(SecurityUtils.getLoginUserInfo().getMenuAndPermissionList()));
        }
    }
}
