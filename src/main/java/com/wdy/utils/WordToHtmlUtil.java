package com.wdy.utils;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author wgch
 * @Description word -> html
 * @date 2020/4/26
 */
public class WordToHtmlUtil {


    /**
     * word->html
     *
     * @param filePath 文件路径
     * @return
     * @throws Exception
     */
    public static String word2003ToHtml(String filePath) throws Exception {
        InputStream input = new FileInputStream(filePath);
        HWPFDocument wordDocument = new HWPFDocument(input);
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
                DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());

        // 解析word文档
        wordToHtmlConverter.processDocument(wordDocument);
        Document htmlDocument = wordToHtmlConverter.getDocument();

        // 也可以使用字符数组流获取解析的内容
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(baos);

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer serializer = factory.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        serializer.transform(domSource, streamResult);

        // 也可以使用字符数组流获取解析的内容
        String content = new String(baos.toByteArray());
        baos.close();
        input.close();
        return content;
    }

    /**
     * html -> word
     *
     * @param context  内容字符串
     * @param filePath 文件路径
     * @throws Exception
     */
    public static void html2Word(String context, String filePath) throws Exception {
        InputStream is = new ByteArrayInputStream(context.getBytes(StandardCharsets.UTF_8));
        OutputStream os = new FileOutputStream(filePath);
        POIFSFileSystem fs = new POIFSFileSystem();
        //对应于org.apache.poi.hdf.extractor.WordDocument
        fs.createDocument(is, "WordDocument");
        fs.writeFilesystem(os);
        os.close();
        is.close();
    }

}
