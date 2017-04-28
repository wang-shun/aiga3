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

	@Query(value = "select (select name from aiga_staff where staff_id = a.assign_id) as assign,"
			+ " b.name as deal, to_char(a.assign_date,'YYYY-MM-DD HH24:MI:SS'), b.bill_id from na_online_task_distribute a,"
			+ " aiga_staff b where a.deal_op_id = b.staff_id and a.task_id = ?1", nativeQuery = true)
	List<Object[]> messageInfo(Long taskId);

	@Modifying
	@Query("delete from NaOnlineTaskDistribute where taskId in (?1)")
	void delete(List<Long> list);

	@Modifying
	@Query("update  NaOnlineTaskDistribute set dealState =2 where taskId = ?1")
	void updateParentTaskDealState(Long taskId);
	
	@Modifying
	@Query("update  NaOnlineTaskDistribute set dealState =?2 where taskId = ?1")
	void updateSubTaskDealState(Long taskId,Long dealState);
	
	
	List<NaOnlineTaskDistribute> findByParentTaskId(Long taskId);
	
	@Modifying
	@Query("delete  from NaOnlineTaskDistribute  where parentTaskId = ?1")
	void deleteByParentTaskId(Long parentTaskId);

	@Query(value="select * from  na_online_task_distribute b where b.task_type = 2 and b.parent_task_id = 0 and b.online_plan = ?1 order by b.assign_date desc",nativeQuery = true)
	List<NaOnlineTaskDistribute>  getOtherTaskInfo(Long onlinePlan);

	@Query(value = "select count(*) from na_change_plan_onile a, na_online_task_distribute b "
			+ " where a.online_plan = b.online_plan and a.plan_state = 4 and b.task_id = ?1", nativeQuery = true)
	Object findCanclePlan(Long parentTaskId);

	@Modifying
	@Query(value = "update na_online_task_distribute set deal_state = 3 where task_id in (select parent_task_id from na_online_task_distribute where task_id = ?1)", nativeQuery = true)
	void updateTaskDealState(Long subTaskId);
}
