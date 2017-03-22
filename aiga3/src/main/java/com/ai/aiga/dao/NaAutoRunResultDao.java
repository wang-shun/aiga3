package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaAutoRunResult;

import java.util.List;

public interface NaAutoRunResultDao extends JpaRepository<NaAutoRunResult, Long>, SearchAndPageRepository<NaAutoRunResult, Long>{

	@Query(value = "select run_info from na_auto_run_result where result_id = ?1 and auto_id = ?2", nativeQuery = true)
	String runInfo(Long resultId, Long autoId);
	
	@Query(value = "select run_log from na_auto_run_result where result_id = ?1 and auto_id = ?2", nativeQuery = true)
	String runLog(Long resultId, Long autoId);

	List<NaAutoRunResult> findByTaskId(Long taskId);
	List<NaAutoRunResult> findbyTaskIdAndResultTypeNot(Long taskId,Byte resultType);
}
