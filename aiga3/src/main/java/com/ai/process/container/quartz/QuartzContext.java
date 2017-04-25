package com.ai.process.container.quartz;

import org.quartz.Scheduler;

/**
 * @ClassName: ProcessContext
 * @author: taoyf
 * @date: 2017年4月24日 下午3:22:36
 * @Description:
 * 
 */
public class QuartzContext {
	
	private static QuartzContext quartzContext;
	
	private QuartzContext(){}
	
	public static QuartzContext instance(){
		if(quartzContext == null){
			synchronized (QuartzContext.class) {
				if(quartzContext == null){
					quartzContext = new QuartzContext(); 
				}
			}
		}
		
		return quartzContext;
	}
	
	private Scheduler scheduler;
	
	private String processName;
	
	private volatile boolean init = false;

	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
	
	public void clearScheduler() {
		this.scheduler = null;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public boolean isInit() {
		return init;
	}

	public void hasInit() {
		this.init = true;
	}
	
	

}

