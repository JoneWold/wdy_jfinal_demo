package com.wdy.biz.progress;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.IOException;

/**
 * @author wgch
 * @Description servlet底层输入流的代理
 * @date 2019/4/24 18:04
 */
public class ServletInputStreamProxy extends ServletInputStream {

    ServletInputStream in;
    ProgressBarObserver observer;

    public ServletInputStreamProxy(ServletInputStream in, ProgressBarObserver observer) {
        this.in = in;
        this.observer = observer;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int r = in.read(b, off, len);
        if (r != -1) {
            observer.incomingContent(r);
        }
        return r;
    }


    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void setReadListener(ReadListener readListener) {

    }

    @Override
    public int read() throws IOException {
        return 0;
    }
}
