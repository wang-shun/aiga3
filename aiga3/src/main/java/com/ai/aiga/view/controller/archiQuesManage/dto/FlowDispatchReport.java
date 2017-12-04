package com.ai.aiga.view.controller.archiQuesManage.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class FlowDispatchReport extends PlatcormOperateBase implements Serializable {

    private String addFlowConnectNum;
    private String totalFlowConnectNum;
    private String predayDispatchNum;
    private String dispatchChainRatio;
    private String dealAverageTime;
    private String dealTimeChainRatio;
    
}
