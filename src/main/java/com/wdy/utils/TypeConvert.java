package com.wdy.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hdy on 2018/4/17.
 */
public class TypeConvert {


    public static boolean isNull(Object... params) {
        if (null == params || params.toString().equals("undefined"))
            return true;
        for (Object o : params) {
            if (null == o || o.equals("null") || o.toString().length() == 0 || o.toString().equals("undefined")) {
                return true;
            }
        }
        return false;
    }

    public static JSONObject toJSONObject(String jsonStr) {
        return TypeConvert.isNull(jsonStr) ? new JSONObject() : JSON.parseObject(jsonStr);
    }

    /**
     * 获取ip地址
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


    /**
     * 将汉字转成编码
     *
     * @param str
     * @return
     */
    public static String chinaToUnicode(String str) {
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            int chr1 = str.charAt(i);
            if ((chr1 >= 19968) && (chr1 <= 171941))
                result = result + "\\u" + Integer.toHexString(chr1);
            else {
                result = result + str.charAt(i);
            }
        }
        return result;
    }

    /**
     * 随即生产几位数数字
     *
     * @param several 生产几位数
     */
    public static int random(int several) {
        if (several > 10 || several < 1) {
            return 0;
        }
        int k = 1;
        for (int i = 1; i < several; i++) {
            k = Integer.parseInt(k + "0");
        }
        return (int) ((Math.random() * 9 + 1) * k);
    }
}