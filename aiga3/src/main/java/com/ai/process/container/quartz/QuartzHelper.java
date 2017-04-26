package com.ai.process.container.quartz;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.CronScheduleBuilder.cronSchedule;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.aiga.domain.Tasks;
import com.ai.aiga.domain.TasksParameter;
import com.ai.aiga.service.task.TaskConstant;
import com.ai.process.config.ProcessDefaultInfo;

/**
 * @ClassName: QuartzHelper
 * @author: taoyf
 * @date: 2017年4月25日 上午11:17:42
 * @Description:
 * 
 */
public class QuartzHelper {
	
	 private static Logger log = LoggerFactory.getLogger(QuartzHelper.class);

	/**
	 * @ClassName: QuartzHelper :: build
	 * @author: taoyf
	 * @date: 2017年4月25日 下午2:33:04
	 *
	 * @Description:
	 * @param tf          
	 */
	public static JobAndTrigger build(Tasks tf) {
		
		if(tf == null){
			return null;
		}
		
		//1, 构建jobdetail
		Class clazz = null;
		try {
			clazz = ClassUtils.getClass(tf.getTaskClass());
		} catch (ClassNotFoundException e) {
			error(tf, "无法找到tasks_class的类!");
			return null;
		}
		
		String jobName = getJobName(tf, clazz);
		
		JobDetail job = newJob(clazz)
				.withIdentity(jobName, ProcessDefaultInfo.JOB_GROUPNAME)
				.withDescription(tf.getTaskName())
				.build();
		
		//jobDataMap
		List<TasksParameter> parameters = tf.getParameters();
		if(parameters != null){
			for(TasksParameter p : parameters){
				job.getJobDataMap().put(p.getName(), p.getValue());
			}
		}
		
		
		//2, 构建触发器
		Trigger trigger = null;
		
		if(TaskConstant.TASKS_TYPE_TF == tf.getTaskType()){
			trigger = buildTFTrigger(tf, jobName);
		}else if(TaskConstant.TASKS_TYPE_TASK == tf.getTaskType()){
			trigger = buildTaskTrigger(tf, jobName);
		}else{
			error(tf, "该tasks_type不支持!");
			return null;
		}
		
		if(trigger == null){
			error(tf, "该tasks生产触发时间失败!");
			return null;
		}
		
		return new JobAndTrigger(job, trigger);
	}

	/**
	 * @ClassName: QuartzHelper :: buildTask
	 * @author: taoyf
	 * @date: 2017年4月25日 下午2:35:47
	 *
	 * @Description:
	 * @param tf
	 * @return          
	 */
	private static Trigger buildTaskTrigger(Tasks tf, String jobName) {
		
		if(TaskConstant.TASK_TRIGGER_TYPE_INTERVAL == tf.getTaskTriggerType()){
			TriggerBuilder builder = newTrigger().withIdentity(jobName + ProcessDefaultInfo.JOB_TRIGGER_NAME_SUFFIX, ProcessDefaultInfo.JOB_TRIGGER_GROUPNAME);
			Date date = tf.getExecuteTime();
			if(date != null){
				builder.startAt(date);
			}
			
			Long IntervalTime = tf.getIntervalTime();
			if(IntervalTime == null || IntervalTime <= 0){
				return null;
			}
			builder.withSchedule(simpleSchedule().withIntervalInSeconds(IntervalTime.intValue()).repeatForever());
			return builder.build();
		}else if(TaskConstant.TASK_TRIGGER_TYPE_CRON ==  tf.getTaskTriggerType()){
			TriggerBuilder builder = newTrigger().withIdentity(jobName + ProcessDefaultInfo.JOB_TRIGGER_NAME_SUFFIX, ProcessDefaultInfo.JOB_TRIGGER_GROUPNAME);
			
			String cron = tf.getCronExpression();
			if(StringUtils.isBlank(cron)){
				return null;
			}
			try{
				builder.withSchedule(cronSchedule(cron));
			}catch (Exception e) {
				error(tf, "该cron表达式错误!");
				return null;
			}
			
			return builder.build();
		}else if(TaskConstant.TASK_TRIGGER_TYPE_ONCE ==  tf.getTaskTriggerType()){
			TriggerBuilder builder = newTrigger().withIdentity(jobName + ProcessDefaultInfo.JOB_TRIGGER_NAME_SUFFIX, ProcessDefaultInfo.JOB_TRIGGER_GROUPNAME);
			Date date = tf.getExecuteTime();
			if(date != null){
				builder.startAt(date);
			}else{
				builder.startNow();
			}
			
			return builder.build();
		}
		
		error(tf, "TF 暂不支持这种触发模式");
		return null;
	}

	/**
	 * @ClassName: QuartzHelper :: buildTF
	 * @author: taoyf
	 * @date: 2017年4月25日 下午2:35:44
	 *
	 * @Description:
	 * @param tf
	 * @return          
	 */
	private static Trigger buildTFTrigger(Tasks tf, String jobName) {
		
		
		if(TaskConstant.TASK_TRIGGER_TYPE_INTERVAL == tf.getTaskTriggerType()){
			TriggerBuilder builder = newTrigger().withIdentity(jobName + ProcessDefaultInfo.JOB_TRIGGER_NAME_SUFFIX, ProcessDefaultInfo.JOB_TRIGGER_GROUPNAME);
			Date date = tf.getExecuteTime();
			if(date != null){
				builder.startAt(date);
			}
			
			Long IntervalTime = tf.getIntervalTime();
			if(IntervalTime == null || IntervalTime <= 0){
				return null;
			}
			builder.withSchedule(simpleSchedule().withIntervalInSeconds(IntervalTime.intValue()).repeatForever());
			return builder.build();
		}else if(TaskConstant.TASK_TRIGGER_TYPE_CRON ==  tf.getTaskTriggerType()){
			TriggerBuilder builder = newTrigger().withIdentity(jobName + ProcessDefaultInfo.JOB_TRIGGER_NAME_SUFFIX, ProcessDefaultInfo.JOB_TRIGGER_GROUPNAME);
			
			String cron = tf.getCronExpression();
			if(StringUtils.isBlank(cron)){
				return null;
			}
			try{
				builder.withSchedule(cronSchedule(cron));
			}catch (Exception e) {
				error(tf, "该cron表达式错误!");
				return null;
			}
			
			return builder.build();
		}
		
		error(tf, "TF 暂不支持这种触发模式");
		return null;
	}
	

	/**
	 * @ClassName: QuartzHelper :: getTaskName
	 * @author: taoyf
	 * @date: 2017年4月25日 下午2:55:50
	 *
	 * @Description:
	 * @param tf
	 * @param clazz
	 * @return          
	 */
	private static String getJobName(Tasks tf, Class clazz) {
		StringBuilder sb = new StringBuilder();
		sb.append(ProcessDefaultInfo.JOB_NAME_PREFIX);
		sb.append("_");
		sb.append(clazz.getSimpleName());
		sb.append("_");
		sb.append(tf.getId());
		
		return sb.toString();
	}

	private static void error(Tasks tf, String reason){
		StringBuilder sb = new StringBuilder();
		sb.append("构建执行job失败 : ");
		sb.append(reason);
		sb.append(" ");
		sb.append("任务信息 : ");
		sb.append(tf);
		
		log.error(sb.toString());
	}
	
	
	
	/**
	 * @ClassName: CoreJob :: isRunning
	 * @author: taoyf
	 * @date: 2017年4月25日 下午4:36:56
	 *
	 * @Description:
	 * @param scheduler
	 * @param detail
	 * @return          
	 */
	public static boolean isRunning(Scheduler scheduler, JobAndTrigger detail) {
		
		JobDetail jobDetail = null;
		try {
			jobDetail = scheduler.getJobDetail(detail.getJobDetail().getKey());
		} catch (SchedulerException e) {
			log.error("判断task" + detail.getJobDetail().getKey() + " 是否已经在执行失败!", e);
			return true;
		}
		
		return jobDetail != null;
	}


	/**
	 * @ClassName: CoreJob :: scheduleJob
	 * @author: taoyf
	 * @date: 2017年4月25日 下午4:34:39
	 *
	 * @Description:
	 * @param scheduler
	 * @param detail          
	 */
	public static void scheduleJob(Scheduler scheduler, JobAndTrigger detail) {
		
		try {
			scheduler.scheduleJob(detail.getJobDetail(), detail.getTrigger());
		} catch (SchedulerException e) {
			log.error("执行" + detail.getJobDetail().getKey() + " 失败!", e);
		}
	}
	

}

