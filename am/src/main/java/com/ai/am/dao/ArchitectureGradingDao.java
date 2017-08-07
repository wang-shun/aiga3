package com.ai.am.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.ArchitectureGrading;

public interface ArchitectureGradingDao extends JpaRepository<ArchitectureGrading, Long>,SearchAndPageRepository<ArchitectureGrading, Long> {
	
}
