package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.OnlineSituationReport;

public interface OnlineSituationReportDao  extends JpaRepository<OnlineSituationReport, Long>, SearchAndPageRepository<OnlineSituationReport, Long> {
	
	@Modifying
	@Query(value = "select count(a.ONLINE_PLANS) from online_plan_bug a join sys_constant b on a.bug_type=b.value and b.category ='bugType' and a.Online_plans=?1 and b.show='异常'",nativeQuery = true)
	Integer countBug(Long onlinPlan);
	
	@Modifying
	@Query(value = "select count(a.ONLINE_PLANS) from online_plan_bug a join sys_constant b on a.bug_type=b.value and b.category ='bugType' and a.Online_plans=?1 and b.show='故障'", nativeQuery = true)
	Integer countFault(Long onlinPlan);
	 
	@Modifying
	@Query(value = "select count(t.fault_id) from online_plan_fault t where t.online_plan = ?1",nativeQuery = true)
	Integer countQuexian(Long onlinPlan);
	
	@Modifying
	@Query(value = "select count(t.fault_id) from online_plan_fault t where t.online_plan = ?1 and effect_fault='1'",nativeQuery = true)
	Integer faultValidCount(Long onlinPlan);
	
	@Modifying
	@Query(value = "select count(t.fault_id) from online_plan_fault t where t.online_plan = ?1 and effect_fault='2'",nativeQuery = true)
	Integer faultInvalidCount(Long onlinPlan);
	
	@Modifying
	@Query(value = "select count(fault_id)+count(bug_num) from online_plan_fault a join sys_constant  b on a.resove=b.value and b.category ='isbug' and a.Online_plan=?1 and b.show='否'",nativeQuery = true)
	Integer remainNum(Long onlinPlan);
	
	@Modifying
	@Query(value = "select task_id from AIGA_ONLINE_TASK_DISTRIBUTE a where  a.task_type= ?1 and parent_task_id=0 and online_plan_id=?2",nativeQuery = true)
	Integer funCheckDurationListS(int type,Long onlinPlan);
}
