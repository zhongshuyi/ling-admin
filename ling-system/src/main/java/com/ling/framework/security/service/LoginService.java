package com.ling.framework.security.service;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.HttpStatus;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.ling.common.constant.AppConstants;
import com.ling.common.enums.DataScopeTypeEnum;
import com.ling.common.exception.BusinessErrorException;
import com.ling.common.util.ip.AddressUtils;
import com.ling.framework.log.entity.SysLoginLog;
import com.ling.framework.log.service.ISysLoginLogService;
import com.ling.framework.model.AdminUserDetails;
import com.ling.framework.mybatisplus.helper.DataPermissionHelper;
import com.ling.framework.util.JwtTokenUtil;
import com.ling.framework.util.SecurityUtils;
import java.util.Date;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * 登录服务层.
 *
 * @author 钟舒艺
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {
    /**
     * jwt工具类.
     */
    private final JwtTokenUtil jwtTokenUtil;

    /**
     * 登录日志记录.
     */
    private final ISysLoginLogService sysLoginLogService;

    /**
     * 认证管理器.
     */
    @Resource
    private AuthenticationManager authenticationManager;

    /**
     * 登录验证.
     * 会调用{@link UserDetailsServiceImpl}的loadUserByUsername方法进行验证
     *
     * @param username 用户名
     * @param password 密码
     * @param request  请求信息
     * @return token
     */
    public String login(
            final String username,
            final String password,
            final HttpServletRequest request
    ) {

        /*
           Authentication 是一个用户凭证.
           接口用来作为用户认证的凭证使用,通常常用的实现有:
           认证用户: UsernamePasswordAuthenticationToken 包含了 UserDetails 有用户的信息
           匿名用户: AnonymousAuthenticationToken 包含了一个字符串 anonymousUser 作为匿名用户的标识
         */
        final Authentication authentication;

        final SysLoginLog sysLoginLog = new SysLoginLog();
        sysLoginLog.setLoginTime(new Date());
        sysLoginLog.setUsername(username);
        sysLoginLog.setIpaddr(ServletUtil.getClientIP(request));
        LoginService.log.info(ServletUtil.getClientIP(request));
        final UserAgent userAgent = UserAgentUtil.parse(request.getHeader("User-Agent"));
        sysLoginLog.setBrowser(userAgent.getBrowser().getName());
        sysLoginLog.setOs(userAgent.getOs().getName());
        sysLoginLog.setLoginLocation(AddressUtils.getAddressByIp(ServletUtil.getClientIP(request)));
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    // UsernamePasswordAuthenticationToken是
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (final BadCredentialsException e) {
            sysLoginLog.setStatus(AppConstants.DISABLE);
            sysLoginLog.setMsg("用户名或密码错误");
            sysLoginLogService.save(sysLoginLog);
            throw new BusinessErrorException(HttpStatus.HTTP_UNAUTHORIZED, "用户名或密码错误", e);
        } catch (final Exception e) {
            throw new BusinessErrorException(e);
        }
        // 获取
        final AdminUserDetails adminUserDetails = (AdminUserDetails) authentication.getPrincipal();
        // 获取该用户的数据权限集
        adminUserDetails.setDataScopeTypeEnumCodeList(
                DataPermissionHelper.getDataPermissionTypeEnum(adminUserDetails)
                        .stream().map(DataScopeTypeEnum::getCode).collect(Collectors.toList()));
        sysLoginLog.setStatus(AppConstants.ENABLE);
        sysLoginLog.setMsg("登录成功");
        sysLoginLog.setUserId(adminUserDetails.getSysAdmin().getId());
        sysLoginLogService.save(sysLoginLog);
        return jwtTokenUtil.generateToken(adminUserDetails);
    }

    /**
     * 用户登出.
     *
     * @return 是否成功.
     */
    public Boolean logout() {
        final AdminUserDetails user = SecurityUtils.getLoginUser();
        return jwtTokenUtil.delUser(user.getUuid());
    }
}
