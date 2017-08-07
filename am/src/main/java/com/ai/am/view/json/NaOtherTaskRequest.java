package com.ai.am.view.json;


import java.util.Date;

public class NaOtherTaskRequest {
	private Long dealState;
	private Long onlinePlan;
	private Long taskId;
	private String subTaskName;
	private Long taskType;

	
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
	public Long getOnlinePlan() {
		return onlinePlan;
	}
	public void setOnlinePlan(Long onlinePlan) {
		this.onlinePlan = onlinePlan;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public String getSubTaskName() {
		return subTaskName;
	}
	public void setSubTaskName(String subTaskName) {
		this.subTaskName = subTaskName;
	}
	public NaOtherTaskRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

}
