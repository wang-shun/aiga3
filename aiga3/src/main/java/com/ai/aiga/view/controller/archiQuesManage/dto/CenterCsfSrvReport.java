package com.ai.aiga.view.controller.archiQuesManage.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class CenterCsfSrvReport implements Serializable {

    private String key1;
    private String dayCsfSrvNum;
    private String totalCsfNum;
    private String activeCsfNum;
    private String centerCsfNum;
    private String csfSrvChainRatio;
    private String predayCsfSuccessRate;
    private String csfSuccessRateChainRatio;
    private String settMonth;
    
}
