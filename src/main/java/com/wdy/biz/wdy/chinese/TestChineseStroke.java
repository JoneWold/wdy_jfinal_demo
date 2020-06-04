package com.wdy.biz.wdy.chinese;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wgch
 * @Description 汉字笔画排序
 * @date 2020/6/1
 */
public class TestChineseStroke {


    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("李四");
        list.add("张三44");
        list.add("一二");
        list.add("张刘");
        list.add("二五");
        list.add("赵明");
        list.add("王东西");
        list.sort(new ObjectStrokeComparator(String.class, ""));
        for (String string : list) {
            System.out.println(string);
        }
    }


}
