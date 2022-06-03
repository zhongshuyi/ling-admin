package com.ling.socket.config;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * SocketIO初始化.
 *
 * @author 钟舒艺
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class ServerRunner implements CommandLineRunner {

    /**
     * SocketIO.
     */
    private final SocketIOServer server;

    @Override
    public final void run(final String... args) {
        server.start();
        log.info("websocket 服务器启动成功。。。");
    }
}
