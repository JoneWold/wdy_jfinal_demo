package com.wdy.biz.progress;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.upload.UploadFile;
import com.wdy.interceptor.ParameterInterceptor;

import java.util.Observable;
import java.util.Observer;


/**
 * @author wgch
 * @Description
 * @date 2019/4/24 18:19
 */
@Before(ParameterInterceptor.class)
public class MobSdController extends ProgressBarController {

    @Clear
    public void _get_progressbar() {
        Object prc = getSessionAttr("progressbar");
        if (prc == null) {
            prc = 0;
        }
        renderJson(prc);
    }

    @SuppressWarnings("unchecked")
    public void upload() {
        removeSessionAttr("progressbar");
        ProgressBarObserver observer = new ProgressBarObserver(getRequest().getContentLength(), 0);

        observer.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                //这里处理进度变化的事情
                if (arg instanceof ProgressBarEntity) {
                    ProgressBarEntity bar = (ProgressBarEntity) arg;
                    setSessionAttr("progressbar", bar.getProgress());
                    System.out.println(bar.getTotalSize() + "\t" + bar.getUploadedSize() + "\t" + bar.getProgress());
                }
            }
        });

        UploadFile file = getFile("uploadFile", observer);

        //UploadFile file = getFile("uploadFile","",PropKit.getInt("fileMaxPostSize", 5242880));
        //TODO

        renderFile(file.getFile().getAbsolutePath());
    }

}
