package com.ai.aiga.service.workFlowNew.dto;

import java.io.Serializable;

import lombok.Data;

/** * @author  lh 
    * @date 创建时间：2017年4月26日 上午11:22:17
    */
@Data
public class NaServiceChangeOnlineListExcel implements Serializable {
	 private String changeType;
     private String serviceName;
     private String seriviceId;
     private String requireId;
     private String taskId;
     private String sysName;
     private String sysSubName;
     private String servicePrivideSystem;
     private String testResult;
     private String devMan;
     private String requireMan;
     private String remark;
     private Long planId;
     
     
	public NaServiceChangeOnlineListExcel() {
	}
	
     
}
