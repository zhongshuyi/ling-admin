package com.mall.web.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import com.mall.common.annotation.RepeatSubmit;
import com.mall.common.core.controller.BaseController;
import com.mall.common.core.domain.CommonResult;
import com.mall.common.core.domain.entity.UmsMenu;
import com.mall.common.core.util.ServletUtils;
import com.mall.common.core.validate.ValidationGroups;
import com.mall.framework.model.AdminUserDetails;
import com.mall.framework.util.JwtTokenUtil;
import com.mall.system.bo.MenuBo;
import com.mall.system.service.IUmsMenuService;
import com.mall.system.util.MenuUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
    public CommonResult<List<Tree<Long>>> getMenuList() {
        AdminUserDetails adminUserDetails = jwtTokenUtil.getAdminUserDetails(ServletUtils.getRequest());

        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setWeightKey("order");

        if (adminUserDetails.getUmsAdmin().getId().equals(1L)) {
            return CommonResult.success(MenuUtil.getMenuList(umsMenuService.selectMenuListAll()));
        } else {
            return CommonResult.success(MenuUtil.getMenuList(umsMenuService.list()));
        }
    }

    @PostMapping
    @ApiOperation("增加菜单")
    public CommonResult addMenu(@Validated(ValidationGroups.Add.class) @RequestBody MenuBo addBo) {
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
    public CommonResult editMenu(@Validated(ValidationGroups.Edit.class) @RequestBody MenuBo bo) {
        if (bo.getId().equals(bo.getParentId())) {
            return CommonResult.failed("上级菜单不能为自己");
        } else if (umsMenuService.checkMenuUnique(BeanUtil.toBean(bo, UmsMenu.class))) {
            return CommonResult.failed("菜单" + bo.getTitle() + "已存在");
        }
        return CommonResult.success(umsMenuService.updateById(BeanUtil.toBean(bo, UmsMenu.class)));
    }

    @ApiOperation("检查菜单是否有子菜单")
    @GetMapping("checkMenuHasChildren/{id}")
    public CommonResult checkMenuHasChildren(@PathVariable Long id) {
        return CommonResult.success(CollUtil.isNotEmpty(umsMenuService.getMenuChildren(id)));
    }

    @ApiOperation("获取权限树结构")
    @GetMapping("getPerm")
    public CommonResult<List<Tree<Long>>> getPerm() {
        return CommonResult.success(MenuUtil.buildPermTree(umsMenuService.list()));
    }
}
