package com.ai.process.task.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.aiga.service.home.HomeDataSv;

import com.ai.aiga.util.spring.ApplicationContextUtil;

/**
 * 
 * @author tlt
 *
 */
public class NaHomeInfoJob implements Job {
	private static Logger log = LoggerFactory.getLogger(SaySmTask.class);
	// @Override
	// public void execute(JobExecutionContext context) throws
	// JobExecutionException {
	//
	// HomeDataSv homeDataSv = ApplicationContextUtil.getBean(HomeDataSv.class);
	// homeDataSv.addInfo();
	// }

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		HomeDataSv homeDataSv = ApplicationContextUtil.getBean(HomeDataSv.class);
		homeDataSv.addInfo();
	}

}
