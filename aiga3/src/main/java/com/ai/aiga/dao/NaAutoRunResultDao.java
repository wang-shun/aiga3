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

	@Modifying
	@Query(value = "delete from na_auto_run_result where task_id=?1",nativeQuery = true)
	int deleteByTaskId(Long taskId);
}
