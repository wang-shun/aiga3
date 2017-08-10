package com.ai.process.task.quartz.backup;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.aiga.service.backup.DataBackupSv;
import com.ai.aiga.util.spring.ApplicationContextUtil;
import com.ai.process.container.quartz.QuartzHelper;

public class AutoDeleteTask implements Job{

	private static final Logger log = LoggerFactory.getLogger(AutoDeleteTask.class);
	
	public static final String DEAL_ID = "DEAL_ID";

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		JobDataMap dataMap = context.getMergedJobDataMap();
		
		String restoreId = (String) QuartzHelper.getValue(dataMap, DEAL_ID);
		
		long id = Long.parseLong(restoreId);
		
		DataBackupSv dataBackupSv = ApplicationContextUtil.getBean(DataBackupSv.class);
		dataBackupSv.delete(id);
	}

}
