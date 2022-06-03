package com.ling.system.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpStatus;
import com.ling.common.constant.AppConstants;
import com.ling.common.core.controller.BaseController;
import com.ling.common.core.domain.CommonResult;
import com.ling.common.core.domain.PageInfo;
import com.ling.common.core.mybatisplus.util.PageUtils;
import com.ling.common.core.validate.ValidationGroups;
import com.ling.common.exception.BusinessErrorException;
import com.ling.framework.config.CustomConfig;
import com.ling.framework.model.AdminUserDetails;
import com.ling.framework.util.JwtTokenUtil;
import com.ling.framework.util.SecurityUtils;
import com.ling.system.dto.UserDTO;
import com.ling.system.entity.SysAdmin;
import com.ling.system.service.ISysAdminService;
import com.ling.system.service.ISysDeptService;
import com.ling.system.service.ISysRoleService;
import com.ling.system.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.HashSet;
import javax.servlet.http.HttpServletRequest;
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
     * 配置信息.
     */
    private final CustomConfig config;

    /**
     * 用户服务类.
     */
    private final ISysAdminService sysAdminService;

    /**
     * 部门服务.
     */
    private final ISysDeptService sysDeptService;

    /**
     * 角色服务.
     */
    private final ISysRoleService sysRoleService;

    /**
     * jwt工具类.
     */
    private final JwtTokenUtil jwtTokenUtil;

    /**
     * 分页获取用户列表.
     *
     * @param user 用户查询条件
     * @return 分页后信息
     */
    @GetMapping
    @ApiOperation(value = "分页获取用户列表")
    public CommonResult<PageInfo<UserVo>> getUserList(@Validated(ValidationGroups.Query.class) final UserDTO user) {
        return CommonResult.success(
                PageUtils.buildPageInfo(
                        sysAdminService.selectUserListPage(
                                PageUtils.buildPagePlus(),
                                user
                        )));
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
        final UserVo userVo = BeanUtil.toBean(sysAdminService.selectSysAdminById(id), UserVo.class);
        userVo.setRoleIds(sysRoleService.selectRoleIdsByUserId(id));
        userVo.setDeptIds(sysDeptService.selectDeptIdsByUserId(id));
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
    public CommonResult<Void> addUser(@Validated({ValidationGroups.Add.class}) @RequestBody final UserDTO user) {
        if (!user.getPassword().equals(user.getPasswordRepeat())) {
            throw new BusinessErrorException(HttpStatus.HTTP_BAD_REQUEST, "两次密码不一样");
        }
        // 设置默认的角色与部门
        setDefaultDeptAndRole(user);
        final SysAdmin sysAdmin = BeanUtil.toBean(user, SysAdmin.class);
        // 检查用户名是否唯一
        if (Boolean.FALSE.equals(sysAdminService.checkUsernameUnique(sysAdmin))) {
            return CommonResult.failed(HttpStatus.HTTP_BAD_REQUEST, "用户名已存在");
        }
        sysAdmin.setPassword(SecurityUtils.encryptPassword(sysAdmin.getPassword()));
        boolean isSuccess = sysAdminService.save(sysAdmin);
        isSuccess = isSuccess && sysRoleService.setUserRole(sysAdmin.getId(), user.getRoleIds());
        isSuccess = isSuccess && sysDeptService.setUserDept(sysAdmin.getId(), user.getDeptIds());
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
        final SysAdmin user = new SysAdmin();
        user.setUsername(username);
        return CommonResult.success(sysAdminService.checkUsernameUnique(user));
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
            @Validated({ValidationGroups.Edit.class}) @RequestBody final UserDTO user
    ) {
        // 设置默认的角色与部门
        setDefaultDeptAndRole(user);
        // 修改角色
        boolean isSuccess = sysRoleService.setUserRole(user.getId(), user.getRoleIds());
        // 修改用户部门
        isSuccess = isSuccess && sysDeptService.setUserDept(user.getId(), user.getDeptIds());
        isSuccess = isSuccess && sysAdminService.updateByDTO(user);
        final AdminUserDetails adminUserDetails = SecurityUtils.getLoginUser();
        // 如果修改的是本用户则更新信息
        if (isSuccess && adminUserDetails.getSysAdmin().getId().equals(user.getId())) {
            adminUserDetails.setSysAdmin(sysAdminService.selectSysAdminById(user.getId()));
            adminUserDetails.setDepts(sysDeptService.selectDeptListByUserId(user.getId()));
            adminUserDetails.setRoles(sysRoleService.selectRoleListByUserId(user.getId()));
            adminUserDetails.getRoles().forEach(r -> {
                if (r.getId().equals(config.getApp().getSuperAdminRoleId())) {
                    adminUserDetails.getSysAdmin().setIsAdmin(true);
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
        final SysAdmin user = new SysAdmin();
        user.setId(id);
        user.setDelFlag(AppConstants.DELETE);
        return toAjax(sysAdminService.updateById(user));
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
        final AdminUserDetails adminUserDetails = SecurityUtils.getLoginUser();
        final Long currentUserId = adminUserDetails.getSysAdmin().getId();
        final Boolean isSuccess = sysAdminService.uploadAvatar(id, file, currentUserId);
        // 如果是更新本人头像,则修改登录者信息
        if (id.equals(currentUserId) && Boolean.TRUE.equals(isSuccess)) {
            adminUserDetails.getSysAdmin().setAvatar(sysAdminService.getUserAvatar(id));
            jwtTokenUtil.setUser(adminUserDetails);
        }
        return toAjax(isSuccess);
    }


    /**
     * 设置默认的部门与角色.
     *
     * @param user 用户操作对象
     */
    private void setDefaultDeptAndRole(final UserDTO user) {
        if (CollUtil.isEmpty(user.getRoleIds())) {
            final HashSet<Long> roleIds = new HashSet<>(1);
            roleIds.add(config.getApp().getDefaultRoleId());
            user.setRoleIds(roleIds);
        }
        if (CollUtil.isEmpty(user.getDeptIds())) {
            final HashSet<Long> deptIds = new HashSet<>(1);
            deptIds.add(config.getApp().getDefaultDeptId());
            user.setDeptIds(deptIds);
        }
    }
}
