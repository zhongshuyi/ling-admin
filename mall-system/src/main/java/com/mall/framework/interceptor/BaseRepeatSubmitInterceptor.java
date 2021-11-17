package com.mall.framework.interceptor;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.common.annotation.RepeatSubmit;
import com.mall.common.core.domain.CommonResult;
import com.mall.common.core.util.ServletUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 防止重复提交拦截器
 *
 * @author ruoyi
 */
@Component
public abstract class BaseRepeatSubmitInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);
            if (annotation != null) {
                if (this.isRepeatSubmit(request)) {
                    CommonResult ajaxResult = CommonResult.failed("不允许重复提交，请稍后再试");
                    ServletUtils.renderString(response, new ObjectMapper().writeValueAsString(ajaxResult));
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * 验证是否重复提交由子类实现具体地防重复提交的规则
     *
     * @param request 请求
     * @return 是否重复
     * @throws Exception 转换异常
     */
    public abstract boolean isRepeatSubmit(HttpServletRequest request) throws Exception;
}
