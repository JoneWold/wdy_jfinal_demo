package com.wdy.biz.hello.controller;

import com.jfinal.aop.Clear;
import com.jfinal.aop.Inject;
import com.jfinal.captcha.CaptchaRender;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;
import com.wdy.config.constant.CacheConstant;
import com.wdy.generator.mysql.model.Blog;
import com.wdy.generator.mysql.model.SysUser;
import com.wdy.biz.hello.service.HelloService;
import com.wdy.message.OutMessage;

import java.util.List;

/**
 * Created by wgch on 2019/3/5.
 */
public class HelloController extends Controller {
    @Inject
    HelloService service;   // 此处会注入依赖对象

    /**
     * 在Controller之中定义的public方法称为Action
     * 1、带@ActionKey注解，访问方式：域名 + 端口 + 注解名
     * 无@ActionKey注解，访问方式：域名 + 端口号 + controllerkey + 方法名
     * 2、在Action中注入参数：可以代替 getPara、getBean、getModel 系列方法获取参数
     */

    @ActionKey("/123")
    public void index(String id) {
        System.out.println("controller in......" + id);
        List<Blog> list = service.getBlogList();   // 调用被注入对象的方法
        renderJson(list);
    }

    @Clear
    public void wdy() {
        set("wdy", "微电影").renderJson();
    }

    public void getUserPage() {
        Page<SysUser> page = service.getUserPage(1, 10);
        renderJson(page);
    }

    // CacheKit 工具类
    public void cacheKit() {
        List<Blog> list = CacheKit.get(CacheConstant.WDY_CACHE, "wkey");
        if (list == null) {
            list = service.getBlogList();
            CacheKit.put(CacheConstant.WDY_CACHE, "wkey", list);
        }
        renderJson(list);
    }

    // Ehcache 缓存
    public void cache() {
        List<Blog> list = service.cache();
        renderJson(list);
    }

    public OutMessage getPostgreSQLList() {
        return service.getPostgreSQLList();
    }

    // sql模板
    public void getBlogs() {
        List<Blog> blogs = service.getBlogList2();
        renderJson(blogs);
    }

    // 生成验证码
    public void createCode() {
        renderCaptcha();
    }

    // 验证码校验
    public boolean checkCode() {
//        boolean validate = CaptchaRender.validate(this, getPara("code"));
        boolean result = validateCaptcha("code");
        if (!result) {
            renderJson("验证码错误");
        }
        return result;
    }


}
