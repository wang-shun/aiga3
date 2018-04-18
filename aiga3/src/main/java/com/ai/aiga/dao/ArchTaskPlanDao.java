package com.ai.aiga.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.ArchTaskPlan;

public interface ArchTaskPlanDao extends JpaRepository<ArchTaskPlan, Long> ,SearchAndPageRepository<ArchTaskPlan, Long> {
	List<ArchTaskPlan> findByState(char state);
}
