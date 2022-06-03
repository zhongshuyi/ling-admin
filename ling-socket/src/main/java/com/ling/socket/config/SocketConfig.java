package com.ling.socket.config;

import cn.hutool.core.text.CharSequenceUtil;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类.
 *
 * @author 钟舒艺
 **/
@Configuration
public class SocketConfig {
    /**
     * 创建SocketIO服务.
     *
     * @param socketConfigBean yaml里的配置信息
     * @return SocketIOServer
     */
    @Bean
    public SocketIOServer server(final SocketConfigBean socketConfigBean) {
        final com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname(socketConfigBean.getHost());
        config.setPort(socketConfigBean.getPort());
        config.setMaxFramePayloadLength(socketConfigBean.getMaxFramePayloadLength());
        config.setMaxHttpContentLength(socketConfigBean.getMaxHttpContentLength());
        config.setBossThreads(socketConfigBean.getBossCount());
        config.setWorkerThreads(socketConfigBean.getWorkCount());
        config.setAllowCustomRequests(socketConfigBean.getAllowCustomRequests());
        config.setUpgradeTimeout(socketConfigBean.getUpgradeTimeout());
        config.setPingTimeout(socketConfigBean.getPingTimeout());
        config.setPingInterval(socketConfigBean.getPingInterval());

        //这个listener可以用来进行身份验证
        config.setAuthorizationListener(data -> {
            // http://localhost:8081?token=xxxxxxx
            // 例如果使用上面的链接进行connect，可以使用如下代码获取用户密码信息，本文不做身份验证
            final String token = data.getSingleUrlParam("token");
            // 校验token的合法性，实际业务需要校验token是否过期等等，参考 spring-boot-demo-rbac-security 里的 JwtUtil
            // 如果认证不通过会返回一个 Socket.EVENT_CONNECT_ERROR 事件
            return CharSequenceUtil.isNotBlank(token);
        });

        return new SocketIOServer(config);
    }

    /**
     * 开启注解扫描.
     *
     * @param server SocketIO服务.
     * @return springAnnotationScanner
     */
    @Bean
    public SpringAnnotationScanner springAnnotationScanner(final SocketIOServer server) {
        return new SpringAnnotationScanner(server);
    }

}
