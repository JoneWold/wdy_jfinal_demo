package com.wdy.bizz.org;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Aop;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wdy.biz.org.service.OrgService;
import com.wdy.bizz.TestBeforeWdyConfig;
import com.wdy.vo.XmlDataCheckVo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.wdy.constant.DBConstant.DB_PGSQL;

/**
 * @author wgch
 * @Description
 * @date 2019/4/29 18:33
 */
public class TestOrgService extends TestBeforeWdyConfig {

    private OrgService orgService = Aop.get(OrgService.class);

    /**
     * 测试管理机构
     */
    @Test
    public void testManageOrg() {
        JSONObject ob = new JSONObject();
        ob.put("orgId", "0ebdcd6d7a914478b77d1a7fe6f8d8c6");
        ob.put("isReadOnly", 1);
        List<JSONObject> list = new ArrayList<>();
        list.add(ob);
        List<String> orgCodes = orgService.listOrgCode(list);
        System.out.println(orgCodes);

    }

    @Test
    public void testCheck() {
        String impId = "2df339691aca450ebaff75e47a0a52f0";
        List<Record> b01TempData = Db.use(DB_PGSQL).find("select * from \"b01_temp\" where \"impId\"=?", impId);
        List<Record> a01TempData = Db.use(DB_PGSQL).find("select * from \"a01_temp\" where \"impId\"=?", impId);
        XmlDataCheckVo vo = orgService.checkTempData(b01TempData, a01TempData, "长江县人民政府", "E09.Q19.V09", "1");
        System.out.println(vo);
    }

}
