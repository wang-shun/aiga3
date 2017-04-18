package com.ai.aiga.dao;

import com.ai.aiga.dao.jpa.SearchAndPageRepository;
import com.ai.aiga.domain.NaAutoDistributeMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 自动化分布式执行机器IP与任务关联表
 *
 * @author defaultekey
 * @date 2017/4/17
 */

public interface NaAutoDistributeMachineDao extends SearchAndPageRepository<NaAutoDistributeMachine,Long>{
    
    @Query(value = "delete from na_auto_distribute_machine where task_id=?1",nativeQuery = true)
    int deleteByTaskId(Long taskId);
    
    NaAutoDistributeMachine findByTaskIdAndMachineIp(Long taskId,String machineIp);
    
    List<NaAutoDistributeMachine> findByTaskIdAndStatusNot(Long taskId, Long status);
    List<NaAutoDistributeMachine> findByTaskIdAndStatus(Long taskId, Long status);
}
