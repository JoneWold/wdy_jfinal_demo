package com.wdy.interceptor;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
        HttpServletRequest request = inv.getController().getRequest();
        NotNull notNull = inv.getMethod().getAnnotation(NotNull.class);
        if (notNull != null) {
            // 获取请求数据
            String[] paras = notNull.paras();
            for (String parameter : paras) {
                Object obj;
                try {
                    // 先对url传参进行参数校验
                    obj = request.getParameter(parameter);
                } catch (Exception e) {
                    inv.getController().renderJson(new OutMessage<>(Status.NOT_NULL_ERROR));
                    return;
                }
                if (ObjectUtil.isEmpty(obj)) {
                    // 如果url没有参数,在对请求体中的参数进行校验
                    String rawData = inv.getController().getRawData();
                    JSONObject jsonObject = null;
                    JSONArray jsonArray = null;
                    try {
                        JSONObject parseObject = JSON.parseObject(rawData);
                        // 请求体内没有参数
                        if (parseObject == null) {
                            String msg = "参数" + parameter + "不能为null";
                            inv.getController().renderJson(new OutMessage<>(Status.FAIL, msg));
                            return;
                        }
                        Object data = parseObject.get("data");
                        // 请求体内是数组 或者 对象
                        if (data.getClass().equals(JSONArray.class)) {
                            jsonArray = (JSONArray) data;
                        } else {
                            jsonObject = (JSONObject) parseObject.get("data");
                        }
                    } catch (PermissionException pe) {
                        throw new PermissionException("JSON转换失败");
                    }
                    Object o = null;
                    if (ObjectUtil.isNotNull(jsonObject)) {
                        o = jsonObject.get(parameter);
                    }
                    if (ObjectUtil.isNotNull(jsonArray)) {
                        JSONObject jo = (JSONObject) jsonArray.get(0);
                        o = jo.get(parameter);
                    }
                    if (ObjectUtil.isNull(o)) {
                        String msg = "参数" + parameter + "不能为null";
                        inv.getController().renderJson(new OutMessage<>(Status.NOT_NULL_ERROR, msg));
                        return;
                    }
                }
            }
        }
        inv.invoke();
    }
}
