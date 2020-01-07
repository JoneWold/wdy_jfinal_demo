package com.wdy.biz.excel.dao;

import cn.hutool.core.util.ObjectUtil;
import com.jfinal.aop.Aop;
import com.jfinal.kit.StrKit;
import com.wdy.generator.postgreSQL.model.MemUnitInstitution;
import com.wdy.generator.postgreSQL.model.UnitInstitution;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.SelectOnConditionStep;

import java.util.HashSet;
import java.util.List;

import static com.wdy.constant.CommonConstant.*;
import static org.jooq.impl.DSL.*;

/**
 * @author wgch
 * @Description 事业单位相关dao
 * @date 2020/1/6 17:26
 */
public class ExportMemUnitDao {

    private UnitInstitution unitInstitution = Aop.get(UnitInstitution.class);
    private MemUnitInstitution memUnitInstitution = Aop.get(MemUnitInstitution.class);


    /**
     * 事业单位人员列表
     */
    public List<MemUnitInstitution> findUnitMemList(Long orgId, String orgCode) {
        SelectOnConditionStep select = DSL_CONTEXT.select(field("\"m\".*")).from(table(name("mem_unit_institution")).as("m"))
                .innerJoin(table(name("unit_institution")).as("u")).on(field(name("m", "career_unit_id")).eq(field(name("u", "id"))));
        Condition condition = this.setMemCondition(noCondition(), orgId, null, orgCode);
        select.where(condition).orderBy(field(name("id")).desc());
        return memUnitInstitution.find(select.getSQL(), select.getBindValues().toArray());
    }

    /**
     * 事业单位集合
     */
    public List<UnitInstitution> findUnitList(HashSet<Long> unitIdList) {
        SelectConditionStep<Record> sqlStr = DSL_CONTEXT.select()
                .from(table(name("unit_institution")))
                .where(field(name("id"), Long.class).in(unitIdList));
        return unitInstitution.find(sqlStr.getSQL(), sqlStr.getBindValues().toArray());
    }


    private Condition setMemCondition(Condition condition, Long orgId, String countLevel, String orgCode) {
        if (ObjectUtil.isNotNull(orgId)) {
            if (ZERO_LONG.equals(orgId)) {
                return condition;
            } else if (FU_ONE_LONG.equals(orgId)) {
                condition = condition.and(" 1=1 ");
            } else {
                condition = condition.and(field(name("m", "career_unit_id")).eq(orgId));
            }
        }
        if (StrKit.notBlank(orgCode)) {
            condition = condition.and(field(name("u", "org_code")).eq(orgCode));
        }
        if (StrKit.notBlank(countLevel)) {
            condition = condition.and(field(name("m", "unit_count_level")).eq(countLevel));
        }
        return condition;
    }


}
