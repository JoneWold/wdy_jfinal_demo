package com.wdy.biz.wdy.chinese;


import com.wdy.dto.stroke.Stroke;
import com.wdy.dto.stroke.StrokeMap;

/**
 * @author ouyangpeng
 */
public class MapperFactory {

    private MapperFactory() {
    }

    private final static MapperFactory factory = new MapperFactory();

    private static StrokeMap mapper;

    public static MapperFactory newInstance() {
        if (mapper == null) {
            MapperObject mapperObject = new MapperObject();
            mapper = mapperObject.getStrokeMapper();
        }
        return factory;
    }

    /**
     * 根据point计算顺序
     *
     * @param keyPoint
     * @return
     */
    public Integer stroke(Integer keyPoint) {
        if (keyPoint == null) {
            return -1;
        }
        Stroke stroke = mapper.get(keyPoint);
        if (stroke == null) {
            return -1;
        }
        Integer order = stroke.getOrder();
        return order == null ? -1 : order;
    }

}
