package com.wdy.biz.org.dao;

import com.jfinal.aop.Aop;
import com.wdy.generator.postgreSQL.model.B01;

import static com.wdy.constant.DBConstant.DB_PGSQL;

/**
 * @author wgch
 * @Description
 * @date 2019/4/29 11:28
 */
public class OrgDao {

    private B01 b01 = Aop.get(B01.class).use(DB_PGSQL);

    public B01 findById(String orgId) {
        return b01.findById(orgId);
    }


}
