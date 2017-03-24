package com.ai.aiga.view.json;

public class TaskRunResultRequest {
	private Long taskId;
	private String taskTag;
	private String taskName;
	private String machineIp;
	private Byte taskResult;
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public String getTaskTag() {
		return taskTag;
	}
	public void setTaskTag(String taskTag) {
		this.taskTag = taskTag;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getMachineIp() {
		return machineIp;
	}
	public void setMachineIp(String machineIp) {
		this.machineIp = machineIp;
	}
	public Byte getTaskResult() {
		return taskResult;
	}
	public void setTaskResult(Byte taskResult) {
		this.taskResult = taskResult;
	}
	
	
}
