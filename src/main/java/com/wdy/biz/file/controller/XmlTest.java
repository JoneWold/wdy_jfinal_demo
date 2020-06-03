package com.wdy.biz.file.controller;

import cn.hutool.core.util.XmlUtil;
import com.jfinal.plugin.activerecord.Record;
import org.apache.commons.lang.StringEscapeUtils;
import org.dom4j.Attribute;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.w3c.dom.Document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.wdy.constant.CommonConstant.PATH_TARGET;

/**
 * @author wgch
 * @Description xml 操作
 * @date 2019/7/26 12:16
 */
public class XmlTest {
    private static final String a01Path = "D:\\wdy\\wdy_jfinal_demo\\src\\main\\webapp\\WEB-INF\\view\\book\\A01.xml";
    private static final String QUXIAN_STR = "区县";
    private static final String SJBM_STR = "市级部门";
    private static final String LXCS_STR = "联系处室";
    private static final String GDYX_STR = "高等院校";

    public static void main(String[] args) {
//        getA01XmlData(a01Path);

        escape();
        unescape();
        getOrgXzConfig();
    }

    /**
     * xml 字符串转义
     */
    private static void escape() {
        String value = "\"<br />\"";
        String s = org.apache.commons.lang3.StringEscapeUtils.escapeXml10(value);
        System.out.println(s);
    }

    /**
     * xml 反转义
     */
    private static void unescape() {
        String value = "<xml><row \"A0000\"=\"现在 &lt;\"/> </xml>";
        String s = StringEscapeUtils.unescapeXml(value);
        System.out.println(s);
    }

    /**
     * 生成xml文件
     *
     * @param document
     * @throws IOException
     */
    public static void writer(org.dom4j.Document document) throws IOException {
        // 设置XML文档输出格式
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("GB2312");
        XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(new File(PATH_TARGET + "book2.xml")), format);
        xmlWriter.write(document);
        xmlWriter.close();
    }

    /**
     * 获取分析查询筛选项
     */
    private static void getOrgXzConfig() {
        SAXReader saxReader = new SAXReader();
        File file = new File("D:\\wdy\\wdy_jfinal_demo\\src\\main\\webapp\\WEB-INF\\view\\config\\OrgXzConfig.xml");
        org.dom4j.Document document = null;
        try {
            document = saxReader.read(file);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element xmlElement = document.getRootElement();
        Iterator<Element> iterator = xmlElement.elementIterator();
        List<Record> county = new ArrayList<>();
        List<Record> cityDepart = new ArrayList<>();
        List<Record> lxcs = new ArrayList<>();
        List<Record> colleges = new ArrayList<>();
        while (iterator.hasNext()) {
            Element next = iterator.next();
            List<Attribute> attributes = next.attributes();
            String orgXz = attributes.get(0).getValue();
            Iterator<Element> xz = next.elementIterator();
            while (xz.hasNext()) {
                Element nextChild = xz.next();
                List<Attribute> attributesChild = nextChild.attributes();
                Record record = new Record();
                for (Attribute attr : attributesChild) {
                    record.set(attr.getName(), attr.getValue());
                }
                if (QUXIAN_STR.equals(orgXz)) {
                    county.add(record);
                } else if (SJBM_STR.equals(orgXz)) {
                    cityDepart.add(record);
                } else if (LXCS_STR.equals(orgXz)) {
                    lxcs.add(record);
                } else if (GDYX_STR.equals(orgXz)) {
                    colleges.add(record);
                }
            }
        }
        System.out.println(county);
        System.out.println(cityDepart);
        System.out.println(lxcs);
        System.out.println(colleges);
    }

    public static List<Record> getA01XmlData(String filePath) {
        File file = new File(a01Path);
        Document document = XmlUtil.readXML(file);
        System.out.println(document);

        return null;
    }


}
