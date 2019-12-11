package com.wdy.generator.postgreSQL.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseA14Temp<M extends BaseA14Temp<M>> extends Model<M> implements IBean {

	public void setImpId(java.lang.String impId) {
		set("impId", impId);
	}
	
	public java.lang.String getImpId() {
		return getStr("impId");
	}

	/**
	 * 人员统一标识符
	 */
	public void setA0000(java.lang.String A0000) {
		set("A0000", A0000);
	}
	
	/**
	 * 人员统一标识符
	 */
	public java.lang.String getA0000() {
		return getStr("A0000");
	}

	/**
	 * 奖惩信息ID
	 */
	public void setA1400(java.lang.String A1400) {
		set("A1400", A1400);
	}
	
	/**
	 * 奖惩信息ID
	 */
	public java.lang.String getA1400() {
		return getStr("A1400");
	}

	/**
	 * 奖惩名称   等
	 */
	public void setA1404A(java.lang.String A1404A) {
		set("A1404A", A1404A);
	}
	
	/**
	 * 奖惩名称   等
	 */
	public java.lang.String getA1404A() {
		return getStr("A1404A");
	}

	/**
	 * 奖惩代码   等
	 */
	public void setA1404B(java.lang.String A1404B) {
		set("A1404B", A1404B);
	}
	
	/**
	 * 奖惩代码   等
	 */
	public java.lang.String getA1404B() {
		return getStr("A1404B");
	}

	/**
	 * 奖惩批准日期   等
	 */
	public void setA1407(java.util.Date A1407) {
		set("A1407", A1407);
	}
	
	/**
	 * 奖惩批准日期   等
	 */
	public java.util.Date getA1407() {
		return get("A1407");
	}

	/**
	 * 奖惩批准机关名称   等
	 */
	public void setA1411A(java.lang.String A1411A) {
		set("A1411A", A1411A);
	}
	
	/**
	 * 奖惩批准机关名称   等
	 */
	public java.lang.String getA1411A() {
		return getStr("A1411A");
	}

	/**
	 * 批准奖惩机关级别   等
	 */
	public void setA1414(java.lang.String A1414) {
		set("A1414", A1414);
	}
	
	/**
	 * 批准奖惩机关级别   等
	 */
	public java.lang.String getA1414() {
		return getStr("A1414");
	}

	/**
	 * 奖惩时职务层次   等
	 */
	public void setA1415(java.lang.String A1415) {
		set("A1415", A1415);
	}
	
	/**
	 * 奖惩时职务层次   等
	 */
	public java.lang.String getA1415() {
		return getStr("A1415");
	}

	/**
	 * 奖惩撤销日期   等
	 */
	public void setA1424(java.util.Date A1424) {
		set("A1424", A1424);
	}
	
	/**
	 * 奖惩撤销日期   等
	 */
	public java.util.Date getA1424() {
		return get("A1424");
	}

	/**
	 * 批准机关性质   等
	 */
	public void setA1428(java.lang.String A1428) {
		set("A1428", A1428);
	}
	
	/**
	 * 批准机关性质   等
	 */
	public java.lang.String getA1428() {
		return getStr("A1428");
	}

	/**
	 * 标识
	 */
	public void setUPDATED(java.lang.String UPDATED) {
		set("UPDATED", UPDATED);
	}
	
	/**
	 * 标识
	 */
	public java.lang.String getUPDATED() {
		return getStr("UPDATED");
	}

	/**
	 * 排序字段
	 */
	public void setSORTID(java.lang.Integer SORTID) {
		set("SORTID", SORTID);
	}
	
	/**
	 * 排序字段
	 */
	public java.lang.Integer getSORTID() {
		return getInt("SORTID");
	}

	/**
	 * 0:是不输出 1:是输出 默认不输出
	 */
	public void setISDISABLED(java.lang.Integer ISDISABLED) {
		set("ISDISABLED", ISDISABLED);
	}
	
	/**
	 * 0:是不输出 1:是输出 默认不输出
	 */
	public java.lang.Integer getISDISABLED() {
		return getInt("ISDISABLED");
	}

	/**
	 * 奖惩说明
	 */
	public void setA1500(java.lang.String A1500) {
		set("A1500", A1500);
	}
	
	/**
	 * 奖惩说明
	 */
	public java.lang.String getA1500() {
		return getStr("A1500");
	}

}