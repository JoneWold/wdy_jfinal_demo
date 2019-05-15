package com.wdy.biz.file.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import com.jfinal.kit.PathKit;
import com.wdy.common.utils.Logs;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author wgch
 * @Description
 * @date 2019/4/10 9:19
 */
public class FileController {
    private static final String SEPARATOR = File.separator;
    private final static String FILEPATH_PHOTOS = "wdyPhotos";

    public static void main(String[] args) {
        copyPhotos(PathKit.getWebRootPath() + SEPARATOR + "download" + SEPARATOR, "ss");
        getFiles("D:\\wdy\\wdy_jfinal_demo\\src\\main\\webapp\\WEB-INF\\view\\book\\20190512.jpg");
    }

    public void zip() throws Exception {
        //File zipFile = new File(zipFileName);
        System.out.println("压缩中...");

        //创建zip输出流
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(""));

        //创建缓冲输出流
        BufferedOutputStream bos = new BufferedOutputStream(out);

        File sourceFile = new File("");

        //调用函数
        compress(out, bos, sourceFile, sourceFile.getName());

        bos.close();
        out.close();
        System.out.println("压缩完成");

    }

    /**
     * 压缩文件 递归
     *
     * @param out
     * @param bos
     * @param sourceFile
     * @param base
     * @throws Exception
     */
    public void compress(ZipOutputStream out, BufferedOutputStream bos, File sourceFile, String base) throws Exception {
        //如果路径为目录（文件夹）
        if (sourceFile.isDirectory()) {

            //取出文件夹中的文件（或子文件夹）
            File[] flist = sourceFile.listFiles();

            //如果文件夹为空，则只需在目的地zip文件中写入一个目录进入点
            if (flist.length == 0) {
                System.out.println(base + "/");
                out.putNextEntry(new ZipEntry(base + "/"));
                //如果文件夹不为空，则递归调用compress，文件夹中的每一个文件（或文件夹）进行压缩
            } else {
                for (int i = 0; i < flist.length; i++) {
                    compress(out, bos, flist[i], base + "/" + flist[i].getName());
                }
            }
            //如果不是目录（文件夹），即为文件，则先写入目录进入点，之后将文件写入zip文件中
        } else {
            out.putNextEntry(new ZipEntry(base));
            FileInputStream fos = new FileInputStream(sourceFile);
            BufferedInputStream bis = new BufferedInputStream(fos);

            int tag;
            System.out.println(base);
            //将源文件写入到zip文件中
            while ((tag = bis.read()) != -1) {
                bos.write(tag);
            }
            bis.close();
            fos.close();

        }
    }


    /**
     * 拷贝文件
     *
     * @param filePath
     * @param fileName
     */
    public static void copyPhotos(String filePath, String fileName) {
        String path = "D:\\wdy\\wdy_jfinal_demo\\src\\main\\webapp\\WEB-INF\\view\\book\\20190512.jpg";

        File file = new File(path);
        try {
            FileInputStream in = new FileInputStream(file);
            BufferedOutputStream out = FileUtil.getOutputStream(filePath + SEPARATOR + FILEPATH_PHOTOS + SEPARATOR + fileName + ".jpg");
            long copy = IoUtil.copy(in, out, IoUtil.DEFAULT_BUFFER_SIZE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文件 递归
     *
     * @param path
     */
    public static void getFiles(String path) {
        File file = new File(path);
        // 如果这个路径是文件夹
        if (file.isDirectory()) {
            // 获取路径下的所有文件
            File[] files = file.listFiles();
            if (ObjectUtil.isNotNull(files) && files.length == 0) {
                return;
            }
            for (int i = 0; i < files.length; i++) {
                // 如果还是文件夹 递归获取里面的文件 文件夹
                if (files[i].isDirectory()) {
                    System.out.println("目录：" + files[i].getPath());
                    getFiles(files[i].getPath());
                } else {
                    String filePath = files[i].getPath();
                    System.out.println("文件：" + filePath);
                    // 获取文件夹名 左闭右开
                    String dirPath = filePath.substring(0, filePath.lastIndexOf(SEPARATOR));
                    String dir = dirPath.substring(dirPath.lastIndexOf(SEPARATOR) + 1);
                    Logs.printHr(dir);
                }
            }
        } else {
            System.out.println("文件：" + file.getPath());
        }
    }


}
