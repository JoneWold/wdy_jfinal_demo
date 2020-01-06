package com.wdy.biz.hello.controller;

import com.jfinal.aop.Clear;
import com.jfinal.aop.Inject;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;
import com.wdy.biz.hello.service.HelloService;
import com.wdy.constant.CacheConstant;
import com.wdy.generator.mysql.model.Blog;
import com.wdy.generator.mysql.model.SysUser;
import com.wdy.message.OutMessage;

import java.util.List;

/**
 * Created by wgch on 2019/3/5.
 */
public class HelloController extends Controller {
    /**
     * 此处会注入依赖对象
     */
    @Inject
    HelloService service;

    /**
     * 在Controller之中定义的public方法称为Action
     * 1、带@ActionKey注解，访问方式：域名 + 端口 + 注解名
     * 无@ActionKey注解，访问方式：域名 + 端口号 + controllerkey + 方法名
     * 2、在Action中注入参数：可以代替 getPara、getBean、getModel 系列方法获取参数
     */
    @ActionKey("/")
    public void index(String id) {
        System.out.println("controller in......" + id);
        // 调用被注入对象的方法
        List<Blog> list = service.getBlogList();
        setAttr("blogList", list);
        render("/index.html");
    }

    @Clear
    public void wdy() {
        set("wdy", "微电影").renderJson();
    }

    @ActionKey("/123")
    public void getUserPage() {
        Page<SysUser> page = service.getUserPage(1, 10);
        renderJson(page);
    }

    /**
     * CacheKit 工具类
     */
    public void cacheKit() {
        List<Blog> list = CacheKit.get(CacheConstant.WDY_CACHE, "wkey");
        if (list == null) {
            list = service.getBlogList();
            CacheKit.put(CacheConstant.WDY_CACHE, "wkey", list);
        }
        renderJson(list);
    }

    /**
     * Ehcache 缓存
     */
    public void cache() {
        List<Blog> list = service.cache();
        renderJson(list);
    }

    public OutMessage getPostgreSQLList() {
        return service.getPostgreSQLList();
    }

    /**
     * sql模板
     */
    public void getBlogs() {
        List<Blog> blogs = service.getBlogList2();
        renderJson(blogs);
    }

    /**
     * 生成验证码
     */
    public void createCode() {
        renderCaptcha();
    }

    /**
     * 验证码校验
     */
    public boolean checkCode() {
        // boolean validate = CaptchaRender.validate(this, getPara("code"));
        boolean result = validateCaptcha("code");
        if (!result) {
            renderJson("验证码错误");
        }
        return result;
    }

    public void json() {
        String str = "{\n" +
                "    \"code\": 0,\n" +
                "    \"msg\": \"成功\",\n" +
                "    \"pageno\": 1,\n" +
                "    \"pagesize\": 20,\n" +
                "    \"totalcount\": 116,\n" +
                "    \"result\": [\n" +
                "        {\n" +
                "            \"orgCname\": \"委领导\",\n" +
                "            \"purpose\": \"2\",\n" +
                "            \"parentOrgId\": \"500000              \",\n" +
                "            \"orgCode\": \"50000000D001\",\n" +
                "            \"organOrder\": 100001,\n" +
                "            \"relationId\": \"c8bf41271b5c475baaaa1d821cc496be\",\n" +
                "            \"invalidFlag\": \"1\",\n" +
                "            \"parentOrgCname\": \"重庆市纪委\",\n" +
                "            \"id\": \"0f5ef19a9fc641659111edcdfde4447b\",\n" +
                "            \"orgShortCname\": \"委领导\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"orgCname\": \"办公厅\",\n" +
                "            \"purpose\": \"2\",\n" +
                "            \"parentOrgId\": \"500000              \",\n" +
                "            \"orgCode\": \"50000000D003\",\n" +
                "            \"organOrder\": 100003,\n" +
                "            \"relationId\": \"92515790d90849109104104bc46477e0\",\n" +
                "            \"invalidFlag\": \"1\",\n" +
                "            \"parentOrgCname\": \"重庆市纪委\",\n" +
                "            \"id\": \"e0814e28cae748ddb694b7cddd664935\",\n" +
                "            \"orgShortCname\": \"办公厅\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        renderJson(str);
    }


}
