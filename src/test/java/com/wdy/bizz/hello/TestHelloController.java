package com.wdy.bizz.hello;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.dialect.PostgreSqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.wdy.biz.hello.service.HelloService;
import com.wdy.common.utils.OutMessage;
import com.wdy.generator.postgreSQL.model._MappingKit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.wdy.config.constant.Constant.DB_MySQL;
import static com.wdy.config.constant.Constant.DB_PGSQL;
import static com.wdy.config.constant.Status.SUCCESS;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author wgch
 * @Description
 * @date 2019/3/28 10:35
 */
public class TestHelloController {

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
     * 测试方法
     */
    @Test
    public void testGetPostgreSQLList() {
        HelloService hello = new HelloService();
        OutMessage message = hello.getPostgreSQLList();
        System.out.println(message);
        assertThat(message.getCode(), is(SUCCESS.getCode()));
    }

    /**
     * 字符串 转 时间格式
     */
    @Test
    public void testStrToDate() {
        String format = DateUtil.format(DateUtil.parse("2019-04-11", "yyyy-MM-dd"), "yyyyMM");
        System.out.println(format);

        // 字符串格式 转 时间格式
        DateTime parse = DateUtil.parse("20190416");
        System.out.println(parse);
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-04-11 12:12:00");
            System.out.println(date);
            String yyyyMM1 = new SimpleDateFormat("yyyyMM").format(date);
            System.out.println(yyyyMM1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testJoin() {
        List<String> list = new ArrayList();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        System.out.println(list);
        String join = CollectionUtil.join(list, " AND ");
        System.out.println(join);
    }

}
