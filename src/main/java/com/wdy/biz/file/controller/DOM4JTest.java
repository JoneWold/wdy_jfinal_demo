package com.wdy.biz.file.controller;

import com.wdy.biz.file.model.Book;
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
public class DOM4JTest {
    private static ArrayList<Book> bookList = new ArrayList<Book>();

    public static void main(String[] args) {
        ArrayList<Book> xmlData = getXmlData();
        System.out.println(xmlData);

    }

    /**
     * DOM4J解析xml
     */
    public static ArrayList<Book> getXmlData() {
        // 解析books.xml文件
        // 创建SAXReader的对象reader
        SAXReader reader = new SAXReader();
        try {
            // 通过reader对象的read方法加载books.xml文件,获取docuemnt对象。
            Document document = reader.read(new File("D:\\wdy\\wdy_jfinal_demo\\src\\main\\webapp\\book\\book.xml"));
            // 通过document对象获取根节点bookstore
            Element bookStore = document.getRootElement();
            // 通过element对象的elementIterator方法获取迭代器
            Iterator it = bookStore.elementIterator();
            // 遍历迭代器，获取根节点中的信息（书籍）
            while (it.hasNext()) {
                Book bookModel = new Book();
                System.out.println("=====开始遍历某一本书=====");
                Element book = (Element) it.next();
                // 获取book的属性名以及 属性值
                List<Attribute> bookAttrs = book.attributes();
                for (Attribute attr : bookAttrs) {
                    System.out.println("属性名：" + attr.getName() + "--属性值："
                            + attr.getValue());
                    bookModel.setId(attr.getValue());
                }
                Iterator itt = book.elementIterator();
                while (itt.hasNext()) {
                    Element bookChild = (Element) itt.next();
                    System.out.println("节点名：" + bookChild.getName() + "--节点值："
                            + bookChild.getStringValue());
                    if ("name".equals(bookChild.getName())) {
                        bookModel.setName(bookChild.getStringValue());
                    } else if ("author".equals(bookChild.getName())) {
                        bookModel.setAuthor(bookChild.getStringValue());
                    } else if ("year".equals(bookChild.getName())) {
                        bookModel.setYear(bookChild.getStringValue());
                    } else if ("price".equals(bookChild.getName())) {
                        bookModel.setPrice(bookChild.getStringValue());
                    }
                }
                bookList.add(bookModel);
                System.out.println("=====结束遍历某一本书=====");
            }
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bookList;
    }


}
