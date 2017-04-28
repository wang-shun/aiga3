package com.ai.aiga.view.json.auto;

public class AutoRunResultRequest {
	private Long taskId;
	private String autoName;
	private Long resultType;
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
	public Long getResultType() {
		return resultType;
	}
	public void setResultType(Long resultType) {
		this.resultType = resultType;
	}
	
}
