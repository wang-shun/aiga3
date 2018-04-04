package com.ai.task.taskimpl.common;

import java.util.Date;
import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.aiga.domain.ArchTaskPlan;
import com.ai.aiga.service.task.ArchTaskPlanSv;
import com.ai.task.QuartzJob;
import com.ai.task.ScheduleJob;
import com.ai.task.TaskInterFace;

@Component
public class TaskReset implements TaskInterFace {
	@Autowired
	private ScheduleJob scheduleJob;
	@Autowired
	private ArchTaskPlanSv archTaskPlanSv;
	
	@Override
	public void taskDo(ArchTaskPlan param) {
		// TODO Auto-generated method stub
		//定时扫任务表，刷新任务
		taskUpdata();
	}
	public void taskUpdata() {
		List<ArchTaskPlan> taskList = archTaskPlanSv.taskGet();
		scheduleJob.removeAllJob();
		for(ArchTaskPlan taskBase : taskList) {
        	if("com.ai.task.taskimpl.common.TaskReset".equals(taskBase.getClassPath())) {
        		continue;
        	}
			scheduleJob.addJob(taskBase);
		}
	}	
}
