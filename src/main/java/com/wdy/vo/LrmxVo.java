package com.wdy.vo;

import lombok.Data;

import java.util.List;

/**
 * @author wgch
 * @Description lrmx vo
 * @date 2019/12/10
 */
@Data
public class LrmxVo {
    private String XingMing;
    private String XingBie;
    private String ChuShengNianYue;
    private String MinZu;
    private String JiGuan;
    private String ChuShengDi;
    private String RuDangShiJian;
    private String CanJiaGongZuoShiJian;
    private String JianKangZhuangKuang;
    private String ZhuanYeJiShuZhiWu;
    private String ShuXiZhuanYeYouHeZhuanChang;
    private String QuanRiZhiJiaoYu_XueLi;
    private String QuanRiZhiJiaoYu_XueWei;
    private String QuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi;
    private String QuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi;
    private String ZaiZhiJiaoYu_XueLi;
    private String ZaiZhiJiaoYu_XueWei;
    private String ZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi;
    private String ZaiZhiJiaoYu_XueWei_BiYeYuanXiaoXi;
    private String XianRenZhiWu;
    private String NiRenZhiWu;
    private String NiMianZhiWu;
    private String JianLi;
    private String JiangChengQingKuang;
    private String NianDuKaoHeJieGuo;
    private String RenMianLiYou;
    private List<LrmxSubVo> JiaTingChengYuan;
    private String ChengBaoDanWei;
    private String JiSuanNianLingShiJian;
    private String TianBiaoShiJian;
    private String TianBiaoRen;
    private String ShenFenZheng;
    private String ZhaoPian;
    private String Version;

}
