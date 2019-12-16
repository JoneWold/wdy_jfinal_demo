package com.wdy.biz.mem.dao;

import com.jfinal.aop.Aop;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wdy.generator.postgreSQL.model.A01;
import org.jooq.SelectConditionStep;
import org.jooq.SelectSelectStep;
import org.jooq.UpdateConditionStep;
import org.jooq.impl.DSL;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.wdy.constant.CommonConstant.DSL_CONTEXT;
import static com.wdy.constant.DBConstant.DB_PGSQL;
import static org.jooq.impl.DSL.*;

/**
 * @author wgch
 * @Description 人员管理dao
 * @date 2019/12/16
 */
public class MemDao {
    private A01 a01Me = Aop.get(A01.class);

    public A01 findA01ById(String a0000) {
        return a01Me.findById(a0000);
    }

    public Map<String, List<Record>> findA08ByA0000s(List<String> a0000s, String impId) {
        SelectSelectStep select = DSL_CONTEXT.select(
                field(name("A0000"))
                , field(name("A0898"))
                , field(name("A0804"))
                , field(name("A0807"))
                , field(name("A0801A"))
                , field(name("A0801B"))
                , field(name("A0901B"))
                , field(name("A0901A"))
                , field(name("A0814"))
                , field(name("A0824"))
                , field(name("A0800"))
                , field(name("A0837")));
        SelectConditionStep condi = null;
        if (StrKit.isBlank(impId)) {
            condi = select.from(DSL.table(name("a08")))
                    .where(noCondition());
        } else {
            condi = select.from(DSL.table(name("a08_temp")))
                    .where(field(name("impId")).eq(impId));
        }
        if (a0000s.size() > 0) {
            condi = condi.and(field(name("A0000")).in(a0000s));
        }
        List<Record> recordList = Db.use(DB_PGSQL).find(condi.getSQL(), condi.getBindValues().toArray());
        return recordList.stream().collect(Collectors.groupingBy(e -> e.getStr("A0000")));
    }


    /**
     * 重置最高学历学位标识(多人)
     *
     * @param a0000s
     * @return
     */
    public int updateA083124589(List<String> a0000s) {
        UpdateConditionStep<org.jooq.Record> sqlStr = DSL_CONTEXT.update(table(name("a08")))
                .set(field(name("A0834")), "0")
                .set(field(name("A0831")), "0")
                .set(field(name("A0832")), "0")
                .set(field(name("A0835")), "0")
                .set(field(name("A0838")), "0")
                .set(field(name("A0839")), "0")
                .where(field(name("A0000")).in(a0000s));
        return Db.use(DB_PGSQL).update(sqlStr.getSQL(), sqlStr.getBindValues().toArray());
    }

    /**
     * 修改学历(多人)
     */
    public void updateA0834(List<String> a0000s) {
        UpdateConditionStep<org.jooq.Record> sqlStr = DSL_CONTEXT.update(table(name("a08")))
                .set(field(name("A0834")), "1")
                .where(field(name("A0800")).in(
                        select(field(name("s", "A0800")))
                                .from(select(field(name("A0800")),
                                        field("ROW_NUMBER () OVER ( PARTITION BY \"A0000\" ORDER BY \"A0801B\")").as("st"))
                                        .from(table(name("a08")))
                                        .where(field(name("A0000")).in(a0000s))
                                        .and(field(name("A0801B")).isNotNull()
                                                .and(field(name("A0801B")).ne(""))).asTable("s")).where(field(name("s", "st")).eq(1))
                ));
        Db.use(DB_PGSQL).update(sqlStr.getSQL(), sqlStr.getBindValues().toArray());
    }

    /**
     * 修改最高学位多人
     */
    public void updateA0835(List<String> a0000s) {
        UpdateConditionStep<org.jooq.Record> sqlStr = DSL_CONTEXT.update(table(name("a08")))
                .set(field(name("A0835")), "1")
                .where(field(name("A0800")).in(
                        select(field(name("s", "A0800")))
                                .from(select(field(name("A0800")),
                                        field("ROW_NUMBER () OVER ( PARTITION BY \"A0000\" ORDER BY \"A0901B\")").as("st"))
                                        .from(table(name("a08")))
                                        .where(field(name("A0000")).in(a0000s))
                                        .and(field(name("A0901B")).isNotNull()
                                                .and(field(name("A0901B")).ne(""))).asTable("s")).where(field(name("s", "st")).eq(1))
                ));
        Db.use(DB_PGSQL).update(sqlStr.getSQL(), sqlStr.getBindValues().toArray());
    }


    /**
     * 最高全日制学历多人
     */
    public void updateA0831(List<String> a0000s) {
        UpdateConditionStep<org.jooq.Record> sqlStr = DSL_CONTEXT.update(table(name("a08")))
                .set(field(name("A0831")), "1")
                .where(field(name("A0800")).in(
                        select(field(name("s", "A0800")))
                                .from(select(field(name("A0800")),
                                        field("ROW_NUMBER () OVER ( PARTITION BY \"A0000\" ORDER BY \"A0801B\")").as("st"))
                                        .from(table(name("a08")))
                                        .where(field(name("A0000")).in(a0000s))
                                        .and(field(name("A0837")).eq("1"))
                                        .and(field(name("A0801B")).isNotNull()
                                                .and(field(name("A0801B")).ne(""))).asTable("s")).where(field(name("s", "st")).eq(1))
                ));
        Db.use(DB_PGSQL).update(sqlStr.getSQL(), sqlStr.getBindValues().toArray());
    }

    /**
     * 最高全日制学位多人
     */
    public void updateA0832(List<String> a0000s) {
        UpdateConditionStep<org.jooq.Record> sqlStr = DSL_CONTEXT.update(table(name("a08")))
                .set(field(name("A0832")), "1")
                .where(field(name("A0800")).in(
                        select(field(name("s", "A0800")))
                                .from(select(field(name("A0800")),
                                        field("ROW_NUMBER () OVER ( PARTITION BY \"A0000\" ORDER BY \"A0901B\")").as("st"))
                                        .from(table(name("a08")))
                                        .where(field(name("A0000")).in(a0000s))
                                        .and(field(name("A0837")).eq("1"))
                                        .and(field(name("A0901B")).isNotNull()
                                                .and(field(name("A0901B")).ne(""))).asTable("s")).where(field(name("s", "st")).eq(1))
                ));
        Db.use(DB_PGSQL).update(sqlStr.getSQL(), sqlStr.getBindValues().toArray());
    }

    /**
     * 最高在职学历
     */
    public void updateA0838(List<String> a0000s) {
        UpdateConditionStep<org.jooq.Record> sqlStr = DSL_CONTEXT.update(table(name("a08")))
                .set(field(name("A0838")), "1")
                .where(field(name("A0800")).in(
                        select(field(name("s", "A0800")))
                                .from(select(field(name("A0800")),
                                        field("ROW_NUMBER () OVER ( PARTITION BY \"A0000\" ORDER BY \"A0801B\")").as("st"))
                                        .from(table(name("a08")))
                                        .where(field(name("A0000")).in(a0000s))
                                        .and(field(name("A0837")).eq("0"))
                                        .and(field(name("A0801B")).isNotNull()
                                                .and(field(name("A0801B")).ne(""))).asTable("s")).where(field(name("s", "st")).eq(1))
                ));
        Db.use(DB_PGSQL).update(sqlStr.getSQL(), sqlStr.getBindValues().toArray());
    }

    /**
     * 最高在职学位 多人
     */
    public void updateA0839(List<String> a0000s) {
        UpdateConditionStep<org.jooq.Record> sqlStr = DSL_CONTEXT.update(table(name("a08")))
                .set(field(name("A0839")), "1")
                .where(field(name("A0800")).in(
                        select(field(name("s", "A0800")))
                                .from(select(field(name("A0800")),
                                        field("ROW_NUMBER () OVER ( PARTITION BY \"A0000\" ORDER BY \"A0901B\")").as("st"))
                                        .from(table(name("a08")))
                                        .where(field(name("A0000")).in(a0000s))
                                        .and(field(name("A0837")).eq("0"))
                                        .and(field(name("A0901B")).isNotNull()
                                                .and(field(name("A0901B")).ne(""))).asTable("s")).where(field(name("s", "st")).eq(1))
                ));
        Db.use(DB_PGSQL).update(sqlStr.getSQL(), sqlStr.getBindValues().toArray());
    }

}



