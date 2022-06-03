package com.ling.system.controller;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.http.HttpStatus;
import com.ling.common.core.controller.BaseController;
import com.ling.common.core.domain.CommonResult;
import com.ling.common.exception.BusinessErrorException;
import com.ling.framework.config.CustomConfig;
import com.ling.system.service.ISysMenuService;
import com.ling.system.util.MenuUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 权限操作.
 *
 * @author 钟舒艺
 * @date 2022-05-10-18:34
 **/

@Slf4j
@RestController
@Api(tags = "权限操作")
@RequiredArgsConstructor
@RequestMapping("/system/perm")
public class PermissionController extends BaseController {

    /**
     * 权限菜单服务.
     */
    private final ISysMenuService sysMenuService;

    /**
     * app配置信息.
     */
    private final CustomConfig config;


    /**
     * 获取权限树.
     *
     * @return 权限树
     */
    @ApiOperation("获取全部权限树结构")
    @GetMapping("getPermTree")
    public CommonResult<List<Tree<Long>>> getPerm() {
        return CommonResult.success(MenuUtil.buildPermTree(sysMenuService.list()));
    }

    /**
     * 获取角色的权限id集合.
     *
     * @param id 角色id
     * @return 角色的权限id集合
     */
    @ApiOperation("获取角色权限")
    @GetMapping("role/{id}")
    public CommonResult<Set<Long>> getPerm(@PathVariable final Long id) {
        return CommonResult.success(sysMenuService.getRolePerm(id));
    }

    /**
     * 更改角色的权限.
     *
     * @param id     角色id
     * @param newIds 新的角色权限集合
     * @return 是否更改成功
     */
    @ApiOperation("更改角色权限")
    @PutMapping("role/{id}")
    public CommonResult<Void> setPerm(
            @PathVariable final Long id,
            @RequestBody final Set<Long> newIds
    ) {
        if (id.equals(config.getApp().getSuperAdminRoleId())) {
            throw new BusinessErrorException(HttpStatus.HTTP_BAD_REQUEST, "不能改变超级管理员权限");
        }
        return toAjax(sysMenuService.setRolePerm(id, newIds));
    }

    /**
     * 获取部门权限id集合.
     *
     * @param id 部门id
     * @return 拥有的权限集合
     */
    @ApiOperation("获取部门权限")
    @GetMapping("deptPerm/{id}")
    public CommonResult<Set<Long>> getDeptPerm(@PathVariable final Long id) {
        return CommonResult.success(sysMenuService.selectDeptPerm(id));
    }

    /**
     * 更改部门的权限.
     *
     * @param deptId 部门id
     * @param newIds 新的权限id集合
     * @return 是否更改成功
     */
    @ApiOperation("更改部门权限")
    @PutMapping("deptPerm/{deptId}")
    public CommonResult<Void> setDeptPerm(
            @PathVariable final Long deptId,
            @RequestBody final Set<Long> newIds
    ) {
        return toAjax(sysMenuService.setDeptPerm(deptId, newIds));
    }

    /**
     * 获取部门的权限树.
     *
     * @param deptId 部门id
     * @return 部门 权限树
     */
    @GetMapping("deptPermTree/{deptId}")
    @ApiOperation("获取部门拥有权限的权限树")
    public CommonResult<List<Tree<Long>>> getDeptPermTree(@PathVariable final Long deptId) {
        return CommonResult.success(
                MenuUtil.buildPermTree(sysMenuService.selectDeptPermMenu(deptId)));
    }

    /**
     * 获取部门角色权限.
     *
     * @param deptRoleId 部门角色id
     * @return 部门角色权限id
     */
    @GetMapping("deptRolePerm/{deptRoleId}")
    @ApiOperation("获取部门角色权限")
    public CommonResult<Set<Long>> getDeptRolePerm(@PathVariable final Long deptRoleId) {
        return CommonResult.success(sysMenuService.selectDeptRolePermIds(deptRoleId));
    }

    /**
     * 更改部门角色权限.
     *
     * @param deptRoleId 部门角色id
     * @param newIds     新的权限id
     * @return 是否成功
     */
    @PutMapping("deptRolePerm/{deptRoleId}")
    @ApiOperation("更改部门角色权限")
    public CommonResult<Void> setDeptRolePerm(
            @PathVariable final Long deptRoleId,
            @RequestBody final Set<Long> newIds) {
        return toAjax(sysMenuService.setDeptRolePerm(deptRoleId, newIds));
    }
}
