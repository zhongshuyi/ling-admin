package com.mall.framework.security.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mall.common.constant.GlobalConstants;
import com.mall.common.enums.BusinessExceptionMsgEnum;
import com.mall.common.enums.Status;
import com.mall.common.exception.BusinessErrorException;
import com.mall.framework.config.CustomConfig;
import com.mall.framework.model.AdminUserDetails;
import com.mall.system.entity.UmsAdmin;
import com.mall.system.entity.UmsMenu;
import com.mall.system.entity.UmsPermissionUrl;
import com.mall.system.entity.UmsRole;
import com.mall.system.mapper.UmsPermissionUrlMapper;
import com.mall.system.service.IUmsAdminService;
import com.mall.system.service.IUmsDeptService;
import com.mall.system.service.IUmsRoleService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * 用户验证处理.
 *
 * @author 钟舒艺
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * 用户服务.
     */
    private final IUmsAdminService umsAdminService;

    /**
     * 部门服务.
     */
    private final IUmsDeptService umsDeptService;

    /**
     * 权限服务.
     */
    private final PermissionService permissionService;

    /**
     * 角色服务.
     */
    private final IUmsRoleService umsRoleService;

    /**
     * 权限与url关联信息.
     */
    private final UmsPermissionUrlMapper urlMapping;

    /**
     * app配置信息.
     */
    private final CustomConfig config;

    @Override
    public final UserDetails loadUserByUsername(
            final String username
    ) throws UsernameNotFoundException {
        log.info(username + "尝试登录");
        final UmsAdmin umsAdmin = umsAdminService.getUmsAdminByUserName(username);
        if (umsAdmin == null) {
            log.info("登录用户：{} 不存在.", username);
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        } else if (Status.DELETED.getCode().equals(umsAdmin.getDelFlag())) {
            log.info("登录用户：{} 已被删除.", username);
            throw new BusinessErrorException(BusinessExceptionMsgEnum.ACCOUNT_DELETED);
        } else if (Status.DISABLE.getCode().equals(umsAdmin.getStatus())) {
            log.info("登录用户：{} 已被停用.", username);
            throw new BusinessErrorException(BusinessExceptionMsgEnum.ACCOUNT_DISABLE);
        }
        final List<UmsRole> roles = umsRoleService.selectRoleListByUserId(umsAdmin.getId());
        roles.forEach(r -> {
            if (r.getStatus().equals(Status.DISABLE.getCode())) {
                log.info("登录用户：{} 角色{}已被停用.", username, r.getRoleName());
                throw new BusinessErrorException(BusinessExceptionMsgEnum.ROLE_DISABLE);
            }
            if (r.getId().equals(GlobalConstants.SUPER_ADMIN_ROLE_ID)) {
                umsAdmin.setIsAdmin(true);
            }
        });
        if (umsAdmin.getIsAdmin() == null) {
            umsAdmin.setIsAdmin(false);
        }
        final List<UmsMenu> permissionList = permissionService.getPermissionList(umsAdmin);
        final List<Long> permissionIds = permissionList.stream().filter(
                p -> p.getMenuType() == 2).map(UmsMenu::getId).collect(Collectors.toList());
        List<UmsPermissionUrl> urlList = config.getLoginHasAuth();
        log.error(urlList.toString());
        if (CollUtil.isNotEmpty(permissionIds)) {
            // 获取url与method 都不为空的权限列表
            urlList = urlMapping.selectList(
                            Wrappers.<UmsPermissionUrl>lambdaQuery().in(UmsPermissionUrl::getMenuId, permissionIds))
                    .stream().filter(u ->
                            CharSequenceUtil.isNotEmpty(u.getUrl()) && CharSequenceUtil.isNotEmpty(u.getMethod()))
                    .collect(Collectors.toList());
        }
        return new AdminUserDetails(
                umsAdmin,
                permissionService.getPermissionList(umsAdmin),
                urlList,
                roles,
                umsDeptService.selectDeptListByUserId(umsAdmin.getId())
        );
    }
}
