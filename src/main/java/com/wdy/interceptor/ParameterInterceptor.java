package com.wdy.interceptor;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.wdy.annotation.NotNull;
import com.wdy.biz.exception.PermissionException;
import com.wdy.message.OutMessage;
import com.wdy.message.Status;

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
            for (String para : paras) {
                Object obj;
                // 1、先对url传参进行参数校验
                try {
                    obj = request.getParameter(para);
                } catch (Exception e) {
                    inv.getController().renderJson(new OutMessage<>(Status.NOT_NULL_ERROR));
                    return;
                }
                // 2、如果url没有参数,在对请求体中的参数进行校验
                if (obj == null) {
                    String rawData = inv.getController().getRawData();
                    JSONObject jsonObject;
                    try {
                        JSONObject parseObject = JSON.parseObject(rawData);
                        jsonObject = (JSONObject) parseObject.get("data");
                    } catch (PermissionException pe) {
                        throw new PermissionException("JSON转换失败");
                    }
                    Object o = null;
                    if (ObjectUtil.isNotNull(jsonObject)) {
                        o = jsonObject.get(para);
                    }
                    if (ObjectUtil.isNull(o)) {
                        String msg = "参数" + para + "不能为null";
                        inv.getController().renderJson(new OutMessage<>(Status.NOT_NULL_ERROR, msg));
                        return;
                    }
                }
            }
        }
        inv.invoke();
    }
}
