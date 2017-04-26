package com.ai.aiga.service.auto.dto;

import lombok.Data;

@Data
public class CaseCollectList implements java.io.Serializable {
	private Long collectId;//用例集id
	private String collectName;//用例集名称
	private String operator;//创建人
	private String createDate;//创建时间
	private Long caseNum;//关联用例数量
	private String caseType;//用例集类型
	private String repairId;//维护人
	private String sysId;//关联系统
	
}
