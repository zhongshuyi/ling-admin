package com.mall.web.controller;

import com.mall.common.core.util.ServletUtils;
import com.mall.framework.model.AdminUserDetails;
import com.mall.framework.util.JwtTokenUtil;
import com.mall.system.bo.add.MenuAddBo;
import com.mall.system.bo.query.MenuQueryBo;
import com.mall.common.core.domain.CommonResult;
import com.mall.system.service.IUmsMenuService;
import com.mall.system.util.MenuUtil;
import com.mall.system.vo.MenuVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 钟舒艺
 * @date 2021-09-21-14:08
 **/
@RestController
@Api(tags = "菜单操作")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/system/menu")
public class MenuController {

    private final IUmsMenuService umsMenuService;

    private final JwtTokenUtil jwtTokenUtil;

    @GetMapping
    @ApiOperation("获取菜单树")
    public CommonResult<List<MenuVo>> getMenuList(@RequestBody MenuQueryBo query) {
        AdminUserDetails adminUserDetails = jwtTokenUtil.getAdminUserDetails(ServletUtils.getRequest());
        if (adminUserDetails.getUmsAdmin().getUserId() == 1L) {
            return CommonResult.success(MenuUtil.getMenuList(umsMenuService.selectMenuListAll()));
        } else {
            return CommonResult.success(MenuUtil.getMenuList(umsMenuService.selectMenuListByUserId(query, adminUserDetails.getUmsAdmin().getUserId())));
        }
    }

    @PostMapping("增加菜单")
    public CommonResult<Void> addMenu(@RequestBody MenuAddBo addBo){
        return null;
    }





}
