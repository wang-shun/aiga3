package com.ai.process.task.quartz;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.aiga.service.report.CaseRunCountSv;
import com.ai.aiga.service.workFlowNew.ReviewPlanSv;
import com.ai.aiga.util.spring.ApplicationContextUtil;
import com.ai.process.container.quartz.QuartzHelper;

public class OnlineCaseRunJob implements Job{

	private static Logger log = LoggerFactory.getLogger(SaySmTask.class);
	
	 public static final String TASK_ID = "taskId";
	 public static final String PLAN_ID = "planId";
	 public static final String COUNT = "count";
	 public static final String SYS_NAME = "sysName";

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {	
		
		JobDataMap dataMap = context.getMergedJobDataMap();
		ReviewPlanSv sv = ApplicationContextUtil.getBean(ReviewPlanSv.class);		
		String taskId = (String) QuartzHelper.getValue(dataMap, TASK_ID);
		String planId = (String) QuartzHelper.getValue(dataMap, PLAN_ID);
		String count = (String) QuartzHelper.getValue(dataMap, COUNT);
		String sysName = (String) QuartzHelper.getValue(dataMap, SYS_NAME);
		String result = sv.getValidateResult(Long.parseLong(taskId), Long.parseLong(planId), count, sysName);
		if("success".equals(result)){
			sv.setResultState(Long.parseLong(planId), count, sysName);
		}
	}

}

