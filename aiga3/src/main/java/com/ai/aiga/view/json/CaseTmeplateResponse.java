package com.ai.aiga.view.json;

import java.util.Date;
import java.util.List;

import com.ai.aiga.domain.NaCaseFactor;

public class CaseTmeplateResponse {
	
	private long caseId;
	private String caseName;
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
	private Long creatorId;
	private Date createTime;
	private Long updateId;
	private Date updateTime;
	
	List<NaCaseFactor> factors;
	
	
	public long getCaseId() {
		return caseId;
	}
	public void setCaseId(long caseId) {
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
	public List<NaCaseFactor> getFactors() {
		return factors;
	}
	public void setFactors(List<NaCaseFactor> factors) {
		this.factors = factors;
	}

}
