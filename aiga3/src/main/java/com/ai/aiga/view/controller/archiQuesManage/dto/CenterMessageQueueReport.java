package com.ai.aiga.view.controller.archiQuesManage.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class CenterMessageQueueReport extends PlatcormOperateBase implements Serializable {

    private String predayMqConsumeNum;
    private String changeNumChainRatio;
    private String messageConsumeSuccessRate;
    private String successRateChainRatio;
    private String messageCheckSameRate;
    
}
