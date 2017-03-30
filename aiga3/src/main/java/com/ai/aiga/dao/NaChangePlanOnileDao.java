package com.ai.aiga.dao;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaAutoMachine;
import com.ai.aiga.domain.NaChangePlanOnile;


public interface NaChangePlanOnileDao extends JpaRepository<NaChangePlanOnile, Long>,SearchAndPageRepository<NaChangePlanOnile, Long> 
{
	
	@Modifying
    @Query(value="update na_change_plan_onile set sign=1 where online_plan=?1",nativeQuery = true)
	void abandonChangePlanOnile(Long onlinePlan);
	@Query(value="select online_plan,online_plan_name,plan_state,types,plan_date,timely,done_date"
			+ "from na_change_plan_onile where online_plan=?1",nativeQuery = true)
	NaChangePlanOnile findById(Long onlinePlan);
	
	@Modifying
	@Query(value="update na_change_plan_onile set plan_state = 4 where online_plan = ?1",nativeQuery = true)
	void delectChangePlanOnile(Long onlinePlan);
	
	@Modifying
	@Query("update NaChangePlanOnile set planState = 2 where onlinePlan = ?1")
	void updatePlanState(Long onlinePlan);


}
