package com.wdy.message;

import lombok.ToString;

/**
 *
 * @author 毛文超
 * @param <T>
 */
@ToString
public class OutMessage<T> {
    /***
     * 状态码
     * */
    private int code;
    /***
     * 状态消息
     * */
    private String message;
    /***
     * 返回数据
     * */
    private T data;

    public OutMessage() {

    }


    public OutMessage(Status status) {

        this.code = status.getCode();
        this.message = status.getMessage();
    }

    public OutMessage(Status status,T data){

        this.code = status.getCode();
        this.message = status.getMessage();
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }

}
