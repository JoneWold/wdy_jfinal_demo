package com.wdy.biz.dictionary.service;

import cn.hutool.core.util.StrUtil;
import com.jfinal.aop.Aop;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;
import com.wdy.biz.dictionary.dao.DictionaryDao;
import com.wdy.constant.CacheConstant;
import com.wdy.generator.postgreSQL.model.CodeValue;
import com.wdy.message.OutMessage;
import com.wdy.message.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 字典表的service
 * @author 刘浩然
 * @date 2019/03/18
 */
public class DictionaryService {

    private DictionaryDao dictionaryDao = Aop.get(DictionaryDao.class);

    /***
     * 根据类型与值进行查询字典
     * */
    public OutMessage<List<Record>> getDictionaryList(CodeValue codeValue) {
        if (StrUtil.isNotEmpty(codeValue.getCodeValue())) {
            List<Record> records = CacheKit.get(CacheConstant.DICT_CACHE_NAME, CacheConstant.DICT_CACHE_PREFIX + codeValue.getCodeType());
            records = records.stream()
                    .filter(record -> record.getStr("CODE_VALUE").
                            startsWith(codeValue.getCodeValue()))
                    .collect(Collectors.toList());
            return new OutMessage<>(Status.SUCCESS, records);
        }
        List<Record> result = CacheKit.get(CacheConstant.DICT_CACHE_NAME, CacheConstant.DICT_CACHE_PREFIX + codeValue.getCodeType());
        return new OutMessage<>(Status.SUCCESS, result);
    }

    /***
     * 加载字典
     * */
    public static void loadDict() {
        List<Record> recordList = DictionaryDao.findAll();
        Map<String, List<Record>> result = recordList.stream().collect(Collectors.groupingBy(o -> o.getStr("CODE_TYPE")));
        //缓存到内存中
        for (Map.Entry<String, List<Record>> entry : result.entrySet()) {
            CacheKit.put(CacheConstant.DICT_CACHE_NAME, CacheConstant.DICT_CACHE_PREFIX + entry.getKey(), entry.getValue());
        }

    }

    /**
     * 根据字典类型获取数据
     *
     * @param codeType
     * @return
     */
    public List<Record> getDictionaryList(String codeType) {
        return CacheKit.get(CacheConstant.DICT_CACHE_NAME, CacheConstant.DICT_CACHE_PREFIX + codeType);
    }

    /**
     * 根据字典类型获取数据
     * @param codeType
     * @return
     */
    public List<CodeValue> getDictionaryListByCode(String codeType) {
        return  dictionaryDao.findByCodeType(codeType);
    }

    /**
     * 根据类型和父代码查询下级
     *
     * @param codeType
     * @param parentValue
     * @return
     */
    public CodeValue getDictionaryByParent(String codeType, String parentValue) {
        return dictionaryDao.getDictionaryByParent(codeType,parentValue);
    }
    public List<CodeValue> getDictionaryList1(String codeType){
        return  dictionaryDao.findByCodeType(codeType);
    }

    public List<String> getDictionaryValue(String codeType){
        List<String> result = new ArrayList<>();
        List<Record> records = CacheKit.get(CacheConstant.DICT_CACHE_NAME, CacheConstant.DICT_CACHE_PREFIX + codeType);
        for (Record record : records) {
            String codeValue = record.getStr("CODE_VALUE");
            result.add(codeValue);
        }
        return result;
    }
}
