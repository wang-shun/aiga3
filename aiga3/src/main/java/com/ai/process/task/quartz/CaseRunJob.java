package com.ai.process.task.quartz;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.aiga.service.report.CaseRunCountSv;
import com.ai.aiga.util.spring.ApplicationContextUtil;

/**
 * @ClassName: CaseRunJob
 * @author: dongch
 * @date: 2017年4月28日 上午11:32:00
 * @Description:
 * 
 */
public class CaseRunJob implements Job{

	private static Logger log = LoggerFactory.getLogger(SaySmTask.class);
	
	 public static final String KEY_MONTH = "month";
	 public static final String KEY_TYPE = "type";
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		JobDataMap dataMap = context.getMergedJobDataMap();
		
		String month = dataMap.getString(KEY_MONTH);
		String type = dataMap.getString(KEY_TYPE);
		CaseRunCountSv sv = ApplicationContextUtil.getBean(CaseRunCountSv.class);
		sv.caseCount();
	}

}

