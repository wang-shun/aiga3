package com.ai.aiga.view.json;

public class AutoRunResultRequest {
	private Long taskId;
	private String autoName;
	private Byte resultType;
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public String getAutoName() {
		return autoName;
	}
	public void setAutoName(String autoName) {
		this.autoName = autoName;
	}
	public Byte getResultType() {
		return resultType;
	}
	public void setResultType(Byte resultType) {
		this.resultType = resultType;
	}
	
}
