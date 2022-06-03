package com.ling.socket.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Socket配置文件.
 *
 * @author 钟舒艺
 **/

@Data
@Component
@ConfigurationProperties(prefix = "socket-io")
class SocketConfigBean {

    /**
     * 端口号.
     */
    private Integer port;

    /**
     * 主机.
     */
    private String host;

    /**
     * 最大每帧处理数据的长度.
     */
    private Integer maxFramePayloadLength;

    /**
     * http交互最大内容长度.
     */
    private Integer maxHttpContentLength;

    /**
     * socket连接数大小.
     */
    private Integer bossCount;

    /**
     * 工作总数.
     */
    private Integer workCount;

    /**
     * 允许自定义总数.
     */
    private Boolean allowCustomRequests;

    /**
     * 协议升级超时时间.
     */
    private Integer upgradeTimeout;

    /**
     * Ping消息超时时间.
     */
    private Integer pingTimeout;

    /**
     * Ping消息间隔.
     */
    private Integer pingInterval;


}
