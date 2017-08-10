package com.ai.process.task.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.aiga.service.home.HomeDataSv;
import com.ai.aiga.util.spring.ApplicationContextUtil;

/**
 * @ClassName: FlowTextJob
 * @author: dongch
 * @date: 2017年5月24日 下午9:54:57
 * @Description:
 * 
 */
public class FlowTextJob implements Job{

	private static Logger log = LoggerFactory.getLogger(SaySmTask.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		HomeDataSv sv = ApplicationContextUtil.getBean(HomeDataSv.class);
		sv.flowText();
	}

}

