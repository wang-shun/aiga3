package com.ai.aiga.service.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.TasksDao;
import com.ai.aiga.dao.TasksParameterDao;
import com.ai.aiga.domain.Tasks;
import com.ai.aiga.domain.TasksParameter;
import com.ai.aiga.service.base.BaseService;


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
	
	public List<Tasks> findTask(String taskCategory, int size){
		if(StringUtils.isBlank(taskCategory)){
			return new ArrayList<Tasks>();
		}
		
		if(size <= 0){
			size = 10;
		}
		
		
		return tasksDao.findByTaskCategoryAndTaskTypeAndStatus(taskCategory, TaskConstant.TASKS_TYPE_TASK, TaskConstant.TASK_STATUS_NEW, new PageRequest(0, size));
	}
	
	public void addTask(Tasks t, Map<String, String> params){
		Tasks task = tasksDao.save(t);
		
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
	
	
	public void addTask(Class<? extends Job> jobClass, Map<String, String> params){
		
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
	
	public void addTask(Class<? extends Job> jobClass, Date startDate, Map<String, String> params){
		
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
	
	public void addTask(Class<? extends Job> jobClass, String cron, Map<String, String> params){
		
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

