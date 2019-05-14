package com.wdy.biz.file.controller;

import com.jfinal.kit.PathKit;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

import static com.wdy.config.constant.CommonConstant.SEPARATOR;

/**
 * @author wgch
 * @Description 图片文件 base64
 * @date 2019/5/13 17:23
 */
public class ImageController {

    public static void main(String[] args) {
        String path = "D:\\wdy\\wdy_jfinal_demo\\src\\main\\webapp\\WEB-INF\\view\\book\\20190512.jpg";
        String imageToBase64 = imageToBase64(path);
        System.out.println(imageToBase64);

        boolean b = base64ToImage(imageToBase64, PathKit.getWebRootPath() + SEPARATOR + "download" + SEPARATOR+"123.jpg");
        System.out.println(b);

    }

    /**
     * 图片 转 base64
     *
     * @param imagePath
     * @return
     */
    public static String imageToBase64(String imagePath) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imagePath);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加密
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    /**
     * @param imgBase64Str base64编码字符串
     * @param imagePath    图片路径-具体到文件
     * @return
     * @Description: 将base64编码字符串转换为图片
     */
    public static boolean base64ToImage(String imgBase64Str, String imagePath) {
        if (imgBase64Str == null) {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //解密
            byte[] b = decoder.decodeBuffer(imgBase64Str);
            //处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(imagePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
