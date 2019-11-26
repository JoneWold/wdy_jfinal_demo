package com.wdy.vo;

import lombok.Data;

import java.util.List;

/**
 * @author wgch
 * @Description Xml人员机构关键信息效验
 * @date 2019/07/16
 */
@Data
public class XmlDataCheckVo {
    private String sameB0114;
    private List<XmlDataCheckSubVo> memList;
    private List<XmlDataCheckSubVo> orgList;
}
