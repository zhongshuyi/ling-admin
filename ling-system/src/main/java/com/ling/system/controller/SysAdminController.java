package com.ling.system.controller;

import cn.hutool.http.HttpStatus;
import com.ling.common.core.controller.BaseController;
import com.ling.common.core.domain.CommonResult;
import com.ling.common.core.domain.PageInfo;
import com.ling.common.core.mybatisplus.util.PageUtils;
import com.ling.common.core.validate.ValidationGroups;
import com.ling.common.exception.BusinessErrorException;
import com.ling.framework.config.CustomConfig;
import com.ling.framework.service.UserService;
import com.ling.system.convert.SysAdminConvert;
import com.ling.system.dto.SysAdminDTO;
import com.ling.system.entity.SysAdmin;
import com.ling.system.security.model.LoginUserInfo;
import com.ling.system.service.ISysAdminService;
import com.ling.system.service.ISysDeptService;
import com.ling.system.service.ISysRoleService;
import com.ling.system.utils.SecurityUtils;
import com.ling.system.vo.SysAdminVO;
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
 * 用户管理.
 *
 * @author 钟舒艺
 * @since 2022-10-28 19:40
 **/
@Slf4j
@RestController
@RequestMapping("/system/user")
@RequiredArgsConstructor
public class SysAdminController extends BaseController {

    /**
     * 用户服务.
     */
    private final UserService userService;

    /**
     * 系统用户服务类.
     */
    private final ISysAdminService sysAdminService;


    /**
     * 配置信息.
     */
    private final CustomConfig config;

    /**
     * 部门服务.
     */
    private final ISysDeptService sysDeptService;

    /**
     * 角色服务.
     */
    private final ISysRoleService sysRoleService;


    /**
     * 分页获取用户列表.
     *
     * @param userDTO 用户查询条件
     * @return 分页后信息
     */
    @GetMapping
    public CommonResult<PageInfo<SysAdminVO>> getUserList(
            @Validated(ValidationGroups.QUERY) final SysAdminDTO userDTO) {

        return CommonResult.success(
                PageUtils.buildPageInfo(
                        sysAdminService.listSysAdminPage(
                                PageUtils.buildPagePlus(),
                                userDTO
                        )));
    }

    /**
     * 获取单个用户详细信息.
     *
     * @param id 用户id
     * @return 用户vo
     */
    @GetMapping("/{id}")
    public CommonResult<SysAdminVO> getUserById(@PathVariable final Long id) {
        final SysAdminVO sysAdminVO = SysAdminConvert.INSTANCT.convertToVO(sysAdminService.getSysAdminById(id));
        sysAdminVO.setRoleIds(sysRoleService.listRoleIdsByUserId(id));
        sysAdminVO.setDeptIds(sysDeptService.selectDeptIdsByUserId(id));
        return CommonResult.success(sysAdminVO);
    }


    /**
     * 检查用户名是否重复.
     *
     * @param username 用户id
     * @return 是否重复
     */
    @GetMapping("/checkUsernameUnique")
    public CommonResult<Boolean> checkUsernameUnique(final String username) {
        return CommonResult.success(sysAdminService.checkUsernameUnique(new SysAdminDTO().setUsername(username)));
    }

    /**
     * 增加用户.
     *
     * @param sysAdminDTO 用户信息
     * @return 是否成功
     */
    @PostMapping
    public CommonResult<Void> addUser(@Validated({ValidationGroups.ADD}) @RequestBody final SysAdminDTO sysAdminDTO) {
        if (!sysAdminDTO.getPassword().equals(sysAdminDTO.getPasswordRepeat())) {
            throw new BusinessErrorException(HttpStatus.HTTP_BAD_REQUEST, "两次密码不一样");
        }

        return toAjax(sysAdminService.saveSysAdmin(sysAdminDTO));
    }

    /**
     * 更改用户.
     *
     * @param user 用户信息
     * @return 是否成功
     */
    @PutMapping
    public CommonResult<Void> edit(
            @Validated({ValidationGroups.EDIT}) @RequestBody final SysAdminDTO user
    ) {

        Boolean flag = sysAdminService.updateSysAdminById(user);

        final LoginUserInfo userInfo = SecurityUtils.getLoginUserInfo();
        // 如果修改的是本用户则更新信息
        if (Boolean.TRUE.equals(flag) && userInfo.getUser().getId().equals(user.getId())) {
            // 重新获取一遍所有信息
            userService.afterLogin(userInfo.getUser());
        }
        return toAjax(flag);
    }

    /**
     * 删除用户.
     *
     * @param id 用户id
     * @return 是否删除成功
     */
    @DeleteMapping("/{id}")
    public CommonResult<Void> del(@PathVariable final Long id) {
        return toAjax(sysAdminService.removeSysAdminById(id));
    }

    /**
     * 头像上传.
     * <P>(自动删除之前的头像)</P>
     *
     * @param id   用户id
     * @param file 头像文件
     * @return 是否成功.
     */
    @PostMapping("/avatar/{id}")
    public CommonResult<Void> uploadAvatar(
            @PathVariable final Long id,
            @RequestParam("file") final MultipartFile file
    ) {
        final LoginUserInfo loginUserInfo = SecurityUtils.getLoginUserInfo();
        final Long currentUserId = (Long) loginUserInfo.getUser().getId();
        final Boolean isSuccess = sysAdminService.setSysAdminAvatar(id, file, currentUserId);
        // 如果是更新本人头像,则修改登录者信息
        if (id.equals(currentUserId) && Boolean.TRUE.equals(isSuccess)) {
            SysAdmin user = (SysAdmin) loginUserInfo.getUser();
            user.setAvatar(sysAdminService.getSysAdminAvatarUrl(id));
            SecurityUtils.setLoginUserInfo(loginUserInfo);
        }
        return toAjax(isSuccess);
    }
}
