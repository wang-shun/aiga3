package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.ArchitectureGrading;

public interface ArchitectureGradingDao extends JpaRepository<ArchitectureGrading, Long>,SearchAndPageRepository<ArchitectureGrading, Long> {
	
}
