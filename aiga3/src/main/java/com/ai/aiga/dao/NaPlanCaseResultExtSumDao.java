package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaPlanCaseResultExpSum;


public interface NaPlanCaseResultExtSumDao extends SearchAndPageRepository<NaPlanCaseResultExpSum, Long> {
	
	
    List<NaPlanCaseResultExpSum>   findBySubTaskId(Long subTaskId);
    
    @Modifying
    @Query(value="update NA_PLAN_CASE_RESULT_EXP_SUM set operat_Id = ?1 where result_id= ?2 ", nativeQuery=true)
    public void saveOperatId(Long operatId , Long resultId);
    

    
}
