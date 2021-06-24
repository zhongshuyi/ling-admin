package com.mall.controller;

import com.mall.mapper.UmsAdminMapper;
import com.mall.model.UmsAdmin;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 钟舒艺
 * @date 2021-06-20-12:34
 **/
@RequestMapping("test")
@RestController
public class Test {

    @Autowired
    UmsAdminMapper umsAdminMapper;

    @GetMapping
    @ApiOperation("测试")
    public List<UmsAdmin> test(){
        return umsAdminMapper.selectList(null);
    }
}
