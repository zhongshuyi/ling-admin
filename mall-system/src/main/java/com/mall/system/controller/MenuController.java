package com.mall.system.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import com.mall.common.annotation.RepeatSubmit;
import com.mall.common.core.controller.BaseController;
import com.mall.common.core.domain.CommonResult;
import com.mall.common.core.validate.ValidationGroups;
import com.mall.common.enums.BusinessMsgEnum;
import com.mall.common.exception.BusinessErrorException;
import com.mall.common.util.ServletUtils;
import com.mall.framework.model.AdminUserDetails;
import com.mall.framework.util.JwtTokenUtil;
import com.mall.system.bo.MenuBo;
import com.mall.system.entity.UmsMenu;
import com.mall.system.service.IUmsMenuService;
import com.mall.system.util.MenuUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 菜单操作.
 *
 * @author 钟舒艺
 **/
@Slf4j
@RestController
@Api(tags = "菜单操作")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/system/menu")
public class MenuController extends BaseController {

    /**
     * 权限服务.
     */
    private final transient IUmsMenuService umsMenuService;

    /**
     * jwt工具类.
     */
    private final transient JwtTokenUtil jwtTokenUtil;

    /**
     * 获取所有菜单.
     *
     * @return 菜单树结构
     */
    @GetMapping
    @ApiOperation("获取所有菜单")
    public CommonResult<List<Tree<Long>>> getMenuList() {
        AdminUserDetails adminUserDetails =
                jwtTokenUtil.getAdminUserDetails(ServletUtils.getRequest());
        if (adminUserDetails == null) {
            throw new BusinessErrorException(BusinessMsgEnum.USER_IS_NOT_LOGIN);
        }
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setWeightKey("order");

        if (adminUserDetails.getUmsAdmin().getIsAdmin()) {
            return CommonResult.success(MenuUtil.getMenuList(umsMenuService.selectMenuListAll()));
        } else {
            return CommonResult.success(MenuUtil.getMenuList(umsMenuService.list()));
        }
    }

    /**
     * 增加菜单.
     *
     * @param addBo 菜单对象
     * @return 是否增加成功
     */
    @PostMapping
    @ApiOperation("增加菜单")
    public CommonResult<Void> addMenu(
            @Validated(ValidationGroups.Add.class)
            @RequestBody final MenuBo addBo
    ) {
        if (umsMenuService.checkMenuUnique(BeanUtil.toBean(addBo, UmsMenu.class))) {
            return CommonResult.failed("菜单" + addBo.getTitle() + "已存在");
        }
        return toAjax(umsMenuService.addByAddBo(addBo));
    }

    /**
     * 删除菜单.
     *
     * @param id 菜单id
     * @return 是否删除成功
     */
    @ApiOperation("删除菜单")
    @DeleteMapping("/{id}")
    public CommonResult<Void> delMenu(@PathVariable final Long id) {
        return toAjax(umsMenuService.deleteById(id));
    }


    /**
     * 更改菜单信息.
     *
     * @param bo 菜单信息
     * @return 是否更改成功
     */
    @ApiOperation("编辑菜单")
    @PutMapping
    @RepeatSubmit
    public CommonResult<Void> editMenu(
            @Validated(ValidationGroups.Edit.class)
            @RequestBody final MenuBo bo) {
        if (bo.getId().equals(bo.getParentId())) {
            return CommonResult.failed("上级菜单不能为自己");
        } else if (umsMenuService.checkMenuUnique(BeanUtil.toBean(bo, UmsMenu.class))) {
            return CommonResult.failed("菜单" + bo.getTitle() + "已存在");
        }
        return toAjax(umsMenuService.updateById(BeanUtil.toBean(bo, UmsMenu.class)));
    }

    /**
     * 检查菜单是否有子菜单.
     *
     * @param id 菜单id
     * @return 是否有子菜单
     */
    @ApiOperation("检查菜单是否有子菜单")
    @GetMapping("checkMenuHasChildren/{id}")
    public CommonResult<Boolean> checkMenuHasChildren(@PathVariable final Long id) {
        return CommonResult.success(CollUtil.isNotEmpty(umsMenuService.getMenuChildren(id)));
    }

    /**
     * 获取权限树.
     *
     * @return 权限树
     */
    @ApiOperation("获取权限树结构")
    @GetMapping("getPerm")
    public CommonResult<List<Tree<Long>>> getPerm() {
        return CommonResult.success(MenuUtil.buildPermTree(umsMenuService.list()));
    }
}
