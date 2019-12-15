package com.wdy.vo;

import com.wdy.generator.postgreSQL.model.A01Temp;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author wgch
 * @Description
 * @date 2019/12/15
 */
@Data
public class RmbA01TempVo {
    private List<A01Temp> a01TempList;
    private Map<String, String> tempA0000toOld;
}
