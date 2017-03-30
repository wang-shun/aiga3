package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.domain.NaOnlineTaskDistribute;

public interface NaOnlineTaskDistributeDao extends JpaRepository<NaOnlineTaskDistribute, Long>{
	
	@Query(value = "select a.task_id, a.task_name, a.task_type, a.deal_state, b.name as creator_name from na_online_task_distribute a,"
			+ " aiga_staff b where a.deal_op_id = b.staff_id and a.online_plan =?1", nativeQuery = true)
	List<Object[]> findByOnlinePlan(Long onlinePlan);

	@Query(value = "select (select name from aiga_staff where staff_id = a.deal_op_id) as deal,"
			+ " b.name as assign, a.assign_date, b.bill_id from na_online_task_distribute a,"
			+ " aiga_staff b where a.assign_id = b.staff_id and a.task_id = ?1", nativeQuery = true)
	List<Object[]> messageInfo(Long taskId);

}
