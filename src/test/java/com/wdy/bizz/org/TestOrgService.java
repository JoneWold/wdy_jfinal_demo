package com.wdy.bizz.org;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Aop;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.TableMapping;
import com.wdy.biz.org.service.OrgService;
import com.wdy.bizz.TestBeforeWdyConfig;
import com.wdy.generator.postgreSQL.model.B01;
import com.wdy.vo.XmlDataCheckVo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static com.wdy.biz.wdy.WdyMain.USER_CONTEXT;
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

    @Test
    public void testField() {
        Record record = new Record().set("id", "123").set("name", "张三");
        Set<String> columnNameSet = TableMapping.me().getTable(B01.class).getColumnNameSet();
        this.setRecordField(record, columnNameSet);
        System.out.println(record);
    }

    /***
     * 字段填充
     * @param newRecord         xml读取的数据
     * @param tempColumnNameSet temp表中的字段集合
     */
    private void setRecordField(Record newRecord, Set<String> tempColumnNameSet) {
        List<String> columnNames = Arrays.asList(newRecord.getColumnNames());
        tempColumnNameSet.forEach(e -> {
            if (!columnNames.contains(e)) {
                newRecord.set(e, null);
            }
        });
    }

    // java对象的引用包括 ： 强引用，软引用，弱引用，虚引用 。

    /**
     * ThreadLocalMap：
     * 弱引用也是用来描述非必需对象的，当JVM进行垃圾回收时，无论内存是否充足，该对象仅仅被弱引用关联，那么就会被回收。当仅仅只有ThreadLocalMap中的Entry的key指向ThreadLocal的时候，ThreadLocal会进行回收的！！！
     * ThreadLocal被垃圾回收后，在ThreadLocalMap里对应的Entry的键值会变成null，但是Entry是强引用，那么Entry里面存储的Object，并没有办法进行回收，所以ThreadLocalMap 存在内存泄露的风险。
     * 所以最佳实践，应该在我们不使用的时候，主动调用remove方法进行清理。
     */
    @Test
    public void testUserContext() {
        // InheritableThreadLocal
        USER_CONTEXT.set("wdy");
        System.out.println(USER_CONTEXT.get());

        ThreadLocal<String> localName = new ThreadLocal<>();
        localName.set("name1");
        String name = localName.get();
        System.out.println(name);
        //每个Thread对象内部都有一个ThreadLoacalMap的成员变量，这个变量类似一个Map类型，其中key为我们定义的ThreadLocal变量的this引用，value则为我们使用set方法设置的值；
        //如果线程不消亡，在ThreadLocalMap中存放的ThreadLocal实例对象可能一直不会清除，所以当我们不需要在使用ThreadLocal的值时，就应该手动调用remove方法清除该值。
    }


}
