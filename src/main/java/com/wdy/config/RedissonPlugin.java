package com.wdy.config;

import com.jfinal.kit.LogKit;
import com.jfinal.plugin.IPlugin;
import org.redisson.config.Config;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author wgch
 * @Description 缓存配置
 * @date 2019/6/21 16:53
 */
public class RedissonPlugin implements IPlugin {


    public static void main(String[] args) {
        try {
            InputStream resource = RedissonPlugin.class.getClassLoader().getResourceAsStream("D:\\wdy\\wdy_jfinal_demo\\src\\main\\resources\\redis\\redis-config.json");
            Config config = Config.fromJSON(resource);
            System.out.println(config);


        } catch (IOException e) {
            LogKit.error("初始化redis插件异常", e);
        }
    }

    @Override
    public boolean start() {
        return false;
    }

    @Override
    public boolean stop() {
        return false;
    }
}
