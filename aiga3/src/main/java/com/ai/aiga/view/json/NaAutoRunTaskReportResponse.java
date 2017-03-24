package com.ai.aiga.view.json;

import java.util.Date;

public class NaAutoRunTaskReportResponse {
	private Long taskId;
	private Long totalCase;
	private Long hasRunCase;
	private Long noneRunCase;
	private Long successCase;
	private Long failCase;
	private Long creatorId;
	private String successRate;
	private Date beginRunTime;
	private Date endRunTime;
	private Long spendTime;
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public Long getTotalCase() {
		return totalCase;
	}
	public void setTotalCase(Long totalCase) {
		this.totalCase = totalCase;
	}
	public Long getHasRunCase() {
		return hasRunCase;
	}
	public void setHasRunCase(Long hasRunCase) {
		this.hasRunCase = hasRunCase;
	}
	public Long getNoneRunCase() {
		return noneRunCase;
	}
	public void setNoneRunCase(Long noneRunCase) {
		this.noneRunCase = noneRunCase;
	}
	public Long getSuccessCase() {
		return successCase;
	}
	public void setSuccessCase(Long successCase) {
		this.successCase = successCase;
	}
	public Long getFailCase() {
		return failCase;
	}
	public void setFailCase(Long failCase) {
		this.failCase = failCase;
	}
	public Long getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}
	public String getSuccessRate() {
		return successRate;
	}
	public void setSuccessRate(String successRate) {
		this.successRate = successRate;
	}
	public Date getBeginRunTime() {
		return beginRunTime;
	}
	public void setBeginRunTime(Date beginRunTime) {
		this.beginRunTime = beginRunTime;
	}
	public Date getEndRunTime() {
		return endRunTime;
	}
	public void setEndRunTime(Date endRunTime) {
		this.endRunTime = endRunTime;
	}
	public Long getSpendTime() {
		return spendTime;
	}
	public void setSpendTime(Long spendTime) {
		this.spendTime = spendTime;
	}
	
}
