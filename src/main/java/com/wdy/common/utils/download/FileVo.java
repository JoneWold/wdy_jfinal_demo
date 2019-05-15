package com.wdy.common.utils.download;

/**
 * @author wgch
 * @Description
 * @date 2019/5/15 16:47
 */
public class FileVo {

    private byte[] data;

    private String fileName;

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
