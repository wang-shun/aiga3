package com.ai.aiga.view.controller.archiQuesManage.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class CenterCsfSrvReport extends PlatcormOperateBase implements Serializable {

    private String dayCsfSrvNum;
    private String totalCsfNum;
    private String activeCsfNum;
    private String centerCsfNum;
    private String csfSrvChainRatio;
    private String predayCsfSuccessRate;
    private String csfSuccessRateChainRatio;
    
}
