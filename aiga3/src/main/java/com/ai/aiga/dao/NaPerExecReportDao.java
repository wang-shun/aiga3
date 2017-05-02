package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaPerExecReport;

/**
 * @ClassName: NaExecPerReportDao
 * @author: dongch
 * @date: 2017年4月28日 下午2:25:30
 * @Description:
 * 
 */
public interface NaPerExecReportDao extends JpaRepository<NaPerExecReport, Long>, SearchAndPageRepository<NaPerExecReport, Long>{

	@Modifying
	@Query("delete from NaPerExecReport")
	void delete();

	@Query(value = "select count(t1.case_id), t5.name, t5.code from na_plan_case_result t1 join na_online_task_distribute t2 on t1.sub_task_id = t2.task_id join na_online_task_distribute t3 on t2.parent_task_id = t3.task_id join na_change_plan_onile t4 on t3.online_plan = t4.online_plan join aiga_staff t5 on t2.deal_op_id = t5.staff_id where t4.online_plan = ?1 and t1.case_type = 1 and (t1.result is null or (t1.result is not null and t1.result <> 2)) group by t5.name, t5.code", nativeQuery = true)
	List<Object> testCount(Long onlinePlan);

	@Query(value = "select count(t1.case_id), t5.name, t5.code from na_plan_case_result t1 join na_online_task_distribute t2 on t1.sub_task_id = t2.task_id join na_online_task_distribute t3 on t2.parent_task_id = t3.task_id join na_change_plan_onile t4 on t3.online_plan = t4.online_plan join aiga_staff t5 on t2.deal_op_id = t5.staff_id where t4.online_plan = ?1 and t1.case_type = ?2 and (t1.result is null or (t1.result is not null and t1.result <> 2)) group by t5.name, t5.code", nativeQuery = true)
	List<Object> autoCount(Long onlinePlan, Long Type);

	@Query(value = "select count(t1.case_id), t5.name, t5.code from na_plan_case_result t1 join na_online_task_distribute t2 on t1.sub_task_id = t2.task_id join na_online_task_distribute t3 on t2.parent_task_id = t3.task_id join na_change_plan_onile t4 on t3.online_plan = t4.online_plan join aiga_staff t5 on t2.deal_op_id = t5.staff_id where t4.online_plan = ?1 and t1.case_type = ?2 and (t1.auto_result is not null and t1.auto_result = 1) and (t1.result is null or (t1.result is not null and t1.result <> 2)) group by t5.name, t5.code", nativeQuery = true)
	List<Object> autoRunCount(Long onlinePlan, Long Type);

	@Query(value = "select t1.ext1, t5.name, t5.code from na_online_task_result t1 join na_online_task_distribute t2 on t1.task_id = t2.task_id join na_online_task_distribute t3 on t2.parent_task_id = t3.task_id join na_change_plan_onile t4 on t3.online_plan = t4.online_plan join aiga_staff t5 on t2.deal_op_id = t5.staff_id  where t4.online_plan = ?1 and (t2.task_type = 2 or t2.task_type = 0)", nativeQuery = true)
	List<Object> autoTime(Long onlinePlan);

	@Query(value = "select ceil((max(t1.done_date) - min(t1.done_date)) * 86400 * 1000), t5.name, t5.code from na_plan_case_result t1 join na_online_task_distribute t2 on t1.sub_task_id = t2.task_id join na_online_task_distribute t3 on t2.parent_task_id = t3.task_id join na_change_plan_onile t4 on t3.online_plan_id = t4.online_plan on t3.online_plan_id = t4.online_plan on t2.deal_op_id = t5.staff_id where t4.online_plan = ?1 and t2.task_type = 1 and t1.done_date is not null group by t5.name, t5.code", nativeQuery = true)
	List<Object> testTime(Long onlinePlan);

	@Query(value = "select count(t1.fault_id),t2.name,t2.code from na_online_plan_fault t1 join aiga_staff t2 on t1.founder = t2.staff_id where t1.online_plan = ?1 group by t2.name,t2.code", nativeQuery = true)
	List<Object> faultCount(Long onlinePlan);

}

