package com.wdy.biz.excel.dto;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

import static com.wdy.constant.Constant.DB_PGSQL;


/**
 * 综合统计dao
 * @date 2019/04/15 09:13
 * @author LHR
 */
public class SummaryDao {

    public List<Record> findData(String orgCode,String A0165Str){
        return Db.use(DB_PGSQL).find("select \"a01\".\"A0000\",\"a01\".\"A0104\",\"a01\".\"A0117\",\"a01\".\"A0141\",\"a01\".\"QRZXL\",\"a01\".\"QRZXW\",\"a01\".\"A0107\",\"a01\".\"A0288\",\"a01\".\"A0197\",min(\"a08\".\"A0801B\") as \"A0801B\",min(\"a08\".\"A0901B\") as \"A0901B\",\"a01\".\"A0221\" from \"a01\" LEFT JOIN \"a02\" on \"a01\".\"A0000\" = a02.\"A0000\" LEFT JOIN \"b01\" on \"a02\".\"A0201B\" = \"b01\".\"id\" LEFT JOIN \"a08\" on \"a01\".\"A0000\" = \"a08\".\"A0000\" where \"b01\".\"B0111\" like '"+orgCode+"%' and \"a01\".\"A0165\" in ("+A0165Str+")  group by \"a01\".\"A0000\"");
    }

}
