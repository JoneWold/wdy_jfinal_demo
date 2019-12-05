package com.wdy.bizz.hello;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.StreamProgress;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.cron.CronUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import cn.hutool.http.HttpUtil;
import com.jfinal.kit.PathKit;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.util.concurrent.CountDownLatch;

/**
 * @author wgch
 * @Description HuTool
 * @date 2019/12/5
 */
public class TestHuTool {

    /**
     * 二维码
     */
    @Test
    public void testQrCode() {
        QrConfig config = new QrConfig(300, 300);
        // 设置边距，既二维码和背景之间的边距
        config.setMargin(3);
        // 设置前景色，既二维码颜色（青色）
        config.setForeColor(Color.CYAN.getRGB());
        // 设置背景色（灰色）
        config.setBackColor(Color.GRAY.getRGB());
        String toFileName = "qrCode" + System.currentTimeMillis() + ".jpg";
        File file = FileUtil.file(PathKit.getWebRootPath() + "/download/" + toFileName);
        // 生成二维码到文件，也可以到流
        QrCodeUtil.generate("http://hutool.cn/", config, file);
        // 将文本内容编码为二维码
        QrCodeUtil.encode("这是一个二维码", config);
        // 附带logo小图标
        QrCodeUtil.generate("1153540212", QrConfig.create().setImg(PathKit.getWebRootPath() + "/download/123.jpg"), file);
    }

    /**
     * 启动定时任务
     */
    @Test
    public void testCron() throws InterruptedException {
        //设置Cron表达式匹配到秒，不然使用的是Linux的crontab表达式，最小单位是分钟
        CronUtil.setMatchSecond(true);
        CronUtil.start();
        //可以手动停止定时任务
        //CronUtil.stop();
        // CountDownLatch：阻塞观察定时任务运行结果
        CountDownLatch latch = new CountDownLatch(1);
        latch.await();

    }

    /**
     * 加密工具
     * Hutool提供了常用的对称加密（例如：AES、DES等）、非对称加密（例如：RSA、DSA等）、摘要加密（例如：MD5、SHA-1、SHA-256、HMAC等）的加解密工具。
     */
    @Test
    public void pwd() {
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

    /**
     * 文件下载
     */
    @Test
    public void fileDownload() {
        String fileUrl = "http://mirrors.sohu.com/centos/filelist.gz";
        //带进度显示的文件下载
        long size = HttpUtil.downloadFile(fileUrl, FileUtil.file(PathKit.getWebRootPath() + "/download/"), new StreamProgress() {
            @Override
            public void start() {
                System.out.println("开始下载...");
            }

            @Override
            public void progress(long progressSize) {
                System.out.printf("已下载：%s\n", FileUtil.readableFileSize(progressSize));
            }

            @Override
            public void finish() {
                System.out.println("下载完成...");
            }
        });
        System.out.println("Download size: " + size);

    }


}
