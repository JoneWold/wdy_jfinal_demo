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

    /**
     * Handler 与 Interceptor
     * <p>
     * 1、handler 处于更早的一个层级，可以拦截所有请求，包括静态资源的请求。而 interceptor 只能拦截 Controller 中定义的 action。
     * 2、handler 的拦截范围总全局的，而 interceptor 的拦截范围可以配置成：method、class、global 三个级别。
     * 3、只要是 interceptor 可以满足需求的情况就用 interceptor，否则才考虑用 handler。
     * <p>
     * 想要深入理解 handler，只需要做一个简单的 handler，然后里头设置一个断点，会看到只要有请求过来都会被拦截到。
     * handler、interceptor 在本质上都是 AOP 的一种实现，都是为了在你的目标代码之前、之后切处一些额外的行为。
     */
    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        // JFinal将不再处理带有“/ws/socket”的请求，交由webSocket处理
        int index = target.indexOf("/ws/socket.ws");
        if (index == -1) {
            next.handle(target, request, response, isHandled);
        }
    }
}
