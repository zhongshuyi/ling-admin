package com.ling.dome.controller;

import com.ling.common.core.controller.BaseController;
import com.ling.common.core.domain.CommonResult;
import com.ling.common.core.domain.PageInfo;
import com.ling.common.core.mybatisplus.util.PageUtils;
import com.ling.common.core.validate.ValidationGroups;
import com.ling.dome.dto.TestDomeDTO;
import com.ling.dome.service.TestDomeService;
import com.ling.dome.vo.TestDomeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
 * 测试借口.
 *
 * @author 钟舒艺
 **/
@Slf4j
@RestController
@Api(tags = "示例dome")
@RequiredArgsConstructor
@RequestMapping("/dome")
public class TestController extends BaseController {

    /**
     * 服务层.
     */
    private final TestDomeService testDomeService;

    /**
     * 查询数据.
     *
     * @param bo 查询条件
     * @return 返回数据.
     */
    @GetMapping
    @ApiOperation("数据权限条件查询数据")
    public CommonResult<List<TestDomeVo>> getList(final TestDomeDTO bo) {
        return CommonResult.success(testDomeService.listVoByDTO(bo));
    }


    /**
     * 分页数据展示.
     *
     * @param bo 查询条件
     * @return 分页后数据
     */
    @GetMapping("page")
    @ApiOperation("加上数据权限的分页示例数据")
    public CommonResult<PageInfo<TestDomeVo>> pageList(final TestDomeDTO bo) {
        return CommonResult.success(PageUtils.buildPageInfo(testDomeService.pageVo(PageUtils.buildPagePlus(), bo)));
    }


    /**
     * 根据id获取数据.
     *
     * @param id id
     * @return 单个.
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id获取示例数据")
    public CommonResult<TestDomeVo> getOneById(@PathVariable final Long id) {
        return CommonResult.success(testDomeService.getVoById(id));
    }


    /**
     * 新增示例数据.
     *
     * @param domeDTO 数据传输对象
     * @return 是否成功
     */
    @PostMapping
    @ApiOperation("新增示例数据")
    public CommonResult<Void> add(@RequestBody @Validated({ValidationGroups.Add.class}) final TestDomeDTO domeDTO) {
        return toAjax(testDomeService.insertByDTO(domeDTO));
    }

    /**
     * 修改示例数据.
     *
     * @param domeDTO 数据传输对象
     * @return 是否成功
     */
    @PutMapping
    @ApiOperation("修改示例数据")
    public CommonResult<Void> edit(@RequestBody @Validated({ValidationGroups.Edit.class}) final TestDomeDTO domeDTO) {
        return toAjax(testDomeService.updateByDTO(domeDTO));
    }

    /**
     * 删除示例数据.
     *
     * @param id 主键id
     * @return 是否成功
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除示例数据")
    public CommonResult<Void> del(@PathVariable final Long id) {
        return toAjax(testDomeService.removeById(id));
    }
}
