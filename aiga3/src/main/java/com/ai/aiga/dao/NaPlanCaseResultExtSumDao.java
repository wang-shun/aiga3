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

	@Modifying
	@Query(value = "delete from na_plan_case_result_exp_sum where sub_task_id = ?1 and result_id in (?2)", nativeQuery = true)
	void delete(Long taskId, List<Long> list);
    

    
}
