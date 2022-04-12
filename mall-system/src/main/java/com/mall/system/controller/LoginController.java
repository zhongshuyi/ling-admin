package com.mall.system.controller;

import cn.hutool.core.bean.BeanUtil;
import com.mall.common.annotation.LoginAuth;
import com.mall.common.core.domain.CommonResult;
import com.mall.common.enums.BusinessExceptionMsgEnum;
import com.mall.common.exception.BusinessErrorException;
import com.mall.framework.model.AdminUserDetails;
import com.mall.framework.model.LoginBody;
import com.mall.framework.security.service.LoginService;
import com.mall.framework.util.SecurityUtils;
import com.mall.system.entity.UmsMenu;
import com.mall.system.service.IUmsMenuService;
import com.mall.system.util.MenuUtil;
import com.mall.system.vo.RouterVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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
@LoginAuth
@RestController
@Api(tags = "登录注册等")
@RequiredArgsConstructor
public class LoginController {

    /**
     * 登录服务.
     */
    private final LoginService loginService;

    /**
     * 权限服务.
     */
    private final IUmsMenuService umsMenuService;

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
        final Map<String, String> map = new HashMap<>(1);
        map.put("token", token);
        return CommonResult.success(map);
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
        assert adminUserDetails.getUmsAdmin() != null;
        final Map<String, Object> map = BeanUtil.beanToMap(adminUserDetails.getUmsAdmin());
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
    public CommonResult<List<GrantedAuthority>> getPermCode() {
        final AdminUserDetails adminUserDetails = SecurityUtils.getLoginUser();
        if (adminUserDetails == null) {
            throw new BusinessErrorException(BusinessExceptionMsgEnum.USER_IS_NOT_LOGIN);
        }
        assert adminUserDetails.getPermissionCodeList() != null;
        return CommonResult.success(adminUserDetails.getPermissionCodeList());
    }

    /**
     * 获取菜单.
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
        assert adminUserDetails.getUmsAdmin() != null;
        if (Boolean.TRUE.equals(adminUserDetails.getUmsAdmin().getIsAdmin())) {
            return CommonResult.success(MenuUtil.getRouter(umsMenuService.selectRouterListAll()));
        } else {
            return CommonResult.success(
                    MenuUtil.getRouter(
                            umsMenuService.selectMenuByIds(
                                    adminUserDetails.getPermissionList()
                                            .stream()
                                            .map(UmsMenu::getId)
                                            .collect(Collectors.toList()))));
        }
    }
}
