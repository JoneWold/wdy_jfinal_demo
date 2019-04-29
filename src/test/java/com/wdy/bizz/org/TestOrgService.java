package com.wdy.bizz.org;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.dialect.PostgreSqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.wdy.biz.org.service.OrgService;
import com.wdy.generator.postgreSQL.model._MappingKit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.wdy.config.constant.Constant.DB_MySQL;
import static com.wdy.config.constant.Constant.DB_PGSQL;

/**
 * @author wgch
 * @Description
 * @date 2019/4/29 18:33
 */
public class TestOrgService {
    /**
     * 初始化
     */
    @BeforeClass
    public static void init() {
        Prop p = PropKit.use("jfinal.properties");
        // mysql
        DruidPlugin mysqldp = new DruidPlugin(p.get("jfinal.MySQL.jdbcUrl"), p.get("jfinal.MySQL.user"), p.get("jfinal.MySQL.password").trim());
        ActiveRecordPlugin arpMysql = new ActiveRecordPlugin(DB_MySQL, mysqldp);
        com.wdy.generator.mysql.model._MappingKit.mapping(arpMysql);
        arpMysql.addSqlTemplate("sql/jfinal_demo.sql");
        arpMysql.setShowSql(true);
        mysqldp.start();
        arpMysql.start();
        // pg
        DruidPlugin dp = new DruidPlugin(
                p.get("jfinal.postgreSQL.url"), p.get("jfinal.postgreSQL.user"),
                p.get("jfinal.postgreSQL.password"), p.get("jfinal.postgreSQL.driverClass"));
        ActiveRecordPlugin arp = new ActiveRecordPlugin(DB_PGSQL, dp);
        arp.setShowSql(true);
        arp.setDialect(new PostgreSqlDialect());
        arp.addSqlTemplate("sql/jfinal_demo.sql");
        _MappingKit.mapping(arp);
        dp.start();
        arp.start();
        EhCachePlugin ehCachePlugin = new EhCachePlugin();
        ehCachePlugin.start();
    }

    @AfterClass
    public static void stop() {
        DbKit.removeConfig(DB_PGSQL);
    }

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
