package com.wdy.biz.excel.ext;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 综合统计参数
 *
 * @author LHR
 * @date 2019/04/15 09:23
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SummaryDto {
    /**
     * 层级码
     */
    private String orgCode;
    /**
     * 职务层次
     */
    private String A0221;
    /**
     * 人员类别
     */
    private String A0165;
}
