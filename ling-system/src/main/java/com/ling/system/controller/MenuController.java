package com.ling.system.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ling.common.annotation.RepeatSubmit;
import com.ling.common.core.controller.BaseController;
import com.ling.common.core.domain.CommonResult;
import com.ling.common.core.validate.ValidationGroups;
import com.ling.common.enums.BusinessExceptionMsgEnum;
import com.ling.common.enums.MenuType;
import com.ling.common.exception.BusinessErrorException;
import com.ling.framework.config.CustomConfig;
import com.ling.framework.model.AdminUserDetails;
import com.ling.framework.util.SecurityUtils;
import com.ling.system.dto.MenuDTO;
import com.ling.system.entity.SysMenu;
import com.ling.system.entity.SysPermissionUrl;
import com.ling.system.mapper.SysPermissionUrlMapper;
import com.ling.system.service.ISysMenuService;
import com.ling.system.util.MenuUtil;
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
    private final ISysMenuService sysMenuService;


    /**
     * 部门服务.
     */
    private final ISysMenuService sysDeptService;

    /**
     * 配置信息.
     */
    private final CustomConfig config;

    /**
     * 权限url mapper.
     */
    private final SysPermissionUrlMapper sysPermissionUrlMapper;


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

        if (Boolean.TRUE.equals(adminUserDetails.getSysAdmin().getIsAdmin())) {
            return CommonResult.success(MenuUtil.getMenuList(sysMenuService.selectMenuListAll()));
        } else {
            return CommonResult.success(MenuUtil.getMenuList(sysMenuService.list()));
        }
    }

    /**
     * 获取所有接口路径和方式.
     *
     * @return 所有接口路径和方式
     */
    @GetMapping("/getAllPermissionUrl")
    @ApiOperation("获取所有可配置权限的接口路径和方式")
    public CommonResult<List<SysPermissionUrl>> getAllPermissionUrl() {
        return CommonResult.success(ListUtil.sortByProperty(config.getPermUrl(), "url"));
    }

    /**
     * 获取权限拥有的接口路径和方式.
     *
     * @param id 权限的id
     * @return 所有接口路径和方式
     */
    @GetMapping("/getPermissionUrlList/{id}")
    @ApiOperation("获取权限已有的接口路径和方式")
    public CommonResult<List<SysPermissionUrl>> getPermissionUrl(@PathVariable final Long id) {
        return CommonResult.success(
                sysPermissionUrlMapper.selectList(
                        Wrappers.<SysPermissionUrl>lambdaQuery().eq(SysPermissionUrl::getMenuId, id)));
    }

    /**
     * 增加菜单.
     *
     * @param dto 菜单对象
     * @return 是否增加成功
     */
    @PostMapping
    @ApiOperation("增加菜单")
    public CommonResult<Void> addMenu(
            @Validated(ValidationGroups.Add.class) @RequestBody final MenuDTO dto) {
        if (Boolean.TRUE.equals(sysMenuService.checkMenuUnique(BeanUtil.toBean(dto, SysMenu.class)))) {
            return CommonResult.failed("菜单" + dto.getTitle() + "已存在");
        }
        return toAjax(sysMenuService.addByDTO(dto));
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
        return toAjax(sysMenuService.deleteById(id));
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
            @Validated(ValidationGroups.Edit.class) @RequestBody final MenuDTO bo) {
        if (bo.getId().equals(bo.getParentId())) {
            return CommonResult.failed("上级菜单不能为自己");
        } else if (Boolean.TRUE.equals(sysMenuService.checkMenuUnique(BeanUtil.toBean(bo, SysMenu.class)))) {
            return CommonResult.failed("菜单" + bo.getTitle() + "已存在");
        }
        if (bo.getMenuType().equals(MenuType.BUTTON.getCode()) && CollUtil.isNotEmpty(bo.getPermissionUrl())) {
            // 删除这个菜单有关的权限url
            sysPermissionUrlMapper.delete(
                    Wrappers.<SysPermissionUrl>lambdaQuery().eq(SysPermissionUrl::getMenuId, bo.getId()));

            for (final SysPermissionUrl url : bo.getPermissionUrl()) {
                url.setMenuId(bo.getId());
            }
            // 重新插入提交过来的
            sysPermissionUrlMapper.insertAll(bo.getPermissionUrl());
        }
        return toAjax(sysMenuService.updateById(BeanUtil.toBean(bo, SysMenu.class)));
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
        return CommonResult.success(CollUtil.isNotEmpty(sysMenuService.selectMenuChildren(id)));
    }
}
