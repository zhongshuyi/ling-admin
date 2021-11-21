package com.mall.system.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mall.common.core.controller.BaseController;
import com.mall.common.core.domain.CommonResult;
import com.mall.common.core.validate.ValidationGroups;
import com.mall.system.bo.DeptBo;
import com.mall.system.entity.UmsDept;
import com.mall.system.entity.UmsMenu;
import com.mall.system.service.IUmsDeptService;
import com.mall.system.service.IUmsMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 部门操作.
 *
 * @author 钟舒艺
 **/
@RestController
@Api(tags = "部门操作")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/system/dept")
@Slf4j
@Getter
public class DeptController extends BaseController {

    /**
     * 部门操作服务.
     */
    private final transient IUmsDeptService umsDeptService;

    /**
     * 菜单权限操作服务.
     */
    private final transient IUmsMenuService umsMenuService;

    /**
     * 获取部门树.
     *
     * @return 部门树结构
     */
    @GetMapping
    @ApiOperation(value = "获取部门列表")
    public CommonResult<List<Tree<Long>>> getDeptVoList() {
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setWeightKey("orderNo");
        treeNodeConfig.setNameKey("deptName");
        return CommonResult.success(TreeUtil.build(
                umsDeptService.list(
                        Wrappers.<UmsDept>lambdaQuery().orderByAsc(UmsDept::getOrderNo)),
                0L,
                treeNodeConfig,
                (treeNode, tree) -> {
                    tree.setId(treeNode.getId());
                    tree.setParentId(treeNode.getParentId());
                    tree.setName(treeNode.getDeptName());
                    tree.setWeight(treeNode.getOrderNo());
                }));
    }

    /**
     * 增加部门.
     *
     * @param addBo 部门对象
     * @return 是否成功
     */
    @PostMapping
    @ApiOperation(value = "增加部门")
    public CommonResult<Void> addDept(
            @Validated(ValidationGroups.Add.class)
            @RequestBody final DeptBo addBo) {
        if (umsDeptService.checkDeptUnique(addBo)) {
            return CommonResult.failed("部门'" + addBo.getDeptName() + "' 已存在");
        } else {
            return toAjax(umsDeptService.addDept(addBo));
        }
    }

    /**
     * 删除部门.
     *
     * @param id 部门id
     * @return 是否成功
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除部门")
    public CommonResult<Void> deleteById(@PathVariable final Long id) {
        return toAjax(umsDeptService.deleteById(id));
    }

    /**
     * 根据id更改部门信息.
     *
     * @param dept 部门对象
     * @return 是否成功
     */
    @PutMapping
    @ApiOperation(value = "更改部门")
    public CommonResult<Void> editDept(@RequestBody final DeptBo dept) {
        if (umsDeptService.checkDeptUnique(dept)) {
            return CommonResult.failed("部门'" + dept.getDeptName() + "' 已存在");
        } else {
            return toAjax(umsDeptService.updateById(BeanUtil.toBean(dept, UmsDept.class)));
        }
    }

    /**
     * 检查部门是否有下级部门.
     *
     * @param id 菜单id
     * @return 是否有子菜单
     */
    @ApiOperation("检查菜单是否有子菜单")
    @GetMapping("checkMenuHasChildren/{id}")
    public CommonResult<Boolean> checkMenuHasChildren(@PathVariable final Long id) {
        return CommonResult.success(CollUtil.isNotEmpty(umsDeptService.getDeptChildren(id)));
    }

    /**
     * 获取部门详细信息.
     *
     * @param id 部门id
     * @return 部门详细信息
     */
    @ApiOperation("获取部门详细信息")
    @GetMapping("/{id}")
    public CommonResult<UmsDept> getDept(@PathVariable final Long id) {
        return CommonResult.success(umsDeptService.getById(id));
    }

    /**
     * 获取部门权限id集合.
     *
     * @param id 部门id
     * @return 拥有的权限集合
     */
    @ApiOperation("获取部门权限")
    @GetMapping("DeptPerm/{id}")
    public CommonResult<Set<Long>> getDeptPerm(@PathVariable final Long id) {
        return CommonResult.success(
                umsMenuService
                        .getDeptPerm(id)
                        .stream()
                        .map(UmsMenu::getId)
                        .collect(Collectors.toSet()));
    }

    /**
     * 更改部门的权限.
     *
     * @param deptId 部门id
     * @param newIds 新的权限id集合
     * @return 是否更改成功
     */
    @ApiOperation("更改部门权限")
    @PutMapping("DeptPerm/{deptId}")
    public CommonResult<Void> setDeptPerm(
            @PathVariable final Long deptId,
            @RequestBody final Set<Long> newIds) {
        Set<Long> oldIds = umsMenuService
                .getDeptPerm(deptId)
                .stream()
                .map(UmsMenu::getId)
                .collect(Collectors.toSet());
        Set<Long> result = new HashSet<>(oldIds);
        result.removeAll(newIds);
        final Boolean isSuccess = umsMenuService.removeDeptPerm(deptId, result);
        result.clear();
        result.addAll(newIds);
        result.removeAll(oldIds);
        return toAjax(umsMenuService.addDeptPerm(deptId, result) && isSuccess);
    }
}
