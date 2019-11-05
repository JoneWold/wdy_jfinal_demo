package com.wdy.biz.wdy.thread;

import cn.hutool.core.thread.ThreadUtil;

import java.util.concurrent.ExecutorService;

/**
 * @author wgch
 * @Description 线程：线程是jvm调度的最小单元，也叫做轻量级进程，进程是由线程组成，线程拥有私有的程序技术器以及栈，并且能够访问堆中的共享资源。
 * 这里提出一个问题，为什么要用多线程？
 * 1、首先，随着cpu核心数的增加，计算机硬件的并行计算能力得到提升，而同一个时刻一个线程只能运行在一个cpu上，那么计算机的资源被浪费了，所以需要使用多线程。
 * 2、其次，也是为了提高系统的响应速度，如果系统只有一个线程可以执行，那么当不同用户有不同的请求时，由于上一个请求没处理完，那么其他的用户必定需要在一个队列中等待，大大降低响应速度，所以需要多线程。
 * @date 2019/11/5
 */
public class MyThreadTest {

    public static void main(String[] args) throws InterruptedException {
        MyThread myThread = new MyThread();
        myThread.run();
        System.out.println("---------------------");
        MyRunnable myRunnable = new MyRunnable();
        myRunnable.run();
        // 1、线程安全测试：
        // 线程安全是多线程编程中经常需要考虑的一个问题，线程安全是指多线程环境下多个线程可能会同时对同一段代码或者共享变量进行执行，
        // 如果每次运行的结果和单线程下的结果是一致的，那么就是线程安全的，如果每次运行的结果不一致，那么就是线程不安全的。
        ExecutorService executorService = ThreadUtil.newExecutor();
        MySafe1 mySafeRunnable = new MySafe1();
        Thread t1 = new Thread(mySafeRunnable);
        Thread t2 = new Thread(mySafeRunnable);
        t1.start();
        System.out.println("-----------------");
        t2.start();
        // 可以看到，这里同时两个线程同时对共享变量j进行访问，减1，并且多次执行程序的结果还不一致，这就是线程不安全的情况，通过加锁可以保证线程安全（synchronized 对象锁）。
        // 因为当加锁过后，每次只能有一个线程访问被加锁的代码，这样就不会出现线程安全了

        // 2、唤醒线程
        //  主线程将创建一个线程t1然后进入t1的锁的同步块中启动线程t1，然后调用wait进入等待状态，这个时候线程t1也进入到同步块中，调用notify后释放掉锁，可以看到主线程后续的东西继续被输出。
        //  当有多个线程调用了wait之后如果采用notify只会随机的唤醒其中的一个线程进入阻塞态，而采用notifyall会将所有的线程给唤醒。在线程运行结束后会调用notifyall将所有等待状态的线程唤醒。
        MySafe2 mySafe2 = new MySafe2("t1");
        /**
         * 在java同步代码快中：synchronized的使用方式（对对象进行加锁）
         * 1、通过对一个对象进行加锁来实现同步
         */
        synchronized (mySafe2) {
            System.out.println(Thread.currentThread().getName() + " start ...");
            mySafe2.start();
            System.out.println(Thread.currentThread().getName() + " wait");
            mySafe2.wait();
            System.out.println(Thread.currentThread().getName() + " waked up");
        }

    }


}

class MyThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                // 每一次循环睡眠0.1s：让当前线程睡眠，睡眠一段时间后重新获取到cpu的使用权。
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(this.getName() + " Thread" + i);
        }
    }
}

class MyRunnable implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + " Runnable" + i);
        }
    }
}

class MySafe1 implements Runnable {
    static int j = 50;

    /**
     * 2、 对一个方法进行synchronized声明，进而对一个方法进行加锁来实现同步
     * 虚拟机会根据synchronized修饰的是实例方法还是类方法，去取对应的实例对象或者Class对象来进行加锁。
     */
    @Override
    public synchronized void run() {
        for (int i = 0; i < 10; i++) {
            System.err.print(j-- + " ");
        }
    }
}

class MySafe2 extends Thread {
    public MySafe2(String name) {
        super(name);
    }

    @Override
    public void run() {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName() + " notify a thread");
            notify();
        }
        while (true) {
        }
    }
}