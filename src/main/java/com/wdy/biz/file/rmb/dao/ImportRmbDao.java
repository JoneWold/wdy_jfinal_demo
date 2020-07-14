package com.wdy.biz.file.rmb.dao;

import cn.hutool.core.util.ObjectUtil;
import com.jfinal.aop.Aop;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.wdy.dto.RmbOldMemInfoDto;
import com.wdy.generator.postgreSQL.model.A01Temp;
import com.wdy.generator.postgreSQL.model.A08;
import com.wdy.generator.postgreSQL.model.YoungCadre;
import org.jooq.DeleteConditionStep;
import org.jooq.Record1;
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
    private A01Temp a01Temp = Aop.get(A01Temp.class);
    private YoungCadre youngCadre = Aop.get(YoungCadre.class);
    private A08 a08 = Aop.get(A08.class);

    public Map<String, String> getDictNameToCode(String type) {
        SelectConditionStep records = DSL_CONTEXT.select()
                .from(table(name("code_value")))
                .where(field(name("CODE_TYPE"), String.class).eq(type));
        List<Record> recordList = Db.use(DB_PGSQL).find(records.getSQL(), records.getBindValues().toArray());
        return recordList.stream().collect(Collectors.toMap(k -> k.getStr("CODE_NAME"), v -> v.getStr("CODE_VALUE"), (k, v) -> k));
    }

    public Map<String, String> getDictCode2Name(String type) {
        SelectConditionStep records = DSL_CONTEXT.select()
                .from(table(name("code_value")))
                .where(field(name("CODE_TYPE"), String.class).eq(type));
        List<Record> recordList = Db.use(DB_PGSQL).find(records.getSQL(), records.getBindValues().toArray());
        return recordList.stream().collect(Collectors.toMap(k -> k.getStr("CODE_VALUE"), v -> v.getStr("CODE_NAME"), (k, v) -> k));
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
     * 在任人员A0000集合
     */
    private HashSet<String> getZzRecord() {
        List<String> list = Db.use(DB_PGSQL).query("select \"A0000\" from \"a02\" where \"A0255\"=? GROUP BY \"A0000\"", "1");
        return new HashSet<>(list);
    }

    /***
     * 单位层级下的在职人员
     * @param b0111 单位层级码
     */
    public HashSet<String> getZzByOrgCode(String b0111) {
        SelectConditionStep record1s = DSL_CONTEXT.select(field(name("A0000")))
                .from(table(name("a02")))
                .innerJoin(table(name("b01"))).on(field(name("a02", "A0201B")).eq(field(name("b01", "id"))))
                .where(field(name("A0255"), String.class).eq("1")
                        .and(field(name("B0111"), String.class).like(b0111 + "%")));
        List<String> list = Db.use(DB_PGSQL).query(record1s.getSQL(), record1s.getBindValues().toArray());
        return new HashSet<>(list);
    }

    /**
     * 获取对比列表
     */
    public Page<A01Temp> getList(int pageNum, int pageSize, String impId) {
        SelectConditionStep count = DSL_CONTEXT.selectCount().from(table(name("a01_temp")))
                .where(field(name("impId"), String.class).eq(impId));
        SelectConditionStep record8s = DSL_CONTEXT.select(field(name("A0000"))
                , field(name("A0101"))
                , field(name("A0107"))
                , field(name("A0184"))
                , field(name("A0192"))
                , field(name("oldDataArray"))
                , field(name("toA0000"))
                , field(name("result"))
        ).from(table(name("a01_temp"))).where(field(name("impId"), String.class).eq(impId));
        return a01Temp.paginateByFullSql(pageNum, pageSize, count.getSQL(), record8s.getSQL(), record8s.getBindValues().toArray());
    }

    /**
     * 更新匹配到的原人员数据
     */
    public int update(String a0000, String toA0000, String impId) {
        return Db.use(DB_PGSQL).update("update \"a01_temp\" set \"toA0000\"=? where \"impId\"=? and \"A0000\"=?", toA0000, impId, a0000);
    }

    /**
     * 根据导入批次号和人员名称获取该人员数据
     *
     * @param impId 批次号
     * @param a0101 姓名
     */
    public Record findByImpIdName(String impId, String a0101) {
        return Db.use(DB_PGSQL).findFirst("select * from \"a01_temp\" where \"impId\"=? and \"A0101\"=?", impId, a0101);
    }

    /**
     * 如果是 zip 需要根据批次号 和 zip的包名去查询
     *
     * @param impId 文件导入表示
     * @return 人员A0000
     */
    public List<String> findA01tempByTypeAndImpId(String impId) {
        SelectConditionStep<Record1<Object>> sqlStr = DSL_CONTEXT.select(field(name("A0000")))
                .from(table(name("a01_temp")))
                .where(field(name("type")).eq("2"))
                .and(field(name("impId")).eq(impId));
        return Db.use(DB_PGSQL).find(sqlStr.getSQL(), sqlStr.getBindValues().toArray())
                .stream().map(var -> var.getStr("A0000"))
                .collect(Collectors.toList());

    }

    public List<A01Temp> findA01TempList(String impId, String type) {
        SelectConditionStep records = DSL_CONTEXT.select().from(table(name("a01_temp")))
                .where(field(name("impId"), String.class).eq(impId)
                        .and(field(name("type")).eq(type)));
        return a01Temp.use(DB_PGSQL).find(records.getSQL(), records.getBindValues().toArray());
    }

    public List<Record> findTempList(String tabName, String impId, String type) {
        SelectConditionStep records = DSL_CONTEXT.select().from(table(name(tabName)))
                .where(field(name("impId"), String.class).eq(impId)
                        .and(field(name("type")).eq(type)));
        return Db.use(DB_PGSQL).find(records.getSQL(), records.getBindValues().toArray());
    }

    /***
     * 清除原表数据
     * @param saveA0000Set 待保存的A0000集合
     */
    public void delTableList(HashSet<String> saveA0000Set) {
        DeleteConditionStep a36Step = DSL_CONTEXT.deleteFrom(table(name("a36"))).where(field(name("A0000")).in(saveA0000Set));
        DeleteConditionStep a57Step = DSL_CONTEXT.deleteFrom(table(name("a57"))).where(field(name("A0000")).in(saveA0000Set));
        int a36 = Db.use(DB_PGSQL).delete(a36Step.getSQL(), a36Step.getBindValues().toArray());
        LogKit.info("清除a36，响应条数：" + a36);
        int a57 = Db.use(DB_PGSQL).delete(a57Step.getSQL(), a57Step.getBindValues().toArray());
        LogKit.info("清除a57，响应条数：" + a57);
    }


    /**
     * 获取优秀年轻干部type=0的人员集合
     */
    public List<YoungCadre> findYoungCadreList() {
        SelectConditionStep records = DSL_CONTEXT.select()
                .from(table(name("youngCadre")))
                .where(field(name("type")).eq("0"));
        return youngCadre.find(records.getSQL(), records.getBindValues().toArray());
    }

    /***
     * 机构排序号
     * @param orgId 机构id
     * @return 公务员的机构id
     */
    public Integer findOrgTreeSortById(String orgId) {
        SelectConditionStep<Record1<Object>> sqlStr = DSL_CONTEXT.select(field(name("orgTreeSort")))
                .from(table(name("b01")))
                .where(field(name("id")).eq(orgId));
        return Db.use(DB_PGSQL).queryInt(sqlStr.getSQL(), sqlStr.getBindValues().toArray());
    }


    /***
     * 通过人员标识查询学历信息
     * @param toA0000
     */
    public List<Record> findA08List(String toA0000) {
        SelectConditionStep records = DSL_CONTEXT.select().from(table(name("a08")))
                .where(field(name("A0000")).eq(toA0000));
        return Db.use(DB_PGSQL).find(records.getSQL(), records.getBindValues().toArray());
    }

    public List<A08> findA08s(String toA0000) {
        SelectConditionStep records = DSL_CONTEXT.select().from(table(name("a08")))
                .where(field(name("A0000")).eq(toA0000));
        return a08.find(records.getSQL(), records.getBindValues().toArray());
    }

    /***
     * 删除
     * @param tempRecord 人员的A0000标识
     * @param impId 导入唯一键
     */
    public void deleteTempByA0000AndImpId(List<String> tempRecord, String impId) {
        DeleteConditionStep<org.jooq.Record> a01SqlStr = DSL_CONTEXT.deleteFrom(table(name("a01_temp")))
                .where(field(name("A0000")).in(tempRecord)).and(field(name("impId")).eq(impId));
        DeleteConditionStep<org.jooq.Record> a02SqlStr = DSL_CONTEXT.deleteFrom(table(name("a02_temp")))
                .where(field(name("A0000")).in(tempRecord)).and(field(name("impId")).eq(impId));
        DeleteConditionStep<org.jooq.Record> a05SqlStr = DSL_CONTEXT.deleteFrom(table(name("a05_temp")))
                .where(field(name("A0000")).in(tempRecord)).and(field(name("impId")).eq(impId));
        DeleteConditionStep<org.jooq.Record> a06SqlStr = DSL_CONTEXT.deleteFrom(table(name("a06_temp")))
                .where(field(name("A0000")).in(tempRecord)).and(field(name("impId")).eq(impId));
        DeleteConditionStep<org.jooq.Record> a08SqlStr = DSL_CONTEXT.deleteFrom(table(name("a08_temp")))
                .where(field(name("A0000")).in(tempRecord)).and(field(name("impId")).eq(impId));
        DeleteConditionStep<org.jooq.Record> a14SqlStr = DSL_CONTEXT.deleteFrom(table(name("a14_temp")))
                .where(field(name("A0000")).in(tempRecord)).and(field(name("impId")).eq(impId));
        DeleteConditionStep<org.jooq.Record> a15SqlStr = DSL_CONTEXT.deleteFrom(table(name("a15_temp")))
                .where(field(name("A0000")).in(tempRecord)).and(field(name("impId")).eq(impId));
        DeleteConditionStep<org.jooq.Record> a30SqlStr = DSL_CONTEXT.deleteFrom(table(name("a30_temp")))
                .where(field(name("A0000")).in(tempRecord)).and(field(name("impId")).eq(impId));
        DeleteConditionStep<org.jooq.Record> a36SqlStr = DSL_CONTEXT.deleteFrom(table(name("a36_temp")))
                .where(field(name("A0000")).in(tempRecord)).and(field(name("impId")).eq(impId));
        DeleteConditionStep<org.jooq.Record> a57SqlStr = DSL_CONTEXT.deleteFrom(table(name("a57_temp")))
                .where(field(name("A0000")).in(tempRecord)).and(field(name("impId")).eq(impId));
        DeleteConditionStep<org.jooq.Record> a99z1SqlStr = DSL_CONTEXT.deleteFrom(table(name("a99z1_temp")))
                .where(field(name("A0000")).in(tempRecord)).and(field(name("impId")).eq(impId));
        Db.use(DB_PGSQL).delete(a01SqlStr.getSQL(), a01SqlStr.getBindValues().toArray());
        Db.use(DB_PGSQL).delete(a02SqlStr.getSQL(), a02SqlStr.getBindValues().toArray());
        Db.use(DB_PGSQL).delete(a05SqlStr.getSQL(), a05SqlStr.getBindValues().toArray());
        Db.use(DB_PGSQL).delete(a06SqlStr.getSQL(), a06SqlStr.getBindValues().toArray());
        Db.use(DB_PGSQL).delete(a08SqlStr.getSQL(), a08SqlStr.getBindValues().toArray());
        Db.use(DB_PGSQL).delete(a14SqlStr.getSQL(), a14SqlStr.getBindValues().toArray());
        Db.use(DB_PGSQL).delete(a15SqlStr.getSQL(), a15SqlStr.getBindValues().toArray());
        Db.use(DB_PGSQL).delete(a30SqlStr.getSQL(), a30SqlStr.getBindValues().toArray());
        Db.use(DB_PGSQL).delete(a36SqlStr.getSQL(), a36SqlStr.getBindValues().toArray());
        Db.use(DB_PGSQL).delete(a57SqlStr.getSQL(), a57SqlStr.getBindValues().toArray());
        Db.use(DB_PGSQL).delete(a99z1SqlStr.getSQL(), a99z1SqlStr.getBindValues().toArray());
    }
}
