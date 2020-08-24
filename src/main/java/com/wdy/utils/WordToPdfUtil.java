package com.wdy.utils;


import com.aspose.words.Document;
import com.aspose.words.FontSettings;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author wgch
 * @Description word -> pdf
 * @date 2019/6/10
 */
public class WordToPdfUtil {
    static {
        License license = new License();
        try {
            license.setLicense(new ByteArrayInputStream(new byte[0]));
        } catch (Exception var2) {
            var2.printStackTrace();
            throw new RuntimeException(var2);
        }
    }

    public static void doc2Pdf(String inPath, String outPath) {
        FileOutputStream os = null;
        try {
            // 新建一个空白pdf文档
            File file = new File(outPath);
            os = new FileOutputStream(file);

            // Address是将要被转化的word文档
            Document doc = new Document(inPath);
            FontSettings.getDefaultInstance().setFontsFolder(WordToPdfUtil.class.getClassLoader().getResource("./fonts").toURI().getPath(), true);
            doc.save(os, SaveFormat.PDF);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
