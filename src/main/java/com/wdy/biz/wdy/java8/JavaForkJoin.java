package com.wdy.biz.wdy.java8;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * @author wgch
 * @Description Fork—Join框架：用于执行任务，就是在必要得情况下，将一个大任务，进行拆分（Fork）成若干个小任务（拆分到不能再拆分），再将一个个的小任务运算得结果进行汇总（join）
 * @date 2019/11/1 15:03
 */
public class JavaForkJoin extends RecursiveTask<Long> {
    private static final long serialVersionUID = -490429507336391188L;

    private long start;
    private long end;

    private JavaForkJoin(long start, long end) {
        this.start = start;
        this.end = end;
    }

    private static final long THRESHOLD = 10000L;

    @Override
    protected Long compute() {
        if (end - start <= THRESHOLD) {
            long sum = 0;
            for (long i = start; i < end; i++) {
                sum += i;
            }
            return sum;
        } else {
            Long middle = (end - start) / 2;
            JavaForkJoin left = new JavaForkJoin(start, middle);
            // 拆分子任务，压入线程队列
            left.fork();
            JavaForkJoin right = new JavaForkJoin(middle, end);
            right.fork();
            // 合并 并返回
            return left.join() + right.join();
        }
    }

    public static void main(String[] args) {
        testSum7();
        testJava8();
    }

    /**
     * 实现数的累加
     */
    public static void testSum7() {
        Instant start = Instant.now();

        // 这里需要一个线程池的支持
        ForkJoinPool pool = new ForkJoinPool();
        JavaForkJoin forkJoin = new JavaForkJoin(0, 100000L);
        Long sum = pool.invoke(forkJoin);
        System.out.println("sum7-->" + sum);

        Instant end = Instant.now();
        System.out.println(Duration.between(start, end).getSeconds());
    }

    /**
     * java 8 并行流 parallel()
     */
    public static void testJava8() {
        Instant start = Instant.now();
        long sum = LongStream.rangeClosed(0, 100000L).parallel().reduce(0, Long::sum);
        System.out.println("sum8-->" + sum);
        Instant end = Instant.now();
        System.out.println(Duration.between(start, end).getSeconds());
    }

}
