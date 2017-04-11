package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaPlanCaseResultExp;
import com.ai.aiga.domain.NaPlanCaseResultExpSum;


public interface NaPlanCaseResultExtDao extends SearchAndPageRepository<NaPlanCaseResultExp, Long> {
	   
    @Modifying
    @Query(value="update na_plan_case_result_exp set remark = ?1 where result_id= ?2", nativeQuery=true)
    public void saveRemark(String remark , Long resultId);
}
