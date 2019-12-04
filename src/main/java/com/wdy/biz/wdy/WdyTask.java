package com.wdy.biz.wdy;

import cn.hutool.core.date.DateUtil;

/**
 * @author wgch
 * @Description 定时任务
 * @date 2019/12/3
 */
public class WdyTask {
    public static int count = 0;

    public void start() {
        System.out.printf("第%d次执行定时任务，当前时间：%s\n", ++count, DateUtil.now());
    }

}
