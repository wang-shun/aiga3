package com.ai.am.service.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.am.dao.TasksDao;
import com.ai.am.dao.TasksParameterDao;
import com.ai.am.domain.Tasks;
import com.ai.am.domain.TasksParameter;
import com.ai.am.exception.BusinessException;
import com.ai.am.service.base.BaseService;


/**
 * @ClassName: TaskSv
 * @author: taoyf
 * @date: 2017年4月25日 上午10:09:19
 * @Description:
 * 
 */
@Service
@Transactional
public class TaskSv extends BaseService {
	
	@Autowired
	private TasksDao tasksDao;
	
	@Autowired
	private TasksParameterDao tasksParameterDao;
	
	
	public List<Tasks> findTf(String taskCategory){
		if(StringUtils.isBlank(taskCategory)){
			return new ArrayList<Tasks>();
		}
		
		return tasksDao.findByTaskCategoryAndTaskType(taskCategory, TaskConstant.TASKS_TYPE_TF);
	}
	
	public List<Tasks> findTask(String taskCategory){
		if(StringUtils.isBlank(taskCategory)){
			return new ArrayList<Tasks>();
		}
		
		return tasksDao.findByTaskCategoryAndTaskTypeAndStatus(taskCategory, TaskConstant.TASKS_TYPE_TASK, TaskConstant.TASK_STATUS_DOING);
	}
	
	public List<Tasks> findTask(String taskCategory, int size){
		if(StringUtils.isBlank(taskCategory)){
			return new ArrayList<Tasks>();
		}
		
		if(size <= 0){
			size = 10;
		}
		
		
		return tasksDao.findByTaskCategoryAndTaskTypeAndStatus(taskCategory, TaskConstant.TASKS_TYPE_TASK, TaskConstant.TASK_STATUS_NEW, new PageRequest(0, size));
	}
	
	public void addTask(Tasks t, HashMap<String, String> params){
		
		//构建businessId, 
		int businessId = -1;
		if(params != null){
			businessId = params.hashCode();
		}
		t.setBusinessId(String.valueOf(businessId));
		
		List<Short> statuses = new ArrayList();
		statuses.add(TaskConstant.TASK_STATUS_NEW);
		statuses.add(TaskConstant.TASK_STATUS_DOING);
		List<Tasks> taskss = tasksDao.findByTaskCategoryAndTaskClassAndBusinessIdAndStatusIn(TaskConstant.TASK_CATEGORY_DEFAULT, t.getTaskClass(), String.valueOf(businessId), statuses);
		if(taskss != null && taskss.size() >= 1){
			BusinessException.throwBusinessException("该任务正在执行中, 请等待执行完成!");
		}
		
		Tasks task = tasksDao.save(t);
		task.setBusinessId(String.valueOf(businessId));
		
		if(params != null){
			List<TasksParameter> TasksParameters = new ArrayList<TasksParameter>();
			for(String key : params.keySet()){
				String value = params.get(key);
				
				TasksParameter p = new TasksParameter();
				p.setName(key);
				p.setValue(value);
				p.setTaskId(task.getId());
				TasksParameters.add(p);
				
				TasksParameters.add(p);
			}
			
			tasksParameterDao.save(TasksParameters);
		}
	}
	
	
	public void addTask(Class<? extends Job> jobClass, HashMap<String, String> params){
		
		Tasks t = new Tasks();
		t.setTaskName(jobClass.getSimpleName());
		t.setTaskClass(jobClass.getName());
		t.setTaskCategory(TaskConstant.TASK_CATEGORY_DEFAULT);
		t.setTaskType(TaskConstant.TASKS_TYPE_TASK);
		t.setTaskTriggerType(TaskConstant.TASK_TRIGGER_TYPE_ONCE);
		t.setStatus(TaskConstant.TASK_STATUS_NEW);
		t.setCreateTime(new Date());
		
		addTask(t, params);
	}
	
	public void addTask(Class<? extends Job> jobClass, Date startDate, HashMap<String, String> params){
		
		Tasks t = new Tasks();
		t.setTaskName(jobClass.getSimpleName());
		t.setTaskClass(jobClass.getName());
		t.setTaskCategory(TaskConstant.TASK_CATEGORY_DEFAULT);
		t.setTaskType(TaskConstant.TASKS_TYPE_TASK);
		t.setTaskTriggerType(TaskConstant.TASK_TRIGGER_TYPE_ONCE);
		t.setExecuteTime(startDate);
		t.setStatus(TaskConstant.TASK_STATUS_NEW);
		t.setCreateTime(new Date());
		
		addTask(t, params);
	}
	
	public void addTask(Class<? extends Job> jobClass, String cron, HashMap<String, String> params){
		
		Tasks t = new Tasks();
		t.setTaskName(jobClass.getSimpleName());
		t.setTaskClass(jobClass.getName());
		t.setTaskCategory(TaskConstant.TASK_CATEGORY_DEFAULT);
		t.setTaskType(TaskConstant.TASKS_TYPE_TASK);
		t.setTaskTriggerType(TaskConstant.TASK_TRIGGER_TYPE_CRON);
		t.setCronExpression(cron);
		t.setStatus(TaskConstant.TASK_STATUS_NEW);
		t.setCreateTime(new Date());
		
		addTask(t, params);
	}

}

