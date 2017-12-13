package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.ArchWorkPlan;

public interface ArchWorkPlanDao extends JpaRepository<ArchWorkPlan, Long>, SearchAndPageRepository<ArchWorkPlan, Long> {

}
