package com.wdy.annotation;

import java.lang.annotation.*;

/**
 * Created by wgch on 2019/3/25.
 * 非空参数注解
 */
@Retention(RetentionPolicy.RUNTIME)  // 表示注解的信息被保留在class文件(字节码文件)中当程序编译时，会被虚拟机保留在运行时
@Target({ElementType.METHOD})        // 表示该注解只能用来修饰在方法上
@Documented
@Inherited
public @interface NotNull {
    String[] paras() default {};    // 属性paras类型为数组,默认值为 空

    String world();                 // 属性world类型为String,没有默认值

    String hello() default "ss";    // 属性hello类型为String,默认值为ss

    int[] array() default {2, 3, 4, 5};  // 属性array类型为数组,默认值为2，3，4，5

    Class style() default String.class;  // 属性style类型为Class,默认值为String类型的Class类型


}
