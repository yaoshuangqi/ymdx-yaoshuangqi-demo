package com.xxcx.pay.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yaoShuangQi
 * @description 告警消息dto
 * @date 2021/7/5 14:00
 */
@Data
public class AlarmMessageDto implements Serializable {

    private int scopeId;
    private String name;
    private int id0;
    private int id1;
    private String alarmMessage;
    private long startTime;
}
