package com.wdy.config.log;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Created by wgch on 2019/3/15.
 */
public class UseLog4j {
    //日志记录器
    private static Logger LOGGER = LogManager.getLogger(UseLog4j.class);

    //循环次数
    private static long CYCLE = 102;

    //程序入口——主函数
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        //自动快速地使用缺省Log4j环境
        BasicConfigurator.configure();
        for (int i = 0; i < CYCLE; i++) {
            if (i < 100) {
                try {
                    //打印对象的信息
                    LOGGER.info(new Person("wdy", (char) (100 / i), 'M'));
                } catch (Exception e) {
                    //打印对象的信息
                    LOGGER.error(i + "岁的小孩还不存在嘛！");
                } finally {
                    //打印对象的信息
                    LOGGER.warn(i + " 现在大部分人的年龄都在0到100岁之间的!");
                }
            } else {
                //打印对象的信息
                LOGGER.info("我是一棵树，我今年活了" + i + "岁!哈哈，我厉害吧！");
            }
        }
        //打印程序运行的时间
        LOGGER.debug("此程序的运行时间是：" + (System.currentTimeMillis() - startTime));
    }
}
