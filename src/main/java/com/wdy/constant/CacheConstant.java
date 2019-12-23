package com.wdy.constant;

/**
 * 用于存放缓存前缀与缓存名称常量
 *
 * @author 毛文超
 * @date 2019/3/116:22 PM
 */
public class CacheConstant {

    /**
     * 用户缓存缓存名
     */
    public static final String CACHE_USER = "user";
    /**
     * 用户缓存前缀
     */
    public static final String CACHE_USER_PREFIX = "user:";

    /**
     * 缓存记录登录失败次数记录名称
     */
    public static final String CACHE_USER_LOGIN_FAILED_COUNT = "userLoginFailedCount";
    /**
     * 缓存登录次数的key前缀
     */
    public static final String CACHE_USER_LOGIN_FAILED_COUNT_PREFIX = "logFailed:";
    /***
     * 层级码缓存名称
     * */
    public static final String LEVEL_CODE = "levelCode";
    /**
     * 层级码缓存前缀
     */
    public static final String LEVEL_CODE_PREFIX = "code:";
    /**
     * 字典表缓存
     */
    public static final String DICT_CACHE_NAME = "dict";
    /***
     * 字典表缓存前缀
     * */
    public static final String DICT_CACHE_PREFIX = "dict:";

    /**
     * 缓存名称
     */
    public static final String WDY_CACHE = "wdyCache";

}
