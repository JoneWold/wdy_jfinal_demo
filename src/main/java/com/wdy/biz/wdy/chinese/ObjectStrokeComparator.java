package com.wdy.biz.wdy.chinese;

import java.lang.reflect.Method;
import java.util.Comparator;

/**
 * @author wgch
 * @Description 汉字笔画排序
 * @date 2020/6/3
 */
public class ObjectStrokeComparator implements Comparator<Object> {
    private MapperFactory factory = MapperFactory.newInstance();
    private Method getMethod = null;

    public ObjectStrokeComparator(Class<?> clazz, String filed) {
        initGetMethod(clazz, filed);
    }


    private void initGetMethod(Class<?> clazz, String filed) {
        if (!String.class.getName().equals(clazz.getName())) {
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equalsIgnoreCase("get" + filed)) {
                    getMethod = method;
                    break;
                }
            }
        }
    }

    @Override
    public int compare(Object o1, Object o2) {
        if (o1 instanceof String && o2 instanceof String) {
            return compareString((String) o1, (String) o2);
        }
        if (getMethod == null) {
            return 0;
        }
        try {
            Object v1 = getMethod.invoke(o1);
            Object v2 = getMethod.invoke(o2);
            if (v1 == null || v2 == null) {
                return 0;
            }
            if (v1 instanceof String && v2 instanceof String) {
                String s1 = (String) v1;
                String s2 = (String) v2;
                return compareString(s1, s2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int compareString(String s1, String s2) {
        if (s1 == null || s2 == null) {
            return 0;
        }
        int point1 = 0;
        int point2 = 0;
        for (int i = 0; i < s1.length() && i < s2.length(); i++) {
            point1 = s1.codePointAt(i);
            point2 = s2.codePointAt(i);
            if (point1 != point2) {
                return factory.stroke(point1) - factory.stroke(point2);
            }
        }
        // 当完全相同时，长度较长的放在后面
        return s1.length() - s2.length();
    }

}
