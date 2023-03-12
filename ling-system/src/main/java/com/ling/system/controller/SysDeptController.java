package com.ling.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ling.common.constant.PermissionCodeConstant;
import com.ling.common.core.controller.BaseController;
import com.ling.common.core.domain.CommonResult;
import com.ling.common.core.domain.model.SysDept;
import com.ling.common.core.validate.ValidationGroups;
import com.ling.system.dto.SysDeptDTO;
import com.ling.system.service.ISysDeptService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
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
 * 部门管理.
 *
 * @author 钟舒艺
 * @since 2022-10-30 21:04
 **/
@Slf4j
@RestController
@Tag(name = "部门管理")
@RequiredArgsConstructor
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController {

    /**
     * 部门操作服务.
     */
    private final ISysDeptService sysDeptService;

    /**
     * 构建部门树结构.
     *
     * @param deptVoList 部门列表
     * @param parentId   父id
     * @return 部门树结构
     */
    public static List<Tree<Long>> buildDeptTree(
            final List<SysDept> deptVoList,
            final Long parentId) {
        final TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setWeightKey("orderNo");
        treeNodeConfig.setNameKey("deptName");
        return TreeUtil.build(
                deptVoList,
                parentId,
                treeNodeConfig, (treeNode, tree) -> {
                    tree.setId(treeNode.getId());
                    tree.setParentId(treeNode.getParentId());
                    tree.setName(treeNode.getDeptName());
                    tree.setWeight(treeNode.getOrderNo());
                    tree.putExtra("parentList", treeNode.getParentList());
                }
        );
    }

    /**
     * 获取部门树.
     *
     * @return 部门树结构
     */
    @GetMapping
    @SaCheckPermission(PermissionCodeConstant.SYS_DEPT_LIST)
    public CommonResult<List<Tree<Long>>> getDeptVoList() {
        return CommonResult.success(
                buildDeptTree(sysDeptService.list(Wrappers.<SysDept>lambdaQuery().orderByAsc(SysDept::getOrderNo)),
                        0L
                ));
    }

    /**
     * 获取部门详细信息.
     *
     * @param id 部门id
     * @return 部门详细信息
     */
    @GetMapping("/{id}")
    @SaCheckPermission(PermissionCodeConstant.SYS_DEPT_QUERY)
    public CommonResult<SysDept> getDept(@PathVariable final Long id) {
        return CommonResult.success(sysDeptService.getById(id));
    }

    /**
     * 增加部门.
     *
     * @param addBo 部门对象
     * @return 是否成功
     */
    @PostMapping
    @SaCheckPermission(PermissionCodeConstant.SYS_DEPT_ADD)
    public CommonResult<Void> addDept(
            @Validated(ValidationGroups.Add.class)
            @RequestBody final SysDeptDTO addBo
    ) {
        if (Boolean.TRUE.equals(sysDeptService.checkDeptUnique(addBo))) {
            return CommonResult.failed("部门'" + addBo.getDeptName() + "' 已存在");
        } else {
            return toAjax(sysDeptService.addDept(addBo));
        }
    }

    /**
     * 删除部门.
     *
     * @param id 部门id
     * @return 是否成功
     */
    @DeleteMapping("/{id}")
    @SaCheckPermission(PermissionCodeConstant.SYS_DEPT_DELETE)
    public CommonResult<Void> deleteById(@PathVariable final Long id) {
        return toAjax(sysDeptService.deleteById(id));
    }

    /**
     * 根据id更改部门信息.
     *
     * @param dept 部门对象
     * @return 是否成功
     */
    @PutMapping
    @SaCheckPermission(PermissionCodeConstant.SYS_DEPT_EDIT)
    public CommonResult<Void> editDept(@RequestBody final SysDeptDTO dept) {
        if (Boolean.TRUE.equals(sysDeptService.checkDeptUnique(dept))) {
            return CommonResult.failed("部门'" + dept.getDeptName() + "' 已存在");
        } else {
            return toAjax(sysDeptService.updateById(BeanUtil.toBean(dept, SysDept.class)));
        }
    }

}
