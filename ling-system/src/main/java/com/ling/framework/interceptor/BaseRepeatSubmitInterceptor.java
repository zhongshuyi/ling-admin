package com.ling.framework.interceptor;


import cn.hutool.core.lang.Singleton;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ling.common.annotation.RepeatSubmit;
import com.ling.common.core.domain.CommonResult;
import com.ling.common.util.ServletUtils;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 防止重复提交拦截器.
 *
 * @author ruoyi
 */
@Component
public abstract class BaseRepeatSubmitInterceptor implements HandlerInterceptor {
    @Override
    public final boolean preHandle(
            @NonNull final HttpServletRequest request,
            @NonNull final HttpServletResponse response,
            @NonNull final Object handler
    ) throws Exception {
        if (handler instanceof HandlerMethod) {
            final HandlerMethod handlerMethod = (HandlerMethod) handler;
            final Method method = handlerMethod.getMethod();
            final RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);
            if (annotation != null && this.isRepeatSubmit(request)) {
                final CommonResult<Void> ajaxResult = CommonResult.failed("不允许重复提交，请稍后再试");
                ServletUtils.renderString(response, Singleton.get(ObjectMapper.class).writeValueAsString(ajaxResult));
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * 验证是否重复提交由子类实现具体地防重复提交的规则.
     *
     * @param request 请求
     * @return 是否重复
     * @throws JsonProcessingException 转换异常
     */
    public abstract boolean isRepeatSubmit(HttpServletRequest request) throws JsonProcessingException;
}
