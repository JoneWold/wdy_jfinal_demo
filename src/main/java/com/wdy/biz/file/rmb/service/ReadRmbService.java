package com.wdy.biz.file.rmb.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.jfinal.aop.Aop;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.TableMapping;
import com.jfinal.plugin.ehcache.CacheKit;
import com.wdy.biz.file.rmb.dao.ImportRmbDao;
import com.wdy.generator.postgreSQL.model.A01Temp;
import com.wdy.generator.postgreSQL.model.A36Temp;
import com.wdy.generator.postgreSQL.model.A57Temp;
import com.wdy.utils.ImageBase64Util;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

import static com.wdy.constant.CommonConstant.NEW_PHOTOS_PATH;
import static com.wdy.constant.DictConstant.*;

/**
 * @author wgch
 * @Description 解析 lrm pic lrmx
 * @date 2019/12/10 15:20
 */
public class ReadRmbService {

    private ImportRmbDao importRmbDao = Aop.get(ImportRmbDao.class);
    private Map<String, String> xbMap = importRmbDao.getDictNameToCode(XB_TYPE);
    private Map<String, String> mzMap = importRmbDao.getDictNameToCode(MZ_TYPE);
    Map<String, String> zzmmMap = importRmbDao.getDictNameToCode(ZZMM_TYPE);

    /**
     * lrm
     */
    public void readTxtLrm(File file, String impId, List<A01Temp> a01TempList, List<A36Temp> a36TempList) throws Exception {
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
        String a0000;
        // 在缓存中获取人员标识符
        String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
        Object wdyCache = CacheKit.get("wdyCache", fileName);
        if (wdyCache == null) {
            a0000 = StrKit.getRandomUUID();
            CacheKit.put("wdyCache", fileName, a0000);
        } else {
            a0000 = (String) wdyCache;
        }
        a01Temp.setImpId(impId);
        a01Temp.setA0000(a0000);
        int index = 0;
        // a36_temp
        String[] chengWei = new String[]{};
        String[] xingMing = new String[]{};
        String[] birthday = new String[]{};
        String[] zzMmName = new String[]{};
        String[] gzdwjzw = new String[]{};
        for (String value : split) {
            if (StrKit.notBlank(value)) {
                // 去掉每一项字段的引号
                value = value.replaceAll("\"", "");
            }
            if (index == 0) a01Temp.setA0101(value);
            else if (index == 1) a01Temp.setA0104(xbMap.get(value));
            else if (index == 2) a01Temp.setA0107(this.getXmlTime(value));
            else if (index == 3) a01Temp.setA0117(mzMap.get(value));
            else if (index == 4) a01Temp.setA0111A(value);
            else if (index == 5) a01Temp.setA0140(value);
            else if (index == 6) a01Temp.setA0128(value);
            else if (index == 7) a01Temp.setA0114A(value);
            else if (index == 8) a01Temp.setA0134(this.getXmlTime(value));
            else if (index == 9) {
                String[] xlXw = value.split("@");
                for (int i = 0; i < xlXw.length; i++) {
                    // 全日制教育
                    if (i == 0) {
                        String[] qrz = xlXw[i].split("#");
                        if (qrz.length == 1) {
                            if (xlXw[i].matches(".+#")) {
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
                        String[] zz = xlXw[1].split("#");
                        if (zz.length == 1) {
                            if (xlXw[i].matches(".+#")) {
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
            } else if (index == 10) {
                String[] xlXw = value.split("@");
                for (int i = 0; i < xlXw.length; i++) {
                    String[] byYx = xlXw[i].split("#");
                    // 全日制学历学位 毕业院校及专业
                    if (i == 0) {
                        if (byYx.length == 1) {
                            if (xlXw[i].matches(".+#")) {
                                a01Temp.setQRZXLXX(byYx[0]);
                            } else {
                                a01Temp.setQRZXWXX(byYx[0]);
                            }
                        } else if (byYx.length == 2) {
                            a01Temp.setQRZXLXX(byYx[0]);
                            a01Temp.setQRZXWXX(byYx[1]);
                        }
                        // 在职学历学位 毕业院校及专业
                    } else if (i == 1) {
                        if (byYx.length == 1) {
                            if (xlXw[i].matches(".+#")) {
                                a01Temp.setZZXLXX(byYx[0]);
                            } else {
                                a01Temp.setZZXWXX(byYx[0]);
                            }
                        } else if (byYx.length == 2) {
                            a01Temp.setZZXLXX(byYx[0]);
                            a01Temp.setZZXWXX(byYx[1]);
                        }
                    }
                }
            } else if (index == 11) a01Temp.setA0196(value);
            else if (index == 12) ;
            else if (index == 13) ;
            else if (index == 14) ;
            else if (index == 15) ;
            else if (index == 16) a01Temp.setA0187A(value);
            else if (index == 17) {
                if (StrKit.notBlank(value)) {
                    value = value.replaceAll("\n", "<br>");
                }
                a01Temp.setA1701(value);
            } else if (index == 18) a01Temp.setA14Z101(value);
            else if (index == 19) a01Temp.setA15Z101(value);
                // 家庭成员
            else if (index == 20) chengWei = value.split("@");
            else if (index == 21) xingMing = value.split("@");
            else if (index == 22) birthday = value.split("@");
            else if (index == 23) zzMmName = value.split("@");
            else if (index == 24) gzdwjzw = value.split("@");
            else if (index == 25) a01Temp.setA0192(value);
            else if (index == 26) a01Temp.setNRZW(value);
            else if (index == 27) a01Temp.setNMZW(value);
            else if (index == 28) a01Temp.setRMLY(value);
            else if (index == 29) a01Temp.setCBDW(value);
            else if (index == 30) a01Temp.setJSNLSJ(this.getXmlTime(value));
            else if (index == 31) a01Temp.setTBR(value);
            else if (index == 32) a01Temp.setTBSJ(this.getXmlTime(value));
            else if (index > 32) a01Temp.setA0184(value);
            index++;
        }
        a01TempList.add(a01Temp);

        // a36_temp
        for (int i = 0; i < chengWei.length; i++) {
            A36Temp a36Temp = new A36Temp();
            a36Temp.setImpId(impId);
            a36Temp.setA0000(a0000);
            a36Temp.setA3600(StrKit.getRandomUUID());
            a36Temp.setA3604A(chengWei[i]);
            a36Temp.setA3601(xingMing[i]);
            a36Temp.setA3607(this.getXmlTime(birthday[i]));
            String zzMmCode = zzmmMap.get(zzMmName[i]);
            a36Temp.setA3627(StrKit.isBlank(zzMmCode) ? zzMmName[i] : zzMmCode);
            a36Temp.setA3611(gzdwjzw[i]);
            a36Temp.setSORTID(i + 1);
            a36Temp.setA3699(1);
            a36TempList.add(a36Temp);
        }

    }

    /**
     * pic
     */
    public void readPic(File file, String impId, List<A57Temp> a57TempList) throws Exception {
        String path = file.getPath();
        String a0000;
        // 在缓存中获取人员标识符
        String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
        String wdyCache = CacheKit.get("wdyCache", fileName);
        if (StrKit.isBlank(wdyCache)) {
            a0000 = StrKit.getRandomUUID();
            CacheKit.put("wdyCache", fileName, a0000);
        } else {
            a0000 = wdyCache;
        }
        // 拷贝照片文件
        String toPath = PathKit.getWebRootPath() + NEW_PHOTOS_PATH + a0000 + ".jpg";
        File toFile = FileUtil.file(toPath);
        File parentFile = toFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        Files.copy(Paths.get(path), Paths.get(toPath), StandardCopyOption.REPLACE_EXISTING);
        // a57_temp
        A57Temp a57Temp = new A57Temp();
        a57Temp.setImpId(impId);
        a57Temp.setA0000(a0000);
        a57Temp.setA5714(toFile.getName());
        a57Temp.setUPDATED("1");
        a57Temp.setPHOTODATA(ImageBase64Util.imgToB64(toPath));
        a57Temp.setPHOTONAME(toFile.getName());
        a57Temp.setPHOTSTYPE(".jpg");

        a57TempList.add(a57Temp);
    }

    /**
     * lrmx
     */
    public void readXmlLrmx(File file, String impId, List<A01Temp> a01TempList, List<A36Temp> a36TempList, List<A57Temp> a57TempList) throws Exception {
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
        // 家庭成员
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
                a36Temp.setA3627(StrKit.isBlank(zzmmCode) ? zhengZhiMianMao : zzmmCode);
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
        String toPath = NEW_PHOTOS_PATH + a0000 + ".jpg";
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
        // 清除空值
        this.setEmptyValue(a01Temp);
        // 返回结果
        a01TempList.add(a01Temp);
        a57TempList.add(a57Temp);
    }

    /**
     * 设置字符串空值
     */
    private void setEmptyValue(Model model) {
        Set<String> columnNameSet = TableMapping.me().getTable(model.getClass()).getColumnNameSet();
        Map<String, Class<?>> typeMap = TableMapping.me().getTable(model.getClass()).getColumnTypeMap();
        columnNameSet.forEach(column -> {
            Class<?> aClass = typeMap.get(column);
            if (aClass.equals(String.class)) {
                Object o = model.get(column);
                if (o != null && StrKit.isBlank(o.toString())) {
                    model.set(column, null);
                }
            }
        });
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
            } else {
                return null;
            }
        } catch (Exception e) {
            LogKit.error("时间格式错误:" + value, e);
        }
        return null;
    }

}
