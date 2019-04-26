package com.wdy.biz.progress.websocket;

import com.jfinal.handler.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wgch
 * @Description Handler拦截webSocket请求
 * @date 2019/4/24 9:22
 */
public class WebSocketHandler extends Handler {

    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        // JFinal将不再处理带有“/ws/socket”的请求，交由webSocket处理
        int index = target.indexOf("/ws/socket.ws");
        if (index == -1) {
            next.handle(target, request, response, isHandled);
        }
    }
}
