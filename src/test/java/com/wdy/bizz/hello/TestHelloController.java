package com.wdy.bizz.hello;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Aop;
import com.jfinal.plugin.activerecord.Record;
import com.wdy.biz.hello.service.HelloService;
import com.wdy.bizz.TestBeforeWdyConfig;
import com.wdy.message.OutMessage;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.wdy.message.Status.SUCCESS;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author wgch
 * @Description hello
 * @date 2019/3/28 10:35
 */
public class TestHelloController extends TestBeforeWdyConfig {
    private HelloService hello = Aop.get(HelloService.class);

    /**
     * 测试方法
     */
    @Test
    public void testGetPostgreSQLList() {
        OutMessage message = hello.getPostgreSQLList();
        System.out.println(message);
        assertThat(message.getCode(), is(SUCCESS.getCode()));
    }

    /**
     * 字符串 转 时间格式
     */
    @Test
    public void testStrToDate() {
        String format = DateUtil.format(DateUtil.parse("2019-04-11", "yyyy-MM-dd"), "yyyyMM");
        System.out.println(format);

        // 字符串格式 转 时间格式
        DateTime parse = DateUtil.parse("20190416");
        System.out.println(parse);
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-04-11 12:12:00");
            System.out.println(date);
            String yyyyMM1 = new SimpleDateFormat("yyyyMM").format(date);
            System.out.println(yyyyMM1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(DateUtil.parse(null));

        // 计算两个日期相差年数
        long betweenYear = DateUtil.betweenYear(DateUtil.parse("2012.06", "yyyy.MM"), new Date(), false);
        System.out.println(betweenYear);
    }

    @Test
    public void testJoin() {
        List<String> list = new ArrayList();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        System.out.println(list);
        String join = CollectionUtil.join(list, " AND ");
        System.out.println(join);
    }

    @Test
    public void list() {
        List<String> list1 = new ArrayList<>();
        list1.add("张三");
        list1.add("李四1");
        list1.add("王五1");
        list1.add("王五2");
        List<String> list2 = new ArrayList<>();
        list2.add("张三");
        list2.add("李四2");
        list2.add("王五2");
        list2.add("王五1");
        //并集
        list1.addAll(list2);
        List list = removeDuplicate(list1);
        System.out.println(list);
        System.out.println("------------------------");
        //交集
        list1.retainAll(list2);
        System.out.println("交集：");
        System.out.println("list1：" + list1);
        System.out.println("list2：" + list2);
        //差集
        list1.removeAll(list2);
        System.out.println("差集：");
        System.out.println("list1：" + list1);
        System.out.println("list2：" + list2);
        //无重复并集
        list2.removeAll(list1);
        list1.addAll(list2);
        System.out.println("无重复并集：");
        System.out.println("list1：" + list1);
        System.out.println("list2：" + list2);
        Record record = new Record();
        for (String ss : list2) {
            record.set(ss, ss);
        }
    }

    /**
     * 通过HashSet踢除list中重复元素
     *
     * @param list
     * @return
     */
    public List<String> removeDuplicate(List<String> list) {
        HashSet hashSet = new HashSet<>(list);
        list.clear();
        list.addAll(hashSet);
        return list;
    }

    /**
     * String 转换 List<String>
     */
    @Test
    public void testStrToList() {
        String queryQX = "[\"1\",\"2\"]";
        List<String> list = JSONObject.parseArray(queryQX, String.class);
        System.out.println(list);

        String string = JSONObject.toJSONString(list);
        System.out.println(string);
    }

}
