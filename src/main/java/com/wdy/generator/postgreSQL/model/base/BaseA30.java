package com.wdy.generator.postgreSQL.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseA30<M extends BaseA30<M>> extends Model<M> implements IBean {

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
	 * 退出方式
	 */
	public void setA3001(java.lang.String A3001) {
		set("A3001", A3001);
	}
	
	/**
	 * 退出方式
	 */
	public java.lang.String getA3001() {
		return getStr("A3001");
	}

	/**
	 * 退出时间
	 */
	public void setA3004(java.util.Date A3004) {
		set("A3004", A3004);
	}
	
	/**
	 * 退出时间
	 */
	public java.util.Date getA3004() {
		return get("A3004");
	}

	/**
	 * 离退后现管理单位名称   多
	 */
	public void setA3117A(java.lang.String A3117A) {
		set("A3117A", A3117A);
	}
	
	/**
	 * 离退后现管理单位名称   多
	 */
	public java.lang.String getA3117A() {
		return getStr("A3117A");
	}

	/**
	 * 离退类别   多
	 */
	public void setA3101(java.lang.String A3101) {
		set("A3101", A3101);
	}
	
	/**
	 * 离退类别   多
	 */
	public java.lang.String getA3101() {
		return getStr("A3101");
	}

	/**
	 * 离退批准文号   多
	 */
	public void setA3137(java.lang.String A3137) {
		set("A3137", A3137);
	}
	
	/**
	 * 离退批准文号   多
	 */
	public java.lang.String getA3137() {
		return getStr("A3137");
	}

	public void setA3034(java.lang.String A3034) {
		set("A3034", A3034);
	}
	
	public java.lang.String getA3034() {
		return getStr("A3034");
	}

	/**
	 * 标识   少
	 */
	public void setUPDATED(java.lang.String UPDATED) {
		set("UPDATED", UPDATED);
	}
	
	/**
	 * 标识   少
	 */
	public java.lang.String getUPDATED() {
		return getStr("UPDATED");
	}

	/**
	 * 退出去向
	 */
	public void setA3007A(java.lang.String A3007A) {
		set("A3007A", A3007A);
	}
	
	/**
	 * 退出去向
	 */
	public java.lang.String getA3007A() {
		return getStr("A3007A");
	}

	/**
	 * 批准单位
	 */
	public void setA3038(java.lang.String A3038) {
		set("A3038", A3038);
	}
	
	/**
	 * 批准单位
	 */
	public java.lang.String getA3038() {
		return getStr("A3038");
	}

	/**
	 * 主键
	 */
	public void setA3000(java.lang.String A3000) {
		set("A3000", A3000);
	}
	
	/**
	 * 主键
	 */
	public java.lang.String getA3000() {
		return getStr("A3000");
	}

	/**
	 * 接收标识
	 */
	public void setA3999(java.lang.Integer A3999) {
		set("A3999", A3999);
	}
	
	/**
	 * 接收标识
	 */
	public java.lang.Integer getA3999() {
		return getInt("A3999");
	}

	/**
	 * 退出去向,单位id
	 */
	public void setA3007B(java.lang.String A3007B) {
		set("A3007B", A3007B);
	}
	
	/**
	 * 退出去向,单位id
	 */
	public java.lang.String getA3007B() {
		return getStr("A3007B");
	}

	/**
	 * 退出时职务
	 */
	public void setA3008(java.lang.String A3008) {
		set("A3008", A3008);
	}
	
	/**
	 * 退出时职务
	 */
	public java.lang.String getA3008() {
		return getStr("A3008");
	}

	/**
	 * 退出时主职务所在单位
	 */
	public void setA3008A(java.lang.String A3008A) {
		set("A3008A", A3008A);
	}
	
	/**
	 * 退出时主职务所在单位
	 */
	public java.lang.String getA3008A() {
		return getStr("A3008A");
	}

	/**
	 * 0:是全息 1:是公务员 。。。。
	 */
	public void setSystem(java.lang.String system) {
		set("system", system);
	}
	
	/**
	 * 0:是全息 1:是公务员 。。。。
	 */
	public java.lang.String getSystem() {
		return getStr("system");
	}

}