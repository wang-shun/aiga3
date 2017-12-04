package com.ai.aiga.view.controller.archiQuesManage.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class TaskDispatchReport implements Serializable {

    private String key1;
    private String predayAddTaskNum;
    private String residentTaskNum;
    private String nonresidentTaskNum;
    private String batchTaskNum;
    private String predayTaskTriggerNum;
    private String changeChainRatio;
    private String settMonth;
    
}
