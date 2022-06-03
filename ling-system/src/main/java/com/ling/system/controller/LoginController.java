package com.ling.system.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.map.MapUtil;
import com.ling.common.annotation.LoginAuth;
import com.ling.common.core.controller.BaseController;
import com.ling.common.core.domain.CommonResult;
import com.ling.common.enums.BusinessExceptionMsgEnum;
import com.ling.common.exception.BusinessErrorException;
import com.ling.framework.config.CustomConfig;
import com.ling.framework.model.AdminUserDetails;
import com.ling.framework.model.LoginBody;
import com.ling.framework.security.service.LoginService;
import com.ling.framework.util.SecurityUtils;
import com.ling.system.entity.SysMenu;
import com.ling.system.service.ISysMenuService;
import com.ling.system.util.MenuUtil;
import com.ling.system.vo.RouterVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录操作.
 *
 * @author 钟舒艺
 **/
@Slf4j
@LoginAuth
@RestController
@Api(tags = "登录注册等")
@RequiredArgsConstructor
public class LoginController extends BaseController {

    /**
     * 登录服务.
     */
    private final LoginService loginService;

    /**
     * 权限服务.
     */
    private final ISysMenuService sysMenuService;

    /**
     * 自定义配置信息.
     */
    private final CustomConfig config;

    /**
     * 登录接口.
     *
     * @param user    登录用户接收类
     * @param request 请求信息
     * @return token
     */
    @PostMapping("/login")
    @ApiOperation("登录接口")
    public CommonResult<Map<String, String>> login(
            @RequestBody final LoginBody user,
            final HttpServletRequest request
    ) {
        final String token = loginService.login(user.getUsername(), user.getPassword(), request);
        return CommonResult.success(MapUtil.builder(new HashMap<String, String>(1)).put("token", token).build());
    }


    /**
     * 获取用户信息接口,获取用户的权限,角色信息,以及基本信息.
     *
     * @return 信息集合
     */
    @GetMapping("/getUserInfo")
    @ApiOperation("获取用户信息")

    public CommonResult<Map<String, Object>> getInfo() {
        final AdminUserDetails adminUserDetails = SecurityUtils.getLoginUser();
        if (adminUserDetails == null) {
            throw new BusinessErrorException(BusinessExceptionMsgEnum.USER_IS_NOT_LOGIN);
        }
        assert adminUserDetails.getSysAdmin() != null;
        final Map<String, Object> map = BeanUtil.beanToMap(adminUserDetails.getSysAdmin());
        map.put("password", "");
        map.put("roles", adminUserDetails.getRoleKey());
        map.put("depts", adminUserDetails.getDepts());
        return CommonResult.success(map);
    }

    /**
     * 获取权限键列表.
     *
     * @return 权限键列表
     */
    @GetMapping("/getPermCode")
    @ApiOperation("获取权限列表")
    public CommonResult<Collection<? extends GrantedAuthority>> getPermCode() {
        final AdminUserDetails adminUserDetails = SecurityUtils.getLoginUser();
        if (adminUserDetails == null) {
            throw new BusinessErrorException(BusinessExceptionMsgEnum.USER_IS_NOT_LOGIN);
        }
        return CommonResult.success(adminUserDetails.getAuthorities());
    }

    /**
     * 获取路由.
     *
     * @return 菜单列表
     */
    @GetMapping("/getRouterList")
    @ApiOperation("获取路由")
    public CommonResult<List<RouterVo>> getMenuList() {
        final AdminUserDetails adminUserDetails = SecurityUtils.getLoginUser();
        if (adminUserDetails == null) {
            throw new BusinessErrorException(BusinessExceptionMsgEnum.USER_IS_NOT_LOGIN);
        }
        assert adminUserDetails.getSysAdmin() != null;
        if (Boolean.TRUE.equals(adminUserDetails.getSysAdmin().getIsAdmin())) {
            return CommonResult.success(MenuUtil.getRouter(sysMenuService.selectRouterListAll()));
        } else {
            return CommonResult.success(MenuUtil.getRouter(sysMenuService.selectMenuByIds(
                    CollStreamUtil.toList(adminUserDetails.getPermissionList(), SysMenu::getId))));
        }
    }

    /**
     * 用户登出.
     *
     * @return 是否成功
     */
    @GetMapping("/logout")
    @ApiOperation("登出")
    public CommonResult<Void> logout() {
        return toAjax(loginService.logout());
    }
}
