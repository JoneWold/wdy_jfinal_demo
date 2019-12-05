package com.wdy.biz.wdy;

import cn.hutool.core.date.DateUtil;
import cn.hutool.cron.CronUtil;

/**
 * @author wgch
 * @Description 定时任务
 * @date 2019/12/3
 */
public class WdyCronTask implements Runnable {
    public static int count = 0;

    public void start() {
        System.out.printf("第%d次执行定时任务，当前时间：%s\n", ++count, DateUtil.now());
    }

    @Override
    public void run() {
        System.out.printf("run 第%d次执行定时任务，当前时间：%s\n", ++count, DateUtil.now());
    }

    public static void main(String[] args) {
        CronUtil.setMatchSecond(true);
        CronUtil.start();
        CronUtil.schedule("*/2 * * * * ?", (Runnable) () -> {
            System.out.println(DateUtil.now() + " 执行新任务");
        });
    }

}
