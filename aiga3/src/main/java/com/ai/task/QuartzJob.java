package com.ai.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ai.aiga.domain.ArchTaskPlan;
import com.ai.aiga.util.spring.ApplicationContextUtil;

public class QuartzJob implements Job {
		
    @Override  
    public void execute(JobExecutionContext arg0) throws JobExecutionException {  
        String jobName = arg0.getJobDetail().getKey().getGroup();// 获取jobName做业务处理  
        JobDataMap mergedJobDataMap = arg0.getMergedJobDataMap(); 
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+ "------定时任务开始------"+jobName);    

        try {  
        	Class jobClass = Class.forName(mergedJobDataMap.get("jobClass").toString().trim()); 
            //从mergedJobDataMap中获取绑定的任务类  
        	TaskInterFace springBean = (TaskInterFace)ApplicationContextUtil .getBean(jobClass);
            //调用执行方法  
        	springBean.taskDo((ArchTaskPlan)mergedJobDataMap.get("taskInfo"));  
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+ "------定时任务结束------"+jobName);    
    } 
}
