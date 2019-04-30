package com.wdy.bizz.org;

import com.alibaba.fastjson.JSONObject;
import com.wdy.biz.org.service.OrgService;
import com.wdy.bizz.TestWdyConfig;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wgch
 * @Description
 * @date 2019/4/29 18:33
 */
public class TestOrgService extends TestWdyConfig {

    /**
     * 测试管理机构
     */
    @Test
    public void testManageOrg() {
        OrgService orgService = new OrgService();
        JSONObject ob = new JSONObject();
        ob.put("orgId", "0ebdcd6d7a914478b77d1a7fe6f8d8c6");
        ob.put("isReadOnly", 1);
        List<JSONObject> list = new ArrayList<>();
        list.add(ob);
        List<String> orgCodes = orgService.listOrgCode(list);
        System.out.println(orgCodes);

    }

}
