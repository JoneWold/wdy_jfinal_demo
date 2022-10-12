package com.wdy.utils;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.io.FileUtil;

import java.io.File;
import java.io.IOException;

/**
 * @author wgch
 * @date 2022/4/29
 */
public class FileUtils {


    // This method does not need to be synchronized because createNewFile()
    // is atomic and used here to mark when a file name is chosen
    public static File rename(File f) {
        if (createNewFile(f)) {
            return f;
        }
        String name = f.getName();
        String body = null;
        String ext = null;

        int dot = name.lastIndexOf(".");
        if (dot != -1) {
            body = name.substring(0, dot);
            ext = name.substring(dot);  // includes "."
        } else {
            body = name;
            ext = "";
        }

        // Increase the count until an empty spot is found.
        // Max out at 9999 to avoid an infinite loop caused by a persistent
        // IOException, like when the destination dir becomes non-writable.
        // We don't pass the exception up because our job is just to rename,
        // and the caller will hit any IOException in normal processing.
        int count = 0;
        while (!createNewFile(f) && count < 9999) {
            count++;
            String newName = body + count + ext;
            f = new File(f.getParent(), newName);
        }

        return f;
    }

    private static boolean createNewFile(File f) {
        try {
            return f.createNewFile();
        } catch (IOException var3) {
            return false;
        }
    }

    /**
     * 文件 转 base64
     */
    public static String fileToBase64(File file) {
        String encode = Base64Encoder.encode(FileUtil.readBytes(file));
        return encode;
    }

    /**
     * base64 转 文件
     */
    public static File base64ToFile(String base64Str, String toPath) {
        byte[] decode = Base64Decoder.decode(base64Str);
        FileUtil.writeBytes(decode, toPath);
        return FileUtil.file(toPath);
    }


    public static void main(String[] args) {
        // 获取base64
        String fileToBase64 = fileToBase64(FileUtil.file("D:\\MyWorks\\IdeaProjects\\JoneWold\\wdy_jfinal_demo\\src\\test\\java\\w1.wps"));
        System.out.println("src ：" + fileToBase64);

        //获取文件
        File file = base64ToFile(fileToBase64, "D:\\MyWorks\\IdeaProjects\\JoneWold\\wdy_jfinal_demo\\src\\test\\java\\w22.doc");

    }

}
