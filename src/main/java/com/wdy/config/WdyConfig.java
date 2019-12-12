package com.wdy.config;

import com.jfinal.config.*;
import com.jfinal.core.Const;
import com.jfinal.core.paragetter.ParaProcessorBuilder;
import com.jfinal.json.FastJsonFactory;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.dialect.PostgreSqlDialect;
import com.jfinal.plugin.cron4j.Cron4jPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.template.Engine;
import com.jfinal.template.source.ClassPathSourceFactory;
import com.wdy.biz.dictionary.service.DictionaryService;
import com.wdy.biz.file.rmb.ImportRmbController;
import com.wdy.biz.progress.jfinal.MobSdController;
import com.wdy.biz.progress.websocket.AlarmThreadController;
import com.wdy.biz.progress.websocket.WebSocketController;
import com.wdy.biz.wdy.JaxRsController;
import com.wdy.generator.mysql.model._MappingKit;
import com.wdy.interceptor.ParameterInterceptor;
import com.wdy.interceptor.jfinal.AdminRoutes;
import com.wdy.message.InMessage;
import com.wdy.message.InMessageParaGetter;
import com.wdy.message.OutMessageInterceptor;
import live.autu.plugin.jfinal.swagger.config.SwaggerPlugin;
import live.autu.plugin.jfinal.swagger.config.routes.SwaggerRoutes;
import live.autu.plugin.jfinal.swagger.model.SwaggerApiInfo;
import live.autu.plugin.jfinal.swagger.model.SwaggerDoc;

/**
 * Created by wgch on 2019/3/5.
 */
public class WdyConfig extends JFinalConfig {
    static Prop p;

    /**
     * 数据库插件
     */
    private DruidPlugin mysqldp;
    private ActiveRecordPlugin arpMysql;

    private DruidPlugin postgreSQLdp;
    private ActiveRecordPlugin arpPostgreSQL;

    /**
     * 先加载开发环境配置，然后尝试加载生产环境配置，生产环境配置不存在时不会抛异常
     * 在生产环境部署时后动创建 demo-config-pro.txt，添加的配置项可以覆盖掉demo-config-dev.txt 中的配置项
     */
    static void loadConfig() {
        if (p == null) {
            p = PropKit.use("jfinal.properties").appendIfExists("demo-config-pro.txt");
        }
    }

//    static {
//        loadConfig();
//    }

    /**
     * 配置常量
     */
    @Override
    public void configConstant(Constants me) {
        loadConfig();
        me.setDevMode(p.getBoolean("jfinal.devMode", false));

        // 支持 Controller、Interceptor 之中使用 @Inject 注入业务层，并且自动实现 AOP
        me.setInjectDependency(true);

        // jfinal 官方提供了 Json  抽象类的三个实现，默认JFinalJson
        me.setJsonFactory(new FastJsonFactory());
        // 配置 Date 类型转 json 后的格式
        me.setJsonDatePattern("yyyy-MM--dd");
        // 文件默认上传大小 10485760字节 10M
        me.setMaxPostSize(10 * Const.DEFAULT_MAX_POST_SIZE);
        ParaProcessorBuilder.me.regist(InMessage.class, InMessageParaGetter.class, null);
    }

    /**
     * 配置路由
     */
    @Override
    public void configRoute(Routes me) {
        me.add(new SwaggerRoutes());
        me.add(new AdminRoutes());
        me.add("/jax", JaxRsController.class);
        me.add("/webSocket", WebSocketController.class, "/ws");
        me.add("/mob", MobSdController.class);
        me.add("/import", ImportRmbController.class);
    }

    /**
     * 配置模板引擎
     */
    @Override
    public void configEngine(Engine me) {
        // 设置视图的基础路径
        me.setBaseTemplatePath(PathKit.getWebRootPath() + "/WEB-INF/view");
        // 添加共享模板函数
        me.addSharedFunction("/common/_layout.html");

        // 支持模板热加载
        me.setDevMode(true);
    }

    /**
     * 配置插件
     */
    @Override
    public void configPlugin(Plugins me) {
        /**
         * MYSQL数据库
         */
        // 配置 druid 数据库连接池插件（数据源）
        mysqldp = new DruidPlugin(
                p.get("jfinal.MySQL.jdbcUrl"),
                p.get("jfinal.MySQL.user"),
                p.get("jfinal.MySQL.password").trim());
        me.add(mysqldp);

        // 配置ActiveRecord 数据库访问插件
        arpMysql = new ActiveRecordPlugin("MySQL", mysqldp);

        // 所有映射在 MappingKit 中自动化搞定（建立了数据库表名到Model的映射关系）
        _MappingKit.mapping(arpMysql);
        me.add(arpMysql);

        /**
         * PostgreSQL数据库
         */
        postgreSQLdp = new DruidPlugin(
                p.get("jfinal.postgreSQL.url"),
                p.get("jfinal.postgreSQL.user"),
                p.get("jfinal.postgreSQL.password"),
                p.get("jfinal.postgreSQL.driverClass").trim());
        me.add(postgreSQLdp);
        arpPostgreSQL = new ActiveRecordPlugin("postgreSQL", postgreSQLdp);
        com.wdy.generator.postgreSQL.model._MappingKit.mapping(arpPostgreSQL);
        me.add(arpPostgreSQL);


        // Dialect多数据库支持，设置数据库方言
        arpMysql.setDialect(new MysqlDialect());
        arpMysql.setShowSql(true);
        // sql 模板管理
        arpMysql.getEngine().setSourceFactory(new ClassPathSourceFactory());
        arpMysql.addSqlTemplate("sql/jfinal_demo.sql");

        arpPostgreSQL.setDialect(new PostgreSqlDialect());
        arpPostgreSQL.setShowSql(true);
        arpPostgreSQL.getEngine().setSourceFactory(new ClassPathSourceFactory());
        arpPostgreSQL.addSqlTemplate("sql/jfinal_demo.sql");


        // 切换到自己的缓存 ？？？
//        arp.setCache(new WdyCache());
        // TODO 配置EhCachePlugin，才能调用缓存操作工具类CacheKit，
        me.add(new EhCachePlugin());
        // TODO 配置swagger插件
        me.add(new SwaggerPlugin(new SwaggerDoc().setBasePath("/").setHost("127.0.0.1").setSwagger("2.0")
                .setInfo(new SwaggerApiInfo("jfinal swagger demo", "1.0", "jfinal swagger", ""))));

        AlarmThreadController.getInstance().start();

        // todo 任务调度插件Cron4jPlugin
        Cron4jPlugin cp = new Cron4jPlugin(p, "cron4j");
        me.add(cp);
    }

    public static DruidPlugin createDruidPluginMySQL() {
        loadConfig();

        return new DruidPlugin(p.get("jfinal.MySQL.jdbcUrl"), p.get("jfinal.MySQL.user"), p.get("jfinal.MySQL.password").trim());
    }

    /**
     * 配置全局拦截器：Global、Routes、Class、Method
     */
    @Override
    public void configInterceptor(Interceptors me) {
        me.add(new OutMessageInterceptor());
        me.add(new ParameterInterceptor());
//        me.add(new GlobalInterceptor());

        // 国际化组件 拦截action请求
//        me.add(new I18nInterceptor());

        // 添加控制层全局拦截器
//        me.addGlobalActionInterceptor(new GlobalInterceptor());
        // 添加业务层全局拦截器
//        me.addGlobalServiceInterceptor(new GlobalInterceptor());

        // ActiveRecord 正则声明式事务支持
//        me.add(new TxByMethodRegex("(.*save.*|.*update.*)"));
//        me.add(new TxByMethods("save", "update"));
//
//        me.add(new TxByActionKeyRegex("/trans.*"));
//        me.add(new TxByActionKeys("/tx/save", "/tx/update"));
    }

    @Override
    public void configHandler(Handlers handlers) {
//        handlers.add(new WebSocketHandler());
//        handlers.add(new UrlSkipHandler("/ws/socket.ws", false));
    }

    @Override
    public void onStart() {
        super.onStart();
        //加载字典表
        DictionaryService.loadDict();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
