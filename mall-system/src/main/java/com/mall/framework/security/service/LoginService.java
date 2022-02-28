package com.mall.framework.security.service;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.HttpStatus;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.mall.common.exception.BusinessErrorException;
import com.mall.common.util.ip.AddressUtils;
import com.mall.framework.log.entity.UmsLoginLog;
import com.mall.framework.log.service.IUmsLoginLogService;
import com.mall.framework.model.AdminUserDetails;
import com.mall.framework.util.JwtTokenUtil;
import java.util.Date;
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
    private final IUmsLoginLogService umsLoginLogService;

    /**
     * 认证管理器.
     */
    @Resource
    private AuthenticationManager authenticationManager;

    /**
     * 登录验证.
     *
     * @param username 用户名
     * @param password 密码
     * @param request  请求信息
     * @return token
     */
    public String login(final String username,
                        final String password,
                        final HttpServletRequest request
    ) {

        // 用户验证
        final Authentication authentication;

        final UmsLoginLog umsLoginLog = new UmsLoginLog();
        umsLoginLog.setLoginTime(new Date());
        umsLoginLog.setUsername(username);
        umsLoginLog.setIpaddr(ServletUtil.getClientIP(request));
        log.info(ServletUtil.getClientIP(request));
        final UserAgent userAgent = UserAgentUtil.parse(request.getHeader("User-Agent"));
        umsLoginLog.setBrowser(userAgent.getBrowser().getName());
        umsLoginLog.setOs(userAgent.getOs().getName());
        umsLoginLog.setLoginLocation(AddressUtils.getAddressByIp(ServletUtil.getClientIP(request)));
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (final BadCredentialsException e) {
            umsLoginLog.setStatus(1);
            umsLoginLog.setMsg("用户名或密码错误");
            umsLoginLogService.save(umsLoginLog);
            throw new BusinessErrorException(HttpStatus.HTTP_UNAUTHORIZED, "用户名或密码错误", e);
        } catch (final Exception e) {
            throw new BusinessErrorException(e);
        }
        final AdminUserDetails adminUserDetails = (AdminUserDetails) authentication.getPrincipal();
        umsLoginLog.setStatus(0);
        umsLoginLog.setMsg("登录成功");
        umsLoginLog.setUserId(adminUserDetails.getUmsAdmin().getId());
        umsLoginLogService.save(umsLoginLog);
        return jwtTokenUtil.generateToken(adminUserDetails);
    }
}
