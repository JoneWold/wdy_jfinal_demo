package com.wdy.message;

import com.alibaba.fastjson.JSON;
import com.jfinal.core.Action;
import com.jfinal.core.Controller;
import com.jfinal.core.paragetter.ParaGetter;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/***
 * 用户转换payload 中的数据为InMessage对象
 * @author 毛文超
 * */
public class InMessageParaGetter extends ParaGetter<InMessage> {

    public InMessageParaGetter(String parameterName, String defaultValue) {
        super(parameterName, defaultValue);
    }

    @Override
    protected InMessage to(String v) {
        return null;
    }

    @Override
    public InMessage get(Action action, Controller c) {
        Method method = action.getMethod();
        Type[] genericParameterTypes = method.getGenericParameterTypes();
        for (Type genericParameterType : genericParameterTypes) {
            ParameterizedType type = (ParameterizedType) genericParameterType;
            Class rawType = (Class) type.getRawType();
            if(InMessage.class.isAssignableFrom(rawType)){
                String rawData = c.getRawData();
                return JSON.parseObject(rawData,genericParameterType);
            }
        }
        return null;
    }
}
