package com.wdy.bizz.hello;

import com.jfinal.plugin.activerecord.Record;
import com.wdy.bizz.TestBeforeWdyConfig;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @author wgch
 * @description java技术测试
 * @date 2019/6/6 11:02
 */
public class TestJavaSkill extends TestBeforeWdyConfig {


    private static String apply(Record d) {
        return d.getStr("A0000");
    }

    /**
     * java8 list分组
     */
    @Test
    public void testGroupBy() {
        List<Record> recordList = new ArrayList<>();
        Record record = new Record();
        record.set("A0000", "1");
        record.set("A0200", "12313213");
        record.set("A03333", "123123");
        Record record2 = new Record();
        record2.set("A0000", "1");
        record2.set("A0200", "1231312312213");
        record2.set("A03333", "123131231231323");
        Record record3 = new Record();
        record3.set("A0000", "2");
        record3.set("A0200", "12313213");
        record3.set("A03333", "123123");
        Record record4 = new Record();
        record4.set("A0000", "2");
        record4.set("A0200", "1231312312213");
        record4.set("A03333", "123131231231323");
        recordList.add(record);
        recordList.add(record2);
        recordList.add(record3);
        recordList.add(record4);

        Map<String, List<Record>> map = recordList.stream().collect(Collectors.groupingBy(TestJavaSkill::apply));
        Map<String, List<Record>> map1 = recordList.stream().collect(Collectors.groupingBy(b -> b.getStr("A0000")));


        // 遍历map集合
        for (Map.Entry<String, List<Record>> entry : map1.entrySet()) {
            String key = entry.getKey();
            List<Record> values = entry.getValue();
            System.out.println(key + ">>>" + values);
        }
        System.out.println(map);
    }

    /**
     * Lambda表达式用于表示一个函数，所以它和函数一样，也拥有参数、返回值、函数体，但它没有函数名，所以Lambda表达式相当于一个匿名函数
     */
    @Test
    public void testForEach() {
        List<String> list1 = Arrays.asList("a", "b", "c");
        System.out.println(list1);

        List<String> list2 = Stream.of("001.002", "001.002.003", "001.001.002").collect(toList());
        System.out.println(list2);

        // e表示参数，花括号中相当于函数体，且JVM会根据->右侧语句的返回结果自动判断返回值类型
        list1.forEach(System.out::print);
        Arrays.asList("1", "2", "3").forEach(e -> {
            System.out.print(e);
            System.out.print(e);
        });
    }


}
