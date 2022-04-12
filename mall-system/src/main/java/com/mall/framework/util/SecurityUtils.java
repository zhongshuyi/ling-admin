package com.mall.framework.util;


import com.mall.framework.model.AdminUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * security 工具类.
 *
 * @author 钟舒艺
 **/
@SuppressWarnings("unused")
public final class SecurityUtils {

    private SecurityUtils() {
    }

    /**
     * 获取用户.
     *
     * @return 用户信息
     */
    public static AdminUserDetails getLoginUser() {
        return (AdminUserDetails) getAuthentication().getPrincipal();
    }

    /**
     * 获取Authentication.
     *
     * @return Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 生成BCryptPasswordEncoder密码.
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(final String password) {
        final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 判断密码是否相同.
     *
     * @param rawPassword     真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(
            final String rawPassword,
            final String encodedPassword
    ) {
        final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}
