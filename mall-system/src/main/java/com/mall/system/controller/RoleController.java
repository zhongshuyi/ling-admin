package com.mall.system.controller;

import cn.hutool.http.HttpStatus;
import com.mall.common.constant.GlobalConstants;
import com.mall.common.core.controller.BaseController;
import com.mall.common.core.domain.CommonResult;
import com.mall.common.core.domain.PageInfo;
import com.mall.common.core.mybatisplus.util.PageUtils;
import com.mall.common.core.validate.ValidationGroups;
import com.mall.common.exception.BusinessErrorException;
import com.mall.system.bo.RoleBo;
import com.mall.system.entity.UmsRole;
import com.mall.system.service.IUmsMenuService;
import com.mall.system.service.IUmsRoleService;
import com.mall.system.vo.RoleVo;
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
    private final IUmsRoleService umsRoleService;

    /**
     * 权限菜单服务.
     */
    private final IUmsMenuService umsMenuService;

    /**
     * 分页获取角色列表.
     *
     * @param role 查询信息
     * @return 分页后的角色列表
     */
    @GetMapping
    @ApiOperation("分页获取角色列表")
    public CommonResult<PageInfo<RoleVo>> getPageList(final RoleBo role) {
        return CommonResult.success(PageUtils.buildPageInfo(umsRoleService.pageVo(PageUtils.buildPagePlus(), role)));
    }

    /**
     * 获取角色列表.
     *
     * @return 角色列表
     */
    @GetMapping("/list")
    @ApiOperation("获取角色列表(下拉列表使用)")
    public CommonResult<List<UmsRole>> getList() {
        return CommonResult.success(
                umsRoleService.lambdaQuery()
                        .select(UmsRole::getRoleName, UmsRole::getId)
                        .list()
        );
    }

    /**
     * 添加角色列表.
     *
     * @param role 角色信息
     * @return 是否添加成功
     */
    @PostMapping
    @ApiOperation("添加角色")
    public CommonResult<Void> add(@Validated(ValidationGroups.Add.class) @RequestBody final RoleBo role) {
        return toAjax(umsRoleService.insertByBo(role));
    }

    /**
     * 修改角色.
     *
     * @param role 角色信息
     * @return 是否修改成功
     */
    @PutMapping
    @ApiOperation("修改角色")
    public CommonResult<Void> edit(@Validated(ValidationGroups.Edit.class) @RequestBody final RoleBo role) {
        if (role.getId().equals(GlobalConstants.SUPER_ADMIN_ROLE_ID)) {
            throw new BusinessErrorException(HttpStatus.HTTP_BAD_REQUEST, "不能修改超级管理员");
        }
        return toAjax(umsRoleService.updateByBo(role));
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
    public CommonResult<Void> stateChanges(@PathVariable final Long id, @PathVariable final Integer state) {
        if (id.equals(GlobalConstants.SUPER_ADMIN_ROLE_ID)) {
            throw new BusinessErrorException(HttpStatus.HTTP_BAD_REQUEST, "不能修改超级管理员状态");
        }
        return toAjax(umsRoleService.stateChanges(id, state));
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
        if (id.equals(GlobalConstants.SUPER_ADMIN_ROLE_ID)) {
            throw new BusinessErrorException(HttpStatus.HTTP_BAD_REQUEST, "不能删除超级管理员");
        }
        return toAjax(umsRoleService.deleteWithValidById(id));
    }

    /**
     * 获取角色的权限id集合.
     *
     * @param id 角色id
     * @return 角色的权限id集合
     */
    @ApiOperation("获取角色权限")
    @GetMapping("perm/{id}")
    public CommonResult<Set<Long>> getPerm(@PathVariable final Long id) {
        return CommonResult.success(umsMenuService.getRolePerm(id));
    }

    /**
     * 更改角色的权限.
     *
     * @param id     角色id
     * @param newIds 新的角色权限集合
     * @return 是否更改成功
     */
    @ApiOperation("更改角色权限")
    @PutMapping("perm/{id}")
    public CommonResult<Void> setPerm(@PathVariable final Long id, @RequestBody final Set<Long> newIds) {
        if (id.equals(GlobalConstants.SUPER_ADMIN_ROLE_ID)) {
            throw new BusinessErrorException(HttpStatus.HTTP_BAD_REQUEST, "不能改变超级管理员权限");
        }
        return toAjax(umsMenuService.setRolePerm(id, newIds));
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
        return CommonResult.success(umsRoleService.getDataScope(id));
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
    public CommonResult<Void> setCustomDataScope(@PathVariable final Long id, @RequestBody final Set<Long> newIds) {
        return CommonResult.success();
    }
}
