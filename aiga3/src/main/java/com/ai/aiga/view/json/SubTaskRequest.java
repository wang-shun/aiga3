package com.ai.aiga.view.json;

/**
 * @ClassName: SubTaskRequest
 * @author: dongch
 * @date: 2017年4月6日 下午7:03:41
 * @Description:
 * 
 */
public class SubTaskRequest {
	private Long onlinePlan;
	private String taskName;
	private String subTaskName;
	private Long taskType;
	private Long dealState;
	public Long getOnlinePlan() {
		return onlinePlan;
	}
	public void setOnlinePlan(Long onlinePlan) {
		this.onlinePlan = onlinePlan;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getSubTaskName() {
		return subTaskName;
	}
	public void setSubTaskName(String subTaskName) {
		this.subTaskName = subTaskName;
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
	
}

