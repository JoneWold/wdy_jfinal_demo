package com.wdy.biz.wdy.thread;

import cn.hutool.core.thread.ThreadUtil;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * @author wgch
 * @Description
 * @date 2019/11/30 14:37
 */
public class JavaThread100 {
    private static int threadNum = 100;
    private static CountDownLatch latch = new CountDownLatch(threadNum);
    private static ExecutorService htService = ThreadUtil.newExecutor();

    public static void main(String[] args) {

        long start = System.currentTimeMillis();

        // 开启100个线程 120毫秒左右
        // 开启1万个线程 在3-5之间
        for (int index = 0; index < threadNum; index++) {
//            new Thread(new MyTask("t" + index)).start();
            htService.execute(new MyTask("t" + index));
        }

        try {
            latch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();

        System.out.println("Threads[" + threadNum + "] is done, spend: " + (end - start) + " mills");

    }

    static class MyTask implements Runnable {
        private String threadName;

        MyTask(String threadName) {
            this.threadName = threadName;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(threadName + " is done");
            latch.countDown();
        }

    }
}
