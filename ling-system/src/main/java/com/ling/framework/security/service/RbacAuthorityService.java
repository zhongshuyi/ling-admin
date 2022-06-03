package com.ling.framework.security.service;

import cn.hutool.core.collection.CollUtil;
import com.ling.framework.model.AdminUserDetails;
import com.ling.framework.util.RequestUtil;
import com.ling.framework.util.SecurityUtils;
import com.ling.system.entity.SysPermissionUrl;
import com.ling.system.entity.SysRole;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

/**
 * 动态路由认证.
 *
 * @author 钟舒艺
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class RbacAuthorityService {

    /**
     * 请求校验.
     */
    private final RequestUtil requestUtil;

    /**
     * 根据访问url判断是否有权限.
     *
     * @param request        请求
     * @param authentication 权限
     * @return 是否通过校验
     */
    public boolean hasPermission(
            final HttpServletRequest request,
            final Authentication authentication
    ) {
        if (requestUtil.checkIgnores(request)) {
            return true;
        }
        final AdminUserDetails user = SecurityUtils.getLoginUser();
        for (final SysRole role : user.getRoles()) {
            if ("admin".equals(role.getRoleKey())) {
                return true;
            }
        }

        if (CollUtil.isEmpty(user.getUrlList())) {
            return false;
        }

        boolean hasPermission = false;
        for (final SysPermissionUrl sysPermissionUrl : user.getUrlList()) {
            final AntPathRequestMatcher antPathMatcher = new AntPathRequestMatcher(
                    sysPermissionUrl.getUrl(), sysPermissionUrl.getMethod());
            if (antPathMatcher.matches(request)) {
                hasPermission = true;
                break;
            }
        }
        return hasPermission;
    }
}
