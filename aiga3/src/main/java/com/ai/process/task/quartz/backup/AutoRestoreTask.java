package com.ai.process.task.quartz.backup;

import java.util.HashMap;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.aiga.service.backup.DataBackupSv;
import com.ai.aiga.util.spring.ApplicationContextUtil;
import com.ai.process.container.quartz.QuartzHelper;

public class AutoRestoreTask implements Job{

	private static final Logger log = LoggerFactory.getLogger(AutoRestoreTask.class);
	
	//public static final String RESTORE_ID = "RESTORE_ID";

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		JobDataMap dataMap = context.getMergedJobDataMap();
		
		HashMap map =  QuartzHelper.getMap(dataMap);
		
		DataBackupSv dataBackupSv = ApplicationContextUtil.getBean(DataBackupSv.class);
		dataBackupSv.restoreOne(map);
	}

}
