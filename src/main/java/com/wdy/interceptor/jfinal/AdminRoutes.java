package com.wdy.interceptor.jfinal;

import com.jfinal.config.Routes;
import com.wdy.controller.hello.HelloController;
import com.wdy.controller.swagger.WdySwaggerController;

/**
 * Created by wgch on 2019/3/6.
 * 后端路由，Routes级别拦截器是指在Routes中添加的拦截器
 */
public class AdminRoutes extends Routes {
    @Override
    public void config() {
        // 此处配置 Routes 级别的拦截器，可配置多个
        addInterceptor(new RoutesInterceptor());
        add("/hello", HelloController.class);
        add("/wdySwagger", WdySwaggerController.class);

//        System.out.println("-->>调用了Route级别拦截器。。。");
    }
}
