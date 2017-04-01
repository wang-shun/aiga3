package com.ai.aiga.jta.task;

/**
 * 定时任务接口
 * 
 * @author baofeida
 * 
 */
public interface ITask {

	public String doTask(int taskId) throws Exception;

}