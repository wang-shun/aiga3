package com.ai.aiga.view.controller.archiQuesManage.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class CenterMessageQueueReport implements Serializable {

    private String key1;
    private String predayMqConsumeNum;
    private String changeNumChainRatio;
    private String messageConsumeSuccessRate;
    private String successRateChainRatio;
    private String messageCheckSameRate;
    private String settMonth;
    
}
