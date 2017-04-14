package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaOnlineTaskDistribute;

public interface NaOnlineTaskDistributeDao extends JpaRepository<NaOnlineTaskDistribute, Long>, SearchAndPageRepository<NaOnlineTaskDistribute, Long>{
	
	@Query(value = "select a.task_id, a.task_name, a.task_type, a.deal_state, b.name as creator_name from na_online_task_distribute a,"
			+ " aiga_staff b where a.deal_op_id = b.staff_id and a.online_plan =?1 ", nativeQuery = true)
	List<Object[]> findByOnlinePlan(Long onlinePlan);
	
	
	@Query(value = "select a.task_id, a.task_name, a.task_type, a.deal_state, b.name as creator_name from na_online_task_distribute a,"
			+ " aiga_staff b where a.deal_op_id = b.staff_id and a.online_plan =?1 and a.parent_task_id=0", nativeQuery = true)
	List<Object[]> findByOnlinePlanAndParentTaskId(Long onlinePlan);

	@Query(value = "select (select name from aiga_staff where staff_id = a.assign_id) as assign,"
			+ " b.name as deal, a.assign_date, b.bill_id from na_online_task_distribute a,"
			+ " aiga_staff b where a.deal_op_id = b.staff_id and a.task_id = ?1", nativeQuery = true)
	List<Object[]> messageInfo(Long taskId);

	@Modifying
	@Query("delete from NaOnlineTaskDistribute where taskId in (?1)")
	void delete(List<Long> list);

	@Modifying
	@Query("update  NaOnlineTaskDistribute set dealState =2 where taskId = ?1")
	void updateParentTaskDealState(Long taskId);
	
	
	List<NaOnlineTaskDistribute> findByParentTaskId(Long taskId);

}
