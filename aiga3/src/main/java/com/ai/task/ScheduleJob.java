package com.ai.task;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.aiga.domain.ArchTaskPlan;
import com.ai.aiga.service.task.ArchTaskPlanSv;

@Component
public class ScheduleJob {
	private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();  
    private static String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME";  
    private static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";  
    private static List<ArchTaskPlan> hisTaskList;
	@Autowired
	private ArchTaskPlanSv archTaskPlanSv;
    @PostConstruct
	public void initJob() {	  
        Scheduler sched;  
        // ===========================添加定时任务start========================  
        List<ArchTaskPlan> taskList = archTaskPlanSv.taskGet();
        // 保证定时扫表
        Boolean isReflash = false;
        for (ArchTaskPlan taskInfo : taskList) {  
        	if("com.ai.task.taskimpl.common.TaskReset".equals(taskInfo.getClassPath())) {
        		isReflash = true;
        	}
        }      
        if(isReflash == false) {
        	ArchTaskPlan resetTask = new ArchTaskPlan();
        	resetTask.setClassPath("com.ai.task.taskimpl.common.TaskReset");
        	resetTask.setCronexpression("0 0 12,18 * * ? ");
        	resetTask.setCreateDate(new Date());
        	resetTask.setTaskId(99999999L);
        	resetTask.setTaskName("定时刷新任务表");
        	resetTask.setTaskDesc("定时扫表，读取任务执行");
        	taskList.add(resetTask);
        }
        hisTaskList = taskList;
        // ===========================添加定时任务end========================  
        try {  
            sched = gSchedulerFactory.getScheduler(); 
            // 启动  
            if (!sched.isShutdown()) {  
                sched.start();  
            }  
            //模拟将任务从数据库中读取出来并注册到定时器中  
            for (ArchTaskPlan taskInfo : taskList) {  
            	addJob(taskInfo);
            }  
  
        } catch (SchedulerException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
  
    } 
    
    @PreDestroy
	public void endJob() {	
    	Scheduler sched;
		try {
			sched = gSchedulerFactory.getScheduler();
	        sched.shutdown();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}  
	
    /** 
     * 添加一个任务 
     * @param job 
     * @param triggerkey 
     * @throws SchedulerException 
     */  	
    public static void addJob(ArchTaskPlan taskInfo) {  
        try {  
        	Scheduler sched = gSchedulerFactory.getScheduler();
            Class<? extends Job> myJobClass = QuartzJob.class;  
            // 创建jobDetail信息  
            JobDetail jobDetail = JobBuilder.newJob(myJobClass)  
                    .withIdentity(taskInfo.getTaskId()+"_"+taskInfo.getTaskName(),JOB_GROUP_NAME).build();  

            // 放入参数，运行时的方法可以获取  
            jobDetail.getJobDataMap().put("taskInfo", taskInfo);  
            jobDetail.getJobDataMap().put("jobClass", taskInfo.getClassPath());  
            // 表达式调度构建器  
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder  
                    .cronSchedule(taskInfo.getCronexpression());  

            // 按新的cronExpression表达式构建一个新的trigger  
            CronTrigger trigger = TriggerBuilder.newTrigger()  
                    .withIdentity(taskInfo.getTaskId()+"_"+taskInfo.getTaskName(),TRIGGER_GROUP_NAME)  
                    .withSchedule(scheduleBuilder).build();  
            sched.scheduleJob(jobDetail, trigger);  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }
	
    /** 
     * 删除一个任务 
     * @param job 
     * @param triggerkey 
     * @throws SchedulerException 
     */  	
    public static void removeJob(String jobName) {  
        try {  
        	Scheduler sched = gSchedulerFactory.getScheduler();
        	TriggerKey triggerKey = TriggerKey.triggerKey(jobName,TRIGGER_GROUP_NAME);
        	//根据TriggerKey获取trigger
            CronTrigger trigger = (CronTrigger)sched.getTrigger(triggerKey);
            if(trigger != null){
            	sched.pauseTrigger(triggerKey);      // 停止触发器
            	sched.unscheduleJob(triggerKey);     // 移除触发器
            	sched.deleteJob(trigger.getJobKey());// 删除任务
            }
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }
    
    /** 
     * 删除所有任务 
     * @param job 
     * @param triggerkey 
     * @throws SchedulerException 
     */  	
    public static void removeAllJob() {  
    	if(hisTaskList==null||hisTaskList.isEmpty()) {
    		//任务为空，不做处理
    	} else {
    		for(ArchTaskPlan taskBase :hisTaskList) {
            	if("com.ai.task.taskimpl.common.TaskReset".equals(taskBase.getClassPath())) {
            		continue;
            	}
    			removeJob(taskBase.getTaskId()+"_"+taskBase.getTaskName());
    		}
    	}
    }	
}
