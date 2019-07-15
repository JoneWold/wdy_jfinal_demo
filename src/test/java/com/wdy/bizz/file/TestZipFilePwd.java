package com.wdy.bizz.file;

import com.wdy.biz.file.util.XmlZipFileUtil;
import lombok.val;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.zip.ZipException;


/**
 * @author wgch
 * @Description 压缩文件 解压文件
 * @date 2019/6/13 16:03
 */
public class TestZipFilePwd {

    /**
     * 测试加密压缩ZIP
     */
    @Test
    public void testinq() throws net.lingala.zip4j.exception.ZipException {
        ZipFile zipFile = new ZipFile("D:\\wdy\\wdy_jfinal_demo\\target\\test.zip");
        ZipParameters parameters = new ZipParameters();
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);

        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
        parameters.setEncryptFiles(true);

        parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);


        parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
        parameters.setPassword("123");

        zipFile.addFile(new File("D:\\wdy\\wdy_jfinal_demo\\download\\123.jpg"), parameters);
    }

    @Test
    public void testUn7zs() {
        String srcPath = "D:\\IdeaProjects\\gb\\file\\exp\\testF.hzb";
        String newParh = "D:\\IdeaProjects\\gb\\file\\exp\\testA";
        String pwd = "Z82z3H4b5z6d24b7";
        XmlZipFileUtil.un7z(srcPath, newParh, pwd);
    }

    @Test
    public void testUn7z() throws IOException, ZipException {
        SevenZFile sevenZFile = new SevenZFile(new File("D:\\IdeaProjects\\gb\\file\\exp\\testA.hzb"), "Z82z3H4b5z6d24b7".toCharArray());
        byte[] data = new byte[1024];
        int len;
        SevenZArchiveEntry nextEntry;
        while ((nextEntry = sevenZFile.getNextEntry()) != null) {
            File file = new File("D:\\IdeaProjects\\gb\\file\\exp\\testF\\" + nextEntry.getName());
            val directory = nextEntry.isDirectory();
            System.out.println("fileName:" + nextEntry.getName() + "   size:" + nextEntry.getSize() + " isDir:" + directory);
            //文件是否创建成功
            if (directory) {
                val mkdirs = file.mkdirs();
            } else {
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    boolean mkdirs = parentFile.mkdirs();
                }
                boolean isOk = file.createNewFile();
                FileOutputStream outputStream = new FileOutputStream(file);
                while ((len = sevenZFile.read(data)) != -1) {
                    outputStream.write(data, 0, len);
                    outputStream.flush();
                }
                outputStream.close();
            }
        }
    }


    @Test
    public void testUnZip() throws net.lingala.zip4j.exception.ZipException {
        ZipFile zipFile = new ZipFile("D:\\IdeaProjects\\gb\\file\\exp\\s.zip");
        zipFile.setPassword("Z82z3H4b5z6d24b7");
        zipFile.extractAll("D:\\IdeaProjects\\gb\\file\\exp\\s");
        zipFile.getFile().delete();
    }

    /**
     * 测试加密压缩ZIP
     */
    @Test
    public void testXmlZipFileUtil() {
        File srcfile = new File("D:\\IdeaProjects\\gb\\file\\exp\\testF");
        File zipfile = new File("D:\\IdeaProjects\\gb\\target\\testF.zip");
        XmlZipFileUtil.zipFilePwd(srcfile, zipfile, "Z82z3H4b5z6d24b7");
    }


    /**
     * 测试加密压缩ZIP
     */
    @Test
    public void testZipFilePWD() throws net.lingala.zip4j.exception.ZipException {
        ZipFile zipFile = new ZipFile("D:\\wdy\\wdy_jfinal_demo\\target\\test1.zip");
        ZipParameters parameters = new ZipParameters();
        // 压缩方式
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        // 压缩级别
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
        parameters.setEncryptFiles(true);
        parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
        // 加密方式
        parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
        parameters.setPassword("123".toCharArray());
        zipFile.addFolder("D:\\wdy\\wdy_jfinal_demo\\download\\testF\\Photos", parameters);
        zipFile.addFolder("D:\\wdy\\wdy_jfinal_demo\\download\\testF\\Table", parameters);
        zipFile.addFile(new File("D:\\wdy\\wdy_jfinal_demo\\download\\testF\\gwyinfo.xml"), parameters);
    }

    /**
     * 寻找文件
     *
     * @throws IOException
     */
    @Test
    public void getFiles() throws IOException {
        Path path = Paths.get("D:\\wdy\\wdy_jfinal_demo\\target\\testF");
        Stream<Path> stream = Files.find(path, 2, (p, basicFileAttributes) -> String.valueOf(p).endsWith("xml"));
        stream.map(Path::getFileName).forEach(System.out::println);
    }

}
