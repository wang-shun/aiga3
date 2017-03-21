package com.ai.aiga.dao;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaAutoMachineEnv;


public interface NaAutoMachineEnvDao extends JpaRepository<NaAutoMachineEnv, BigDecimal>,SearchAndPageRepository<NaAutoMachineEnv, BigDecimal> {
	@Modifying
	@Query("delete from NaAutoMachineEnv where machineId= ?1")
	void deleteByMachineId(BigDecimal machineId);
	
	@Modifying
	@Query("delete from NaAutoMachineEnv where envId= ?1")
	void deleteByEnvId(BigDecimal machineId);
	
}
