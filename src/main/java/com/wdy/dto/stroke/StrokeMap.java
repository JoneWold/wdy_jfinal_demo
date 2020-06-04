package com.wdy.dto.stroke;

import lombok.Data;

import java.util.HashMap;

/**
 * @author wgch
 * @date 2020/6/1
 */
@Data
public class StrokeMap extends HashMap<Integer, Stroke> {
    private static final long serialVersionUID = 3857727786160895810L;
    private String code;
    private Stroke stroke;

    public void put(Stroke stroke) {
        if (stroke != null) {
            this.put(stroke.getCode(), stroke);
        }
    }

}
