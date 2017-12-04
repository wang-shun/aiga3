package com.ai.aiga.view.controller.archiQuesManage.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class FlowDispatchReport implements Serializable {

    private String key1;
    private String addFlowConnectNum;
    private String totalFlowConnectNum;
    private String predayDispatchNum;
    private String dispatchChainRatio;
    private String dealAverageTime;
    private String dealTimeChainRatio;
    private String settMonth;
    
}
