package com.ai.process.task.quartz;
/** * @author  lh 
    * @date 创建时间：2017年4月27日 上午10:33:08
    */

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.aiga.service.report.PerformanceCaseSv;
import com.ai.aiga.util.spring.ApplicationContextUtil;

public class PerformanceCaseJob implements Job {
	private static Logger log = LoggerFactory.getLogger(SaySmTask.class);
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap dataMap = context.getMergedJobDataMap();
		PerformanceCaseSv sv = ApplicationContextUtil.getBean(PerformanceCaseSv.class);
		sv.count();
	}

}
