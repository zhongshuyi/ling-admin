package com.ling.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.http.HttpStatus;
import com.ling.common.constant.PermissionCodeConstant;
import com.ling.common.core.controller.BaseController;
import com.ling.common.core.domain.CommonResult;
import com.ling.common.exception.BusinessErrorException;
import com.ling.framework.config.CustomConfig;
import com.ling.system.service.ISysMenuService;
import com.ling.system.utils.MenuUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "权限操作")
@RequiredArgsConstructor
@RequestMapping("/system/perm")
public class SysPermissionController extends BaseController {

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
    @GetMapping("getPermTree")
    @SaCheckPermission(PermissionCodeConstant.SYS_ROLE_EDIT)
    public CommonResult<List<Tree<Long>>> getPerm() {
        return CommonResult.success(MenuUtil.buildPermTree(sysMenuService.list()));
    }

    /**
     * 获取角色的权限id集合.
     *
     * @param id 角色id
     * @return 角色的权限id集合
     */
    @GetMapping("role/{id}")
    @SaCheckPermission(PermissionCodeConstant.SYS_ROLE_EDIT)
    public CommonResult<Set<Long>> getPerm(@PathVariable final Long id) {
        return CommonResult.success(sysMenuService.listRolePerm(id));
    }

    /**
     * 更改角色的权限.
     *
     * @param id     角色id
     * @param newIds 新的角色权限集合
     * @return 是否更改成功
     */
    @PutMapping("role/{id}")
    @SaCheckPermission(PermissionCodeConstant.SYS_ROLE_EDIT)
    public CommonResult<Void> setPerm(
            @PathVariable final Long id,
            @RequestBody final Set<Long> newIds
    ) {
        if (id.equals(config.getApp().getSuperAdminRoleId())) {
            throw new BusinessErrorException(HttpStatus.HTTP_BAD_REQUEST, "不能改变超级管理员权限");
        }
        return toAjax(sysMenuService.updateRolePerm(id, newIds));
    }

    /**
     * 获取部门权限id集合.
     *
     * @param id 部门id
     * @return 拥有的权限集合
     */
    @GetMapping("deptPerm/{id}")
    @SaCheckPermission(PermissionCodeConstant.SYS_DEPT_EDIT)
    public CommonResult<Set<Long>> getDeptPerm(@PathVariable final Long id) {
        return CommonResult.success(sysMenuService.listDeptPerm(id));
    }

    /**
     * 更改部门的权限.
     *
     * @param deptId 部门id
     * @param newIds 新的权限id集合
     * @return 是否更改成功
     */
    @PutMapping("deptPerm/{deptId}")
    @SaCheckPermission(PermissionCodeConstant.SYS_DEPT_EDIT)
    public CommonResult<Void> setDeptPerm(
            @PathVariable final Long deptId,
            @RequestBody final Set<Long> newIds
    ) {
        return toAjax(sysMenuService.updateDeptPerm(deptId, newIds));
    }

    /**
     * 获取部门的权限树.
     *
     * @param deptId 部门id
     * @return 部门 权限树
     */
    @GetMapping("deptPermTree/{deptId}")
    @SaCheckPermission(PermissionCodeConstant.SYS_SUBDEPT_LIST)
    public CommonResult<List<Tree<Long>>> getDeptPermTree(@PathVariable final Long deptId) {
        return CommonResult.success(
                MenuUtil.buildPermTree(sysMenuService.listDeptPermMenu(deptId)));
    }

    /**
     * 获取部门角色权限.
     *
     * @param deptRoleId 部门角色id
     * @return 部门角色权限id
     */
    @GetMapping("deptRolePerm/{deptRoleId}")
    @SaCheckPermission(PermissionCodeConstant.SYS_SUBDEPT_LIST)
    public CommonResult<Set<Long>> getDeptRolePerm(@PathVariable final Long deptRoleId) {
        return CommonResult.success(sysMenuService.listDeptRolePermIds(deptRoleId));
    }

    /**
     * 更改部门角色权限.
     *
     * @param deptRoleId 部门角色id
     * @param newIds     新的权限id
     * @return 是否成功
     */
    @PutMapping("deptRolePerm/{deptRoleId}")
    @SaCheckPermission(PermissionCodeConstant.SYS_SUBDEPT_EDIT)
    public CommonResult<Void> setDeptRolePerm(
            @PathVariable final Long deptRoleId,
            @RequestBody final Set<Long> newIds) {
        return toAjax(sysMenuService.updateDeptRolePerm(deptRoleId, newIds));
    }
}
