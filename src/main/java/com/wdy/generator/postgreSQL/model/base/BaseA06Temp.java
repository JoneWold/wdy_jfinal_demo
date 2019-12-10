package com.wdy.generator.postgreSQL.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseA06Temp<M extends BaseA06Temp<M>> extends Model<M> implements IBean {

	public void setImpId(java.lang.String impId) {
		set("impId", impId);
	}
	
	public java.lang.String getImpId() {
		return getStr("impId");
	}

	/**
	 * 专业技术任职资格ID
	 */
	public void setA0600(java.lang.String A0600) {
		set("A0600", A0600);
	}
	
	/**
	 * 专业技术任职资格ID
	 */
	public java.lang.String getA0600() {
		return getStr("A0600");
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
	 * 专业技术任职资格代码   等
	 */
	public void setA0601(java.lang.String A0601) {
		set("A0601", A0601);
	}
	
	/**
	 * 专业技术任职资格代码   等
	 */
	public java.lang.String getA0601() {
		return getStr("A0601");
	}

	/**
	 * 专业技术任职资格名称   等
	 */
	public void setA0602(java.lang.String A0602) {
		set("A0602", A0602);
	}
	
	/**
	 * 专业技术任职资格名称   等
	 */
	public java.lang.String getA0602() {
		return getStr("A0602");
	}

	/**
	 * 获得资格日期   等
	 */
	public void setA0604(java.util.Date A0604) {
		set("A0604", A0604);
	}
	
	/**
	 * 获得资格日期   等
	 */
	public java.util.Date getA0604() {
		return get("A0604");
	}

	/**
	 * 取得资格途径   等
	 */
	public void setA0607(java.lang.String A0607) {
		set("A0607", A0607);
	}
	
	/**
	 * 取得资格途径   等
	 */
	public java.lang.String getA0607() {
		return getStr("A0607");
	}

	/**
	 * 评委会或考试名称   等
	 */
	public void setA0611(java.lang.String A0611) {
		set("A0611", A0611);
	}
	
	/**
	 * 评委会或考试名称   等
	 */
	public java.lang.String getA0611() {
		return getStr("A0611");
	}

	/**
	 * 当前专业技术任职资格标识
	 */
	public void setA0614(java.lang.String A0614) {
		set("A0614", A0614);
	}
	
	/**
	 * 当前专业技术任职资格标识
	 */
	public java.lang.String getA0614() {
		return getStr("A0614");
	}

	/**
	 * 输出. true—是；false—否。
	 */
	public void setA0699(java.lang.String A0699) {
		set("A0699", A0699);
	}
	
	/**
	 * 输出. true—是；false—否。
	 */
	public java.lang.String getA0699() {
		return getStr("A0699");
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
	 * 专业技术职务级别
	 */
	public void setA0615(java.lang.String A0615) {
		set("A0615", A0615);
	}
	
	/**
	 * 专业技术职务级别
	 */
	public java.lang.String getA0615() {
		return getStr("A0615");
	}

}
