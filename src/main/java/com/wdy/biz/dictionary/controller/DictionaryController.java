package com.wdy.biz.dictionary.controller;

import cn.hutool.core.util.StrUtil;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.wdy.annotation.RequiresPermissions;
import com.wdy.biz.dictionary.service.DictionaryService;
import com.wdy.generator.postgreSQL.model.CodeValue;
import com.wdy.message.InMessage;
import com.wdy.message.OutMessage;
import com.wdy.message.Status;
import io.swagger.v3.oas.annotations.Operation;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * 字典表controller
 *
 * @author 刘浩然
 * @Date 2019/03/18
 */
@Path("/dictionary")
@Produces("application/json")
public class DictionaryController extends Controller {

    @Inject
    DictionaryService dictionaryService;

    @POST
    @Path("/getDictionaryList")
    @Operation(summary = "获得字典",
            tags = {"dictionaryS"},
            description = "This can only be done by the logged in user.")
    /**
     * 根据字典类型获取字典的值
     * @date 2019/03/24
     * @author LHR
     */
    @RequiresPermissions
    public OutMessage<List<Record>> getDictionaryList(InMessage<CodeValue> inMessage){
        if(inMessage.getData() == null || StrUtil.isEmpty(inMessage.getData().getCodeType())){
            return new OutMessage<>(Status.PARA_ERROR);
        }
        return dictionaryService.getDictionaryList(inMessage.getData());
    }

}
