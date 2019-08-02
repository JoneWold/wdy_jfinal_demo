package com.wdy.biz.file.controller;

import cn.hutool.core.util.ObjectUtil;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static com.wdy.constant.CommonConstant.SEPARATOR;

/**
 * @author wgch
 * @Description zip文件压缩
 * @date 2019/6/13 9:11
 */
public class ZipFileController {

    public static void main(String[] args) throws IOException {
        String filePath = "D:\\wdy\\wdy_jfinal_demo\\download\\testF";
        zipFile(filePath + ".zip", new File(filePath));
        HzbFile(filePath + ".hzb", new File(filePath));
    }

    /**
     * 压缩文件
     *
     * @param toPath  目标文件路径
     * @param srcFile 源文件
     */
    public static void zipFile(String toPath, File srcFile) {
        try {
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(new File(toPath)));
            compress(out, srcFile, "");
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 压缩文件 递归
     *
     * @param out        压缩输出流
     * @param sourceFile 源文件
     * @param base       文件路径
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

    /**
     * 7z 压缩
     *
     * @param toPath 目标文件路径
     * @param scFile 原文件
     * @throws IOException
     */
    public static void HzbFile(String toPath, File scFile) throws IOException {
        try (SevenZOutputFile out = new SevenZOutputFile(new File(toPath))) {
            compress(out, scFile, "");
        }
    }

    private static void compress(SevenZOutputFile out, File file, String dir) {
        try {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (ObjectUtil.isNotNull(files)) {
                    if (files.length == 0) {
                        out.putArchiveEntry(out.createArchiveEntry(new File(dir), dir + SEPARATOR));
                    } else {
                        for (int i = 0; i < files.length; i++) {
                            if ("Table".equals(dir) || "Photos".equals(dir)) {
                                compress(out, files[i], dir + SEPARATOR + files[i].getName());
                            } else {
                                compress(out, files[i], files[i].getName());
                            }
                        }
                    }
                }
            } else {
                SevenZArchiveEntry entry = out.createArchiveEntry(file, dir);
                out.putArchiveEntry(entry);

                FileInputStream in = new FileInputStream(file);
                byte[] b = new byte[2048];
                int len;
                while ((len = in.read(b)) > 0) {
                    out.write(b, 0, len);
                }
                out.closeArchiveEntry();
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
