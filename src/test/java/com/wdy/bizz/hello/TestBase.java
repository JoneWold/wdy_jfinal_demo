package com.wdy.bizz.hello;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author wgch
 * @Description
 * @date 2019/11/30
 */
public class TestBase {

    @Test
    public void testBigDecimal() {
        float a = 1.0f - 0.9f;
        float b = 0.9f - 0.8f;
        System.out.println(a);
        System.out.println(b);
        // 精度丢失
        System.out.println(Objects.equals(a, b)); // false
        BigDecimal x = new BigDecimal(1.0);
        BigDecimal y = new BigDecimal(0.9);
        BigDecimal z = new BigDecimal(0.8);
        BigDecimal sub1 = x.subtract(y);
        BigDecimal sub2 = y.subtract(z);
        System.out.println(sub1);
        System.out.println(sub2);
        // 精度不丢失
        System.out.println(sub1.equals(sub2)); //true
        // BigDecimal 的大小比较 x.compareTo(y):返回 -1 表示小于，0 表示 等于， 1表示 大于。
        System.out.println(x.compareTo(y));    // 1
        // 通过 setScale方法设置保留几位小数以及保留规则
        BigDecimal pai = new BigDecimal("3.141592653589793");
        System.out.println(pai.setScale(5, BigDecimal.ROUND_HALF_DOWN));

    }


}
