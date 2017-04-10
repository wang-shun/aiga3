package com.ai.aiga.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaAutoEnvironment;
import com.ai.aiga.domain.NaAutoMachine;
import com.ai.aiga.domain.NaAutoMachineEnv;


public interface NaAutoMachineEnvDao extends JpaRepository<NaAutoMachineEnv, BigDecimal>,SearchAndPageRepository<NaAutoMachineEnv, BigDecimal> {
	@Modifying
	@Query("delete from NaAutoMachineEnv where machineId= ?1")
	void deleteByMachineId(BigDecimal machineId);
	
	@Modifying
	@Query("delete from NaAutoMachineEnv where envId= ?1")
	void deleteByEnvId(BigDecimal machineId);

	@Query("select a.machineId,a.machineIp,a.machineName,a.status from NaAutoMachine a,NaAutoMachineEnv b "
			+ "where b.machineId=a.machineId and b.envId=?1")
	List<NaAutoMachine> selectall(BigDecimal envId);
	
	@Query("select a.envId,c.sysName,a.envName,a.envUrl from NaAutoEnvironment a,NaAutoMachineEnv b,AigaSystemFolder c "
			+ "where b.envId=a.envId and c.sysId=a.sysId and b.machineId=?1")
	List<NaAutoEnvironment> select(BigDecimal machineId);
	
	@Modifying
	@Query("delete from NaAutoMachineEnv where envId= ?1 and machineId=?2")
	void delrel(BigDecimal envId,BigDecimal machineId);
	
	@Modifying
	@Query("delete from NaAutoMachineEnv where machineId= ?1 and envId=?2")
	void delectrel(BigDecimal machineId,BigDecimal envId);
	
}
