package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.domain.NaAutoPropertyConfig;

public interface NaAutoPropertyConfigDao extends JpaRepository<NaAutoPropertyConfig,Long>{
	
	List<NaAutoPropertyConfig> findByPropertyId(String propertyId);
	
}
