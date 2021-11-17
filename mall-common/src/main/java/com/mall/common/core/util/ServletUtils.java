package com.mall.common.core.util;

import cn.hutool.core.convert.Convert;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Servlet工具类
 *
 * @author 钟舒艺
 * @date 2021-07-02-17:41
 **/
@SuppressWarnings("unused")
@Slf4j
public class ServletUtils {

    /**
     * 获取RequestAttributes
     *
     * @return RequestAttributes
     */
    public static ServletRequestAttributes getRequestAttributes() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }

    /**
     * 获取request
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    /**
     * 获取response
     *
     * @return HttpServletResponse
     */
    public static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }

    /**
     * 获取session
     *
     * @return HttpSession
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param string   待渲染的字符串
     */
    public static void renderString(HttpServletResponse response, String string) {
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        } catch (IOException e) {
            log.error("错误", e);
        }
    }


    /**
     * 获取String参数
     *
     * @param name 参数名
     * @return 参数值
     */
    public static String getParameter(String name) {
        return getRequest().getParameter(name);
    }


    /**
     * 获取String参数(有默认值)
     *
     * @param name         键
     * @param defaultValue 默认值
     * @return 值
     */
    public static String getParameter(String name, String defaultValue) {
        return Convert.toStr(getRequest().getParameter(name), defaultValue);
    }


    /**
     * 获取Integer参数
     *
     * @param name 键名
     * @return 值
     */
    public static Integer getParameterToInt(String name) {
        return Convert.toInt(getRequest().getParameter(name));
    }


    /**
     * 获取Integer参数(默认值)
     *
     * @param name         键
     * @param defaultValue 默认值
     * @return 值
     */
    public static Integer getParameterToInt(String name, Integer defaultValue) {
        return Convert.toInt(getRequest().getParameter(name), defaultValue);
    }
}
