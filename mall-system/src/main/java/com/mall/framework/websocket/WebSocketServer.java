package com.mall.framework.websocket;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * WebSocket服务.
 *
 * @author 钟舒艺
 **/
@ServerEndpoint("/ws/asset/{sid}")
@Component
@Slf4j
public class WebSocketServer {
    /**
     * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的.
     */
    private static final AtomicInteger ONLINE_NUM = new AtomicInteger();

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象.
     */
    private static final ConcurrentHashMap<String, Session> SESSION_POOLS = new ConcurrentHashMap<>();

    /**
     * 发送消息.
     *
     * @param session 连接对象
     * @param message 消息
     * @throws IOException 异常.
     */
    private void sendMessage(final Session session, final String message) throws IOException {
        if (session != null) {
            session.getBasicRemote().sendText(message);
        }
    }

    /**
     * 给指定用户发送信息.
     *
     * @param userName 用户名
     * @param message  消息
     */
    public void sendInfo(final String userName, final String message) {
        final Session session = SESSION_POOLS.get(userName);
        try {
            sendMessage(session, message);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 建立连接成功调用.
     *
     * @param session  连接
     * @param userName 用户名
     */
    @OnOpen
    public void onOpen(final Session session, @PathParam(value = "sid") final String userName) {
        SESSION_POOLS.put(userName, session);
        addOnlineCount();
        log.info(userName + "加入webSocket！当前人数为" + ONLINE_NUM);
        try {
            sendMessage(session, "欢迎" + userName + "加入连接！");
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭连接时调用.
     *
     * @param userName 用户名
     */
    @OnClose
    public void onClose(@PathParam(value = "sid") final String userName) {
        SESSION_POOLS.remove(userName);
        subOnlineCount();
        log.info(userName + "断开webSocket连接！当前人数为" + ONLINE_NUM);
    }

    /**
     * 收到客户端信息.
     *
     * @param message 信息
     */
    @OnMessage
    public void onMessage(final String message) {
        log.info("收到信息:" + message);
        for (final Session session : SESSION_POOLS.values()) {
            try {
                sendMessage(session, message);
            } catch (final Exception e) {
                log.error("信息错误", e);
            }
        }
    }

    /**
     * 错误时调用.
     *
     * @param session   连接
     * @param throwable 异常
     */
    @OnError
    public void onError(final Session session, final Throwable throwable) {
        log.error("WebSocket错误", throwable);
    }

    /**
     * 增加在线用户.
     */
    private static void addOnlineCount() {
        ONLINE_NUM.incrementAndGet();
    }

    /**
     * 减少在线用户.
     */
    private static void subOnlineCount() {
        ONLINE_NUM.decrementAndGet();
    }
}
