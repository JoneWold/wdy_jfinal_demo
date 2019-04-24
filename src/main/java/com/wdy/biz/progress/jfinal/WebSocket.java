package com.wdy.biz.progress.jfinal;


import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author wgch
 * @Description
 * @date 2019/4/24 9:30
 */
@ServerEndpoint("/ws/socket.ws")
public class WebSocket {
    private Session session;
    private Logger logger = Logger.getLogger(this.getClass().getName());
    static Map<String, Session> sessionMap = new Hashtable<>();

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        sessionMap.put(session.getId(), session);
    }

    /**
     * 收到客户端消息时触发
     *
     * @return
     * @throws IOException
     */
    @OnMessage
    public void onMessage(Session session, String key) throws IOException {
        System.out.println("来自客户端的消息:" + key);
        //向客户端返回发送过来的消息
        session.getBasicRemote().sendText("wdy 回复：" + key + "");
    }

    @OnMessage
    public void receiveMessage(ByteBuffer b, Session session) {
        for (int i = 0; i < b.capacity(); i++) {
            System.out.println("msg=" + b.getChar(i));
        }

    }

    /**
     * 广播给所有人
     *
     * @param message
     */
    public static void broadcastAll(String type, String message, String data) {
        Set<Map.Entry<String, Session>> set = sessionMap.entrySet();
        for (Map.Entry<String, Session> i : set) {
            try {
                i.getValue().getBasicRemote().sendText("{type:'" + type + "',text:'" + message + "',data:'" + data + "'}");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 异常时触发
     *
     * @param session
     */
    @OnError
    public void onError(Throwable throwable, Session session) {
        sessionMap.remove(session.getId());
        System.err.println("session " + session.getId() + " error:" + throwable);
    }

    /**
     * 关闭连接时触发
     *
     * @param session
     */
    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        sessionMap.remove(session.getId());
        logger.info(String.format("Session %s closed because of %s", session.getId(), closeReason));
    }
}
