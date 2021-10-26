package com.mall.web.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.mall.common.core.controller.BaseController;
import com.mall.common.core.domain.CommonResult;
import com.mall.common.core.domain.entity.UmsDept;
import com.mall.common.core.domain.entity.UmsMenu;
import com.mall.system.bo.add.DeptAddBo;
import com.mall.system.service.IUmsDeptService;
import com.mall.system.service.IUmsMenuService;
import com.mall.system.vo.DeptVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 钟舒艺
 * @date 2021-10-09-10:33
 **/
@RestController
@Api(tags = "部门操作")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RequestMapping("/system/dept")
@Slf4j
public class DeptController extends BaseController {

    public final IUmsDeptService umsDeptService;

    public final IUmsMenuService umsMenuService;

    @GetMapping
    @ApiOperation(value = "获取部门列表")
    public CommonResult<List<DeptVo>> getDeptVoList() {
        return CommonResult.success(umsDeptService.getDeptVoList());
    }

    @PostMapping
    @ApiOperation(value = "增加部门")
    public CommonResult addDept(@RequestBody DeptAddBo addBo) {
        if (umsDeptService.checkDeptUnique(addBo)) {
            return CommonResult.failed("部门'" + addBo.getDeptName() + "' 已存在");
        } else {
            return toAjax(umsDeptService.addDept(addBo));
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除部门")
    public CommonResult deleteById(@PathVariable Long id) {
        return toAjax(umsDeptService.deleteById(id));
    }

    @PutMapping
    @ApiOperation(value = "更改部门")
    public CommonResult editDept(@RequestBody DeptAddBo dept) {
        if (umsDeptService.checkDeptUnique(dept)) {
            return CommonResult.failed("部门'" + dept.getDeptName() + "' 已存在");
        } else {
            return toAjax(umsDeptService.updateById(BeanUtil.toBean(dept, UmsDept.class)));
        }
    }

    @ApiOperation("检查菜单是否有子菜单")
    @GetMapping("checkMenuHasChildren/{id}")
    public CommonResult checkMenuHasChildren(@PathVariable Long id) {
        return CommonResult.success(CollUtil.isNotEmpty(umsDeptService.getDeptChildren(id)));
    }

    @ApiOperation("获取部门详细信息")
    @GetMapping("/{id}")
    public CommonResult getDept(@PathVariable Long id) {
        return CommonResult.success(umsDeptService.getById(id));
    }

    @ApiOperation("获取部门权限")
    @GetMapping("DeptPerm/{id}")
    public CommonResult<Set<Long>> getDeptPerm(@PathVariable Long id) {
        return CommonResult.success(umsMenuService.getDeptPerm(id).stream().map(UmsMenu::getId).collect(Collectors.toSet()));
    }

    @ApiOperation("更改部门权限")
    @PutMapping("DeptPerm/{deptId}")
    public CommonResult setDeptPerm(@PathVariable Long deptId,@RequestBody Set<Long> newIds){

        Set<Long> oldIds = umsMenuService.getDeptPerm(deptId).stream().map(UmsMenu::getId).collect(Collectors.toSet());
        Set<Long> result = new HashSet<>(oldIds);
        result.removeAll(newIds);
        System.out.println("删除的值"+result);
        Boolean isSuccess = umsMenuService.removeDeptPerm(deptId,result);
        result.clear();
        result.addAll(newIds);
        result.removeAll(oldIds);
        System.out.println("新增的值"+result);
        return toAjax(umsMenuService.addDeptPerm(deptId,result)&&isSuccess);
    }
}
