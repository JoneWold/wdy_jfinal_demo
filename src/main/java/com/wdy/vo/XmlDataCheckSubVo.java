package com.wdy.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author wgch
 * @Description Xml人员机构关键信息效验vo
 * @date 2019/11/25
 */
@Data
public class XmlDataCheckSubVo {
    private String id;
    private String orgId;
    private String orgName;
    private String shortName;
    private String ErrorMessage;
    private String A0000;
    private String A0165;
    private String name;
    private String cardID;
    private String A0192A;
    private Date checkTime;
}
