package com.ai.process.container.quartz;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ai.am.util.ExceptionUtil;
import com.ai.process.config.ProcessDefaultInfo;
import com.ai.process.container.Container;
import com.ai.process.container.quartz.listener.TaskLifeCycleListener;


/**
 * @ClassName: QuartzContainer
 * @author: taoyf
 * @date: 2017年4月21日 下午3:40:54
 * @Description:
 * 
 */
public class QuartzContainer implements Container{
	
	private Scheduler scheduler;
	
	private ClassPathXmlApplicationContext context;

	public void start() {
		
		// 1, 启动spring.
		context = new ClassPathXmlApplicationContext();
		String profile = System.getProperty(ProcessDefaultInfo.SPRING_PFOFILE_KEY);
		if(StringUtils.isBlank(profile)){
			profile = ProcessDefaultInfo.SPRING_DEFAULT_PROFILE;
		}
		
		String[] profiles = StringUtils.split(profile, ",");
		context.getEnvironment().setActiveProfiles(profiles);
		
		String sprongLocation = ProcessDefaultInfo.QUARTZ_SPRING_LOCATION;
		context.setConfigLocations(sprongLocation.split("[,\\s]+"));
		context.refresh();
		
		context.start();
		
		// 2, 启动quartz
		
		// Grab the Scheduler instance from the Factory
		try {
			
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			// and start it off
			scheduler.start();
			
			//设置到上线文中
			QuartzContext.instance().setScheduler(scheduler);
			
			//3, 添加核心JOB
			JobDetail job = newJob(ProcessDefaultInfo.PROCESS_CORE_JOB)
					.withIdentity(ProcessDefaultInfo.PROCESS_CORE_JOB_NAME, ProcessDefaultInfo.PROCESS_CORE_GROUPNAME)
					.build();
			
			Trigger trigger = newTrigger().withIdentity(ProcessDefaultInfo.PROCESS_CORE_JOB_TRIGGER_NAME, ProcessDefaultInfo.PROCESS_CORE_GROUPNAME).startNow()
					.withSchedule(simpleSchedule().withIntervalInSeconds(5).repeatForever()).build();
			
			scheduler.getListenerManager().addJobListener(new TaskLifeCycleListener());
			
			scheduler.scheduleJob(job, trigger);
			
		} catch (SchedulerException e) {
			QuartzContext.instance().clearScheduler();
			ExceptionUtil.unchecked(e);
		}
	}

	public void stop() {
		try {
			scheduler.shutdown(true);
			context.stop();
			QuartzContext.instance().clearScheduler();
		} catch (SchedulerException e) {
			ExceptionUtil.unchecked(e);
		}
	}

}

