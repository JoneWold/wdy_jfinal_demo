package com.wdy.biz.wdy.thread;

import com.wdy.dto.BankAccount;

import java.util.Random;

import static com.wdy.biz.wdy.thread.JavaExecutorService.htService;

/**
 * @author wgch
 * @Description synchronized
 * @date 2020/7/29
 */
public class MySync {


    public static void main(String[] args) {
        bank();
    }

    /**
     * synchronized可以修饰普通方法，静态方法和代码块。
     * 当synchronized修饰一个方法或者一个代码块的时候，它能够保证在同一时刻最多只有一个线程执行该段代码。
     */
    private static void bank() {
        BankAccount myAccount = new BankAccount("accountOfMG", 1000.00);
        for (int i = 0; i < 10; i++) {
            htService.execute(() -> {
                try {
                    int var = new Random().nextInt(10);
                    Thread.sleep(var);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                double deposit = myAccount.deposit(100.00);
                System.err.println(Thread.currentThread().getName() + " balance:" + deposit);
            });
        }


        for (int i = 0; i < 10; i++) {
            htService.execute(() -> {
                try {
                    int var = new Random().nextInt(10);
                    Thread.sleep(var);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                double deposit = myAccount.withdraw(100.00);
                System.out.println(Thread.currentThread().getName() + " balance:" + deposit);
            });
        }
        System.out.println("---->>>" + myAccount.getBalance());
    }


}
