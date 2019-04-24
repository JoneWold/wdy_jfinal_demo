package com.wdy.biz.progress;

import java.util.Observable;

/**
 * @author wgch
 * @Description 进度条组件被观察者
 * @date 2019/4/24 18:06
 */
public class ProgressBarObserver extends Observable {
    private int uploadedSize = 0;
    private ProgressBarEntity bar;

    public ProgressBarObserver(long totalSize, int uploadedSize) {
        super();
        this.uploadedSize = uploadedSize;
        bar = new ProgressBarEntity(totalSize, uploadedSize);
    }

    public void incomingContent(int readSize) {
        uploadedSize += readSize;
        bar.setUploadedSize(uploadedSize);
        setChanged();
        notifyObservers(bar);
    }

    public int getUploadedSize() {
        return uploadedSize;
    }

    public void setUploadedSize(int uploadedSize) {
        this.uploadedSize = uploadedSize;
    }
}
