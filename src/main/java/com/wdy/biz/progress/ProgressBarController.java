package com.wdy.biz.progress;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.jfinal.config.Constants;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PathKit;
import com.jfinal.upload.UploadFile;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.multipart.FileRenamePolicy;

/**
 * @author wgch
 * @Description 进度条控制器
 * @date 2019/4/24 18:16
 */
public class ProgressBarController extends Controller {
    com.oreilly.servlet.MultipartRequest multipartRequest = null;
    static FileRenamePolicy fileRenamePolicy = new DefaultFileRenamePolicy();

    public UploadFile getFile(String parameterName, String saveDirectory, int maxPostSize, ProgressBarObserver observer) {
        HttpServletRequestProxy uploadRequest = new HttpServletRequestProxy(getRequest(), observer);
        List<UploadFile> uploadFiles = wrapMultipartRequest(uploadRequest, saveDirectory, maxPostSize, JFinal.me().getConstants().getEncoding());
        for (UploadFile uploadFile : uploadFiles) {
            if (uploadFile.getParameterName().equals(parameterName)) {
                return uploadFile;
            }
        }
        return null;
    }

    public UploadFile getFile(ProgressBarObserver observer) {
        HttpServletRequestProxy uploadRequest = new HttpServletRequestProxy(getRequest(), observer);
        Constants constants = JFinal.me().getConstants();
        List<UploadFile> uploadFiles = wrapMultipartRequest(uploadRequest, constants.getBaseUploadPath(),
                constants.getMaxPostSize(), constants.getEncoding());
        return uploadFiles.size() > 0 ? uploadFiles.get(0) : null;
    }

    public UploadFile getFile(String parameterName, ProgressBarObserver observer) {
        HttpServletRequestProxy uploadRequest = new HttpServletRequestProxy(getRequest(), observer);
        Constants constants = JFinal.me().getConstants();
//        System.out.println(constants.getUploadedFileSaveDirectory());
        System.out.println(constants.getBaseUploadPath());
        List<UploadFile> uploadFiles = wrapMultipartRequest(uploadRequest, constants.getBaseUploadPath(),
                constants.getMaxPostSize(), constants.getEncoding());
        for (UploadFile uploadFile : uploadFiles) {
            if (uploadFile.getParameterName().equals(parameterName)) {
                return uploadFile;
            }
        }
        return null;
    }

    /**以下部分代码剥离自原装的MultipartRequest**/

    /**
     * 添加对相对路径的支持
     * 1: 以 "/" 开头或者以 "x:开头的目录被认为是绝对路径
     * 2: 其它路径被认为是相对路径, 需要 JFinalConfig.uploadedFileSaveDirectory 结合
     */
    private String handleSaveDirectory(String saveDirectory) {
        if (saveDirectory.startsWith("/") || saveDirectory.indexOf(":") == 1)
            return saveDirectory;
        else {
            //这个地方有修改
            return PathKit.getWebRootPath() + "/" + saveDirectory;
        }
    }

    @SuppressWarnings("rawtypes")
    private List<UploadFile> wrapMultipartRequest(HttpServletRequest request, String saveDirectory, int maxPostSize, String encoding) {
        saveDirectory = handleSaveDirectory(saveDirectory);

        File dir = new File(saveDirectory);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new RuntimeException("Directory " + saveDirectory + " not exists and can not create directory.");
            }
        }

//		String content_type = request.getContentType();
//        if (content_type == null || content_type.indexOf("multipart/form-data") == -1) {
//        	throw new RuntimeException("Not multipart request, enctype=\"multipart/form-data\" is not found of form.");
//        }

        List<UploadFile> uploadFiles = new ArrayList<UploadFile>();

        try {
            multipartRequest = new com.oreilly.servlet.MultipartRequest(request, saveDirectory, maxPostSize, encoding, fileRenamePolicy);
            Enumeration files = multipartRequest.getFileNames();
            while (files.hasMoreElements()) {
                String name = (String) files.nextElement();
                String filesystemName = multipartRequest.getFilesystemName(name);

                // 文件没有上传则不生成 UploadFile, 这与 cos的解决方案不一样
                if (filesystemName != null) {
                    String originalFileName = multipartRequest.getOriginalFileName(name);
                    String contentType = multipartRequest.getContentType(name);
                    UploadFile uploadFile = new UploadFile(name, saveDirectory, filesystemName, originalFileName, contentType);
                    if (isSafeFile(uploadFile))
                        uploadFiles.add(uploadFile);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return uploadFiles;
    }

    private boolean isSafeFile(UploadFile uploadFile) {
        String fileName = uploadFile.getFileName().trim().toLowerCase();
        if (fileName.endsWith(".jsp") || fileName.endsWith(".jspx")) {
            uploadFile.getFile().delete();
            return false;
        }
        return true;
    }

    @Override
    public String getPara(String name) {
        if (multipartRequest == null) {
            return super.getPara(name);
        }
        return multipartRequest.getParameter(name);
    }

    /*other getPara overrides*/
}
