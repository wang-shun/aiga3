package com.ai.aiga.view.json;

public class TaskRunResultRequest {
	private String taskTag;
	private String taskName;
	private String machineIp;
	private Byte resultType;
	private Byte runType;
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
	public Byte getResultType() {
		return resultType;
	}
	public void setResultType(Byte resultType) {
		this.resultType = resultType;
	}
	public Byte getRunType() {
		return runType;
	}
	public void setRunType(Byte runType) {
		this.runType = runType;
	}
	
}
