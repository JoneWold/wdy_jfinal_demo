package com.wdy.biz.file.rmb.dao;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import org.jooq.SelectConditionStep;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.wdy.constant.CommonConstant.DSL_CONTEXT;
import static com.wdy.constant.DBConstant.DB_PGSQL;
import static org.jooq.impl.DSL.*;

/**
 * @author wgch
 * @Description 导入任免表dao
 * @date 2019/12/13
 */
public class ImportRmbDao {

    public Map<String, String> getDictNameToCode(String type) {
        SelectConditionStep records = DSL_CONTEXT.select()
                .from(table(name("code_value")))
                .where(field(name("CODE_TYPE"), String.class).eq(type));
        List<Record> recordList = Db.use(DB_PGSQL).find(records.getSQL(), records.getBindValues().toArray());
        return recordList.stream().collect(Collectors.toMap(k -> k.getStr("CODE_NAME"), v -> v.getStr("CODE_VALUE"), (k, v) -> k));
    }


}
