package com.wdy.biz.excel.ext;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

import static com.wdy.constant.DBConstant.DB_PGSQL;


/**
 * 综合统计dao
 *
 * @author LHR
 * @date 2019/04/15 09:13
 */
public class SummaryDao {

    public List<Record> findData(String orgCode, String A0165Str) {
        return Db.use(DB_PGSQL).find("SELECT MIN ( \"a01\".\"A0000\") \"A0000\",MIN ( \"a01\".\"A0104\") \"A0104\",MIN ( \"a01\".\"A0117\") \"A0117\",MIN ( \"a01\".\"A0141\") \"A0141\",MIN ( \"a01\".\"QRZXL\") \"QRZXL\",MIN ( \"a01\".\"QRZXW\") \"QRZXW\",MIN ( \"a01\".\"A0107\") \"A0107\",MIN ( \"a01\".\"A0288\") \"A0288\",MIN ( \"a01\".\"A0197\") \"A0197\",MIN ( \"a08\".\"A0801B\" ) AS \"A0801B\",MIN ( \"a08\".\"A0901B\" ) AS \"A0901B\",MIN (\"a01\".\"A0221\") \"A0221\" " +
                "FROM \"a01\" LEFT JOIN \"a02\" ON \"a01\".\"A0000\" = a02.\"A0000\" LEFT JOIN \"b01\" ON \"a02\".\"A0201B\" = \"b01\".\"id\" LEFT JOIN \"a08\" ON \"a01\".\"A0000\" = \"a08\".\"A0000\" " +
                "WHERE \"b01\".\"B0111\" LIKE ' " + orgCode + "%' AND \"a01\".\"A0165\" IN ( " + A0165Str + " ) GROUP BY \"a01\".\"A0000\"");
    }

}
