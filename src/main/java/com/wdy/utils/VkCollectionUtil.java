package com.wdy.utils;

import cn.hutool.core.util.StrUtil;
import com.jfinal.plugin.activerecord.Record;
import com.wdy.generator.postgreSQL.model.CodeValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author TMW
 * @version 1.0
 * @Description: 集合
 * @date 2019/3/21 10:24
 */
public class VkCollectionUtil {

    /**
     * list转map
     *
     * @param collection
     * @return
     */
    public static Map<String, String> listToMap(List<CodeValue> collection) {
        return collection.stream().collect(Collectors.toMap(CodeValue::getCodeValue, CodeValue::getCodeName, (key1, key2) -> key2));
    }

    /**
     * list<Record>转map
     *
     * @param collection
     * @return
     */
    public static Map<String, String> listRecordToMap(List<Record> collection) {

        Map<String, String> map = new HashMap<>();

        collection.forEach(record -> {
            map.put(record.getStr("CODE_VALUE"), record.getStr("CODE_NAME"));
        });
        return map;
    }

    public static Map<String, String> listRecordToMapName(List<Record> collection) {
        Map<String, String> map = new HashMap<>();

        collection.forEach(record -> {
            map.put(record.getStr("CODE_NAME"), record.getStr("CODE_VALUE"));
        });
        return map;
    }

    /**
     * 拼接ids
     *
     * @param ids
     * @return
     */
    public static String getString(List<String> ids) {
        String s = "";
        int i = 0;
        for (String id : ids) {
            if (StrUtil.isNotEmpty(id)) {
                if (i == 0) {
                    s += "'" + id + "'";
                } else {
                    s += "," + "'" + id + "'";
                }
                i++;
            }
        }
        return s;
    }

    /**
     * 拼接ids
     *
     * @param ids
     * @return
     */
    public static String getString(String[] ids) {
        String s = "";
        int i = 0;
        for (String id : ids) {
            if (StrUtil.isNotEmpty(id)) {
                if (i == 0) {
                    s += "'" + id + "'";
                } else {
                    s += "," + "'" + id + "'";
                }
                i++;
            }
        }
        return s;
    }
}
