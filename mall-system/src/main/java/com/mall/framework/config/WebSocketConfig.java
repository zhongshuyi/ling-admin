package com.mall.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * WebSocket 配置类.
 *
 * @author 钟舒艺
 **/
@Configuration
public class WebSocketConfig {
    /**
     * ServerEndpointExporter 这个Bean会自动注册使用@ServerEndpoint注解声明的websocket endpoint.
     *
     * @return ServerEndpointExporter
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
