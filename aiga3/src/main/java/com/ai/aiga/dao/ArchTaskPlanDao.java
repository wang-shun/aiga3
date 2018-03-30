package com.ai.aiga.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ai.aiga.domain.ArchTaskPlan;

public interface ArchTaskPlanDao extends JpaRepository<ArchTaskPlan, Long> {
	List<ArchTaskPlan> findByState(char state);
}
