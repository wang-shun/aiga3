package com.ai.process.task.quartz;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.aiga.service.report.CaseConstructionSv;
import com.ai.aiga.util.spring.ApplicationContextUtil;

/**
 * @ClassName: CaseStatisticsJob
 * @author: taoyf
 * @date: 2017年4月25日 下午6:43:03
 * @Description:
 * 
 */
public class CaseStatisticsJob implements Job{
	
	 private static Logger log = LoggerFactory.getLogger(SaySmTask.class);
	 
	 public static final String KEY_MONTH = "month";
	 public static final String KEY_TYPE = "type";

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		JobDataMap dataMap = context.getMergedJobDataMap();
		
		String month = dataMap.getString(KEY_MONTH);
		String type = dataMap.getString(KEY_TYPE);
		
		CaseConstructionSv sv = ApplicationContextUtil.getBean(CaseConstructionSv.class);
		
		sv.count(month, type);
		
	}

}

