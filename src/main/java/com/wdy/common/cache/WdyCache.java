package com.wdy.common.cache;

import com.jfinal.plugin.activerecord.cache.ICache;
import com.jfinal.plugin.ehcache.CacheKit;

/**
 * Created by wgch on 2019/3/7.
 * 使用任意缓存实现 ？？？
 */
public class WdyCache implements ICache {

    @Override
    public <T> T get(String cacheName, Object key) {
        return CacheKit.get(cacheName, key);
    }

    @Override
    public void put(String cacheName, Object key, Object value) {
        CacheKit.put(cacheName, key, value);
    }

    @Override
    public void remove(String cacheName, Object key) {
        CacheKit.remove(cacheName, key);
    }

    @Override
    public void removeAll(String cacheName) {
        CacheKit.removeAll(cacheName);
    }
}
