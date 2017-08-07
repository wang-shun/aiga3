package com.ai.am.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.NaAutoPropertyConfig;

public interface NaAutoPropertyConfigDao extends SearchAndPageRepository<NaAutoPropertyConfig, Long>{
	
	List<NaAutoPropertyConfig> findByPropertyId(String propertyId);
	
	@Query(value="select * from na_auto_property_config where sort_id=1",nativeQuery = true)
	List<NaAutoPropertyConfig> distinctPropertyConfigList();
	
	@Query(value="select nvl(max(sort_id),0) from na_auto_property_config where property_id=?1",nativeQuery = true)
	int getMaxSortId(String propertyId);
	
}
