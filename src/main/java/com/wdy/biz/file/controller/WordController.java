package com.wdy.biz.file.controller;

import cn.hutool.core.date.DateUtil;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.FieldsDocumentPart;
import org.apache.poi.hwpf.usermodel.Field;
import org.apache.poi.hwpf.usermodel.Fields;
import org.apache.poi.hwpf.usermodel.Range;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.wdy.constant.CommonConstant.PATH_DOWNLOAD;
import static com.wdy.constant.CommonConstant.SEPARATOR;

/**
 * @author wgch
 * @Description word 文档操作
 * @date 2020/4/10 17:23
 */
public class WordController {

    public static void main(String[] args) {
        // 模板路径
        String filePath = PATH_DOWNLOAD + "ftl" + SEPARATOR + "wordFtl.doc";
        // 填充数据
        Map<String, String> map = new HashMap<>(1);
        map.put("date", "2019年××月××日 （星期××）\n" +
                "\t09:30  谈话调研推荐\n" +
                "\t11:30  召开部务会，确定会议推荐参考人选\n" +
                "\t17:00  召开部务会，确定。。。");
        map.put("address", "市委组织部机关");

        readwriteWord(filePath, map);
    }


    /**
     * 实现对word读取和修改操作
     *
     * @param filePath word模板路径和名称
     * @param map      待填充的数据，从数据库读取
     */
    public static void readwriteWord(String filePath, Map<String, String> map) {
        //读取word模板
        FileInputStream in = null;
        try {
            in = new FileInputStream(new File(filePath));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        HWPFDocument hdt = null;
        try {
            hdt = new HWPFDocument(in);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        Fields fields = hdt.getFields();
        for (Field field : fields.getFields(FieldsDocumentPart.MAIN)) {
            System.out.println(field.getType());
        }

        //读取word文本内容
        Range range = hdt.getRange();
        System.out.println(range.text());
        //替换文本内容
        for (Map.Entry<String, String> entry : map.entrySet()) {
            range.replaceText("${" + entry.getKey() + "}", entry.getValue());
        }
        ByteArrayOutputStream ostream = new ByteArrayOutputStream();
        String toFileName = DateUtil.format(new Date(), "yyyyMMddHHmmsss") + ".doc";
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(PATH_DOWNLOAD + toFileName, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            hdt.write(ostream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //输出字节流
        try {
            out.write(ostream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ostream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
