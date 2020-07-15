package com.wdy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author wgch
 * @date 2020/7/15
 */
@Data
@AllArgsConstructor
public class LOL {

    /**
     * 赛区
     */
    private String area;
    /**
     * 战队名称
     */
    private String teamName;
    /**
     * 战队选手
     */
    List<String> player;

}
