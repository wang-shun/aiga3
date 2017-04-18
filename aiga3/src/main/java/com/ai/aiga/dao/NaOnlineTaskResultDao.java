package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.domain.NaOnlineTaskResult;

/**
 * @ClassName: NaOnlineTaskResultDao
 * @author: dongch
 * @date: 2017年4月6日 下午4:13:01
 * @Description:
 * 
 */
public interface NaOnlineTaskResultDao extends JpaRepository<NaOnlineTaskResult, Long>{

	
	NaOnlineTaskResult findByTaskId(Long taskId);
	
	
	@Modifying
	@Query(value="update  na_online_task_result  set state =?2  where task_Id = ?1" , nativeQuery=true)
	void updateParentTaskDealState(Long taskId,Long state);


}

