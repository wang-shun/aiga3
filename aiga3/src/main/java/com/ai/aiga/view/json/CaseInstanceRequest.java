package com.ai.aiga.view.json;



import org.hibernate.validator.constraints.NotBlank;


public class CaseInstanceRequest {
	
	private Long testId;
	
	@NotBlank(message = "用例名称不能为空!")
	private String testName;
	
	private String preResult;
	
	private String testDesc;
	
	private Long caseId;
	
	private String factors;

	public Long getTestId() {
		return testId;
	}

	public void setTestId(Long testId) {
		this.testId = testId;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public Long getCaseId() {
		return caseId;
	}

	public void setCaseId(Long caseId) {
		this.caseId = caseId;
	}

	public String getFactors() {
		return factors;
	}

	public void setFactors(String factors) {
		this.factors = factors;
	}

	public String getPreResult() {
		return preResult;
	}

	public void setPreResult(String preResult) {
		this.preResult = preResult;
	}

	public String getTestDesc() {
		return testDesc;
	}

	public void setTestDesc(String testDesc) {
		this.testDesc = testDesc;
	}
	

}
