package com.wdy.common.log;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by wgch on 2019/3/15.
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    //姓名
    private String name;
    //性别
    private char sex;
    //年龄
    private int age;

}
