package com.mall.web.controller;

import com.mall.common.core.domain.CommonResult;
import com.mall.common.core.domain.entity.UmsRole;
import com.mall.system.service.IUmsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 钟舒艺
 * @date 2021-10-26-14:47
 **/
@Slf4j
@RestController
@Api(tags = "角色操作")
@RequestMapping("/system/role")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RoleController {

    private final IUmsRoleService umsRoleService;

    @GetMapping
    @ApiOperation("获取角色列表")
    public CommonResult<List<UmsRole>> getList() {
        return CommonResult.success(umsRoleService.list());
    }
}
