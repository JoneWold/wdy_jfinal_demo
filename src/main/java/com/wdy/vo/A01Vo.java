package com.wdy.vo;

import com.wdy.generator.postgreSQL.model.A02;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author TMW
 * @version 1.0
 * @Description: 任免表信息
 * @date 2019/3/29 15:49
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class A01Vo {
    /**
     * 人员统一标识符
     */
    private String A0000;
    /**
     * 姓名   等
     */
    private String A0101;
    /**
     * 姓名拼音缩写   等
     */
    private String A0102;
    /**
     * 性别   等
     */
    private String A0104;
    /**
     * 出生日期   等
     */
    private String A0107;
    /**
     * 籍贯   等
     */
    private String A0111A;
    /**
     * 出生地   等
     */
    private String A0114A;
    /**
     * 成长地    多
     */
    private String A0115A;
    /**
     * 民族   等
     */
    private String A0117;
    /**
     * 健康状况   等
     */
    private String A0128;
    /**
     * 参加工作时间   等
     */
    private String A0134;
    /**
     * 入党时间文字   等
     */
    private String A0140;
    /**
     * 政治面貌   等
     */
    private String A0141;
    /**
     * 入党时间   等
     */
    private Date A0144;
    /**
     * 第二党派   等
     */
    private String A3921;
    /**
     * 第三党派   等
     */
    private String A3927;
    /**
     * 人员类别   等
     */
    private String A0160;
    /**
     * 人员管理状态   等
     */
    private String A0163;
    /**
     * 管理类别   等
     */
    private String A0165;
    /**
     * 公民身份号码   等
     */
    private String A0184;
    /**
     * 专长   等
     */
    private String A0187A;
    /**
     * 现工作单位及职务简称   等
     */
    private String A0192;
    /**
     * 现工作单位及职务全称   等
     */
    private String A0192A;
    /**
     * 职务层次    多
     */
    private String A0221;
    /**
     * 职务层次中文
     */
    private String A0221Name;
    /**
     * 任现职务层次时间   多
     */
    private Date A0288;
    /**
     * 职级   等
     */
    private String A0192D;
    /**
     * 职级中文
     */
    private String A0192DName;
    /**
     * 任该职级时间   多
     */
    private Date A0192C;
    /**
     * 专业技术职务   等
     */
    private String A0196;
    /**
     * 是否具有两年以上基层工作经历   多 1 是 0 否
     */
    private String A0197;
    /**
     * 统计关系所在单位   等
     */
    private String A0195;
    /**
     * 简历   等
     */
    private String A1701;
    private String A14Z101;
    private String A15Z101;
    /**
     * 级别   等
     */
    private String A0120;
    /**
     * 编制类型   等
     */
    private String A0121;
    /**
     * 公务员登记时间   多
     */
    private String A2949;
    /**
     * 专业技术类公务员任职资格   等
     */
    private String A0122;
    /**
     * 性别(汉字)1
     */
    private String A0104A;
    /**
     * 籍贯
     */
    private String A0111;
    /**
     * 出生地
     */
    private String A0114;
    /**
     * 民族(汉字)1
     */
    private String A0117A;
    /**
     * 健康状况1
     */
    private String A0128B;
    /**
     * 记录本人三个政治面貌1
     */
    private String A0141D;
    /**
     * 参加组织时间二1
     */
    private String A0144B;
    /**
     * 参加组织时间三1
     */
    private String A0144C;
    /**
     * 最高职务层次
     */
    private String A0148;
    /**
     * 职务层次批准时间1
     */
    private String A0148C;
    /**
     * 职务层次
     */
    private String A0149;
    /**
     * 该人参加公务员登记的标识。1—登记；2—暂缓登记；3—不予登记。
     */
    private String A0151;
    /**
     * 公务员登记类别1—公务员；02—参照管理人员；3—其他
     */
    private String A0153;
    /**
     * 人事关系所在单位名称1
     */
    private String A0157;
    /**
     * 公务员级别1
     */
    private String A0158;
    /**
     * 公务员登记后所定职务
     */
    private String A0159;
    /**
     * 职务拼接方式1
     */
    private String A015A;
    private String A0161;
    /**
     * 公务员级别批准日期1
     */
    private String A0162;
    private String A0180;
    /**
     * 与列表关联
     */
    private String A0191;
    private String A0192B;
    /**
     * 职务层次1
     */
    private String A0193;
    /**
     * 该人在基层工作的时间是否2年以上-报送标准A0197
     */
    private String A0194U;
    /**
     * 照片路径1
     */
    private String A0198;
    /**
     * 用于区分是否是正在增加的人员，1为任免表导入人员；0为正常人员1
     */
    private String A0199;
    /**
     * 最高职务层次类别1
     */
    private String A01K01;
    /**
     * 该人是否有基层工作经验的标志。
     */
    private String A01K02;
    /**
     * 呈报单审批结果
     */
    private String CBDRESULT;
    /**
     * 呈报单位1
     */
    private String CBDW;
    /**
     * 多次保存结果1
     */
    private String ISVALID;
    /**
     * 年龄1
     */
    private String NL;
    /**
     * 拟免职务1
     */
    private String NMZW;
    /**
     * 拟任职务1
     */
    private String NRZW;
    /**
     * 历史离退人员所在的机构id
     */
    private String ORGID;
    /**
     * 最高全日制学历
     */
    private String QRZXL;
    /**
     * 院校系专业（最高全日制学历）
     */
    private String QRZXLXX;
    /**
     * 最高全日制学位
     */
    private String QRZXW;
    /**
     * 院校系专业（最高全日制学位）
     */
    private String QRZXWXX;
    /**
     * 任免理由1
     */
    private String RMLY;
    /**
     * 状态 0完全删除 1正常 2历史库3离退人员4临时数据
     */
    private String STATUS;
    /**
     * 填报人id
     */
    private String TBR;
    /**
     * 填报人所在机构id  1-删
     */
    private String TBRJG;
    /**
     * 结果集的用户名id号1
     */
    private String USERLOG;
    /**
     * 修改人1
     */
    private String XGR;
    /**
     * 最高在职学历
     */
    private String ZZXL;
    /**
     * 院校系专业（最高在职学历）
     */
    private String ZZXLXX;
    /**
     * 最高在职学位
     */
    private String ZZXW;
    /**
     * 院校系专业（最高在职学位）
     */
    private String ZZXWXX;
    /**
     * 公务员登记时间
     */
    private Date A0155;
    /**
     * 年龄1
     */
    private Integer AGE;
    /**
     * 计算年龄时间1
     */
    private Date JSNLSJ;
    /**
     * 排序号1
     */
    private Integer RESULTSORTID;
    /**
     * 填表时间
     */
    private Date TBSJ;
    /**
     * 修改时间1
     */
    private Date XGSJ;
    /**
     * 排序字段1
     */
    private Integer SORTID;
    /**
     * 该人在基层工作的年月   将年月计算成月份存储
     */
    private Integer A0194;
    /**
     * 职级（职级并行）
     */
    private String A0192E;
    /**
     * 工作单位及职务全称对应的，任职时间
     */
    private String A0192F;
    /**
     * 最高机构
     */
    private String TORGID;
    /**
     * 最高机构所在机体内排序
     */
    private String TORDER;
    private String ZGXL;
    private String ZGXLXX;
    private String ZGXW;
    private String ZGXWXX;
    private String TCSJSHOW;
    /**
     * 退出方式
     */
    private String TCFSSHOW;
    /**
     * 全日制综述
     */
    private String QRZZS;
    /**
     * 在职综述
     */
    private String ZZZS;
    /**
     * 公务员登记号
     */
    private String A0155A;
    /**
     * 家庭成员集合
     */
    private List<A36Vo> a36VoList;
    /**
     * 岗位集合(定义)
     */
    private List<A02> a02VoList;
    /**
     * 是否已经套转
     */
    private Boolean isTurn;
    /**
     * 是否可以撤销
     */
    private Boolean isRevokeTurn;
    /**
     * A0200唯一标示
     */
    private String A0200;
    /**
     * A0500唯一标示
     */
    private String A0500;
    /**
     * 熟悉领域
     */
    private String familiar;
    /**
     * 历任经历
     */
    private String liren;
    /**
     * 优秀年轻干部 id
     */
    private String youngId;
    /**
     * 进入优秀年轻干部时间 (优秀年轻干部)
     */
    private String joinDate;
    /**
     * 职称代码 (职称代码)
     */
    private String jobCall;
    /**
     * 职称中文
     */
    private String jobCallName;
    /**
     * 优秀年轻干部 熟悉领域
     */
    private String familiarRelam;
    /**
     * 区县、乡镇街道正职经历
     */
    private Integer isCountyVillagesExperience;
    /**
     * 乡镇街道及村社区工作经历
     */
    private Integer isVillagesCommunityExperience;
    /**
     * 有团工作经历
     */
    private Integer isTeamExperience;
    /**
     * 有秘书工作经历
     */
    private Integer isSecretaryExperience;
    /**
     * 有企业、高校、科研院所正职领导任职经历
     */
    private Integer isCompanyExperience;
    /**
     * 是否四必核查 1 是 0否
     */
    private Integer isFourMustCheck;
    /**
     * 是否核查干部人事档案 1是 0否
     */
    private Integer isCadreCheck;
    /**
     * 干部人事档案核查结果代码
     */
    private String cadreCheckResult;
    /**
     * 干部人事档案核查结果代码名称
     */
    private String cadreCheckResultName;
    /**
     * 干部人事档案核查附加文件
     */
    private String cadreCheckFileUrl;
    /**
     * 是否个人有关事项 1 是 0否
     */
    private Integer isPersonalCheck;
    /**
     * 个人有关事项报告结果代码
     */
    private String personalCheckResult;
    /**
     * 个人有关事项报告结果代码
     */
    private String personalCheckResultName;
    /**
     * 个人有关事项报告附加文件
     */
    private String personalCheckFileUrl;
    /**
     * 是否核查纪检监察机关意见 1是 0否
     */
    private Integer isSuperviseCheck;
    /**
     * 纪检监察机关意见核查结果代码
     */
    private String superviseCheckResult;
    /**
     * 纪检监察机关意见核查结果代码
     */
    private String superviseCheckResultName;
    /**
     * 纪检监察机关意见核查结果附加文件
     */
    private String superviseCheckFileUrl;
    /**
     * 是否有可查性的信访举报核查 1是 0否
     */
    private Integer isPetitionCheck;
    /**
     * 是否有可查性的信访举报核查结果意见代码
     */
    private String petitionCheckResult;
    /**
     * 是否有可查性的信访举报核查结果意见代码
     */
    private String petitionCheckResultName;
    /**
     * 是否有可查性的信访举报核查结果附加文件
     */
    private String petitionCheckFileUrl;
    /**
     * 未检查原因
     */
    private String noCheckReason;
    /**
     * 房产几套
     */
    private Integer house;
    /**
     * 股票基金等理财产品代码
     */
    private String shares;
    /**
     * 成为选调生的时间
     */
    private String selectDate;
    /**
     * 选调时的单位
     */
    private String selectUnit;
    /**
     * 选调时的职务
     */
    private String selectJob;
    /**
     * 变动时间
     */
    private Date changeTime;
    /**
     * 变动原因代码
     */
    private String changeReason;
    /**
     * 变动原因中文
     */
    private String changeReasonName;
    /**
     * 变动备注
     */
    private String changeRemark;
    /**
     * 定向优选和集中普选
     */
    private Integer dxyxOrJzpx;
    /**
     * 是否退出
     */
    private Integer isExit;
    /**
     * 职位类别
     */
    private String A0123;
    /**
     * 选调时的毕业院校
     */
    private String selectSchool;
    /**
     * 行测
     */
    private BigDecimal lineTest;
    /**
     * 申论
     */
    private BigDecimal expounding;
    /**
     * 面试成绩
     */
    private BigDecimal interviewScore;
    /**
     * 选调生的联系电话
     */
    private String phoneNumber;
    /**
     * 任相当职务职级时间
     */
    private Date A0192X;

    // TODO: 2020/1/11 Watermelon (增加任现职级批准时间)
    private Date A0504;
    /**
     * 培训方向
     */
    private String trainingDirection;
    /**
     * 培训期限
     */
    private String trainingDone;
    /**
     * 联系电话
     */
    private String A99Z195;
    /**
     * 任相当职务层次
     */
    private String A0221Z100;
    /**
     * 任相当职务层次时间
     */
    private String A0288Z100;

    /**
     * 审批机关意见
     */
    private String approvalIdea;
    /**
     * 行政机关任免意见
     */
    private String executiveIdea;
    /**
     * 考察材料名称
     */
    private String inspectFileName;
    /**
     * 考察材料URL
     */
    private String inspectFileUrl;
    /**
     * 考察材料PDF
     */
    private String inspectPdf;

    /**
     *  排序类型  1 id排序 2 sort排序
     */
    private Integer type;


}
