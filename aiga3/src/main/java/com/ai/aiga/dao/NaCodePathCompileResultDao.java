package com.ai.aiga.dao;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.AigaAuthor;
import com.ai.aiga.domain.NaCodePathCompileResult;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NaCodePathCompileResultDao extends SearchAndPageRepository<NaCodePathCompileResult, Long> {

	@Query(value="select * from na_code_path_compile_result where to_char(plan_date ,'yyyy-MM-dd') like ?1 ", nativeQuery=true)
   List<NaCodePathCompileResult> findByPlanDate(String planDate);
	
	@Query(value="select * from na_code_path_compile_result where to_char(plan_date ,'yyyy-MM-dd') like ?1   and value is not null  and (status  like 'running'  or status  is null )  ", nativeQuery=true)
	   List<NaCodePathCompileResult> findByPlanDateAndStatus(String planDate);
}
