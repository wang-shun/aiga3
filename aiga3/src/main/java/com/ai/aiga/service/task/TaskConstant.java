package com.ai.aiga.service.task;

/**
 * @ClassName: TaskConstant
 * @author: taoyf
 * @date: 2017年4月25日 上午10:10:38
 * @Description:
 * 
 */
public class TaskConstant {
	
	
	/**
	 * 任务类型
	 */
	//TF
	public static final short TASKS_TYPE_TF = 1;
	public static final short TASKS_TYPE_TASK = 2;
	
	/**
	 * 执行触发类型
	 */
	//循环
	public static final short TASK_TRIGGER_TYPE_INTERVAL = 1;
	//cron
	public static final short TASK_TRIGGER_TYPE_CRON = 2;
	//once
	public static final short TASK_TRIGGER_TYPE_ONCE = 3;
	
	
	/**
	 * 执行触发类型
	 */
	//未执行
	public static final short TASK_STATUS_NEW = 0;
	//正在执行
	public static final short TASK_STATUS_DOING = 1;
	//执行完成
	public static final short TASK_STATUS_FINISH = 2;
	//执行异常
	public static final short TASK_STATUS_EXCEPTION = 3;
	
	
	
	/**
	 * 默认的一些数据
	 */
	public static final String TASK_CATEGORY_DEFAULT = "default";
	
	
	
	


}

