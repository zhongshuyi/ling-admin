package com.ling.framework.security.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ling.common.constant.AppConstants;
import com.ling.common.enums.BusinessExceptionMsgEnum;
import com.ling.common.exception.BusinessErrorException;
import com.ling.framework.config.CustomConfig;
import com.ling.framework.model.AdminUserDetails;
import com.ling.system.entity.SysAdmin;
import com.ling.system.entity.SysMenu;
import com.ling.system.entity.SysPermissionUrl;
import com.ling.system.entity.SysRole;
import com.ling.system.mapper.SysPermissionUrlMapper;
import com.ling.system.service.ISysAdminService;
import com.ling.system.service.ISysDeptService;
import com.ling.system.service.ISysRoleService;
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
 * 继承了{@link UserDetailsService}借口,该接口只有一个方法loadUserByUsername
 * 通过用户名来加载用户 ,这个方法主要用于从系统数据中查询并加载具体的用户到Spring Security中
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
    private final ISysAdminService sysAdminService;

    /**
     * 部门服务.
     */
    private final ISysDeptService sysDeptService;

    /**
     * 权限服务.
     */
    private final PermissionService permissionService;

    /**
     * 角色服务.
     */
    private final ISysRoleService sysRoleService;

    /**
     * 权限与url关联信息.
     */
    private final SysPermissionUrlMapper urlMapping;

    /**
     * app配置信息.
     */
    private final CustomConfig config;

    @Override
    public final UserDetails loadUserByUsername(
            final String username
    ) throws UsernameNotFoundException {
        UserDetailsServiceImpl.log.info(username + "尝试登录");
        final SysAdmin sysAdmin = sysAdminService.selectSysAdminByUserName(username);
        if (sysAdmin == null) {
            UserDetailsServiceImpl.log.info("登录用户：{} 不存在.", username);
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        } else if (sysAdmin.getDelFlag().equals(AppConstants.DELETE)) {
            UserDetailsServiceImpl.log.info("登录用户：{} 已被删除.", username);
            throw new BusinessErrorException(BusinessExceptionMsgEnum.ACCOUNT_DELETED);
        } else if (sysAdmin.getStatus().equals(AppConstants.DISABLE)) {
            UserDetailsServiceImpl.log.info("登录用户：{} 已被停用.", username);
            throw new BusinessErrorException(BusinessExceptionMsgEnum.ACCOUNT_DISABLE);
        }
        final List<SysRole> roles = sysRoleService.selectRoleListByUserId(sysAdmin.getId());
        roles.forEach(r -> {
            if (r.getStatus().equals(AppConstants.DISABLE)) {
                UserDetailsServiceImpl.log.info("登录用户：{} 角色{}已被停用.", username, r.getRoleName());
                throw new BusinessErrorException(BusinessExceptionMsgEnum.ROLE_DISABLE);
            }
            if (r.getId().equals(config.getApp().getSuperAdminRoleId())) {
                sysAdmin.setIsAdmin(true);
            }
        });
        if (sysAdmin.getIsAdmin() == null) {
            sysAdmin.setIsAdmin(false);
        }
        // 获取用户所拥有的菜单与权限放在用户信息中
        final List<SysMenu> permissionList = permissionService.getPermissionList(sysAdmin);
        // 提取出权限
        final List<Long> permissionIds = permissionList.stream().filter(
                p -> p.getMenuType() == 2).map(SysMenu::getId).collect(Collectors.toList());
        // 获取只需要登录就能访问的接口路径,加到用户有权限访问路径中
        final List<SysPermissionUrl> urlList = config.getLoginHasAuth();
        if (CollUtil.isNotEmpty(permissionIds)) {
            // 获取该用户有权限的接口
            urlList.addAll(urlMapping.selectList(
                            Wrappers.<SysPermissionUrl>lambdaQuery().in(SysPermissionUrl::getMenuId, permissionIds))
                    .stream().filter(u ->
                            CharSequenceUtil.isNotEmpty(u.getUrl()) && CharSequenceUtil.isNotEmpty(u.getMethod()))
                    .collect(Collectors.toList()));
        }
        return new AdminUserDetails(
                sysAdmin,
                permissionList,
                urlList,
                roles,
                sysDeptService.selectDeptListByUserId(sysAdmin.getId())
        );
    }
}
