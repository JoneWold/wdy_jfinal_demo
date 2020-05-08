package com.wdy.biz.file.controller;

import cn.hutool.core.date.DateUtil;
import com.wdy.utils.WordToHtmlUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.FieldsDocumentPart;
import org.apache.poi.hwpf.usermodel.Field;
import org.apache.poi.hwpf.usermodel.Fields;
import org.apache.poi.hwpf.usermodel.Range;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.wdy.constant.CommonConstant.*;

/**
 * @author wgch
 * @Description word 文档操作
 * @date 2020/4/10 17:23
 */
public class WordController {

    public static void main(String[] args) {
        readHtml();
        wordtoHtml();

        // 模板路径
        String filePath = PATH_DOWNLOAD + "ftl/wordFtl" + SEPARATOR + "wordFtl.doc";
        // 填充数据 (char) 11 换行
        Map<String, String> map = new HashMap<>(1);
        map.put("date", "2019年××月××日 （星期××）" + (char) 11 +
                "\t09:30  谈话调研推荐" + (char) 11 +
                "\t11:30  召开部务会，确定会议推荐参考人选" + (char) 11 +
                "\t17:00  召开部务会，确定。。。");
        map.put("address", "市委组织部机关");

        readwriteWord(filePath, map);
    }

    /**
     * word html 互转
     */
    private static void wordtoHtml() {
        String ftlPath = "D:\\wdy\\wdy_jfinal_demo\\download\\ftl\\wordFtl\\MzIntroduceFtl.doc";
        try {
            String html = WordToHtmlUtil.Word2003ToHtml(ftlPath);
            System.out.println(html);

            String toPath = PATH_TARGET + DateUtil.format(new Date(), "yyyyMMdd") + ".html";
            WordToHtmlUtil.html2Word(html, toPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void readHtml() {
        String htmlStr = "<html><p class=\\\"MsoNormal\\\" align=\\\"center\\\" style=\\\"color: rgb(0, 0, 0); font-size: medium; text-align: center; line-height: 31pt;\\\"><b><span style=\\\"font-family: 方正小标宋简体; font-size: 24pt;\\\"><font face=\\\"方正小标宋简体\\\">部机关</font></span></b><b><span style=\\\"font-family: 方正小标宋简体; font-size: 24pt;\\\">XX</span></b><b><span style=\\\"font-family: 方正小标宋简体; font-size: 24pt;\\\"><font face=\\\"方正小标宋简体\\\">领导干部选拔民主推荐和</font></span></b><b><span style=\\\"font-family: 方正小标宋简体; font-size: 24pt;\\\"><o:p></o:p></span></b></p><p class=\\\"MsoNormal\\\" align=\\\"center\\\" style=\\\"color: rgb(0, 0, 0); font-size: medium; text-align: center; line-height: 31pt;\\\"><b><span style=\\\"font-family: 方正小标宋简体; font-size: 24pt;\\\"><font face=\\\"方正小标宋简体\\\">考察工作方案</font></span></b><b><span style=\\\"font-family: 方正小标宋简体; font-size: 24pt;\\\"><o:p></o:p></span></b></p><p class=\\\"MsoNormal\\\" align=\\\"center\\\" style=\\\"color: rgb(0, 0, 0); font-size: medium; text-align: center; line-height: 31pt;\\\"><span style=\\\"font-family: 方正小标宋简体; font-size: 18pt;\\\"><o:p>&nbsp;</o:p></span></p><p class=\\\"MsoNormal\\\" style=\\\"color: rgb(0, 0, 0); font-size: medium; text-indent: 36pt; line-height: 31pt;\\\"><span style=\\\"font-family: 方正黑体_GBK; font-size: 18pt;\\\"><font face=\\\"方正黑体_GBK\\\">一、时</font> &nbsp;<font face=\\\"方正黑体_GBK\\\">间</font></span><span style=\\\"font-family: 方正黑体_GBK; font-size: 18pt;\\\"><o:p></o:p></span></p><p class=\\\"MsoNormal\\\" style=\\\"color: rgb(0, 0, 0); font-size: medium; text-indent: 36pt; line-height: 31pt;\\\"><span style=\\\"font-family: 方正仿宋_GBK; font-size: 18pt;\\\">201</span><span style=\\\"font-family: 方正仿宋_GBK; font-size: 18pt;\\\">9</span><span style=\\\"font-family: 方正仿宋_GBK; font-size: 18pt;\\\"><font face=\\\"方正仿宋_GBK\\\">年</font></span><span style=\\\"font-family: 方正仿宋_GBK; font-size: 18pt;\\\">XX</span><span style=\\\"font-family: 方正仿宋_GBK; font-size: 18pt;\\\"><font face=\\\"方正仿宋_GBK\\\">月</font></span><span style=\\\"font-family: 方正仿宋_GBK; font-size: 18pt;\\\">XX</span><span style=\\\"font-family: 方正仿宋_GBK; font-size: 18pt;\\\"><font face=\\\"方正仿宋_GBK\\\">日（星期</font></span><span style=\\\"font-family: 方正仿宋_GBK; font-size: 18pt;\\\">XX</span><span style=\\\"font-family: 方正仿宋_GBK; font-size: 18pt;\\\"><font face=\\\"方正仿宋_GBK\\\">）</font></span><span style=\\\"font-family: 方正仿宋_GBK; font-size: 18pt;\\\"><o:p></o:p></span></p><p class=\\\"MsoNormal\\\" style=\\\"color: rgb(0, 0, 0); font-size: medium; text-indent: 54pt; line-height: 31pt;\\\"><span style=\\\"font-family: 方正仿宋_GBK; font-size: 18pt;\\\">09:30 </span><span style=\\\"font-family: 方正仿宋_GBK; font-size: 18pt;\\\"><font face=\\\"方正仿宋_GBK\\\">谈话调研推荐</font></span><span style=\\\"font-family: 方正仿宋_GBK; font-size: 18pt;\\\"><o:p></o:p></span></p><p class=\\\"MsoNormal\\\" style=\\\"color: rgb(0, 0, 0); font-size: medium; text-indent: 54pt; line-height: 31pt;\\\"><span style=\\\"font-family: 方正仿宋_GBK; font-size: 18pt;\\\">11:30 召开部务会，确定会议推荐参考人选</span><span style=\\\"font-family: 方正仿宋_GBK; font-size: 18pt;\\\"><o:p></o:p></span></p><p class=\\\"MsoNormal\\\" style=\\\"color: rgb(0, 0, 0); font-size: medium; text-indent: 54pt; line-height: 31pt;\\\"><span style=\\\"font-family: 方正仿宋_GBK; font-size: 18pt;\\\">14:00 召开干部大会，进行会议推荐</span><span style=\\\"font-family: 方正仿宋_GBK; font-size: 18pt;\\\"><o:p></o:p></span></p><p class=\\\"MsoNormal\\\" style=\\\"color: rgb(0, 0, 0); font-size: medium; text-indent: 54pt; line-height: 31pt;\\\"><span style=\\\"font-family: 方正仿宋_GBK; font-size: 18pt;\\\">17:00 召开部务会，确定考察人选</span><span style=\\\"font-family: 方正仿宋_GBK; font-size: 18pt;\\\"><o:p></o:p></span></p><p class=\\\"MsoNormal\\\" style=\\\"color: rgb(0, 0, 0); font-size: medium; text-indent: 36pt; line-height: 31pt;\\\"><span style=\\\"font-family: 方正仿宋_GBK; font-size: 18pt;\\\">2019年</span><span style=\\\"font-family: 方正仿宋_GBK; font-size: 18pt;\\\">XX</span><span style=\\\"font-family: 方正仿宋_GBK; font-size: 18pt;\\\"><font face=\\\"方正仿宋_GBK\\\">月</font></span><span style=\\\"font-family: 方正仿宋_GBK; font-size: 18pt;\\\">XX</span><span style=\\\"font-family: 方正仿宋_GBK; font-size: 18pt;\\\"><font face=\\\"方正仿宋_GBK\\\">日（星期</font></span><span style=\\\"font-family: 方正仿宋_GBK; font-size: 18pt;\\\">XX</span><span style=\\\"font-family: 方正仿宋_GBK; font-size: 18pt;\\\"><font face=\\\"方正仿宋_GBK\\\">）</font></span><span style=\\\"font-family: 方正仿宋_GBK; font-size: 18pt;\\\"><o:p></o:p></span></p><p class=\\\"MsoNormal\\\" style=\\\"color: rgb(0, 0, 0); font-size: medium; text-indent: 36pt; line-height: 31pt;\\\"><span style=\\\"font-family: 方正仿宋_GBK; font-size: 18pt;\\\">&nbsp;&nbsp;09:30 开展考察</span><span style=\\\"font-family: 方正仿宋_GBK; font-size: 18pt;\\\"><o:p></o:p></span></p><p class=\\\"MsoNormal\\\" style=\\\"color: rgb(0, 0, 0); font-size: medium; text-indent: 36pt; line-height: 31pt;\\\"><span style=\\\"font-family: 方正黑体_GBK; font-size: 18pt;\\\"><font face=\\\"方正黑体_GBK\\\">二、地</font> &nbsp;<font face=\\\"方正黑体_GBK\\\">点</font></span><span style=\\\"font-family: 方正黑体_GBK; font-size: 18pt;\\\"><o:p></o:p></span></p><p class=\\\"MsoNormal\\\" style=\\\"color: rgb(0, 0, 0); font-size: medium; text-indent: 36pt; line-height: 31pt;\\\"><span style=\\\"font-family: 方正仿宋_GBK; font-size: 18pt;\\\"><font face=\\\"方正仿宋_GBK\\\">市委组织部机关</font></span><span style=\\\"font-family: 方正黑体_GBK; font-size: 18pt;\\\"><o:p></o:p></span></p><p class=\\\"MsoNormal\\\" style=\\\"color: rgb(0, 0, 0); font-size: medium; text-indent: 36pt; line-height: 31pt;\\\"><span style=\\\"font-family: 方正黑体_GBK; font-size: 18pt;\\\"><font face=\\\"方正黑体_GBK\\\">三、民主推荐</font></span><span style=\\\"font-family: 方正黑体_GBK; font-size: 18pt;\\\"><o:p></o:p></span></p><p class=\\\"MsoNormal\\\" style=\\\"color: rgb(0, 0, 0); font-size: medium; text-indent: 36pt; line-height: 31pt;\\\"><span style=\\\"font-family: 方正楷体_GBK; font-size: 18pt;\\\"><font face=\\\"方正楷体_GBK\\\">（一）推荐职位</font></span><span style=\\\"font-family: 方正仿宋_GBK; font-size: 18pt;\\\"><o:p></o:p></span></p><p class=\\\"MsoNormal\\\" style=\\\"color: rgb(0, 0, 0); font-size: medium; text-indent: 36pt; line-height: 31pt;\\\"><span style=\\\"font-family: 方正仿宋_GBK; font-size: 18pt;\\\">XX</span><span style=\\\"font-family: 方正仿宋_GBK; font-size: 18pt;\\\"><font face=\\\"方正仿宋_GBK\\\">领导干部</font></span><span style=\\\"font-family: 方正仿宋_GBK; font-size: 18pt;\\\">XX</span><span style=\\\"font-family: 方正仿宋_GBK; font-size: 18pt;\\\"><font face=\\\"方正仿宋_GBK\\\">名左右</font></span><span style=\\\"font-family: 方正仿宋_GBK; font-size: 18pt;\\\"><font face=\\\"方正仿宋_GBK\\\">。</font></span><span style=\\\"font-family: 方正仿宋_GBK; font-size: 18pt;\\\"><o:p></o:p></span></p><p class=\\\"MsoNormal\\\" style=\\\"color: rgb(0, 0, 0); font-size: medium; text-indent: 36pt; line-height: 31pt;\\\"><span style=\\\"font-family: 方正楷体_GBK; font-size: 18pt;\\\"><font face=\\\"方正楷体_GBK\\\">（二）任职条件</font></span><span style=\\\"font-family: 方正楷体_GBK; font-size: 18pt;\\\"><o:p></o:p></span></p></html>";
        Document document = Jsoup.parse(htmlStr);
        String html = StringEscapeUtils.unescapeHtml(htmlStr);
        String word = htmlStr.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "").replaceAll("[(/>)<]", "");
        System.out.println(word);

        String regex = "（一）推荐职位(.)*（二）任职条件";
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(word);
        if (matcher.find()) {
            System.out.println(matcher.group(0));
        }
//        String introduceJob = word.substring(word.indexOf("（一）推荐职位") + 7, word.indexOf("（二）任职条件"));
//        String introduceDate = word.substring(word.indexOf("一、时 间") + 5, word.indexOf("一、时 间") + 16);
//        System.out.println("推荐职位：" + introduceJob);
//        System.out.println("推荐时间：" + introduceDate);
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
            out = new FileOutputStream(PATH_TARGET + toFileName, true);
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
