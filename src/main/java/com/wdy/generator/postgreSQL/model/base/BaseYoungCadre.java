package com.wdy.generator.postgreSQL.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseYoungCadre<M extends BaseYoungCadre<M>> extends Model<M> implements IBean {

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
     * a01的A0000字段
     */
    public void setA0000(java.lang.String A0000) {
        set("A0000", A0000);
    }

    /**
     * a01的A0000字段
     */
    public java.lang.String getA0000() {
        return getStr("A0000");
    }

    /**
     * 职称代码
     */
    public void setJobCall(java.lang.String jobCall) {
        set("jobCall", jobCall);
    }

    /**
     * 职称代码
     */
    public java.lang.String getJobCall() {
        return getStr("jobCall");
    }

    /**
     * 熟悉领域
     */
    public void setFamiliarRelam(java.lang.String familiarRelam) {
        set("familiarRelam", familiarRelam);
    }

    /**
     * 熟悉领域
     */
    public java.lang.String getFamiliarRelam() {
        return getStr("familiarRelam");
    }

    /**
     * 区县、乡镇街道正职经历
     */
    public void setIsCountyVillagesExperience(java.lang.Integer isCountyVillagesExperience) {
        set("isCountyVillagesExperience", isCountyVillagesExperience);
    }

    /**
     * 区县、乡镇街道正职经历
     */
    public java.lang.Integer getIsCountyVillagesExperience() {
        return getInt("isCountyVillagesExperience");
    }

    /**
     * 乡镇街道及村社区工作经历
     */
    public void setIsVillagesCommunityExperience(java.lang.Integer isVillagesCommunityExperience) {
        set("isVillagesCommunityExperience", isVillagesCommunityExperience);
    }

    /**
     * 乡镇街道及村社区工作经历
     */
    public java.lang.Integer getIsVillagesCommunityExperience() {
        return getInt("isVillagesCommunityExperience");
    }

    /**
     * 有团工作经历
     */
    public void setIsTeamExperience(java.lang.Integer isTeamExperience) {
        set("isTeamExperience", isTeamExperience);
    }

    /**
     * 有团工作经历
     */
    public java.lang.Integer getIsTeamExperience() {
        return getInt("isTeamExperience");
    }

    /**
     * 有秘书经验
     */
    public void setIsSecretaryExperience(java.lang.Integer isSecretaryExperience) {
        set("isSecretaryExperience", isSecretaryExperience);
    }

    /**
     * 有秘书经验
     */
    public java.lang.Integer getIsSecretaryExperience() {
        return getInt("isSecretaryExperience");
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
     * 培训方向字典表
     */
    public void setTrainingDirection(java.lang.String trainingDirection) {
        set("trainingDirection", trainingDirection);
    }

    /**
     * 培训方向字典表
     */
    public java.lang.String getTrainingDirection() {
        return getStr("trainingDirection");
    }

    /**
     * 培训期限字典表
     */
    public void setTrainingDone(java.lang.String trainingDone) {
        set("trainingDone", trainingDone);
    }

    /**
     * 培训期限字典表
     */
    public java.lang.String getTrainingDone() {
        return getStr("trainingDone");
    }

    /**
     * 1 是 退出 0 是在系统出显示，永远保证 一个人 只有一个0 多条退出是可行的
     */
    public void setType(java.lang.String type) {
        set("type", type);
    }

    /**
     * 1 是 退出 0 是在系统出显示，永远保证 一个人 只有一个0 多条退出是可行的
     */
    public java.lang.String getType() {
        return getStr("type");
    }

    /**
     * 进入优秀年轻干部时间
     */
    public void setJoinDate(java.util.Date joinDate) {
        set("joinDate", joinDate);
    }

    /**
     * 进入优秀年轻干部时间
     */
    public java.util.Date getJoinDate() {
        return get("joinDate");
    }

    /**
     * 进入年
     */
    public void setJoinYear(java.lang.Integer joinYear) {
        set("joinYear", joinYear);
    }

    /**
     * 进入年
     */
    public java.lang.Integer getJoinYear() {
        return getInt("joinYear");
    }

    /**
     * 进入月
     */
    public void setJoinMonth(java.lang.Integer joinMonth) {
        set("joinMonth", joinMonth);
    }

    /**
     * 进入月
     */
    public java.lang.Integer getJoinMonth() {
        return getInt("joinMonth");
    }

    /**
     * 进入时的机构id
     */
    public void setJoinOrg(java.lang.String joinOrg) {
        set("joinOrg", joinOrg);
    }

    /**
     * 进入时的机构id
     */
    public java.lang.String getJoinOrg() {
        return getStr("joinOrg");
    }

    /**
     * 进入时现任职务
     */
    public void setJoinJob(java.lang.String joinJob) {
        set("joinJob", joinJob);
    }

    /**
     * 进入时现任职务
     */
    public java.lang.String getJoinJob() {
        return getStr("joinJob");
    }

    /**
     * 进入时职务层次
     */
    public void setJoinJobLevel(java.lang.String joinJobLevel) {
        set("joinJobLevel", joinJobLevel);
    }

    /**
     * 进入时职务层次
     */
    public java.lang.String getJoinJobLevel() {
        return getStr("joinJobLevel");
    }

    /**
     * 进入时的职级
     */
    public void setJoinJobRank(java.lang.String joinJobRank) {
        set("joinJobRank", joinJobRank);
    }

    /**
     * 进入时的职级
     */
    public java.lang.String getJoinJobRank() {
        return getStr("joinJobRank");
    }

    /**
     * 进入时最高学历
     */
    public void setJoinEducation(java.lang.String joinEducation) {
        set("joinEducation", joinEducation);
    }

    /**
     * 进入时最高学历
     */
    public java.lang.String getJoinEducation() {
        return getStr("joinEducation");
    }

    /**
     * 进入时全日制高学历
     */
    public void setJoinFullTimeEducation(java.lang.String joinFullTimeEducation) {
        set("joinFullTimeEducation", joinFullTimeEducation);
    }

    /**
     * 进入时全日制高学历
     */
    public java.lang.String getJoinFullTimeEducation() {
        return getStr("joinFullTimeEducation");
    }

    /**
     * 进入时在职最高学历
     */
    public void setJoinJobEducation(java.lang.String joinJobEducation) {
        set("joinJobEducation", joinJobEducation);
    }

    /**
     * 进入时在职最高学历
     */
    public java.lang.String getJoinJobEducation() {
        return getStr("joinJobEducation");
    }

    /**
     * 退出优秀年轻干部时间
     */
    public void setOutDate(java.util.Date outDate) {
        set("outDate", outDate);
    }

    /**
     * 退出优秀年轻干部时间
     */
    public java.util.Date getOutDate() {
        return get("outDate");
    }

    /**
     * 退出年
     */
    public void setOutYear(java.lang.Integer outYear) {
        set("outYear", outYear);
    }

    /**
     * 退出年
     */
    public java.lang.Integer getOutYear() {
        return getInt("outYear");
    }

    /**
     * 退出月
     */
    public void setOutMonth(java.lang.Integer outMonth) {
        set("outMonth", outMonth);
    }

    /**
     * 退出月
     */
    public java.lang.Integer getOutMonth() {
        return getInt("outMonth");
    }

    /**
     * 退出时的类型
     */
    public void setOutType(java.lang.String outType) {
        set("outType", outType);
    }

    /**
     * 退出时的类型
     */
    public java.lang.String getOutType() {
        return getStr("outType");
    }

    /**
     * 退出时的orgId
     */
    public void setOutOrg(java.lang.String outOrg) {
        set("outOrg", outOrg);
    }

    /**
     * 退出时的orgId
     */
    public java.lang.String getOutOrg() {
        return getStr("outOrg");
    }

    /**
     * 退出时现任职务
     */
    public void setOutJob(java.lang.String outJob) {
        set("outJob", outJob);
    }

    /**
     * 退出时现任职务
     */
    public java.lang.String getOutJob() {
        return getStr("outJob");
    }

    /**
     * 退出时职务层次
     */
    public void setOutJobLevel(java.lang.String outJobLevel) {
        set("outJobLevel", outJobLevel);
    }

    /**
     * 退出时职务层次
     */
    public java.lang.String getOutJobLevel() {
        return getStr("outJobLevel");
    }

    /**
     * 退出时的职级
     */
    public void setOutJobRank(java.lang.String outJobRank) {
        set("outJobRank", outJobRank);
    }

    /**
     * 退出时的职级
     */
    public java.lang.String getOutJobRank() {
        return getStr("outJobRank");
    }

    /**
     * 退出时最高学历
     */
    public void setOutEducation(java.lang.String outEducation) {
        set("outEducation", outEducation);
    }

    /**
     * 退出时最高学历
     */
    public java.lang.String getOutEducation() {
        return getStr("outEducation");
    }

    /**
     * 退出时全日制高学历
     */
    public void setOutFullTimeEducation(java.lang.String outFullTimeEducation) {
        set("outFullTimeEducation", outFullTimeEducation);
    }

    /**
     * 退出时全日制高学历
     */
    public java.lang.String getOutFullTimeEducation() {
        return getStr("outFullTimeEducation");
    }

    /**
     * 退出时在职最高学历
     */
    public void setOutJobEducation(java.lang.String outJobEducation) {
        set("outJobEducation", outJobEducation);
    }

    /**
     * 退出时在职最高学历
     */
    public java.lang.String getOutJobEducation() {
        return getStr("outJobEducation");
    }

    /**
     * 排序字段
     */
    public void setSortId(java.lang.Integer sortId) {
        set("sortId", sortId);
    }

    /**
     * 排序字段
     */
    public java.lang.Integer getSortId() {
        return getInt("sortId");
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


    public void setOtherOrgId(String otherOrgId) {
        set("otherOrgId", otherOrgId);
    }

    public String getOtherOrgId() {
        return getStr("otherOrgId");
    }

    /**
     * 优秀年轻干部 勾选的单位OrgTreeSort
     */
    public void setOrgTreeSort(Integer orgTreeSort) {
        set("orgTreeSort", orgTreeSort);
    }

    /**
     * 优秀年轻干部 勾选的单位OrgTreeSort
     */
    public Integer getOrgTreeSort() {
        return getInt("orgTreeSort");
    }


    /**
     * 是否四必核查人员
     */
    public void setIsFourMustCheck(Integer isFourMustCheck) {
        set("isFourMustCheck", isFourMustCheck);
    }

    /**
     * 优秀年轻干部 勾选的单位OrgTreeSort
     */
    public Integer getIsFourMustCheck() {
        return getInt("isFourMustCheck");
    }
}
