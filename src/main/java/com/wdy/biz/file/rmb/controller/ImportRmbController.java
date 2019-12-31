package com.wdy.biz.file.rmb.controller;

import com.jfinal.aop.Aop;
import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;
import com.wdy.annotation.NotNull;
import com.wdy.biz.file.rmb.service.ImportRmbService;
import com.wdy.message.OutMessage;

import java.util.List;
import java.util.Locale;

/**
 * @author wgch
 * @Description 导入任免表
 * @date 2019/12/12
 */
public class ImportRmbController extends Controller {
    private ImportRmbService service = Aop.get(ImportRmbService.class);

    /***
     * 上传任免表
     * @param impId 导入批次号
     */
    public OutMessage addAppointmemt(String impId) throws Exception {
        List<UploadFile> files = this.getFiles();
        return service.uploadRmb(files, impId);
    }

    /**
     * 获取对比列表
     */
    @NotNull(paras = "impId", world = "")
    public OutMessage getList(int pageNum, int pageSize, String impId) {
        return service.getList(pageNum, pageSize, impId);
    }


    /***
     * 更新匹配到的原人员数据
     * @param a0000   temp 表人员标识符
     * @param toA0000 原表数据人员标识符
     * @param impId   导入批次号
     */
    @NotNull(paras = {"a0000", "toA0000", "impId"}, world = "")
    public OutMessage update(String a0000, String toA0000, String impId) {
        return service.update(a0000, toA0000, impId);
    }

    /***
     * 导入任免表
     * @param orgId 机构id
     * @param impId 导入批次号
     */
    public OutMessage importRmb(String orgId, String impId) {
        String system = this.getHeader("system");
        return service.importRmb(orgId, impId, system);
    }

    /***
     * 删除文件
     * @param impId    导入批次号
     * @param fileName 文件名（通过文件名确定文件所对应的数据，文件名相同被视为重复文件）
     */
    @NotNull(paras = {"impId", "fileName"}, world = "")
    public OutMessage deleteFile(String impId, String fileName) {
        return service.deleteFile(impId, fileName);
    }


    /**
     * 获取操作系统属性
     */
    public static void main(String[] args) {
        String oS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
        boolean win = oS.contains("win");
        System.out.println(win);
    }

}
