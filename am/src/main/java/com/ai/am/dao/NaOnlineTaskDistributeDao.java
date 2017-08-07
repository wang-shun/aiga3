package com.ai.am.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.NaOnlineTaskDistribute;

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
	@Query("update  NaOnlineTaskDistribute set dealState =2  where taskId = ?1")
	void updateParentTaskDealState(Long taskId);
	
	@Modifying
	@Query("update  NaOnlineTaskDistribute set dealState =?2 where taskId = ?1")
	void updateSubTaskDealState(Long taskId,Long dealState);
	
	
	List<NaOnlineTaskDistribute> findByParentTaskId(Long taskId);
	
	@Modifying
	@Query("delete  from NaOnlineTaskDistribute  where parentTaskId = ?1")
	void deleteByParentTaskId(Long parentTaskId);

	@Query(value="select * from  na_online_task_distribute b where b.parent_task_id = (select task_id from na_online_task_distribute c where c.task_type = 2 and c.parent_task_id = 0 and c.online_plan = ?1) and b.task_type in (10,11)  ",nativeQuery = true)
	List<NaOnlineTaskDistribute>  getOtherTaskInfo(Long onlinePlan);
	
	
	@Query(value="select * from  na_online_task_distribute b where  b.parent_task_id = 0 and b.online_plan = ?1 ",nativeQuery = true)
	List<NaOnlineTaskDistribute>  findByOnlinePlannAndParentTaskId(Long onlinePlan);

	@Query(value = "select count(*) from na_change_plan_onile a, na_online_task_distribute b "
			+ " where a.online_plan = b.online_plan and a.plan_state = 4 and b.task_id = ?1", nativeQuery = true)
	Object findCanclePlan(Long parentTaskId);

	@Modifying
	@Query(value = "update na_online_task_distribute set deal_state = 3 where task_id in (select parent_task_id from na_online_task_distribute where task_id = ?1)", nativeQuery = true)
	void updateTaskDealState(Long subTaskId);
	
	
	@Modifying
	@Query(value = "update na_online_task_distribute set deal_state = ?2  where  parent_task_id =0  and online_plan = ?1 and task_type =?3 ", nativeQuery = true)
	void updateTaskDealStateByPlanId(Long planId ,Long dealState ,Long tasktype);
	
	

	@Modifying
	@Query(value = " update na_online_task_distribute p set p.deal_state = (select case when count(*) = 0 then 3 else 2 end from na_online_task_result t where t.state <> 2 and task_id in (select task_id from na_online_task_distribute b where b.parent_task_id = (select a.parent_task_id from na_online_task_distribute a where a.task_id = ?1 ))) where p.task_id = (select o.parent_task_id from na_online_task_distribute o where o.task_id = ?1)", nativeQuery = true)
	void updateSubTaskDealState(Long taskId);
	
	
	

	
	@Query(value = 	"select d.sub_task_id, d.inter_id, d.inter_code from na_online_task_distribute p,\n" +
									"               na_online_task_distribute s,\n" + 
									"              na_plan_case_result_exp_sum d\n" + 
									"        where p.task_id = s.parent_task_id\n" + 
									"          and s.task_id = d.sub_task_id\n" + 
									"          and p.parent_task_id = 0\n" + 
									"          and p.task_type = 3\n" + 
									"          and p.online_plan = (select a.online_plan from na_change_plan_onile a,\n" + 
									"              na_online_task_distribute b\n" + 
									"        where a.online_plan = b.online_plan \n" + 
									"          and b.task_id = ?1)"  , nativeQuery = true)
		List<Object> findSubTaskResult(Long subTaskId);
	
	
	
	@Query(value=" select a.sub_task_id,\n" +
				"       a.inter_id,\n" + 
				"       b.totalTime,\n" + 
				"       a.bfproddata,\n" + 
				"       a.conclusion\n" + 
				"  		from na_plan_case_result_exp a,\n" + 
				"       (select t.sub_task_id as subTaskId,\n" + 
				"               t.inter_id as interId,\n" + 
				"               sum(nvl(durtime,0)) as totalTime,\n" + 
				"               max(nvl(testtimes, 0)) as maxTimes\n" + 
				"          from na_plan_case_result_exp t\n" + 
				"         group by t.sub_task_id, t.inter_id\n" + 
				"        having t.sub_task_id in ( ?1 )) b\n" + 
				"	 where a.sub_task_id = b.subTaskId\n" + 
				"   and a.inter_id = b.interId\n" + 
				"   and a.testtimes = b.maxTimes" ,nativeQuery = true)
	List<Object> findSubResultList(List<Long> subTaskId);
	

	@Modifying
	@Query(value="update na_online_task_result t set t.state = 1, t.done_date = sysdate where t.task_id in " +
					"(select distinct sub_task_id from na_plan_case_result_exp_sum t where t.case_state <> 1 and t.sub_task_id in (?1))" ,nativeQuery=true)
   void	updateUndealSubTask(List<Long> subTaskId);
	
	@Modifying
	@Query(value="	update na_online_task_distribute t set t.deal_state = 3 where t.task_id in " +
					"(select distinct sub_task_id from na_plan_case_result_exp_sum t where t.case_state <> 1 and t.sub_task_id in (?1))" ,nativeQuery=true)
   void	updateUnfinishedSubTask(List<Long> subTaskId);
	
	@Modifying
	@Query(value="	update na_online_task_result t set t.state = 2, t.done_date = sysdate, t.finish_date = (select case when a.finish_date is null then sysdate else a.finish_date end from na_online_task_result a where a.deal_id = t.deal_id) where t.task_id not in\n" +
				"(select distinct sub_task_id from na_plan_case_result_exp_sum t where t.case_state <> 1 and t.sub_task_id in (?1)) and t.task_id in (?1)" ,nativeQuery=true)
   void	updatefinishedSubTask(List<Long> subTaskId);
	
	
	@Modifying  
	@Query(value="update na_online_task_distribute t set t.deal_state = 2 where t.task_id not in\n" +
					"(select distinct sub_task_id from na_plan_case_result_exp_sum t where t.case_state <> 1 and t.sub_task_id in (?1)) and t.task_id in (?1)" ,nativeQuery=true)
   void	updatefinishsSubTask(List<Long> subTaskId);

	@Query("from NaOnlineTaskDistribute where taskType = ?1 and onlinePlan = ?2 and parentTaskId = 0")
	List<NaOnlineTaskDistribute> findByTaskTypeAndOnlinePlan(Long taskType, Long onlinePlan);

	NaOnlineTaskDistribute findByExt1(String string);
	
	
	@Query("from NaOnlineTaskDistribute where  onlinePlan = ?1 and parentTaskId = 0 and taskType in (?2)" )
	List<NaOnlineTaskDistribute> findByOnlinePlanAndParentTaskId(Long onlinePlan ,List<Long>  taskType);

	
	@Modifying
	@Query(value = "update na_online_task_distribute p set p.deal_state = (select case when count(*) = 0 then 3 else 2 end from na_online_task_result t where t.state <> 2 and task_id in (select task_id from na_online_task_distribute b where b.parent_task_id = (select a.parent_task_id from na_online_task_distribute a where a.task_id = ?1 ))) where p.task_id = (select o.parent_task_id from na_online_task_distribute o where o.task_id = ?1)", nativeQuery = true)
	void updateParTaskDealStateBysub(Long subTaskId);
	
}
