package com.ai.process.task.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @ClassName: SaySmTask
 * @author: taoyf
 * @date: 2017年4月25日 上午10:59:58
 * @Description:
 * 
 */
public class SaySmTask2 implements Job{
	
	 private static Logger log = LoggerFactory.getLogger(SaySmTask2.class);


	/**
	 * @Function: Job :: execute
	 * @author: taoyf
	 * @date: 2017年4月25日 上午11:00:17
	 * @Description: 
	 * @return
	 * @throws 
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		String test = "测试--" + System.currentTimeMillis() + "yingjj 你是猪么?" ;
		
		try {
			Thread.sleep(10*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.info(test);
		System.out.println(test);
		
	}

}

