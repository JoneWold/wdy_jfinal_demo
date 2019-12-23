package com.wdy.biz.file.rmb.controller;

import com.jfinal.aop.Aop;
import com.jfinal.core.Controller;
import com.wdy.biz.file.rmb.service.ExportRmbService;
import com.wdy.dto.ExportRmbDto;
import com.wdy.message.InMessage;
import com.wdy.message.OutMessage;

/**
 * @author wgch
 * @Description 导出任免表
 * @date 2019/12/17
 */
public class ExportRmbController extends Controller {

    private ExportRmbService service = Aop.get(ExportRmbService.class);

    /***
     * 导出任免表 .lrm
     * @param inMessage 导出人员标识符
     * @return 文件下载路径
     */
    public OutMessage exportLrm(InMessage<ExportRmbDto> inMessage) throws Exception {
        ExportRmbDto data = inMessage.getData();
        return service.exportLrm(data.getA0000s());
    }

    /***
     * 导出任免表 .lrmx
     * @param inMessage 导出人员标识符
     * @return 文件下载路径
     */
    public OutMessage exportLrmx(InMessage<ExportRmbDto> inMessage) {
        ExportRmbDto data = inMessage.getData();
        return service.exportLrmx(data.getA0000s());
    }


    /***
     * 导出任免表 .word
     * @param inMessage 导出人员标识符
     * @return 文件下载路径
     */
    public OutMessage exportWord(InMessage<ExportRmbDto> inMessage) {
        ExportRmbDto data = inMessage.getData();
        return service.exportWord(data.getA0000s());
    }

    /**
     * 打印任免表
     */
    public OutMessage stampWord(InMessage<ExportRmbDto> inMessage) {
        ExportRmbDto data = inMessage.getData();
        return service.stampWord(data.getA0000s());
    }


}
