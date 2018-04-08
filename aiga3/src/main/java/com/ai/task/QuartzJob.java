package com.ai.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ai.aiga.dao.NaIndexAllocationDao;
import com.ai.aiga.domain.ArchTaskControl;
import com.ai.aiga.domain.ArchTaskPlan;
import com.ai.aiga.service.task.ArchTaskControlSv;
import com.ai.aiga.util.spring.ApplicationContextUtil;
@DisallowConcurrentExecution 
public class QuartzJob implements Job {	
    protected final Log logger = LogFactory.getLog(getClass());

    @Override  
    public void execute(JobExecutionContext arg0) throws JobExecutionException {  
        String jobName = arg0.getJobDetail().getKey().getName();// 获取jobName做业务处理  
        JobDataMap mergedJobDataMap = arg0.getMergedJobDataMap();
        //多实例判断
        if(clusterTaskControl(arg0)) {
            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+ "======定时任务开始======Task"+jobName);    
            try {  
            	Class jobClass = Class.forName(mergedJobDataMap.get("jobClass").toString().trim()); 
                //从mergedJobDataMap中获取绑定的任务类  
            	TaskInterFace springBean = (TaskInterFace)ApplicationContextUtil .getBean(jobClass);
                //调用执行方法 
            	springBean.taskDo((ArchTaskPlan)mergedJobDataMap.get("taskInfo"));  
            } catch (Exception e) {  
                e.printStackTrace(); 
                logger.error(e);
            }
            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+ "======定时任务结束======Task"+jobName);
        } else {
        	//其他实例已经执行，跳过 	
        }
    } 
    
    //多实例处理防止   任务多次执行
    private Boolean clusterTaskControl(JobExecutionContext arg0) {
    	try {
        	ArchTaskControlSv archTaskControlSv = ApplicationContextUtil.getBean(ArchTaskControlSv.class);
        	String className = arg0.getMergedJobDataMap().get("jobClass").toString().trim();
        	if("com.ai.task.taskimpl.common.TaskReset".equals(className) || archTaskControlSv.check(arg0.getFireInstanceId(),String.valueOf(arg0.getNextFireTime()))) {
        		//任务未执行
        		ArchTaskControl insTask = new ArchTaskControl();
        		insTask.setFireTime(String.valueOf(arg0.getFireTime()));
        		insTask.setTaskIns(arg0.getJobDetail().getKey().getName());
        		insTask.setNextFireTime(String.valueOf(arg0.getNextFireTime()));
        		insTask.setClassPath(className);
        		insTask.setState('U');
        		archTaskControlSv.update(insTask);
        		return true;	
        	} else {
        		//任务已执行
        		return false;	
        	}
		} catch (Exception e) {
            e.printStackTrace(); 
            logger.error(e);
		}
    	return false;
    }
}
