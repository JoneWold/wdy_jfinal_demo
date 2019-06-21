package com.wdy.biz.dictionary.dao;

import com.jfinal.aop.Aop;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wdy.generator.postgreSQL.model.CodeValue;

import java.util.List;

import static com.wdy.constant.Constant.DB_PGSQL;


/**
 *
 * @date 2019/03/26
 * @author LHR
 */
public class DictionaryDao {
    public CodeValue dictMe = Aop.get(CodeValue.class).use(DB_PGSQL);

    /**
     * 返回所有的字典数据
     */
    public static List<Record> findAll() {
        return Db.use(DB_PGSQL).find("SELECT * FROM \"code_value\" order by \"CODE_TYPE\",\"ININO\",\"CODE_VALUE\"");
    }

    /**
     * 根据父节点查询
     *
     * @param codeType    字典类型
     * @param parentValue 父值
     * @return
     */
    public CodeValue getDictionaryByParent(String codeType, String parentValue) {
        return dictMe.findFirst("SELECT \"CODE_VALUE_SEQ\",\"CODE_TYPE\",\"CODE_COLUMN_NAME\",\"CODE_LEVEL\",\"CODE_VALUE\",\"ININO\",\"SUB_CODE_VALUE\",\"CODE_NAME\",\"CODE_NAME2\",\"CODE_NAME3\",\"CODE_REMARK\",\"CODE_SPELLING\",\"ISCUSTOMIZE\",\"CODE_ASSIST\",\"CODE_STATUS\",\"CODE_LEAF\",\"START_DATE\",\"STOP_DATE\" from code_value where \"CODE_TYPE\" = ? and \"SUB_CODE_VALUE\" = ? order by \"CODE_VALUE\"", codeType, parentValue);
    }
    public List<CodeValue> findByCodeType(String code){
            return dictMe.find("select \"CODE_VALUE_SEQ\",\"CODE_TYPE\",\"CODE_COLUMN_NAME\",\"CODE_LEVEL\",\"CODE_VALUE\",\"ININO\",\"SUB_CODE_VALUE\",\"CODE_NAME\",\"CODE_NAME2\",\"CODE_NAME3\",\"CODE_REMARK\",\"CODE_SPELLING\",\"ISCUSTOMIZE\",\"CODE_ASSIST\",\"CODE_STATUS\",\"CODE_LEAF\",\"START_DATE\",\"STOP_DATE\" FROM \"code_value\" WHERE \"CODE_TYPE\" = ? order by \"CODE_VALUE\"", code);
    }
}
