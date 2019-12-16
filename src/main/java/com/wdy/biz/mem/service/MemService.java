package com.wdy.biz.mem.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.jfinal.aop.Aop;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;
import com.wdy.biz.mem.dao.MemDao;
import com.wdy.dto.A08MergeDegreeDto;
import com.wdy.generator.postgreSQL.model.A08;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wgch
 * @Description 人员管理
 * @date 2019/12/16
 */
public class MemService {

    private MemDao dao = Aop.get(MemDao.class);

    /**
     * 最高学历标识 等 6 个标识 多人的情况下处理
     */
    public void updateA0834Many(List<String> a0000s) {
        // 重置学历学位标识
        dao.updateA083124589(a0000s);
        // 最高学历
        dao.updateA0834(a0000s);
        // 最高学位
        dao.updateA0835(a0000s);
        // 最高全日制学历
        dao.updateA0831(a0000s);
        // 最高全日制学位
        dao.updateA0832(a0000s);
        // 最高在职学历
        dao.updateA0838(a0000s);
        // 最高在职学位
        dao.updateA0839(a0000s);
    }

    /**
     * 组合 学历学位
     *
     * @param a0000s 人员标识符集合
     * @param impId  temp表标识
     * @return
     * @versions 2.0
     */
    public Map<String, A08MergeDegreeDto> mergeDegreeInfo(List<String> a0000s, String impId) {
        Map<String, A08MergeDegreeDto> result = new HashMap<>();
//        List<String> idList = new ArrayList<>();
//        if (StrKit.notBlank(a0000str)) {
//            String[] split = a0000str.replaceAll("'", "").split(",");
//            idList = Arrays.asList(split);
//        }
        Map<String, List<Record>> map = dao.findA08ByA0000s(a0000s, impId);
        for (Map.Entry<String, List<Record>> entry : map.entrySet()) {
            String key = entry.getKey();
            List<Record> records = entry.getValue();
            // 组合结果
            A08MergeDegreeDto mcDegree = this.mergeMCDegreeInfo(records);
            A08MergeDegreeDto rmbDegree = this.mergeRMBDegreeInfo(records);
            mcDegree.setQRZXL(rmbDegree.getQRZXL());
            mcDegree.setQRZXLXX(rmbDegree.getQRZXLXX());
            mcDegree.setQRZXW(rmbDegree.getQRZXW());
            mcDegree.setQRZXWXX(rmbDegree.getQRZXWXX());
            mcDegree.setZZXL(rmbDegree.getZZXL());
            mcDegree.setZZXLXX(rmbDegree.getZZXLXX());
            mcDegree.setZZXW(rmbDegree.getZZXW());
            mcDegree.setZZXWXX(rmbDegree.getZZXWXX());
            result.put(key, mcDegree);
        }
        return result;
    }

    /**
     * 组合名册 学历学位
     *
     * @version 2.0
     */
    private A08MergeDegreeDto mergeMCDegreeInfo(List<Record> a08List) {
        StringBuilder qrzBuffer = new StringBuilder();
        StringBuilder zzBuffer = new StringBuilder();
        Map<String, String> xldmMap = new LinkedHashMap<>();
        Map<String, String> xwdmMap = new LinkedHashMap<>();
        A08MergeDegreeDto mcDto = new A08MergeDegreeDto();
        for (Record record : a08List) {
            A08 a08 = new A08();
            a08.put(record);
            // 输出
            if ("1".equals(a08.getA0898())) {
                String value = "";
                // 入学时间
                Date a0804 = null;
                if (ObjectUtil.isNotNull(a08.get("A0804"))) {
                    a0804 = DateUtil.parse(a08.get("A0804").toString().substring(0, 10));
                }
                String a0804Str = "";
                String a0807Str = "";
                if (ObjectUtil.isNotNull(a0804)) {
                    a0804Str = new SimpleDateFormat("yyyy.MM").format(a0804);
                }
                // 毕业时间
                Date a0807 = null;
                if (ObjectUtil.isNotNull(a08.get("A0807"))) {
                    a0807 = DateUtil.parse(a08.get("A0807").toString().substring(0, 10));
                }
                if (ObjectUtil.isNotNull(a0807)) {
                    a0807Str = new SimpleDateFormat("yyyy.MM").format(a0807);
                }
                // 毕业院校
                String a0814 = StrKit.isBlank(a08.getA0814()) ? "" : a08.getA0814();
                // 所学专业
                String a0824 = StrKit.isBlank(a08.getA0824()) ? "" : a08.getA0824();
                // 学历名称
                String a0801A = StrKit.isBlank(a08.getA0801A()) ? "" : a08.getA0801A();
                // 学位名称
                String a0901A = StrKit.isBlank(a08.getA0901A()) ? "" : a08.getA0901A();
                // 学历学位代码
                String a0801B = a08.getA0801B();
                String a0901B = a08.getA0901B();
                String xldm = "";
                if (StrKit.notBlank(a0801B)) {
                    xldm = xldmMap.get(a0801B);
                }
                // 学历 不为空，跟之前的学历代码不一致
                if (StrKit.notBlank(a0801B) && !a0801B.equals(xldm)) {
                    // 学位 不为空
                    if (StrKit.notBlank(a0901B)) {
                        value = a0804Str + "--" + a0807Str + "  " + a0814 + a0824 + "\n" + a0801A + "、" + a0901A;
                    } else {
                        value = a0804Str + "--" + a0807Str + "  " + a0814 + a0824 + "\n" + a0801A;
                    }
                    // 单学历多学位
                } else if (xldm.equals(a0801B)) {
                    if (StrKit.notBlank(a0901B)) {
                        value = a0804Str + "--" + a0807Str + "  " + a0814 + a0824 + "\n" + a0901A;
                    }
                }
                // 学历为空
                if (StrKit.notBlank(a0901B) && StrKit.isBlank(a0801B)) {
                    value = a0804Str + "--" + a0807Str + "  " + a0814 + a0824 + "\n" + a0901A;
                }

                // 只有学历学位信息
                if (StrKit.isBlank(a0804Str) || StrKit.isBlank(a0807Str)) {
                    if (StrKit.isBlank(a0901B)) {
                        value = a0814 + a0824 + "\n" + a0801A;
                    } else {
                        value = a0814 + a0824 + "\n" + a0801A + "、" + a0901A;
                    }
                }
                // 学历学位代码
                xldmMap.put(a0801B, a0801B);
                xwdmMap.put(a0901B, a0901B);
                // 全日制
                if ("1".equals(a08.getA0837())) {
                    qrzBuffer.append(value + "\n");
                } else {
                    zzBuffer.append(value + "\n");
                }
                mcDto.setQRZZS(qrzBuffer.toString());
                mcDto.setZZZS(zzBuffer.toString());
            }
        }
        return mcDto;
    }


    /**
     * 组合任免表 学历学位
     *
     * @version 2.1
     */
    private A08MergeDegreeDto mergeRMBDegreeInfo(List<Record> a08List) {
        // 全日制学历学位
        StringBuilder qrzXLBuffer = new StringBuilder();
        StringBuilder qrzXLXXBuffer = new StringBuilder();
        StringBuilder qrzXWBuffer = new StringBuilder();
        StringBuilder qrzXWXXBuffer = new StringBuilder();
        // 在职学历学位
        StringBuilder zzXLBuffer = new StringBuilder();
        StringBuilder zzXLXXBuffer = new StringBuilder();
        StringBuilder zzXWBuffer = new StringBuilder();
        StringBuilder zzXWXXBuffer = new StringBuilder();

        // 学历代码
        List<String> a0801BList = new ArrayList<>(a08List.size());
        // 全日制学历
        for (int i = 0; i < a08List.size(); i++) {
            A08 a08 = new A08();
            a08.put(a08List.get(i));
            if ("1".equals(a08.getA0898()) && "1".equals(a08.getA0837())) {
                String a0801B = a08.getA0801B();
                if (StrKit.notBlank(a0801B)) {
                    if (i == 0 || !a0801BList.contains(a0801B)) {
                        a0801BList.add(a0801B);
                        // 毕业院校
                        String a0814 = a08.getA0814() == null ? "" : a08.getA0814();
                        // 所学专业
                        String a0824 = a08.getA0824() == null ? "" : a08.getA0824();
                        // 学历名称
                        String a0801A = a08.getA0801A() == null ? "" : a08.getA0801A();

                        qrzXLBuffer.append(a0801A).append("、");
                        qrzXLXXBuffer.append(a0814).append(a0824).append("、");
                    }
                }
            }
        }

        List<String> a0901BList = new ArrayList<>(a0801BList.size());
        // 全日制学位
        for (int i = 0; i < a08List.size(); i++) {
            A08 a08 = new A08();
            a08.put(a08List.get(i));
            if ("1".equals(a08.getA0898()) && "1".equals(a08.getA0837())) {
                String a0901B = a08.getA0901B();
                if (StrKit.notBlank(a0901B)) {
                    if (i == 0 || !a0901BList.contains(a0901B)) {
                        a0901BList.add(a0901B);
                        String a0901Axw = a08.getA0901A() == null ? "" : a08.getA0901A();
                        String a0814xw = a08.getA0814() == null ? "" : a08.getA0814();
                        String a0824xw = a08.getA0824() == null ? "" : a08.getA0824();
                        String qrzXwXx = a0814xw + "" + a0824xw + "、";
                        qrzXWBuffer.append(a0901Axw).append("、");
                        qrzXWXXBuffer.append(qrzXwXx);
                    }
                }
            }
        }

        List<String> zzA0801BList = new ArrayList<>();
        // 在职学历
        for (int i = 0; i < a08List.size(); i++) {
            A08 a08 = new A08();
            a08.put(a08List.get(i));
            if ("1".equals(a08.getA0898()) && "0".equals(a08.getA0837())) {
                String zzA0801B = a08.getA0801B();
                if (StrKit.notBlank(zzA0801B)) {
                    if (i == 0 || !zzA0801BList.contains(zzA0801B)) {
                        // 毕业院校
                        String a0814zz = a08.getA0814() == null ? "" : a08.getA0814();
                        // 所学专业
                        String a0824zz = a08.getA0824() == null ? "" : a08.getA0824();
                        // 学历名称
                        String a0801Azz = a08.getA0801A() == null ? "" : a08.getA0801A();
                        zzXLBuffer.append(a0801Azz).append("、");
                        zzXLXXBuffer.append(a0814zz).append(a0824zz).append("、");
                        zzA0801BList.add(zzA0801B);
                    }
                }
            }
        }

        List<String> zzA0901BList = new ArrayList<>(a08List.size());
        // 在职学位
        for (int i = 0; i < a08List.size(); i++) {
            A08 a08 = new A08();
            a08.put(a08List.get(i));
            if ("1".equals(a08.getA0898()) && "0".equals(a08.getA0837())) {
                String a0901B = a08.getA0901B();
                if (StrKit.notBlank(a0901B)) {
                    if (i == 0 || !zzA0901BList.contains(a0901B)) {
                        zzA0901BList.add(a0901B);
                        String a0901Axw = a08.getA0901A() == null ? "" : a08.getA0901A();
                        zzXWBuffer.append(a0901Axw).append("、");
                        String a0814xw = a08.getA0814() == null ? "" : a08.getA0814();
                        String a0824xw = a08.getA0824() == null ? "" : a08.getA0824();
                        String zzXwXx = a0814xw + "" + a0824xw + "、";
                        zzXWXXBuffer.append(zzXwXx);
                    }
                }
            }
        }
        A08MergeDegreeDto rmbDto = new A08MergeDegreeDto();
        String qrzXL = qrzXLBuffer.toString();
        String qrzXLXX = qrzXLXXBuffer.toString();
        if (qrzXL.length() > 0) {
            qrzXL = qrzXL.substring(0, qrzXL.length() - 1);
        }
        if (qrzXLXX.length() > 0) {
            qrzXLXX = qrzXLXX.substring(0, qrzXLXX.length() - 1);
        }
        rmbDto.setQRZXL(qrzXL);
        rmbDto.setQRZXLXX(qrzXLXX);
        String qrzXW = qrzXWBuffer.toString();
        String qrzXWXX = qrzXWXXBuffer.toString();
        if (qrzXW.length() > 0) {
            qrzXW = qrzXW.substring(0, qrzXW.length() - 1);
        }
        if (qrzXWXX.length() > 0) {
            qrzXWXX = qrzXWXX.substring(0, qrzXWXX.length() - 1);
        }
        if (qrzXWXX.equals(qrzXLXX)) {
            qrzXWXX = "";
        }
        rmbDto.setQRZXW(qrzXW);
        rmbDto.setQRZXWXX(qrzXWXX);

        String zzXL = zzXLBuffer.toString();
        if (zzXL.length() > 1) {
            zzXL = zzXL.substring(0, zzXL.length() - 1);
        }
        String zzXLXX = zzXLXXBuffer.toString();
        if (zzXLXX.length() > 1) {
            zzXLXX = zzXLXX.substring(0, zzXLXX.length() - 1);
        }
        rmbDto.setZZXL(zzXL);
        rmbDto.setZZXLXX(zzXLXX);

        String zzXW = zzXWBuffer.toString();
        String zzXWXX = zzXWXXBuffer.toString();
        if (zzXW.length() > 0) {
            zzXW = zzXW.substring(0, zzXW.length() - 1);
        }
        if (zzXWXX.length() > 0) {
            zzXWXX = zzXWXX.substring(0, zzXWXX.length() - 1);
        }
        if (zzXWXX.equals(zzXLXX)) {
            zzXWXX = "";
        }
        // 同学校学历学位
        if (zzXWXX.equals(zzXLXXBuffer.toString())) {
            zzXWXX = "";
        }
        if (qrzXWXX.equals(zzXWXX) && StrKit.notBlank(qrzXWXX)) {
            zzXWXX = "";
        }
        rmbDto.setZZXW(zzXW);
        rmbDto.setZZXWXX(zzXWXX);
        return rmbDto;
    }

}
