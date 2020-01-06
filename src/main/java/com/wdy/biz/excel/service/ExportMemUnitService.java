package com.wdy.biz.excel.service;

import com.jfinal.aop.Aop;
import com.wdy.biz.excel.dao.ExportMemUnitDao;
import com.wdy.generator.postgreSQL.model.MemUnitInstitution;
import com.wdy.generator.postgreSQL.model.UnitInstitution;
import com.wdy.generator.postgreSQL.model.base.BaseMemUnitInstitution;
import com.wdy.message.OutMessage;
import com.wdy.message.Status;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wgch
 * @Description 导出Excel
 * @date 2020/1/6 17:01
 */
public class ExportMemUnitService {

    private ExportMemUnitDao memUnitDao = Aop.get(ExportMemUnitDao.class);

    public OutMessage exportMemUnit(Long orgId, String orgCode) {
        List<MemUnitInstitution> unitMemList = memUnitDao.findUnitMemList(orgId, orgCode);
        List<Long> unitIdList = unitMemList.stream().map(BaseMemUnitInstitution::getCareerUnitId).collect(Collectors.toList());
        List<UnitInstitution> unitList = memUnitDao.findUnitList(unitIdList);

        List<File> fileList = new ArrayList<>();
        for (UnitInstitution unit : unitList) {
            List<MemUnitInstitution> memList = unitMemList.stream()
                    .filter(memUnitInstitution -> memUnitInstitution.getCareerUnitId().equals(unit.getId())).collect(Collectors.toList());

        }

        return new OutMessage(Status.SUCCESS);
    }


}
