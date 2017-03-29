package com.ai.aiga.view.json;

public class NaOnlineTaskDistributeResponse {
	
	private Long taskId;
	private String taskName;
	private Long taskType;
	private Long dealState;
	private String dealrName;
	private Long dealOpId;
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Long getTaskType() {
		return taskType;
	}
	public void setTaskType(Long taskType) {
		this.taskType = taskType;
	}
	public Long getDealState() {
		return dealState;
	}
	public void setDealState(Long dealState) {
		this.dealState = dealState;
	}
	public String getDealrName() {
		return dealrName;
	}
	public void setDealrName(String dealrName) {
		this.dealrName = dealrName;
	}
	public Long getDealOpId() {
		return dealOpId;
	}
	public void setDealOpId(Long dealOpId) {
		this.dealOpId = dealOpId;
	}
	
	
}
