package com.wdy.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.wdy.annotation.NotNull;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wgch
 * @Description 参数拦截器
 * @date 2019/3/26 18:25
 */
public class ParameterInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
        NotNull notNull = inv.getMethod().getAnnotation(NotNull.class);
        if (notNull != null) {
            // 获取请求参数
            String[] paras = notNull.paras();
            HttpServletRequest request = inv.getController().getRequest();

        }
        inv.invoke();
    }
}
