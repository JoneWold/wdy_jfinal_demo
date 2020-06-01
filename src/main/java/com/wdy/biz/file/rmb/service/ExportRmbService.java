package com.wdy.biz.file.rmb.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.aspose.words.Document;
import com.aspose.words.ImportFormatMode;
import com.b1809.aspose.rmb.Person;
import com.b1809.aspose.rmb.PersonFamilyMember;
import com.b1809.aspose.rmb.ReportRmb;
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
import net.lingala.zip4j.core.ZipFile;

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
                toPath = "/upload/" + fileName + "等" + a01List.size() + "人任免表信息" + DateUtil.format(new Date(), "yyyyMMddHHmmss") + POINT_ZIP;
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
            engine.addSharedFunction("/rmb/rmbLrmx.xml");
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
            engine.getTemplate("/rmb/rmbLrmx.xml").render(null, file);
            fileList.add(file);
        }
        if (fileList.size() > 1) {
            String fileName = fileList.get(0).getName();
            String name = fileName.substring(0, fileName.lastIndexOf("."));
            toPath = "/upload/" + name + "等" + fileList.size() + "人任免表信息" + DateUtil.format(new Date(), "yyyyMMddHHmmss") + POINT_ZIP;
            XmlZipFileUtil.zipFile(fileList, PathKit.getWebRootPath() + toPath);
            fileList.forEach(FileUtil::del);
        }
        return StrKit.isBlank(toPath) ? new OutMessage(Status.FAIL) : new OutMessage<>(Status.SUCCESS, toPath);
    }


    public OutMessage exportWord(List<String> a0000s) throws Exception {
        List<File> fileList = new ArrayList<>();
        String toPath = "";
        List<A01> a01List = dao.findA01List(a0000s);
        for (A01 a01 : a01List) {
            List<A36> a36List = dao.findA36List(a01.getA0000());
            Person person = new Person();
            // 数据填充
            this.setPerson(person, a01, a36List);
            ReportRmb reportRmb = new ReportRmb();
            Document word = reportRmb.createWord(person);
            // 生成文件 .doc
            toPath = "/upload/" + a01.getA0101() + ".doc";
            File file = FileUtil.file(PathKit.getWebRootPath() + toPath);
            word.save(file.getPath());
            fileList.add(file);
        }
        if (fileList.size() > 1) {
            String a0101 = a01List.get(0).getA0101();
            toPath = "/upload/" + a0101 + "等" + fileList.size() + "人任免表信息" + DateUtil.format(new Date(), "yyyyMMddHHmmss") + POINT_ZIP;
            XmlZipFileUtil.zipFile(fileList, PathKit.getWebRootPath() + toPath);
            fileList.forEach(FileUtil::del);
        }
        return new OutMessage<>(Status.SUCCESS, toPath);
    }


    public OutMessage stampWord(List<String> a0000s) throws Exception {
        if (a0000s.size() == 1) {
            return this.exportWord(a0000s);
        }
        String zipPath = this.exportWord(a0000s).getData().toString();
        // 获取zip文件
        ZipFile zipFile = new ZipFile(PathKit.getWebRootPath() + zipPath);
        // 解压目录
        File dir = FileUtil.file(PathKit.getWebRootPath() + "/upload/" + DateUtil.format(new Date(), "yyyyMMddHHmmss"));
        if (dir.isDirectory() && !dir.exists()) {
            boolean mkdir = dir.mkdir();
        }
        // 解压文件
        zipFile.extractAll(dir.getPath());
        // 清除原压缩文件
        FileUtil.del(zipFile.getFile());
        // 获取文件列表
        File[] files = dir.listFiles();
        Document nodes = new Document();
        if (files != null) {
            int index = 0;
            for (File file : files) {
                Document document = new Document(file.getPath());
                document.getFirstSection().getPageSetup().setSectionStart(1);
                if (index == 0) {
                    nodes = document;
                } else {
                    nodes.appendDocument(document, ImportFormatMode.KEEP_SOURCE_FORMATTING);
                }
                index++;
            }
        }
        String toPath = "/upload/任免表人员数据打印文件" + DateUtil.format(new Date(), "yyyyMMddHHmmss") + ".doc";
        nodes.save(PathKit.getWebRootPath() + toPath);
        // 清除压缩后的文件
        FileUtil.del(dir);
        return new OutMessage<>(Status.SUCCESS, toPath);
    }


    /**
     * 填充null值
     */
    private void setEmptyField(Record record) {
        String[] columnNames = record.getColumnNames();
        // 获取每一个字段的值
        for (String columnName : columnNames) {
            if (ObjectUtil.isNull(record.get(columnName))) {
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

    /**
     * 填充word数据
     */
    private void setPerson(Person person, A01 a01, List<A36> a36List) {
        person.set_XingMing(a01.getA0101());
        person.set_XingMing_Output(a01.getA0101());
        person.set_XingBie(xbMap.get(a01.getA0104()));
        Date a0107 = a01.getA0107();
        if (a0107 != null) {
            String birthday = DateUtil.format(a0107, "yyyy.MM");
            long age = DateUtil.betweenYear(a0107, new Date(), false);
            person.set_ChuShengNianYue(birthday + "(" + age + ")");
            person.set_ChuShengNianYue_Output(birthday + "  (" + age + "岁" + ")");
        }
        person.set_MinZu(mzMap.get(a01.getA0117()));
        person.set_JiGuan(a01.getA0111A());
        person.set_RuDangShiJian(a01.getA0140());
        person.set_RuDangShiJian_Output(a01.getA0140());
        person.set_JianKangZhuangKuang(a01.getA0128B());
        person.set_ChuShengDi(a01.getA0114A());
        Date a0134 = a01.getA0134();
        person.set_CanJiaGongZuoShiJian(DateUtil.format(a0134, "yyyy.MM"));
        person.set_CanJiaGongZuoShiJian_Output(DateUtil.format(a0134, "yyyy.MM"));
        person.set_ZhuanYeJiShuZhiWu(a01.getA0196());
        person.set_ShuXiZhuanYeYouHeZhuanChang(a01.getA0187A());
        // 全日制学历学位
        person.set_QuanRiZhiJiaoYu_XueLi(a01.getQRZXL());
        person.set_QuanRiZhiJiaoYu_XueLiXueWei_Output(a01.getQRZXL() + "\n" + a01.getQRZXW());
        person.set_QuanRiZhiJiaoYu_XueWei(a01.getQRZXW());
        person.set_QuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi(a01.getQRZXLXX());
        person.set_QuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi(a01.getQRZXW());
        person.set_QuanRiZhiJiaoYu_BiYeYuanXiaoXi_Output(a01.getQRZXLXX());
        // 在职学历学位
        person.set_ZaiZhiJiaoYu_XueLi(a01.getZZXL());
        person.set_ZaiZhiJiaoYu_XueWei(a01.getZZXW());
        person.set_ZaiZhiZhiJiaoYu_XueLiXueWei_Output(a01.getZZXL() + "\n" + a01.getZZXW());
        person.set_ZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi(a01.getZZXLXX());
        person.set_ZaiZhiJiaoYu_XueWei_BiYeYuanXiaoXi(a01.getZZXW());
        person.set_ZaiZhiJiaoYu_BiYeYuanXiaoXi_Output(a01.getZZXLXX());
        person.set_XianRenZhiWu(a01.getA0192());
        person.set_NiRenZhiWu(a01.getNRZW());
        person.set_NiMianZhiWu(a01.getNMZW());
        String a1701 = StrUtil.isEmpty(a01.getA1701()) ? "" : a01.getA1701().replaceAll("<br/>", "").replaceAll("<br />", "");
        person.set_JianLi_Output(this.setA1701Value(a1701));
        person.set_JiangChengQingKuang(a01.getA14Z101());
        person.set_NianDuKaoHeJieGuo(a01.getA15Z101());
        person.set_RenMianLiYou(a01.getRMLY());
        person.set_ChengBaoDanWei(a01.getCBDW());
        person.set_JiSuanNianLingShiJian(DateUtil.format(new Date(), "yyyyMMdd"));
        person.set_TianBiaoRen(a01.getTBR());
        person.set_ShenFenZheng(a01.getA0184());
        String a0198 = a01.getA0198();
        if (StrKit.notBlank(a0198)) {
            person.set_ZhaoPianLuJing(PathKit.getWebRootPath() + a0198);
        }
        // 家庭成员
        List<PersonFamilyMember> familyList = new ArrayList<>();
        for (A36 a36 : a36List) {
            PersonFamilyMember family = new PersonFamilyMember();
            family.set_ChengWei(a36.getA3604A());
            family.set_XingMing(a36.getA3601());
            family.set_XingMing_Output(a36.getA3601());
            family.set_NianLing(String.valueOf(DateUtil.betweenYear(a36.getA3607(), new Date(), false)));
            String zzMm = zzmmMap.get(a36.getA3627());
            family.set_ZhengZhiMianMao(StrKit.isBlank(zzMm) ? a36.getA3627() : zzMm);
            family.set_GongZuoDanWeiJiZhiWu(a36.getA3611());
            if (StrUtil.isNotEmpty(a36.getA3611()) && StrUtil.containsAny(a36.getA3611(), "已去世")) {
                family.set_NianLing("");
            }
            familyList.add(family);
        }
        person.setList_JiaTingChengYuan(familyList);
        person.set_TianBiaoShiJian_Output("年  月  日");
        person.set_Version("3.2.1.16");
    }

    /**
     * 设置简历格式
     * 将word文档中不必要的换行符去掉
     */
    private String setA1701Value(String value) {
        if (StrUtil.isNotEmpty(value)) {
            char[] chars = value.toCharArray();
            if (chars.length > 5) {
                for (int i = 2; i < chars.length - 5; i++) {
                    char aChar4 = chars[i - 2];
                    char aChar0 = chars[i - 1];
                    char aChar = chars[i];
                    char aChar1 = chars[i + 1];
                    char aChar2 = chars[i + 2];
                    char aChar3 = chars[i + 3];
                    // 换行 \n 10
                    if (aChar0 == 10) {
                        // 如果后面四位不是连续的数字格式，则去掉该换行符
                        // 0->48，9->57
                        if (!(aChar >= 48 && aChar <= 57 && aChar1 >= 48 && aChar1 <= 57 && aChar2 >= 48 && aChar2 <= 57 && aChar3 >= 48 && aChar3 <= 57)) {
                            // 回车 归位键 13
                            chars[i - 1] = 13;
                        } else {
                            // 如果某一行末尾是换行符，且前一位是（  ， ： ； 等，则去掉该换行符
                            // 中文标点符号编码 '（' 65288   '，' 65292   '：' 65306   '；' 65307
                            // asc码 括号（->40  逗号，->44  冒号：->58  分号；->59
                            if (aChar4 == 65288 || aChar4 == 65292 || aChar4 == 65306 || aChar4 == 65307) {
                                chars[i - 1] = 13;
                            }
                        }
                    }
                }
            }
            return String.valueOf(chars).replaceAll("\r", "");
        }
        return value;
    }


    public static void main(String[] args) {
        String v = "1982.09--1985.07  四川省石柱师范学校中专学习\n" +
                "1985.07--1985.08  待分配\n" +
                "1985.08--1991.08  四川省石柱自治县下路中学教师（其间：\n" +
                "1988.08--1990.07四川省教育学院历史系历史专业大专学习）\n" +
                "1993.10--1996.12  四川省石柱自治县委办公室主办干事（1993.05--1995.12四川省委第二党校经济管理专业大学学习；\n" +
                "1995.12--1996.12挂职任大歇乡党委委员）\n" +
                "1998.01--2001.09  重庆市石柱自治县委办公室副主任，\n" +
                "2000.07正科级\n" +
                "2007.02--2012.02  重庆市石柱自治县委常委、办公室主任\n" +
                "（2004.09--2007.06重庆市委党校政治学理论专业研究生学习）\n" +
                "2016.11--         重庆市忠县县委常委、政法委书记";
        String a1701Value = new ExportRmbService().setA1701Value(v);
        System.out.println(a1701Value);
    }

}
