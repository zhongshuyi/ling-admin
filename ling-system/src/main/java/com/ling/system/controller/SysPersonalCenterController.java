package com.ling.system.controller;

import cn.dev33.satoken.secure.BCrypt;
import com.ling.common.annotation.LoginAuth;
import com.ling.common.core.controller.BaseController;
import com.ling.common.core.domain.CommonResult;
import com.ling.common.core.domain.model.LoginUser;
import com.ling.common.core.validate.ValidationGroups;
import com.ling.framework.utils.SecurityUtils;
import com.ling.system.dto.PasswordDTO;
import com.ling.system.dto.SysAdminDTO;
import com.ling.system.entity.SysAdmin;
import com.ling.system.service.ISysAdminService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "个人中心")
@RequiredArgsConstructor
@RequestMapping("/personalCenter")
public class SysPersonalCenterController extends BaseController {

    /**
     * 用户服务类.
     */
    private final ISysAdminService sysAdminService;


    /**
     * 个人中心修改本用户信息.
     *
     * @param user 用户信息.
     * @return 是否成功
     */
    @PutMapping
    public CommonResult<Void> setUserInfo(
            @Validated({ValidationGroups.Edit.class}) @RequestBody final SysAdminDTO user
    ) {
        final LoginUser loginUserInfo = SecurityUtils.getLoginUser();
        final SysAdmin sysAdmin = (SysAdmin) loginUserInfo.getUser();
        // 如果修改的是本用户则更新信息
        if (sysAdmin.getId().equals(user.getId())) {
            final Boolean isSuccess = sysAdminService.updateSysAdminById(user);
            loginUserInfo.setUser(sysAdminService.getSysAdminById(user.getId()));
            SecurityUtils.setLoginUserInfo(loginUserInfo);
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
    public CommonResult<Void> setPassword(@RequestBody final PasswordDTO password) {
        final LoginUser loginUserInfo = SecurityUtils.getLoginUser();
        final SysAdmin sysAdmin = (SysAdmin) loginUserInfo.getUser();
        if (!BCrypt.checkpw(password.getPasswordOld(), sysAdmin.getPassword())) {
            return CommonResult.failed("旧密码错误");
        }
        return toAjax(
                sysAdminService.updateById(sysAdmin.setPassword(BCrypt.hashpw(password.getPasswordNew(), BCrypt.gensalt()))));
    }


    /**
     * 本人修改头像.
     *
     * @param file 头像文件
     * @return 是否成功
     */
    @PostMapping("/avatar")
    public CommonResult<Void> uploadAvatar(
            @RequestParam("file") final MultipartFile file
    ) {
        final LoginUser loginUserInfo = SecurityUtils.getLoginUser();
        final SysAdmin sysAdmin = (SysAdmin) loginUserInfo.getUser();
        final Long currentUserId = sysAdmin.getId();
        final Boolean isSuccess = sysAdminService.setSysAdminAvatar(currentUserId, file, currentUserId);
        if (Boolean.TRUE.equals(isSuccess)) {
            sysAdmin.setAvatar(sysAdminService.getSysAdminAvatarUrl(currentUserId));
            SecurityUtils.setLoginUserInfo(loginUserInfo);
        }
        return toAjax(isSuccess);
    }
}
