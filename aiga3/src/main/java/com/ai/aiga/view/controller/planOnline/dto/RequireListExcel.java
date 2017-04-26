package com.ai.aiga.view.controller.planOnline.dto;

import java.util.Date;

import lombok.Data;

/**
 * @ClassName: RequireListExcel
 * @author: liujinfang
 * @date: 2017年4月25日 下午4:30:05
 * @Description:
 * 
 */
@Data
public class RequireListExcel  implements java.io.Serializable{
	 private String testMan;
     private String testManager;
     private String requireCode;
     private String requireName;
     private String bigSystem;
     private String system;
     private String devMan;
     private String devManager;
     private String requireMan;
     private String remarks;
     private Long introducedState;
     private Byte testResult;
     private Long testProgress;
     private Long testCondition;
     private Long defectNumber;
     private Date requireDate;
     private String requireSource;
     private Long requireLevel;
     private Long requirePriority;
     private Byte groupCheck;
     private Long requireClassify;
     private Long requireType;
     private Long requireOnlineType;
     private Date onlineDate;
     private String onlineReason;
     private Date firstTime;
     private Date lastTime;
     private Date upDate;
     private Long devTaskId;
     private String devTaskName;
     private Long taskState;
     private Long isImportantRequire;
     private String testGroup;
     private String requireApplicant;
     private Long workloadEvaluation;
     private String situationAnalysis;
     private Long isCycleDemand;
     private Long isSystemAdjust;
     private Long groupDemand;
     private Long isDatebaseScript;
     private Long reviewState;

}

