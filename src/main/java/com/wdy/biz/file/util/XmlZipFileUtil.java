package com.wdy.biz.file.util;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import static com.wdy.constant.CommonConstant.SEPARATOR;

/**
 * 文件压缩解压
 *
 * @author wgch
 * @date 2019/6/13
 */
public class XmlZipFileUtil {

    /**
     * 加密压缩ZIP
     *
     * @param srcFile
     * @param zipFile
     * @param pwd
     */
    public static void zipFilePwd(File srcFile, File zipFile, String pwd) {
        try {
            // 加密压缩
            String zipFilePath = zipFile.getPath();
            ZipParameters parameters = new ZipParameters();
            // 压缩方式
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            // 压缩级别
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            parameters.setEncryptFiles(true);
            parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
            // 加密方式
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
            parameters.setPassword(pwd.toCharArray());
            ZipFile zip = new ZipFile(zipFilePath);
            ArrayList<File> list = new ArrayList<>();
            getFiles(srcFile.getPath(), list);
            for (File ff : list) {
                String path = ff.getPath();
                if (path.endsWith("xml")) {
                    zip.addFile(new File(path), parameters);
                } else {
                    zip.addFolder(path, parameters);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 解压加密ZIP
     *
     * @param srcPath
     * @param newParh
     * @param pwd
     */
    public static void unZip(String srcPath, String newParh, String pwd) {
        try {
            ZipFile zipFile = new ZipFile(srcPath);
            zipFile.setPassword(pwd);
            zipFile.extractAll(newParh);
            zipFile.getFile().delete();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 解压加密HZB
     *
     * @param srcPath
     * @param newParh
     * @param pwd
     */
    public static void un7z(String srcPath, String newParh, String pwd) {
        try {
            File srcFile = new File(srcPath);
            SevenZFile sevenZFile = new SevenZFile(srcFile, pwd.toCharArray());

            byte[] data = new byte[1024];
            int len;
            SevenZArchiveEntry nextEntry;
            while ((nextEntry = sevenZFile.getNextEntry()) != null) {
                File file = new File(newParh + SEPARATOR + nextEntry.getName());
                boolean directory = nextEntry.isDirectory();
                if (directory) {
                    file.mkdirs();
                } else {
                    file.createNewFile();
                    FileOutputStream outputStream = new FileOutputStream(file);
                    while ((len = sevenZFile.read(data)) != -1) {
                        outputStream.write(data, 0, len);
                        outputStream.flush();
                    }
                    outputStream.close();
                }
            }
            srcFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取Zip内文件集合
     *
     * @param path
     * @param Filelist
     */
    public static void getFiles(String path, ArrayList<File> Filelist) {
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                Filelist.add(new File(files[i].getPath()));
            }
        }
    }

}
