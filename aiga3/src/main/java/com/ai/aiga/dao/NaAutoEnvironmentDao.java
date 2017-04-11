package com.ai.aiga.dao;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaAutoEnvironment;

public interface NaAutoEnvironmentDao extends JpaRepository<NaAutoEnvironment, BigDecimal>,SearchAndPageRepository<NaAutoEnvironment, BigDecimal>{

	
	NaAutoEnvironment findBySysIdAndEnvType(Long sysId,Long envType);
}
