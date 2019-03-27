package com.wdy.interceptor.jfinal;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

/**
 * Created by wgch on 2019/3/6.
 */
public class MethodInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation invocation) {
        invocation.invoke();
        System.out.println("调用了methodInterceptor拦截器");
    }
}
