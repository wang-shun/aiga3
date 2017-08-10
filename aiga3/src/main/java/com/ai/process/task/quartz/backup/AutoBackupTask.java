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


public class AutoBackupTask implements Job {

	private static final Logger log = LoggerFactory.getLogger(AutoBackupTask.class);

	public static final String DEAL_ID = "DEAL_ID";

	/**
	 * @Function: Job :: execute
	 * @author: taoyf
	 * @date: 2017年5月8日 下午3:57:25
	 * @Description: 
	 * @return
	 * @throws 
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		JobDataMap dataMap = context.getMergedJobDataMap();
		
		String dealId = (String) QuartzHelper.getValue(dataMap, DEAL_ID);
		
		long id = Long.parseLong(dealId);
		
		DataBackupSv sv = ApplicationContextUtil.getBean(DataBackupSv.class);
		sv.backupOne(id);
		
		
	}

}
