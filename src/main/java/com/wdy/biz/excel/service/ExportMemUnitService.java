package com.wdy.biz.excel.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
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
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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

    }

    /**
     * 单位列表
     */
    private void setUnitList(XSSFWorkbook wb, UnitInstitution unit) {
        XSSFSheet sheet = wb.getSheetAt(1);


    }

    /**
     * 领导人员情况
     */
    private void setUnitLDRYQKMemInfo(XSSFWorkbook wb, List<MemUnitInstitution> memList) {
        XSSFSheet sheet = wb.getSheetAt(2);

    }

    /**
     * 变化情况
     */
    private void setUnitBHQKMemInfo(XSSFWorkbook wb, List<MemUnitInstitution> memList) {
        XSSFSheet sheet = wb.getSheetAt(3);

    }

    /**
     * 处分情况
     */
    private void setUnitCFQKemInfo(XSSFWorkbook wb, List<MemUnitInstitution> memList) {
        XSSFSheet sheet = wb.getSheetAt(4);

    }


}
