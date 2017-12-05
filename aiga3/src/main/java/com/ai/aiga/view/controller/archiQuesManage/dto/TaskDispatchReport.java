package com.ai.aiga.view.controller.archiQuesManage.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class TaskDispatchReport extends PlatcormOperateBase implements Serializable {

    private String predayAddTaskNum;
    private String residentTaskNum;
    private String nonresidentTaskNum;
    private String batchTaskNum;
    private String predayTaskTriggerNum;
    private String changeChainRatio;
    
}
