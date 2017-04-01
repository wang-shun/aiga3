package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.domain.NaAutoPropertyCorrelation;

public interface NaAutoPropertyCorrelationDao extends JpaRepository<NaAutoPropertyCorrelation,Long>{

	List<NaAutoPropertyCorrelation> findByPropertyId(String propertyId);
	
}
