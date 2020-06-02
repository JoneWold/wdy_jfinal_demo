package com.wdy.biz.file.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.aspose.words.License;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.HyperLinkTextRenderData;
import com.jfinal.plugin.activerecord.Record;
import com.wdy.biz.file.aspose.WordHyperLink;
import com.wdy.utils.WordToHtmlUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.FieldsDocumentPart;
import org.apache.poi.hwpf.usermodel.Field;
import org.apache.poi.hwpf.usermodel.Fields;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xwpf.usermodel.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.wdy.constant.CommonConstant.*;

/**
 * @author wgch
 * @Description word 文档操作
 * @date 2020/4/10 17:23
 */
public class WordController {
    static {
        License license = new License();
        try {
            license.setLicense(new ByteArrayInputStream(new byte[0]));
        } catch (Exception var2) {
            var2.printStackTrace();
            throw new RuntimeException(var2);
        }
    }

    public static void main(String[] args) throws Exception {
        readHtml();
//        wordtoHtml();
//        readwriteWord();
//        addHyperLink();
//        addHyperLink2();
//        addHyperLinkDemo();
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

        // [\s\S]*是完全通配的意思
        String regex = "（一）推荐职位([\\s\\S]*)（二）任职条件";
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
     */
    private static void readwriteWord() throws Exception {
        // word模板路径和名称
        String filePath = PATH_DOWNLOAD + "ftl/wordFtl" + SEPARATOR + "wordFtl.doc";
        // 填充数据 (char) 11 换行
        Map<String, String> map = new HashMap<>(1);
        map.put("date", "2019年××月××日 （星期××）" + (char) 11 +
                "\t09:30  谈话调研推荐" + (char) 11 +
                "\t11:30  召开部务会，确定会议推荐参考人选" + (char) 11 +
                "\t17:00  召开部务会，确定。。。");
        map.put("address", "市委组织部机关");

        //读取word模板
        FileInputStream in = new FileInputStream(new File(filePath));
        HWPFDocument hdt = new HWPFDocument(in);
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
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String toFileName = DateUtil.format(new Date(), "yyyyMMddHHmmsss") + ".doc";
        FileOutputStream out = new FileOutputStream(PATH_TARGET + toFileName, true);

        hdt.write(outputStream);
        //输出字节流
        out.write(outputStream.toByteArray());
        out.close();
        outputStream.close();
    }


    /**
     * 超链接 poi-tl
     */
    private static void addHyperLink() throws Exception {
        List<Record> a01List = new ArrayList<>();
        a01List.add(new Record().set("A0000", "11111").set("name", "张三"));
        a01List.add(new Record().set("A0000", "22222").set("name", "李四"));
        a01List.add(new Record().set("A0000", "33333").set("name", "王五"));
        // 源文件
        String filePath = PATH_DOWNLOAD + "ftl/wordFtl" + SEPARATOR + "wordFtl.doc";
        File file = new File(filePath);
        String suffix = file.getName().substring(file.getName().lastIndexOf("."));
        // doc 转为 docx
        if ((".doc").equals(suffix)) {
            FileInputStream in = new FileInputStream(file);
            com.aspose.words.Document document = new com.aspose.words.Document(in);
            File toFile = new File(PATH_TARGET + "aaa.docx");
            document.save(toFile.getPath());
            in.close();
            file = toFile;
        }
        // word指定字段填充标签
        FileInputStream inputStream = new FileInputStream(file);
        XWPFDocument document = new XWPFDocument(inputStream);
        List<String> names = a01List.stream().map(e -> e.getStr("name")).collect(Collectors.toList());
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            List<XWPFRun> runs = paragraph.getRuns();
            // 一个XWPFRun代表具有相同属性的一个区域：一段文本
            for (XWPFRun run : runs) {
                String oneparaString = run.getText(run.getTextPosition());
                // 清除超链接
//                if (run instanceof XWPFHyperlinkRun) {
//                    XWPFHyperlinkRun rr = (XWPFHyperlinkRun) run;
//                    XWPFHyperlink hyperlink = rr.getHyperlink(document);
//                    if (ObjectUtil.isNotNull(hyperlink)) {
//                        paragraph.removeRun(0);
//                    }
//                }
                if (StrUtil.isBlank(oneparaString)) {
                    continue;
                }
                for (String name : names) {
                    if (oneparaString.contains("{{" + name + "}}")) {
                        continue;
                    }
                    oneparaString = oneparaString.replace(name, "{{" + name + "}}");
                }
                run.setText(oneparaString, 0);
            }
        }
        // 生成带有标签的word文档
        String destPath = PATH_TARGET + "b.docx";
        ByteArrayOutputStream ostream = new ByteArrayOutputStream();
        FileOutputStream out = new FileOutputStream(new File(destPath));
        document.write(out);
        out.write(ostream.toByteArray());
        out.flush();
        document.close();
        inputStream.close();
        // poi-tl 超链接
        XWPFTemplate template = XWPFTemplate.compile(destPath);
        Map<String, Object> map = new HashMap<>();
        for (Record record : a01List) {
            String name = record.getStr("name");
            map.put(name, new HyperLinkTextRenderData(name, "http://deepoove.com"));
        }
        template.render(map);
        template.writeToFile(PATH_TARGET + "addHyperLink.docx");
        template.close();
    }

    /**
     * 超链接 aspose
     */
    private static void addHyperLink2() throws Exception {
        List<Record> a01List = new ArrayList<>();
        a01List.add(new Record().set("A0000", "11111").set("name", "张三"));
        String filePath = PATH_DOWNLOAD + "ftl/wordFtl" + SEPARATOR + "wordFtl.doc";
        File file = new File(filePath);
        IdentityHashMap<String, String> identityMap = new IdentityHashMap<>();
        for (Record record : a01List) {
            String a0000 = record.getStr("A0000");
            String keyWord = record.getStr("name");
            keyWord = keyWord.replaceAll(" ", "");
            boolean flag = keyWord.length() != 2;
            if (flag) {
                identityMap.put(keyWord, a0000);
            } else {
                StringBuffer buffer = new StringBuffer(keyWord);
                identityMap.put(keyWord, a0000);
                identityMap.put(buffer.insert(1, " ").toString(), a0000);
                buffer = new StringBuffer(keyWord);
                identityMap.put(buffer.insert(1, "  ").toString(), a0000);
                buffer = new StringBuffer(keyWord);
                identityMap.put(buffer.insert(1, "\u3000").toString(), a0000);
                buffer = new StringBuffer(keyWord);
                identityMap.put(buffer.insert(1, "\u00a0").toString(), a0000);
            }
        }

        com.aspose.words.Document document = new com.aspose.words.Document(new FileInputStream(file));
        for (Map.Entry<String, String> entry : identityMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            document.getRange().replace(Pattern.compile(key), new WordHyperLink(document, key, value), false);
        }
        document.save(PATH_TARGET + "addHyperLink2.docx");
    }


    /**
     * 超链接 poi
     */
    private static void addHyperLinkDemo() throws Exception {
        String text = "测试超链接HyperLink";
        XWPFDocument document = new XWPFDocument();
        XWPFParagraph paragraph = document.createParagraph();
        // 添加链接作为外部关系
        String id = paragraph
                .getDocument()
                .getPackagePart()
                .addExternalRelationship("www.baidu.com",
                        XWPFRelation.HYPERLINK.getRelation()).getId();
        // 附加链接并将其绑定到关系
        CTHyperlink cLink = paragraph.getCTP().addNewHyperlink();
        cLink.setId(id);

        // 创建链接文本
        CTText ctText = CTText.Factory.newInstance();
        ctText.setStringValue(text);
        CTR ctr = CTR.Factory.newInstance();
        CTRPr rpr = ctr.addNewRPr();

        //设置超链接样式
        CTColor color = CTColor.Factory.newInstance();
        color.setVal("0000FF");
        rpr.setColor(color);
        rpr.addNewU().setVal(STUnderline.SINGLE);

        //设置字体
        CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
        fonts.setAscii("微软雅黑");
        fonts.setEastAsia("微软雅黑");
        fonts.setHAnsi("微软雅黑");

        //设置字体大小
        CTHpsMeasure sz = rpr.isSetSz() ? rpr.getSz() : rpr.addNewSz();
        sz.setVal(new BigInteger("24"));

        ctr.setTArray(new CTText[]{ctText});
        // 将链接的文本插入到链接中
        cLink.setRArray(new CTR[]{ctr});

        //设置段落居中
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        paragraph.setVerticalAlignment(TextAlignment.CENTER);

        FileOutputStream outputStream = new FileOutputStream(PATH_TARGET + "addHyperLinkDemo.docx");
        document.write(outputStream);
        document.close();
        outputStream.close();
    }


}
