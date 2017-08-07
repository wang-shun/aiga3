package com.ai.process.config;

import com.ai.process.container.quartz.job.CoreJob;

/**
 * @ClassName: ProcessInfo
 * @author: taoyf
 * @date: 2017年4月21日 下午6:13:28
 * @Description:
 * 
 */
public class ProcessDefaultInfo {
	
	/**
	 * spring配置文件默认位置
	 */
	public static final String SPRING_LOCATION = "classpath:spring-task.xml";
	
	/**
	 * spring task scheduler 调度器ID
	 */
	public static final String SPRING_TASK_SCHEDULER_ID = "aiTaskScheduler";
	
	
	
	
	
	/**
	 * spring配置文件默认位置
	 */
	public static final String QUARTZ_SPRING_LOCATION = "classpath:spring.xml";
	
	/**
	 * 环境
	 */
	public static final String SPRING_PFOFILE_KEY = "spring.profiles.active";
	public static final String SPRING_DEFAULT_PROFILE = "dev";
	
	/**
	 * 核心job
	 */
	public static final String CONFIG_CORE_JOB_INTERVAL_KEY = "corejob.interval.time";
	public static final Class<CoreJob> PROCESS_CORE_JOB = CoreJob.class;
	public static final String PROCESS_CORE_JOB_NAME = CoreJob.class.getSimpleName().toUpperCase();
	public static final Integer PROCESS_CORE_JOB_INTERVAL_SECONDS = 5;
	public static final String PROCESS_CORE_JOB_TRIGGER_NAME = CoreJob.class.getSimpleName().toUpperCase() + "_TRIGGER";
	public static final String PROCESS_CORE_GROUPNAME = "AI_PROCESS_CORE";
	
	
	/**
	 * 普通job
	 */
	public static final String JOB_NAME_PREFIX = "AI_PROCESS";
	public static final String JOB_TRIGGER_NAME_SUFFIX = "_TRIGGER";
	public static final String JOB_GROUPNAME = "AI_PROCESS_JOB";
	public static final String JOB_TRIGGER_GROUPNAME = "AI_PROCESS_JOB_TRIGGER";
	public static final String JOB_TASKS_HOLDER = "AI_PROCESS_TASKS_DB";
	

}

