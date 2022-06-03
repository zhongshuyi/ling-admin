package com.ling.common.constant;

/**
 * 正则表达式.
 *
 * @author 钟舒艺
 **/
public final class Regular {

    /**
     * 中国大陆手机号.
     */
    public static final String CHINA_TEL_NO = "(13\\d|14[579]|15[^4\\D]|17[^49\\D]|18\\d)\\d{8}";

    /**
     * 网址.
     */
    public static final String INTERNET_URL
            = "(ht|f)tp(s?)\\:\\/\\/[0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*(:(0-9)*)*(\\/?)([a-zA-Z0-9\\-\\.\\?\\,\\'\\/\\\\&%\\+\\$#_=]*)?";

    private Regular() {
    }
}
