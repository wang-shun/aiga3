package com.ai.aiga.view.controller.workFlowNew.dto;

import lombok.Data;

/**
 * 变更评审-变更准备工作
 * @author liuxx
 *@date 2017年4月25日21:23:09
 */
@Data
public class ChangePrepareWorkRequest {
    private Long id;
    private String isReady; //准备是否就行
    private String runStep;//实施步骤
    private String runPerson;//实施人员
    private String startRunTime;//实施开始时间
    private String stopRunTime;//实施结束时间
    private Long planId;
    private String fileName;
    private String ext1;
}
