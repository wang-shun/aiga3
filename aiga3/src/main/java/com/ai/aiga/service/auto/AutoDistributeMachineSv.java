package com.ai.aiga.service.auto;

import com.ai.aiga.dao.NaAutoDistributeMachineDao;
import com.ai.aiga.domain.NaAutoDistributeMachine;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.enums.AutoRunEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 自动化分布式执行机器IP与任务关联表
 *
 * @author defaultekey
 * @date 2017/4/17
 */
@Service
@Transactional
public class AutoDistributeMachineSv {
    
    @Autowired
    private NaAutoDistributeMachineDao autoDistributeMachineDao;

    /**
     * 保存操作(唯一入口)
     * @param machine 保存对象
     * @return NaAutoDistributeMachine
     */
    public NaAutoDistributeMachine save(NaAutoDistributeMachine machine) {
        if (machine == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "machine");
        }
        if (machine.getTaskId() == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskId");
        }
        if (StringUtils.isBlank(machine.getMachineIp())) {
                  BusinessException.throwBusinessException(ErrorCode.Parameter_null, "machineIp");
        }
        if (machine.getStatus() == null) {
            machine.setStatus(AutoRunEnum.MachineStatus_on.getValue());//默认占用中
        }
        return autoDistributeMachineDao.save(machine);
    }

    /**
     * 根据任务ID删除机器关联关系
     * @param taskId 任务ID
     */
    public void deleteByTaskId(Long taskId){
        if (taskId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskId");
        }
        autoDistributeMachineDao.deleteByTaskId(taskId);
    }

    /**
     * 根据任务ID和机器IP
     * @param taskId 任务ID
     * @param machineIp 机器IP
     * @return NaAutoDistributeMachine
     */
    public NaAutoDistributeMachine findByTaskIdMachineIp(Long taskId,String machineIp){
        if (taskId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskId");
        }
        if (StringUtils.isBlank(machineIp)) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "machineIp");
        }
        NaAutoDistributeMachine machine=autoDistributeMachineDao.findByTaskIdAndMachineIp(taskId,machineIp);
        if (machine == null) {
            BusinessException.throwBusinessException(String.format("findByTaskIdMachineIp could not found the distribute machine!taskId: %d machineIp %s",taskId,machineIp));
        }
        return machine;
    }

    /**
     * 根据任务ID和机器IP更新机器状态
     * @param taskId 任务ID
     * @param machineIp 机器IP
     * @param status 状态
     * @return NaAutoDistributeMachine
     */
    public NaAutoDistributeMachine updateMachineStatus(Long taskId,String machineIp,Long status){
        NaAutoDistributeMachine autoDistributeMachine=this.findByTaskIdMachineIp(taskId,machineIp);
        autoDistributeMachine.setStatus(status);
        return this.save(autoDistributeMachine);
    }

    /**
     * 根据任务ID和机器IP保存关系
     * @param taskId 任务ID
     * @param machineIp 机器IP
     * @return NaAutoDistributeMachine
     */
    public NaAutoDistributeMachine saveByTaskIdMachineIp(Long taskId,String machineIp){
        NaAutoDistributeMachine distributeMachine=new NaAutoDistributeMachine();
        distributeMachine.setTaskId(taskId);
        distributeMachine.setMachineIp(machineIp);
        distributeMachine.setStatus(AutoRunEnum.MachineStatus_on.getValue());
        return this.save(distributeMachine);
    }
    
    public boolean checkMachineFree(Long taskId){
        if (taskId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskId");
        }
        List<NaAutoDistributeMachine> machineList=autoDistributeMachineDao.findByTaskIdAndStatus(taskId,AutoRunEnum.MachineStatus_on.getValue());
        return machineList.size()==0;
    }
    
}
