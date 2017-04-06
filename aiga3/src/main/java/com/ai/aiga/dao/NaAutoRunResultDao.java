package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaAutoRunResult;

import java.util.List;

public interface NaAutoRunResultDao extends JpaRepository<NaAutoRunResult, Long>, SearchAndPageRepository<NaAutoRunResult, Long>{

	List<NaAutoRunResult> findByTaskId(Long taskId);

	List<NaAutoRunResult> findByTaskIdAndResultTypeNot(Long taskId,Long resultType);

	List<NaAutoRunResult> findByTaskIdAndResultType(Long taskId,Long resultType);

	List<NaAutoRunResult> findByTaskIdAndRunType(Long taskId,Long runType);

	List<NaAutoRunResult> findByTaskIdAndRunTypeNot(Long taskId,Long runType);

	List<NaAutoRunResult> findByTaskIdAndResultTypeOrderBySortGroupAscSortNumberAsc(Long taskId,Long resultType);

	@Modifying
	@Query(value = "delete from na_auto_run_result where task_id=?1",nativeQuery = true)
	int deleteByTaskId(Long taskId);

	@Modifying
	@Query(value = "update na_auto_run_result set run_type=?2 where task_id=?1 and result_type=?3",nativeQuery = true)
	int updateRunTypeByTaskIdAndResultType(Long taskId,Long runType,Long resultType);
}
