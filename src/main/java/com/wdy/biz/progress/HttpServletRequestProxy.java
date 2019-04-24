package com.wdy.biz.progress;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

/**
 * @author wgch
 * @Description 代理HttpServletRequest
 * @date 2019/4/24 18:08
 */
public class HttpServletRequestProxy extends HttpServletRequestWrapper {
    private ProgressBarObserver observer;

    public HttpServletRequestProxy(HttpServletRequest request, ProgressBarObserver observer) {
        super(request);
        this.observer = observer;
    }
    /**
     * (non-Javadoc)
     *
     * @see javax.servlet.ServletRequestWrapper#getInputStream()
     */
    public ServletInputStream getInputStream() throws IOException {
        ServletInputStream in = super.getInputStream();
        return new ServletInputStreamProxy(in, observer);
    }

}
