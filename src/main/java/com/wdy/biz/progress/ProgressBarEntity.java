package com.wdy.biz.progress;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @author wgch
 * @Description 进度条实体
 * @date 2019/4/24 18:11
 */
@Data
@ToString
@AllArgsConstructor
public class ProgressBarEntity {
    private long totalSize;
    private int uploadedSize;
    private String progress;

    public ProgressBarEntity(long totalSize, int uploadedSize) {
        this.totalSize = totalSize;
        this.uploadedSize = uploadedSize;
    }
}
