package com.wdy.biz.progress.websocket;


import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wgch
 * @Description webSocket
 * @date 2019/4/23 19:05
 */

@ServerEndpoint("/websocket/{code}")
public class WebSocketListen {

    // 用于记录接入的websocket连接
    private static ConcurrentHashMap<String, WebsocketDto> websocketMap = new ConcurrentHashMap<String, WebsocketDto>();

    /**
     * 连接建立成功调用的方法
     *
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(String code, Session session) {
        WebsocketDto websocketDto = new WebsocketDto();
        websocketDto.setCode(code);
        websocketDto.setSession(session);
        websocketMap.put(code, websocketDto);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(String code) {
        websocketMap.remove(code);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
    }

    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    //发送消息
    public static void sendMessage(String code, String message) throws IOException {
        Session session = websocketMap.get(code).getSession();
        session.getBasicRemote().sendText(message);
    }

    public static ConcurrentHashMap<String, WebsocketDto> getWebsocketMap() {
        return websocketMap;
    }

    public static void setWebsocketMap(ConcurrentHashMap<String, WebsocketDto> websocketMap) {
        WebSocketListen.websocketMap = websocketMap;
    }

}