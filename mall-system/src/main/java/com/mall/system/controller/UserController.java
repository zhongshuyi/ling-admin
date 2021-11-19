package com.mall.system.controller;

import com.mall.common.core.controller.BaseController;
import com.mall.common.core.domain.CommonResult;
import com.mall.common.core.domain.PageInfo;
import com.mall.common.core.mybatisplus.util.PageUtils;
import com.mall.common.core.validate.ValidationGroups;
import com.mall.system.bo.UserBo;
import com.mall.system.service.IUmsAdminService;
import com.mall.system.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理操作.
 *
 * @author 钟舒艺
 **/
@Slf4j
@RestController
@Api(tags = "用户管理操作")
@RequestMapping("/system/user")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserController extends BaseController {

    /**
     * 用户服务类.
     */
    private final transient IUmsAdminService umsAdminService;

    /**
     * 分页获取用户列表.
     *
     * @param user 用户查询条件
     * @return 分页后信息
     */
    @GetMapping
    @ApiOperation(value = "分页获取用户列表")
    public CommonResult<PageInfo<UserVo>> getUserList(
            @Validated(ValidationGroups.Query.class) final UserBo user) {
        return CommonResult.success(
                PageUtils.buildPageInfo(
                        umsAdminService.pageVo(PageUtils.buildPagePlus(), user)
                )
        );
    }

    /**
     * 增加用户接口.
     *
     * @param user 用户信息
     * @return 是否成功
     */
    @PostMapping
    @ApiOperation("增加用户")
    public CommonResult<Void> addUser(
            @Validated({ValidationGroups.Add.class}) final UserBo user) {
        return toAjax(umsAdminService.insertByBo(user));
    }

    /**
     * 更改用户.
     *
     * @param user 用户信息
     * @return 是否成功
     */
    @PutMapping
    @ApiOperation("更改用户")
    public CommonResult<Void> edit(
            @Validated({ValidationGroups.Edit.class}) final UserBo user) {
        return toAjax(umsAdminService.updateByBo(user));
    }

    /**
     * 删除用户.
     *
     * @param id 用户id
     * @return 是否删除成功
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除用户")
    public CommonResult<Void> del(@PathVariable final Long id) {
        return toAjax(umsAdminService.deleteWithValidById(id));
    }


}
