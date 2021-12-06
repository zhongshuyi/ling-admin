package com.mall.dome.controller;

import com.mall.common.core.domain.CommonResult;
import com.mall.dome.service.TestDomeService;
import com.mall.dome.vo.TestDomeVo;
import io.swagger.annotations.Api;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试借口.
 *
 * @author 钟舒艺
 **/
@Slf4j
@RestController
@Api(tags = "测试接口")
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    /**
     * 服务层.
     */
    private final transient TestDomeService testDomeService;

    /**
     * 测试接口.
     *
     * @return 返回数据.
     */
    @GetMapping
    public CommonResult<List<TestDomeVo>> getList() {
        return CommonResult.success(testDomeService.listVo());
    }
}
