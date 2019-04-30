package com.wdy.annotation;

import java.lang.annotation.*;

/**
 * @author TMW
 * @version 1.0
 * @Description: 权限注解
 * @date 2019/3/8 15:34
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
@Documented
@Inherited
public @interface RequiresPermissions {
    int[] value() default -1;
}
