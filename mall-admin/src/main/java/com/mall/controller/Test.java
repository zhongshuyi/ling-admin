package com.mall.controller;

import com.mall.model.UmsAdmin;
import com.mall.service.IUmsAdminService;
import com.mall.service.IUmsMenuService;
import com.mall.util.MenuUtil;
import com.mall.vo.RouterVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 钟舒艺
 * @date 2021-06-20-12:34
 **/
@RequestMapping("test")
@RestController
@Api(tags = "测试")
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class Test {

    private final IUmsAdminService umsAdminService;

    private final IUmsMenuService umsMenuService;

    @GetMapping
    @ApiOperation("测试")
    public List<RouterVo> test(){
        return MenuUtil.buildMenus(umsMenuService.selectMenuListAll(),0L);
    }
}
