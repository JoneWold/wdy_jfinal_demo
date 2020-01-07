package com.wdy.biz.excel.controller;

import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.wdy.biz.excel.service.ExportExcelService;
import com.wdy.biz.excel.service.ExportMemUnitService;
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
    private ExportExcelService service;
    @Inject
    private ExportMemUnitService memUnitService;

    /**
     * 年龄统计导出
     *
     * @param orgCode 机构层级码
     * @param a0165   管理类别
     * @param a0221   职务层次
     * @return
     */
    public OutMessage exportAge(String orgCode, String a0165, String a0221) throws IOException {
        if (StrKit.isBlank(orgCode) || StrKit.isBlank(a0221)) {
            return new OutMessage(Status.PARA_ERROR);
        }
        return service.exportAge(orgCode, a0165, a0221);
    }

    /**
     * 事业单位领导人员信息表导出
     *
     * @param orgId   事业单位id
     * @param orgCode 事业单位编码
     */
    public OutMessage exportMemUnit(Long orgId, String orgCode) throws Exception {
        return memUnitService.exportMemUnit(orgId, orgCode);
    }


}
