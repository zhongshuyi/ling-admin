package com.mall.framework.config;

import com.mall.framework.interceptor.BaseRepeatSubmitInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 资源配置.
 *
 * @author 钟舒艺
 **/
@Configuration
@RequiredArgsConstructor
public class ResourcesConfig implements WebMvcConfigurer {

    /**
     * 重复提交拦截器.
     */
    private final transient BaseRepeatSubmitInterceptor repeatSubmitInterceptor;

    /**
     * 自定义拦截规则.
     */
    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(repeatSubmitInterceptor).addPathPatterns("/**");
    }

}
