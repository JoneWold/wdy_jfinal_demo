package com.wdy.biz.progress.jfinal;

import com.jfinal.core.Controller;

/**
 * @author wgch
 * @Description 进入webSocket页面
 * @date 2019/4/24 11:53
 */
public class InterWsController extends Controller {

    public void index() {
        render("/ws/webSocket.html");
    }

}
