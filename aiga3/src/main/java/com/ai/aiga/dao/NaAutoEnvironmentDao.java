package com.ai.aiga.dao;



import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaAutoEnvironment;

public interface NaAutoEnvironmentDao extends JpaRepository<NaAutoEnvironment, Long>,SearchAndPageRepository<NaAutoEnvironment, Long>{

	
	NaAutoEnvironment findBySysIdAndEnvType(Long sysId,Long envType);
}
