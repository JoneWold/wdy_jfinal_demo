package com.wdy.generator.postgreSQL.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseA36Temp<M extends BaseA36Temp<M>> extends Model<M> implements IBean {

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
	 * 家庭成员及社会关系ID
	 */
	public void setA3600(java.lang.String A3600) {
		set("A3600", A3600);
	}
	
	/**
	 * 家庭成员及社会关系ID
	 */
	public java.lang.String getA3600() {
		return getStr("A3600");
	}

	/**
	 * 人员姓名   等
	 */
	public void setA3601(java.lang.String A3601) {
		set("A3601", A3601);
	}
	
	/**
	 * 人员姓名   等
	 */
	public java.lang.String getA3601() {
		return getStr("A3601");
	}

	/**
	 * 人员称谓   等
	 */
	public void setA3604A(java.lang.String A3604A) {
		set("A3604A", A3604A);
	}
	
	/**
	 * 人员称谓   等
	 */
	public java.lang.String getA3604A() {
		return getStr("A3604A");
	}

	/**
	 * 出生年月   等
	 */
	public void setA3607(java.util.Date A3607) {
		set("A3607", A3607);
	}
	
	/**
	 * 出生年月   等
	 */
	public java.util.Date getA3607() {
		return get("A3607");
	}

	/**
	 * 工作单位及职务   等
	 */
	public void setA3611(java.lang.String A3611) {
		set("A3611", A3611);
	}
	
	/**
	 * 工作单位及职务   等
	 */
	public java.lang.String getA3611() {
		return getStr("A3611");
	}

	/**
	 * 政治面貌   等
	 */
	public void setA3627(java.lang.String A3627) {
		set("A3627", A3627);
	}
	
	/**
	 * 政治面貌   等
	 */
	public java.lang.String getA3627() {
		return getStr("A3627");
	}

	/**
	 * 排序号   等
	 */
	public void setSORTID(java.lang.Integer SORTID) {
		set("SORTID", SORTID);
	}
	
	/**
	 * 排序号   等
	 */
	public java.lang.Integer getSORTID() {
		return getInt("SORTID");
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
	 * 身份证号码   多
	 */
	public void setA3684(java.lang.String A3684) {
		set("A3684", A3684);
	}
	
	/**
	 * 身份证号码   多
	 */
	public java.lang.String getA3684() {
		return getStr("A3684");
	}

	/**
	 * 输出标识
	 */
	public void setA3699(java.lang.Integer A3699) {
		set("A3699", A3699);
	}
	
	/**
	 * 输出标识
	 */
	public java.lang.Integer getA3699() {
		return getInt("A3699");
	}

	/**
	 * 上传文件类型：1（lrm,lrmx,pic）,2（zip）
	 */
	public void setType(java.lang.String type) {
		set("type", type);
	}
	
	/**
	 * 上传文件类型：1（lrm,lrmx,pic）,2（zip）
	 */
	public java.lang.String getType() {
		return getStr("type");
	}

}
