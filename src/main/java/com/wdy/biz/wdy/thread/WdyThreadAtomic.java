package com.wdy.biz.wdy.thread;

import cn.hutool.core.thread.ThreadUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author wgch
 * @Description 线程 原子方式
 * @date 2019/7/17 16:59
 */
public class WdyThreadAtomic {

    private static ExecutorService executorService = ThreadUtil.newExecutor();

    /**
     * 获取线程池
     */
    public static ExecutorService getExecutorService() {
        return executorService;
    }


    public static void main(String[] args) throws InterruptedException {
        // 多线程运行速度
//        betweenTime();
        // 原子方式更新对象引用：1000个线程，对一个Integer累加1，直到1000
        new WdyThreadAtomic().atomic();
    }

    private static void betweenTime() {
        long startTime = System.currentTimeMillis();
        getExecutorService().execute(() -> {
            for (int i = 0; i < 500000; i++) {
                System.out.println("i..." + i);
            }
            long endTime = System.currentTimeMillis();
            System.out.println(endTime - startTime);
        });

        getExecutorService().execute(() -> {
            for (int i = 0; i < 500000; i++) {
                System.out.println("kkk..." + i);
            }
        });

//        for (int i = 0; i < 1000000; i++) {
//            System.out.println("ss...." + i);
//        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }


    public void atomic() throws InterruptedException {
        // AtomicReference 类提供了一个可以原子读写的对象引用变量。
        // 原子意味着多个线程尝试更改相同AtomicReference（例如，使用比较和交换操作）将不会使AtomicReference最终达到不一致的状态。
        AtomicReference<Integer> ref = new AtomicReference<>(0);
        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Thread t = new Thread(new WdyTask(ref), "Thread-no" + i);
            list.add(t);
            t.start();
        }
        for (Thread t : list) {
            t.join();
        }
        // 打印2000
        System.out.println(ref.get());
    }

    class WdyTask implements Runnable {
        private AtomicReference<Integer> ref;

        WdyTask(AtomicReference<Integer> ref) {
            this.ref = ref;
        }

        /**
         * 使用自旋+CAS的无锁操作保证共享变量的线程安全
         */
        @Override
        public void run() {
            for (; ; ) {    //自旋操作
                Integer oldV = ref.get();
                // CAS操作
                if (ref.compareAndSet(oldV, oldV + 1)) {
                    break;
                }
            }
        }
    }
}
