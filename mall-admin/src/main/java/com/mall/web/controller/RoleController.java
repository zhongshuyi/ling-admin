package com.mall.web.controller;

import com.mall.common.core.domain.CommonResult;
import com.mall.common.core.domain.PageInfo;
import com.mall.common.core.util.PageUtils;
import com.mall.common.core.validate.ValidationGroups;
import com.mall.system.bo.RoleBo;
import com.mall.system.service.IUmsRoleService;
import com.mall.system.vo.RoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
public class RoleController {

    private final IUmsRoleService umsRoleService;

    @GetMapping
    @ApiOperation("分页获取角色列表")
    public CommonResult<PageInfo<RoleVo>> getList() {
        return CommonResult.success(PageUtils.buildPageInfo(umsRoleService.pageVo(PageUtils.buildPagePlus())));
    }


    @PostMapping
    @ApiOperation("添加角色")
    public CommonResult add(@Validated(ValidationGroups.Add.class) @RequestBody RoleBo role) {
        System.out.println(role);
        return CommonResult.success();
    }

}
