package com.ai.task.taskimpl.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.aiga.domain.ArchTaskPlan;
import com.ai.task.ScheduleJob;
import com.ai.task.TaskInterFace;

@Component
public class TaskReset implements TaskInterFace {
	@Autowired
	private ScheduleJob scheduleJob;
	@Override
	public void taskDo(ArchTaskPlan param) {
		// TODO Auto-generated method stub
		//定时扫任务表，刷新任务
		scheduleJob.reflashJob();
	}
}
