package com.mall.system.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mall.common.core.controller.BaseController;
import com.mall.common.core.domain.CommonResult;
import com.mall.common.core.mybatisplus.core.PagePlus;
import com.mall.common.core.mybatisplus.util.PageUtils;
import com.mall.common.core.validate.ValidationGroups;
import com.mall.common.exception.BusinessErrorException;
import com.mall.framework.model.AdminUserDetails;
import com.mall.framework.util.SecurityUtils;
import com.mall.system.bo.DeptRoleBo;
import com.mall.system.entity.UmsDept;
import com.mall.system.entity.UmsDeptRole;
import com.mall.system.mapper.UmsDeptMapper;
import com.mall.system.service.IUmsDeptRoleService;
import com.mall.system.service.IUmsDeptService;
import com.mall.system.service.IUmsMenuService;
import com.mall.system.vo.DeptRoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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
 * 管理部门.
 *
 * @author 钟舒艺
 **/
@Slf4j
@RestController
@Api(tags = "上级管理部门操作")
@RequiredArgsConstructor
@RequestMapping("/system/deptManagement")
public class DeptManagementController extends BaseController {

    /**
     * 部门服务.
     */
    private final IUmsDeptService umsDeptService;

    /**
     * 权限菜单服务.
     */
    private final IUmsMenuService umsMenuService;

    /**
     * 部门mapper.
     */
    private final UmsDeptMapper umsDeptMapper;

    /**
     * 部门角色服务.
     */
    private final IUmsDeptRoleService deptRoleService;

    /**
     * 获取用户管理的下级部门树结构.
     *
     * @return 下级部门
     */
    @GetMapping("list")
    @ApiOperation("获取管理的下级部门树结构")
    public CommonResult<List<Tree<Long>>> getDepts() {
        final AdminUserDetails adminUserDetails = SecurityUtils.getLoginUser();
        if (!adminUserDetails.getUmsAdmin().getUserIdentity().equals(1)) {
            throw new BusinessErrorException(HttpStatus.HTTP_FORBIDDEN, "用户不是上级");
        }
        if (CharSequenceUtil.isEmpty(adminUserDetails.getUmsAdmin().getDepartIds())) {
            return CommonResult.success(CollUtil.newArrayList());
        }
        final long[] parentIds = CharSequenceUtil.splitToLong(adminUserDetails.getUmsAdmin().getDepartIds(), ",");
        // 获取管理的直接部门
        final List<UmsDept> manageList = umsDeptService.listByIds(CollUtil.toList(parentIds));
        // 获取管理部门及下级部门
        final QueryWrapper<UmsDept> queryWrapper = new QueryWrapper<>();
        manageList.forEach(p -> queryWrapper.or().likeRight("parent_list", p.getParentList()));
        final List<UmsDept> subDepts = umsDeptService.list(queryWrapper);
        // 构成树结构
        final List<Tree<Long>> allList = new ArrayList<>();
        final TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setWeightKey("orderNo");
        treeNodeConfig.setNameKey("deptName");
        final Set<Long> parentIdSet = manageList.stream().map(UmsDept::getParentId).collect(Collectors.toSet());
        for (final Long parentId : parentIdSet) {
            final List<Tree<Long>> note = TreeUtil.build(subDepts, parentId, treeNodeConfig, (treeNode, tree) -> {
                tree.setId(treeNode.getId());
                tree.setParentId(treeNode.getParentId());
                tree.setName(treeNode.getDeptName());
                tree.setWeight(treeNode.getOrderNo());
                tree.putExtra("parentList", treeNode.getParentList());
            });
            if (CollUtil.isNotEmpty(note)) {
                allList.addAll(note);
            }
        }
        return CommonResult.success(allList);
    }

    /**
     * 添加已有用户进部门.
     *
     * @param userId 用户id
     * @param deptId 部门id
     * @return 是否成功
     */
    @PostMapping("addExistUser")
    @ApiOperation("增加已有用户")
    public CommonResult<Void> addExistUser(
            final Long userId,
            final Long deptId
    ) {
        return toAjax(umsDeptMapper.addUserDept(userId, CollUtil.newHashSet(deptId)));
    }

    /**
     * 分页获取部门角色.
     *
     * @param deptRoleBo 部门角色查询参数
     * @return 是否成功
     */
    @GetMapping("deptRole")
    @ApiOperation("分页获取部门角色")
    public CommonResult<PagePlus<UmsDeptRole, DeptRoleVo>> getDeptRoleList(
            @RequestBody final DeptRoleBo deptRoleBo
    ) {
        return CommonResult.success(deptRoleService.pageVo(PageUtils.buildPagePlus(), deptRoleBo));
    }

    /**
     * 增加部门角色.
     *
     * @param deptRoleBo 部门角色操作对象
     * @return 是否成功
     */
    @PostMapping("deptRole")
    @ApiOperation("增加部门角色")
    public CommonResult<Void> addDeptRole(
            @Validated(ValidationGroups.Add.class)
            @RequestBody final DeptRoleBo deptRoleBo
    ) {
        return toAjax(deptRoleService.insertByBo(deptRoleBo));
    }

    /**
     * 修改部门角色.
     *
     * @param deptRoleBo 部门角色操作对象
     * @return 是否成功
     */
    @PutMapping("deptRole")
    @ApiOperation("修改部门角色")
    public CommonResult<Void> editDeptRole(
            @Validated(ValidationGroups.Edit.class)
            @RequestBody final DeptRoleBo deptRoleBo
    ) {
        return toAjax(deptRoleService.updateByBo(deptRoleBo));
    }


    /**
     * 删除部门角色.
     *
     * @param id 部门角色id
     * @return 是否成功
     */
    @DeleteMapping("deptRole/{id}")
    @ApiOperation("删除部门角色")
    public CommonResult<Void> delDeptRole(@PathVariable final Long id) {
        return toAjax(deptRoleService.deleteWithValidById(id));
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
        return CommonResult.success(umsMenuService.getDeptRolePerm(deptRoleId));

    }
}
