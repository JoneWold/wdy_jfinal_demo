package com.wdy.common.utils;

import cn.hutool.core.thread.ThreadUtil;

import java.util.concurrent.ExecutorService;

/**
 * @author wgch
 * @Description 线程工具类
 * @date 2019/7/17 16:59
 */
public class WdyThreadUtil {

    private static ExecutorService executorService = ThreadUtil.newExecutor();

    /**
     * 获取线程池
     */
    public static ExecutorService getExecutorService() {
        return executorService;
    }


    public static void main(String[] args) {
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

}
