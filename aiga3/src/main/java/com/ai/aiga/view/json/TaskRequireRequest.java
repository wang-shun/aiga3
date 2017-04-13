package com.ai.aiga.view.json;

import java.util.List;

import com.ai.aiga.domain.NaInterfaceList;
import com.ai.aiga.domain.NaOnlineTaskDistribute;

/**
 * @ClassName: TaskRequireRequest
 * @author: dongch
 * @date: 2017年4月12日 下午6:47:54
 * @Description:
 * 
 */
public class TaskRequireRequest {
	private Long taskId;
	private Long taskType;
	private List<NaInterfaceList> list;
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public Long getTaskType() {
		return taskType;
	}
	public void setTaskType(Long taskType) {
		this.taskType = taskType;
	}
	public List<NaInterfaceList> getList() {
		return list;
	}
	public void setList(List<NaInterfaceList> list) {
		this.list = list;
	}
	
}

