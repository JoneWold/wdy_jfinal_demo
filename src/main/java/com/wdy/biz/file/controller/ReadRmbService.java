package com.wdy.biz.file.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wdy.generator.postgreSQL.model.A01Temp;
import com.wdy.generator.postgreSQL.model.A36Temp;
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


    public void readTxtLrm(String path) throws Exception {
        File file = FileUtil.file(path);
        InputStreamReader reader = new InputStreamReader(new FileInputStream(file), "GBK");
        BufferedReader br = new BufferedReader(reader);
        // 存储字符串
        StringBuilder sb = new StringBuilder();
        String temp;
        while ((temp = br.readLine()) != null) {
            sb.append(temp);
        }
        br.close();
        reader.close();
//        System.out.println(sb);
        String[] split = sb.toString().split(",");
        A01Temp a01Temp = new A01Temp();


    }

    public List<Object> readXmlLrmx(String path, String impId) throws Exception {
        Map<String, String> xbMap = this.getDictNameToCode(XB_TYPE);
        Map<String, String> mzMap = this.getDictNameToCode(MZ_TYPE);
        Map<String, String> zzmmMap = this.getDictNameToCode(ZZMM_TYPE);
        File file = FileUtil.file(path);
        SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        // Person
        Element root = document.getRootElement();
        // 组装数据
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
        List<A36Temp> a36temps = new ArrayList<>();
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
                a36temps.add(a36Temp);
                sort++;

            }
        }
        a01Temp.setCBDW(root.elementText("ChengBaoDanWei"));
        a01Temp.setJSNLSJ(this.getXmlTime(root.elementText("JiSuanNianLingShiJian")));
        a01Temp.setTBSJ(this.getXmlTime(root.elementText("TianBiaoShiJian")));
        a01Temp.setTBR(root.elementText("TianBiaoRen"));
        a01Temp.setA0184(root.elementText("ShenFenZheng"));
        String zhaoPian = root.elementText("ZhaoPian");
        String toPath = SEPARATOR + "download" + SEPARATOR + a0000 + ".jpg";
        // 将base64编码字符串转换为图片，存入图片路径
        a01Temp.setA0198(ImageController.base64ToImage(zhaoPian, toPath));
        List<Object> list = new ArrayList<>();
        list.add(a01Temp);
        list.add(a36temps);
        return list;
    }


    private Map<String, String> getDictNameToCode(String type) {
        SelectConditionStep records = DSL_CONTEXT.select().from(table(name("code_value")))
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
