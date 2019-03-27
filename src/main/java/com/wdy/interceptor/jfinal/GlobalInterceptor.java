package com.wdy.interceptor.jfinal;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

/**
 * Created by wgch on 2019/3/5.
 */
public class GlobalInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation invocation) {
//        System.out.println("Before invoking: " + invocation.getActionKey());
        // 调用目标方法
        invocation.invoke();
//        System.out.println("After invoking: " + invocation.getActionKey());
        System.out.println("调用了globalInterceptor拦截器");
    }
}
