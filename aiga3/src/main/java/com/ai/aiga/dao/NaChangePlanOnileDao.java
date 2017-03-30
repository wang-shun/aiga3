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
	@Query("update NaChangePlanOnile set planState = 2 where onlinePlan = ?1")
	void updatePlanState(Long onlinePlan);

}
