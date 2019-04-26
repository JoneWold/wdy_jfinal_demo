package com.wdy.biz.progress.jfinal;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.upload.UploadFile;
import com.wdy.interceptor.ParameterInterceptor;

import java.util.Observable;
import java.util.Observer;


/**
 * @author wgch
 * @Description jFinal文件上传进度
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

        // ThreadLocal来存放结果
        ThreadLocal<Object> local = new ThreadLocal<>();

        removeSessionAttr("progressbar");
        ProgressBarObserver observer = new ProgressBarObserver(getRequest().getContentLength(), 0);

        observer.addObserver(new Observer() {

            @Override
            public void update(Observable o, Object arg) {
                //这里处理进度变化的事情
                if (arg instanceof ProgressBarEntity) {
                    ProgressBarEntity bar = (ProgressBarEntity) arg;
                    setSessionAttr("progressbar", bar.getProgress());
//                    System.out.println(bar.getTotalSize() + "\t" + bar.getUploadedSize() + "\t" + bar.getProgress());
                    // 进度比例
                    long totalSize = bar.getTotalSize();
                    int uploadedSize = bar.getUploadedSize();
                    float ratio = ((float) uploadedSize / totalSize);
//                    System.out.println(new DecimalFormat("0.00").format(ratio));
                    String result = String.format("%.2f", ratio);
                    // 匿名内部类的传值
                    local.set(result);
                }
            }
        });

//        UploadFile file = getFile("uploadFile", observer);
        UploadFile file = getFile(observer);

        //UploadFile file = getFile("uploadFile","",PropKit.getInt("fileMaxPostSize", 5242880));
        //TODO 接收类部类传值
        System.out.println(local.get());
        Object _ratio = local.get();
        local.remove();
        renderJson(_ratio);
    }

}
