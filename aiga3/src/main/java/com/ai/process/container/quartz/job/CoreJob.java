package com.ai.process.container.quartz.job;

import java.util.List;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.aiga.domain.Tasks;
import com.ai.aiga.service.task.TaskSv;
import com.ai.aiga.util.spring.ApplicationContextUtil;
import com.ai.process.container.quartz.JobAndTrigger;
import com.ai.process.container.quartz.QuartzContext;
import com.ai.process.container.quartz.QuartzHelper;

/**
 * @ClassName: CoreJob
 * @author: taoyf
 * @date: 2017年4月24日 下午2:40:31
 * @Description:
 * 
 */
@DisallowConcurrentExecution  
public class CoreJob implements Job{
	
	 private static Logger log = LoggerFactory.getLogger(CoreJob.class);

	/**
	 * @Function: Job :: execute
	 * @author: taoyf
	 * @date: 2017年4月24日 下午3:03:00
	 * @Description: 
	 * @return
	 * @throws 
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		TaskSv sv = ApplicationContextUtil.getBean(TaskSv.class);
		
		Scheduler scheduler = context.getScheduler();
		
		if(!QuartzContext.instance().isInit()){
			List<Tasks> tfs = sv.findTf(QuartzContext.instance().getProcessName());
			QuartzContext.instance().hasInit();
			
			if(tfs != null){
				for(Tasks tf : tfs){
					JobAndTrigger detail = QuartzHelper.build(tf);
					
					if(detail != null){
						QuartzHelper.scheduleJob(scheduler, detail);
						log.info("开始调度TF : ID = " + tf.getId());
					}
				}
			}
			
		}
		
		List<Tasks> tasks = sv.findTask(QuartzContext.instance().getProcessName(), 20);
		if(tasks != null){
			for(Tasks task : tasks){
				JobAndTrigger detail = QuartzHelper.build(task);
				
				if(detail != null && !QuartzHelper.isRunning(scheduler, detail)){
					QuartzHelper.scheduleJob(scheduler, detail);
					log.info("开始调度TASK : ID = " + task.getId());
				}
			}
		}
		
		
	}


}

