package com.ling.system.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ling.common.core.controller.BaseController;
import com.ling.common.core.domain.CommonResult;
import com.ling.common.core.domain.PageInfo;
import com.ling.common.core.mybatisplus.util.PageUtils;
import com.ling.common.core.validate.ValidationGroups;
import com.ling.common.exception.BusinessErrorException;
import com.ling.framework.config.CustomConfig;
import com.ling.system.dto.RoleDTO;
import com.ling.system.entity.SysRole;
import com.ling.system.service.ISysMenuService;
import com.ling.system.service.ISysRoleService;
import com.ling.system.vo.RoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
 * 角色管理操作.
 *
 * @author 钟舒艺
 **/
@Slf4j
@Validated
@RestController
@Api(tags = "角色操作")
@RequestMapping("/system/role")
@RequiredArgsConstructor
public class RoleController extends BaseController {

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
    @ApiOperation("分页获取角色列表")
    public CommonResult<PageInfo<RoleVo>> getPageList(final RoleDTO role) {
        return CommonResult.success(
                PageUtils.buildPageInfo(
                        sysRoleService.pageVo(
                                PageUtils.buildPagePlus(),
                                new QueryWrapper<>(BeanUtil.toBean(role, SysRole.class)).orderByAsc("order_no")
                        )
                )
        );
    }

    /**
     * 获取角色列表.
     *
     * @return 角色列表
     */
    @GetMapping("/list")
    @ApiOperation("获取角色列表(下拉列表使用)")
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
    @ApiOperation("添加角色")
    public CommonResult<Void> add(@Validated(ValidationGroups.Add.class) @RequestBody final RoleDTO role) {
        return toAjax(sysRoleService.insertByDTO(role));
    }

    /**
     * 修改角色.
     *
     * @param role 角色信息
     * @return 是否修改成功
     */
    @PutMapping
    @ApiOperation("修改角色")
    public CommonResult<Void> edit(@Validated(ValidationGroups.Edit.class) @RequestBody final RoleDTO role) {
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
    @ApiOperation("更改角色状态")
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
     * 根据id删除角色(如果有关联的用户或菜单则不允许删除).
     *
     * @param id 角色id
     * @return 是否删除成功
     */
    @ApiOperation("根据id删除角色")
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
    @ApiOperation("获取角色的自定义数据权限范围")
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
    @ApiOperation("更改角色的自定义数据权限范围")
    @PutMapping("dataScope/{id}")
    public CommonResult<Void> setCustomDataScope(
            @PathVariable final Long id,
            @RequestBody final Set<Long> newIds
    ) {
        return toAjax(sysRoleService.setDataScope(id, newIds));
    }
}
