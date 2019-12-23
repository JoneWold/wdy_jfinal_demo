package com.wdy.biz.file.rmb.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.jfinal.aop.Aop;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;
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
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.wdy.constant.CommonConstant.*;
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

    public OutMessage exportLrm(List<String> a0000s) throws Exception {
        List<File> fileList = new ArrayList<>();
        String toPath = "";
        // 获取人员数据
        List<A01> a01List = dao.findA01List(a0000s);
        for (A01 a01 : a01List) {
            this.setEmptyField(a01.toRecord());
            List<A36> a36List = dao.findA36List(a01.getA0000());
            // 组装结果字符串
            StringBuilder sb = new StringBuilder();
            sb.append("\"").append(a01.getA0101()).append("\",");
            sb.append("\"").append(xbMap.get(a01.getA0104())).append("\",");
            String birthday = DateUtil.format(a01.getA0107(), "yyyyMM");
            sb.append("\"").append(StrKit.isBlank(birthday) ? "" : birthday).append("\",");
            sb.append("\"").append(mzMap.get(a01.getA0117())).append("\",");
            sb.append("\"").append(a01.getA0111A()).append("\",");
            sb.append("\"").append(a01.getA0140()).append("\",");
            sb.append("\"").append(a01.getA0128()).append("\",");
            sb.append("\"").append(a01.getA0114A()).append("\",");
            String joinTime = DateUtil.format(a01.getA0134(), "yyyyMM");
            sb.append("\"").append(StrKit.isBlank(joinTime) ? "" : joinTime).append("\",");
            sb.append("\"").append(this.setXlXwTxt(a01)).append("\",");
            sb.append("\"").append(this.setByYxTxt(a01)).append("\",");
            sb.append("\"").append(a01.getA0196()).append("\",");
            sb.append("\"").append("\",");
            sb.append("\"").append("\",");
            sb.append("\"").append("\",");
            sb.append("\"").append("\",");
            sb.append("\"").append(a01.getA0187A()).append("\",");
            sb.append("\"").append(a01.getA1701()).append("\",");
            sb.append("\"").append(a01.getA14Z101()).append("\",");
            sb.append("\"").append(a01.getA15Z101()).append("\",");
            // 家庭成员 5列 分成5句字符串
            this.setFamilyTxt(sb, a36List);
            sb.append("\"").append(a01.getA0192()).append("\",");
            sb.append("\"").append(a01.getNRZW()).append("\",");
            sb.append("\"").append(a01.getNMZW()).append("\",");
            sb.append("\"").append(a01.getRMLY()).append("\",");
            sb.append("\"").append(a01.getCBDW()).append("\",");
            String jSNLsj = new SimpleDateFormat("yyyyMMdd").format(new Date());
            sb.append("\"").append(jSNLsj).append("\",");
            sb.append("\"").append(a01.getTBR()).append("\",");
            sb.append("\"").append(a01.getTBSJ()).append("\"");

            // 创建文件 .lrm （注意：lrm 文件必须配备在相同路径下相对应文件名的pic图片文件才能显示出照片）
            File file = new File(PathKit.getWebRootPath() + "/upload/" + a01.getA0101() + POINT_LRM);
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), "GBK");
            writer.write(sb.toString().replaceAll("null", ""));
            writer.close();
            fileList.add(file);
            // 创建照片 .pic
            String a0198 = a01.getA0198();
            if (StrKit.notBlank(a0198)) {
                File imgFile = new File(PathKit.getWebRootPath() + a0198);
                if (imgFile.exists()) {
                    String target = PathKit.getWebRootPath() + "/upload/" + a01.getA0101() + ".pic";
                    Path copy = Files.copy(Paths.get(imgFile.getPath()), Paths.get(target), StandardCopyOption.COPY_ATTRIBUTES);
                    fileList.add(copy.toFile());
                } else {
                    LogKit.error(a01.getA0101() + "-->>> 照片文件缺失！");
                }
            } else {
                LogKit.error(a01.getA0101() + "-->>> 照片路径错误或不存在！");
            }
        }
        // 压缩文件
        String fileName;
        if (a01List.size() > 0) {
            fileName = a01List.get(0).getA0101();
            if (a01List.size() == 1) {
                toPath = "/upload/" + fileName + POINT_ZIP;
            } else {
                toPath = "/upload/" + fileName + "等" + a01List.size() + "人任免表信息" + POINT_ZIP;
            }
            XmlZipFileUtil.zipFile(fileList, PathKit.getWebRootPath() + toPath);
            fileList.forEach(FileUtil::del);
        }
        return new OutMessage<>(Status.SUCCESS, toPath);
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
            // 使用模板引擎
            Engine engine = new Engine();
            engine.setBaseTemplatePath("template");
            // 设置引擎从 class path 以及 jar 包中加载模板文件 rmbLrmx.xml
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


    /**
     * 填充null值
     */
    private void setEmptyField(Record record) {
        String[] columnNames = record.getColumnNames();
        // 获取每一个字段的值
        for (String columnName : columnNames) {
            if (record.get(columnName) == null) {
                record.set(columnName, "");
            }
        }
    }

    /**
     * 组合学历学位 字符串
     * , 每一项大的标题 # 单元格中换行 @ 单元格外换行
     */
    private String setXlXwTxt(A01 a01) {
        StringBuilder sb = new StringBuilder();
        String qrzXL = a01.getQRZXL();
        String qrzXW = a01.getQRZXW();
        String zzXL = a01.getZZXL();
        String zzXW = a01.getZZXW();
        sb.append(qrzXL).append("#").append(qrzXW).append("@");
        sb.append(zzXL).append("#").append(zzXW);
        return sb.toString();
    }

    /**
     * 组合毕业院校及专业 字符串
     */
    private String setByYxTxt(A01 a01) {
        StringBuilder sb = new StringBuilder();
        String qrzxlxx = a01.getQRZXLXX();
        String qrzxwxx = a01.getQRZXWXX();
        String zzxlxx = a01.getZZXLXX();
        String zzxwxx = a01.getZZXWXX();
        sb.append(qrzxlxx).append("#").append(qrzxwxx).append("@");
        sb.append(zzxlxx).append("#").append(zzxwxx);
        return sb.toString();
    }

    /**
     * "妻子@母亲@父亲@@@@@@@@@@",
     * "啧啧@祁娜康@颜厚菊@@@@@@@@@@",
     * "201802@197208@196911@@@@@@@@@@",
     * "中共党员@群众@群众@@@@@@@@@@",
     * "啊实打实大@璧山区璧城街道居民@重庆市璧山区狮子新街居民@@@@@@@@@@",
     */
    private void setFamilyTxt(StringBuilder sb, List<A36> a36List) {
        StringBuilder chengWei = new StringBuilder();
        StringBuilder xingMing = new StringBuilder();
        StringBuilder birthday = new StringBuilder();
        StringBuilder zzMmName = new StringBuilder();
        StringBuilder gzdwjzw = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            // 获取家庭成员数据
            if (i <= a36List.size() - 1) {
                A36 a36 = a36List.get(i);
                chengWei.append(a36.getA3604A()).append("@");
                xingMing.append(a36.getA3601()).append("@");
                String a3607 = DateUtil.format(a36.getA3607(), "yyyyMM");
                birthday.append(a3607).append("@");
                String zzMm = zzmmMap.get(a36.getA3627());
                zzMmName.append(StrKit.isBlank(zzMm) ? a36.getA3627() : zzMm).append("@");
                gzdwjzw.append(a36.getA3611()).append("@");
            } else {
                chengWei.append("@");
                xingMing.append("@");
                birthday.append("@");
                zzMmName.append("@");
                gzdwjzw.append("@");
            }
        }
        sb.append("\"").append(chengWei).append("\",");
        sb.append("\"").append(xingMing).append("\",");
        sb.append("\"").append(birthday).append("\",");
        sb.append("\"").append(zzMmName).append("\",");
        sb.append("\"").append(gzdwjzw).append("\",");
    }

}
