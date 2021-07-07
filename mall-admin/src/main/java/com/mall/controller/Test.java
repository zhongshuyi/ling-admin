package com.mall.controller;

import com.mall.model.UmsAdmin;
import com.mall.service.IUmsAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 钟舒艺
 * @date 2021-06-20-12:34
 **/
@RequestMapping("test")
@RestController
@Api(tags = "后台用户管理")
@Slf4j
public class Test {

    final
    IUmsAdminService umsAdminService;

    public Test(IUmsAdminService umsAdminService) {
        this.umsAdminService = umsAdminService;
    }


    @GetMapping
    @ApiOperation("查询后台用户列表")
    @PreAuthorize("@ss.hasPermi('usm:user:select')")
    public List<UmsAdmin> test(){
        log.info("info");
        log.debug("debug");
        log.error("error");
        log.warn("warn");
        log.trace("trace");
        return umsAdminService.list();
    }
}
