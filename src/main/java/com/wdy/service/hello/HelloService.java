package com.wdy.service.hello;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.wdy.generator.mysql.model.Blog;
import com.wdy.generator.mysql.model.SysUser;
import com.wdy.generator.postgreSQL.model.Drop;

import java.sql.SQLException;
import java.util.List;

import static com.wdy.config.constant.Constant.DB_MySQL;
import static com.wdy.config.constant.Constant.DB_PGSQL;

/**
 * Created by wgch on 2019/3/4.
 */
public class HelloService {

    private Blog blog = new Blog();
    private SysUser dao_user = new SysUser().dao();
    private Drop drop = new Drop().use(DB_PGSQL).dao();

    public List<Blog> getBlogList() {
        List<Blog> ret = blog.use(DB_MySQL).find("select * from blog");
        System.out.println("service in......");
        return ret;
    }

    public Page<SysUser> getUserPage(int pageNummber, int pageSize) {
//        Page<SysUser> page = dao_user.paginate(pageNummber, pageSize, "select id,name ", " from sys_user");

        // Sql 模板管理实现分页功能
        SqlPara sqlPara = Db.getSqlPara("mysql.getUserPage");
        Page<SysUser> page = dao_user.paginate(pageNummber, pageSize, sqlPara);
        System.err.println("tx--->>" + succeed);
        return page;
    }

    // 事务处理：同时操作多张表
    boolean succeed = Db.tx(new IAtom() {

        @Override
        public boolean run() throws SQLException {
            int count = Db.update("update sys_user set name = ? where id = ?", "jfinal_1", 52);
            int count2 = Db.update("update sys_user set name = ? where id = ?", "jfinal_2", 53);
            return count == 1 && count2 == 1;
        }
    });

    // Ehcache 缓存 ？？？
    public List<Blog> cache() {
        List<Blog> blogList = blog.findByCache("WdyCache22", "wkey22", "select * from blog");
        return blogList;
    }

    // 多数据源配置
    public List<Drop> getPostgreSQLList() {
        // TODO 只有对象才能调用 use() 方法指定数据源，而dao只能用于查询。
//        List<Record> drops = Db.use(DB_PGSQL).find("select * from SysUser");
        List<Drop> drops = drop.find("select * from SysUser");

        String findDrop = drop.getSql("findSysUser");
        List<Drop> drops2 = drop.find(findDrop);

        return drops2;
    }

    // sql 模板
    public List<Blog> getBlogList2() {
        // 对象.find() 里面带值参数，sql里面占位符是 ?
        String sql = blog.getSql("mysql.findBlog");
        List<Blog> records1 = blog.find(sql, 2);

        // #para 指令 方式一：传入 int型常量参数，如 #para(0)
        SqlPara sqlPara = blog.getSqlPara("mysql.findBlog2", 3);
        List<Blog> records2 = blog.find(sqlPara);

        // #para 指令 方式二：传入除了 int 型常量以外的任意类型参数，Map data参数
        Kv cond = Kv.by("id", 3).set("title", "test");
//        Kv cond = Kv.by("title", "test").set("id", 3);
        SqlPara sp = blog.getSqlPara("mysql.findBlog3", cond);
        List<Blog> records3 = blog.find(sp);

        return records3;
    }


}
