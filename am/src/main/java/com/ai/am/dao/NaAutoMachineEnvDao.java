package com.ai.am.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.NaAutoEnvironment;
import com.ai.am.domain.NaAutoMachine;
import com.ai.am.domain.NaAutoMachineEnv;


public interface NaAutoMachineEnvDao extends JpaRepository<NaAutoMachineEnv, Long>,SearchAndPageRepository<NaAutoMachineEnv, Long> {
	@Modifying
	@Query("delete from NaAutoMachineEnv where machineId= ?1")
	void deleteByMachineId(Long machineId);
	
	@Modifying
	@Query("delete from NaAutoMachineEnv where envId= ?1")
	void deleteByEnvId(Long machineId);

	
	
	
	
	@Modifying
	@Query("delete from NaAutoMachineEnv where envId= ?1 and machineId=?2")
	void delrel(Long envId,Long machineId);
	
	@Modifying
	@Query("delete from NaAutoMachineEnv where machineId= ?1 and envId=?2")
	void delectrel(Long machineId,Long envId);
	
}
