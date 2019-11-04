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

    // 并行流 多线程异步任务
    @Test
    public void parallelStream() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        // Stream 的强大之处在于其原型链的设计，使得它可以对遍历处理后的数据进行再处理
        List<String> toStrList = list.stream().map(e -> Integer.toString(++e)).collect(Collectors.toList());
        System.err.println("\n1、使用parallelStream后，结果并不按照集合原有顺序输出-->>>");
        list.parallelStream().forEach(System.out::print);
        System.err.println("\n2、打印出线程信息-->>>");
        list.parallelStream().forEach(e -> System.out.println(Thread.currentThread().getName() + "-->>>" + e));
        // TODO 我们可以通过虚拟机启动参数 来设置worker的数量。
        // -Djava.util.concurrent.ForkJoinPool.common.parallelism=N

        System.err.println("\n获取虚拟机中所有线程的StackTraceElement对象，可以查看堆栈--->>>");
        Map<Thread, StackTraceElement[]> traces = Thread.getAllStackTraces();
        for (Map.Entry<Thread, StackTraceElement[]> entry : traces.entrySet()) {
            Thread thread = entry.getKey();
            StackTraceElement[] value = entry.getValue();
            if (thread.equals(Thread.currentThread())) {
                System.err.println("当前线程：" + thread.getName());
                continue;
            }
            System.out.println("线程：" + thread.getName());
        }
    }

    @Test
    public void testParallelStream() {
        // 并行流 陷阱
        // 1、线程安全
        // 由于并行流使用多线程，则一切线程安全问题都应该是需要考虑的问题，如：资源竞争、死锁、事务、可见性等等。
        // 2、线程消费
        // 在虚拟机启动时，我们指定了worker线程的数量，整个程序的生命周期都将使用这些工作线程；这必然存在任务生产和消费的问题，如果某个生产者生产了许多重量级的任务（耗时很长），那么其他任务毫无疑问将会没有工作线程可用；更可怕的事情是这些工作线程正在进行IO阻塞。
        // 本应利用并行加速处理的业务，因为工作者不够反而会额外增加处理时间，使得系统性能在某一时刻大打折扣。而且这一类问题往往是很难排查的。我们并不知道一个重量级项目中的哪一个框架、哪一个模块在使用并行流。
        Thread thread = new Thread(this::streamTest);
        Thread thread1 = new Thread(this::streamTest2);
        thread.start();
        thread1.start();
        try {
            thread1.join();
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 通过示例我们会发现，第一个并行流率先获得worker线程的使用权，第二个并行流变为串行；直到第一个并行流处理完毕，第二个并行流获取worker线程，开始并行处理。

        // 串行流：适合存在线程安全问题、阻塞任务、重量级任务，以及需要使用同一事务的逻辑。
        // 并行流：适合没有线程安全问题、较单纯的数据处理任务。
    }

    public void streamTest() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        list.parallelStream().forEach(e -> {
            System.out.println("第一次请求并行：" + Thread.currentThread().getName() + "-->>" + e);
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        });

    }

    public void streamTest2() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        list.parallelStream().forEach(e -> {
            System.err.println("再次请求并行：" + Thread.currentThread().getName() + "-->>" + e);
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        });

    }

}
