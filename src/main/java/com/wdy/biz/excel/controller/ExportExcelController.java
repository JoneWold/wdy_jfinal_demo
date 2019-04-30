package com.wdy.biz.excel.controller;

import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.wdy.biz.excel.service.ExportExcelService;
import com.wdy.message.OutMessage;
import com.wdy.message.Status;

import java.io.IOException;

/**
 * @author wgch
 * @Description 综合统计导出
 * @date 2019/4/26 10:13
 */
public class ExportExcelController extends Controller {

    @Inject
    public ExportExcelService excelService;

    /**
     * 年龄统计导出
     *
     * @param orgCode
     * @param a0165
     * @param a0221
     * @return
     */
    public OutMessage exportAge(String orgCode, String a0165, String a0221) throws IOException {
        if (StrKit.isBlank(orgCode) || StrKit.isBlank(a0221)) {
            return new OutMessage(Status.PARA_ERROR);
        }
        return excelService.exportAge(orgCode, a0165, a0221);
    }


}
