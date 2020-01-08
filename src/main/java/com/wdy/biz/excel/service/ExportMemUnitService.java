package com.wdy.biz.excel.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.jfinal.aop.Aop;
import com.jfinal.kit.PathKit;
import com.wdy.biz.excel.dao.ExportMemUnitDao;
import com.wdy.generator.postgreSQL.model.MemUnitInstitution;
import com.wdy.generator.postgreSQL.model.UnitInstitution;
import com.wdy.generator.postgreSQL.model.base.BaseMemUnitInstitution;
import com.wdy.message.OutMessage;
import com.wdy.message.Status;
import com.wdy.utils.XmlZipFileUtil;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author wgch
 * @Description 导出Excel
 * @date 2020/1/6 17:01
 */
public class ExportMemUnitService {

    private ExportMemUnitDao memUnitDao = Aop.get(ExportMemUnitDao.class);

    public OutMessage exportMemUnit(Long orgId, String orgCode) throws Exception {
        List<MemUnitInstitution> unitMemList = memUnitDao.findUnitMemList(orgId, orgCode);
        HashSet<Long> unitIdList = unitMemList.stream().map(BaseMemUnitInstitution::getCareerUnitId).collect(Collectors.toCollection(HashSet::new));
        List<UnitInstitution> unitList = memUnitDao.findUnitList(unitIdList);

        List<File> fileList = new ArrayList<>();
        for (UnitInstitution unit : unitList) {
            List<MemUnitInstitution> memList = unitMemList.stream()
                    .filter(memUnitInstitution -> memUnitInstitution.getCareerUnitId().equals(unit.getId())).collect(Collectors.toList());

            String fileName = unit.getName() + "事业单位领导人员信息表";
            fileList.add(this.exportUnitMemFile(fileName, unit, memList));
        }
        String toPath = "/upload/" + "事业单位领导人员" + DateUtil.format(new Date(), "yyyyMMddHHmmssSSS") + ".zip";
        XmlZipFileUtil.zipFile(fileList, PathKit.getWebRootPath() + toPath);
        fileList.forEach(File::delete);

        return new OutMessage<>(Status.SUCCESS, toPath);
    }

    /**
     * 导出Excel文件
     */
    private File exportUnitMemFile(String fileName, UnitInstitution unit, List<MemUnitInstitution> memList) throws Exception {
        // 读取Excel模板
        File ftlFile = FileUtil.file("D:\\wdy\\wdy_jfinal_demo\\src\\main\\resources\\template\\memUnitInstitutionFtl.xlsx");
        FileInputStream inputStream = new FileInputStream(ftlFile);
        // 延迟解析比率
        ZipSecureFile.setMinInflateRatio(-1.0d);
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);

        // 单位信息
        this.setUnitInfo(wb, unit);
        // 单位列表
        this.setUnitList(wb, unit);
        // 在职情况
        this.setUnitLDRYQKMemInfo(wb, memList);
        // 变化情况
        this.setUnitBHQKMemInfo(wb, memList);
        // 处分情况
        this.setUnitCFQKemInfo(wb, memList);

        // 生成文件
        String toPath = PathKit.getWebRootPath() + "/upload/" + fileName + DateUtil.format(new Date(), "yyyyMMddHHmmssSSS") + ".xlsx";
        File toFile = FileUtil.file(toPath);
        File parentFile = toFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(toFile);
        wb.write(out);
        wb.close();
        out.close();
        return toFile;
    }

    /**
     * 单位信息
     */
    private void setUnitInfo(XSSFWorkbook wb, UnitInstitution unit) {
        // 获取第一个sheet页
        XSSFSheet orgSheet = wb.getSheetAt(0);
        orgSheet.getRow(3).getCell(6).setCellValue(unit.getUnitAttributesName());
        orgSheet.getRow(4).getCell(6).setCellValue(unit.getUnitCountLevelName());
        orgSheet.getRow(5).getCell(6).setCellValue(unit.getUnitTypeName());
        orgSheet.getRow(6).getCell(6).setCellValue(unit.getContact());
        orgSheet.getRow(7).getCell(6).setCellValue(unit.getPhone());
    }

    /**
     * 单位列表
     */
    private void setUnitList(XSSFWorkbook wb, UnitInstitution unit) {
        XSSFSheet sheet = wb.getSheetAt(1);
        Row row = this.getRow(sheet, 2);
        this.setCell(row, 0, unit.getName());
        this.setCell(row, 1, unit.getTermInstitutionName());
        this.setCell(row, 2, unit.getTermTargetInstitutionName());
        this.setCell(row, 3, unit.getPreYearAssessName());
        this.setCell(row, 4, unit.getInYearAssessName());
        this.setCell(row, 5, String.valueOf(unit.getPreMainLeader()));
        this.setCell(row, 6, String.valueOf(unit.getPreMinorLeader()));
        this.setCell(row, 7, String.valueOf(unit.getPreOtherLeader()));
        this.setCell(row, 8, String.valueOf(unit.getMainLeader()));
        this.setCell(row, 9, String.valueOf(unit.getMinorLeader()));
        this.setCell(row, 10, String.valueOf(unit.getOtherLeader()));

    }

    /**
     * 领导人员情况
     */
    private void setUnitLDRYQKMemInfo(XSSFWorkbook wb, List<MemUnitInstitution> memList) {
        XSSFSheet sheet = wb.getSheetAt(2);
        int rowIndex = 2;
        for (MemUnitInstitution mem : memList) {
            Row row = this.getRow(sheet, rowIndex);
            List<String> cellValueList = this.getLDRYQKCellValueList(mem);
            int cellIndex = 0;
            for (String value : cellValueList) {
                this.setCell(row, cellIndex, value);
                cellIndex++;
            }
            rowIndex++;
        }

    }

    /**
     * 变化情况
     */
    private void setUnitBHQKMemInfo(XSSFWorkbook wb, List<MemUnitInstitution> memList) {
        XSSFSheet sheet = wb.getSheetAt(3);
        this.setCellValueFunc(sheet, memList, mem -> {
            List<String> list = new ArrayList<>();
            list.add(mem.getName());
            list.add(mem.getIdNum());
            list.add(mem.getCurJob());
            list.add(mem.getJobTypeName());
            Integer changeStatus = mem.getChangeStatus();
            list.add(changeStatus != null ? (Objects.equals(changeStatus, 1) ? "增加" : "减少") : "");
            if (changeStatus == null) {
                // 减少人员
                list.add(null);
            } else {
                list.add(mem.getSourceJobName());
            }
            list.add(this.getChineseStr(mem.getIsPromoted()));
            list.add(this.getChineseStr(mem.getIsLeapfrogPromoted()));
            if (changeStatus != null && changeStatus == 2) {
                list.add(mem.getReduceMemTypeName());
                list.add(mem.getReduceJobGrowingName());
                list.add(mem.getReduceDeposeName());
                list.add(mem.getReduceResignName());
            }
            return list;
        });

    }

    /**
     * 处分情况
     */
    private void setUnitCFQKemInfo(XSSFWorkbook wb, List<MemUnitInstitution> memList) {
        List<MemUnitInstitution> collect = memList.stream()
                .filter(memUnitInstitution -> memUnitInstitution.getSelectPunish() != null && memUnitInstitution.getSelectPunish() == 1)
                .collect(Collectors.toList());
        XSSFSheet sheet = wb.getSheetAt(4);
        this.setCellValueFunc(sheet, collect, mem -> {
            List<String> list = new ArrayList<>();
            list.add(mem.getName());
            list.add(mem.getIdNum());
            list.add(mem.getPunishJob());
            list.add(this.getPunishJobTypeStr(mem.getPunishJobType()));
            list.add(mem.getPunishCpcDisciplineName());
            list.add(mem.getPunishPoliticsDisciplineName());
            list.add(mem.getPunishPenalDisciplineName());
            return list;
        });
    }


    /***
     * 获取第几行
     * @param sheet 单元格
     * @param index 行数
     */
    public Row getRow(Sheet sheet, Integer index) {
        Row row = sheet.getRow(index);
        if (row == null) {
            row = sheet.createRow(index);
        }
        return row;
    }

    /***
     * 设置单元格值
     * @param row   行
     * @param index 列号
     * @param value 数据
     */
    private synchronized void setCell(Row row, Integer index, String value) {
        Cell cell = row.getCell(index);
        if (cell == null) {
            cell = row.getCell(index, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        }

        Workbook workbook = row.getSheet().getWorkbook();
        // Excel 单元格格式
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        DataFormat dataFormat = workbook.createDataFormat();
        cellStyle.setDataFormat(dataFormat.getFormat("@"));

        cell.setCellStyle(cellStyle);
        cell.setCellValue(value);
    }

    /**
     * 数据填充 函数
     *
     * @param sheet    Excel工作表对象
     * @param memList  事业单位人员数据
     * @param function 传入事业单位人员数据，获取每一行所有列的值
     */
    private void setCellValueFunc(XSSFSheet sheet, List<MemUnitInstitution> memList, Function<MemUnitInstitution, List<String>> function) {
        int rowIndex = 2;
        for (MemUnitInstitution mem : memList) {
            Row row = this.getRow(sheet, rowIndex);
            // 执行这个函数
            List<String> apply = function.apply(mem);
            int cellIndex = 0;
            for (String value : apply) {
                this.setCell(row, cellIndex, value);
                cellIndex++;
            }
            rowIndex++;
        }
    }


    /***
     * 获取领导人员情况每一行所有列的值
     * @param mem 事业单位人员数据
     * @return list 每一行所有列的值
     */
    private List<String> getLDRYQKCellValueList(MemUnitInstitution mem) {
        List<String> list = new ArrayList<>();
        list.add(mem.getName());
        list.add(mem.getIdNum());
        list.add(mem.getSex() != null ? (Objects.equals(String.valueOf(mem.getSex()), "1") ? "男" : "女") : null);
        list.add(mem.getEthnic() != null ? (StrUtil.equalsAny(mem.getEthnic(), "01") ? "汉族" : "少数民族") : null);
        list.add(mem.getBirthdate() == null ? "" : DateUtil.format(mem.getBirthdate(), "yyyyMMdd"));
        list.add(mem.getPoliticCountenance());
        list.add(mem.getEducationFullTimeName());
        list.add(mem.getEducationName());
        list.add(mem.getEducationDegreeName());
        list.add(mem.getCurJob());
        list.add(mem.getJobTypeName());
        list.add(mem.getManagerJobLevelName());
        list.add(mem.getSpecialtyJobLevelName());
        list.add(mem.getSourceJobName());
        list.add(mem.getSelectTypeName());
        list.add(mem.getAppointTypeName());
        list.add(this.getChineseStr(mem.getIsPromoted()));
        list.add(this.getChineseStr(mem.getIsLeapfrogPromoted()));
        list.add(mem.getTermTargetInstitutionName());
        list.add(mem.getPreYearAssessName());
        list.add(mem.getInYearAssessYear());
        return list;
    }

    /**
     * 获取是否的中文
     */
    private String getChineseStr(Integer flag) {
        if (flag == null) {
            return "否";
        } else if (Integer.valueOf(1).equals(flag)) {
            return "是";
        } else if (Integer.valueOf(0).equals(flag)) {
            return "否";
        }
        return "否";
    }

    /**
     * 事业单位处分时职务类型
     */
    private String getPunishJobTypeStr(String punishJobType) {
        if (punishJobType == null) {
            return null;
        } else if ("1".equals(punishJobType)) {
            return "正职";
        } else if ("2".equals(punishJobType)) {
            return "副职";
        } else if ("3".equals(punishJobType)) {
            return "其他";
        } else {
            return null;
        }
    }

}
