package com.wdy.message;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

/***
 * 如果action 返回值为outMessage 则render 为json
 * @author 毛文超
 * */
public class OutMessageInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation inv) {
        inv.invoke();
        Object returnValue = inv.getReturnValue();
        if (returnValue instanceof OutMessage) {
            inv.getController().renderJson(returnValue);
        }
    }
}
