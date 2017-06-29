package com.ai.aiga.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.domain.ArchitectureStaticData;

public interface ArchitectureStaticDataDao  extends JpaRepository<ArchitectureStaticData, Long> {
	
}
