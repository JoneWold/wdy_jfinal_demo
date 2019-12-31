package com.wdy.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import com.jfinal.kit.LogKit;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.wdy.constant.CommonConstant.POINT;
import static com.wdy.constant.CommonConstant.SEPARATOR;

/**
 * 文件压缩解压
 *
 * @author wgch
 * @date 2019/6/13
 */
public class XmlZipFileUtil {
    private final static String FILEPATH_TABLE = "Table";
    private final static String FILEPATH_PHOTOS = "Photos";
    private static final String GWY_INFO = "gwyinfo";
    private static final String COMPRESS_COMMAND = "%s a -mhe -mmt%d -p%s %s %s/*";
    private static final String UNCOMPRESS_COMMAND = "%s x -p%s -O%s %s";

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
    public static Map unZip(String srcPath, String newParh, String pwd) {
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(srcPath);
            zipFile.setPassword(pwd);
            zipFile.extractAll(newParh);
            File file = zipFile.getFile();
            if (file != null) {
                boolean delete = file.delete();
                if (delete) {
                    LogKit.info("源文件删除成功!");
                } else {
                    LogKit.info("源文件删除失败!");
                }
            }
        } catch (Exception e) {
            if (ObjectUtil.isNotNull(zipFile)) {
                File file = zipFile.getFile();
                if (file != null) {
                    boolean delete = file.delete();
                    if (delete) {
                        LogKit.info("源文件删除成功!");
                    } else {
                        LogKit.info("源文件删除失败!");
                    }
                }
            }
            LogKit.error("Zip文件验证失败", e);
            return new HashMap<String, String>(1) {{
                put("msg", "文件内容无法通过验证，请确保该文件格式正常");
            }};
        }
        return null;
    }

    /**
     * 解压加密HZB
     *
     * @param srcPath
     * @param newParh
     * @param pwd
     */
    public static Map un7z(String srcPath, String newParh, String pwd) {
        File srcFile = new File(srcPath);
        SevenZFile sevenZFile = null;
        try {
            sevenZFile = new SevenZFile(srcFile, pwd.toCharArray());
        } catch (IOException e) {
            e.printStackTrace();
            LogKit.error("解压失败,无法打开7z文件!");
            if (sevenZFile != null) {
                try {
                    sevenZFile.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    LogKit.error("关闭7z数据流异常", e);
                }
            }
            return new HashMap(1) {{
                put("msg", "文件内容无法通过验证，请确保该文件格式正常");
            }};
        }
        SevenZArchiveEntry entry;
        try {
            while ((entry = sevenZFile.getNextEntry()) != null) {
                boolean directory = entry.isDirectory();
                File file = new File(newParh + SEPARATOR + entry.getName());
                //目录则新建目录
                if (directory) {
                    boolean exists = file.exists();
                    if (!exists) {
                        boolean mkdirs = file.mkdirs();
                        if (!mkdirs) {
                            LogKit.error("新建解压缩目录失败:" + file.toString());
                        }
                    }

                } else {
                    //获取文件大小
                    long size = entry.getSize();
                    //最大为Int的最大值
                    byte[] content;
                    if (size > Integer.MAX_VALUE) {
                        content = new byte[Integer.MAX_VALUE];
                    } else {
                        content = new byte[(int) size];
                    }
                    FileOutputStream outputStream = null;
                    try {
                        boolean exists = file.exists();
                        if (!exists) {
                            boolean isOk = file.createNewFile();
                            if (!isOk) {
                                LogKit.error("创建文件失败:" + file.toString());
                                continue;
                            }
                        }
                        outputStream = new FileOutputStream(file);
                        int len;
                        while ((len = sevenZFile.read(content)) != -1) {
                            outputStream.write(content, 0, len);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        LogKit.error("写文件失败!", e);
                    } finally {
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                                LogKit.error("关闭异常", e);
                            }
                        }
                        content = null;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogKit.error("读取7z文件失败!", e);
        } finally {
            //关闭资源
            try {
                sevenZFile.close();
            } catch (IOException e) {
                e.printStackTrace();
                LogKit.error("关闭7Z资源失败!", e);
            }
        }


        return null;
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

    /**
     * 转移图片文件
     *
     * @param srcPath 文件路径
     * @param newPath 目标文件路径
     */
    public static void copyPhotoFile(String srcPath, String newPath) {
        File srcFile = new File(srcPath);
        File newFile = new File(newPath);
        if (!srcFile.exists()) {
            srcFile.mkdirs();
        }
        if (!newFile.exists()) {
            newFile.mkdirs();
        }
        boolean directory = srcFile.isDirectory();
        if (!directory) {
            return;
        }
        try {
            Files.walk(srcFile.toPath()).forEach(path -> {
                File file = path.toFile();
                boolean isDir = file.isDirectory();
                if (isDir) {
                    return;
                }
                String name = file.getName();
                if (checkFileName(name)) {
                    try {
                        Files.copy(path, Paths.get(newPath + SEPARATOR + name), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        e.printStackTrace();
                        LogKit.error("copy文件异常!", e);
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            LogKit.error("遍历文件异常!", e);
        }
    }

    /**
     * 图片文件
     *
     * @param fileName
     * @return
     */
    public static boolean checkFileName(String fileName) {
        if (fileName.lastIndexOf(POINT) == -1) {
            return false;
        }
        String suffixStr = fileName.substring(fileName.lastIndexOf(POINT));
        if (".jpg".equals(suffixStr) || ".png".equals(suffixStr) || ".JPG".equals(suffixStr) || ".PNG".equals(suffixStr)
                || ".JPEG".equals(suffixStr) || ".jpeg".equals(suffixStr)) {
            return true;
        }
        return false;
    }


    /**
     * 获取文件列表
     *
     * @param tabList Table文件集合
     * @param gwyList gwyInfo文件集合
     * @param srcPath 文件路径
     */
    public static Record getFiles(List<Record> tabList, List<Record> gwyList, String srcPath) {
        Path path = Paths.get(srcPath);
        Stream<Path> stream;
        try {
            stream = Files.walk(path, FileVisitOption.FOLLOW_LINKS);
        } catch (IOException e) {
            LogKit.error("文件操作错误", e);
            return null;
        }
        for (Path p : stream.collect(Collectors.toList())) {
            File file = new File(p.toUri());
            if (file.isFile()) {
                Record record = new Record();
                Path fileName = p.getFileName();
                record.set("fileName", fileName);
                record.set("filePath", p);
                if (p.toString().contains(FILEPATH_TABLE)) {
                    tabList.add(record);
                } else if (p.toString().contains(FILEPATH_PHOTOS)) {

                } else {
                    gwyList.add(record);
                }
            }
        }
        Record record = new Record();
        record.set(FILEPATH_TABLE, tabList);
        record.set(GWY_INFO, gwyList);
        return record;
    }

    /**
     * 命令行压缩,使用7z工具
     *
     * @param srcPath 需要压缩的文件或者文件目录
     * @param zaPath  压缩包路径
     **/
    public static void commandCompress(String srcPath, String zaPath) {
        boolean isWindows = checkPlatformIsWindows();
        String platformPath = isWindows ? "windows/7z.exe" : "linux/7za";
        URL resource = XmlZipFileUtil.class.getClassLoader().getResource("./7z/" + platformPath);
        if (resource == null) {
            throw new RuntimeException("压缩失败,未找到7z工具!");
        } else {
            try {
                URI uri = resource.toURI();
                String commandPath = uri.getPath();
                String command = String.format(COMPRESS_COMMAND, commandPath, Runtime.getRuntime().availableProcessors(), "Z82z3H4b5z6d24b7", zaPath, srcPath);
                LogKit.info(command);
                try {
                    Process exec = Runtime.getRuntime().exec(command);
                    exec.waitFor();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
                LogKit.error("路径转换异常!", e);
                throw new RuntimeException("压缩失败,路径转换异常,未找到7z工具!", e);
            }
        }
    }

    /****
     * 命令行解压缩
     * @param distPath 解压目录
     * @param zaPath 压缩包目录
     * */
    public static void commandUncompress(String distPath, String zaPath) {
        boolean isWindows = checkPlatformIsWindows();
        String platformPath = isWindows ? "windows/7z.exe" : "linux/7za";
        URL resource = XmlZipFileUtil.class.getClassLoader().getResource("./7z/" + platformPath);
        if (resource == null) {
            throw new RuntimeException("压缩失败,未找到7z工具!");
        } else {
            try {
                URI uri = resource.toURI();
                String commandPath = uri.getPath();
                String command = String.format(UNCOMPRESS_COMMAND, commandPath, "Z82z3H4b5z6d24b7", distPath, zaPath);
                LogKit.info(command);
                Process exec = Runtime.getRuntime().exec(command);
                exec.waitFor();
            } catch (Exception e) {
                e.printStackTrace();
                LogKit.error("压缩失败!", e);
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 压缩Hzb
     *
     * @param scFile 原文件
     * @param toPath 目标路径
     */
    public static void getHzbFile(File scFile, String toPath) {
//        try (SevenZOutputFile out = new SevenZOutputFile(new File(toPath))) {
//            compress(out, scFile, "");
//        } catch (Exception e) {
//            LogKit.error("文件压缩失败", e);
//        }
    }

    private static void compress(SevenZOutputFile out, File file, String name) {
//        try {
//            if (file.isDirectory()) {
//                File[] files = file.listFiles();
//                if (ObjectUtil.isNotNull(files)) {
//                    if (files.length == 0) {
//                        SevenZArchiveEntry entry = out.createArchiveEntry(new File(name), name);
//                        out.putArchiveEntry(entry);
//                    } else {
//                        for (int i = 0; i < files.length; i++) {
//                            if ("Table".equals(name) || "Photos".equals(name)) {
//                                compress(out, files[i], name + SEPARATOR + files[i].getName());
//                            } else {
//                                compress(out, files[i], files[i].getName());
//                            }
//                        }
//                    }
//                }
//            } else {
//                SevenZArchiveEntry entry = out.createArchiveEntry(file, name);
//                out.putArchiveEntry(entry);
//                FileInputStream in = new FileInputStream(file);
//                BufferedInputStream bis = new BufferedInputStream(in);
//                byte[] b = new byte[2048];
//                int len;
//                while ((len = bis.read(b)) > 0) {
//                    out.write(b, 0, len);
//                }
//                out.closeArchiveEntry();
//                bis.close();
//                in.close();
//            }
//        } catch (Exception e) {
//            LogKit.error("文件压缩失败", e);
//        }
    }


    /***
     * 压缩文件
     * @param srcFileList 文件列表
     * @param toPath  目标路径
     */
    public static void zipFile(List<File> srcFileList, String toPath) {
        try {
            ZipFile zip = new ZipFile(toPath);
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            for (File file : srcFileList) {
                zip.addFile(file, parameters);
            }
        } catch (Exception e) {
            LogKit.error(e.getMessage());
        }
    }


    /**
     * 获取当前操作系统
     */
    private static boolean checkPlatformIsWindows() {
        String oS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
        return oS.contains("win");
    }

    /**
     * 获取jFinal上传文件
     *
     * @param uploadFile 上传文件对象
     * @return file 原文件 或 重命名的文件(文件名含有空格)
     */
    public static File getUploadFile(UploadFile uploadFile) {
        File file = uploadFile.getFile();
        String fileName = file.getName();
        String toFileName = fileName.replaceAll(" ", "");
        // 文件上传到服务器 如果文件名包含空格，该文件可能无法访问，所以操作文件之前需要去掉文件名中的空格
        if (fileName.equals(toFileName)) {
            return file;
        }
        // 上传文件路径
        String uploadPath = uploadFile.getUploadPath();
        File toFile = FileUtil.file(uploadPath + SEPARATOR + toFileName);
        if (toFile.exists()) {
            toFile.delete();
        }
        file.renameTo(toFile);
        return toFile;
    }

}
