package com.ai.process.task.quartz;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.aiga.service.report.CaseRunCountSv;
import com.ai.aiga.util.spring.ApplicationContextUtil;
import com.ai.process.container.quartz.QuartzHelper;

/**
 * * @author lh
 * 
 * @date 创建时间：2017年5月22日 下午5:14:46
 */
public class AutoRunResultToCaseResultJob implements Job {
	private static Logger log = LoggerFactory.getLogger(SaySmTask.class);
	public static final String SUB_TASK_ID = "SUB_TASK_ID";
	public static final String AUTO_TASK_ID = "AUTO_TASK_ID";
	/**
	 * @Description: TODO
	 * @author lh
	 * @date 2017年5月22日 下午5:14:46
	 * @param
	 * @return
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap dataMap = context.getMergedJobDataMap();
		//传入参数应该有2个，1：sub_task_id.---分派的子任务编号。2：auto_task_id.-----对应的自动化任务编号
		Object subTaskIdObj = QuartzHelper.getValue(dataMap, SUB_TASK_ID);
		Object autoTaskIdObj = QuartzHelper.getValue(dataMap, AUTO_TASK_ID);
		Long subTaskId = null;
		Long autoTaskId = null;
		try {
            if(subTaskIdObj instanceof Long){
            	subTaskId = ((Long) subTaskIdObj).longValue();
            }else{
            	subTaskId = Long.parseLong((String)subTaskIdObj);
            }
        } catch (Exception e) {
            throw new ClassCastException("Identified object is not a Long.");
        }
		try {
            if(autoTaskIdObj instanceof Long){
            	autoTaskId = ((Long) autoTaskIdObj).longValue();
            }else{
            	autoTaskId = Long.parseLong((String)autoTaskIdObj);
            }
        } catch (Exception e) {
            throw new ClassCastException("Identified object is not a Long.");
        }
		
		CaseRunCountSv sv = ApplicationContextUtil.getBean(CaseRunCountSv.class);
		boolean  falg=sv.resultMove(subTaskId,autoTaskId);
		//自动化任务执行结束，进程停止
		if(falg){
			QuartzHelper.stopJobScheduler(context);
		}
		
	}

}
