package com.mall.system.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mall.common.annotation.RepeatSubmit;
import com.mall.common.core.controller.BaseController;
import com.mall.common.core.domain.CommonResult;
import com.mall.common.core.validate.ValidationGroups;
import com.mall.common.enums.BusinessExceptionMsgEnum;
import com.mall.common.exception.BusinessErrorException;
import com.mall.framework.config.CustomConfig;
import com.mall.framework.model.AdminUserDetails;
import com.mall.framework.util.SecurityUtils;
import com.mall.system.bo.MenuBo;
import com.mall.system.entity.UmsMenu;
import com.mall.system.entity.UmsPermissionUrl;
import com.mall.system.mapper.UmsPermissionUrlMapper;
import com.mall.system.service.IUmsMenuService;
import com.mall.system.util.MenuUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
@RequestMapping("/system/menu")
public class MenuController extends BaseController {

    /**
     * 权限服务.
     */
    private final IUmsMenuService umsMenuService;

    /**
     * 部门服务.
     */
    private final IUmsMenuService umsDeptService;

    /**
     * 配置信息.
     */
    private final CustomConfig config;

    /**
     * 权限url mapper.
     */
    private final UmsPermissionUrlMapper umsPermissionUrlMapper;


    /**
     * 获取所有菜单.
     *
     * @return 菜单树结构
     */
    @GetMapping
    @ApiOperation("获取所有菜单")
    public CommonResult<List<Tree<Long>>> getMenuList() {
        final AdminUserDetails adminUserDetails = SecurityUtils.getLoginUser();
        if (adminUserDetails == null) {
            throw new BusinessErrorException(BusinessExceptionMsgEnum.USER_IS_NOT_LOGIN);
        }
        final TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setWeightKey("order");

        if (Boolean.TRUE.equals(adminUserDetails.getUmsAdmin().getIsAdmin())) {
            return CommonResult.success(MenuUtil.getMenuList(umsMenuService.selectMenuListAll()));
        } else {
            return CommonResult.success(MenuUtil.getMenuList(umsMenuService.list()));
        }
    }

    /**
     * 获取所有接口路径和方式.
     *
     * @return 所有接口路径和方式
     */
    @GetMapping("/getAllPermissionUrl")
    @ApiOperation("获取所有接口路径和方式")
    public CommonResult<List<UmsPermissionUrl>> getAllPermissionUrl() {
        return CommonResult.success(config.getAllUrl());
    }

    /**
     * 获取所有接口路径和方式.
     *
     * @param id 权限的id
     * @return 所有接口路径和方式
     */
    @GetMapping("/getPermissionUrlList/{id}")
    @ApiOperation("获取所有接口路径和方式")
    public CommonResult<List<UmsPermissionUrl>> getPermissionUrl(@PathVariable final Long id) {
        return CommonResult.success(umsPermissionUrlMapper.selectList(
                Wrappers.<UmsPermissionUrl>lambdaQuery().eq(UmsPermissionUrl::getMenuId, id)));
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
            @Validated(ValidationGroups.Add.class) @RequestBody final MenuBo addBo
    ) {
        if (Boolean.TRUE.equals(umsMenuService.checkMenuUnique(BeanUtil.toBean(addBo, UmsMenu.class)))) {
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
            @Validated(ValidationGroups.Edit.class) @RequestBody final MenuBo bo
    ) {
        if (bo.getId().equals(bo.getParentId())) {
            return CommonResult.failed("上级菜单不能为自己");
        } else if (Boolean.TRUE.equals(umsMenuService.checkMenuUnique(BeanUtil.toBean(bo, UmsMenu.class)))) {
            return CommonResult.failed("菜单" + bo.getTitle() + "已存在");
        }
        // 删除这个菜单有关的权限url
        umsPermissionUrlMapper.delete(
                Wrappers.<UmsPermissionUrl>lambdaQuery().eq(UmsPermissionUrl::getMenuId, bo.getId()));

        for (final UmsPermissionUrl url : bo.getPermissionUrl()) {
            url.setMenuId(bo.getId());
        }
        // 重新插入提交过来的
        umsPermissionUrlMapper.insertAll(bo.getPermissionUrl());
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

    /**
     * 获取部门的权限树.
     *
     * @param deptId 部门id
     * @return 部门权限树
     */
    @GetMapping("deptPermTree/{deptId}")
    @ApiOperation("获取部门拥有权限的权限树")
    public CommonResult<List<Tree<Long>>> getDeptPermTree(@PathVariable final Long deptId) {
        return CommonResult.success(
                MenuUtil.buildPermTree(umsMenuService.listByIds(umsDeptService.getDeptPerm(deptId))));
    }
}
