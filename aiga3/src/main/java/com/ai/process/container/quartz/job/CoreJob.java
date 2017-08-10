package com.ai.process.container.quartz.job;

import java.util.List;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.aiga.domain.Tasks;
import com.ai.aiga.service.task.TaskCmpt;
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
public class CoreJob implements Job {

	private static Logger log = LoggerFactory.getLogger(CoreJob.class);

	/**
	 * @Function: Job :: execute @author: taoyf @date: 2017年4月24日
	 * 下午3:03:00 @Description: @return @throws
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		TaskSv sv = ApplicationContextUtil.getBean(TaskSv.class);
		TaskCmpt cmpt = ApplicationContextUtil.getBean(TaskCmpt.class);

		Scheduler scheduler = context.getScheduler();

		if (!QuartzContext.instance().isInit()) {
			
			//第一次, 找TF
			List<Tasks> tfs = sv.findTf(QuartzContext.instance().getProcessName());
			QuartzContext.instance().hasInit();

			if (tfs != null) {
				for (Tasks tf : tfs) {
					try{
						JobAndTrigger detail = QuartzHelper.build(tf);
						scheduleJob(scheduler, detail);
						log.info("开始调度TF : ID = " + tf.getId());
						
						//TODO 虽然存在bug 但是这次就这样吧.
					}catch (Exception e) {
						log.error("启动TF : ID = " + tf.getId() + "报错 ",e);
						
						cmpt.saveTaskErrorInfo(tf, e);
					}
				}
			}
			
			//第一次, 找状态为doing的TF
			List<Tasks> tasks = sv.findTask(QuartzContext.instance().getProcessName());
			LoopTasks(tasks, scheduler, cmpt);
		}

		List<Tasks> tasks = sv.findTask(QuartzContext.instance().getProcessName(), 20);
		LoopTasks(tasks, scheduler, cmpt);

	}
	
	private void LoopTasks(List<Tasks> tasks, Scheduler scheduler, TaskCmpt cmpt){
		if (tasks != null) {
			for (Tasks task : tasks) {
				try{
					JobAndTrigger detail = QuartzHelper.build(task);
					cmpt.saveTaskRunning(task);
					if (!QuartzHelper.isRunning(scheduler, detail)) {
						scheduleJob(scheduler, detail);
						log.info("开始调度TASK : ID = " + task.getId());
					}
				}catch (Exception e) {
					log.error("调用TASK : ID = " + task.getId() + "报错 ",e);
					cmpt.saveTaskErrorInfo(task, e);
				}
			}
		}
	}

	private void scheduleJob(Scheduler scheduler, JobAndTrigger detail) throws SchedulerException {
		scheduler.scheduleJob(detail.getJobDetail(), detail.getTrigger());
	}

}
