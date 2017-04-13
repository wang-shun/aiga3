package com.ai.aiga.service;

import com.ai.aiga.dao.NaAutoMachineDao;
import com.ai.aiga.dao.NaAutoRunPlanCaseDao;
import com.ai.aiga.dao.NaAutoRunTaskCaseDao;
import com.ai.aiga.domain.*;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.util.mapper.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * 自动化执行任务与用例关系
 *
 * @author defaultekey
 * @date 2017/3/20
 */
@Service
@Transactional
public class AutoRunTaskCaseSv {

    @Autowired
    private NaAutoRunTaskCaseDao autoRunTaskCaseDao;

    @Autowired
    private NaAutoRunPlanCaseDao autoRunPlanCaseDao;

    @Autowired
    private AutoRunTaskSv autoRunTaskSv;
    
    @Autowired
    private NaAutoMachineDao autoMachineDao;
    
    /**
     * 根据任务ID删除关联关系
     * @param taskId
     */
    public void deleteByTaskId(Long taskId){
        if (taskId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskId");
        }
        autoRunTaskCaseDao.deleteByTaskId(taskId);
    }

    /**
     * 根据任务ID查询任务下所有用例
     * @param taskId
     * @return
     */
    public List<NaAutoRunTaskCase> getListByTaskId(Long taskId){
        if (taskId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskId");
        }
        return autoRunTaskCaseDao.findByTaskId(taskId);
    }

    /**
     * 根据计划ID查询出关联用例，然后根据任务ID保存
     * @param planId
     * @param taskId
     */
    public void saveListByPlanId(Long planId,Long taskId){
        if (planId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "planId");
        }
        if (taskId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskId");
        }
        List<NaAutoRunPlanCase> planCaseList=autoRunPlanCaseDao.findByPlanId(planId);
        if (planCaseList ==null || planCaseList.size()==0) {
            BusinessException.throwBusinessException("the plan without autoCase! please make sure the planId: "+planId);
        }
        List<Object> list=new ArrayList<Object>();
        for (NaAutoRunPlanCase planCase:planCaseList){
            NaAutoRunTaskCase taskCase= BeanMapper.map(planCase,NaAutoRunTaskCase.class);
            //将映射的主键值置为null
            taskCase.setRelaId(null);
            taskCase.setTaskId(taskId);
            list.add(taskCase);
        }
        autoRunTaskCaseDao.saveList(list);
    }

    /**
     * 根据任务ID获取用例所有的环境
     * @param taskId
     * @return
     */
    public String getEnvByTaskId(Long taskId){
        if (taskId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskId");
        }
        List<Object> envList=autoRunTaskCaseDao.findEnvByTaskId(taskId);
        if (envList == null || envList.size()==0) {
            BusinessException.throwBusinessException("getEnvByTaskId error! task case is empty! please make sure the taskId:"+taskId);
        }
        String envId = "";
        for (Object env:envList){
            envId+=env.toString()+"_";
        }
        envId=envId.substring(0,envId.length()-1);
        return envId;
    }

    /**
     * 根据任务ID获取用例能够执行的机器IP
     * @param taskId
     * @return
     */
    public List<NaAutoMachine> getMachineByTaskId(Long taskId){
        if (taskId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskId");
        }
        List<NaAutoMachine> machineList=this.autoMachineDao.findMachineIpByTaskId(taskId);
        if (machineList == null || machineList.size()==0) {
            BusinessException.throwBusinessException("could not found the executable machine!");
        }
        return machineList;
    }

    /**
     * 根据任务ID获取最多能分布式执行的机器数量
     * @param taskId
     * @return
     */
    public int getDistributeNumByTaskId(Long taskId){
        if (taskId == null) {
            BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskId");
        }
        NaAutoRunTask autoRunTask=this.autoRunTaskSv.findById(taskId);
        int distributeNum=autoRunTask.getDistributeNum().intValue();//每台机器可分布式执行数量
        //获取未分组的用例需执行机器数量(组顺序默认为：0)
        int defaultTotal=this.autoRunTaskCaseDao.findByTaskIdAndSortGroup(taskId,0L).size();
        int defautCount = defaultTotal % distributeNum != 0 ? (defaultTotal / distributeNum + 1) : (defaultTotal / distributeNum);
        //获取分组的用例需执行机器数量(同一组用例需在同一机器跑，所以每组分一个机器)
        int groupCount=this.autoRunTaskCaseDao.findGroupByTaskId(taskId).size();
        return groupCount+defautCount;
    }
}
