package com.wdy.biz.file.controller;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;
import com.wdy.dto.Book;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author wgch
 * @Description DOM4J解析xml
 * @date 2019/4/11 15:50
 */
public class XmlDOM4JTest {
    private static ArrayList<Book> bookList = new ArrayList<>();

    public static void main(String[] args) {
        String bookPath = "D:\\wdy\\wdy_jfinal_demo\\src\\main\\webapp\\WEB-INF\\view\\book\\book.xml";
        String a01Path = "D:\\wdy\\wdy_jfinal_demo\\src\\main\\webapp\\WEB-INF\\view\\book\\A01.xml";
//        ArrayList<Book> xmlData = getXmlDataDemo(bookPath);
//        System.out.println(xmlData);
        List<Record> a01XmlData = getA01XmlData(a01Path);
        System.out.println(a01XmlData);
        testJL();
    }


    /**
     * DOM4J解析xml
     *
     * @param filePath
     * @return
     */
    public static ArrayList<Book> getXmlDataDemo(String filePath) {
        // 解析books.xml文件
        // 创建SAXReader的对象reader
        SAXReader reader = new SAXReader();

//        reader.setXMLFilter(new XMLFilterImpl() {
//            @Override
//            public void characters(char[] ch, int start, int length) throws SAXException {
//                if (length == 5) {
//                    if ((int) ch[1] == 10) {
//                        char[] escape = "&#10;".toCharArray();
//                        super.characters(escape, 0, escape.length);
//                        return;
//                    }
//                }
//
//                super.characters(ch, start, length);
//            }
//        });
        try {
            // 通过reader对象的read方法加载books.xml文件,获取docuemnt对象。
            Document document = reader.read(new File(filePath));
            // xml文件保留换行
//            document.getRootElement().addAttribute(QName.get("space", Namespace.XML_NAMESPACE), "preserve");

            // 通过document对象获取根节点bookstore
            Element bookStore = document.getRootElement();
            // 通过element对象的elementIterator方法获取迭代器
            Iterator it = bookStore.elementIterator();
            // 遍历迭代器，获取根节点中的信息（书籍）
            while (it.hasNext()) {
                Book bookModel = new Book();
                Element book = (Element) it.next();
                // 获取book的属性名以及 属性值
                List<Attribute> bookAttrs = book.attributes();
                for (Attribute attr : bookAttrs) {
                    // 属性名
                    String name = attr.getName();
                    // 属性值
                    String value = attr.getValue();
//                    value = value.replaceAll("\r\n", "\n").replaceAll("\n", "\n").replaceAll("\r", "\r");
//                    value = value.replaceAll("\\x0a", "");
                    // &#xD; 回车  &#xA; 换行
//                    value = value.replaceAll("&#xD;", "\r").replaceAll("&#xA;", "\n");
                    // 你可以使用实体&#10; 在XML属性中表示换行符。 &#13; 可以用来表示回车。 Windows风格的CRLF可以表示为&#13;&#10; 。
//                    value = value.replaceAll("&#13;&#10;", "\n");
                    bookModel.setId(value);
                }
                Iterator itt = book.elementIterator();
                while (itt.hasNext()) {
                    Element bookChild = (Element) itt.next();
                    // 节点名
                    String name = bookChild.getName();
                    // 节点值
                    String stringValue = bookChild.getStringValue();
                    // 组装数据
                    if ("name".equals(name)) {
                        bookModel.setName(stringValue);
                    } else if ("author".equals(name)) {
                        bookModel.setAuthor(stringValue);
                    } else if ("year".equals(name)) {
                        bookModel.setYear(stringValue);
                    } else if ("price".equals(name)) {
                        bookModel.setPrice(stringValue);
                    }
                }
                bookList.add(bookModel);
//                writer(document);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookList;
    }

    /**
     * 解析 A01.xml
     *
     * @param filePath
     * @return
     */
    private static List<Record> getA01XmlData(String filePath) {
        List<Record> data = new ArrayList<>();
        SAXReader saxReader = new SAXReader();
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        Document document = null;
        try {
            document = saxReader.read(file);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        Element xmlElement = document.getRootElement();
        Iterator<Element> iterator = xmlElement.elementIterator();
        int i = 0;
        while (iterator.hasNext()) {
            Element dataElement = iterator.next();
            Iterator<Element> it = dataElement.elementIterator();
            while (it.hasNext()) {
                Record record = new Record();
                Element row = it.next();
                List<Attribute> attributes = row.attributes();
                for (Attribute a : attributes) {
                    String name = a.getName();
                    String value = a.getValue();
                    // 简历格式拼接，将空格转换为换行
                    value = setFormatA1701(name, value);
                    record.set(name, value);
                }
                data.add(record);
                i++;
                if (data.size() % 1000 == 0 || !it.hasNext()) {
                    System.out.println("---->>>" + i);
                    data.clear();
                }
            }
        }
        return data;
    }


    private static void testJL() {
        String value = "2005.09--2009.07  重庆邮电大学\n" +
                "2009.07--2010.09  四川省成都市成都昊晟好利来食品厂\n" +
                "2010.09--2015.01  四川省广安市武胜县新型农村合作医疗管理中心（其间：2011.03--2014.07福建师范大学在职本科财务管理专业学习）\n" +
                "2015.01--2015.06  重庆市车管所四分所员工\n" +
                "2015.06--2016.08  待业\n" +
                "2016.08--2017.08  重庆市万州区甘宁镇财政所试用期人员（其间：2015.09——  重庆工商大学在职研究生工商管理专业）\n" +
                "2017.08--        重庆市万州区甘宁镇财政所科员";

        String value2 = "1994.09--1997.07   重庆第一财贸学校财会专业学习；\n" +
                "1997.07--1997.10   待业\n" +
                "1997.10--2001.04   潼南县药品检验所工作（其间借调到潼南县卫生局任会计,1999年05月助理会计师）；\n" +
                "2001.04--2003.11   潼南县药品监督管理局\n" +
                "2003.11--2010.01   食品药品监督管理局（2009.03起副厅局级）潼南县分局（其间:2003.06取得重庆工商大学金融专业自学考试专科学历,2006.02--2009.01重庆工商大学会计专业学习,2006.07副主任科员）\n" +
                "2010.01--2014.12   食品药品监督管理局（2013.12起厅局级）规划财务处主任科员\n" +
                "2014.12--        食品药品监督管理局规划财务处副调研员";

        String a1701 = setFormatA1701("A1701", value);
        System.out.println(a1701);
    }

    /**
     * 格式化简历
     */
    private static String setFormatA1701(String name, String value) {
        if ("A1701".equals(name) && StrKit.notBlank(value)) {
            char[] chars = value.toCharArray();
            StringBuilder builder = new StringBuilder();
            int index = 0;
            List<Integer> indexList = new ArrayList<>();
            for (int i = 1; i < chars.length - 4; i++) {
                StringBuilder sb = new StringBuilder();
                char aChar0 = chars[i - 1];
                char aChar = chars[i];
                char aChar1 = chars[i + 1];
                char aChar2 = chars[i + 2];
                char aChar3 = chars[i + 3];
                // 前一位是空格，当前及后面三位是数字（index最后一次赋值及最后一行的第一个字符的序号）
                if ((aChar0 == 32 || aChar0 == 10) && aChar >= 48 && aChar <= 57 && aChar1 >= 48 && aChar1 <= 57
                        && aChar2 >= 48 && aChar2 <= 57 && aChar3 >= 48 && aChar3 <= 57) {
                    sb.append(value, index, i - 1).append("\n");
                    index = i;
                    builder.append(sb);
                    indexList.add(index);
                }
            }
            // 倒数第二段
            int sameSpace = 0;
            if (indexList.size() > 2) {
                String secondStr = value.substring(indexList.get(indexList.size() - 2), index);
                char[] array = secondStr.toCharArray();
                int num = 0;
                for (int i = 0; i < array.length - 2; i++) {
                    if (array[i] == 45 && array[i + 1] == 45) {
                        num = i + 2;
                        for (int k = num; k < array.length - 2; k++) {
                            if (array[k] == 32) {
                                sameSpace++;
                            }
                            if (k - num > 9) {
                                break;
                            }
                        }
                    }
                }
            }
            // 最后一段 2015.12--         药品不良反应监测中心 副主任科员
            String endStr = value.substring(index);
            char[] endChars = endStr.toCharArray();
            int num = 0;
            int space = 0;
            for (int i = 0; i < endChars.length - 2; i++) {
                // 找到两条'-'的坐标
                if (endChars[i] == 45 && endChars[i + 1] == 45) {
                    num = i + 2;
                    // 从空格处开始循环
                    for (int k = num; k < endChars.length - 2; k++) {
                        if (endChars[k] == 32) {
                            space++;
                        }
                        // 只循环 9 次，后面的字符不读了（从0开始）
                        if (k - num > 7) {
                            break;
                        }
                    }
                }
            }
            if (space == 7) {
                endStr = value.substring(index, index + 9) + " " + " " + value.substring(index + 9);
            } else {
                if (space == 8) {
                    if (sameSpace == 2) {
                        endStr = value.substring(index, index + 9) + " " + value.substring(index + 9);
                    } else if (sameSpace == 3) {
                        endStr = value.substring(index, index + 9) + " " + " " + value.substring(index + 9);
                    }
                }
            }
            builder.append(endStr);
            value = builder.toString();
        }
        return value;
    }


}
