package com.wdy.biz.jaxrs;

import com.jfinal.core.Controller;
import com.wdy.annotation.NotNull;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

/**
 * Created by wgch on 2019/3/25.
 * 用于RESTful Web服务的Java API。是一个Java 编程语言的应用程序接口，支持按照表述性状态转移（REST）架构风格创建Web服务
 *
 * @GET 标注用于在服务器上检索资源
 * @POST 标注用于在服务器上创建资源
 * @PUT 标注用于更新服务器上的资源
 * @DELETE 标注用于删除服务器上的资源
 * @Produce 注解指定返回给客户端的MIME媒体类型。可以用于注解类或者注解方法
 * @Consumes 该注解用于指定可以接受的客户端请求的MIME媒体类型
 * @Context 一些特殊的对象可以被注入到单例的构造函数或者属性中。一般用于获取request或者response相关的上下文
 */
@Path("/jax")
//@Singleton
@Produces({"application/xml; qs=0.9", "application/json"})
public class JaxRsController extends Controller {

    @Context
    Request request; // this is ok: the proxy of Request will be injected into this singleton

    @Path("/helloworld")
    public void HelloWorldResource() {
        System.out.println("HelloWorldResource...");
    }

    @GET
    @Path("/getList")
    public void getList() {
        System.out.println("getList...");
        renderJson();
    }

    @POST
    @Path("/add")
    public void add() {
        System.out.println("add...");
    }

    @PUT
    @Path("/update")
    public void update() {
        System.out.println("update...");
    }

    @DELETE
    @Path("/del")
    public void del() {
        System.out.println("del...");
    }

    @Context
    public void setRequest(Request request) {
        // injection into a setter method
        System.out.println(request != null);
        renderNull();
    }


    @NotNull(paras = {"12"}, hello = "hello", world = "wdy", array = {}, style = int.class)
    public void wdyAnnotation() {
        System.out.println("wdyAnnotation...");
        renderNull();
    }


}
