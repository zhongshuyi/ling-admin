package com.ling.framework.config;

import cn.hutool.core.lang.Singleton;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 应用需要使用的bean.
 *
 * @author 钟舒艺
 **/
@Configuration
public class GlobalBean {

    /**
     * 创建ObjectMapper bean.
     *
     * @return objectMapper
     */
    @Bean
    public ObjectMapper objectMapper() {
        return Singleton.get(ObjectMapper.class);
    }

}
