package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.WorkPlan;

public interface WorkPlanDao extends JpaRepository<WorkPlan, Long>, SearchAndPageRepository<WorkPlan, Long> {

}
