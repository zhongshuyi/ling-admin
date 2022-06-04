package com.ling.system.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ling.common.core.controller.BaseController;
import com.ling.common.core.domain.CommonResult;
import com.ling.common.core.domain.PageInfo;
import com.ling.common.core.mybatisplus.core.PagePlus;
import com.ling.common.core.mybatisplus.util.PageUtils;
import com.ling.common.core.validate.ValidationGroups;
import com.ling.common.exception.BusinessErrorException;
import com.ling.framework.model.AdminUserDetails;
import com.ling.framework.util.SecurityUtils;
import com.ling.system.dto.DeptRoleDTO;
import com.ling.system.dto.UserDTO;
import com.ling.system.entity.SysDept;
import com.ling.system.entity.SysDeptRole;
import com.ling.system.mapper.SysDeptMapper;
import com.ling.system.service.ISysAdminService;
import com.ling.system.service.ISysDeptRoleService;
import com.ling.system.service.ISysDeptService;
import com.ling.system.vo.DeptRoleVo;
import com.ling.system.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Arrays;
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
    private final ISysDeptService sysDeptService;

    /**
     * 权限菜单服务.
     */
    private final ISysAdminService sysAdminService;

    /**
     * 部门mapper.
     */
    private final SysDeptMapper sysDeptMapper;

    /**
     * 部门角色服务.
     */
    private final ISysDeptRoleService deptRoleService;

    /**
     * 获取用户管理的下级部门树结构.
     *
     * @return 下级部门
     */
    @GetMapping("list")
    @ApiOperation("获取管理的下级部门树结构")
    public CommonResult<List<Tree<Long>>> getDepts() {
        final AdminUserDetails adminUserDetails = SecurityUtils.getLoginUser();
        if (Boolean.TRUE.equals(adminUserDetails.getSysAdmin().getIsAdmin())) {
            return CommonResult.success(
                    DeptController.buildDeptTree(
                            sysDeptService.list(Wrappers.<SysDept>lambdaQuery().orderByAsc(SysDept::getOrderNo)),
                            0L
                    ));
        }
        if (!adminUserDetails.getSysAdmin().getUserIdentity().equals(1)) {
            throw new BusinessErrorException(HttpStatus.HTTP_FORBIDDEN, "用户不是上级");
        }
        if (CharSequenceUtil.isEmpty(adminUserDetails.getSysAdmin().getDepartIds())) {
            return CommonResult.success(CollUtil.newArrayList());
        }
        final long[] parentIds = CharSequenceUtil.splitToLong(adminUserDetails.getSysAdmin().getDepartIds(), ",");
        // 获取管理的直接部门
        final List<SysDept> manageList = sysDeptService.listByIds(
                Arrays.stream(parentIds).boxed().collect(Collectors.toList()));
        // 获取管理部门及下级部门
        final QueryWrapper<SysDept> queryWrapper = new QueryWrapper<>();
        manageList.forEach(p -> queryWrapper.or().likeRight("parent_list", p.getParentList()));
        final List<SysDept> subDepts = sysDeptService.list(queryWrapper);
        // 构成树结构
        final List<Tree<Long>> allList = new ArrayList<>();
        final TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setWeightKey("orderNo");
        treeNodeConfig.setNameKey("deptName");
        final Set<Long> parentIdSet = manageList.stream().map(SysDept::getParentId).collect(Collectors.toSet());
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
     * 获取管理部门的用户列表.
     *
     * @param user 部门id跟用户姓名信息查询条件
     * @return 用户列表
     */
    @GetMapping("userList")
    @ApiOperation("获取管理部门的用户列表")
    public CommonResult<PageInfo<UserVo>> getUserList(final UserDTO user) {
        final AdminUserDetails adminUserDetails = SecurityUtils.getLoginUser();
        if (!adminUserDetails.getSysAdmin().getUserIdentity().equals(1)) {
            throw new BusinessErrorException(HttpStatus.HTTP_FORBIDDEN, "用户不是上级");
        }
        if (CharSequenceUtil.isEmpty(adminUserDetails.getSysAdmin().getDepartIds())) {
            return CommonResult.success(PageUtils.buildPageInfo(new PagePlus<SysDept, UserVo>()));
        }
        // 获取管理的直接部门id
        final long[] parentIds = CharSequenceUtil.splitToLong(adminUserDetails.getSysAdmin().getDepartIds(), ",");
        user.setDeptIds(Arrays.stream(parentIds).boxed().collect(Collectors.toSet()));
        return CommonResult.success(
                PageUtils.buildPageInfo(
                        sysAdminService.selectUserListPage(PageUtils.buildPagePlus(), user)));
    }

    /**
     * 添加已有用户进部门.
     *
     * @param userId 用户id
     * @param deptId 部门id
     * @return 是否成功
     */
    @PostMapping("addExistUser")
    @ApiOperation("部门增加已有用户")
    public CommonResult<Void> addExistUser(
            final Long userId,
            final Long deptId) {
        return toAjax(sysDeptMapper.addUserDept(userId, CollUtil.newHashSet(deptId)));
    }

    /**
     * 分页获取部门角色.
     *
     * @param deptRoleDTO 部门角色查询参数
     * @return 是否成功
     */
    @GetMapping("deptRole")
    @ApiOperation("分页获取部门角色")
    public CommonResult<PageInfo<DeptRoleVo>> getDeptRoleList(
            final DeptRoleDTO deptRoleDTO) {
        if (deptRoleDTO.getDeptId() == null || deptRoleDTO.getDeptId() == -1L) {
            return CommonResult.success(new PageInfo<>(0, CollUtil.newArrayList()));
        }
        return CommonResult.success(
                PageUtils.buildPageInfo(deptRoleService.pageVo(PageUtils.buildPagePlus(), deptRoleDTO)));
    }

    /**
     * 获取该部门全部的部门角色.
     *
     * @param deptId 部门id
     * @return 部门 角色集合
     */
    @GetMapping("deptRole/{deptId}")
    @ApiOperation("获取全部部门角色")
    public CommonResult<List<DeptRoleVo>> getDeptRoleList(@PathVariable final Long deptId) {
        return CommonResult.success(
                deptRoleService.listVo(Wrappers.<SysDeptRole>lambdaQuery().eq(SysDeptRole::getDeptId, deptId)));
    }

    /**
     * 增加部门角色.
     *
     * @param deptRoleDTO 部门角色操作对象
     * @return 是否成功
     */
    @PostMapping("deptRole")
    @ApiOperation("增加部门角色")
    public CommonResult<Void> addDeptRole(
            @Validated(ValidationGroups.Add.class) @RequestBody final DeptRoleDTO deptRoleDTO) {
        return toAjax(deptRoleService.insertByDTO(deptRoleDTO));
    }

    /**
     * 修改部门角色.
     *
     * @param deptRoleDTO 部门角色操作对象
     * @return 是否成功
     */
    @PutMapping("deptRole")
    @ApiOperation("修改部门角色")
    public CommonResult<Void> editDeptRole(
            @Validated(ValidationGroups.Edit.class) @RequestBody final DeptRoleDTO deptRoleDTO) {
        return toAjax(deptRoleService.updateByDTO(deptRoleDTO));
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
     * 修改用户的部门角色.
     *
     * @param userId 用户id
     * @param deptId 部门id
     * @return 是否成功
     */
    @GetMapping("userDeptRole")
    @ApiOperation("查询用户的部门角色")
    public CommonResult<Set<Long>> getDeptRoleToUser(
            final Long userId,
            final Long deptId) {
        return CommonResult.success(deptRoleService.getDeptRoleIdsByUserId(deptId, userId));
    }


    /**
     * 修改用户的部门角色.
     *
     * @param userId      用户id
     * @param deptId      部门id
     * @param deptRoleIds 部门角色id集合
     * @return 是否成功
     */
    @PutMapping("userDeptRole")
    @ApiOperation("修改用户的部门角色")
    public CommonResult<Void> setDeptRoleToUser(
            final Long userId,
            final Long deptId,
            final Long[] deptRoleIds) {
        DeptManagementController.log.error(userId.toString());
        DeptManagementController.log.error(deptId.toString());
        DeptManagementController.log.error(Arrays.toString(deptRoleIds));
        return CommonResult.success();
    }

}
