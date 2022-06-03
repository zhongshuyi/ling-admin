package com.ling.system.controller;

import com.ling.common.annotation.LoginAuth;
import com.ling.common.core.controller.BaseController;
import com.ling.common.core.domain.CommonResult;
import com.ling.common.core.validate.ValidationGroups;
import com.ling.framework.model.AdminUserDetails;
import com.ling.framework.util.JwtTokenUtil;
import com.ling.framework.util.SecurityUtils;
import com.ling.system.dto.PasswordDTO;
import com.ling.system.dto.UserDTO;
import com.ling.system.service.ISysAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 个人中心(登录了就有权限访问).
 *
 * @author 钟舒艺
 * @date 2022-05-13-13:52
 **/
@Slf4j
@LoginAuth
@RestController
@Api(tags = "个人中心")
@RequiredArgsConstructor
@RequestMapping("/personalCenter")
public class PersonalCenterController extends BaseController {

    /**
     * 用户服务类.
     */
    private final ISysAdminService sysAdminService;

    /**
     * jwt工具类.
     */
    private final JwtTokenUtil jwtTokenUtil;

    /**
     * 个人中心修改本用户信息.
     *
     * @param user 用户信息.
     * @return 是否成功
     */
    @PutMapping
    @ApiOperation("设置账户信息")
    public CommonResult<Void> setUserInfo(
            @Validated({ValidationGroups.Edit.class}) @RequestBody final UserDTO user
    ) {
        final AdminUserDetails adminUserDetails = SecurityUtils.getLoginUser();
        // 如果修改的是本用户则更新信息
        if (adminUserDetails.getSysAdmin().getId().equals(user.getId())) {
            final Boolean isSuccess = sysAdminService.updateByDTO(user);
            adminUserDetails.setSysAdmin(sysAdminService.selectSysAdminById(user.getId()));
            jwtTokenUtil.setUser(adminUserDetails);
            return toAjax(isSuccess);
        } else {
            return CommonResult.forbidden("无权限");
        }
    }

    /**
     * 修改密码.
     *
     * @param password 密码载体
     * @return 是否成功
     */
    @PutMapping("password")
    @ApiOperation("修改密码")
    public CommonResult<Void> setPassword(@RequestBody final PasswordDTO password) {
        final AdminUserDetails adminUserDetails = SecurityUtils.getLoginUser();
        if (!SecurityUtils.matchesPassword(password.getPasswordOld(), adminUserDetails.getPassword())) {
            return CommonResult.failed("旧密码错误");
        }
        return toAjax(
                sysAdminService.updateById(
                        adminUserDetails
                                .getSysAdmin()
                                .setPassword(SecurityUtils.encryptPassword(password.getPasswordNew()))));
    }


    /**
     * 本人修改头像.
     *
     * @param file 头像文件
     * @return 是否成功
     */
    @PostMapping("/avatar")
    @ApiOperation("头像上传")
    public CommonResult<Void> uploadAvatar(
            @RequestParam("file") final MultipartFile file
    ) {
        final AdminUserDetails adminUserDetails = SecurityUtils.getLoginUser();
        final Long currentUserId = adminUserDetails.getSysAdmin().getId();
        final Boolean isSuccess = sysAdminService.uploadAvatar(currentUserId, file, currentUserId);
        if (Boolean.TRUE.equals(isSuccess)) {
            adminUserDetails.getSysAdmin().setAvatar(sysAdminService.getUserAvatar(currentUserId));
            jwtTokenUtil.setUser(adminUserDetails);
        }
        return toAjax(isSuccess);
    }
}
