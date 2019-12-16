package com.wdy.vo;

import com.wdy.generator.postgreSQL.model.A01Temp;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @author wgch
 * @Description
 * @date 2019/12/15
 */
@Data
public class RmbA01TempVo {
    /**
     * 完善后的a01_temp数据
     */
    private List<A01Temp> a01TempNewList;
    /**
     * 待保存的A0000集合
     */
    private HashSet<String> saveA0000Set;
    /**
     * a01_temp中的A0000 -> 匹配到的a01中的A0000
     */
    private Map<String, String> updateA0000toOld;

}
