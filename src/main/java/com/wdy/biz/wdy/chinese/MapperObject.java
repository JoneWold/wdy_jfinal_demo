package com.wdy.biz.wdy.chinese;


import com.alibaba.fastjson.JSONObject;
import com.wdy.dto.stroke.StrokeMap;
import io.undertow.util.FileUtils;

import java.io.InputStream;

/**
 * @author ouyangpeng
 */
public class MapperObject {
    public StrokeMap getStrokeMapper() {
        StrokeMap mapper = null;
//        ObjectInputStream is = null;
        try {
//            is = new ObjectInputStream(MapperObject.class.getResourceAsStream("/chinese/stroke.s"));
//            mapper = (StrokeMap) is.readObject();
            InputStream inputStream = MapperObject.class.getResourceAsStream("/chinese/stroke.json");
            String content = FileUtils.readFile(inputStream);
            mapper = JSONObject.parseObject(content, StrokeMap.class);
        } catch (Exception e) {
            e.printStackTrace();
//        } finally {
//            try {
//                if (is != null)
//                    is.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
        return mapper;
    }
}
