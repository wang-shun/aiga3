package com.ai.am.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.NaAutoRunPlan;



public interface NaAutoRunPlanDao extends SearchAndPageRepository<NaAutoRunPlan, Long>{

	@Modifying
	@Query(value="delete from na_auto_run_plan_case where Plan_id=?1",nativeQuery=true)
	public void deleteNaAutoPunPlanCaseByPlanId(Long planId);
  
	NaAutoRunPlan findByPlanId(Long planId);
	
}
