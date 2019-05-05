package com.wdy.bizz.excel;

import cn.hutool.core.collection.CollectionUtil;
import com.jfinal.plugin.activerecord.Record;
import com.wdy.biz.dictionary.service.DictionaryService;
import com.wdy.biz.excel.service.ExportExcelService;
import com.wdy.bizz.TestWdyConfig;
import com.wdy.message.OutMessage;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.wdy.config.constant.CommonConstant.FONE;
import static com.wdy.config.constant.CommonConstant.ONE;

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

    /**
     * 测试职务层次字典表
     */
    @Test
    public void testZb09() {
        DictionaryService dictionaryService = new DictionaryService();
        // 父级code集合
        List<String> A0221List = new ArrayList<>();
        // 父级名称 集合
        List<String> nameList = new ArrayList<>();
        List<Record> zb091 = dictionaryService.getDictionaryList("ZB09");
        for (Record record : zb091) {
            String sub_code_value = record.getStr("SUB_CODE_VALUE");
            String code_value = record.getStr("CODE_VALUE");
            String code_name = record.getStr("CODE_NAME");
            if (!sub_code_value.equals(ONE) && !sub_code_value.equals(FONE)
                    && !CollectionUtil.contains(A0221List, sub_code_value)) {
                A0221List.add(sub_code_value);
            }
            if ((sub_code_value.equals(ONE) || sub_code_value.equals(FONE) || sub_code_value.equals("7")) && !code_value.equals(ONE)) {
                nameList.add(code_name);
            }
        }
        System.out.println(A0221List);
        System.out.println(nameList);
    }

}
