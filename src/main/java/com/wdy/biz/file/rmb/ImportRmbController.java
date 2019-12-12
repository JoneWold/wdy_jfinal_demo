package com.wdy.biz.file.rmb;

import com.jfinal.aop.Aop;
import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;
import com.wdy.message.OutMessage;

import java.util.List;

/**
 * @author wgch
 * @Description 导入任免表
 * @date 2019/12/12
 */
public class ImportRmbController extends Controller {
    private ReadRmbService rmbService = Aop.get(ReadRmbService.class);

    /**
     * 导入任免表
     */
    public OutMessage addAppointmemt(String impId) throws Exception {
        List<UploadFile> files = this.getFiles();
        return rmbService.importRmb(files, impId);
    }

}
