package com.ling.system.controller;

import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ling.common.core.controller.BaseController;
import com.ling.common.core.domain.CommonResult;
import com.ling.common.core.domain.PageInfo;
import com.ling.common.core.mybatisplus.util.PageUtils;
import com.ling.common.core.validate.ValidationGroups;
import com.ling.common.exception.BusinessErrorException;
import com.ling.framework.config.CustomConfig;
import com.ling.system.convert.SysRoleConvert;
import com.ling.system.dto.SysRoleDTO;
import com.ling.system.entity.SysRole;
import com.ling.system.service.ISysMenuService;
import com.ling.system.service.ISysRoleService;
import com.ling.system.vo.SysRoleVO;
import java.util.List;
import java.util.Set;
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
 * 角色管理.
 *
 * @author 钟舒艺
 * @since 2022-10-29 14:22
 **/
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/role")
public class SysRoleController extends BaseController {

    /**
     * 角色服务.
     */
    private final ISysRoleService sysRoleService;

    /**
     * 权限菜单服务.
     */
    private final ISysMenuService sysMenuService;

    /**
     * app配置信息.
     */
    private final CustomConfig config;

    /**
     * 分页获取角色列表.
     *
     * @param role 查询信息
     * @return 分页后的角色列表
     */
    @GetMapping
    public CommonResult<PageInfo<SysRoleVO>> getPageList(final SysRoleDTO role) {
        return CommonResult.success(
                PageUtils.buildPageInfo(
                        sysRoleService.pageVo(
                                PageUtils.buildPagePlus(),
                                new QueryWrapper<>(SysRoleConvert.INSTANCT.dtoToEntity(role)).orderByAsc("order_no"),
                                SysRoleConvert.class
                        )
                )
        );
    }

    /**
     * 获取角色列表.
     * 下拉列表使用
     *
     * @return 角色列表
     */
    @GetMapping("/list")
    public CommonResult<List<SysRole>> getList() {
        return CommonResult.success(
                sysRoleService.lambdaQuery()
                        .select(SysRole::getRoleName, SysRole::getId)
                        .list()
        );
    }

    /**
     * 添加角色.
     *
     * @param role 角色信息
     * @return 是否添加成功
     */
    @PostMapping
    public CommonResult<Void> add(@Validated(ValidationGroups.ADD) @RequestBody final SysRoleDTO role) {
        return toAjax(sysRoleService.saveByDTO(role, SysRoleConvert.class));
    }

    /**
     * 修改角色.
     *
     * @param role 角色信息
     * @return 是否修改成功
     */
    @PutMapping
    public CommonResult<Void> edit(@Validated(ValidationGroups.EDIT) @RequestBody final SysRoleDTO role) {
        if (role.getId().equals(config.getApp().getSuperAdminRoleId())) {
            throw new BusinessErrorException(HttpStatus.HTTP_BAD_REQUEST, "不能修改超级管理员");
        }
        return toAjax(sysRoleService.updateByDTO(role));
    }

    /**
     * 更改角色状态.
     *
     * @param id    角色id
     * @param state 新的角色状态
     * @return 是否更改成功
     */
    @PutMapping("/{id}/{state}")
    public CommonResult<Void> stateChanges(
            @PathVariable final Long id,
            @PathVariable final Byte state
    ) {
        if (id.equals(config.getApp().getSuperAdminRoleId())) {
            throw new BusinessErrorException(HttpStatus.HTTP_BAD_REQUEST, "不能修改超级管理员状态");
        }
        return toAjax(sysRoleService.stateChanges(id, state));
    }

    /**
     * 根据id删除角色.
     *
     * @param id 角色id
     * @return 是否删除成功
     */
    @DeleteMapping("/{id}")
    public CommonResult<Void> del(@PathVariable final Long id) {
        if (id.equals(config.getApp().getSuperAdminRoleId())) {
            throw new BusinessErrorException(HttpStatus.HTTP_BAD_REQUEST, "不能删除超级管理员");
        }
        return toAjax(sysRoleService.deleteWithValidById(id));
    }

    /**
     * 获取角色的自定义数据权限范围.
     *
     * @param id 角色id
     * @return 部门id集合
     */
    @GetMapping("dataScope/{id}")
    public CommonResult<Set<Long>> getCustomDataScope(@PathVariable final Long id) {
        return CommonResult.success(sysRoleService.selectDataScope(id));
    }

    /**
     * 更改角色的自定义数据权限范围.
     *
     * @param id     角色id
     * @param newIds 新的自定义权限的部门id
     * @return 是否成功
     */
    @PutMapping("dataScope/{id}")
    public CommonResult<Void> setCustomDataScope(
            @PathVariable final Long id,
            @RequestBody final Set<Long> newIds
    ) {
        return toAjax(sysRoleService.updateDataScope(id, newIds));
    }
}
