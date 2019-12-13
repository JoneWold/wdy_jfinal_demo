package com.wdy.biz.file.rmb.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.jfinal.aop.Aop;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.upload.UploadFile;
import com.wdy.generator.postgreSQL.model.A01Temp;
import com.wdy.generator.postgreSQL.model.A36Temp;
import com.wdy.generator.postgreSQL.model.A57Temp;
import com.wdy.generator.postgreSQL.model.base.BaseA57Temp;
import com.wdy.message.OutMessage;
import com.wdy.message.Status;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.wdy.constant.CommonConstant.*;
import static com.wdy.constant.DBConstant.DB_PGSQL;

/**
 * @author wgch
 * @Description 导入任免表service
 * @date 2019/12/12
 */
public class ImportRmbService {

    private ReadRmbService readRmbService = Aop.get(ReadRmbService.class);

    public OutMessage importRmb(List<UploadFile> files, String impId) throws Exception {
        List<String> fileSuffix = this.checkFileSuffix(files);
        if (fileSuffix.size() > 0) {
            // 如果存在 类型不匹配的文件，清理掉当前批次的所有文件。
            files.forEach(uploadFile1 -> FileUtil.del(uploadFile1.getFile()));
            return new OutMessage<>(Status.FILE_FORMAT_ERROR, fileSuffix);
        }
        List<A01Temp> a01TempList = new ArrayList<>();
        List<A36Temp> a36TempList = new ArrayList<>();
        List<A57Temp> a57TempList = new ArrayList<>();
        // 1 读取文件数据
        for (UploadFile uploadFile : files) {
            File file = uploadFile.getFile();
            String fileName = file.getName();
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            switch (suffix) {
                case POINT_LRM:
                    readRmbService.readTxtLrm(file, impId, a01TempList, a36TempList);
                    FileUtil.del(file);
                    break;
                case POINT_LRMX:
                    readRmbService.readXmlLrmx(file, impId, a01TempList, a36TempList, a57TempList);
                    FileUtil.del(file);
                    break;
                case POINT_PIC:
                    readRmbService.readPic(file, impId, a57TempList);
                    FileUtil.del(file);
                    break;
                case POINT_ZIP:
                    FileUtil.del(file);
                    break;
                default:
                    return new OutMessage<>(Status.FILE_FORMAT_ERROR, fileName);
            }
        }
        // 2 完善数据（lrm 文件需要与pic 文件配合使用）
        Map<String, String> a57Map = a57TempList.stream().collect(Collectors.toMap(BaseA57Temp::getA0000, BaseA57Temp::getA5714, (k, v) -> k));
        for (A01Temp a01Temp : a01TempList) {
            String a0000 = a01Temp.getA0000();
            String a0198 = a01Temp.getA0198();
            if (StrKit.isBlank(a0198) && a57Map.containsKey(a0000)) {
                String path = "/upload/" + a57Map.get(a0000);
                File file = new File(PathKit.getWebRootPath() + path);
                if (file.exists()) {
                    a01Temp.setA0198(path);
                }
            }
        }
        // 3 写入数据 （a01_temp 需要根据身份证、姓名和出生年月 判断该人员是否存在，a36_temp和a57_temp可以直接插入）
        int[] a36s = Db.use(DB_PGSQL).batchSave(a36TempList, 100);
        int[] a57s = Db.use(DB_PGSQL).batchSave(a57TempList, 100);

        StringBuilder sb = new StringBuilder("数据写入成功：");
        sb.append("a36_temp ->").append(a36s.length).append("条。");
        sb.append("a57_temp ->").append(a57s.length).append("条。");
        return new OutMessage<>(Status.SUCCESS, sb);
    }

    /**
     * 效验文件格式
     */
    private List<String> checkFileSuffix(List<UploadFile> files) {
        List<String> list = new ArrayList<>();
        for (UploadFile uploadFile : files) {
            String fileName = uploadFile.getFile().getName();
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            if (!StrUtil.equalsAny(suffix, POINT_LRM, POINT_LRMX, POINT_PIC, POINT_ZIP)) {
                list.add(fileName);
            }
        }
        return list;
    }

}
