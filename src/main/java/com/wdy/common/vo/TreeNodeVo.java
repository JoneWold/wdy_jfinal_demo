package com.wdy.common.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author TMW
 * @version 1.0
 * @Description: 返回前端树形结构
 * @date 2019/3/18 17:00
 */
@Data
@ToString
@NoArgsConstructor
public class TreeNodeVo implements Serializable {
    private static final long serialVersionUID = 6068282489180800070L;
    private String id;
    private String name;
    private String code;
    private String parentCode;
    private List<TreeNodeVo> children;

    public TreeNodeVo(String id, String name, String code, String parentCode) {
        this.id = id;
        this.parentCode = parentCode;
        this.name = name;
        this.code = code;
    }
}
