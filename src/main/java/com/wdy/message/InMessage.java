package com.wdy.message;

/***
 * 请求消息
 * @author 毛文超
 * */

public class InMessage<T> {

    /**
     * 请求的数据
     */
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
