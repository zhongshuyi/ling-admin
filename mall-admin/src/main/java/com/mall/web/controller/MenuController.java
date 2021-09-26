package com.mall.web.controller;

import cn.hutool.core.bean.BeanUtil;
import com.mall.common.annotation.RepeatSubmit;
import com.mall.common.core.controller.BaseController;
import com.mall.common.core.domain.entity.UmsMenu;
import com.mall.common.core.util.ServletUtils;
import com.mall.framework.model.AdminUserDetails;
import com.mall.framework.util.JwtTokenUtil;
import com.mall.system.bo.add.MenuAddBo;
import com.mall.system.bo.edit.MenuEditBo;
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

import java.util.Arrays;
import java.util.List;

/**
 * @author 钟舒艺
 * @date 2021-09-21-14:08
 **/
@RestController
@Api(tags = "菜单操作")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/system/menu")
public class MenuController extends BaseController {

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

    @PostMapping
    @ApiOperation("增加菜单")
    public CommonResult addMenu(@RequestBody MenuAddBo addBo) {
        if (umsMenuService.checkMenuUnique(BeanUtil.toBean(addBo, UmsMenu.class))) {
            return CommonResult.failed("菜单" + addBo.getTitle() + "已存在");
        }
        return toAjax(umsMenuService.addByAddBo(addBo));
    }

    @ApiOperation("删除菜单")
    @DeleteMapping("/{ids}")
    public CommonResult delMenu(@PathVariable Long[] ids) {
        return toAjax(umsMenuService.deleteWithValidByIds(Arrays.asList(ids), true));
    }


    @ApiOperation("编辑菜单")
    @PutMapping()
    @RepeatSubmit
    public CommonResult editMenu(@RequestBody MenuEditBo bo) {
        if (bo.getId().equals(bo.getParentId())) {
            return CommonResult.failed("上级菜单不能为自己");
        } else if (umsMenuService.checkMenuUnique(BeanUtil.toBean(bo, UmsMenu.class))) {
            return CommonResult.failed("菜单" + bo.getTitle() + "已存在");
        }
        return CommonResult.success(umsMenuService.updateById(BeanUtil.toBean(bo, UmsMenu.class)));
    }
}
