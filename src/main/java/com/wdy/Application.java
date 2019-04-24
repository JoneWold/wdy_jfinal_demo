package com.wdy;

import com.jfinal.server.undertow.UndertowServer;
import com.wdy.config.WdyConfig;

/**
 * Created by wgch on 2019/3/8.
 */
public class Application {
    public static void main(String[] args) {
        // 方式一：默认启动 Starting Undertow Server http://localhost:80
//        UndertowServer.start(WdyConfig.class);
        // 方式二：读取配置文件启动 Starting Undertow Server http://127.0.0.1:8080
//        UndertowServer.create(WdyConfig.class, "jfinal.properties").start();

//        UndertowServer undertowServer = UndertowServer.create(WdyConfig.class, "jfinal.properties");
//        System.out.println("host--->>" + undertowServer.getUndertowConfig().getHost());
//        System.out.println("port--->>" + undertowServer.getUndertowConfig().getPort());

        UndertowServer.create(WdyConfig.class, "jfinal.properties")
                .configWeb(builder -> {
                    // 配置 WebSocket，MyWebSocket 需使用 ServerEndpoint 注解
                    builder.addWebSocketEndpoint("com.wdy.biz.progress.jfinal.WebSocketController");
                }).start();

    }
}
