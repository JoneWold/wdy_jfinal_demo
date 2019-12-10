package com.wdy.biz.base;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Aop;
import com.jfinal.core.Controller;
import com.jfinal.core.NotAction;
import com.jfinal.plugin.ehcache.CacheKit;
import com.wdy.biz.exception.PermissionException;
import com.wdy.constant.PermissionConstant;
import com.wdy.generator.postgreSQL.model.B01;
import com.wdy.vo.UserVo;

import java.util.ArrayList;
import java.util.List;

import static com.wdy.constant.CacheConstant.CACHE_USER;


/**
 * @author TMW
 * @version 1.0
 * @Description: controller层的base包
 * @date 2019/3/24 17:09
 */
public class BaseController extends Controller {


    private B01 b01Dao = Aop.get(B01.class);

    /**
     * 获取当前用户账号
     *
     * @return
     */
    @NotAction
    protected UserVo getCurrUser() {
        String token = this.getRequest().getHeader(PermissionConstant.HEADER_AUTHORIZATION);
        UserVo userVo = CacheKit.get(CACHE_USER, token);
        if (ObjectUtil.isNull(userVo)) {
            throw new PermissionException("从缓存中获取用户信息失败,token为:" + token);
        }
        return userVo;
    }

    /**
     * 获取当前用户管理的机构数组
     *
     * @return
     */
    @NotAction
    protected List<JSONObject> getCurrOrgArray() {
        JSONArray orgArray = this.getCurrUser().getUserRolePermission().getOrgArray();
        return orgArray == null ? new ArrayList<>() : orgArray.toJavaList(JSONObject.class);
    }

    /**
     * 获取当前用户的机构ID
     *
     * @return
     */
    @NotAction
    protected String getCurrOrgId() {
        return this.getCurrUser().getUser().getCurrentUserRoleId();
    }

    /**
     * 获取当前用户的权限字符串
     *
     * @return
     */
    @NotAction
    protected String getCurrPermission() {
        return this.getCurrUser().getUserRolePermission().getPermission();
    }

    /**
     * 获取当前用户的角色id
     *
     * @return
     */
    @NotAction
    protected String getCurrRoleId() {
        return this.getCurrUser().getUserRolePermission().getRoleID();
    }

    /**
     * 获取当前用户的管理层次
     *
     * @return
     */
    @NotAction
    protected String getCurrSysLevel() {
        return this.getCurrUser().getUser().getSysLevel();
    }

    /**
     * 获得当前用户管理的所有机构节点
     */
    @NotAction
    protected List<String> getCurrOrgCode(){
        List<String> userOrgs = new ArrayList<>();
        List<String> tempOrgs = new ArrayList<>();
        String minOrgCode = null;
        for (JSONObject jsonObject : this.getCurrOrgArray()) {
            String orgId = jsonObject.getString("orgId");
            B01 b01 = b01Dao.findById(orgId);
            tempOrgs.add(b01.getB0111());
            if (StrUtil.isEmpty(minOrgCode)) {
                minOrgCode = b01.getB0111();
            } else {
                if (b01.getB0111().length() < minOrgCode.length()) {
                    minOrgCode = b01.getB0111();
                }
            }
        }

        for (String tempOrg : tempOrgs) {
            if (!tempOrg.startsWith(minOrgCode)) {
                userOrgs.add(tempOrg);
            }
        }
        userOrgs.add(minOrgCode);
        return userOrgs;
    }

}
