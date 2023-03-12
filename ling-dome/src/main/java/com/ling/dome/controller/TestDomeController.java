package com.ling.dome.controller;

import com.ling.common.core.controller.BaseController;
import com.ling.common.core.domain.CommonResult;
import com.ling.common.core.domain.PageInfo;
import com.ling.common.core.mybatisplus.util.PageUtils;
import com.ling.common.core.validate.ValidationGroups;
import com.ling.dome.convert.TestDomeConvert;
import com.ling.dome.dto.TestDomeDTO;
import com.ling.dome.service.ITestDomeService;
import com.ling.dome.vo.TestDomeVO;
import io.swagger.v3.oas.annotations.tags.Tag;
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
 * <p>
 * 测试表 前端控制器.
 * </p>
 *
 * @author 钟舒艺
 * @since 2023-03-07
 */
@Slf4j
@RestController
@Tag(name = "测试表")
@RequiredArgsConstructor
@RequestMapping("/dome/testDome")
public class TestDomeController extends BaseController {

    private final ITestDomeService testDomeService;

    /**
     * 分页获取测试表信息列表.
     *
     * @param testDomeDTO 查询条件
     * @return 分页后数据
     */
    @GetMapping
    public CommonResult<PageInfo<TestDomeVO>> getUserList(
            @Validated(ValidationGroups.Query.class) final TestDomeDTO testDomeDTO) {

        return CommonResult.success(
                PageUtils.buildPageInfo(
                        testDomeService.pageVo(
                                PageUtils.buildPagePlus(),
                                testDomeDTO
                        )));
    }


    /**
     * 获取单个测试表详细信息.
     *
     * @param id 商品id
     * @return 测试表信息
     */
    @GetMapping("/{id}")
    public CommonResult<TestDomeVO> getById(@PathVariable final Long id) {
        return CommonResult.success(testDomeService.getVoById(id));
    }


    /**
     * 添加测试表.
     *
     * @param dto 测试表信息
     * @return {@link CommonResult}<{@link Void}> 是否成功
     */
    @PostMapping
    public CommonResult<Void> add(@Validated(ValidationGroups.Add.class) @RequestBody final TestDomeDTO dto) {
        return toAjax(testDomeService.saveByDTO(dto, TestDomeConvert.class));
    }

    /**
     * 编辑测试表信息.
     *
     * @param dto 测试表dto
     * @return {@link CommonResult}<{@link Void}>
     */
    @PutMapping
    public CommonResult<Void> edit(@Validated(ValidationGroups.Edit.class) @RequestBody final TestDomeDTO dto) {
        return toAjax(testDomeService.updateByDTO(dto));
    }


    /**
     * 删除测试表信息.
     *
     * @param id id
     * @return {@link CommonResult}<{@link Void}> 是否成功
     */
    @DeleteMapping("/{id}")
    public CommonResult<Void> del(@PathVariable final Long id) {
        return toAjax(testDomeService.deleteWithValidById(id));
    }


}

