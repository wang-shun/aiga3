package com.ai.aiga.view.json;

import java.util.Date;
import java.util.List;

import com.ai.aiga.domain.NaCaseFactor;
import com.ai.aiga.domain.NaTestCaseParam;

public class CaseTestResponse {
	
	private long testId;
	private String testName;
	private long caseId;
	private String testType;
	private byte caseType;
	private String testDesc;
	private String preResult;
	private Short important;
	private Long sysId;
	private Long sysSubId;
	private Long funId;
	private Long scId;
	private Long busiId;
	private Long creatorId;
	private Date createTime;
	private Long updateId;
	private Date updateTime;
	
	List<NaTestCaseParam> factors;

	public long getTestId() {
		return testId;
	}

	public void setTestId(long testId) {
		this.testId = testId;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public long getCaseId() {
		return caseId;
	}

	public void setCaseId(long caseId) {
		this.caseId = caseId;
	}

	public String getTestType() {
		return testType;
	}

	public void setTestType(String testType) {
		this.testType = testType;
	}

	public byte getCaseType() {
		return caseType;
	}

	public void setCaseType(byte caseType) {
		this.caseType = caseType;
	}

	public String getTestDesc() {
		return testDesc;
	}

	public void setTestDesc(String testDesc) {
		this.testDesc = testDesc;
	}

	public String getPreResult() {
		return preResult;
	}

	public void setPreResult(String preResult) {
		this.preResult = preResult;
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

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateId() {
		return updateId;
	}

	public void setUpdateId(Long updateId) {
		this.updateId = updateId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public List<NaTestCaseParam> getFactors() {
		return factors;
	}

	public void setFactors(List<NaTestCaseParam> factors) {
		this.factors = factors;
	}

	@Override
	public String toString() {
		return "CaseTestResponse [testId=" + testId + ", testName=" + testName + ", caseId=" + caseId + ", testType="
				+ testType + ", caseType=" + caseType + ", testDesc=" + testDesc + ", preResult=" + preResult
				+ ", important=" + important + ", sysId=" + sysId + ", sysSubId=" + sysSubId + ", funId=" + funId
				+ ", scId=" + scId + ", busiId=" + busiId + ", creatorId=" + creatorId + ", createTime=" + createTime
				+ ", updateId=" + updateId + ", updateTime=" + updateTime + ", factors=" + factors + "]";
	}

}
