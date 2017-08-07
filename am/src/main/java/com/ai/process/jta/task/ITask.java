package com.ai.process.jta.task;

/**
 * 定时任务接口
 * 
 * @author baofeida
 * 
 */
public interface ITask {

	public String doTask(int taskId) throws Exception;

}