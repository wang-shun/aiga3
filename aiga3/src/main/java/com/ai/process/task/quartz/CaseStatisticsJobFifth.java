package com.ai.process.task.quartz;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.aiga.service.report.CaseConstructionSv;
import com.ai.aiga.util.spring.ApplicationContextUtil;

/**
 * @ClassName: CaseStatisticsJobFifth
 * @author: dongch
 * @date: 2017年5月24日 下午8:46:46
 * @Description:
 * 
 */
public class CaseStatisticsJobFifth implements Job{

	private static Logger log = LoggerFactory.getLogger(SaySmTask.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
		Calendar calendar = Calendar.getInstance();  
		calendar.setTime(new Date());  
		String month = formatter.format(calendar.getTime());
		String type = "jobStaff";
		
		CaseConstructionSv sv = ApplicationContextUtil.getBean(CaseConstructionSv.class);
		
		sv.count(month, type);
	}

}

