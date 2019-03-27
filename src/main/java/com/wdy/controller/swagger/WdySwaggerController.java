package com.wdy.controller.swagger;

import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import live.autu.plugin.jfinal.swagger.annotation.*;
import live.autu.plugin.jfinal.swagger.config.RequestMethod;

/**
 * Created by wgch on 2019/3/15.
 */
@Api(description = "测试接口")
public class WdySwaggerController extends Controller {
    @ApiOperation(methods = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", required = false, description = "这是学员的姓名")
    })
    public void index() {
        String name = getPara("userName");
        renderJson(Ret.ok("msg", "测试成功！").set("userName", name));
    }

}
