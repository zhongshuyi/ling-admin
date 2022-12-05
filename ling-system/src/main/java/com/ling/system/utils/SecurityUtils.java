package com.ling.system.utils;


import cn.dev33.satoken.stp.StpUtil;
import com.ling.common.constant.AppConstants;
import com.ling.common.util.RedisUtils;
import com.ling.system.security.model.LoginUserInfo;
import java.time.Duration;

/**
 * new class.
 *
 * @author 钟舒艺
 * @since 2022-10-27 23:16
 **/
public class SecurityUtils {

    private SecurityUtils() {
    }

    /**
     * 从redis获取当前登录用户信息.
     *
     * @return 当前登录用户信息
     */
    public static LoginUserInfo getLoginUserInfo() {
        return RedisUtils.getCacheObject(getUserInfoKey());
    }

    /**
     * 存储当前登录信息到redis.
     *
     * @param loginUserInfo 用户信息.
     */
    public static void setLoginUserInfo(final LoginUserInfo loginUserInfo) {
        RedisUtils.setCacheObject(getUserInfoKey(), loginUserInfo, Duration.ofSeconds(StpUtil.getTokenTimeout()));
    }

    /**
     * 刷新用户信息的有效期.
     */
    public static void refreshUserInfoExpire() {
        RedisUtils.expire(getUserInfoKey(), Duration.ofSeconds(StpUtil.getTokenTimeout()));
    }

    /**
     * 从redis获取当前登录用户信息.
     *
     * @return 当前登录用户信息
     */
    public static String getUserInfoKey() {
        return AppConstants.getUserInfoKey(StpUtil.getTokenName(), StpUtil.getTokenValue());
    }
}
