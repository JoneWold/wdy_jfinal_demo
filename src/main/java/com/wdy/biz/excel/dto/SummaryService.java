package com.wdy.biz.excel.dto;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.jfinal.aop.Aop;
import com.jfinal.plugin.activerecord.Record;
import com.wdy.biz.base.BaseService;
import com.wdy.biz.dictionary.service.DictionaryService;
import com.wdy.common.utils.VkCollectionUtil;
import com.wdy.message.OutMessage;
import com.wdy.message.Status;

import java.util.*;

import static com.wdy.constant.CommonConstant.*;


/**
 * 综合统计
 *
 * @author LHR
 * @date 2019/04/15 09:13
 */
public class SummaryService extends BaseService {

    private SummaryDao summaryDao = Aop.get(SummaryDao.class);

    private DictionaryService dictionaryService = Aop.get(DictionaryService.class);

    /**
     * 综合统计接口
     *
     * @param summary
     * @return
     */
    public OutMessage<List<List<Map>>> summary(SummaryDto summary) {

        //1:为空判断
        if (StrUtil.isEmpty(summary.getOrgCode()) || StrUtil.isEmpty(summary.getA0165()) || StrUtil.isEmpty(summary.getA0221())) {
            return new OutMessage<>(Status.PARA_ERROR);
        }

        //二维数组
        List<List<String>> packList = new ArrayList<>();
        Map<String, List<String>> packMap = new LinkedHashMap<String, List<String>>();
        packMap.put("count", this.initList());

        //2:层次以及职级
        String A0165Str = VkCollectionUtil.getString(summary.getA0165().split(","));
        //3:查询出字典表的数据(职务层次)
        List<String> A0221List = new ArrayList<>();
        this.saveMapAndList(packMap, A0221List, summary);
        //4:查出数据
        List<Record> datas = summaryDao.findData(summary.getOrgCode(), A0165Str);

        //5:组装map
        for (Record data : datas) {
            this.assembleMap(packMap, data);
        }


        String typePre = null;
        List<String> countList = new ArrayList<>();
        List<String> ZJList = new ArrayList<>();
        //6:完成每个职务层次小计的统计
        for (String s : packMap.keySet()) {
            //由于是linkedHashMap所以是顺序的
            if (s.equals("count")) {
                ZJList = packMap.get(s);
            }
            if (s.contains("count") && !s.equals("count")) {
                int count = s.indexOf("count");
                typePre = s.substring(0, count);
                countList = packMap.get(s);
            } else {
                this.countAny(ZJList, packMap.get(s));
            }
            if (ObjectUtil.isNotNull(typePre) && s.contains(typePre)) {
                List<String> typeList = packMap.get(s);
                this.countAny(countList, typeList);
            }
            packList.add(packMap.get(s));
        }
        //操作
        List<List<Map>> result = this.ListListToListMap(packList);
        return new OutMessage<List<List<Map>>>(Status.SUCCESS, result);
    }


    /**
     * 综合统计接口
     *
     * @param summary
     * @return
     */
    public OutMessage<List<List<Map>>> summaryAge(SummaryDto summary) {
        //1:为空判断
        if (StrUtil.isEmpty(summary.getOrgCode()) || StrUtil.isEmpty(summary.getA0165()) || StrUtil.isEmpty(summary.getA0221())) {
            return new OutMessage<>(Status.PARA_ERROR);
        }

        //二维数组
        List<List<String>> packList = new ArrayList<>();
        Map<String, List<String>> packMap = new LinkedHashMap<String, List<String>>();
        packMap.put("count", this.initAgeList());

        //2:层次以及职级
        String A0165Str = VkCollectionUtil.getString(summary.getA0165().split(","));
        //3:查询出字典表的数据(职务层次)
        List<String> A0221List = new ArrayList<>();
        this.saveMapAndListAge(packMap, A0221List, summary);

        //4:查询出数据
        List<Record> datas = summaryDao.findData(summary.getOrgCode(), A0165Str);

        //5:组装map
        for (Record data : datas) {
            List<String> avgAge = new ArrayList<>();
            this.assembleMapAge(packMap, data, avgAge);
        }
        String typePre = null;
        List<String> countList = new ArrayList<>();
        List<String> ZJList = new ArrayList<>();
        //6:完成每个职务层次小计的统计
        for (String s : packMap.keySet()) {
            //由于是linkedHashMap所以是顺序的
            if (s.equals("count")) {
                ZJList = packMap.get(s);
            }
            if (s.contains("count") && !s.equals("count")) {
                int count = s.indexOf("count");
                typePre = s.substring(0, count);
                countList = packMap.get(s);
            } else {
                this.countAny(ZJList, packMap.get(s));
            }
            if (ObjectUtil.isNotNull(typePre) && s.contains(typePre)) {
                List<String> typeList = packMap.get(s);
                this.countAny(countList, typeList);
            }
            packList.add(packMap.get(s));
        }
        //操作
        List<List<Map>> result = this.ListListToListMapAge(packList);

        return new OutMessage<List<List<Map>>>(Status.SUCCESS, result);
    }


    /**
     * 每个职务层次小计的统计
     */
    private void countAny(List<String> countList, List<String> typeList) {
        for (int i = 0; i < countList.size(); i++) {
            if (StrUtil.isNotEmpty(typeList.get(i))) {
                int oldNum = StrUtil.isEmpty(countList.get(i)) ? 0 : Integer.valueOf(countList.get(i));
                int newNum = Integer.valueOf(typeList.get(i)) + oldNum;
                countList.set(i, String.valueOf(newNum));
            }
        }
    }

    /**
     * 初始化List
     */
    private List<String> initList() {
        return new LinkedList<String>() {{
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
        }};
    }

    /**
     * 初始化List
     */
    private List<String> initAgeList() {
        return new LinkedList<String>() {{
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
            add("");
        }};
    }

    /**
     * 组装Map信息
     */
    private void assembleMap(Map<String, List<String>> packMap, Record record) {
        //判断职务层次
        if (StrUtil.isNotEmpty(record.getStr("A0221"))) {
            String a0221 = record.getStr("A0221");
            List<String> a02211 = packMap.get(a0221);
            if (ObjectUtil.isNotNull(a02211)) {
                if (StrUtil.isEmpty(a02211.get(0))) {
                    a02211.set(0, "1");
                } else {
                    a02211.set(0, String.valueOf(Integer.valueOf(a02211.get(0)) + 1));
                }

                //性别 女
                if (StrUtil.isNotEmpty(record.getStr("A0104")) && record.getStr("A0104").equals(TWO)) {
                    updateList(a02211, 1);
                }

                //少数民族
                if (StrUtil.isNotEmpty(record.get("A0117")) && !record.getStr("A0117").equals(ZERO_ONE)) {
                    updateList(a02211, 2);
                }

                //是否党员
                if (StrUtil.isNotEmpty(record.get("A0141"))) {
                    if (record.getStr("A0141").equals(ZERO_ONE) || record.getStr("A0141").equals(ZERO_TWO)) {
                        updateList(a02211, 3);
                    } else {
                        updateList(a02211, 4);
                    }
                }

                //学历数据
                if (StrUtil.isNotEmpty(record.getStr("A0801B"))) {
                    String a0801B = record.getStr("A0801B");
                    //研究生
                    if (a0801B.startsWith(ONE)) {
                        updateList(a02211, 5);
                    } else
                        //本科
                        if (a0801B.startsWith(TWO)) {
                            updateList(a02211, 6);
                        } else
                            //专科
                            if (a0801B.startsWith(THREE)) {
                                updateList(a02211, 7);
                            } else
                                //中专
                                if (a0801B.startsWith("41") && a0801B.startsWith("47")) {
                                    updateList(a02211, 8);
                                } else {
                                    //高中及以下
                                    updateList(a02211, 9);
                                }
                }

                //学位数据
                if (StrUtil.isNotEmpty(record.getStr("A0901B"))) {
                    String a0901B = record.getStr("A0901B");
                    //博士
                    if (a0901B.startsWith(ONE) || a0901B.startsWith(TWO)) {
                        updateList(a02211, 10);
                    }
                    //硕士
                    if (a0901B.startsWith(THREE)) {
                        updateList(a02211, 11);
                    }
                    //学士
                    if (a0901B.startsWith(FOUR)) {
                        updateList(a02211, 12);
                    }
                }

                //年龄
                if (ObjectUtil.isNotNull(record.getDate("A0107"))) {
                    Date a0107 = record.getDate("A0107");
                    long age = DateUtil.betweenYear(a0107, new Date(), false);
                    //35岁及以下
                    if (age <= 35L) {
                        updateList(a02211, 13);
                    }
                    //36-40
                    if (age >= 36L && age <= 40L) {
                        updateList(a02211, 14);
                    }
                    //41-45
                    if (age >= 41L && age <= 45L) {
                        updateList(a02211, 15);
                    }
                    //46-50
                    if (age >= 46L && age <= 50L) {
                        updateList(a02211, 16);
                    }
                    //51-54
                    if (age >= 51L && age <= 54L) {
                        updateList(a02211, 17);
                    }
                    //55-59
                    if (age >= 55L && age <= 59L) {
                        updateList(a02211, 18);
                    }
                    //60-00
                    if (age >= 60) {
                        updateList(a02211, 19);
                    }
                }

                //任现职务层次年限
                if (ObjectUtil.isNotNull(record.getDate("A0288"))) {
                    Date a0288 = record.getDate("A0288");
                    long year = DateUtil.betweenYear(a0288, new Date(), false);
                    // year < 1
                    if (year < 1L) {
                        updateList(a02211, 20);
                    }
                    // 1<= year < 3
                    if (year >= 1L && year < 3L) {
                        updateList(a02211, 21);
                    }
                    // 3<= year < 5
                    if (year >= 3L && year < 5L) {
                        updateList(a02211, 22);
                    }
                    // 5<= year < 10
                    if (year >= 5L && year < 10L) {
                        updateList(a02211, 23);
                    }
                    // year >= 10
                    if (year >= 10L) {
                        updateList(a02211, 24);
                    }
                }

                //是否具有2年以上基层工作经验
                if (StrUtil.isNotEmpty(record.getStr("A0197"))) {
                    String a0197 = record.getStr("A0197");
                    if (a0197.equals(ONE)) {
                        updateList(a02211, 25);
                    }
                }
            }
        }
    }

    /**
     * 保存数组
     */
    private void updateList(List<String> list, int index) {
        if (StrUtil.isEmpty(list.get(index))) {
            list.set(index, "1");
        } else {
            list.set(index, String.valueOf(Integer.valueOf(list.get(index)) + 1));
        }
    }

    /**
     * 将List转成Map
     */
    private List<List<Map>> ListListToListMapAge(List<List<String>> packList) {
        List<List<Map>> result = new ArrayList<>();
        for (List<String> strings : packList) {
            List<Map> maps = new ArrayList<>();
            int i = 0;
            for (String string : strings) {
                Map<String, String> map = new HashMap<String, String>(1);
                if (i == 42) {
                    if (StrUtil.isNotEmpty(strings.get(0)) && StrUtil.isNotEmpty(string)) {
                        Double count = Double.valueOf(strings.get(0));
                        Double num = Double.valueOf(string);
                        map.put("value", String.format("%.2f", num / count));
                        maps.add(map);
                    } else {
                        map.put("value", string);
                        maps.add(map);
                    }
                } else {
                    map.put("value", string);
                    maps.add(map);
                }
                i++;
            }
            result.add(maps);
        }
        return result;
    }

    /**
     * 将List转成Map
     */
    private List<List<Map>> ListListToListMap(List<List<String>> packList) {
        List<List<Map>> result = new ArrayList<>();
        for (List<String> strings : packList) {
            List<Map> maps = new ArrayList<>();
            for (String string : strings) {
                Map<String, String> map = new HashMap<String, String>(1);
                map.put("value", string);
                maps.add(map);
            }
            result.add(maps);
        }
        return result;
    }

    /**
     * 组装Map年龄统计
     *
     * @param packMap
     * @param record
     */
    private void assembleMapAge(Map<String, List<String>> packMap, Record record, List<String> avgAge) {
        //判断职务层次
        if (StrUtil.isNotEmpty(record.getStr("A0221"))) {
            String a0221 = record.getStr("A0221");
            List<String> a02211 = packMap.get(a0221);
            if (ObjectUtil.isNotNull(a02211)) {
                if (StrUtil.isEmpty(a02211.get(0))) {
                    a02211.set(0, "1");
                } else {
                    a02211.set(0, String.valueOf(Integer.valueOf(a02211.get(0)) + 1));
                }

                if (ObjectUtil.isNotNull(record.getDate("A0107"))) {
                    Date a0107 = record.getDate("A0107");
                    Integer age = (int) DateUtil.betweenYear(a0107, new Date(), false);
                    if (age <= 20) {
                        this.updateList(a02211, 1);
                        this.setAvgAge(a02211, age);
                    }
                    if (age >= 60) {
                        this.updateList(a02211, 41);
                        this.setAvgAge(a02211, age);
                    }
                    for (int i = 21; i <= 59; i++) {
                        if (age == i) {
                            this.updateList(a02211, i - 19);
                            this.setAvgAge(a02211, age);
                        }
                    }
                }
            }
        }
    }

    private void setAvgAge(List<String> a02211, Integer age) {
        if (StrUtil.isEmpty(a02211.get(42))) {
            a02211.set(42, String.valueOf(age));
        } else {
            a02211.set(42, String.valueOf(Integer.valueOf(a02211.get(42)) + age));
        }
    }

    /**
     * 组装字典表的数据(人员)
     *
     * @param packMap
     * @param A0221List
     * @param summary
     */
    private void saveMapAndList(Map packMap, List<String> A0221List, SummaryDto summary) {
        List<String> zb09 = dictionaryService.getDictionaryValue("ZB09");
        if (!summary.getA0221().equals("all")) {
            A0221List = Arrays.asList(summary.getA0221().split(","));
        } else {
            List<Record> zb091 = dictionaryService.getDictionaryList("ZB09");
            for (Record record : zb091) {
                String sub_code_value = record.getStr("SUB_CODE_VALUE");
                if (!sub_code_value.equals(ONE) && !sub_code_value.equals(FONE) && !sub_code_value.equals(SEVEN)
                        && !CollectionUtil.contains(A0221List, sub_code_value)) {
                    A0221List.add(sub_code_value);
                }
            }
        }


        for (String A0221 : A0221List) {
            int i = 0;
            for (String zb09Str : zb09) {
                if (zb09Str.startsWith(A0221) && !zb09Str.equals(A0221)) {
                    if (i == 0) {
                        packMap.put(A0221 + "count", this.initList());
                    }
                    packMap.put(zb09Str, this.initList());
                    i++;
                }
            }
        }
    }

    /**
     * 组装字典表的数据(年龄)
     *
     * @param packMap
     * @param A0221List
     * @param summary
     */
    private void saveMapAndListAge(Map packMap, List<String> A0221List, SummaryDto summary) {
        List<String> zb09 = dictionaryService.getDictionaryValue("ZB09");
        if (!summary.getA0221().equals("all")) {
            A0221List = Arrays.asList(summary.getA0221().split(","));
        } else {
            List<Record> zb091 = dictionaryService.getDictionaryList("ZB09");
            for (Record record : zb091) {
                String sub_code_value = record.getStr("SUB_CODE_VALUE");
                if (!sub_code_value.equals(ONE) && !sub_code_value.equals(FONE) && !sub_code_value.equals(SEVEN)
                        && !CollectionUtil.contains(A0221List, sub_code_value)) {
                    A0221List.add(sub_code_value);
                }
            }
        }


        for (String A0221 : A0221List) {
            int i = 0;
            for (String zb09Str : zb09) {
                if (zb09Str.startsWith(A0221) && !zb09Str.equals(A0221)) {
                    if (i == 0) {
                        packMap.put(A0221 + "count", this.initAgeList());
                    }
                    packMap.put(zb09Str, this.initAgeList());
                    i++;
                }
            }
        }
    }
}