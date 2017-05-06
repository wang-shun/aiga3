package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.AigaSubSysFolder;
import com.ai.aiga.domain.NaProcessNodeRecord;
import com.ai.aiga.domain.Tasks;

public interface NaProcessNodeRecordDao extends JpaRepository<NaProcessNodeRecord, Long>
,SearchAndPageRepository<NaProcessNodeRecord, Long>{

	@Modifying
	@Query(value="update na_process_node_record a  set a.type=1 ,"
			+ "a.time = to_date(to_char(sysdate,'yyyy-MM-dd '),'yyyy-MM-dd') "
			+ "where a.plan_id =?1 "
			+ "and  node=?2",nativeQuery=true)
	void  update(Long onlinePlan ,Long node);
	
	
	@Modifying
	@Query(value="update na_process_node_record a  set a.type=2 ,"
			+ "a.time = to_date(to_char(sysdate,'yyyy-MM-dd '),'yyyy-MM-dd') "
			+ "where a.plan_id =?1 "
			+ "and  node=?2",nativeQuery=true)
	void  commit(Long onlinePlan ,Long node);
	
	


	@Query(value = "select count(distinct big_system) from na_require_list where plan_id = ?1", nativeQuery = true)
	Object system(Long onlinePlan);

	@Query(value = "select count(id) from na_require_list where plan_id = ?1", nativeQuery = true)
	Object require(Long onlinePlan);

	
	NaProcessNodeRecord findByPlanIdAndNode(Long onlinePlan, Long node);

	@Query(value = "select count(distinct package_name) from na_code_path where plan_date = to_date(?1,'yyyy-mm-dd') and state <> 3", nativeQuery = true)
	Object pack(String planDate);

	@Query(value = "select count(distinct file_name) from na_file_upload where plan_id = ?1", nativeQuery = true)
	Object file(Long onlinePlan);

	@Query(value = "select count(case_id) from na_plan_case_result a,na_auto_case b where a.case_id = b.auto_id and b.environment_type = ?1 and a.sub_task_id in(select b.task_id from na_online_task_distribute a, na_online_task_distribute b where a.task_id = b.parent_task_id and a.task_type = ?2 and a.online_plan = ?3)", nativeQuery = true)
	Object auto(Long env, Long taskType, Long onlinePlan);

	@Query(value = "select count(case_id) from na_plan_case_result a,na_test_case b where a.case_id = b.test_id and a.sub_task_id in (select b.task_id from na_online_task_distribute a, na_online_task_distribute b where a.task_id = b.parent_task_id and a.task_type = ?1 and a.online_plan = ?2)", nativeQuery = true)
	Object test(Long taskType, Long onlinePlan);

	@Query(value = "select trunc((max(done_date)- min(done_date))*24,1) from na_plan_case_result where sub_task_id in (select b.task_id from na_online_task_distribute a, na_online_task_distribute b where a.task_id = b.parent_task_id and a.task_type = ?1 and a.online_plan = ?2)", nativeQuery = true)
	Object time(Long taskType,Long onlinePlan);

	@Query(value = "select count(total_time_count) from aiga_boss_test_result where online_plan = ?1", nativeQuery = true)
	Object time2(Long onlinePlan);

	@Query(value = "select count(distinct plan_id) from na_process_node_record where plan_date = to_date(?1,'yyyy-mm-dd') ",nativeQuery = true)
	long findPlan(String planDate);


	@Query(value = "select count(online_plan) from na_change_plan_onile where plan_date >= to_date(?1,'yyyy-mm-dd') and plan_date < to_date(?2,'yyyy-mm-dd')", nativeQuery = true)
	long countPlan(String date1, String date2);


	@Query(value = "select count(bug_id) from na_online_plan_bug where bug_type = ?1 and create_date >= to_date(?2,'yyyy-mm-dd') and create_date < to_date(?3,'yyyy-mm-dd')", nativeQuery = true)
	long countAbnormal(Long bugType, String date1, String date2);


	@Query(value = "select distinct plan_id from na_process_node_record where plan_date = to_date(?1,'yyyy-mm-dd') order by plan_date", nativeQuery = true)
	long[] plan(String planDate);


	
	List<NaProcessNodeRecord> findByPlanId(long l);
	

	

}
