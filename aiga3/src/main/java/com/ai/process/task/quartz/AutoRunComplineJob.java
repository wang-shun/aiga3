package com.ai.process.task.quartz;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.aiga.dao.NaCodePathCompileResultDao;
import com.ai.aiga.dao.NaCodePathComplieDao;
import com.ai.aiga.dao.NaSystemInterfaceAddressDao;
import com.ai.aiga.service.onlineProcess.NodeRecordSv;
import com.ai.aiga.service.report.CaseRunCountSv;
import com.ai.aiga.service.workFlowNew.ReviewPlanSv;
import com.ai.aiga.util.spring.ApplicationContextUtil;
import com.ai.process.container.quartz.QuartzHelper;

/**
 * 定时执行编译job
 * @author liuxx
 *
 */
public class AutoRunComplineJob implements Job {
	private static Logger log = LoggerFactory.getLogger(SaySmTask.class);
	   private static final String  PLAN_Id  = "planId"; 
	    private static final String  EVENTMENTS  = "everment"; 
	    private static final String  NAME  = "name"; 

	    
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap dataMap = context.getMergedJobDataMap();
		ReviewPlanSv sv = ApplicationContextUtil.getBean(ReviewPlanSv.class);
		
		String planId = (String) QuartzHelper.getValue(dataMap, PLAN_Id);

		String name = (String) QuartzHelper.getValue(dataMap, NAME);
		String everment = (String) QuartzHelper.getValue(dataMap, EVENTMENTS);
		sv.NaCodePathCompileToBmc(name, Long.valueOf(planId), everment);
	}

}
