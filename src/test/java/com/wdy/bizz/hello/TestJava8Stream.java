package com.wdy.bizz.hello;

import org.junit.Test;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author wgch
 * @Description java8 流
 * @date 2019/11/1 17:25
 */
public class TestJava8Stream {

    /**
     * ①. Stream 自己不会存储元素
     * ②. Stream 不会改变源对象。相反，它会返回一个持有结果得新Stream
     * ③. Stream 操作是延迟执行的。这意味着它们会等到需要结果时才执行。（延迟加载）
     * <p>
     * Stream 操作的三个步骤
     * 1）创建Stream　　一个数据源（集合，数组），获取一个流。
     * 2）中间操作 　　　一个中间操作链，对数据源的数据进行处理。
     * 3）终止操作 　　　一个终止操作，执行中间操作链，并产生结果。
     */
    @Test
    public void createStream() {
        // 1、通过Collection的Stream()方法（串行流）或者 parallelStream()方法（并行流）创建Stream。
        List<String> list = new ArrayList<>();
        Stream<String> stream = list.stream();
        Stream<String> stream1 = list.parallelStream();

        // 2、通过Arrays中得静态方法stream()获取数组流
        IntStream stream2 = Arrays.stream(new int[]{1, 2, 3});

        // 3、通过Stream类中得 of()静态方法获取流
        Stream<String> stream3 = Stream.of("a", "b");

        // 4、创建无限流（迭代、生成）
        // 迭代（需要传入一个种子，也就是起始值，然后传入一个一元操作）
        Stream<Integer> iterate = Stream.iterate(2, (x) -> x * 2);
        // 生成（无限产生对象）
        Stream<Double> generate = Stream.generate(Math::random);
        System.out.println(Instant.now());
    }

    @Test
    public void usingStream() {
        List<Integer> list = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        System.out.println("1、filter —— 筛选与切片，接收Lambda ，从流中排除某些元素。");
        //内部迭代：在此过程中没有进行过迭代，由Stream api进行迭代
        //中间操作：不会执行任何操作
        Stream<Integer> stream = list.stream().filter(e -> {
            System.err.print("Stream API 中间操作 ");
            return e > 2;
        });
        //终止操作：只有执行终止操作才会执行全部。即：延迟加载
        stream.forEachOrdered(System.out::print);

        System.out.println();
        System.out.println("limit —— 截断流，使其元素不超过给定数量。");
        list.stream().filter(e -> e > 3).limit(2).forEach(System.out::print);

        System.out.println();
        System.out.println("2、skip(n)，返回一个扔掉了前n个元素的流。若流中元素不足n个，则返回一个空，与limit(n)互补。");
        list.stream().skip(2).forEach(System.out::print);

        System.out.println();
        System.out.println("3、distinct 通过流所生成元素的hashCode()和equals()去除重复元素");
        list.stream().distinct().forEach(System.out::print);

        System.out.println();
        System.out.println("4、映射");
        List<String> list2 = Arrays.asList("aaa", "bbb", "ccc", "ddd");
        list2.stream().map(String::toUpperCase).forEach(System.out::printf);

        System.out.println();
        System.out.println("5、排序：sorted有两种方法，一种是不传任何参数，叫自然排序，还有一种需要传Comparator 接口参数，叫做定制排序。");
        // TODO 2019年11月1日 定制排序：升序
        List<Integer> sortList = list.stream().sorted((e1, e2) -> {
            // 前一位等于后一位 位置不变
            if (e1.equals(e2)) {
                return 0;
                // 前一位大于后一位 向后
            } else if (e1 > e2) {
                return 1;
            } else {
                return -1;
            }
        }).collect(Collectors.toList());
        System.out.println(sortList);
    }

    @Test
    public void stopStream() {
        //1、查找与匹配：allMatch anyMatch noneMatch findFirst findAny count max min
        //2、归约（可以将流中元素反复结合在一起，得到一个值）
        List<Integer> list = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        //(1) reduce（T identitty，BinaryOperator）首先，需要传一个起始值，然后，传入的是一个二元运算。
        Integer sum = list.stream().reduce(0, (x, y) -> x + y);
        System.out.println(sum);
        //(2) reduce（BinaryOperator）此方法相对于上面方法来说，没有起始值，则有可能结果为空，所以返回的值会被封装到Optional中
        Optional<Integer> sum2 = list.stream().reduce(Integer::sum);
        boolean present = sum2.isPresent();

        //3、收集collect（List，Set，Map）
        // 将流转换为其他形式。接收一个Collector接口得实现，用于给其他Stream中元素做汇总的方法
        HashSet<Integer> hashSet = list.stream().filter(e -> e > 5).collect(Collectors.toCollection(HashSet::new));
        System.out.println(hashSet);
        //Collectors.maxBy() 求最大值
        Optional<Integer> maxBy = list.stream().collect(Collectors.maxBy((x, y) -> x.compareTo(y)));
        Optional<Integer> maxBy2 = list.stream().max(Integer::compareTo);
        Optional<Integer> maxBy3 = list.stream().max(Comparator.naturalOrder());
        System.out.println(maxBy.get());
        //Collectors.groupingBy()分组，返回一个map
        //Collectors.partitioningBy()分区，参数中传一个函数，返回true，和false 分成两个区
        Map<Boolean, List<Integer>> booMap = list.stream().collect(Collectors.partitioningBy((x) -> x > 9));
        System.out.println(booMap);
    }


}
