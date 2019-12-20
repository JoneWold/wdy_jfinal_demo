package com.wdy.generator.postgreSQL.model.base;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseA36<M extends BaseA36<M>> extends Model<M> implements IBean {

    /**
     * 人员统一标识符
     */
    public M setA0000(java.lang.String A0000) {
        set("A0000", A0000);
        return (M) this;
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
    public M setA3600(java.lang.String A3600) {
        set("A3600", A3600);
        return (M) this;
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
    public M setA3601(java.lang.String A3601) {
        set("A3601", A3601);
        return (M) this;
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
    public M setA3604A(java.lang.String A3604A) {
        set("A3604A", A3604A);
        return (M) this;
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
    public M setA3607(java.util.Date A3607) {
        set("A3607", A3607);
        return (M) this;
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
    public M setA3611(java.lang.String A3611) {
        set("A3611", A3611);
        return (M) this;
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
    public M setA3627(java.lang.String A3627) {
        set("A3627", A3627);
        return (M) this;
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
    public M setSORTID(java.lang.Integer SORTID) {
        set("SORTID", SORTID);
        return (M) this;
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
    public M setUPDATED(java.lang.String UPDATED) {
        set("UPDATED", UPDATED);
        return (M) this;
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
    public M setA3684(java.lang.String A3684) {
        set("A3684", A3684);
        return (M) this;
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
    public M setA3699(java.lang.Integer A3699) {
        set("A3699", A3699);
        return (M) this;
    }

    /**
     * 输出标识
     */
    public java.lang.Integer getA3699() {
        return getInt("A3699");
    }
}
