package com.ai.process.task.quartz;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.aiga.service.report.CaseRunCountSv;
import com.ai.aiga.util.spring.ApplicationContextUtil;
import com.ai.process.container.quartz.QuartzHelper;

/**
 * @ClassName: StaffRunJob
 * @author: dongch
 * @date: 2017年5月2日 下午2:26:28
 * @Description:
 * 
 */
public class StaffRunJob implements Job{

	private static Logger log = LoggerFactory.getLogger(SaySmTask.class);
	
	 public static final String KEY_MONTH = "month";
	 public static final String KEY_TYPE = "type";
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap dataMap = context.getMergedJobDataMap();
		
		String month = (String) QuartzHelper.getValue(dataMap, KEY_MONTH);
		String type = (String) QuartzHelper.getValue(dataMap, KEY_TYPE);
		CaseRunCountSv sv = ApplicationContextUtil.getBean(CaseRunCountSv.class);
		sv.staffCount();
	}

}

