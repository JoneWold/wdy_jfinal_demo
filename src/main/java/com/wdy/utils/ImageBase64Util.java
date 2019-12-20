package com.wdy.utils;

import cn.hutool.core.io.IoUtil;
import com.jfinal.kit.Base64Kit;
import com.jfinal.kit.PathKit;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static com.wdy.constant.CommonConstant.SEPARATOR;

/**
 * @author wgch
 * @Description 图片文件 base64
 * @date 2019/5/13 17:23
 */
public class ImageBase64Util {

    public static void main(String[] args) {
        String path = "D:\\wdy\\wdy_jfinal_demo\\src\\main\\webapp\\WEB-INF\\view\\book\\20190512.jpg";
        String imgToB64 = imgToB64(path);

        String imageToBase64 = imageToBase64(path);
        System.out.println(imageToBase64);

        String image = base64ToImage(imageToBase64, SEPARATOR + "download" + SEPARATOR + System.currentTimeMillis() + ".jpg");
        System.out.println(image);

    }

    /**
     * jFinal图片 转 base64
     */
    public static String imgToB64(String path) {
        InputStream inputStream;
        try {
            inputStream = Files.newInputStream(Paths.get(path), StandardOpenOption.READ);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        byte[] bytes = IoUtil.readBytes(inputStream);
        return Base64Kit.encode(bytes);
    }

    /**
     * 图片 转 base64
     *
     * @param imagePath 图片路径绝对路径
     * @return base64编码字符串
     */
    public static String imageToBase64(String imagePath) {
        InputStream inputStream;
        byte[] data = new byte[1];
        try {
            inputStream = new FileInputStream(imagePath);
            data = new byte[inputStream.available()];
            int read = inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        // 加密
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    /**
     * @param imgBase64Str base64编码字符串
     * @param imagePath    图片路径-具体到文件(相对路径)
     * @return
     * @Description: 将base64编码字符串转换为图片
     */
    public static String base64ToImage(String imgBase64Str, String imagePath) {
        if (imgBase64Str == null) {
            return null;
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
            OutputStream out = new FileOutputStream(PathKit.getWebRootPath() + imagePath);
            out.write(b);
            out.flush();
            out.close();
            return imagePath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
