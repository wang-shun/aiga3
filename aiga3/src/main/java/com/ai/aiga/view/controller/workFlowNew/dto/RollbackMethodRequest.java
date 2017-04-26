package com.ai.aiga.view.controller.workFlowNew.dto;
/**
 * 变更评审-变更回退方案
 */
import lombok.Data;

@Data
public class RollbackMethodRequest {
	  private Long id;
	     private String isRollback;//是否具备回退
	     private String rollbackStep;//回退步骤
	     private String rollbackPerson;//回退人员
	     private String startRollbackTime;//回退开始时间
	     private String stopRollbackTime;//回退结束时间
	     private String rollbackScript;//回退脚本
	     private Long planId;
	     private String fileName;
	     private String ext1;
}
