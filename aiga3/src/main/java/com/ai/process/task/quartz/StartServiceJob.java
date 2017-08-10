package com.ai.process.task.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.aiga.service.auto.listener.MachineListener;
import com.ai.aiga.util.spring.ApplicationContextUtil;

/**
 * @ClassName: StartServiceJob
 * @author: dongch
 * @date: 2017年5月26日 下午2:24:16
 * @Description:
 * 
 */
public class StartServiceJob implements Job{

	private static Logger log = LoggerFactory.getLogger(SaySmTask.class);
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		MachineListener listener = ApplicationContextUtil.getBean(MachineListener.class);
		try {
			listener.startService();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

