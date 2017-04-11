package com.ai.aiga.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.NaInterfaceListDao;
import com.ai.aiga.dao.NaOnlineTaskDistributeDao;
import com.ai.aiga.dao.NaPlanCaseResultExtSumDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.domain.NaInterfaceList;
import com.ai.aiga.domain.NaOnlineTaskDistribute;
import com.ai.aiga.domain.NaPlanCaseResultExpSum;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ctc.wstx.util.StringUtil;

import antlr.StringUtils;

/**
 * @ClassName: PerformanceTaskSv
 * @author: dongch
 * @date: 2017年4月11日 上午10:41:28
 * @Description:
 * 性能测试子任务分派
 */
@Service
@Transactional
public class PerformanceTaskSv extends BaseService{

	@Autowired
	private NaOnlineTaskDistributeDao naOnlineTaskDistributeDao;
	
	@Autowired
	private NaInterfaceListDao naInterfaceListDao;
	
	@Autowired
	private NaPlanCaseResultExtSumDao naPlanCaseResultExtSumDao; 
	
	/**
	 * @ClassName: PerformanceTaskSv :: save
	 * @author: dongch
	 * @date: 2017年4月11日 上午10:44:24
	 *
	 * @Description:性能子任务新增或修改
	 * @param naOnlineTaskDistribute          
	 */
	public void save(NaOnlineTaskDistribute naOnlineTaskDistribute) {
		if(naOnlineTaskDistribute == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(naOnlineTaskDistribute.getParentTaskId() == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "ParentTaskId");
		}
		if(naOnlineTaskDistribute.getTaskType() == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "TaskType");
		}
		if(naOnlineTaskDistribute.getTaskName() == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "TaskName");
		}
		if(naOnlineTaskDistribute.getTaskId() == null){
			NaOnlineTaskDistribute parentTask = naOnlineTaskDistributeDao.findOne(naOnlineTaskDistribute.getParentTaskId());
			naOnlineTaskDistribute.setAssignId(parentTask.getDealOpId());
			naOnlineTaskDistribute.setOnlinePlan(parentTask.getOnlinePlan());
			naOnlineTaskDistribute.setDealState(0L);
			naOnlineTaskDistribute.setCreateDate(new Date());
			naOnlineTaskDistributeDao.save(naOnlineTaskDistribute);
		}else{
			NaOnlineTaskDistribute distribute = naOnlineTaskDistributeDao.findOne(naOnlineTaskDistribute.getTaskId());
			if(naOnlineTaskDistribute.getTaskName() != null && !naOnlineTaskDistribute.getTaskName().equals("")){
				distribute.setTaskName(naOnlineTaskDistribute.getTaskName());
			}
			if(naOnlineTaskDistribute.getTaskType() != null){
				distribute.setTaskType(naOnlineTaskDistribute.getTaskType());
			}
			naOnlineTaskDistributeDao.save(distribute);
		}
	}

	/**
	 * @ClassName: PerformanceTaskSv :: interList
	 * @author: dongch
	 * @date: 2017年4月11日 下午2:43:16
	 *
	 * @Description:计划接口列表
	 * @param condition
	 * @param pageNumber
	 * @param pageSize
	 * @return          
	 */
	public Page<NaInterfaceList> interList(NaInterfaceList condition, int pageNumber, int pageSize) {
		
		List<Condition> cons = new ArrayList<Condition>();
		
		if(condition.getServiceId() != null){
			cons.add(new Condition("serviceId", condition.getServiceId(), Condition.Type.EQ));
		}
		
		if(condition.getServiceName() != null && !condition.getServiceName().equals("")){
			cons.add(new Condition("serviceName", "%".concat(condition.getServiceName()).concat("%"), Condition.Type.LIKE));
		}
		
		if(condition.getState() != null){
			cons.add(new Condition("state", condition.getState(), Condition.Type.EQ));
		}
		
		if(condition.getRequireMan() != null && !condition.getRequireMan().equals("")){
			cons.add(new Condition("requireMan", "%".concat(condition.getRequireMan()).concat("%"), Condition.Type.LIKE));
		}
		
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return naInterfaceListDao.search(cons, pageable);
	}

	/**
	 * @ClassName: PerformanceTaskSv :: taskRequireReal
	 * @author: dongch
	 * @date: 2017年4月11日 下午3:23:14
	 *
	 * @Description:
	 * @param request
	 * @param list          
	 */
	public void taskRequireReal(NaOnlineTaskDistribute request, List<NaInterfaceList> list) {
		
		if(request.getTaskId() == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "TaskId");
		}
		
		if(request.getTaskType() == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "TaskType");
		}
		if(list != null && list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				NaInterfaceList naInterfaceList = list.get(i);
				NaPlanCaseResultExpSum exp = new NaPlanCaseResultExpSum();
				exp.setSubTaskId(request.getTaskId());
				exp.setCaseType(request.getTaskType());
				exp.setInterId(naInterfaceList.getId());
				exp.setInterCode(naInterfaceList.getServiceId());
				exp.setCaseState(0L);
				naPlanCaseResultExtSumDao.save(exp);
				naInterfaceListDao.updateState(naInterfaceList.getId());
			}
		}
	}

	/**
	 * @ClassName: PerformanceTaskSv :: taskRequireDel
	 * @author: dongch
	 * @date: 2017年4月11日 下午4:10:31
	 *
	 * @Description:删除关联关系
	 * @param taskId
	 * @param ids          
	 */
	public void taskRequireDel(Long taskId, String ids) {
		
		if(taskId == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskId");
		}
		
		if(ids == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "ids");
		}
		
		List<Long> list = new ArrayList<Long>();
		String [] id = ids.split(",");
		for(int i = 0; i < id.length; i++){
			list.add(Long.valueOf(id[i]).longValue());
		}
		naPlanCaseResultExtSumDao.delete(taskId, list);
		naInterfaceListDao.stateUpdate(list);
	}

	/**
	 * @ClassName: PerformanceTaskSv :: taskRequireList
	 * @author: dongch
	 * @date: 2017年4月11日 下午5:15:53
	 *
	 * @Description:子任务下关联接口展示
	 * @param taskId
	 * @return          
	 */
	public Object taskRequireList(Long taskId, int pageNumber, int pageSize) {
		
		if(taskId == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskId");
		}
		
		String sql = "select a.id, a.service_id, a.service_name, a.require_code, a.change_type,"
				+ " a.state, a.dev_man, a.require_man from na_interface_list a, na_plan_case_result_exp_sum b"
				+ " where a.id = b.inter_id and b.sub_task_id = "+taskId;
		
		List<String> list = new ArrayList<String>();
		list.add("id");
		list.add("serviceId");
		list.add("serviceName");
		list.add("requireCode");
		list.add("changeType");
		list.add("state");
		list.add("devMan");
		list.add("requireMan");
		
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return naInterfaceListDao.searchByNativeSQL(sql, pageable, list);
	}

	

}

