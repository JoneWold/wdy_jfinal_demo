package com.wdy.generator.postgreSQL.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

/**
 * 通用数据模型
 *
 * @author doge
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseUnitInstitution<M extends BaseUnitInstitution<M>> extends Model<M> implements IBean {

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

    public M setOrgCode(String orgCode) {
        set("org_code", orgCode);
        return (M) this;
    }

    public String getOrgCode() {
        return getStr("org_code");
    }

    public M setName(String name) {
        set("name", name);
        return (M) this;
    }

    public String getName() {
        return getStr("name");
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

    public M setContact(String contact) {
        set("contact", contact);
        return (M) this;
    }

    public String getContact() {
        return getStr("contact");
    }

    public M setPhone(String phone) {
        set("phone", phone);
        return (M) this;
    }

    public String getPhone() {
        return getStr("phone");
    }

    public M setUnitAttributes(String unitAttributes) {
        set("unit_attributes", unitAttributes);
        return (M) this;
    }

    public String getUnitAttributes() {
        return getStr("unit_attributes");
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

    public M setTermInstitution(String termInstitution) {
        set("term_institution", termInstitution);
        return (M) this;
    }

    public String getTermInstitution() {
        return getStr("term_institution");
    }

    public M setTermInstitutionName(String termInstitutionName) {
        set("term_institution_name", termInstitutionName);
        return (M) this;
    }

    public String getTermInstitutionName() {
        return getStr("term_institution_name");
    }

    public M setTermTargetInstitution(Integer termTargetInstitution) {
        set("term_target_institution", termTargetInstitution);
        return (M) this;
    }

    public Integer getTermTargetInstitution() {
        return getInt("term_target_institution");
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

    public M setInYearAssessName(String inYearAssessName) {
        set("in_year_assess_name", inYearAssessName);
        return (M) this;
    }

    public String getInYearAssessName() {
        return getStr("in_year_assess_name");
    }

    public M setPreMainLeader(Integer preMainLeader) {
        set("pre_main_leader", preMainLeader);
        return (M) this;
    }

    public Integer getPreMainLeader() {
        return getInt("pre_main_leader");
    }

    public M setMainLeader(Integer mainLeader) {
        set("main_leader", mainLeader);
        return (M) this;
    }

    public Integer getMainLeader() {
        return getInt("main_leader");
    }

    public M setPreMinorLeader(Integer preMinorLeader) {
        set("pre_minor_leader", preMinorLeader);
        return (M) this;
    }

    public Integer getPreMinorLeader() {
        return getInt("pre_minor_leader");
    }

    public M setMinorLeader(Integer minorLeader) {
        set("minor_leader", minorLeader);
        return (M) this;
    }

    public Integer getMinorLeader() {
        return getInt("minor_leader");
    }

    public M setPreOtherLeader(Integer preOtherLeader) {
        set("pre_other_leader", preOtherLeader);
        return (M) this;
    }

    public Integer getPreOtherLeader() {
        return getInt("pre_other_leader");
    }

    public M setOtherLeader(Integer otherLeader) {
        set("other_leader", otherLeader);
        return (M) this;
    }

    public Integer getOtherLeader() {
        return getInt("other_leader");
    }

    public M setSyncTime(java.util.Date syncTime) {
        set("sync_time", syncTime);
        return (M) this;
    }

    public java.util.Date getSyncTime() {
        return get("sync_time");
    }

    public Integer getSumLeaderDuty() {
        return getInt("sum_leader_duty");
    }

    public Integer getSumLeader() {
        return getInt("sum_leader");
    }

    public M setSumLeaderDuty(Integer sum_leader_duty) {
        set("sum_leader_duty", sum_leader_duty);
        return (M) this;
    }

    public M setSumLeader(Integer sum_leader) {
        set("sum_leader", sum_leader);
        return (M) this;
    }

}
