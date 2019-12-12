package com.wdy.message;

/***
 * 定义全局状态码:
 *
 * 0  代表操作成功代码
 * 10 开始代表通用的错误代码
 * 11 开始代表用户相关错误代码
 * 12 开始代表角色相关错误代码
 * 13 开始代表权限相关错误代码
 *
 * @author 毛文超
 * */
public enum Status {

    /**
     * 操作成功
     */
    SUCCESS(0, "操作成功"),
    /**
     * 操作失败
     */
    FAIL(1000, "操作失败"),
    /**
     * 参数错误
     */
    PARA_ERROR(1001, "参数错误"),
    /**
     * 请检查参数是否正确
     */
    NOT_NULL_ERROR(1002, "请检查参数是否正确"),


    /**
     * 用户未登录
     */
    NOT_LOGIN(1100, "用户未登录"),
    /**
     * 用户名不能为空
     */
    USERNAME_ISEMPTY(1101, "用户名不能为空"),
    /**
     * 密码不能为空
     */
    PASSWORD_ISEMPTY(1102, "密码不能为空"),
    /**
     * 用户不存在
     */
    USERNAME_NOT_EXIST(1103, "用户不存在"),
    /**
     * 验证码错误
     */
    CODE_ERROR(1104, "验证码不匹配"),
    /**
     * 用户名或者密码不对
     */
    USER_NAME_OR_PASSWORD_ERROR(1105, "用户名或密码错误"),
    /**
     * 该用户已经被冻结24小时候后在登录
     */
    USERNAME_LOCK(1106, "该用户已经被冻结24小时候后在登录"),


    /**
     * 内建角色不可编辑
     */
    BUILT_IN_UNEDIT(1200, "内建角色不可编辑"),
    /**
     * 角色父ID参数错误
     */
    PARENTID_ISNULL(1201, "角色父ID参数错误"),
    /**
     * 角色code已经到达最大
     */
    ROLE_CODE_MAX(1202, "角色code已经达到最大"),
    /**
     * 角色已过期
     */
    ROLE_BEHIND_TIME(1203, "角色已过期"),
    /**
     * 角色不存在
     */
    ROLE_ISNOTEXIST(1204, "角色不存在"),
    /**
     * 内建字段不可编辑
     */
    BUILT_IN_FIELD_UNEDIT(1205, "内建字段不可编辑"),
    /**
     * 角色Id为空
     */
    ROLE_ID_ISEMPTY(1206, "角色Id为空"),
    /**
     * 父角色不能是自身
     */
    PARENTID_ERROR(1207, "父角色不能是自身!"),


    /**
     * token认证失败
     */
    TOKEN_AUTH_FAILD(1300, "token认证失败"),
    /**
     * 权限不足
     */
    PERMISSION_DENIED(1301, "权限不足"),
    /**
     * 权限不能为空
     */
    PERMISSION_NOT_NULL(1302, "权限不能为空"),
    /**
     * 权限数据错误
     */
    PERMISSION_LENGTH(1303, "权限数据错误"),
    /**
     * 角色已过期
     */
    ROLE_PAST(1304, "角色已过期"),

    /**
     * 权限越界
     */
    PERMISSION_INDEX_OUT(1305, "权限越界"),
    /**
     * 权限数据错误
     */
    PERMISSION_length(1306, "权限数据错误"),

    /**
     * 文件格式错误
     */
    FILE_FORMAT_ERROR(2000, "文件格式错误"),

    /**
     * 该对象在数据库存不存在,不能修改
     */
    OBJECT_DBISNOTALIVE(9996, "该对象在数据库存不存在,不能修改"),
    /**
     * 该对象已在数据库存在不能保存
     */
    OBJECT_DBISALIVE(9997, "该对象已经在数据库存在"),
    /**
     * 每页显示个数大于100,太大
     */
    PAGE_MAX_PAGESIZE_ISLARGER(9998, "每页显示个数大于100"),

    /**
     * 系统错误
     */
    SYSTEM_ERROR(9999, "系统错误");

    /**
     * 标识码
     */
    private int code;

    /**
     * 消息
     */
    private String message;

    Status(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
