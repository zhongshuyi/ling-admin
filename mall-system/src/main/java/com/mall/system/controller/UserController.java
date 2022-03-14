package com.mall.system.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpStatus;
import com.mall.common.constant.GlobalConstants;
import com.mall.common.core.controller.BaseController;
import com.mall.common.core.domain.CommonResult;
import com.mall.common.core.domain.PageInfo;
import com.mall.common.core.mybatisplus.util.PageUtils;
import com.mall.common.core.validate.ValidationGroups;
import com.mall.common.exception.BusinessErrorException;
import com.mall.framework.model.AdminUserDetails;
import com.mall.framework.util.JwtTokenUtil;
import com.mall.framework.util.SecurityUtils;
import com.mall.system.bo.UserBo;
import com.mall.system.entity.UmsAdmin;
import com.mall.system.service.IUmsAdminService;
import com.mall.system.service.IUmsDeptService;
import com.mall.system.service.IUmsRoleService;
import com.mall.system.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.HashSet;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户管理操作.
 *
 * @author 钟舒艺
 **/
@Slf4j
@RestController
@Api(tags = "用户管理操作")
@RequestMapping("/system/user")
@RequiredArgsConstructor
public class UserController extends BaseController {

    /**
     * 用户服务类.
     */
    private final IUmsAdminService umsAdminService;

    /**
     * 部门服务.
     */
    private final IUmsDeptService umsDeptService;

    /**
     * 角色服务.
     */
    private final IUmsRoleService umsRoleService;

    /**
     * jwt工具类.
     */
    private final JwtTokenUtil jwtTokenUtil;

    /**
     * 默认角色id.
     */
    @Value("${app.default-roleId}")
    private Long defaultRoleId;

    /**
     * 默认部门id.
     */
    @Value("${app.default-deptId}")
    private Long defaultDeptId;

    /**
     * 分页获取用户列表.
     *
     * @param user 用户查询条件
     * @return 分页后信息
     */
    @GetMapping
    @ApiOperation(value = "分页获取用户列表")
    public CommonResult<PageInfo<UserVo>> getUserList(@Validated(ValidationGroups.Query.class) final UserBo user) {
        return CommonResult.success(
                PageUtils.buildPageInfo(
                        umsAdminService.getUserListPage(PageUtils.buildPagePlus(), user)
                )
        );
    }

    /**
     * 获取单个用户详细信息.
     *
     * @param id 用户id
     * @return 用户vo
     */
    @GetMapping("/{id}")
    @ApiOperation("获取用户详细信息")
    public CommonResult<UserVo> getUserById(@PathVariable final Long id) {
        final UserVo userVo = BeanUtil.toBean(umsAdminService.getUmsAdminById(id), UserVo.class);
        userVo.setRoleIds(umsRoleService.selectRoleIdsByUserId(id));
        userVo.setDeptIds(umsDeptService.selectDeptIdsByUserId(id));
        return CommonResult.success(userVo);
    }

    /**
     * 增加用户接口.
     *
     * @param user 用户信息
     * @return 是否成功
     */
    @PostMapping
    @ApiOperation("增加用户")
    public CommonResult<Void> addUser(@Validated({ValidationGroups.Add.class}) @RequestBody final UserBo user) {
        if (!user.getPassword().equals(user.getPasswordRepeat())) {
            throw new BusinessErrorException(HttpStatus.HTTP_BAD_REQUEST, "两次密码不一样");
        }
        // 设置默认的角色与部门
        setDefaultDeptAndRole(user);
        final UmsAdmin umsAdmin = BeanUtil.toBean(user, UmsAdmin.class);
        // 检查用户名是否唯一
        if (Boolean.FALSE.equals(umsAdminService.checkUsernameUnique(umsAdmin))) {
            return CommonResult.failed(HttpStatus.HTTP_BAD_REQUEST, "用户名已存在");
        }
        umsAdmin.setPassword(SecurityUtils.encryptPassword(umsAdmin.getPassword()));
        boolean isSuccess = umsAdminService.save(umsAdmin);
        isSuccess = isSuccess && umsRoleService.setUserRole(umsAdmin.getId(), user.getRoleIds());
        isSuccess = isSuccess && umsDeptService.setUserDept(umsAdmin.getId(), user.getDeptIds());
        return toAjax(isSuccess);
    }


    /**
     * 检查用户名是否重复.
     *
     * @param username 用户id
     * @return 是否重复
     */
    @GetMapping("/checkUsernameUnique")
    @ApiOperation("检查用户名是否重复")
    public CommonResult<Boolean> checkUsernameUnique(final String username) {
        final UmsAdmin user = new UmsAdmin();
        user.setUsername(username);
        return CommonResult.success(umsAdminService.checkUsernameUnique(user));
    }


    /**
     * 更改用户.
     *
     * @param user    用户信息
     * @param request 请求信息
     * @return 是否成功
     */
    @PutMapping
    @ApiOperation("更改用户")
    public CommonResult<Void> edit(
            @Validated({ValidationGroups.Edit.class})
            @RequestBody final UserBo user,
            final HttpServletRequest request
    ) {
        // 设置默认的角色与部门
        setDefaultDeptAndRole(user);
        // 修改角色
        boolean isSuccess = umsRoleService.setUserRole(user.getId(), user.getRoleIds());
        // 修改用户部门
        isSuccess = isSuccess && umsDeptService.setUserDept(user.getId(), user.getDeptIds());
        isSuccess = isSuccess && umsAdminService.updateByBo(user);
        final AdminUserDetails adminUserDetails = jwtTokenUtil.getAdminUserDetails(request);
        // 如果修改的是本用户则更新信息
        if (isSuccess && adminUserDetails.getUmsAdmin().getId().equals(user.getId())) {
            adminUserDetails.setUmsAdmin(umsAdminService.getUmsAdminById(user.getId()));
            adminUserDetails.setDepts(umsDeptService.selectDeptListByUserId(user.getId()));
            adminUserDetails.setRoles(umsRoleService.selectRoleListByUserId(user.getId()));
            adminUserDetails.getRoles().forEach(r -> {
                if (r.getId().equals(GlobalConstants.SUPER_ADMIN_ROLE_ID)) {
                    adminUserDetails.getUmsAdmin().setIsAdmin(true);
                }
            });
            jwtTokenUtil.setUser(adminUserDetails);
        }
        return toAjax(isSuccess);
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
        final UmsAdmin user = new UmsAdmin();
        user.setId(id);
        user.setDelFlag(1);
        return toAjax(umsAdminService.updateById(user));
    }


    /**
     * 头像上传(自动删除之前的头像).
     *
     * @param id      用户id
     * @param file    头像文件
     * @param request 请求信息
     * @return 是否成功.
     */
    @ApiOperation("头像上传")
    @PostMapping("/avatar/{id}")
    public CommonResult<Void> uploadAvatar(
            @PathVariable final Long id,
            @RequestParam("file") final MultipartFile file,
            final HttpServletRequest request
    ) {
        final Boolean isSuccess =
                umsAdminService.uploadAvatar(
                        id,
                        file,
                        jwtTokenUtil.getAdminUserDetails(request).getUmsAdmin().getId());
        final AdminUserDetails adminUserDetails = jwtTokenUtil.getAdminUserDetails(request);
        if (Boolean.TRUE.equals(isSuccess) && adminUserDetails.getUmsAdmin().getId().equals(id)) {
            adminUserDetails.getUmsAdmin().setAvatar(umsAdminService.getUserAvatar(id));
            jwtTokenUtil.setUser(adminUserDetails);
        }
        return toAjax(isSuccess);
    }

    /**
     * 设置默认的部门与角色.
     *
     * @param user 用户操作对象
     */
    private void setDefaultDeptAndRole(final UserBo user) {
        if (CollUtil.isEmpty(user.getRoleIds())) {
            final HashSet<Long> roleIds = new HashSet<>(1);
            roleIds.add(defaultRoleId);
            user.setRoleIds(roleIds);
        }
        if (CollUtil.isEmpty(user.getDeptIds())) {
            final HashSet<Long> deptIds = new HashSet<>(1);
            deptIds.add(defaultDeptId);
            user.setDeptIds(deptIds);
        }
    }
}
