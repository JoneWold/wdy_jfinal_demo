package com.wdy.generator.postgreSQL.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseYoungStudy<M extends BaseYoungStudy<M>> extends Model<M> implements IBean {

	/**
	 * 唯一标示
	 */
	public void setId(java.lang.String id) {
		set("id", id);
	}
	
	/**
	 * 唯一标示
	 */
	public java.lang.String getId() {
		return getStr("id");
	}

	/**
	 * 人员A0000
	 */
	public void setA0000(java.lang.String A0000) {
		set("A0000", A0000);
	}
	
	/**
	 * 人员A0000
	 */
	public java.lang.String getA0000() {
		return getStr("A0000");
	}

	/**
	 * 类型（0理论培训，1实践锻炼）
	 */
	public void setType(java.lang.Integer type) {
		set("type", type);
	}
	
	/**
	 * 类型（0理论培训，1实践锻炼）
	 */
	public java.lang.Integer getType() {
		return getInt("type");
	}

	/**
	 * 培训类型 ZB29
	 */
	public void setTrainType(java.lang.String trainType) {
		set("trainType", trainType);
	}
	
	/**
	 * 培训类型 ZB29
	 */
	public java.lang.String getTrainType() {
		return getStr("trainType");
	}

	/**
	 * 培训渠道 NQ_PXQD
	 */
	public void setTrainChannel(java.lang.String trainChannel) {
		set("trainChannel", trainChannel);
	}
	
	/**
	 * 培训渠道 NQ_PXQD
	 */
	public java.lang.String getTrainChannel() {
		return getStr("trainChannel");
	}

	/**
	 * 培训形式 NQ_PXXS
	 */
	public void setTrainMode(java.lang.String trainMode) {
		set("trainMode", trainMode);
	}
	
	/**
	 * 培训形式 NQ_PXXS
	 */
	public java.lang.String getTrainMode() {
		return getStr("trainMode");
	}

	/**
	 * 培训学时
	 */
	public void setTrainHours(java.lang.String trainHours) {
		set("trainHours", trainHours);
	}
	
	/**
	 * 培训学时
	 */
	public java.lang.String getTrainHours() {
		return getStr("trainHours");
	}

	/**
	 * 是否出国境培训（0否，1是）
	 */
	public void setIsTrainAbroad(java.lang.String isTrainAbroad) {
		set("isTrainAbroad", isTrainAbroad);
	}
	
	/**
	 * 是否出国境培训（0否，1是）
	 */
	public java.lang.String getIsTrainAbroad() {
		return getStr("isTrainAbroad");
	}

	/**
	 * 排序号
	 */
	public void setSortId(java.lang.Integer sortId) {
		set("sortId", sortId);
	}
	
	/**
	 * 排序号
	 */
	public java.lang.Integer getSortId() {
		return getInt("sortId");
	}

	/**
	 * 开始时间
	 */
	public void setStartTime(java.util.Date startTime) {
		set("startTime", startTime);
	}
	
	/**
	 * 开始时间
	 */
	public java.util.Date getStartTime() {
		return get("startTime");
	}

	/**
	 * 结束时间
	 */
	public void setEndTime(java.util.Date endTime) {
		set("endTime", endTime);
	}
	
	/**
	 * 结束时间
	 */
	public java.util.Date getEndTime() {
		return get("endTime");
	}

	/**
	 * 培训班名称
	 */
	public void setName(java.lang.String name) {
		set("name", name);
	}
	
	/**
	 * 培训班名称
	 */
	public java.lang.String getName() {
		return getStr("name");
	}

	/**
	 * 考核成绩
	 */
	public void setCheckResult(java.lang.String checkResult) {
		set("checkResult", checkResult);
	}
	
	/**
	 * 考核成绩
	 */
	public java.lang.String getCheckResult() {
		return getStr("checkResult");
	}

	/**
	 * 论文题目
	 */
	public void setTitle(java.lang.String title) {
		set("title", title);
	}
	
	/**
	 * 论文题目
	 */
	public java.lang.String getTitle() {
		return getStr("title");
	}

	/**
	 * 党性分析
	 */
	public void setAnalyze(java.lang.String analyze) {
		set("analyze", analyze);
	}
	
	/**
	 * 党性分析
	 */
	public java.lang.String getAnalyze() {
		return getStr("analyze");
	}

	/**
	 * 学习小结
	 */
	public void setConclusion(java.lang.String conclusion) {
		set("conclusion", conclusion);
	}
	
	/**
	 * 学习小结
	 */
	public java.lang.String getConclusion() {
		return getStr("conclusion");
	}

	/**
	 * 党员支部意见
	 */
	public void setZbOpinion(java.lang.String zbOpinion) {
		set("zbOpinion", zbOpinion);
	}
	
	/**
	 * 党员支部意见
	 */
	public java.lang.String getZbOpinion() {
		return getStr("zbOpinion");
	}

	/**
	 * 教务意见
	 */
	public void setJwOpinion(java.lang.String jwOpinion) {
		set("jwOpinion", jwOpinion);
	}
	
	/**
	 * 教务意见
	 */
	public java.lang.String getJwOpinion() {
		return getStr("jwOpinion");
	}

	/**
	 * 文件名
	 */
	public void setFileName(java.lang.String fileName) {
		set("fileName", fileName);
	}
	
	/**
	 * 文件名
	 */
	public java.lang.String getFileName() {
		return getStr("fileName");
	}

	/**
	 * 文件路径
	 */
	public void setFileUrl(java.lang.String fileUrl) {
		set("fileUrl", fileUrl);
	}
	
	/**
	 * 文件路径
	 */
	public java.lang.String getFileUrl() {
		return getStr("fileUrl");
	}

	/**
	 * 实践锻炼方式 NQ_DLFS
	 */
	public void setExerciseType(java.lang.String exerciseType) {
		set("exerciseType", exerciseType);
	}
	
	/**
	 * 实践锻炼方式 NQ_DLFS
	 */
	public java.lang.String getExerciseType() {
		return getStr("exerciseType");
	}

	/**
	 * 实践锻炼时职务
	 */
	public void setExerciseDuty(java.lang.String exerciseDuty) {
		set("exerciseDuty", exerciseDuty);
	}
	
	/**
	 * 实践锻炼时职务
	 */
	public java.lang.String getExerciseDuty() {
		return getStr("exerciseDuty");
	}

	/**
	 * 鉴定情况
	 */
	public void setExerciseAppraisal(java.lang.String exerciseAppraisal) {
		set("exerciseAppraisal", exerciseAppraisal);
	}
	
	/**
	 * 鉴定情况
	 */
	public java.lang.String getExerciseAppraisal() {
		return getStr("exerciseAppraisal");
	}

}