package com.mall.web.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
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
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class MenuController extends BaseController {

    private final IUmsMenuService umsMenuService;

    private final JwtTokenUtil jwtTokenUtil;

    @GetMapping
    @ApiOperation("获取所有菜单")
    public CommonResult<List<MenuVo>> getMenuList(MenuQueryBo query) {
        log.warn("请求getMenu");
        query.setTitle("");
        query.setStatus(null);
        AdminUserDetails adminUserDetails = jwtTokenUtil.getAdminUserDetails(ServletUtils.getRequest());
        if (adminUserDetails.getUmsAdmin().getUserId() == 1L) {
            return CommonResult.success(MenuUtil.getMenuList(umsMenuService.selectMenuListAll()));
        } else {
            return CommonResult.success(MenuUtil.getMenuList(umsMenuService.selectMenuListByUserId(adminUserDetails.getUmsAdmin().getUserId())));
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
    @DeleteMapping("/{id}")
    public CommonResult delMenu(@PathVariable Long id) {
        return toAjax(umsMenuService.deleteById(id));
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

    @ApiOperation("检查菜单是否有子菜单")
    @GetMapping("checkMenuHasChildren/{id}")
    public  CommonResult checkMenuHasChildren(@PathVariable Long id){
        return CommonResult.success(CollUtil.isNotEmpty(umsMenuService.getMenuChildren(id)));
    }
}
