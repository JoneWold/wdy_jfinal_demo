package com.wdy.biz.base;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * BaseService
 * @date 2019/04/03
 * @author LHR
 */
public class BaseService {

    protected static List<SimpleDateFormat> dateFormats = new ArrayList<>();

    static {
        SimpleDateFormat yyyyMM = new SimpleDateFormat("yyyyMM");
        yyyyMM.setLenient(false);
        SimpleDateFormat yyyyMMdd =new SimpleDateFormat("yyyyMMdd");
        yyyyMMdd.setLenient(false);
        SimpleDateFormat yyyy_MM_dd = new SimpleDateFormat("yyyy.MM.dd");
        yyyy_MM_dd.setLenient(false);
        SimpleDateFormat yyyy_MM = new SimpleDateFormat("yyyy.MM");
        yyyy_MM.setLenient(false);
        dateFormats.add(yyyyMM);dateFormats.add(yyyyMMdd);dateFormats.add(yyyy_MM_dd);dateFormats.add(yyyy_MM);
    }

    /**
     * 获得查询支持的时间格式
     * @return TimeFormats
     */
    public List<SimpleDateFormat> getDateFormats(){
        return dateFormats;
    }

    public  String getCurrentYear(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return sdf.format(date);
    }


}
