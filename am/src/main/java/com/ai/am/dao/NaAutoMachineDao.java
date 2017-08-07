package com.ai.am.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ai.am.dao.jpa.SearchAndPageRepository;
import com.ai.am.domain.NaAutoMachine;


public interface NaAutoMachineDao extends JpaRepository<NaAutoMachine, Long>,
SearchAndPageRepository<NaAutoMachine, Long> 
{

    @Query(value = "select a from NaAutoMachine a,NaAutoMachineEnv b,NaAutoRunTaskCase c " +
            " where a.machineId=b.machineId and b.envId=c.environmentType and a.status=2 and c.taskId=?1")
    List<NaAutoMachine> findMachineIpByTaskId(Long taskId);
    
    NaAutoMachine findByMachineIp(String machineIp);
    
    List<NaAutoMachine> findByStatusNot(Long status);

    List<NaAutoMachine> findByStatus(Long status);

}
