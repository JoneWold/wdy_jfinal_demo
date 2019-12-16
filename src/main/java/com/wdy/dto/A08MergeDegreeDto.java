package com.wdy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author wgch
 * @Description 拼接学历学位
 * @date 2019/5/23 9:58
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class A08MergeDegreeDto {
    /**
     * 全日制综述
     */
    private String QRZZS;
    /**
     * 在职综述
     */
    private String ZZZS;
    /**
     * 全日制学历
     */
    private String QRZXL;
    /**
     * 全日制学历信息
     */
    private String QRZXLXX;
    /**
     * 全日制学位
     */
    private String QRZXW;
    /**
     * 全日制学位信息
     */
    private String QRZXWXX;
    /**
     * 在职学历
     */
    private String ZZXL;
    /**
     * 在职学历信息
     */
    private String ZZXLXX;
    /**
     * 在职学位
     */
    private String ZZXW;
    /**
     * 在职学位信息
     */
    private String ZZXWXX;

}
