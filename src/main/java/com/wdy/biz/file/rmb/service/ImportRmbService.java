package com.wdy.biz.file.rmb.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.jfinal.aop.Aop;
import com.jfinal.aop.Before;
import com.jfinal.kit.*;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.TableMapping;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.upload.UploadFile;
import com.wdy.biz.file.rmb.dao.ImportRmbDao;
import com.wdy.biz.mem.dao.MemDao;
import com.wdy.biz.mem.service.MemService;
import com.wdy.biz.org.dao.OrgDao;
import com.wdy.dto.A08MergeDegreeDto;
import com.wdy.dto.RmbOldMemInfoDto;
import com.wdy.generator.postgreSQL.model.*;
import com.wdy.generator.postgreSQL.model.base.BaseA01Temp;
import com.wdy.generator.postgreSQL.model.base.BaseA57Temp;
import com.wdy.generator.postgreSQL.model.base.BaseYoungCadre;
import com.wdy.message.OutMessage;
import com.wdy.message.Status;
import com.wdy.vo.RmbA01TempVo;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static com.wdy.constant.CommonConstant.*;
import static com.wdy.constant.DBConstant.DB_PGSQL;
import static com.wdy.constant.DictConstant.*;

/**
 * @author wgch
 * @Description 导入任免表service
 * @date 2019/12/12
 */
public class ImportRmbService {

    private OrgDao orgDao = Aop.get(OrgDao.class);
    private MemDao memDao = Aop.get(MemDao.class);
    private ImportRmbDao dao = Aop.get(ImportRmbDao.class);
    private MemService memService = Aop.get(MemService.class);
    private ReadRmbService readRmbService = Aop.get(ReadRmbService.class);
    private Prop p = PropKit.use("undertow.txt");

    @Before(Tx.class)
    public OutMessage uploadRmb(List<UploadFile> files, String impId) throws Exception {
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
        List<A01Temp> a01TempNewList = this.groupA01TempList(a01TempList, a57Map);
        a36TempList.forEach(e -> e.setType("1"));
        a57TempList.forEach(e -> e.setType("1"));

        // 3 写入数据 （a01_temp 需要根据身份证、姓名和出生年月 判断该人员是否存在，a36_temp和a57_temp可以直接插入）
        int[] a01s = Db.use(DB_PGSQL).batchSave(a01TempNewList, 100);
        int[] a36s = Db.use(DB_PGSQL).batchSave(a36TempList, 100);
        int[] a57s = Db.use(DB_PGSQL).batchSave(a57TempList, 100);
        LogKit.info("---------数据写入成功---------");
        LogKit.info("a01_temp -->" + a01s.length);
        LogKit.info("a36_temp -->" + a36s.length);
        LogKit.info("a57_temp -->" + a57s.length);
        return new OutMessage<>(Status.SUCCESS);
    }


    public OutMessage getList(int pageNum, int pageSize, String impId) {
        Page<A01Temp> page = dao.getList(pageNum, pageSize, impId);
        return new OutMessage<>(Status.SUCCESS, page);
    }

    public OutMessage update(String a0000, String toA0000, String impId) {
        int update = dao.update(a0000, toA0000, impId);
        return update == 1 ? new OutMessage(Status.SUCCESS) : new OutMessage(Status.FAIL);
    }

    @Before(Tx.class)
    public OutMessage importRmb(String orgId, String impId, String system) {
        B01 b01 = orgDao.findById(orgId);
        List<A01Temp> a01TempList = dao.findA01TempList(impId, "1");
        RmbA01TempVo tempVo = this.getRmbA01TempVo(a01TempList, orgId);
        // 人员数据去重
        List<A01Temp> a01TempNewList = this.distinctA01Temps(tempVo.getA01TempNewList());

        HashSet<String> saveA0000Set = tempVo.getSaveA0000Set();
        Map<String, String> updateA0000toOld = tempVo.getUpdateA0000toOld();
        // a01
        List<A01> a01SaveList = new ArrayList<>();
        List<A01> a01UpdateList = new ArrayList<>();
        this.getUpdateSaveList(a01TempNewList, saveA0000Set, updateA0000toOld, a01SaveList, a01UpdateList, system);

        // a02
        List<A02> a02List = this.getA02List(a01TempNewList, b01);

        List<Record> a36TempList = dao.findTempList("a36_temp", impId, "1");
        List<Record> a57TempList = dao.findTempList("a57_temp", impId, "1");
        // 替换a36,a57待更新的主键
        List<Record> a36List = this.getA36A57List(a36TempList, updateA0000toOld);
        List<Record> a57List = this.getA36A57List(a57TempList, updateA0000toOld);

        // 清除原表数据
        dao.delTableList(saveA0000Set);
        // 写入数据
        int[] a01update = Db.use(DB_PGSQL).batchUpdate(a01UpdateList, 100);
        int[] a01save = Db.use(DB_PGSQL).batchSave(a01SaveList, 100);
        int[] a02s = Db.use(DB_PGSQL).batchSave(a02List, 100);
        int[] a36s = Db.use(DB_PGSQL).batchSave("a36", a36List, 100);
        int[] a57s = Db.use(DB_PGSQL).batchSave("a57", a57List, 100);
        // 处理优秀年轻干部主表
        this.updateYoungCadre(a01TempNewList.stream().map(BaseA01Temp::getA0000).collect(Collectors.toList()), system, b01);
        // TODO 添加a08学历学位，补全a01学历学位相关字段
        a01TempNewList.forEach(this::saveA08Data);

        LogKit.info("---------数据导入成功---------");
        LogKit.info("a01 update -->" + a01update.length);
        LogKit.info("a01 save -->" + a01save.length);
        LogKit.info("a02 -->" + a02s.length);
        LogKit.info("a36 -->" + a36s.length);
        LogKit.info("a57 -->" + a57s.length);
        return new OutMessage<>(Status.SUCCESS);
    }


    /**
     * 完善a01_temp数据
     */
    private List<A01Temp> groupA01TempList(List<A01Temp> a01TempList, Map<String, String> a57Map) {
        if (a01TempList.size() > 0) {
            Map<String, List<RmbOldMemInfoDto>> a01ByA0184 = dao.findA01ByA0184();
            a01TempList.forEach(a01Temp -> {
                // 完善照片路径
                String a0000 = a01Temp.getA0000();
                String a0198 = a01Temp.getA0198();
                if (StrKit.isBlank(a0198) && a57Map.containsKey(a0000)) {
                    String path = "/upload/" + a57Map.get(a0000);
                    File file = new File(PathKit.getWebRootPath() + path);
                    if (file.exists()) {
                        a01Temp.setA0198(path);
                    }
                }
                // 身份证
                String a0184 = a01Temp.getA0184();
                // 获取该人员  在原数据库中的对应原数据oldDataArray
                List<RmbOldMemInfoDto> memInfoDtos;
                if (StrKit.notBlank(a0184)) {
                    memInfoDtos = a01ByA0184.get(a0184) == null ? new ArrayList<>() : a01ByA0184.get(a0184);
                } else {
                    // 姓名、出生年月
                    memInfoDtos = dao.findA01ByA0101(a01Temp.getA0101(), a01Temp.getA0107());
                }
                if (memInfoDtos.size() == 0) {
                    a01Temp.setResult("系统无此人");
                } else if (memInfoDtos.size() == 1) {
                    a01Temp.setOldDataArray(JSONArray.parseArray(JSON.toJSONString(memInfoDtos)));
                    a01Temp.setToA0000(memInfoDtos.get(0).A0000);
                    a01Temp.setResult("与系统一致");
                }
                a01Temp.setType("1");
            });
        }
        return a01TempList;
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

    /**
     * 获取任免表新增、编辑等主键信息
     */
    private RmbA01TempVo getRmbA01TempVo(List<A01Temp> a01TempList, String orgId) {
        HashSet<String> saveA0000Set = new HashSet<>();
        Map<String, String> updateA0000toOld = new HashMap<>();
        for (A01Temp a01Temp : a01TempList) {
            String a0000 = a01Temp.getA0000();
            JSONArray oldDataArray = a01Temp.getOldDataArray();
            String toA0000 = a01Temp.getToA0000();
            String result = a01Temp.getResult();
            // 统计关系所在单位
            a01Temp.setA0195(orgId);
            // 待保存的人员信息
            if ("系统无此人".equals(result) && oldDataArray.size() == 0) {
                saveA0000Set.add(a0000);
            } else if ("与系统一致".equals(result) && oldDataArray.size() == 0) {
                // 待更新的人员信息，更新该条记录主键
                a01Temp.setA0000(toA0000);
                updateA0000toOld.put(a0000, toA0000);
            } else {
                // 无对比结果 调用update接口后指定的待更新的人员数据
                if (StrKit.notBlank(toA0000)) {
                    // 将当前temp人员标识改为原数据人员标识
                    a01Temp.setA0000(toA0000);
                    updateA0000toOld.put(a0000, toA0000);
                }
            }
        }
        // 组装结果集
        RmbA01TempVo tempVo = new RmbA01TempVo();
        tempVo.setA01TempNewList(a01TempList);
        tempVo.setSaveA0000Set(saveA0000Set);
        tempVo.setUpdateA0000toOld(updateA0000toOld);
        return tempVo;
    }

    /**
     * 人员数据去重
     */
    private List<A01Temp> distinctA01Temps(List<A01Temp> a01TempList) {
        List<A01Temp> a01TempNewList = new ArrayList<>();
        HashSet<String> a0000Set = new HashSet<>();
        for (int i = 0; i < a01TempList.size(); i++) {
            A01Temp a01Temp = a01TempList.get(i);
            // 替换主键后，如果遇到相同的A0000，只取其中一个
            String a0000 = a01Temp.getA0000();
            if (i == 0) {
                a0000Set.add(a0000);
                a01TempNewList.add(a01Temp);
            } else {
                if (!a0000Set.contains(a0000)) {
                    a0000Set.add(a0000);
                    a01TempNewList.add(a01Temp);
                }
            }
        }
        return a01TempNewList;
    }

    /***
     * 处理a01_temp数据
     * @param a01TempNewList   a01_temp数据
     * @param saveA0000Set     待保存的人员标识
     * @param updateA0000toOld a01_temp中的A0000 -> 匹配到的a01中的A0000
     * @param a01SaveList      保存到a01的数据
     * @param a01UpdateList    更新到a01的数据
     */
    private void getUpdateSaveList(List<A01Temp> a01TempNewList, HashSet<String> saveA0000Set, Map<String, String> updateA0000toOld
            , List<A01> a01SaveList, List<A01> a01UpdateList, String system) {
        Set<String> a01TempColumnNameSet = TableMapping.me().getTable(A01Temp.class).getColumnNameSet();
        Set<String> a01ColumnNameSet = TableMapping.me().getTable(A01.class).getColumnNameSet();
        // 存放a01中不包含但a01_temp包含的字段
        List<String> removeStr = new ArrayList<>();
        a01TempColumnNameSet.forEach(e -> {
            if (!a01ColumnNameSet.contains(e)) {
                removeStr.add(e);
            }
        });
        a01TempNewList.forEach(a01Temp -> {
            // 清除a01_temp多余字段
            removeStr.forEach(a01Temp::remove);
            A01 a01 = new A01();
            a01._setAttrs(BeanUtil.beanToMap(a01Temp));
            // TODO 补全政治面貌相关字段
            this.setZzmm(a01, a01Temp, system);
            if ("1".equals(system)) {
                // 人员类别 1 公务员
                a01.setA0160("1");
                // 编制类型
                a01.setA0121("1");
            }
            // 优秀年轻干部时
            if ("5".equals(system)) {
                if (StrKit.notBlank(a01Temp.getA0165())) {
                    a01.setA0165(a01Temp.getA0165());
                }
            } else {
                if (StrUtil.isNotEmpty(p.get("QxA0165"))) {
                    String[] qxA0165s = p.get("QxA0165").split(",");
                    a01.setA0165(qxA0165s[qxA0165s.length - 1]);
                } else {
                    // 管理类别 02 省级党委管理干部
                    a01.setA0165("02");
                }
            }

            String a0000 = a01Temp.getA0000();
            if (saveA0000Set.contains(a0000)) {
                a01SaveList.add(a01);
            } else if (updateA0000toOld.containsValue(a0000)) {
                a01UpdateList.add(a01);
            }
        });

    }


    private List<A02> getA02List(List<A01Temp> a01TempNewList, B01 b01) {
        List<A02> a02List = new ArrayList<>();
        HashSet<String> zzSet = dao.getZzSet(b01.getB0111());
        a01TempNewList.forEach(a01Temp -> {
            String a0000 = a01Temp.getA0000();
            // 当前单位下没有在职数据就得新增一条
            if (!zzSet.contains(a0000)) {
                A02 a02 = new A02();
                a02.setA0000(a0000);
                a02.setA0200(StrKit.getRandomUUID());
                a02.setA0201A(b01.getB0101());
                a02.setA0201B(b01.getId());
                a02.setA0255("1");
                a02List.add(a02);
            }
        });
        return a02List;
    }

    /**
     * 替换a36,a57待更新的主键
     */
    private List<Record> getA36A57List(List<Record> tempList, Map<String, String> updateA0000toOld) {
        tempList.forEach(temp -> {
            String a0000 = temp.getStr("A0000");
            String toA0000 = updateA0000toOld.get(a0000);
            if (StrKit.notBlank(toA0000)) {
                temp.set("A0000", toA0000);
            }
        });
        return tempList;
    }


    /**
     * 优秀年轻信息表
     *
     * @param a01IdTempList a01_temp有效人员标识符集合
     * @param system        系统标识
     * @param b01           当前机构信息
     */
    private void updateYoungCadre(List<String> a01IdTempList, String system, B01 b01) {
        // 5 优秀年轻干部
        if ("5".equals(system)) {
            List<YoungCadre> youngCadreList = dao.findYoungCadreList();
            Map<String, YoungCadre> cadreMap = youngCadreList.stream().collect(Collectors.toMap(BaseYoungCadre::getA0000, e -> e));
            Integer orgTreeSortById = dao.findOrgTreeSortById(b01.getId());
            HashSet<String> a0000s = youngCadreList.stream().filter(e -> a01IdTempList.contains(e.getA0000())).map(BaseYoungCadre::getA0000).collect(Collectors.toCollection(HashSet::new));
            List<YoungCadre> updateList = new ArrayList<>();
            List<YoungCadre> saveList = new ArrayList<>();
            for (String a0000 : a0000s) {
                YoungCadre youngCadre = cadreMap.get(a0000);
                youngCadre.setOtherOrgId(b01.getId());
                youngCadre.setJoinOrg(b01.getId());
                youngCadre.setOrgTreeSort(orgTreeSortById);
                updateList.add(youngCadre);
            }
            for (String a0000 : a01IdTempList) {
                if (!a0000s.contains(a0000)) {
                    YoungCadre cadre = new YoungCadre();
                    cadre.setId(StrKit.getRandomUUID());
                    cadre.setA0000(a0000);
                    cadre.setOtherOrgId(b01.getId());
                    cadre.setJoinOrg(b01.getId());
                    cadre.setOrgTreeSort(orgTreeSortById);
                    cadre.setType("0");
                    cadre.setCreateTime(new Date());
                    saveList.add(cadre);
                }
            }
            Db.use(DB_PGSQL).batchUpdate(updateList, 1000);
            Db.use(DB_PGSQL).batchSave(saveList, 1000);
        }
    }

    /**
     * 设置政治面貌
     *
     * @param a01     原数据人员信息
     * @param a01Temp temp表人员信息
     */
    private void setZzmm(A01 a01, A01Temp a01Temp, String system) {
        Map<String, String> zZmmMap = dao.getDictNameToCode(ZZMM_TYPE);
        String a0140 = a01Temp.getA0140();
        if (StrKit.notBlank(a0140)) {
            String[] zZmm = a0140.replaceAll("，", ",").replaceAll("、", ",").split(",");
            for (int i = 0; i < zZmm.length; i++) {
                if (i == 0) {
                    // 第二党派时间
                    String a3921Code = zZmmMap.get(zZmm[i]) == null ? "" : zZmmMap.get(zZmm[i]);
                    if (StrKit.notBlank(a3921Code)) {
                        if ("04,05,06,07,08,09,10,11".contains(a3921Code)) {
                            a01.setA3921(a3921Code);
                        } else {
                            a01.setA0141(a3921Code);
                        }
                    }
                    // 政治面貌   等（九三学社、致公党、民进  民革(2017.09)  2012.12）
                    if (StrUtil.containsAny(zZmm[i], zZmmMap.keySet().toArray(new String[0]))) {
                        for (Map.Entry<String, String> entry : zZmmMap.entrySet()) {
                            if (zZmm[i].contains(entry.getKey())) {
                                if ("04,05,06,07,08,09,10,11".contains(entry.getValue())) {
                                    a01.setA3921(entry.getValue());
                                }
                            }
                        }
                    }
                    if (zZmm[i].contains(".")) {
                        // 8党外
                        if ("8".equals(system)) {
                            // 群众
                            a01.setA0141("13");
                        } else {
                            // 中共党员
                            a01.setA0141("01");
                        }
                    }
                }
                if (i == 1) {
                    // 第三党派时间
                    String a3927Code = zZmmMap.get(zZmm[i]) == null ? "" : zZmmMap.get(zZmm[i]);
                    if (StrKit.notBlank(a3927Code)) {
                        if ("04,05,06,07,08,09,10,11".contains(a3927Code)) {
                            a01.setA3927(a3927Code);
                        } else {
                            a01.setA3927(zZmm[i]);
                        }
                    }
                }
            }
        }
    }

    /**
     * 添加学历信息
     *
     * @param a01Temp temp人员数据
     */
    private void saveA08Data(A01Temp a01Temp) {
        Map<String, String> xldm = dao.getDictNameToCode(XLDM_TYPE);
        Map<String, String> xwdm = dao.getDictNameToCode(XWDM_TYPE);
        Map<String, String> zy = dao.getDictNameToCode(ZYDM_TYPE);
        String toA0000 = a01Temp.getToA0000();
        List<Record> a08List = dao.findA08List(toA0000);
        if (a08List == null) {
            return;
        }
        List<A08> saveList = new ArrayList<>();
        // 相关数据
        String qrzxl = a01Temp.getQRZXL();
        String qrzxw = a01Temp.getQRZXW();
        String qrzxlxx = a01Temp.getQRZXLXX();
        String qrzxwxx = a01Temp.getQRZXWXX();

        String zzxl = a01Temp.getZZXL();
        String zzxw = a01Temp.getZZXW();
        String zzxlxx = a01Temp.getZZXLXX();
        String zzxwxx = a01Temp.getZZXWXX();
        // 原库学历学位信息没有直接加
        // 原库中有全日制就只增加在职，有在职就只增加全日制
        boolean hasQrz = false;
        boolean hasZz = false;
        for (Record record : a08List) {
            A08 a08 = new A08();
            a08.put(record);
            if ("1".equals(a08.getA0837())) {
                hasQrz = true;
            }
            if ("0".equals(a08.getA0837())) {
                hasZz = true;
            }
        }
        if (!hasQrz) {
            // 全日制
            if (StrKit.notBlank(qrzxl) || StrKit.notBlank(qrzxw) || StrKit.notBlank(qrzxlxx) || StrKit.notBlank(qrzxwxx)) {
                A08 qrzA08 = new A08();
                qrzA08.setA0800(StrKit.getRandomUUID());
                qrzA08.setA0000(toA0000);
                qrzA08.setA0801A(qrzxl);
                qrzA08.setA0801B(xldm.get(qrzxl) == null ? "" : xldm.get(qrzxl));
                qrzA08.setA0901A(qrzxw);
                qrzA08.setA0901B(xwdm.get(qrzxw) == null ? "" : xwdm.get(qrzxw));
                qrzA08.setA0814(qrzxlxx);
                qrzA08.setA0824(qrzxwxx);
                qrzA08.setA0827(zy.get(qrzxwxx) == null ? "" : zy.get(qrzxwxx));
                qrzA08.setA0837("1");
                qrzA08.setA0898("1");
                saveList.add(qrzA08);
            }
        }
        if (!hasZz) {
            // 在职
            if (StrKit.notBlank(zzxl) || StrKit.notBlank(zzxw) || StrKit.notBlank(zzxlxx) || StrKit.notBlank(zzxwxx)) {
                A08 zzA08 = new A08();
                zzA08.setA0800(StrKit.getRandomUUID());
                zzA08.setA0000(toA0000);
                zzA08.setA0801A(zzxl);
                zzA08.setA0801B(xldm.get(zzxl) == null ? "" : xldm.get(zzxl));
                zzA08.setA0901A(zzxw);
                zzA08.setA0901B(xwdm.get(zzxw) == null ? "" : xwdm.get(zzxw));
                zzA08.setA0814(zzxlxx);
                zzA08.setA0824(zzxwxx);
                zzA08.setA0827(zy.get(zzxwxx) == null ? "" : zy.get(zzxwxx));
                zzA08.setA0837("0");
                zzA08.setA0898("1");
                saveList.add(zzA08);
            }
        }
        int[] ints = Db.use(DB_PGSQL).batchSave(saveList, 100);
        LogKit.info("a08 -->" + ints.length);
        //处理最高全日制 等等 6个标识
        memService.updateA0834Many(saveList.stream()
                .filter(var -> StrUtil.isNotEmpty(var.getStr("A0000")))
                .map(var -> var.getStr("A0000")).collect(Collectors.toList()));
        Map<String, A08MergeDegreeDto> info = memService.mergeDegreeInfo(Collections.singletonList(toA0000), "");
        A01 a01 = memDao.findA01ById(toA0000);
        if (a01 != null && info != null) {
            A08MergeDegreeDto merge = info.get(a01.getA0000());
            if (merge != null) {
                a01.setQRZZS(merge.getQRZZS());
                a01.setZZZS(merge.getZZZS());
                a01.setQRZXL(merge.getQRZXL());
                a01.setQRZXLXX(merge.getQRZXLXX());
                a01.setQRZXW(merge.getQRZXW());
                a01.setQRZXWXX(merge.getQRZXWXX());
                a01.setZZXL(merge.getZZXL());
                a01.setZZXLXX(merge.getZZXLXX());
                a01.setZZXW(merge.getZZXW());
                a01.setZZXWXX(merge.getZZXWXX());
            }
            a01.update();
        }
    }

}
