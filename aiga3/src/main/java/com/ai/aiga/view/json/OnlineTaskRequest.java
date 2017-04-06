package com.ai.aiga.view.json;

/**
 * @ClassName: OnlineTaskRequest
 * @author: dongch
 * @date: 2017年4月6日 上午11:41:40
 * @Description:
 * 
 */
public class OnlineTaskRequest {
	private Long taskId;
	private String taskName;
	private Long collectId;
	private Long dealOpId;
	private Long parentTaskId;
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
	public Long getCollectId() {
		return collectId;
	}
	public void setCollectId(Long collectId) {
		this.collectId = collectId;
	}
	public Long getDealOpId() {
		return dealOpId;
	}
	public void setDealOpId(Long dealOpId) {
		this.dealOpId = dealOpId;
	}
	public Long getParentTaskId() {
		return parentTaskId;
	}
	public void setParentTaskId(Long parentTaskId) {
		this.parentTaskId = parentTaskId;
	}
	
}

