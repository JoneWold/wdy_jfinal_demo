package com.wdy.biz.file.rmb.service;

import cn.hutool.core.io.FileUtil;
import com.jfinal.aop.Aop;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.StrKit;
import com.jfinal.template.Engine;
import com.wdy.biz.file.rmb.dao.ExportRmbDao;
import com.wdy.biz.file.rmb.dao.ImportRmbDao;
import com.wdy.generator.postgreSQL.model.A01;
import com.wdy.generator.postgreSQL.model.A36;
import com.wdy.message.OutMessage;
import com.wdy.message.Status;
import com.wdy.utils.ImageBase64Util;
import com.wdy.utils.XmlZipFileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.wdy.constant.CommonConstant.POINT_LRMX;
import static com.wdy.constant.CommonConstant.POINT_ZIP;
import static com.wdy.constant.DictConstant.*;

/**
 * @author wgch
 * @Description 导出任免表
 * @date 2019/12/17
 */
public class ExportRmbService {
    private ExportRmbDao dao = Aop.get(ExportRmbDao.class);
    private ImportRmbDao importRmbDao = Aop.get(ImportRmbDao.class);
    private Map<String, String> xbMap = importRmbDao.getDictCode2Name(XB_TYPE);
    private Map<String, String> mzMap = importRmbDao.getDictCode2Name(MZ_TYPE);
    private Map<String, String> zzmmMap = importRmbDao.getDictCode2Name(ZZMM_TYPE);

    public OutMessage exportLrm(List<String> a0000s) {
        return null;
    }


    public OutMessage exportLrmx(List<String> a0000s) {
        List<File> fileList = new ArrayList<>();
        String toPath = "";
        // 获取人员数据
        List<A01> a01List = dao.findA01List(a0000s);
        for (A01 a01 : a01List) {
            // 家庭成员
            List<A36> a36List = dao.findA36List(a01.getA0000());
            // 照片
            String img = ImageBase64Util.imgToB64(PathKit.getWebRootPath() + a01.getA0198());

            Engine engine = new Engine();
            engine.setBaseTemplatePath("template");
            // 设置引擎从 class path 以及 jar 包中加载模板文件
            engine.setToClassPathSourceFactory();
            engine.addSharedFunction("rmbLrmx.xml");
            engine.addSharedObject("a01", a01);
            engine.addSharedObject("a36List", a36List);
            engine.addSharedObject("xbMap", xbMap);
            engine.addSharedObject("mzMap", mzMap);
            engine.addSharedObject("zzmmMap", zzmmMap);
            engine.addSharedObject("curDate", new Date());
            engine.addSharedObject("img", img);
            // 渲染到 .lrmx 文件中去
            toPath = "/upload/" + a01.getA0101() + POINT_LRMX;
            File file = FileUtil.file(PathKit.getWebRootPath() + toPath);
            engine.getTemplate("rmbLrmx.xml").render(null, file);
            fileList.add(file);
        }
        if (fileList.size() > 1) {
            String fileName = fileList.get(0).getName();
            String name = fileName.substring(0, fileName.lastIndexOf("."));
            toPath = "/upload/" + name + "等" + fileList.size() + "人任免表信息" + POINT_ZIP;
            XmlZipFileUtil.zipFile(fileList, PathKit.getWebRootPath() + toPath);
            fileList.forEach(FileUtil::del);
        }
        return StrKit.isBlank(toPath) ? new OutMessage(Status.FAIL) : new OutMessage<>(Status.SUCCESS, toPath);
    }


    public OutMessage exportWord(List<String> a0000s) {
        return null;
    }


    public OutMessage stampWord(List<String> a0000s) {
        return null;
    }


}
