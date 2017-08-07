package com.ai.am.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.Tasks;

/**
 * @ClassName: TasksDao
 * @author: taoyf
 * @date: 2017年4月25日 上午10:07:48
 * @Description:
 * 
 */
public interface TasksDao  extends SearchAndPageRepository<Tasks, Long> {

	/**
	 * @ClassName: TasksDao :: findTFs
	 * @author: taoyf
	 * @date: 2017年4月25日 上午10:29:35
	 *
	 * @Description: 根据分类和类型查找所有的TF
	 * @param taskCategory
	 * @param tasksTypeTf          
	 */
	List<Tasks> findByTaskCategoryAndTaskType(String taskCategory, short tasksTypeTf);
	
	List<Tasks> findByTaskCategoryAndTaskTypeAndStatus(String taskCategory, short tasksType, short status);
	
	List<Tasks> findByTaskCategoryAndTaskTypeAndStatus(String taskCategory, short tasksType, short status, Pageable pageRequest);

	List<Tasks> findByTaskCategoryAndTaskClassAndBusinessIdAndStatusIn(String taskCategory, String tasksClass, String businessId, List<Short> status);
	
	
}

