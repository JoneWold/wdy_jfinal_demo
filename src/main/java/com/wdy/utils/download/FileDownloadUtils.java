package com.wdy.utils.download;

import com.jfinal.render.RenderException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author wgch
 * @Description 流 通用文件下载
 * @date 2019/5/10 9:21
 */
public class FileDownloadUtils {

    /**
     * 下载文件
     *
     * @param fileVo
     * @param request
     * @param response
     */
    public static void renderFile(FileVo fileVo, HttpServletRequest request, HttpServletResponse response) {
        ServletContext servletContext = request.getServletContext();
        response.setHeader("Accept-Ranges", "bytes");
        response.setHeader("Content-disposition", "attachment; " + encodeFileName(request, fileVo.getFileName()));
        String contentType = servletContext.getMimeType(fileVo.getFileName());
        response.setContentType(contentType != null ? contentType : "application/octet-stream");
        normalRender(fileVo.getData(), response);
    }

    /**
     * @param data
     * @param response
     */
    public static void normalRender(byte[] data, HttpServletResponse response) {
        response.setHeader("Content-Length", String.valueOf(data.length));
        OutputStream outputStream;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(data);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {    // ClientAbortException、EofException 直接或间接继承自 IOException
            String name = e.getClass().getSimpleName();
            if (name.equals("ClientAbortException") || name.equals("EofException")) {
            } else {
                throw new RenderException(e);
            }
        } catch (Exception e) {
            throw new RenderException(e);
        }
    }

    /**
     * 依据浏览器判断编码规则
     *
     * @param request
     * @param fileName
     * @return
     */
    public static String encodeFileName(HttpServletRequest request, String fileName) {
        String userAgent = request.getHeader("User-Agent");
        try {
            String encodedFileName = URLEncoder.encode(fileName, "UTF8");
            // 如果没有UA，则默认使用IE的方式进行编码
            if (userAgent == null) {
                return "filename=\"" + encodedFileName + "\"";
            }

            userAgent = userAgent.toLowerCase();
            // IE浏览器，只能采用URLEncoder编码
            if (userAgent.contains("msie")) {
                return "filename=\"" + encodedFileName + "\"";
            }

            // Opera浏览器只能采用filename*
            if (userAgent.contains("opera")) {
                return "filename*=UTF-8''" + encodedFileName;
            }

            // Safari浏览器，只能采用ISO编码的中文输出,Chrome浏览器，只能采用MimeUtility编码或ISO编码的中文输出
            if (userAgent.contains("safari") || userAgent.contains("applewebkit") || userAgent.contains("chrome")) {
                return "filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO8859-1") + "\"";
            }

            // FireFox浏览器，可以使用MimeUtility或filename*或ISO编码的中文输出
            if (userAgent.indexOf("mozilla") != -1) {
                return "filename*=UTF-8''" + encodedFileName;
            }

            return "filename=\"" + encodedFileName + "\"";
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }


}
