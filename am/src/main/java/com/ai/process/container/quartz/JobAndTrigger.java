package com.ai.process.container.quartz;

import org.quartz.JobDetail;
import org.quartz.Trigger;

/**
 * @ClassName: JobAndTrigger
 * @author: taoyf
 * @date: 2017年4月25日 下午2:30:48
 * @Description:
 * 
 */
public class JobAndTrigger {

	private JobDetail jobDetail;
	private Trigger trigger;

	/**
	 * @param jobDetail
	 * @param trigger
	 */
	public JobAndTrigger(JobDetail jobDetail, Trigger trigger) {
		this.jobDetail = jobDetail;
		this.trigger = trigger;
	}

	public JobDetail getJobDetail() {
		return jobDetail;
	}

	public void setJobDetail(JobDetail jobDetail) {
		this.jobDetail = jobDetail;
	}

	public Trigger getTrigger() {
		return trigger;
	}

	public void setTrigger(Trigger trigger) {
		this.trigger = trigger;
	}

}
