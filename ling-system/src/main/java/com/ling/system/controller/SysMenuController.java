package com.ling.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import com.ling.common.annotation.RepeatSubmit;
import com.ling.common.constant.PermissionCodeConstant;
import com.ling.common.core.controller.BaseController;
import com.ling.common.core.domain.CommonResult;
import com.ling.common.core.domain.model.LoginUser;
import com.ling.common.core.domain.model.SysMenu;
import com.ling.common.core.validate.ValidationGroups;
import com.ling.framework.utils.SecurityUtils;
import com.ling.system.convert.SysMenuConvert;
import com.ling.system.dto.SysMenuDTO;
import com.ling.system.service.ISysMenuService;
import com.ling.system.utils.MenuUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
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
 * 菜单管理.
 *
 * @author 钟舒艺
 * @since 2022-10-29 15:28
 **/
@Slf4j
@RestController
@Tag(name = "菜单管理")
@RequiredArgsConstructor
@RequestMapping("/system/menu")
public class SysMenuController extends BaseController {

    /**
     * 权限服务.
     */
    private final ISysMenuService sysMenuService;

    /**
     * 获取所有菜单.
     *
     * @return 菜单树结构
     */
    @GetMapping
    @SaCheckPermission(PermissionCodeConstant.SYS_MENU_LIST)
    public CommonResult<List<Tree<Long>>> getMenuList() {
        final LoginUser loginUserInfo = SecurityUtils.getLoginUser();
        return CommonResult.success(MenuUtil.getMenuList(loginUserInfo.getSysMenuAndPermissionList()));
    }

    /**
     * 增加菜单.
     *
     * @param dto 菜单对象
     * @return 是否增加成功
     */
    @PostMapping
    @SaCheckPermission(PermissionCodeConstant.SYS_MENU_ADD)
    public CommonResult<Void> addMenu(
            @Validated(ValidationGroups.Add.class) @RequestBody final SysMenuDTO dto) {
        if (Boolean.TRUE.equals(sysMenuService.checkMenuUnique(BeanUtil.toBean(dto, SysMenu.class)))) {
            return CommonResult.failed("菜单" + dto.getTitle() + "已存在");
        }
        return toAjax(sysMenuService.saveMenuByDTO(dto));
    }

    /**
     * 删除菜单.
     *
     * @param id 菜单id
     * @return 是否删除成功
     */
    @DeleteMapping("/{id}")
    @SaCheckPermission(PermissionCodeConstant.SYS_MENU_DELETE)
    public CommonResult<Void> delMenu(@PathVariable final Long id) {
        return toAjax(sysMenuService.removeMenuById(id));
    }

    /**
     * 更改菜单信息.
     *
     * @param dto 菜单信息
     * @return 是否更改成功
     */
    @PutMapping
    @RepeatSubmit
    @SaCheckPermission(PermissionCodeConstant.SYS_MENU_EDIT)
    public CommonResult<Void> editMenu(
            @Validated(ValidationGroups.Edit.class) @RequestBody final SysMenuDTO dto) {
        if (dto.getId().equals(dto.getParentId())) {
            return CommonResult.failed("上级菜单不能为自己");
        } else if (Boolean.TRUE.equals(sysMenuService.checkMenuUnique(BeanUtil.toBean(dto, SysMenu.class)))) {
            return CommonResult.failed("菜单" + dto.getTitle() + "已存在");
        }
        return toAjax(sysMenuService.updateById(SysMenuConvert.INSTANCT.dtoToEntity(dto)));
    }


    /**
     * 检查菜单是否有子菜单.
     *
     * @param id 菜单id
     * @return 是否有子菜单
     */
    @GetMapping("checkMenuHasChildren/{id}")
    public CommonResult<Boolean> checkMenuHasChildren(@PathVariable final Long id) {
        return CommonResult.success(CollUtil.isNotEmpty(sysMenuService.listMenuChildren(id)));
    }
}
