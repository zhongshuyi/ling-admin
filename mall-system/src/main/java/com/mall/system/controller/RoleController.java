package com.mall.system.controller;

import com.mall.common.constant.GlobalConstants;
import com.mall.common.core.controller.BaseController;
import com.mall.common.core.domain.CommonResult;
import com.mall.common.core.domain.PageInfo;
import com.mall.common.core.util.PageUtils;
import com.mall.common.core.validate.ValidationGroups;
import com.mall.common.exception.BusinessErrorException;
import com.mall.system.bo.RoleBo;
import com.mall.system.service.IUmsMenuService;
import com.mall.system.service.IUmsRoleService;
import com.mall.system.vo.RoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

/**
 * @author 钟舒艺
 * @date 2021-10-26-14:47
 **/
@Slf4j
@Validated
@RestController
@Api(tags = "角色操作")
@RequestMapping("/system/role")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RoleController extends BaseController {

    private final IUmsRoleService umsRoleService;

    private final IUmsMenuService umsMenuService;

    /**
     * 分页获取角色列表
     *
     * @return 分页后的角色列表
     */
    @GetMapping
    @ApiOperation("分页获取角色列表")
    public CommonResult<PageInfo<RoleVo>> getList() {
        return CommonResult.success(PageUtils.buildPageInfo(umsRoleService.pageVo(PageUtils.buildPagePlus())));
    }

    /**
     * 添加角色列表
     *
     * @param role 角色信息
     * @return 是否添加成功
     */
    @PostMapping
    @ApiOperation("添加角色")
    public CommonResult add(@Validated(ValidationGroups.Add.class) @RequestBody RoleBo role) {
        return toAjax(umsRoleService.insertByBo(role));
    }

    /**
     * 修改角色
     *
     * @param role 角色信息
     * @return 是否修改成功
     */
    @PutMapping
    @ApiOperation("修改角色")
    public CommonResult edit(@Validated(ValidationGroups.Edit.class) @RequestBody RoleBo role) {
        if (role.getId().equals(GlobalConstants.SUPER_ADMIN_ROLE_ID)) {
            throw new BusinessErrorException(400, "超级管理员角色不能操作");
        }
        return toAjax(umsRoleService.updateByBo(role));
    }

    /**
     * 更改角色状态
     *
     * @param id    角色id
     * @param state 新的角色状态
     * @return 是否更改成功
     */
    @PutMapping("/{id}/{state}")
    @ApiOperation("更改角色状态")
    public CommonResult stateChanges(@PathVariable Long id, @PathVariable Integer state) {
        if (id.equals(GlobalConstants.SUPER_ADMIN_ROLE_ID)) {
            throw new BusinessErrorException(400, "超级管理员角色不能操作");
        }
        return toAjax(umsRoleService.stateChanges(id, state));
    }

    /**
     * 根据id删除角色(如果有关联的用户或菜单则不允许删除)
     *
     * @param id 角色id
     * @return 是否删除成功
     */
    @ApiOperation("根据id删除角色")
    @DeleteMapping("/{id}")
    public CommonResult del(@PathVariable Long id) {
        if (id.equals(GlobalConstants.SUPER_ADMIN_ROLE_ID)) {
            throw new BusinessErrorException(400, "超级管理员角色不能操作");
        }
        return toAjax(umsRoleService.removeById(id));
    }

    /**
     * 获取角色的权限id集合
     *
     * @param id 角色id
     * @return 角色的权限id集合
     */
    @ApiOperation("获取角色权限")
    @GetMapping("perm/{id}")
    public CommonResult<Set<Long>> getPerm(@PathVariable Long id) {
        return CommonResult.success(umsMenuService.getRolePerm(id));
    }

    /**
     * 更改角色的权限
     *
     * @param id     角色id
     * @param newIds 新的角色权限集合
     * @return 是否更改成功
     */
    @ApiOperation("更改角色权限")
    @PutMapping("perm/{id}")
    public CommonResult setPerm(@PathVariable Long id, @RequestBody Set<Long> newIds) {
        if (id.equals(GlobalConstants.SUPER_ADMIN_ROLE_ID)) {
            throw new BusinessErrorException(400, "超级管理员角色不能操作");
        }
        Set<Long> oldIds = umsMenuService.getRolePerm(id);
        Set<Long> result = new HashSet<>(oldIds);
        result.removeAll(newIds);
        Boolean isSuccess = umsMenuService.removeRolePerm(id, result);
        result.clear();
        result.addAll(newIds);
        result.removeAll(oldIds);
        return toAjax(umsMenuService.addRolePerm(id, result) && isSuccess);
    }


}
