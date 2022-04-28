package com.mall.framework.security.service;

import cn.hutool.core.collection.CollUtil;
import com.mall.framework.model.AdminUserDetails;
import com.mall.framework.util.RequestUtil;
import com.mall.framework.util.SecurityUtils;
import com.mall.system.entity.UmsPermissionUrl;
import com.mall.system.entity.UmsRole;
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
        for (final UmsRole role : user.getRoles()) {
            if ("admin".equals(role.getRoleKey())) {
                return true;
            }
        }

        if (CollUtil.isEmpty(user.getUrlList())) {
            return false;
        }

        boolean hasPermission = false;

        for (final UmsPermissionUrl umsPermissionUrl : user.getUrlList()) {
            final AntPathRequestMatcher antPathMatcher = new AntPathRequestMatcher(
                    umsPermissionUrl.getUrl(), umsPermissionUrl.getMethod());
            if (antPathMatcher.matches(request)) {
                hasPermission = true;
                break;
            }
        }
        return hasPermission;
    }
}
