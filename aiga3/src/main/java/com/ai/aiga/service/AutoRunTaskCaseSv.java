package com.ai.aiga.service;

import com.ai.aiga.dao.NaAutoRunPlanCaseDao;
import com.ai.aiga.dao.NaAutoRunTaskCaseDao;
import com.ai.aiga.domain.NaAutoRunPlanCase;
import com.ai.aiga.domain.NaAutoRunTaskCase;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.util.mapper.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    @PersistenceContext
    private EntityManager entityManager;

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
        for (NaAutoRunPlanCase planCase:planCaseList){
            NaAutoRunTaskCase taskCase= BeanMapper.map(planCase,NaAutoRunTaskCase.class);
            //将映射的主键值置为null
            taskCase.setRelaId(null);
            taskCase.setTaskId(taskId);
            entityManager.persist(taskCase);
        }
        entityManager.flush();
        entityManager.clear();
    }
}
