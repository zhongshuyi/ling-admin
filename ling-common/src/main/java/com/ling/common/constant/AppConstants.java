package com.ling.common.constant;

import cn.hutool.core.text.CharSequenceUtil;

/**
 * 应用全局常量.
 *
 * @author 钟舒艺
 **/
@SuppressWarnings("unused")
public final class AppConstants {

    /**
     * 默认分页大小.
     */
    public static final Long DEFAULT_PAGE_SIZE = 10L;
    /**
     * 默认当前页.
     */
    public static final Long DEFAULT_PAGE_NUM = 1L;

    /**
     * 数据库中表示是.
     */
    public static final Byte TRUE = 1;

    /**
     * 数据库中表示为假的.
     */
    public static final Byte FALSE = 0;

    /**
     * 数据库中表示启用.
     */
    public static final Byte ENABLE = 1;

    /**
     * 数据库中表示禁用.
     */
    public static final Byte DISABLE = 0;

    /**
     * 已删除标识.
     */
    public static final Byte DELETE = 1;

    /**
     * 未删除标识.
     */
    public static final Byte NOT_DELETE = 0;

    /**
     * 用户信息 Redis Key 标识模板.
     */
    private static final String USERINFO_KEY_TEMPLATE = "{}:userinfo:{}";


    /**
     * 获取用于存储用户信息的 Redis Key.
     *
     * @param tokenName token名
     * @param tokenKey  token值
     * @return 键名
     */
    public static String getUserInfoKey(
            String tokenName,
            String tokenKey) {
        return CharSequenceUtil.format(USERINFO_KEY_TEMPLATE, tokenName, tokenKey);
    }

    private AppConstants() {
    }
}
