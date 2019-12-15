package com.wdy.biz.file.rmb.dao;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wdy.dto.RmbOldMemInfoDto;
import org.jooq.SelectConditionStep;

import java.util.*;
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


    /**
     * 根据身份证 查询人员
     */
    public Map<String, List<RmbOldMemInfoDto>> findA01ByA0184() {
        List<Record> records = Db.use(DB_PGSQL).find("select \"A0000\",\"A0184\",\"A0192\",\"A0107\",\"A0101\" from \"a01\" where \"A0184\" is not null");
        Map<String, List<Record>> a0184Map = records.stream().collect(Collectors.groupingBy(record -> record.getStr("A0184")));
        // 在任人员
        HashSet<String> zzRecord = this.getZzRecord();
        // 组装结果集 key身份证 value身份证对应的人员信息
        Map<String, List<RmbOldMemInfoDto>> result = new HashMap<>(a0184Map.size());
        for (Map.Entry<String, List<Record>> entry : a0184Map.entrySet()) {
            String a0184 = entry.getKey();
            List<RmbOldMemInfoDto> memInfoDtoList = new ArrayList<>();
            // 类型转换
            entry.getValue().forEach(e -> memInfoDtoList.add(this.setMemInfoDto(e, zzRecord)));
            result.put(a0184, memInfoDtoList);
        }
        return result;
    }

    /**
     * 根据姓名，出生年月查询人员
     *
     * @param a0101 姓名
     * @param a0107 出生年月
     */
    public List<RmbOldMemInfoDto> findA01ByA0101(String a0101, Date a0107) {
        List<RmbOldMemInfoDto> memInfoDtoList = new ArrayList<>();
        if (StrKit.isBlank(a0101) || ObjectUtil.isEmpty(a0107)) {
            return memInfoDtoList;
        }
        List<Record> records = Db.use(DB_PGSQL).find("SELECT * FROM \"a01\" where \"A0101\"=? and \"A0107\"=?", a0101, a0107);
        // 在任人员
        HashSet<String> zzRecord = this.getZzRecord();
        for (Record record : records) {
            RmbOldMemInfoDto memInfoDto = this.setMemInfoDto(record, zzRecord);
            memInfoDtoList.add(memInfoDto);
        }
        return memInfoDtoList;
    }

    /**
     * 设置原数据库人员数据
     */
    private RmbOldMemInfoDto setMemInfoDto(Record record, HashSet<String> zzRecord) {
        RmbOldMemInfoDto memInfoDto = new RmbOldMemInfoDto();
        String a0000 = record.getStr("A0000");
        memInfoDto.setA0000(a0000);
        memInfoDto.setA0184(StrKit.isBlank(record.getStr("A0184")) ? "" : record.getStr("A0184"));
        memInfoDto.setA0192(StrKit.isBlank(record.getStr("A0192")) ? "" : record.getStr("A0192"));
        memInfoDto.setA0107(StrKit.isBlank(record.getStr("A0107")) ? "" : record.getStr("A0107"));
        memInfoDto.setA0101(StrKit.isBlank(record.getStr("A0101")) ? "" : record.getStr("A0101"));
        // 是否在任
        String a0255 = zzRecord.contains(a0000) ? "1" : "0";
        memInfoDto.setA0255(a0255);
        return memInfoDto;
    }


    /**
     * 在任人员
     */
    private HashSet<String> getZzRecord() {
        List<String> list = Db.use(DB_PGSQL).query("select \"A0000\" from \"a02\" where \"A0255\"=? GROUP BY \"A0000\"", "1");
        return new HashSet<>(list);
    }


}
