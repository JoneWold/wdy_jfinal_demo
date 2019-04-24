package com.wdy.biz.progress.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.websocket.Session;

/**
 * @author wgch
 * @Description
 * @date 2019/4/23 19:07
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WebsocketDto {

    private String code; //客户端传入的唯一标识

    private int totalNum; //总数量

    private int handleNum = 0; //当前操作数量

    private Session session; //当前websocket的session

}
