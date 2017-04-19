package com.ai.aiga.view.json;


import java.util.Date;

public class NaOtherTaskRequest {
	private Long taskType;
	private Long onlinePlan;
	private Long taskId;
	private Long subTaskId;

	public Long getTaskType() {
		return taskType;
	}
	public void setTaskType(Long taskType) {
		this.taskType = taskType;
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
	public Long getSubTaskId() {
		return subTaskId;
	}
	public void setSubTaskId(Long subTaskId) {
		this.subTaskId = subTaskId;
	}
	public NaOtherTaskRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

}
