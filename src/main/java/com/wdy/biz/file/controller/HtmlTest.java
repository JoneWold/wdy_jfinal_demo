package com.wdy.biz.file.controller;

import cn.hutool.core.date.DateUtil;
import com.wdy.utils.WordToHtmlUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.wdy.constant.CommonConstant.PATH_TARGET;

/**
 * @author wgch
 * @Description
 * @date 2020/6/3
 */
public class HtmlTest {

    public static void main(String[] args) {
        readHtml();
        wordToHtml();

    }

    /**
     * word html 互转
     */
    private static void wordToHtml() {
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


}
