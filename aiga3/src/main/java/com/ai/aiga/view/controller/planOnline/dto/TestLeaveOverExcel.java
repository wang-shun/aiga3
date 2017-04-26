package com.ai.aiga.view.controller.planOnline.dto;

import lombok.Data;

/**
 * @ClassName: TestLeaveOverExcel
 * @author: liujinfang
 * @date: 2017年4月25日 下午4:07:38
 * @Description:
 * 
 */
@Data
public class TestLeaveOverExcel implements java.io.Serializable{
	 private Long defectId;
     private String problemDescription;
     private String testStage;
     private Long defectType;
     private String influence;
     private String reviewResult;
     private String devMan;
     private String testMan;
     private String requireManager;
     private Long requireId;
     private Long devTaskId;
     private String requireName;
     
     
     
}

