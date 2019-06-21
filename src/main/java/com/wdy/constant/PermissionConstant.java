package com.wdy.constant;

/**
 * @author TMW
 * @version 1.0
 * @Description: 权限常量类
 * @date 2019/3/8 16:03
 */
public class PermissionConstant {
    /**
     * 有权限
     */
    public static final String IS_ACCESS = "1";
    /**
     * 无权限
     */
    public static final String NO_ACCESS = "0";
    /**
     * 前端请求中header中权限字段
     */
    public static final String HEADER_PERMISSION = "permission";
    /**
     * 前端请求中header中token
     */
    public static final String HEADER_AUTHORIZATION = "Authorization";

    /**
     * 缓存中用户token的前缀
     */
    public static final String PRE_TOKEN = "user:";

    /**
     * 请求机构相关接口,需要的额外参数
     */
    public static final String ORG_CODE = "orgCode";
    /**
     * 用户权限中间表组织key
     */
    public static final String ORG_ID = "orgId";
    /**
     * 用户权限中间表是否只读key
     */
    public static final String IS_READ_ONLY = "isReadOnly";
}
