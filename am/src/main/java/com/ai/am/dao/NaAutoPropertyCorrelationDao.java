package com.ai.am.dao;

import java.util.List;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.NaAutoPropertyCorrelation;

public interface NaAutoPropertyCorrelationDao extends SearchAndPageRepository<NaAutoPropertyCorrelation, Long>{

	List<NaAutoPropertyCorrelation> findByPropertyId(String propertyId);
	
	
}
