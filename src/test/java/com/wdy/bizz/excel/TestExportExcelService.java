package com.wdy.bizz.excel;

import com.wdy.biz.excel.service.ExportExcelService;
import com.wdy.bizz.TestWdyConfig;
import com.wdy.message.OutMessage;
import org.junit.Test;

import java.io.IOException;

/**
 * @author wgch
 * @Description
 * @date 2019/4/30 10:44
 */
public class TestExportExcelService extends TestWdyConfig {

    /**
     * 测试年龄统计导出
     */
    @Test
    public void testExportAge() throws IOException {
        ExportExcelService excelService = new ExportExcelService();
        OutMessage message = excelService.exportAge("001.001", "01,02,03,04,09", "1A,1B");
        System.out.println(message);
    }


}
