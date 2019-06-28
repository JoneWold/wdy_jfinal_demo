package com.wdy.biz.excel.dao;

import cn.hutool.core.collection.CollectionUtil;
import com.jfinal.aop.Aop;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wdy.biz.dictionary.service.DictionaryService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import static com.wdy.constant.DBConstant.DB_PGSQL;


/**
 * @author wgch
 * @Description
 * @date 2019/4/26 10:51
 */
public class ExportExcelDao {

    private DictionaryService dictionaryService = Aop.get(DictionaryService.class);

    /**
     * 职务层次字典表数据
     *
     * @param a0221
     * @return
     */
    public ZwcjDto getZwcjData(String a0221) {
        String sql = "SELECT \"CODE_VALUE\",\"CODE_NAME\" from code_value where \"CODE_TYPE\"='ZB09'";
        // 父级职务层集合
        List<Record> parentList;
        String[] parentCodes = a0221.split(",");
        List<String> parentMergeCodes = new ArrayList<>();
        for (String code : parentCodes) {
            parentMergeCodes.add("\"CODE_VALUE\"='" + code + "'");
        }
        String parentCode = CollectionUtil.join(parentMergeCodes, " OR ");
        parentList = Db.use(DB_PGSQL).find(sql + " and (" + parentCode + ")");

        String mergeCode;
        if ("all".equals(a0221)) {
            String parentCodesSql = "select \"CODE_VALUE\",\"CODE_NAME\" from code_value where \"CODE_TYPE\"='ZB09' and (\"SUB_CODE_VALUE\" ='-1' or \"SUB_CODE_VALUE\"='1' or \"SUB_CODE_VALUE\"='7') and \"CODE_VALUE\" !='1' ORDER BY \"CODE_VALUE\"\n";
            parentList = Db.use(DB_PGSQL).find(parentCodesSql);
            List<String> mergeCodes = new ArrayList<>();
            for (Record record : parentList) {
                String code_value = record.getStr("CODE_VALUE");
                mergeCodes.add("\"SUB_CODE_VALUE\" like '" + code_value + "%'");
            }
            mergeCode = CollectionUtil.join(mergeCodes, " OR ");
        } else {
            mergeCode = this.likeSearchPre(a0221, "\"CODE_VALUE\"");
        }
        sql += " and (" + mergeCode + ") order by \"CODE_TYPE\",\"ININO\",\"CODE_VALUE\" ";
        List<Record> sonList = Db.use(DB_PGSQL).find(sql);
        ZwcjDto zwcjDto = new ZwcjDto();
        zwcjDto.setParent(parentList);
        zwcjDto.setSon(sonList);
        return zwcjDto;
    }


    public List<String> getZwcjName() {
        // 父级名称 集合
        List<String> nameList = new ArrayList<>();
        List<Record> zb091 = dictionaryService.getDictionaryList("ZB09");
        for (Record record : zb091) {
            String subCodeValue = record.getStr("SUB_CODE_VALUE");
            String codeValue = record.getStr("CODE_VALUE");
            String codeName = record.getStr("CODE_NAME");
            if (!codeValue.equals("1")) {
                if (subCodeValue.equals("1") || subCodeValue.equals("-1") || subCodeValue.equals("7")) {
                    nameList.add(codeName);
                }
            }
        }
        return nameList;
    }

    /**
     * like以什么开头进行查询
     */
    public String likeSearchPre(String likeStr, String filed) {
        String sql = "";
        String s = likeStr.replaceAll(" ", ",");
        likeStr = s.replaceAll("，", ",");
        String[] likes = likeStr.split(",");
        for (int i = 0; i < likes.length; i++) {
            if (i == 0) {
                if (likes.length != 1) {
                    sql += " (" + filed + " like '" + likes[i] + "%' ";
                } else {
                    sql += " (" + filed + " like '" + likes[i] + "%' ) ";
                }
            } else if (i == likes.length - 1) {
                sql += " or " + filed + " like '" + likes[i] + "%' )";
            } else {
                sql += " or " + filed + " like '" + likes[i] + "%'";
            }
        }
        return sql;
    }


    @Data
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public class ZwcjDto {
        public List<Record> parent;
        public List<Record> son;
    }

}
