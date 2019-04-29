package com.wdy.biz.org.service;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Aop;
import com.wdy.biz.org.dao.OrgDao;
import com.wdy.generator.postgreSQL.model.B01;

import java.util.ArrayList;
import java.util.List;

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


}
