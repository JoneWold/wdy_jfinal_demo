package com.wdy.biz.excel.controller;

import cn.hutool.core.date.DateUtil;
import com.jfinal.kit.PathKit;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * @author wgch
 * @Description 合并单元格
 * @date 2019/4/30 10:08
 */
public class MergeExcelDemo {


    public static void main(String[] args) throws IOException {

        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        HSSFSheet sheet = workbook.createSheet("sheet");

        HSSFRow row0 = sheet.createRow(0);
        HSSFCell cell_00 = row0.createCell(0);
        cell_00.setCellStyle(style);
        cell_00.setCellValue("日期");
        HSSFCell cell_01 = row0.createCell(1);
        cell_01.setCellStyle(style);
        cell_01.setCellValue("午别");

        HSSFRow row1 = sheet.createRow(1);
        HSSFCell cell_10 = row1.createCell(0);
        cell_10.setCellStyle(style);
        cell_10.setCellValue(DateUtil.format(new Date(), "yyyyMMdd"));
        HSSFCell cell_11 = row1.createCell(1);
        cell_11.setCellStyle(style);
        cell_11.setCellValue("上午");

        HSSFRow row2 = sheet.createRow(2);
        HSSFCell cell_21 = row2.createCell(1);
        cell_21.setCellStyle(style);
        cell_21.setCellValue("下午");

        // 合并日期占两行(4个参数，分别为起始行，结束行，起始列，结束列)
        // 行和列都是从0开始计数，且起始结束都会合并
        // 这里是合并excel中日期的两行为一行
        CellRangeAddress region = new CellRangeAddress(1, 2, 0, 0);
        sheet.addMergedRegion(region);

        File file = new File(PathKit.getWebRootPath() + "/download/少时诵诗书.xls");
        FileOutputStream fos = new FileOutputStream(file);
        workbook.write(fos);
        fos.close();
    }

}