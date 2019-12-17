package com.wdy.biz.file.rmb.service;

import com.jfinal.aop.Aop;
import com.wdy.biz.file.rmb.dao.ExportRmbDao;
import com.wdy.message.OutMessage;

import java.util.List;

/**
 * @author wgch
 * @Description 导出任免表
 * @date 2019/12/17
 */
public class ExportRmbService {
    private ExportRmbDao dao = Aop.get(ExportRmbDao.class);


    public OutMessage exportLrm(List<String> a0000s) {
        return null;
    }


    public OutMessage exportLrmx(List<String> a0000s) {
        return null;
    }


    public OutMessage exportWord(List<String> a0000s) {
        return null;
    }


    public OutMessage stampWord(List<String> a0000s) {
        return null;
    }


}
