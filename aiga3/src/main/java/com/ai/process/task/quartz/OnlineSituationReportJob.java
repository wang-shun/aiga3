package com.ai.process.task.quartz;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.aiga.service.report.CaseConstructionSv;
import com.ai.aiga.service.report.OnlineSituationReportSv;
import com.ai.aiga.util.spring.ApplicationContextUtil;

/** * @author  lh 
    * @date 创建时间：2017年4月27日 上午10:17:04
    */
public class OnlineSituationReportJob implements Job{
	private static Logger log = LoggerFactory.getLogger(SaySmTask.class);
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap dataMap = context.getMergedJobDataMap();
		OnlineSituationReportSv sv = ApplicationContextUtil.getBean(OnlineSituationReportSv.class);
		sv.count();
	}

}
