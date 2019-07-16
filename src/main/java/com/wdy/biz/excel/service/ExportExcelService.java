package com.wdy.biz.excel.service;

import cn.hutool.core.util.ObjectUtil;
import com.jfinal.aop.Aop;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Record;
import com.wdy.biz.excel.dao.ExportExcelDao;
import com.wdy.biz.excel.dto.SummaryDto;
import com.wdy.biz.excel.dto.SummaryService;
import com.wdy.message.OutMessage;
import com.wdy.message.Status;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wgch
 * @Description
 * @date 2019/4/26 10:13
 */
public class ExportExcelService {

    private SummaryService summaryService = Aop.get(SummaryService.class);
    private ExportExcelDao exportExcelDao = Aop.get(ExportExcelDao.class);

    /**
     * 年龄统计导出
     *
     * @param orgCode
     * @param a0165
     * @param a0221
     * @return
     */
    public OutMessage exportAge(String orgCode, String a0165, String a0221) throws IOException {
        SummaryDto summaryDto = new SummaryDto();
        summaryDto.setOrgCode(orgCode);
        summaryDto.setA0221(a0221);
        summaryDto.setA0165(a0165);
        // 职务层次
        ExportExcelDao.ZwcjDto zwcjData = exportExcelDao.getZwcjData(a0221);
        // 表格数据集
        List<List<Map>> data = summaryService.summaryAge(summaryDto).getData();


        // 读取模板文件
        File file = new File(PathKit.getWebRootPath() + "/src/main/resources/template/AgeTemplate.xls");
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
        // 读取excel模板
        HSSFWorkbook workBook = new HSSFWorkbook(fs);
        // 获取第一个sheet页
        HSSFSheet sheet = workBook.getSheetAt(0);
//        HSSFSheet sheet = workBook.cloneSheet(0);
        sheet.setForceFormulaRecalculation(true);

        // 存储表头及其样式
        CellStyle[] cellStyles = new CellStyle[3];
        // 获取表格模板样式
        HSSFRow row = sheet.getRow(3);
        // 带颜色的单元格样式
        cellStyles[0] = row.getCell(0).getCellStyle();
        // 不带颜色的单元格样式
        cellStyles[1] = row.getCell(2).getCellStyle();

        // 组合字典表数据
        List<Map<String, List<String>>> A0221List = this.getA0221List(zwcjData.getParent(), zwcjData.getSon());

        // 构造带有字典表的Excel模板
        this.writerExcel(sheet, cellStyles, A0221List);

        // 在相应的单元格进行赋值
        int rowNum = 3;
        for (List<Map> dataList : data) {
            // 获取行
            HSSFRow sheetRow = ObjectUtil.isNull(sheet.getRow(rowNum)) ? sheet.createRow(rowNum) : sheet.getRow(rowNum);
            int cellNum = 2;
            for (Map map : dataList) {
                // 获取列
                HSSFCell rowCell = ObjectUtil.isNull(sheetRow.getCell(cellNum)) ? sheetRow.createCell(cellNum) : sheetRow.getCell(cellNum);
                rowCell.setCellStyle(cellStyles[1]);
                rowCell.setCellValue((String) map.get("value"));
                cellNum++;
            }
            rowNum++;
        }

        // 修改模板内容导出新模板
        String fileName = PathKit.getWebRootPath() + "/download/年龄统计表" + System.currentTimeMillis() + ".xls";
        File newFile = new File(fileName);
        FileOutputStream out = new FileOutputStream(newFile);
        // 将文件以流的形式输出
        workBook.write(out);
        out.close();
        return new OutMessage<>(Status.SUCCESS, newFile.getPath());
    }

    /**
     * 获取Excel中字典表数据
     *
     * @param parentList
     * @param sonList
     * @return
     */
    public List<Map<String, List<String>>> getA0221List(List<Record> parentList, List<Record> sonList) {
        // 结果集 对应Excel模板中的前两列数据
        List<Map<String, List<String>>> resList = new ArrayList<>();
        for (Record parent : parentList) {
            String parentCode = parent.getStr("CODE_VALUE");
            Map<String, List<String>> map = new LinkedHashMap<>();
            int i = 0;
            for (Record son : sonList) {
                String sonCode = son.getStr("CODE_VALUE");
                if (sonCode.startsWith(parentCode)) {
                    if (parentCode.equals(sonCode)) {
                        // 添加当前map的key
                        List<String> sonSonList = new ArrayList<>();
                        sonSonList.add("小计");
                        map.put(parent.getStr("CODE_NAME"), sonSonList);
                    } else if (i == 0) {

                        i++;
                    } else {
                        // 获取当前map的value
                        List<String> value = map.entrySet().iterator().next().getValue();
                        value.add(son.getStr("CODE_NAME"));
                    }
                }
            }
            resList.add(map);
        }
        return resList;
    }


    /**
     * 将字典表数据写入到Excel
     *
     * @param sheet      当前Excel模板
     * @param cellStyles 模板中带有的样式
     * @param A0221List  模板中需要填充的字典表数据
     */
    public void writerExcel(HSSFSheet sheet, CellStyle[] cellStyles, List<Map<String, List<String>>> A0221List) {
        for (Map<String, List<String>> map : A0221List) {
            Map.Entry<String, List<String>> entry = map.entrySet().iterator().next();
            String key = entry.getKey();
            List<String> values = entry.getValue();
            int startRow = 0;
            int endRow = 0;
            for (int i = 0; i < values.size(); i++) {
                // 每一次内层循环 就新增一行
                HSSFRow sheetRow = sheet.createRow(sheet.getLastRowNum() + 1);
                // 第一行数据填充
                if (i == 0) {
                    startRow = sheetRow.getRowNum();
                    HSSFCell cell = sheetRow.createCell(0);
                    cell.setCellStyle(cellStyles[0]);
                    cell.setCellValue(key);
                    HSSFCell cell1 = sheetRow.createCell(1);
                    cell1.setCellStyle(cellStyles[0]);
                    cell1.setCellValue(values.get(i));
                    // 最后一行数据填充
                } else if (i == values.size() - 1) {
                    HSSFCell cell = sheetRow.createCell(1);
                    cell.setCellStyle(cellStyles[0]);
                    cell.setCellValue(values.get(i));
                    endRow = sheetRow.getRowNum();
                    // 中间行数据填充
                } else {
                    HSSFCell cell = sheetRow.createCell(1);
                    cell.setCellStyle(cellStyles[0]);
                    cell.setCellValue(values.get(i));
                }
            }
            // 合并单元格
            CellRangeAddress region = new CellRangeAddress(startRow, endRow, 0, 0);
            sheet.addMergedRegion(region);
        }
    }

    /**
     * 删除行
     *
     * @param sheet
     * @param startRow 起始行
     * @param endRow   结束行
     */
    private void deleteRow(Sheet sheet, int startRow, int endRow) {
        for (int i = startRow; i < startRow + endRow - 1; i++) {
            sheet.removeRow(sheet.getRow(i));
        }
        int lastRowNum = sheet.getLastRowNum();
        // 将余下的行向上移
        sheet.shiftRows(endRow, lastRowNum, (startRow - endRow));
    }


}
