package com.ai.am.service.task;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ai.am.dao.TasksDao;
import com.ai.am.domain.Tasks;
import com.ai.am.service.base.BaseService;

/**
 * @ClassName: TaskCmpt
 * @author: taoyf
 * @date: 2017年4月26日 下午2:56:01
 * @Description:
 * 
 */
@Component
@Transactional
public class TaskCmpt extends BaseService {
	
	@Autowired
	private TasksDao tasksDao;
	

	/**
	 * @ClassName: TaskCmpt :: saveTaskErrorInfo
	 * @author: taoyf
	 * @date: 2017年4月26日 下午3:19:23
	 *
	 * @Description:
	 * @param tf
	 * @param errorinfo          
	 */
	public void saveTaskErrorInfo(Tasks tf, String errorinfo) {
		
		if(tf != null){
			Tasks t = tasksDao.findOne(tf.getId());
			
			t.setStatus(TaskConstant.TASK_STATUS_EXCEPTION);
			t.setExceptionDetail(errorinfo);
			t.setFinishTime(new Date());
			tasksDao.save(t);
		}
	}

	/**
	 * @ClassName: TaskCmpt :: saveTaskRunning
	 * @author: taoyf
	 * @date: 2017年4月26日 下午3:42:28
	 *
	 * @Description:
	 * @param tf          
	 */
	public void saveTaskRunning(Tasks tf) {
		
		if(tf != null){
			Tasks t = tasksDao.findOne(tf.getId());
			
			
			if(TaskConstant.TASKS_TYPE_TF == t.getTaskType()){
				return;
			}
			
			if(TaskConstant.TASK_STATUS_NEW == t.getStatus()){
				t.setStatus(TaskConstant.TASK_STATUS_DOING);
				tasksDao.save(t);
			}
		}
		
	}
	
	public void saveTaskFinish(Tasks tf) {
		
		if(tf != null){
			Tasks t = tasksDao.findOne(tf.getId());
			
			
			if(TaskConstant.TASKS_TYPE_TF == t.getTaskType()){
				return;
			}
			
			t.setStatus(TaskConstant.TASK_STATUS_FINISH);
			t.setFinishTime(new Date());
			tasksDao.save(t);
		}
		
	}


}

