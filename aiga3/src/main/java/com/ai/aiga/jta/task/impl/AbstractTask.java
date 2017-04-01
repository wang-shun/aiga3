package com.ai.aiga.jta.task.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.aiga.jta.task.ITask;

public abstract class AbstractTask implements ITask {

	private static transient Logger log = LoggerFactory.getLogger(AbstractTask.class);

	public static final String RESULT_NULL = "RESULT_NULL";// 没有执行
	public static final String RESULT_SUCCESS = "RESULT_SUCCESS";// 执行成功

	// 防止并发计数器
	private Long COUNT = new Long(0L);

	public String doTask(int taskId) throws Exception {
		long tmp = this.COUNT.longValue();
		if (tmp == this.COUNT.longValue()) {
			synchronized (this.COUNT) {
				if (tmp == this.COUNT.longValue()) {
					if (log.isInfoEnabled()) {
						log.info("task[" + taskId + "],实现类:" + super.getClass().getName()
								+ ",开始执行任务");
					}
					long startTime = System.currentTimeMillis();
					
					// 业务方法
					doBusiness();
					
					this.COUNT = new Long(this.COUNT.longValue() + 1L);

					long endTime = System.currentTimeMillis();
					if (log.isInfoEnabled()) {
						log.info("task[" + taskId + "]执行任务耗时：" + (endTime - startTime) / 1000
								+ "sec");
					}

					return RESULT_SUCCESS;
				}
			}
		}
		return RESULT_NULL;
	}

	/**
	 * 定时处理业务方法
	 * 
	 * @throws Exception
	 */
	public abstract void doBusiness() throws Exception;

}
