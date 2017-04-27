package com.ai.process.container.quartz.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.aiga.domain.Tasks;
import com.ai.aiga.service.task.TaskCmpt;
import com.ai.aiga.service.task.TaskConstant;
import com.ai.aiga.util.ExceptionUtil;
import com.ai.aiga.util.spring.ApplicationContextUtil;
import com.ai.aiga.util.text.MoreStringUtil;
import com.ai.process.config.ProcessDefaultInfo;


/**
 * @ClassName: JobListener
 * @author: taoyf
 * @date: 2017年4月26日 下午4:10:14
 * @Description:
 * 
 */
public class TaskLifeCycleListener implements JobListener{
	
	private static Logger log = LoggerFactory.getLogger(TaskLifeCycleListener.class);

	@Override
	public String getName() {
		return TaskLifeCycleListener.class.getName();
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		log.info(context.getJobDetail().getKey() + " - 不在运行");
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		
		Tasks task = (Tasks) context.getMergedJobDataMap().get(ProcessDefaultInfo.JOB_TASKS_HOLDER);
		if(task == null){
			//大约是核心job
			return ;
		}
		
		if (TaskConstant.TASKS_TYPE_TF == task.getTaskType()) {
			//认为是驻留, 就不处理了.
		} else if (TaskConstant.TASKS_TYPE_TASK == task.getTaskType()) {
			TaskCmpt cmpt = ApplicationContextUtil.getBean(TaskCmpt.class);
			if(jobException != null){
				
				String msg = ExceptionUtil.stackTraceText(jobException);
				
				if(msg != null && msg.getBytes().length > 2000){
					String[] ss = MoreStringUtil.substringAsL(msg, 2000);
					if(ss != null && ss.length > 0){
						msg = ss[0];
					}else{
						msg = "";
					}
				}
				cmpt.saveTaskErrorInfo(task, msg);
			}else{
				cmpt.saveTaskFinish(task);
			}
		}
	}

}

