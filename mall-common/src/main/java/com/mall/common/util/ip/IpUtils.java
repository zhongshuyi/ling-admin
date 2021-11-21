package com.mall.common.util.ip;

import cn.hutool.core.net.NetUtil;
import cn.hutool.http.HTMLFilter;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;


/**
 * ip地址工具类.
 *
 * @author 钟舒艺
 **/
@SuppressWarnings("unused")
@Slf4j
public final class IpUtils {

    /**
     * 未知地址.
     */
    public static final String UNKNOWN = "unknown";

    private IpUtils() {
    }

    /**
     * 根据请求获取ip地址.
     *
     * @param request HttpServletRequest
     * @return ip地址
     */
    public static String getIpAdder(final HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }

        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return NetUtil.isInnerIP(ip) ? "本地" : new HTMLFilter().filter(ip);
    }
}
