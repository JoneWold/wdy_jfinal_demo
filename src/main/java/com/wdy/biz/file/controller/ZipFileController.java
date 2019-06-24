package com.wdy.biz.file.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.wdy.constant.CommonConstant.SEPARATOR;

/**
 * @author wgch
 * @Description zip文件压缩
 * @date 2019/6/13 9:11
 */
public class ZipFileController {

    public static void main(String[] args) {
        zipFile(new File("D:\\wdy\\wdy_jfinal_demo\\download\\testF"), new File("D:\\wdy\\wdy_jfinal_demo\\target\\testF.zip"));

    }

    /**
     * 压缩文件
     *
     * @param srcFile 源文件路径
     * @param zipFile 目标文件路径
     */
    public static void zipFile(File srcFile, File zipFile) {
        try {
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));
            compress(out, srcFile, "");
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 压缩文件 递归
     *
     * @param out
     * @param sourceFile
     * @param base
     */
    public static void compress(ZipOutputStream out, File sourceFile, String base) {
        try {
            //如果路径为目录（文件夹）
            if (sourceFile.isDirectory()) {
                //取出文件夹中的文件（或子文件夹）
                File[] flist = sourceFile.listFiles();

                //如果文件夹为空
                if (flist.length == 0) {
                    out.putNextEntry(new ZipEntry(base + "/"));
                } else {
                    for (int i = 0; i < flist.length; i++) {
                        if ("Table".equals(base) || "Photos".equals(base)) {
                            compress(out, flist[i], base + SEPARATOR + flist[i].getName());
                        } else {
                            compress(out, flist[i], flist[i].getName());
                        }
                    }
                }
            } else {
                out.putNextEntry(new ZipEntry(base));
                FileInputStream fos = new FileInputStream(sourceFile);
                BufferedInputStream bis = new BufferedInputStream(fos);
                byte[] buf = new byte[2048];
                int len;
                while ((len = bis.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.closeEntry();
                fos.close();
                bis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
