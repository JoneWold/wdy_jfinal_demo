package com.wdy.generator.postgreSQL.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseWzHonorAward<M extends BaseWzHonorAward<M>> extends Model<M> implements IBean {

	/**
	 * 主键
	 */
	public void setId(java.lang.String id) {
		set("id", id);
	}
	
	/**
	 * 主键
	 */
	public java.lang.String getId() {
		return getStr("id");
	}

	/**
	 * 人员标识符
	 */
	public void setA0000(java.lang.String A0000) {
		set("A0000", A0000);
	}
	
	/**
	 * 人员标识符
	 */
	public java.lang.String getA0000() {
		return getStr("A0000");
	}

	/**
	 * 奖励时间
	 */
	public void setAwardTime(java.util.Date awardTime) {
		set("awardTime", awardTime);
	}
	
	/**
	 * 奖励时间
	 */
	public java.util.Date getAwardTime() {
		return get("awardTime");
	}

	/**
	 * 主要奖项或荣誉称号
	 */
	public void setAwards(java.lang.String awards) {
		set("awards", awards);
	}
	
	/**
	 * 主要奖项或荣誉称号
	 */
	public java.lang.String getAwards() {
		return getStr("awards");
	}

	/**
	 * 授奖部门
	 */
	public void setDepartment(java.lang.String department) {
		set("department", department);
	}
	
	/**
	 * 授奖部门
	 */
	public java.lang.String getDepartment() {
		return getStr("department");
	}

	/**
	 * 备注
	 */
	public void setRemark(java.lang.String remark) {
		set("remark", remark);
	}
	
	/**
	 * 备注
	 */
	public java.lang.String getRemark() {
		return getStr("remark");
	}

	/**
	 * 附件
	 */
	public void setFiles(java.lang.String files) {
		set("files", files);
	}
	
	/**
	 * 附件
	 */
	public java.lang.String getFiles() {
		return getStr("files");
	}

	/**
	 * 创建时间
	 */
	public void setCreateTime(java.util.Date createTime) {
		set("createTime", createTime);
	}
	
	/**
	 * 创建时间
	 */
	public java.util.Date getCreateTime() {
		return get("createTime");
	}

	/**
	 * 修改时间
	 */
	public void setUpdateTime(java.util.Date updateTime) {
		set("updateTime", updateTime);
	}
	
	/**
	 * 修改时间
	 */
	public java.util.Date getUpdateTime() {
		return get("updateTime");
	}

}
