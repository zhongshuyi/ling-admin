package com.mall.framework.config;

import com.mall.framework.interceptor.BaseRepeatSubmitInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 钟舒艺
 * @date 2021-09-23-15:21
 **/
@Configuration
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ResourcesConfig implements WebMvcConfigurer {

    private final BaseRepeatSubmitInterceptor repeatSubmitInterceptor;

    /**
     * 自定义拦截规则
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(repeatSubmitInterceptor).addPathPatterns("/**");
    }

}
