//package com.wdy.service;
//
//import com.jfinal.kit.StrKit;
//import com.jfinal.plugin.activerecord.Db;
//import com.jfinal.plugin.activerecord.Record;
//import java.text.ParseException;
//import java.util.Date;
//import java.util.List;
//
///**
// * Created by wgch on 2019/3/13.
// */
//public class ServiceTest {
//
//    /**
//     * 编辑角色
//     *
//     * @param data
//     * @return
//     */
//    public OutMessage updateRole(Role data) throws ParseException {
//        String id = data.getId();
//        if (!StrKit.notNull(id)) {
//            return new OutMessage(Status.PARA_ERROR);
//        }
//        Role role = roleDao.me.findById(id);
//        if (!StrKit.notNull(role)) {
//            return new OutMessage(Status.FAIL);
//        }
//        if (IS_BUILT_IN == role.getIsBuiltIn()) {
//            return new OutMessage(Status.BUILT_IN_UNEDIT);
//        }
//        role.setUpdateTime(new Date());
//        boolean b = roleDao.update(role);
//        if (b) {
//            return new OutMessage(Status.SUCCESS);
//        }
//        return new OutMessage(Status.FAIL);
//    }
//
//    /**
//     * 删除角色
//     *
//     * @param data
//     * @return
//     */
//    public OutMessage delRole(Role data) {
//        String ids = data.getStr("ids");
//        for (String roleId : ids.split(",")) {
//
//            // 该角色所关联用户
//            List<Record> userIdList = Db.find("select \"userID\" from \"SysUserRolePermission\"  group by \"userID\"");
//            boolean flag = true;
//            for (Record userID : userIdList) {
//                // 该用户所持有的角色
//                List<Record> records = Db.find("select id,\"roleID\" from \"SysUserRolePermission\" where \"roleID\"!=? and \"userID\"=?", roleId, userID);
//                // 只要某一个用户仅具有一个角色，便不可删除
//                if (records.size() == 0) {
//                    flag = false;
//                    break;
//                }
//            }
//            // 除了该角色还有其他角色，可删除
//            if (flag) {
//                List<Record> UserRoleIds = Db.find("select id,\"userID\" from \"SysUserRolePermission\" where \"roleID\" =?", roleId);
//                for (Record record : UserRoleIds) {
//                    String userId = record.getStr("userID");
//                    Record user = Db.findFirst("select id,currentuserroleid from \"SysUser\" where isdelete=0 and \"currentuserroleid\"=? and id=?", roleId, userId);
//                    Record roleIdRecord = Db.findFirst("select id,\"userID\",\"roleID\" from \"SysUserRolePermission\" where \"roleID\" !=?  and \"userID\"=?", roleId, userId);
//                    // 重新给该用户赋值一个角色
//                    user.set("currentuserroleid", roleIdRecord.getStr("roleID"));
//                    Db.update("SysUser", record);
//
//                    Integer id = record.getInt("id");
//                    // 删除关联表中该角色
//                    Record sysUserRolePermission = Db.findById("SysUserRolePermission", id);
//                    boolean delUserRole = Db.delete("SysUserRolePermission", sysUserRolePermission);
//                    // 删除主表中该角色
//                    Record sysRole = Db.findById("SysRole", roleId);
//                    boolean delRole = Db.delete("SysRole", sysRole);
//                    if (delUserRole && delRole) {
//                        return new OutMessage(Status.SUCCESS);
//                    }
//                }
//            } else {
//                return new OutMessage(Status.FAIL, "每个用户至少含有一种角色");
//            }
//        }
//        return new OutMessage(Status.FAIL);
//    }
//
//}
