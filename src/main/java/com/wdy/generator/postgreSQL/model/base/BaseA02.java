package com.wdy.generator.postgreSQL.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseA02<M extends BaseA02<M>> extends Model<M> implements IBean {

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
	 * 主键
	 */
	public void setA0200(java.lang.String A0200) {
		set("A0200", A0200);
	}
	
	/**
	 * 主键
	 */
	public java.lang.String getA0200() {
		return getStr("A0200");
	}

	/**
	 * 任职机构名称   等
	 */
	public void setA0201A(java.lang.String A0201A) {
		set("A0201A", A0201A);
	}
	
	/**
	 * 任职机构名称   等
	 */
	public java.lang.String getA0201A() {
		return getStr("A0201A");
	}

	/**
	 * 任职机构代码   等
	 */
	public void setA0201B(java.lang.String A0201B) {
		set("A0201B", A0201B);
	}
	
	/**
	 * 任职机构代码   等
	 */
	public java.lang.String getA0201B() {
		return getStr("A0201B");
	}

	/**
	 * 是否班子成员   等
	 */
	public void setA0201D(java.lang.String A0201D) {
		set("A0201D", A0201D);
	}
	
	/**
	 * 是否班子成员   等
	 */
	public java.lang.String getA0201D() {
		return getStr("A0201D");
	}

	/**
	 * 班子成员类别   等
	 */
	public void setA0201E(java.lang.String A0201E) {
		set("A0201E", A0201E);
	}
	
	/**
	 * 班子成员类别   等
	 */
	public java.lang.String getA0201E() {
		return getStr("A0201E");
	}

	/**
	 * 职务名称   等
	 */
	public void setA0215A(java.lang.String A0215A) {
		set("A0215A", A0215A);
	}
	
	/**
	 * 职务名称   等
	 */
	public java.lang.String getA0215A() {
		return getStr("A0215A");
	}

	/**
	 * 是否领导职务   等
	 */
	public void setA0219(java.lang.String A0219) {
		set("A0219", A0219);
	}
	
	/**
	 * 是否领导职务   等
	 */
	public java.lang.String getA0219() {
		return getStr("A0219");
	}

	/**
	 * 职务排序   等
	 */
	public void setA0223(java.lang.Integer A0223) {
		set("A0223", A0223);
	}
	
	/**
	 * 职务排序   等
	 */
	public java.lang.Integer getA0223() {
		return getInt("A0223");
	}

	/**
	 * 集体内排序   等
	 */
	public void setA0225(java.lang.String A0225) {
		set("A0225", A0225);
	}
	
	/**
	 * 集体内排序   等
	 */
	public java.lang.String getA0225() {
		return getStr("A0225");
	}

	/**
	 * 任职时间   等
	 */
	public void setA0243(java.util.Date A0243) {
		set("A0243", A0243);
	}
	
	/**
	 * 任职时间   等
	 */
	public java.util.Date getA0243() {
		return get("A0243");
	}

	public void setA0245(java.lang.String A0245) {
		set("A0245", A0245);
	}
	
	public java.lang.String getA0245() {
		return getStr("A0245");
	}

	/**
	 * 任职方式   等
	 */
	public void setA0247(java.lang.String A0247) {
		set("A0247", A0247);
	}
	
	/**
	 * 任职方式   等
	 */
	public java.lang.String getA0247() {
		return getStr("A0247");
	}

	/**
	 * 是否破格提拔   等
	 */
	public void setA0251B(java.lang.String A0251B) {
		set("A0251B", A0251B);
	}
	
	/**
	 * 是否破格提拔   等
	 */
	public java.lang.String getA0251B() {
		return getStr("A0251B");
	}

	/**
	 * 任职状态   等 1在任，0免任
	 */
	public void setA0255(java.lang.String A0255) {
		set("A0255", A0255);
	}
	
	/**
	 * 任职状态   等 1在任，0免任
	 */
	public java.lang.String getA0255() {
		return getStr("A0255");
	}

	/**
	 * 免职时间   等
	 */
	public void setA0265(java.util.Date A0265) {
		set("A0265", A0265);
	}
	
	/**
	 * 免职时间   等
	 */
	public java.util.Date getA0265() {
		return get("A0265");
	}

	/**
	 * 免职文号   等
	 */
	public void setA0267(java.lang.String A0267) {
		set("A0267", A0267);
	}
	
	/**
	 * 免职文号   等
	 */
	public java.lang.String getA0267() {
		return getStr("A0267");
	}

	/**
	 * 职务变动原因综述   多
	 */
	public void setA0272(java.lang.String A0272) {
		set("A0272", A0272);
	}
	
	/**
	 * 职务变动原因综述   多
	 */
	public java.lang.String getA0272() {
		return getStr("A0272");
	}

	/**
	 * 职务输出标识   等
	 */
	public void setA0281(java.lang.String A0281) {
		set("A0281", A0281);
	}
	
	/**
	 * 职务输出标识   等
	 */
	public java.lang.String getA0281() {
		return getStr("A0281");
	}

	/**
	 * 县处级正职任职年限   少
	 */
	public void setA0221T(java.lang.Integer A0221T) {
		set("A0221T", A0221T);
	}
	
	/**
	 * 县处级正职任职年限   少
	 */
	public java.lang.Integer getA0221T() {
		return getInt("A0221T");
	}

	/**
	 * 删除   少
	 */
	public void setB0238(java.lang.String B0238) {
		set("B0238", B0238);
	}
	
	/**
	 * 删除   少
	 */
	public java.lang.String getB0238() {
		return getStr("B0238");
	}

	/**
	 * 删除   少
	 */
	public void setB0239(java.lang.String B0239) {
		set("B0239", B0239);
	}
	
	/**
	 * 删除   少
	 */
	public java.lang.String getB0239() {
		return getStr("B0239");
	}

	/**
	 * 职务等级 ZB136 （采集）   少
	 */
	public void setA0221A(java.lang.String A0221A) {
		set("A0221A", A0221A);
	}
	
	/**
	 * 职务等级 ZB136 （采集）   少
	 */
	public java.lang.String getA0221A() {
		return getStr("A0221A");
	}

	/**
	 * ？   少
	 */
	public void setWageUsed(java.lang.Integer wageUsed) {
		set("WAGE_USED", wageUsed);
	}
	
	/**
	 * ？   少
	 */
	public java.lang.Integer getWageUsed() {
		return getInt("WAGE_USED");
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
	 * 交流去向 ZB74   少
	 */
	public void setA4907(java.lang.String A4907) {
		set("A4907", A4907);
	}
	
	/**
	 * 交流去向 ZB74   少
	 */
	public java.lang.String getA4907() {
		return getStr("A4907");
	}

	/**
	 * 交流原因 ZB73    少
	 */
	public void setA4904(java.lang.String A4904) {
		set("A4904", A4904);
	}
	
	/**
	 * 交流原因 ZB73    少
	 */
	public java.lang.String getA4904() {
		return getStr("A4904");
	}

	/**
	 * 交流方式 ZB72    少
	 */
	public void setA4901(java.lang.String A4901) {
		set("A4901", A4901);
	}
	
	/**
	 * 交流方式 ZB72    少
	 */
	public java.lang.String getA4901() {
		return getStr("A4901");
	}

	/**
	 * 统计关系所在单位？   少
	 */
	public void setA0299(java.lang.String A0299) {
		set("A0299", A0299);
	}
	
	/**
	 * 统计关系所在单位？   少
	 */
	public java.lang.String getA0299() {
		return getStr("A0299");
	}

	/**
	 * 最高职务标志？   少
	 */
	public void setA0295(java.lang.String A0295) {
		set("A0295", A0295);
	}
	
	/**
	 * 最高职务标志？   少
	 */
	public java.lang.String getA0295() {
		return getStr("A0295");
	}

	/**
	 * 任前一职务层次时间？   少
	 */
	public void setA0289(java.lang.String A0289) {
		set("A0289", A0289);
	}
	
	/**
	 * 任前一职务层次时间？   少
	 */
	public java.lang.String getA0289() {
		return getStr("A0289");
	}

	/**
	 * 任职务层次时间(该人开始担任此职务所属职务层次的时间) （采集）   少
	 */
	public void setA0288(java.lang.String A0288) {
		set("A0288", A0288);
	}
	
	/**
	 * 任职务层次时间(该人开始担任此职务所属职务层次的时间) （采集）   少
	 */
	public java.lang.String getA0288() {
		return getStr("A0288");
	}

	/**
	 * 交流标识(免去该职务时是否交流任职的标识。1—是；0—否) XZ09   少
	 */
	public void setA0284(java.lang.String A0284) {
		set("A0284", A0284);
	}
	
	/**
	 * 交流标识(免去该职务时是否交流任职的标识。1—是；0—否) XZ09   少
	 */
	public java.lang.String getA0284() {
		return getStr("A0284");
	}

	/**
	 * 职位类别？   少
	 */
	public void setA0277(java.lang.String A0277) {
		set("A0277", A0277);
	}
	
	/**
	 * 职位类别？   少
	 */
	public java.lang.String getA0277() {
		return getStr("A0277");
	}

	/**
	 * 免职类别 ZB16   少
	 */
	public void setA0271(java.lang.String A0271) {
		set("A0271", A0271);
	}
	
	/**
	 * 免职类别 ZB16   少
	 */
	public java.lang.String getA0271() {
		return getStr("A0271");
	}

	/**
	 * 破格晋升标识   少
	 */
	public void setA0259(java.lang.String A0259) {
		set("A0259", A0259);
	}
	
	/**
	 * 破格晋升标识   少
	 */
	public java.lang.String getA0259() {
		return getStr("A0259");
	}

	/**
	 * 任下一级岗位月数？   少
	 */
	public void setA0256C(java.lang.Integer A0256C) {
		set("A0256C", A0256C);
	}
	
	/**
	 * 任下一级岗位月数？   少
	 */
	public java.lang.Integer getA0256C() {
		return getInt("A0256C");
	}

	/**
	 * 任下一级岗位年数？   少
	 */
	public void setA0256B(java.lang.Integer A0256B) {
		set("A0256B", A0256B);
	}
	
	/**
	 * 任下一级岗位年数？   少
	 */
	public java.lang.Integer getA0256B() {
		return getInt("A0256B");
	}

	/**
	 * 任下一级岗位年限？   少
	 */
	public void setA0256A(java.lang.String A0256A) {
		set("A0256A", A0256A);
	}
	
	/**
	 * 任下一级岗位年限？   少
	 */
	public java.lang.String getA0256A() {
		return getStr("A0256A");
	}

	/**
	 * 任下一级岗位时间？   少
	 */
	public void setA0256(java.lang.String A0256) {
		set("A0256", A0256);
	}
	
	/**
	 * 任下一级岗位时间？   少
	 */
	public java.lang.String getA0256() {
		return getStr("A0256");
	}

	/**
	 * 任职变动类型 ZB13   少
	 */
	public void setA0251(java.lang.String A0251) {
		set("A0251", A0251);
	}
	
	/**
	 * 任职变动类型 ZB13   少
	 */
	public java.lang.String getA0251() {
		return getStr("A0251");
	}

	/**
	 * 分管（从事）工作( （采集）   少
	 */
	public void setA0229(java.lang.String A0229) {
		set("A0229", A0229);
	}
	
	/**
	 * 分管（从事）工作( （采集）   少
	 */
	public java.lang.String getA0229() {
		return getStr("A0229");
	}

	/**
	 * 岗位类别 ZB127 （采集）   少
	 */
	public void setA0222(java.lang.String A0222) {
		set("A0222", A0222);
	}
	
	/**
	 * 岗位类别 ZB127 （采集）   少
	 */
	public java.lang.String getA0222() {
		return getStr("A0222");
	}

	/**
	 * 职务工资待遇层次？   少
	 */
	public void setA0221W(java.lang.String A0221W) {
		set("A0221W", A0221W);
	}
	
	/**
	 * 职务工资待遇层次？   少
	 */
	public java.lang.String getA0221W() {
		return getStr("A0221W");
	}

	/**
	 * 职务层次 ZB09 （采集）   少
	 */
	public void setA0221(java.lang.String A0221) {
		set("A0221", A0221);
	}
	
	/**
	 * 职务层次 ZB09 （采集）   少
	 */
	public java.lang.String getA0221() {
		return getStr("A0221");
	}

	/**
	 * 职务工资待遇类型？   少
	 */
	public void setA0219W(java.lang.String A0219W) {
		set("A0219W", A0219W);
	}
	
	/**
	 * 职务工资待遇类型？   少
	 */
	public java.lang.String getA0219W() {
		return getStr("A0219W");
	}

	/**
	 * 职务名称？   少
	 */
	public void setA0216A(java.lang.String A0216A) {
		set("A0216A", A0216A);
	}
	
	/**
	 * 职务名称？   少
	 */
	public java.lang.String getA0216A() {
		return getStr("A0216A");
	}

	/**
	 * 职务？1   少
	 */
	public void setA0215B(java.lang.String A0215B) {
		set("A0215B", A0215B);
	}
	
	/**
	 * 职务？1   少
	 */
	public java.lang.String getA0215B() {
		return getStr("A0215B");
	}

	/**
	 * 任职机构性质类别？   少
	 */
	public void setA0209(java.lang.String A0209) {
		set("A0209", A0209);
	}
	
	/**
	 * 任职机构性质类别？   少
	 */
	public java.lang.String getA0209() {
		return getStr("A0209");
	}

	/**
	 * 任职机构级别？   少
	 */
	public void setA0207(java.lang.String A0207) {
		set("A0207", A0207);
	}
	
	/**
	 * 任职机构级别？   少
	 */
	public java.lang.String getA0207() {
		return getStr("A0207");
	}

	/**
	 * 任职法人单位编码？   少
	 */
	public void setA0204(java.lang.String A0204) {
		set("A0204", A0204);
	}
	
	/**
	 * 任职法人单位编码？   少
	 */
	public java.lang.String getA0204() {
		return getStr("A0204");
	}

	/**
	 * 任职机构简称   少
	 */
	public void setA0201C(java.lang.String A0201C) {
		set("A0201C", A0201C);
	}
	
	/**
	 * 任职机构简称   少
	 */
	public java.lang.String getA0201C() {
		return getStr("A0201C");
	}

	/**
	 * 任职机构(没用)？   少
	 */
	public void setA0201(java.lang.String A0201) {
		set("A0201", A0201);
	}
	
	/**
	 * 任职机构(没用)？   少
	 */
	public java.lang.String getA0201() {
		return getStr("A0201");
	}

	/**
	 * 主职务标识
	 */
	public void setA0279(java.lang.String A0279) {
		set("A0279", A0279);
	}
	
	/**
	 * 主职务标识
	 */
	public java.lang.String getA0279() {
		return getStr("A0279");
	}

	/**
	 * 备注
	 */
	public void setMark(java.lang.String mark) {
		set("mark", mark);
	}
	
	/**
	 * 备注
	 */
	public java.lang.String getMark() {
		return getStr("mark");
	}

	public void setA0243Str(java.lang.String A0243Str) {
		set("A0243Str", A0243Str);
	}
	
	public java.lang.String getA0243Str() {
		return getStr("A0243Str");
	}

	/**
	 * 备注时间
	 */
	public void setMarkTime(java.util.Date markTime) {
		set("markTime", markTime);
	}
	
	/**
	 * 备注时间
	 */
	public java.util.Date getMarkTime() {
		return get("markTime");
	}

	/**
	 * 任职说明(对应全息系统)
	 */
	public void setA02Z101(java.lang.String A02Z101) {
		set("A02Z101", A02Z101);
	}
	
	/**
	 * 任职说明(对应全息系统)
	 */
	public java.lang.String getA02Z101() {
		return getStr("A02Z101");
	}

	/**
	 * 流动方式(对应全息系统)
	 */
	public void setA02Z102(java.lang.String A02Z102) {
		set("A02Z102", A02Z102);
	}
	
	/**
	 * 流动方式(对应全息系统)
	 */
	public java.lang.String getA02Z102() {
		return getStr("A02Z102");
	}

	/**
	 * 任职岗位id(对应全息系统)
	 */
	public void setA02Z103(java.lang.String A02Z103) {
		set("A02Z103", A02Z103);
	}
	
	/**
	 * 任职岗位id(对应全息系统)
	 */
	public java.lang.String getA02Z103() {
		return getStr("A02Z103");
	}

}
