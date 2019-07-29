package com.wdy.biz.file.controller;

import cn.hutool.core.util.XmlUtil;
import com.jfinal.plugin.activerecord.Record;
import org.apache.commons.lang.StringEscapeUtils;
import org.w3c.dom.Document;

import java.io.File;
import java.util.List;

/**
 * @author wgch
 * @Description
 * @date 2019/7/26 12:16
 */
public class XmlController {
    public static final String a01Path = "D:\\wdy\\wdy_jfinal_demo\\src\\main\\webapp\\WEB-INF\\view\\book\\A01.xml";

    public static void main(String[] args) {
        getA01XmlData(a01Path);

        String value = "<xml><row \"A0000\"=\"XZ &lt;\"/> </xml>";
        String s = StringEscapeUtils.unescapeXml(value);
        System.out.println(s);
    }

    public static List<Record> getA01XmlData(String filePath) {
        File file = new File(a01Path);
        Document document = XmlUtil.readXML(file);
        System.out.println(document);

        return null;
    }


}
