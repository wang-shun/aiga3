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

/** * @author  lh 
    * @date 创建时间：2017年7月18日 下午2:15:35
    */
public class AutoCompareTableTask implements Job {
	private static final Logger log = LoggerFactory.getLogger(AutoCompareTableTask.class);
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		DataBackupSv sv = ApplicationContextUtil.getBean(DataBackupSv.class);
		sv.compare();
	}

}
