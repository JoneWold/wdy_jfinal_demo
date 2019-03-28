package com.wdy.generator.postgreSQL.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseA32<M extends BaseA32<M>> extends Model<M> implements IBean {

	/**
	 * 用户id
	 */
	public void setUSERID(java.lang.String USERID) {
		set("USERID", USERID);
	}
	
	/**
	 * 用户id
	 */
	public java.lang.String getUSERID() {
		return getStr("USERID");
	}

	/**
	 * 用户是否有效 1有效，0无效
	 */
	public void setUSEFUL(java.lang.String USEFUL) {
		set("USEFUL", USEFUL);
	}
	
	/**
	 * 用户是否有效 1有效，0无效
	 */
	public java.lang.String getUSEFUL() {
		return getStr("USEFUL");
	}

	/**
	 * 男公务员退休年龄
	 */
	public void setMAGE(java.lang.Integer MAGE) {
		set("MAGE", MAGE);
	}
	
	/**
	 * 男公务员退休年龄
	 */
	public java.lang.Integer getMAGE() {
		return getInt("MAGE");
	}

	/**
	 * 女公务员退休年龄
	 */
	public void setFMAGE(java.lang.Integer FMAGE) {
		set("FMAGE", FMAGE);
	}
	
	/**
	 * 女公务员退休年龄
	 */
	public java.lang.Integer getFMAGE() {
		return getInt("FMAGE");
	}

	/**
	 * 距离试用期到期时间
	 */
	public void setSYDAY(java.lang.Integer SYDAY) {
		set("SYDAY", SYDAY);
	}
	
	/**
	 * 距离试用期到期时间
	 */
	public java.lang.Integer getSYDAY() {
		return getInt("SYDAY");
	}

	/**
	 * 距离生日时间
	 */
	public void setBIRTHDAY(java.lang.Integer BIRTHDAY) {
		set("BIRTHDAY", BIRTHDAY);
	}
	
	/**
	 * 距离生日时间
	 */
	public java.lang.Integer getBIRTHDAY() {
		return getInt("BIRTHDAY");
	}

}
