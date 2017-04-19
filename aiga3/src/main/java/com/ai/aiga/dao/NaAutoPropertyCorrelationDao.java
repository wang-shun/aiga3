package com.ai.aiga.dao;

import java.util.List;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaAutoPropertyCorrelation;

public interface NaAutoPropertyCorrelationDao extends SearchAndPageRepository<NaAutoPropertyCorrelation, Long>{

	List<NaAutoPropertyCorrelation> findByPropertyId(String propertyId);
	
	
}
