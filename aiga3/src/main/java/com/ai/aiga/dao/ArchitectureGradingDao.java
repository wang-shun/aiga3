package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.ArchitectureGrading;
import com.ai.aiga.domain.NaFileUpload;

public interface ArchitectureGradingDao extends JpaRepository<ArchitectureGrading, Long>,SearchAndPageRepository<ArchitectureGrading, Long> {
	
	ArchitectureGrading findByCloudOrderId(String cloudOrderId);
	
	List<ArchitectureGrading> findByName(String name);

}
