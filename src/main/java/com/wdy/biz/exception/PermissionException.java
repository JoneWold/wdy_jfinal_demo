package com.wdy.biz.exception;

/**
 * @author TMW
 * @version 1.0
 * @Description: 权限异常
 * @date 2019/3/8 16:27
 */
public class PermissionException extends RuntimeException {
    private static final long serialVersionUID = 8964077531482161130L;

    public PermissionException() {
        super();
    }

    public PermissionException(String message, Throwable cause) {
        super(message, cause);
    }

    public PermissionException(String message) {
        super(message);
    }

    public PermissionException(Throwable cause) {
        super(cause);
    }
}
