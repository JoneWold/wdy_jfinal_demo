package com.wdy.biz.file.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wdy.generator.postgreSQL.model.A01Temp;
import com.wdy.generator.postgreSQL.model.A36Temp;
import com.wdy.generator.postgreSQL.model.A57Temp;
import com.wdy.utils.ImageBase64Util;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jooq.SelectConditionStep;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

import static com.wdy.constant.CommonConstant.DSL_CONTEXT;
import static com.wdy.constant.CommonConstant.SEPARATOR;
import static com.wdy.constant.DBConstant.DB_PGSQL;
import static com.wdy.constant.DictConstant.*;
import static org.jooq.impl.DSL.*;

/**
 * @author wgch
 * @Description 解析 lrm lrmx
 * @date 2019/12/10 15:20
 */
public class ReadRmbService {

    private Map<String, String> xbMap = this.getDictNameToCode(XB_TYPE);
    private Map<String, String> mzMap = this.getDictNameToCode(MZ_TYPE);
    private Map<String, String> zzmmMap = this.getDictNameToCode(ZZMM_TYPE);

    public List<Object> readTxtLrm(String path, String impId) throws Exception {
        File file = FileUtil.file(path);
        InputStreamReader reader = new InputStreamReader(new FileInputStream(file), "GBK");
        BufferedReader br = new BufferedReader(reader);
        // 读取文本数据以字符串形式存储
        StringBuilder sb = new StringBuilder();
        String temp;
        while ((temp = br.readLine()) != null) {
            sb.append(temp);
        }
        br.close();
        reader.close();
        //字符串排列规则：, 每一项大的标题  # 单元格中换行  @ 单元格外换行
        String[] split = sb.toString().split(",");
        // a01_temp
        A01Temp a01Temp = new A01Temp();
        String a0000 = StrKit.getRandomUUID();
        a01Temp.setImpId(impId);
        a01Temp.setA0000(a0000);
        int index = 0;
        for (String value : split) {
            if (StrKit.notBlank(value)) {
                // 去掉每一项字段的引号
                value = value.replaceAll("\"", "");
            }
            if (index == 0) a01Temp.setA0101(value);
            if (index == 1) a01Temp.setA0104(xbMap.get(value));
            if (index == 2) a01Temp.setA0107(this.getXmlTime(value));
            if (index == 3) a01Temp.setA0117(mzMap.get(value));
            if (index == 4) a01Temp.setA0111A(value);
            if (index == 5) a01Temp.setA0140(value);
            if (index == 6) a01Temp.setA0128(value);
            if (index == 7) a01Temp.setA0114A(value);
            if (index == 8) a01Temp.setA0134(this.getXmlTime(value));
            if (index == 9) {
                String[] xlxw = value.split("@");
                for (int i = 0; i < xlxw.length; i++) {
                    // 全日制教育
                    if (i == 0) {
                        String[] qrz = xlxw[i].split("#");
                        if (qrz.length == 1) {
                            if (xlxw[i].matches(".+#")) {
                                a01Temp.setQRZXL(qrz[0]);
                            } else {
                                a01Temp.setQRZXW(qrz[0]);
                            }
                        } else if (qrz.length == 2) {
                            a01Temp.setQRZXL(qrz[0]);
                            a01Temp.setQRZXW(qrz[1]);
                        }
                        // 在职教育
                    } else if (i == 1) {
                        String[] zz = xlxw[1].split("#");
                        if (zz.length == 1) {
                            if (xlxw[i].matches(".+#")) {
                                a01Temp.setZZXL(zz[0]);
                            } else {
                                a01Temp.setZZXW(zz[0]);
                            }
                        } else if (zz.length == 2) {
                            a01Temp.setZZXL(zz[0]);
                            a01Temp.setZZXW(zz[1]);
                        }
                    }
                }
            }
            if (index == 10) {
                String[] xlxw = value.split("@");

            }


            index++;
        }


        List<Object> list = new ArrayList<>();
        list.add(a01Temp);

        return list;
    }

    public List<Object> readXmlLrmx(String path, String impId) throws Exception {
        File file = FileUtil.file(path);
        SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        // Person
        Element root = document.getRootElement();
        // a01_temp组装数据
        A01Temp a01Temp = new A01Temp();
        String a0000 = StrKit.getRandomUUID();
        a01Temp.setImpId(impId);
        a01Temp.setA0000(a0000);
        a01Temp.setA0101(root.elementText("XingMing"));
        String xingBie = root.elementText("XingBie");
        a01Temp.setA0104(xbMap.get(xingBie));
        String chuShengNianYue = root.elementText("ChuShengNianYue");
        a01Temp.setA0107(this.getXmlTime(chuShengNianYue));
        String minZu = root.elementText("MinZu");
        a01Temp.setA0117(mzMap.get(minZu));
        a01Temp.setA0111A(root.elementText("JiGuan"));
        a01Temp.setA0114A(root.elementText("ChuShengDi"));
        a01Temp.setA0140(root.elementText("RuDangShiJian"));
        String canJiaGongZuoShiJian = root.elementText("CanJiaGongZuoShiJian");
        a01Temp.setA0134(this.getXmlTime(canJiaGongZuoShiJian));
        a01Temp.setA0128(root.elementText("JianKangZhuangKuang"));
        a01Temp.setA0196(root.elementText("ZhuanYeJiShuZhiWu"));
        a01Temp.setA0187A(root.elementText("ShuXiZhuanYeYouHeZhuanChang"));
        a01Temp.setQRZXL(root.elementText("QuanRiZhiJiaoYu_XueLi"));
        a01Temp.setQRZXW(root.elementText("QuanRiZhiJiaoYu_XueWei"));
        a01Temp.setQRZXLXX(root.elementText("QuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi"));
        a01Temp.setQRZXWXX(root.elementText("QuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi"));
        a01Temp.setZZXL(root.elementText("ZaiZhiJiaoYu_XueLi"));
        a01Temp.setZZXW(root.elementText("ZaiZhiJiaoYu_XueWei"));
        a01Temp.setZZXLXX(root.elementText("ZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi"));
        a01Temp.setZZXWXX(root.elementText("ZaiZhiJiaoYu_XueWei_BiYeYuanXiaoXi"));
        a01Temp.setA0192(root.elementText("XianRenZhiWu"));
        a01Temp.setNRZW(root.elementText("NiRenZhiWu"));
        a01Temp.setNMZW(root.elementText("NiMianZhiWu"));
        String jianLi = root.elementText("JianLi");
        if (StrKit.notBlank(jianLi)) {
            a01Temp.setA1701(jianLi.replaceAll("<br>", "\n").replaceAll("<br/>", "\n").replaceAll("<br >", "\n"));
        }
        a01Temp.setA14Z101(root.elementText("JiangChengQingKuang"));
        a01Temp.setA15Z101(root.elementText("NianDuKaoHeJieGuo"));
        a01Temp.setRMLY(root.elementText("RenMianLiYou"));
        List<A36Temp> a36TempList = new ArrayList<>();
        Iterator jTCYList = root.elementIterator("JiaTingChengYuan");
        while (jTCYList.hasNext()) {
            Element next = (Element) jTCYList.next();
            Iterator item = next.elementIterator("Item");
            int sort = 1;
            while (item.hasNext()) {
                Element element = (Element) item.next();
                String chengWei = element.elementText("ChengWei");
                String xingMing = element.elementText("XingMing");
                String chuShengRiQi = element.elementText("ChuShengRiQi");
                String zhengZhiMianMao = element.elementText("ZhengZhiMianMao");
                String gongZuoDanWeiJiZhiWu = element.elementText("GongZuoDanWeiJiZhiWu");
                // a36_temp
                A36Temp a36Temp = new A36Temp();
                a36Temp.setImpId(impId);
                a36Temp.setA0000(a0000);
                a36Temp.setA3600(StrKit.getRandomUUID());
                a36Temp.setA3604A(chengWei);
                a36Temp.setA3601(xingMing);
                a36Temp.setA3607(this.getXmlTime(chuShengRiQi));
                String zzmmCode = zzmmMap.get(zhengZhiMianMao);
                if (StrKit.isBlank(zzmmCode)) {
                    zzmmCode = zhengZhiMianMao;
                }
                a36Temp.setA3627(zzmmCode);
                a36Temp.setA3611(gongZuoDanWeiJiZhiWu);
                a36Temp.setSORTID(sort);
                a36Temp.setA3699(1);
                a36TempList.add(a36Temp);
                sort++;

            }
        }
        a01Temp.setCBDW(root.elementText("ChengBaoDanWei"));
        a01Temp.setJSNLSJ(this.getXmlTime(root.elementText("JiSuanNianLingShiJian")));
        a01Temp.setTBSJ(this.getXmlTime(root.elementText("TianBiaoShiJian")));
        a01Temp.setTBR(root.elementText("TianBiaoRen"));
        a01Temp.setA0184(root.elementText("ShenFenZheng"));
        String zhaoPian = root.elementText("ZhaoPian");

        StringBuilder fileName = new StringBuilder();
        String toPath = SEPARATOR + "download" + SEPARATOR + a0000 + ".jpg";
        // 将base64编码字符串转换为图片，存入图片路径
        String a0198 = ImageBase64Util.base64ToImage(zhaoPian, toPath);
        if (StrKit.notBlank(a0198)) {
            fileName.append(a0000).append(".jpg");
        }
        a01Temp.setA0198(a0198);
        // a57_temp
        A57Temp a57Temp = new A57Temp();
        a57Temp.setImpId(impId);
        a57Temp.setA0000(a0000);
        a57Temp.setA5714(fileName.toString());
        a57Temp.setUPDATED("1");
        a57Temp.setPHOTODATA(zhaoPian);
        a57Temp.setPHOTONAME(fileName.toString());
        a57Temp.setPHOTSTYPE("jpg");
        // 返回结果
        List<Object> list = new ArrayList<>();
        list.add(a01Temp);
        list.add(a36TempList);
        list.add(a57Temp);
        return list;
    }


    private Map<String, String> getDictNameToCode(String type) {
        SelectConditionStep records = DSL_CONTEXT.select()
                .from(table(name("code_value")))
                .where(field(name("CODE_TYPE"), String.class).eq(type));
        List<Record> recordList = Db.use(DB_PGSQL).find(records.getSQL(), records.getBindValues().toArray());
        return recordList.stream().collect(Collectors.toMap(k -> k.getStr("CODE_NAME"), v -> v.getStr("CODE_VALUE"), (k, v) -> k));
    }


    /**
     * 时间日期格式化
     */
    private Date getXmlTime(String value) {
        try {
            if (StrKit.notBlank(value)) {
                value = value.replaceAll("\\.", "");
                if (value.length() == 8) {
                    return DateUtil.parse(value, "yyyyMMdd");
                } else if (value.length() == 6) {
                    return DateUtil.parse(value, "yyyyMM");
                } else if (value.length() == 4) {
                    return DateUtil.parse(value, "yyyy");
                }
            }
        } catch (Exception e) {
            LogKit.error("时间格式错误:" + value, e);
        }
        return null;
    }

}
