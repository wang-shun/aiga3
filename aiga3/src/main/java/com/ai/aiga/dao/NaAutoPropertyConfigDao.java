package com.ai.aiga.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.domain.NaAutoPropertyConfig;

public interface NaAutoPropertyConfigDao extends JpaRepository<NaAutoPropertyConfig,Long>{
	
	List<NaAutoPropertyConfig> findByPropertyId(String propertyId);
	
	@Query(value="select distinct property_id,property_name from na_auto_property_config",nativeQuery = true)
	List<NaAutoPropertyConfig> distinctPropertyConfigList();
	
}
