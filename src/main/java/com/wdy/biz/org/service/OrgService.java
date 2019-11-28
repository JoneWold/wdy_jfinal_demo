package com.wdy.biz.org.service;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Aop;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;
import com.wdy.biz.org.dao.OrgDao;
import com.wdy.generator.postgreSQL.model.B01;
import com.wdy.vo.XmlDataCheckSubVo;
import com.wdy.vo.XmlDataCheckVo;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wgch
 * @Description 机构管理
 * @date 2019/4/23 11:23
 */
public class OrgService {

    private OrgDao orgDao = Aop.get(OrgDao.class);

    /**
     * 管理机构
     *
     * @param currOrgArray
     * @return
     */
    public List<String> listOrgCode(List<JSONObject> currOrgArray) {
        List<String> userOrgs = new ArrayList<>();
        List<String> tempOrgs = new ArrayList<>();
        String minOrgCode = null;
        for (JSONObject jsonObject : currOrgArray) {
            String orgId = jsonObject.getString("orgId");
            B01 b01 = orgDao.findById(orgId);
            tempOrgs.add(b01.getB0111());
            if (StrUtil.isEmpty(minOrgCode)) {
                minOrgCode = b01.getB0111();
            } else {
                if (b01.getB0111().length() < minOrgCode.length()) {
                    minOrgCode = b01.getB0111();
                }
            }
        }

        for (String tempOrg : tempOrgs) {
            if (!tempOrg.startsWith(minOrgCode)) {
                userOrgs.add(tempOrg);
            }
        }
        userOrgs.add(minOrgCode);
        return userOrgs;
    }

    /**
     * @param currOrgArray
     * @return
     */
    public List<String> currManageOrg(List<JSONObject> currOrgArray) {
        // 所有管理层级
        List<String> midList = new ArrayList<>();
        for (JSONObject jsonObject : currOrgArray) {
            String currOrgId = jsonObject.getString("orgId");
            B01 org = orgDao.findById(currOrgId);
            String b0111 = org.getB0111();
            midList.add(b0111);
        }
        List<String> orgCodeList = new ArrayList<>();
        // 合并相同层级，不同层级的层级码
        for (String midCode : midList) {
            if (orgCodeList.size() == 1) {
                orgCodeList.add(midCode);
            }
            for (String orgCode : orgCodeList) {
                // 相同层级
                if (orgCode.startsWith(midCode)) {
                    orgCodeList.remove(orgCode);
                    orgCodeList.add(midCode);
                } else {
                    orgCodeList.add(midCode);
                }
            }
        }
        return orgCodeList;
    }


    /**
     * 验证功能 1.机构编码 2.身份证 3.顶层机构编码
     */
    public XmlDataCheckVo checkTempData(List<Record> b01TempData, List<Record> a01TempData, String tB0104, String tB0114, String userId) {
        XmlDataCheckVo checkVo = new XmlDataCheckVo();
        // 机构编码校验
        List<XmlDataCheckSubVo> orgVoList = new ArrayList<>();
        // 按层级码升序
        List<Record> tempList = b01TempData.stream().sorted(Comparator.comparing(e -> e.getStr("B0111"), (x1, x2) -> {
            if (x1.length() < x2.length()) {
                return -1;
            } else if (x1.length() == x2.length()) {
                return x1.compareTo(x2);
            } else {
                return 2;
            }
        })).collect(Collectors.toList());
        boolean flag = false;
        boolean isShow = true;
        //选择导入的根节点重庆市（E09）与数据包子节点XXX（E09）机构编码相同，请选择与数据包顶级节点相同的单位导入。
        tB0104 = StrUtil.isEmpty(tB0104) ? " " : tB0104;
        StringBuilder sameB0114 = new StringBuilder("选择导入的根节点").append(tB0104).append("（").append(tB0114).append("）与数据包子节点");
        int i = 0;
        for (Record record : tempList) {
            String b0114 = record.getStr("B0114");
            if (StrKit.isBlank(b0114)) {
                orgVoList.add(this.getSubVo(record, "机构编码-字段为空", "1"));
            }
            String b0101 = record.getStr("B0101");
            if (i != 0) {
                if (StrUtil.equalsAny(tB0114, b0114)) {
                    sameB0114.append(b0101).append(" ");
                    flag = true;
                }
            }
            i++;
        }
        //机构编码重复  机构编码与 张三 李四 机构编码重复（E09.001）
        Map<String, List<Record>> b0114Map = tempList.stream().collect(Collectors.groupingBy(e -> e.getStr("B0114")));
        for (Map.Entry<String, List<Record>> entry : b0114Map.entrySet()) {
            List<Record> value = entry.getValue();
            StringBuilder message = new StringBuilder("机构编码与 ");
            if (value.size() > 1) {
                Record record = new Record();
                for (int k = 0; k < value.size(); k++) {
                    record = value.get(0);
                    if (k != 0) {
                        message.append(value.get(k).getStr("B0101")).append(" ");
                    }
                    if (k == value.size() - 1) {
                        String b0114 = record.getStr("B0114");
                        message.append("机构编码重复（").append(StrKit.isBlank(b0114) ? "无" : b0114).append("）");
                    }
                }
                orgVoList.add(this.getSubVo(record, message.toString(), "1"));
                isShow = false;
            }
        }
        sameB0114.append("（").append(tB0114).append("）").append("机构编码相同，请选择与数据包顶级节点相同的单位导入。");
        // 数据包中没有重复机构编码，再看选择的节点单位编码是否是数据包的子节点
        if (flag && isShow) {
            checkVo.setSameB0114(sameB0114.toString());
        }
        // 身份证校验
        List<XmlDataCheckSubVo> memVoList = new ArrayList<>();
        a01TempData.parallelStream().forEach(record -> {
            String a0184 = record.getStr("A0184");
            if (StrKit.isBlank(a0184)) {
                memVoList.add(this.getSubVo(record, "身份证号-字段为空", "2"));
            }
        });
        // 身份证号与 张三 李四 身份证重复（00000）
        Map<String, List<Record>> a0184Map = a01TempData.stream().collect(Collectors.groupingBy(e -> e.getStr("A0184")));
        for (Map.Entry<String, List<Record>> entry : a0184Map.entrySet()) {
            List<Record> value = entry.getValue();
            StringBuilder message = new StringBuilder("身份证号与 ");
            if (value.size() > 1) {
                Record record = new Record();
                for (int k = 0; k < value.size(); k++) {
                    record = value.get(0);
                    if (k != 0) {
                        message.append(value.get(k).getStr("A0101")).append(" ");
                    }
                    if (k == value.size() - 1) {
                        String a0184 = record.getStr("A0184");
                        message.append("身份证重复（").append(StrKit.isBlank(a0184) ? "无" : a0184).append("）");
                    }
                }
                memVoList.add(this.getSubVo(record, message.toString(), "2"));
            }
        }
        checkVo.setOrgList(orgVoList);
        checkVo.setMemList(memVoList);
        return checkVo;
    }

    private XmlDataCheckSubVo getSubVo(Record record, String message, String type) {
        XmlDataCheckSubVo subVo = new XmlDataCheckSubVo();
        subVo.setId(StrKit.getRandomUUID());
        if ("1".equals(type)) {
            subVo.setOrgId(record.getStr("id"));
            subVo.setOrgName(record.getStr("B0101"));
            subVo.setShortName(record.getStr("B0104"));
        } else {
            subVo.setA0000(record.getStr("A0000"));
            subVo.setA0165(record.getStr("A0165"));
            subVo.setName(record.getStr("A0101"));
            subVo.setCardID(record.getStr("A0184"));
            subVo.setA0192A(record.getStr("A0192A"));
        }
        subVo.setErrorMessage(message);
        subVo.setCheckTime(new Date());
        return subVo;
    }

}
