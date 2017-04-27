package com.ai.aiga.view.controller.workFlowNew.dto;
/**
 * 变更评审-变更运维管理
 * @author liuxx
 * @date 2017年4月25日21:28:38
 */
import lombok.Data;

@Data
public class ChangeOperationManagerRequest {
    private Long id;
    private String isAddMonitor; //添加监控部署
    private String runPerson;//实施人员
    private String startRunTime;//实施开始时间
    private String stopRunTime;//实施结束时间
    private String operationScript;//操作脚本
    private Long planId;
    private String fileName;
    private String ext1;
}
