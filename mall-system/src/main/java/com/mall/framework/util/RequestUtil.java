package com.mall.framework.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import com.mall.common.core.domain.CommonResult;
import com.mall.common.util.ServletUtils;
import com.mall.framework.config.CustomConfig;
import com.mall.system.entity.UmsPermissionUrl;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

/**
 * 请求工具类.
 *
 * @author 钟舒艺
 **/
@Component
@RequiredArgsConstructor
public class RequestUtil {

    /**
     * 全局配置.
     */
    private final CustomConfig customConfig;

    /**
     * Jackson序列化.
     */
    private final ObjectMapper objectMapper;

    /**
     * 校验请求是否存在.
     *
     * @param request  请求
     * @param response 响应
     * @return 是否存在 true 为存在
     * @throws JsonProcessingException json转换异常
     */
    public boolean checkRequest(
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws JsonProcessingException {
        // 获取当前 request 的方法
        final String currentMethod = request.getMethod();
        boolean isExists = false;
        for (final UmsPermissionUrl url : customConfig.getAllUrl()) {
            // 通过 AntPathRequestMatcher 匹配 url
            // 可以通过 2 种方式创建 AntPathRequestMatcher
            // 1：new AntPathRequestMatcher(uri,method) 这种方式可以直接判断方法是否匹配，因为这里我们把 方法不匹配 自定义抛出，所以，我们使用第2种方式创建
            // 2：new AntPathRequestMatcher(uri) 这种方式不校验请求方法，只校验请求路径
            final AntPathRequestMatcher antPathMatcher = new AntPathRequestMatcher(url.getUrl());
            if (antPathMatcher.matches(request)) {
                isExists = true;
                if (url.getMethod().equals(currentMethod)) {
                    // 各项都匹配,那就直接返回
                    return true;
                }
            }
        }
        // 如果循环结束还没找到,看看是不是不存在
        if (!isExists) {
            ServletUtils.renderString(
                    response,
                    objectMapper.writeValueAsString(CommonResult.failed(HttpStatus.HTTP_NOT_FOUND, "请求地址不存在"))
            );
            return false;
        }
        // 如果存在,那肯定是请求方式不支持
        ServletUtils.renderString(response, objectMapper.writeValueAsString(
                CommonResult.failed(HttpStatus.HTTP_NOT_FOUND, "请求方式不支持")));
        return false;
    }


    /**
     * 请求是否不需要进行权限拦截.
     *
     * @param request 当前请求
     * @return true - 忽略，false - 不忽略
     */
    public boolean checkIgnores(final HttpServletRequest request) {
        final String method = request.getMethod();

        HttpMethod httpMethod = HttpMethod.resolve(method);
        if (httpMethod == null) {
            httpMethod = HttpMethod.GET;
        }
        final Set<String> ignores = Sets.newHashSet();
        switch (httpMethod) {
            case GET:
                ignores.addAll(customConfig.getIgnores().getGet());
                break;
            case PUT:
                ignores.addAll(customConfig.getIgnores().getPut());
                break;
            case HEAD:
                ignores.addAll(customConfig.getIgnores().getHead());
                break;
            case POST:
                ignores.addAll(customConfig.getIgnores().getPost());
                break;
            case PATCH:
                ignores.addAll(customConfig.getIgnores().getPatch());
                break;
            case TRACE:
                ignores.addAll(customConfig.getIgnores().getTrace());
                break;
            case DELETE:
                ignores.addAll(customConfig.getIgnores().getDelete());
                break;
            case OPTIONS:
                ignores.addAll(customConfig.getIgnores().getOptions());
                break;
            default:
                break;
        }

        ignores.addAll(customConfig.getIgnores().getPattern());

        if (CollUtil.isNotEmpty(ignores)) {
            for (final String ignore : ignores) {
                final AntPathRequestMatcher matcher = new AntPathRequestMatcher(ignore, method);
                if (matcher.matches(request)) {
                    return true;
                }
            }
        }
        return false;
    }

}
