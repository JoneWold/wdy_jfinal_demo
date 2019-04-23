package com.wdy.biz.org.controller;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wgch
 * @Description
 * @date 2019/4/23 11:23
 */
public class OrgController {


    /**
     * 管理机构
     *
     * @param currOrgArray
     * @return
     */
    public List<String> listOrgCode(List<JSONObject> currOrgArray) {
        List<String> userOrgs = new ArrayList<>();
//        List<String> tempOrgs = new ArrayList<>();
//        String minOrgCode = null;
//        for (JSONObject jsonObject : currOrgArray) {
//            String orgId = jsonObject.getString("orgId");
//            B01 b01 = orgDao.findById(orgId);
//            tempOrgs.add(b01.getB0111());
//            if (StrUtil.isEmpty(minOrgCode)) {
//                minOrgCode = b01.getB0111();
//            } else {
//                if (b01.getB0111().length() < minOrgCode.length()) {
//                    minOrgCode = b01.getB0111();
//                }
//            }
//        }
//
//        for (String tempOrg : tempOrgs) {
//            if (!tempOrg.startsWith(minOrgCode)) {
//                userOrgs.add(tempOrg);
//            }
//        }
//        userOrgs.add(minOrgCode);
        return userOrgs;
    }
}
