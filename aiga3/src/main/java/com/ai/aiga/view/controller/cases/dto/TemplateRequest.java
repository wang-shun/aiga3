package com.ai.aiga.view.controller.cases.dto;

import java.util.List;


import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;


public class TemplateRequest {
	
	private Long caseId;
	
	@NotBlank(message = "用例模板名称不能为空!")
	private String caseName;
	
	@Range(min=1, max=3, message = "模板用例类型不符合要求!")
	private Long caseType;
	
	private String testType;
	private String preCond;
	private String operateDesc;
	private Short important;
	private Long sysId;
	private Long subSysId;
	private Long funId;
	private Long scId;
	private Long busiId;
	
	private String factors;
	
	private Long interfaceType;
	private String address;
	private Long messageType;
	private String validParam;
	private String messageName;
	private Long messageId;

	public Long getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(Long interfaceType) {
		this.interfaceType = interfaceType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getMessageType() {
		return messageType;
	}

	public void setMessageType(Long messageType) {
		this.messageType = messageType;
	}

	public String getValidParam() {
		return validParam;
	}

	public void setValidParam(String validParam) {
		this.validParam = validParam;
	}

	public String getMessageName() {
		return messageName;
	}

	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public Long getCaseId() {
		return caseId;
	}

	public void setCaseId(Long caseId) {
		this.caseId = caseId;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public Long getCaseType() {
		return caseType;
	}

	public void setCaseType(Long caseType) {
		this.caseType = caseType;
	}

	public String getTestType() {
		return testType;
	}

	public void setTestType(String testType) {
		this.testType = testType;
	}

	public String getPreCond() {
		return preCond;
	}

	public void setPreCond(String preCond) {
		this.preCond = preCond;
	}

	public String getOperateDesc() {
		return operateDesc;
	}

	public void setOperateDesc(String operateDesc) {
		this.operateDesc = operateDesc;
	}

	public Short getImportant() {
		return important;
	}

	public void setImportant(Short important) {
		this.important = important;
	}

	public Long getSysId() {
		return sysId;
	}

	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}



	public Long getSubSysId() {
		return subSysId;
	}

	public void setSubSysId(Long subSysId) {
		this.subSysId = subSysId;
	}

	public Long getFunId() {
		return funId;
	}

	public void setFunId(Long funId) {
		this.funId = funId;
	}

	public Long getScId() {
		return scId;
	}

	public void setScId(Long scId) {
		this.scId = scId;
	}

	public Long getBusiId() {
		return busiId;
	}

	public void setBusiId(Long busiId) {
		this.busiId = busiId;
	}

	public String getFactors() {
		return factors;
	}

	public void setFactors(String factors) {
		this.factors = factors;
	}

}
