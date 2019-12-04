package com.wdy.bizz.hello;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.cron.CronUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/**
 * @author wgch
 * @Description
 * @date 2019/11/30
 */
public class TestBase {

    /**
     * CountDownLatch：阻塞观察定时任务运行结果
     */
    @Test
    public void timerTest() throws InterruptedException {
        //设置Cron表达式匹配到秒，不然使用的是Linux的crontab表达式，最小单位是分钟
        CronUtil.setMatchSecond(true);
        CronUtil.start();
        //可以手动停止定时任务
        //CronUtil.stop();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatch.await();
    }

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

        // TODO 基本数据类型与包装数据类型的使用标准
        //Reference:《阿里巴巴Java开发手册》
        //【强制】所有的 POJO 类属性必须使用包装数据类型。
        //【强制】RPC 方法的返回值和参数必须使用包装数据类型。
        //【推荐】所有的局部变量使用基本数据类型。
        //比如我们如果自定义了一个Student类,其中有一个属性是成绩score,如果用Integer而不用int定义,一次考试,学生可能没考,值是null,也可能考了,但考了0分,值是0,这两个表达的状态明显不一样.
        //       说明 :POJO 类属性没有初值是提醒使用者在需要使用时，必须自己显式地进行赋值，任何 NPE 问题，或者入库检查，都由使用者来保证。
        //正例 : 数据库的查询结果可能是 null，因为自动拆箱，用基本数据类型接收有 NPE 风险。
        //反例 : 比如显示成交总额涨跌情况，即正负 x%，x 为基本数据类型，调用的 RPC 服务，调用不成功时，返回的是默认值，页面显示为 0%，这是不合理的，应该显示成中划线。所以包装数据类型的 null 值，能够表示额外的信息，如:远程调用失败，异常退出。

    }

    @Test
    public void testList() {

        String[] myArray = {"Apple", "Banana", "Orange"};
        List<String> myList = Arrays.asList(myArray);
        //上面两个语句等价于下面一条语句
        List<String> myList2 = Arrays.asList("Apple", "Banana", "Orange");


    }


    /**
     * 加密工具
     * Hutool提供了常用的对称加密（例如：AES、DES等）、非对称加密（例如：RSA、DSA等）、摘要加密（例如：MD5、SHA-1、SHA-256、HMAC等）的加解密工具。
     */
    @Test
    public void huTool() {
        String beforeStr = "123456";
        //md5加密(摘要加密)
        Assert.assertEquals("e10adc3949ba59abbe56e057f20f883e", SecureUtil.md5(beforeStr));

        //AES加密(对称加密)
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
        AES aes = SecureUtil.aes(key);
        String encryptStr = aes.encryptHex(beforeStr);
        String decryptStr = aes.decryptStr(encryptStr, CharsetUtil.CHARSET_UTF_8);
        Assert.assertEquals(beforeStr, decryptStr);

        //RSA加密（非对称加密）
        RSA rsa = SecureUtil.rsa();
        //签名，私钥加密，公钥解密
        byte[] encrypt = rsa.encrypt(StrUtil.bytes(beforeStr, CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
        byte[] decrypt = rsa.decrypt(encrypt, KeyType.PrivateKey);
        Assert.assertEquals(beforeStr, StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8));
        //加密私钥加密，公钥解密
        byte[] encrypt2 = rsa.encrypt(StrUtil.bytes(beforeStr, CharsetUtil.CHARSET_UTF_8), KeyType.PrivateKey);
        byte[] decrypt2 = rsa.decrypt(encrypt2, KeyType.PublicKey);
        Assert.assertEquals(beforeStr, StrUtil.str(decrypt2, CharsetUtil.CHARSET_UTF_8));
    }


}
