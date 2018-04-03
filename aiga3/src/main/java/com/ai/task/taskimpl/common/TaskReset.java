package com.ai.task.taskimpl.common;

import java.util.Date;
import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ai.aiga.domain.ArchTaskPlan;
import com.ai.aiga.service.task.ArchTaskPlanSv;
import com.ai.task.QuartzJob;
import com.ai.task.ScheduleJob;
import com.ai.task.TaskInterFace;

@Component
public class TaskReset implements TaskInterFace {
	@Autowired
	private ScheduleJob scheduleJob;
	@Autowired
	private ArchTaskPlanSv archTaskPlanSv;
	
	@Override
	public void taskDo(ArchTaskPlan param) {
		// TODO Auto-generated method stub
		System.out.println("刷新任务表--该任务暂不开通");
		//定时扫任务表，刷新任务
//		scheduleJob.reflashJob();
	}
	
	public void reflashJob() {
		SchedulerFactory  gSchedulerFactory = scheduleJob.gSchedulerFactory;
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
        // ===========================添加定时任务end========================  
        try {  
            sched = gSchedulerFactory.getScheduler(); 
            sched.shutdown();
            sched = gSchedulerFactory.getScheduler(); 
            // 启动  
            if (!sched.isShutdown()) {  
                sched.start();  
            }  
            //模拟将任务从数据库中读取出来并注册到定时器中  
            for (ArchTaskPlan taskInfo : taskList) {  
                Class<? extends Job> myJobClass = QuartzJob.class;  
                // 创建jobDetail信息  
                JobDetail jobDetail = JobBuilder.newJob(myJobClass)  
                        .withIdentity("jobDetailName", taskInfo.getTaskId()+"_"+taskInfo.getTaskName()).build();  
  
                // 放入参数，运行时的方法可以获取  
                jobDetail.getJobDataMap().put("taskInfo", taskInfo);  
                jobDetail.getJobDataMap().put("jobClass", taskInfo.getClassPath());  
                // 表达式调度构建器  
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder  
                        .cronSchedule(taskInfo.getCronexpression());  
  
                // 按新的cronExpression表达式构建一个新的trigger  
                CronTrigger trigger = TriggerBuilder.newTrigger()  
                        .withIdentity("jobTriggerName", taskInfo.getTaskId()+"_"+taskInfo.getTaskName())  
                        .withSchedule(scheduleBuilder).build();  
                sched.scheduleJob(jobDetail, trigger);  
            }  
  
        } catch (SchedulerException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
  
    } 
}
