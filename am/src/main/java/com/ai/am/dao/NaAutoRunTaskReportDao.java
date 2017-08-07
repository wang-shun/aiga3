package com.ai.am.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ai.am.domain.NaAutoRunTaskReport;

public interface NaAutoRunTaskReportDao extends JpaRepository<NaAutoRunTaskReport, Long>{
	
	@Query(value = " select t.task_id,t.total_case,t.none_run_case,t.has_run_case,t.success_case,t.fail_case,"
			+ " a.creator_id,a.begin_run_time,a.end_run_time,a.spend_time from"
			+ " (select task_id,count(auto_id) as total_case,"
			+ " count(case when(result_type = 2)  then result_type end) as none_run_case,"
			+ " count(case when(result_type != 2) then result_type end) as has_run_case,"
			+ " count(case when(result_type = 0)  then result_type end) as success_case,"
			+ " count(case when(result_type = 1)  then result_type end) as fail_case"
			+ " from  na_auto_run_result  group by task_id having task_id =?1) t , na_auto_run_task a"
			+ " where a.task_id = t.task_id ", nativeQuery = true)
	List<Object[]> findResultByTaskId(Long taskId);

	NaAutoRunTaskReport findByTaskId(Long taskId);

}
