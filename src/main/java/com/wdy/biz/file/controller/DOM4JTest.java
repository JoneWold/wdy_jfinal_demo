package com.wdy.biz.file.controller;

import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Record;
import com.wdy.biz.file.model.Book;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author wgch
 * @Description DOM4J解析xml
 * @date 2019/4/11 15:50
 */
public class DOM4JTest {
    private static ArrayList<Book> bookList = new ArrayList<Book>();

    public static void main(String[] args) {
        String bookPath = "D:\\wdy\\wdy_jfinal_demo\\src\\main\\webapp\\WEB-INF\\view\\book\\book.xml";
        String a01Path = "D:\\wdy\\wdy_jfinal_demo\\src\\main\\webapp\\WEB-INF\\view\\book\\A01.xml";
//        ArrayList<Book> xmlData = getXmlData(bookPath);
//        System.out.println(xmlData);
        List<Record> a01XmlData = getA01XmlData(a01Path);
        System.out.println(a01XmlData);
//        testDoc();
//        testCharAt();
    }

    public static void testDoc() {
        try {
            String text = "<root value=\"123\n321\"></root>";
            Document document = DocumentHelper.parseText(text);
            System.out.println(document.valueOf("//root/@value"));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public static void testCharAt() {
        char s = "\n".charAt(0);
        System.out.println(s);
    }

    /**
     * DOM4J解析xml
     *
     * @param filePath
     * @return
     */
    public static ArrayList<Book> getXmlData(String filePath) {
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

                    // 简历格式 将空格 转换为 换行
                    char[] chars = value.toCharArray();
                    StringBuilder builder = new StringBuilder();
                    int index = 0;
                    for (int i = 1; i < chars.length - 4; i++) {
                        StringBuilder sb = new StringBuilder();
                        char aChar0 = chars[i - 1];
                        char aChar = chars[i];
                        char aChar1 = chars[i + 1];
                        char aChar2 = chars[i + 2];
                        char aChar3 = chars[i + 3];
                        if (aChar0 == 32 && aChar >= 48 && aChar <= 57 && aChar1 >= 48 && aChar1 <= 57
                                && aChar2 >= 48 && aChar2 <= 57 && aChar3 >= 48 && aChar3 <= 57) {
                            sb.append(value.substring(index, i - 1)).append("\n");
                            index = i;
                            builder.append(sb);
                        }
                    }
                    // 最后一段
                    builder.append(value.substring(index));
                    value = builder.toString();
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
    public static List<Record> getA01XmlData(String filePath) {
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
                    if ("A1701".equals(name)) {
                        char[] chars = value.toCharArray();
                        StringBuilder builder = new StringBuilder();
                        int index = 0;
                        for (int i = 1; i < chars.length - 4; i++) {
                            StringBuilder sb = new StringBuilder();
                            char aChar0 = chars[i - 1];
                            char aChar = chars[i];
                            char aChar1 = chars[i + 1];
                            char aChar2 = chars[i + 2];
                            char aChar3 = chars[i + 3];
                            // 前一位是空格，当前及后面三位是数字
                            if (aChar0 == 32 && aChar >= 48 && aChar <= 57 && aChar1 >= 48 && aChar1 <= 57
                                    && aChar2 >= 48 && aChar2 <= 57 && aChar3 >= 48 && aChar3 <= 57) {
                                sb.append(value, index, i - 1).append("\n");
                                index = i;
                                builder.append(sb);
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
                                }
                            }
                        }
                        if (space == 7) {
                            endStr = value.substring(index, index + 9) + " " + " " + value.substring(index + 9);
                        }
                        builder.append(endStr);

                        value = builder.toString();
                    }
                    record.set(name, value);
                }
                data.add(record);
            }
        }
        return data;
    }

    /**
     * 生成xml文件
     *
     * @param document
     * @throws IOException
     */
    public static void writer(Document document) throws IOException {
        // 设置XML文档输出格式
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("GB2312");
        XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(new File(PathKit.getWebRootPath() + "/download/book2.xml")), format);
        xmlWriter.write(document);
        xmlWriter.close();
    }


}
