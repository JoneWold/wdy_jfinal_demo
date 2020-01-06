package com.wdy.generator.postgreSQL.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

/**
 * 通用数据模型
 *
 * @author doge
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseMemUnitInstitution<M extends BaseMemUnitInstitution<M>> extends Model<M> implements IBean {

    public M setId(Long id) {
        set("id", id);
        return (M) this;
    }

    public Long getId() {
        return getLong("id");
    }

    public M setYear(Integer year) {
        set("year", year);
        return (M) this;
    }

    public Integer getYear() {
        return getInt("year");
    }

    public M setUnitCountLevel(String unitCountLevel) {
        set("unit_count_level", unitCountLevel);
        return (M) this;
    }

    public String getUnitCountLevel() {
        return getStr("unit_count_level");
    }

    public M setUnitCountLevelName(String unitCountLevelName) {
        set("unit_count_level_name", unitCountLevelName);
        return (M) this;
    }

    public String getUnitCountLevelName() {
        return getStr("unit_count_level_name");
    }

    public M setCareerUnitId(Long careerUnitId) {
        set("career_unit_id", careerUnitId);
        return (M) this;
    }

    public Long getCareerUnitId() {
        return getLong("career_unit_id");
    }

    public M setName(String name) {
        set("name", name);
        return (M) this;
    }

    public String getName() {
        return getStr("name");
    }

    public M setIdNum(String idNum) {
        set("id_num", idNum);
        return (M) this;
    }

    public String getIdNum() {
        return getStr("id_num");
    }

    public M setSex(Integer sex) {
        set("sex", sex);
        return (M) this;
    }

    public Integer getSex() {
        return getInt("sex");
    }

    public M setAge(Integer age) {
        set("age", age);
        return (M) this;
    }

    public Integer getAge() {
        return getInt("age");
    }

    public M setEducationFullTime(String educationFullTime) {
        set("education_full_time", educationFullTime);
        return (M) this;
    }

    public String getEducationFullTime() {
        return getStr("education_full_time");
    }

    public M setEducationFullTimeName(String educationFullTimeName) {
        set("education_full_time_name", educationFullTimeName);
        return (M) this;
    }

    public String getEducationFullTimeName() {
        return getStr("education_full_time_name");
    }

    public M setEthnic(String ethnic) {
        set("ethnic", ethnic);
        return (M) this;
    }

    public String getEthnic() {
        return getStr("ethnic");
    }

    public M setEducation(String education) {
        set("education", education);
        return (M) this;
    }

    public String getEducation() {
        return getStr("education");
    }

    public M setEducationName(String educationName) {
        set("education_name", educationName);
        return (M) this;
    }

    public String getEducationName() {
        return getStr("education_name");
    }

    public M setEducationDegree(String educationDegree) {
        set("education_degree", educationDegree);
        return (M) this;
    }

    public String getEducationDegree() {
        return getStr("education_degree");
    }

    public M setEducationDegreeName(String educationDegreeName) {
        set("education_degree_name", educationDegreeName);
        return (M) this;
    }

    public String getEducationDegreeName() {
        return getStr("education_degree_name");
    }

    public M setBirthdate(java.util.Date birthdate) {
        set("birthdate", birthdate);
        return (M) this;
    }

    public java.util.Date getBirthdate() {
        return get("birthdate");
    }

    public M setPoliticCountenance(String politicCountenance) {
        set("politic_countenance", politicCountenance);
        return (M) this;
    }

    public String getPoliticCountenance() {
        return getStr("politic_countenance");
    }

    public M setJoinWorkDate(java.util.Date joinWorkDate) {
        set("join_work_date", joinWorkDate);
        return (M) this;
    }

    public java.util.Date getJoinWorkDate() {
        return get("join_work_date");
    }

    public M setJobUnit(Long jobUnit) {
        set("job_unit", jobUnit);
        return (M) this;
    }

    public Long getJobUnit() {
        return getLong("job_unit");
    }

    public M setCurJob(String curJob) {
        set("cur_job", curJob);
        return (M) this;
    }

    public String getCurJob() {
        return getStr("cur_job");
    }

    public M setJobType(String jobType) {
        set("job_type", jobType);
        return (M) this;
    }

    public String getJobType() {
        return getStr("job_type");
    }

    public M setJobTypeName(String jobTypeName) {
        set("job_type_name", jobTypeName);
        return (M) this;
    }

    public String getJobTypeName() {
        return getStr("job_type_name");
    }

    public M setManagerJobLevel(String managerJobLevel) {
        set("manager_job_level", managerJobLevel);
        return (M) this;
    }

    public String getManagerJobLevel() {
        return getStr("manager_job_level");
    }

    public M setManagerJobLevelName(String managerJobLevelName) {
        set("manager_job_level_name", managerJobLevelName);
        return (M) this;
    }

    public String getManagerJobLevelName() {
        return getStr("manager_job_level_name");
    }

    public M setSpecialtyJobLevel(String specialtyJobLevel) {
        set("specialty_job_level", specialtyJobLevel);
        return (M) this;
    }

    public String getSpecialtyJobLevel() {
        return getStr("specialty_job_level");
    }

    public M setSpecialtyJobLevelName(String specialtyJobLevelName) {
        set("specialty_job_level_name", specialtyJobLevelName);
        return (M) this;
    }

    public String getSpecialtyJobLevelName() {
        return getStr("specialty_job_level_name");
    }

    public M setSourceJob(String sourceJob) {
        set("source_job", sourceJob);
        return (M) this;
    }

    public String getSourceJob() {
        return getStr("source_job");
    }

    public M setSourceJobName(String sourceJobName) {
        set("source_job_name", sourceJobName);
        return (M) this;
    }

    public String getSourceJobName() {
        return getStr("source_job_name");
    }

    public M setSelectType(String selectType) {
        set("select_type", selectType);
        return (M) this;
    }

    public String getSelectType() {
        return getStr("select_type");
    }

    public M setSelectTypeName(String selectTypeName) {
        set("select_type_name", selectTypeName);
        return (M) this;
    }

    public String getSelectTypeName() {
        return getStr("select_type_name");
    }

    public M setAppointType(String appointType) {
        set("appoint_type", appointType);
        return (M) this;
    }

    public String getAppointType() {
        return getStr("appoint_type");
    }

    public M setAppointTypeName(String appointTypeName) {
        set("appoint_type_name", appointTypeName);
        return (M) this;
    }

    public String getAppointTypeName() {
        return getStr("appoint_type_name");
    }

    public M setIsPromoted(Integer isPromoted) {
        set("is_promoted", isPromoted);
        return (M) this;
    }

    public Integer getIsPromoted() {
        return getInt("is_promoted");
    }

    public M setIsLeapfrogPromoted(Integer isLeapfrogPromoted) {
        set("is_leapfrog_promoted", isLeapfrogPromoted);
        return (M) this;
    }

    public Integer getIsLeapfrogPromoted() {
        return getInt("is_leapfrog_promoted");
    }

    public M setTermTargetInstitution(String termTargetInstitution) {
        set("term_target_institution", termTargetInstitution);
        return (M) this;
    }

    public String getTermTargetInstitution() {
        return getStr("term_target_institution");
    }

    public M setTermTargetInstitutionName(String termTargetInstitutionName) {
        set("term_target_institution_name", termTargetInstitutionName);
        return (M) this;
    }

    public String getTermTargetInstitutionName() {
        return getStr("term_target_institution_name");
    }

    public M setPreYearAssess(String preYearAssess) {
        set("pre_year_assess", preYearAssess);
        return (M) this;
    }

    public String getPreYearAssess() {
        return getStr("pre_year_assess");
    }

    public M setPreYearAssessName(String preYearAssessName) {
        set("pre_year_assess_name", preYearAssessName);
        return (M) this;
    }

    public String getPreYearAssessName() {
        return getStr("pre_year_assess_name");
    }

    public M setInYearAssess(String inYearAssess) {
        set("in_year_assess", inYearAssess);
        return (M) this;
    }

    public String getInYearAssess() {
        return getStr("in_year_assess");
    }

    public M setInYearAssessYear(String inYearAssessYear) {
        set("in_year_assess_year", inYearAssessYear);
        return (M) this;
    }

    public String getInYearAssessYear() {
        return getStr("in_year_assess_year");
    }

    public M setIsYearCount(Integer isYearCount) {
        set("is_year_count", isYearCount);
        return (M) this;
    }

    public Integer getIsYearCount() {
        return getInt("is_year_count");
    }

    public M setChangeStatus(Integer changeStatus) {
        set("change_status", changeStatus);
        return (M) this;
    }

    public Integer getChangeStatus() {
        return getInt("change_status");
    }

    public M setAddMemType(String addMemType) {
        set("add_mem_type", addMemType);
        return (M) this;
    }

    public String getAddMemType() {
        return getStr("add_mem_type");
    }

    public M setAddMemTypeName(String addMemTypeName) {
        set("add_mem_type_name", addMemTypeName);
        return (M) this;
    }

    public String getAddMemTypeName() {
        return getStr("add_mem_type_name");
    }

    public M setReduceMemType(String reduceMemType) {
        set("reduce_mem_type", reduceMemType);
        return (M) this;
    }

    public String getReduceMemType() {
        return getStr("reduce_mem_type");
    }

    public M setReduceMemTypeName(String reduceMemTypeName) {
        set("reduce_mem_type_name", reduceMemTypeName);
        return (M) this;
    }

    public String getReduceMemTypeName() {
        return getStr("reduce_mem_type_name");
    }

    public M setReduceJobGrowing(String reduceJobGrowing) {
        set("reduce_job_growing", reduceJobGrowing);
        return (M) this;
    }

    public String getReduceJobGrowing() {
        return getStr("reduce_job_growing");
    }

    public M setReduceJobGrowingName(String reduceJobGrowingName) {
        set("reduce_job_growing_name", reduceJobGrowingName);
        return (M) this;
    }

    public String getReduceJobGrowingName() {
        return getStr("reduce_job_growing_name");
    }

    public M setReduceDepose(String reduceDepose) {
        set("reduce_depose", reduceDepose);
        return (M) this;
    }

    public String getReduceDepose() {
        return getStr("reduce_depose");
    }

    public M setReduceDeposeName(String reduceDeposeName) {
        set("reduce_depose_name", reduceDeposeName);
        return (M) this;
    }

    public String getReduceDeposeName() {
        return getStr("reduce_depose_name");
    }

    public M setReduceResign(String reduceResign) {
        set("reduce_resign", reduceResign);
        return (M) this;
    }

    public String getReduceResign() {
        return getStr("reduce_resign");
    }

    public M setReduceResignName(String reduceResignName) {
        set("reduce_resign_name", reduceResignName);
        return (M) this;
    }

    public String getReduceResignName() {
        return getStr("reduce_resign_name");
    }

    public M setPunishJob(String punishJob) {
        set("punish_job", punishJob);
        return (M) this;
    }

    public String getPunishJob() {
        return getStr("punish_job");
    }

    public M setPunishJobType(String punishJobType) {
        set("punish_job_type", punishJobType);
        return (M) this;
    }

    public String getPunishJobType() {
        return getStr("punish_job_type");
    }

    public M setPunishCpcDiscipline(String punishCpcDiscipline) {
        set("punish_cpc_discipline", punishCpcDiscipline);
        return (M) this;
    }

    public String getPunishCpcDiscipline() {
        return getStr("punish_cpc_discipline");
    }

    public M setPunishCpcDisciplineName(String punishCpcDisciplineName) {
        set("punish_cpc_discipline_name", punishCpcDisciplineName);
        return (M) this;
    }

    public String getPunishCpcDisciplineName() {
        return getStr("punish_cpc_discipline_name");
    }

    public M setPunishPoliticsDiscipline(String punishPoliticsDiscipline) {
        set("punish_politics_discipline", punishPoliticsDiscipline);
        return (M) this;
    }

    public String getPunishPoliticsDiscipline() {
        return getStr("punish_politics_discipline");
    }

    public M setPunishPoliticsDisciplineName(String punishPoliticsDisciplineName) {
        set("punish_politics_discipline_name", punishPoliticsDisciplineName);
        return (M) this;
    }

    public String getPunishPoliticsDisciplineName() {
        return getStr("punish_politics_discipline_name");
    }

    public M setPunishPenalDiscipline(String punishPenalDiscipline) {
        set("punish_penal_discipline", punishPenalDiscipline);
        return (M) this;
    }

    public String getPunishPenalDiscipline() {
        return getStr("punish_penal_discipline");
    }

    public M setPunishPenalDisciplineName(String punishPenalDisciplineName) {
        set("punish_penal_discipline_name", punishPenalDisciplineName);
        return (M) this;
    }

    public String getPunishPenalDisciplineName() {
        return getStr("punish_penal_discipline_name");
    }

    public M setUnitAttributes(Integer unitAttributes) {
        set("unit_attributes", unitAttributes);
        return (M) this;
    }

    public Integer getUnitAttributes() {
        return getInt("unit_attributes");
    }

    public M setUnitAttributesName(String unitAttributesName) {
        set("unit_attributes_name", unitAttributesName);
        return (M) this;
    }

    public String getUnitAttributesName() {
        return getStr("unit_attributes_name");
    }

    public M setUnitType(Integer unitType) {
        set("unit_type", unitType);
        return (M) this;
    }

    public Integer getUnitType() {
        return getInt("unit_type");
    }

    public M setUnitTypeName(String unitTypeName) {
        set("unit_type_name", unitTypeName);
        return (M) this;
    }

    public String getUnitTypeName() {
        return getStr("unit_type_name");
    }

    public M setSyncTime(java.util.Date syncTime) {
        set("sync_time", syncTime);
        return (M) this;
    }

    public java.util.Date getSyncTime() {
        return get("sync_time");
    }

    public M setSelectNewAdd(Integer selectNewAdd) {
        set("select_new_add", selectNewAdd);
        return (M) this;
    }

    public Integer getSelectNewAdd() {
        return getInt("select_new_add");
    }

    public M setSelectReduce(Integer selectReduce) {
        set("select_reduce", selectReduce);
        return (M) this;
    }

    public Integer getSelectReduce() {
        return getInt("select_reduce");
    }

    public M setSelectPunish(Integer selectPunish) {
        set("select_punish", selectPunish);
        return (M) this;
    }

    public Integer getSelectPunish() {
        return getInt("select_punish");
    }

    public M setOrgCode(String orgCode) {
        set("org_code", orgCode);
        return (M) this;
    }

    public String getOrgCode() {
        return getStr("org_code");
    }

}
