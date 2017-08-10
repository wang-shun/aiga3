package com.ai.process.task.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ai.aiga.service.auto.AutoResourcePoolSv;
import com.ai.aiga.util.spring.ApplicationContextUtil;

public class ResourcePoolUpdateJob implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		AutoResourcePoolSv poolSv = ApplicationContextUtil.getBean(AutoResourcePoolSv.class);
		poolSv.ResourcePoolUpdate();
	}

}
