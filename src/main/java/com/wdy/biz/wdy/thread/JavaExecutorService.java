package com.wdy.biz.wdy.thread;

import cn.hutool.core.thread.ThreadUtil;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author wgch
 * @Description 线程池 分布式执行服务 一颗塞克特
 * @date 2019/11/3
 */
public class JavaExecutorService {
    /**
     * 使用工厂方法创建一个可以容纳10个线程任务的线程池
     */
    static ExecutorService javaService = Executors.newFixedThreadPool(10);
    static ExecutorService htService = ThreadUtil.newExecutor();


    public static void main(String[] args) throws Exception {
        test();
        runTest();
    }

    public static void test() {
        // 创建线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorService executorService1 = Executors.newSingleThreadExecutor();
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);

        // 向 execute() 方法中传递一个异步的 Runnable 接口的实现，这样做会让 ExecutorService 中的某个线程执行这个 Runnable 线程。
        javaService.execute(() -> System.out.println("异步任务。。"));
        javaService.shutdown();
        // 关闭线程池后判断所有任务是否都已完成
        boolean terminated = javaService.isTerminated();
        // 判断线程池是否已关闭
        // ExecutorService并不会马上关闭，而是不再接收新的任务，一旦所有的线程结束执行当前任务，ExecutorService 才会真的关闭。所有在调用 shutdown() 方法之前提交到 ExecutorService 的任务都会执行。
        boolean shutdown = javaService.isShutdown();
        System.out.println(terminated + " " + shutdown);
    }

    private static void runTest() throws Exception {
        // ExecutorService 使用方法：将任务委托给线程池
        // 1、submit(Runnable) 这个 Future 对象可以用于判断 Runnable 是否结束执行
        Future<?> submit = htService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("submit run 无返回值.......");
            }
        });
        // 如果任务结束执行则返回 null
        System.out.println(submit.get());
        // 2、submit(Callable)
        Future<Object> submit1 = htService.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                System.out.println("submit call 有返回值.....");
                return "result";
            }
        });
        System.out.println(submit1.get());

        // 3、invokeAny(List<Callable>)
        // 返回集合中某一个 Callable 对象的结果，而且无法保证调用之后返回的结果是哪一个 Callable，只知道它是这些 Callable 中一个执行结束的 Callable 对象。
        // 如果壹個任务运行完毕或者抛出异常，方法会取消其它的 Callable 的执行。
        HashSet<Callable<String>> callables = new HashSet<>();
        callables.add(() -> "11");
        callables.add(() -> "22");
        callables.add(() -> "33");
        System.out.println("invokeAny()...");
        for (int i = 0; i < 100; i++) {
            String result = htService.invokeAny(callables);
            System.out.print(result + " ");
        }

        // 4、invokeAll()
        // 会调用存在于参数集合中的所有 Callable 对象，并且返回壹個包含 Future 对象的集合，你可以通过这個返回的集合来管理每個 Callable 的执行结果。
        System.out.println("\ninvokeAll()...");
        List<Future<String>> futures = htService.invokeAll(callables);
        for (Future<String> future : futures) {
            String s = future.get();
            System.out.print(s + " ");
        }
    }

}
