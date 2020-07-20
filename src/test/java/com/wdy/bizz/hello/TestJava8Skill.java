package com.wdy.bizz.hello;

import cn.hutool.core.util.StrUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.wdy.bizz.TestBeforeWdyConfig;
import com.wdy.dto.LOL;
import org.junit.Test;

import java.text.Collator;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.wdy.constant.DBConstant.DB_MySQL;
import static com.wdy.constant.DBConstant.DB_PGSQL;
import static java.util.stream.Collectors.toList;

/**
 * @author wgch
 * @description java技术测试
 * @date 2019/6/6 11:02
 */
public class TestJava8Skill extends TestBeforeWdyConfig {


    private static String apply(Record d) {
        return d.getStr("A0000");
    }

    /**
     * java8 list分组groupingBy
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

        Map<String, List<Record>> map = recordList.stream().collect(Collectors.groupingBy(TestJava8Skill::apply));
        Map<String, List<Record>> map1 = recordList.stream().collect(Collectors.groupingBy(b -> b.getStr("A0000")));
        // 分组 统计
        Map<String, Long> map2 = recordList.stream().collect(Collectors.groupingBy(e -> e.getStr("A0000"), Collectors.counting()));

        // 遍历map集合
        for (Map.Entry<String, List<Record>> entry : map.entrySet()) {
            String key = entry.getKey();
            List<Record> values = entry.getValue();
            System.out.println(key + ">>>" + values);
        }
        System.out.println(map1);
        System.out.println(map2);
    }

    /**
     * Stream（流）是一个来自数据源的元素队列并支持聚合操作
     * 元素是特定类型的对象，形成一个队列。 Java中的Stream并不会存储元素，而是按需计算。
     * 数据源 流的来源。 可以是集合，数组，I/O channel， 产生器generator 等。
     * 聚合操作 类似SQL语句一样的操作， 比如filter, map, reduce, find, match, sorted等。
     */
    @Test
    public void stream() {
        List<String> strList = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        // 1、filter 方法用于通过设置的条件过滤出元素
        List<String> filtered = strList.stream().filter(e -> !e.isEmpty()).collect(Collectors.toList());
        String joinStr = strList.stream().filter(e -> !e.isEmpty()).collect(Collectors.joining(","));
        long length3 = strList.stream().filter(e -> e.length() == 3).count();
        System.out.println("filtered-->" + filtered);
        System.out.println("joinStr-->" + joinStr);
        System.out.println("length3-->" + length3);

        // 2、sorted 方法用于对流进行倒叙排序
        List<String> sorted = strList.stream().sorted(Comparator.comparing(String::length).reversed()).collect(Collectors.toList());
        System.out.println("sorted-->" + sorted);

        // 3、map 方法用于映射每个元素到对应的结果：输出了元素对应的平方数
        List<Integer> numList = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        List<Integer> pfList = numList.stream().map(i -> i * i).distinct().collect(Collectors.toList());
        System.out.println("intList-->" + pfList);
        // 4、limit 方法用于获取指定数量的流
        List<Integer> limitList = numList.stream().limit(5).collect(Collectors.toList());
        System.out.println("limitList-->" + limitList);

        // 5、parallelStream 是流并行处理程序的代替方法。获取空字符串的数量
        long count = strList.parallelStream().filter(e -> e.isEmpty()).count();
        System.out.println(count);

        // Collectors 类实现了很多归约操作，例如将流转换成集合和聚合元素。
        // 统计
        IntSummaryStatistics statistics = numList.stream().mapToInt((x) -> x).summaryStatistics();
        System.out.println("statistics-->" + statistics);
        System.out.println("max-->" + statistics.getMax());
        System.out.println("sum-->" + statistics.getSum());

        // 对中文排序
        List<String> hzList = Arrays.asList("谷歌", "腾讯", "百度", "淘宝");
        Collator collator = Collator.getInstance(Locale.CANADA);
        hzList.sort((e, v) -> collator.compare(e, v));
        System.out.println(hzList);

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

        // 1、e表示参数，花括号中相当于函数体，且JVM会根据->右侧语句的返回结果自动判断返回值类型
        list1.forEach(System.out::println);
        Arrays.asList("1", "2", "3").forEach(e -> {
            System.out.print(e);
            System.out.print(e);
            System.out.println();
        });

        // 2、输出了5个随机数，排序默认升序
        Random random = new Random();
        random.ints().limit(5).sorted().forEach(System.out::println);

        // 3、skip() 方法
        // 跳过stream中的前n个元素，n不能为负值。如果n大于stream的大小，则返回空stream。
        System.out.println("获取流中的偶数，并跳过前两个-->>>");
        Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).filter(i -> i % 2 == 0).skip(2).forEach(i -> System.out.print(i + " "));

        // 4、limit() 对于将无限的数字流 截断为有限流非常有用
        Stream.iterate(0, i -> i + 1).filter(i -> i % 2 == 0).limit(10).forEach(i -> System.out.print(i + " "));

        // 5、对流进行分页
        List<Integer> evenNumbers = getEvenNumbers(2, 10);
        System.out.println(evenNumbers);

    }

    private static List<Integer> getEvenNumbers(int offset, int limit) {
        return Stream.iterate(0, i -> i + 1)
                .filter(i -> i % 2 == 0)
                .skip(offset)
                .limit(limit)
                .collect(Collectors.toList());
    }

    /**
     * 从List<Record>中取出某一字段重新组合成新的集合
     */
    @Test
    public void testTypeConversion() {
        List<Record> records = Db.use(DB_MySQL).find("SELECT * FROM blog");
        System.out.println(records);
        List<String> strList = records.stream()
                .filter(e -> StrUtil.isNotEmpty(e.getStr("title")))
                .map(e -> e.getStr("title"))
                .collect(toList());
        System.out.println(strList);
    }

    /**
     * 将list转换为map
     */
    @Test
    public void testListToMap() {
        List<Record> list = Db.use(DB_MySQL).find("SELECT * FROM blog");
        Map<String, Record> map = list.stream().collect(Collectors.toMap(e -> e.getStr("id"), v -> v, (e, v) -> e));
        System.out.println(map);
        // 分页
        Page<Record> page = this.getJava8Page(1, 10, list);
        System.out.println(page);
    }

    private Page<Record> getJava8Page(int pageNum, int pageSize, List<Record> list) {
        Page<Record> page = new Page<>();
        List<Record> collect = list.stream().skip((pageNum - 1) * pageSize).limit(pageSize).collect(toList());
        page.setList(collect);
        page.setPageNumber(pageNum);
        page.setPageSize(pageSize);
        page.setTotalRow(collect.size());
        page.setTotalPage(collect.size() % pageSize == 0 ? collect.size() / pageSize : collect.size() / pageSize + 1);
        return page;
    }

    @Test
    public void getJava8Min() {
        List<Record> list = Db.use(DB_PGSQL).find("select * from \"b01\" limit 100");
        // 最小值
        Optional<Record> min = list.stream().min((v, x) -> v.getStr("B0111").length() - x.getStr("B0111").length());
        Optional<Record> min2 = list.stream().min(Comparator.comparingInt(v -> v.getStr("B0111").length()));
        if (min2.isPresent()) {
            Record record = min2.get();
            System.out.println(record.getStr("B0111"));
        }
    }

    /**
     * Function<T, R>
     */
    @Test
    public void testFunction() {
        System.out.print("运行步骤\t1-->");
        // y = f(x); 函数传入类型Integer，结果类型List<>
        Function<Integer, List<Integer>> fun = i -> {
            System.out.print("3-->");
            return new ArrayList<Integer>() {{
                add(i + 1);
                add(i + 2);
                add(i + 3);
            }};
        };
        System.out.print("2-->");
        List<Integer> list = fun.apply(3);
        System.out.println("4");
        System.out.println(list);
    }

    @Test
    public void testFindFirst() {
        List<LOL> lols = new ArrayList<>();
        lols.add(new LOL("LPL", "IG", Arrays.asList("TheShy", "JK", "Rk", "BaoLan", "Ning")));
        lols.add(new LOL("LPL", "FPX", Arrays.asList("Doinb", "GimGoon", "Tian", "Lwx", "Crisp")));
        lols.add(new LOL("LCK", "SKT", Arrays.asList("faker", "khan", "child", "teddy", "mata")));
        LOL lol = lols.stream().findFirst().orElse(null);
        System.out.println(lol);
    }


}
