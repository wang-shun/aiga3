package com.ai.aiga.view.json;

import java.util.List;


import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;


public class TemplateRequest {
	
	private Long caseId;
	
	@NotBlank(message = "用例模板名称不能为空!")
	private String caseName;
	
	@Range(min=1, max=3, message = "模板用例类型不符合要求!")
	private byte caseType;
	
	private String testType;
	private String preCond;
	private String operateDesc;
	private Short important;
	private Long sysId;
	private Long sysSubId;
	private Long funId;
	private Long scId;
	private Long busiId;
	
	private String factors;

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

	public byte getCaseType() {
		return caseType;
	}

	public void setCaseType(byte caseType) {
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

	public Long getSysSubId() {
		return sysSubId;
	}

	public void setSysSubId(Long sysSubId) {
		this.sysSubId = sysSubId;
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
