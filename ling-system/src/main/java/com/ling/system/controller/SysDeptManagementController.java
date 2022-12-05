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
import com.ling.system.dto.SysAdminDTO;
import com.ling.system.dto.SysDeptRoleDTO;
import com.ling.system.entity.SysAdmin;
import com.ling.system.entity.SysDept;
import com.ling.system.entity.SysDeptRole;
import com.ling.system.mapper.SysDeptMapper;
import com.ling.system.security.model.LoginUserInfo;
import com.ling.system.service.ISysAdminService;
import com.ling.system.service.ISysDeptRoleService;
import com.ling.system.service.ISysDeptService;
import com.ling.system.utils.SecurityUtils;
import com.ling.system.vo.SysAdminVO;
import com.ling.system.vo.SysDeptRoleVO;
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
 * 上级管理部门.
 *
 * @author 钟舒艺
 * @since 2022-10-30 21:19
 **/
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/deptManagement")
public class SysDeptManagementController extends BaseController {

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
    public CommonResult<List<Tree<Long>>> getDepts() {
        LoginUserInfo loginUserInfo = SecurityUtils.getLoginUserInfo();
        SysAdmin sysAdmin = (SysAdmin) loginUserInfo.getUser();
        if (Boolean.TRUE.equals(loginUserInfo.getIsAdmin())) {
            return CommonResult.success(
                    SysDeptController.buildDeptTree(
                            sysDeptService.list(Wrappers.<SysDept>lambdaQuery().orderByAsc(SysDept::getOrderNo)),
                            0L
                    ));
        }
        if (!sysAdmin.getUserIdentity().equals(1)) {
            throw new BusinessErrorException(HttpStatus.HTTP_FORBIDDEN, "用户不是上级");
        }
        if (CharSequenceUtil.isEmpty(sysAdmin.getDepartIds())) {
            return CommonResult.success(CollUtil.newArrayList());
        }
        final long[] parentIds = CharSequenceUtil.splitToLong(sysAdmin.getDepartIds(), ",");
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
     * @param adminDTO 部门id跟用户姓名信息查询条件
     * @return 用户列表
     */
    @GetMapping("userList")
    public CommonResult<PageInfo<SysAdminVO>> getUserList(final SysAdminDTO adminDTO) {
        LoginUserInfo loginUserInfo = SecurityUtils.getLoginUserInfo();
        SysAdmin sysAdmin = (SysAdmin) loginUserInfo.getUser();
        if (!sysAdmin.getUserIdentity().equals(1)) {
            throw new BusinessErrorException(HttpStatus.HTTP_FORBIDDEN, "用户不是上级");
        }
        if (CharSequenceUtil.isEmpty(sysAdmin.getDepartIds())) {
            return CommonResult.success(PageUtils.buildPageInfo(new PagePlus<SysDept, SysAdminVO>()));
        }
        // 获取管理的直接部门id
        final long[] parentIds = CharSequenceUtil.splitToLong(sysAdmin.getDepartIds(), ",");
        adminDTO.setDeptIds(Arrays.stream(parentIds).boxed().collect(Collectors.toSet()));
        return CommonResult.success(
                PageUtils.buildPageInfo(
                        sysAdminService.listSysAdminPage(PageUtils.buildPagePlus(), adminDTO)));
    }

    /**
     * 添加已有用户进部门.
     *
     * @param userId 用户id
     * @param deptId 部门id
     * @return 是否成功
     */
    @PostMapping("addExistUser")
    public CommonResult<Void> addExistUser(
            final Long userId,
            final Long deptId) {
        return toAjax(sysDeptMapper.insertUserDept(userId, CollUtil.newHashSet(deptId)));
    }

    /**
     * 分页获取部门角色.
     *
     * @param deptRoleDTO 部门角色查询参数
     * @return 是否成功
     */
    @GetMapping("deptRole")
    public CommonResult<PageInfo<SysDeptRoleVO>> getDeptRoleList(
            final SysDeptRoleDTO deptRoleDTO) {
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
    public CommonResult<List<SysDeptRoleVO>> getDeptRoleList(@PathVariable final Long deptId) {
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
    public CommonResult<Void> addDeptRole(
            @Validated(ValidationGroups.ADD) @RequestBody final SysDeptRoleDTO deptRoleDTO) {
        return toAjax(deptRoleService.saveByDTO(deptRoleDTO));
    }

    /**
     * 修改部门角色.
     *
     * @param deptRoleDTO 部门角色操作对象
     * @return 是否成功
     */
    @PutMapping("deptRole")
    public CommonResult<Void> editDeptRole(
            @Validated(ValidationGroups.EDIT) @RequestBody final SysDeptRoleDTO deptRoleDTO) {
        return toAjax(deptRoleService.updateByDTO(deptRoleDTO));
    }

    /**
     * 删除部门角色.
     *
     * @param id 部门角色id
     * @return 是否成功
     */
    @DeleteMapping("deptRole/{id}")
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
    public CommonResult<Void> setDeptRoleToUser(
            final Long userId,
            final Long deptId,
            final Long[] deptRoleIds) {
        SysDeptManagementController.log.error(userId.toString());
        SysDeptManagementController.log.error(deptId.toString());
        SysDeptManagementController.log.error(Arrays.toString(deptRoleIds));
        return CommonResult.success();
    }
}
