package com.wdy.biz.file.rmb.dao;

import com.jfinal.aop.Aop;
import com.wdy.generator.postgreSQL.model.A01;
import com.wdy.generator.postgreSQL.model.A36;
import org.jooq.SelectConditionStep;
import org.jooq.SelectSeekStep1;

import java.util.List;

import static com.wdy.constant.CommonConstant.DSL_CONTEXT;
import static org.jooq.impl.DSL.*;

/**
 * @author wgch
 * @Description 导出任免表
 * @date 2019/12/17
 */
public class ExportRmbDao {
    private A01 a01 = Aop.get(A01.class);
    private A36 a36 = Aop.get(A36.class);

    /**
     * 获取人员信息
     */
    public List<A01> findA01List(List<String> a0000s) {
        SelectConditionStep records = DSL_CONTEXT.select().from(table(name("a01")))
                .where(field(name("A0000")).in(a0000s));
        return a01.find(records.getSQL(), records.getBindValues().toArray());
    }

    /**
     * 获取家庭成员
     */
    public List<A36> findA36List(String a0000) {
        SelectSeekStep1 records = DSL_CONTEXT.select().from(table(name("a36")))
                .where(field(name("A0000")).eq(a0000))
                .orderBy(field(name("SORTID")).asc());
        return a36.find(records.getSQL(), records.getBindValues().toArray());
    }


}
