package com.wdy.dto;

import lombok.Data;

/**
 * @author wgch
 * @Description 任免表原数据人员信息
 * @date 2019/5/12 13:32
 */
@Data
public class RmbOldMemInfoDto {
    /**
     * A0000
     */
    public String A0000;
    /**
     * 身份证
     */
    public String A0184;
    /**
     * 现任职务
     */
    public String A0192;
    /**
     * 出生日期
     */
    public String A0107;
    /**
     * 姓名
     */
    public String A0101;
    /**
     * 是否在任
     */
    public String A0255;
}
