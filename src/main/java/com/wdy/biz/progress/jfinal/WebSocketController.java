package com.wdy.biz.progress.jfinal;

import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;

import java.io.File;


/**
 * @author wgch
 * @Description 进入webSocket页面
 * @date 2019/4/24 11:53
 */
public class WebSocketController extends Controller {

    public void index() {
        render("/ws/webSocket.html");
    }

    public void upload() {
        UploadFile uploadFile = getFile();
        File file = uploadFile.getFile();
        long length = file.length();
        // 1、将文件进度存入缓存
        // 2、通过ws访问直接取缓存值
        int contentLength = getRequest().getContentLength();
        System.out.println(contentLength);

        renderText(String.valueOf(length));
    }

}
