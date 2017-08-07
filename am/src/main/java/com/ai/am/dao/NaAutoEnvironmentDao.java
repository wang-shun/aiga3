package com.ai.am.dao;



import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.NaAutoEnvironment;

public interface NaAutoEnvironmentDao extends JpaRepository<NaAutoEnvironment, Long>,SearchAndPageRepository<NaAutoEnvironment, Long>{

	
	NaAutoEnvironment findBySysIdAndEnvType(Long sysId,Long envType);
}
