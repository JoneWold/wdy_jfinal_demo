package com.wdy.config.constant;

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
     * */
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
     * */
    public static final String LEVEL_CODE_PREFIX = "code:";
    /**
     * 字典表缓存
     * */
    public static final String DICT_CACHE_NAME = "dict";
    /***
     * 字典表缓存前缀
     * */
    public static final String DICT_CACHE_PREFIX = "dict:";
    /**
     * 数字一
     */
    public static final String DATA_ONE="1";
    /**
     * 文件缓存
     */
    public static final String FILE="file";
    /**
     * 文件缓存前缀
     */
    public static final String FILE_PREFIX="file:";

    /**
     * 文件上传进度
     */
    public static final String FILE_UPLOAD_PROGRESS = "fileUploadProgress";
    /**
     * 文件上传进度
     */
    public static final String FILE_UPLOAD_PROGRESS_PREFIX = "fileUploadProgress:";
    /**
     * 文件下载进度
     */
    public static final String FILE_DOWNLOAD_PROGRESS = "fileDownloadProgress";
    /**
     * 文件下载进度
     */
    public static final String FILE_DOWNLOAD_PROGRESS_PREFIX = "fileDownloadProgress:";
    /**
     * 数据交换进度
     */
    public static final String EXCHANGE_DATA_PROGRESS = "exchangeDataProgress";
    /**
     * 数据交换进度
     */
    public static final String EXCHANGE_DATA_PROGRESS_PREFIX = "exchangeDataProgress:";
    /**
     * 数据校核进度
     */
    public static final String VERIFY_DATA_PROGRESS = "verifyDataProgress";
    /**
     * 数据校核进度
     */
    public static final String VERIFY_DATA_PROGRESS_PREFIX = "verifyDataProgress:";
    /**
     * 人员数据效验进度
     */
    public static final String VERIFY_DATA_MEM_PROGRESS = "verifyDataMemProgress";
    /**
     * 人员数据效验进度
     */
    public static final String VERIFY_DATA_MEM_PROGRESS_PREFIX = "verifyDataMemProgress:";
    /**
     * 机构数据效验进度
     */
    public static final String VERIFY_DATA_ORG_PROGRESS = "verifyDataOrgProgress";
    /**
     * 机构数据效验进度
     */
    public static final String VERIFY_DATA_ORG_PROGRESS_PREFIX = "verifyDataOrgProgress:";
}
