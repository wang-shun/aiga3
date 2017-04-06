package com.ai.aiga.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.NaAutoCollGroupCaseDao;
import com.ai.aiga.dao.NaOnlineTaskDistributeDao;
import com.ai.aiga.dao.NaOnlineTaskResultDao;
import com.ai.aiga.domain.NaAutoCollGroupCase;
import com.ai.aiga.domain.NaOnlineTaskDistribute;
import com.ai.aiga.domain.NaOnlineTaskResult;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.view.json.OnlineTaskRequest;

/**
 * @ClassName: OnlineTaskSv
 * @author: dongch
 * @date: 2017年4月5日 下午6:34:48
 * @Description:
 * 
 */
@Service
@Transactional
public class OnlineTaskSv {
	
	@Autowired
	private NaOnlineTaskDistributeDao naOnlineTaskDistributeDao;
	
	@Autowired
	private NaAutoCollGroupCaseDao naAutoCollGroupCaseDao;
	
	@Autowired
	private NaOnlineTaskResultDao naOnlineTaskResultDao;
	
	/**
	 * @ClassName: OnlineTaskSv :: list
	 * @author: dongch
	 * @date: 2017年4月5日 下午6:49:24
	 *
	 * @Description:
	 * @param condition
	 * @param pageNumber
	 * @param pageSize
	 * @return          
	 */
	public Object list(NaOnlineTaskDistribute condition, int pageNumber, int pageSize) {
		
		String sql = "select a.online_plan, a.online_plan_name, a.task_id, a.task_name, a.task_type, a.deal_state,"
				+ " a.assign_date, b.name as assign_name, (select name from aiga_staff where staff_id = a.deal_op_id) as deal_name"
				+ " from na_online_task_distribute a, aiga_staff b where a.assign_id = b.staff_id ";
		if(condition.getTaskName() != null){
			sql += " and a.task_name like '%"+condition.getTaskName()+"%'";
		}
		if(condition.getOnlinePlan() != null){
			sql += " and a.online_plan = "+condition.getOnlinePlan();
		}
		if(condition.getDealState() != null){
			sql += " and a.deal_state = "+condition.getDealState();
		}
		
		List<String> list = new ArrayList<String>();
		list.add("onlinePlan");
		list.add("onlinePlanName");
		list.add("taskId");
		list.add("taskName");
		list.add("taskType");
		list.add("dealState");
		list.add("assignDate");
		list.add("assignName");
		list.add("dealName");
		
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}
		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return naOnlineTaskDistributeDao.searchByNativeSQL(sql, pageable, list);
	}

	/**
	 * @ClassName: OnlineTaskSv :: childList
	 * @author: dongch
	 * @date: 2017年4月5日 下午7:20:07
	 *
	 * @Description:
	 * @param condition
	 * @param pageNumber
	 * @param pageSize
	 * @return          
	 */
	public Object childList(NaOnlineTaskDistribute condition, int pageNumber, int pageSize) {
		if(condition.getTaskId() == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskId");
		}
		String sql = "select a.task_id, b.task_name, b.task_type, a.state, c.collect_name, d.op_name,"
				+ " b.assign_date from na_online_task_result a, na_online_task_distribute b, na_auto_collection c,"
				+ " aiga_staff d where a.task_id = b.task_id and a.auto_plan_id = c.collect_id and a.op_id = d.staff_id"
				+ " and b.parent_task_id = "+condition.getTaskId();
		if(condition.getTaskName() != null){
			sql += " and b.task_name like '%"+condition.getTaskName()+"%'";
		}
		List<String> list = new ArrayList<String>();
		list.add("taskId");
		list.add("taskName");
		list.add("taskType");
		list.add("state");
		list.add("collectName");
		list.add("opName");
		list.add("assignDate");
		
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}
		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return naOnlineTaskDistributeDao.searchByNativeSQL(sql, pageable, list);
	}

	/**
	 * @ClassName: OnlineTaskSv :: delete
	 * @author: dongch
	 * @date: 2017年4月5日 下午7:47:57
	 *
	 * @Description:
	 * @param taskIds          
	 */
	public void delete(String taskIds) {
		
		if(taskIds == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		String [] taskId = taskIds.split(",");
		List<Long> list = new ArrayList<Long>();
		for(int i = 0; i < taskId.length; i++){
			list.add(Long.valueOf(taskId[i]).longValue());
		}
		naOnlineTaskDistributeDao.delete(list);
	}

	/**
	 * @ClassName: OnlineTaskSv :: save
	 * @author: dongch
	 * @date: 2017年4月6日 下午12:36:45
	 *
	 * @Description:
	 * @param onlineTaskRequest          
	 */
	public void save(OnlineTaskRequest onlineTaskRequest) {
		if(onlineTaskRequest == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(onlineTaskRequest.getTaskId() != null){
			//修改入网验收任务名称及处理人
			NaOnlineTaskDistribute distribute = naOnlineTaskDistributeDao.findOne(onlineTaskRequest.getTaskId());
			if(onlineTaskRequest.getTaskName() != null){
				distribute.setTaskName(onlineTaskRequest.getTaskName());
			}
			if(onlineTaskRequest.getDealOpId() != null){
				distribute.setDealOpId(onlineTaskRequest.getDealOpId());
			}
			naOnlineTaskDistributeDao.save(distribute);
		}else{
			if(onlineTaskRequest.getTaskName() == null){
				BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskName");
			}
			if(onlineTaskRequest.getCollectId() == null){
				BusinessException.throwBusinessException(ErrorCode.Parameter_null, "collectId");
			}
			if(onlineTaskRequest.getDealOpId() == null){
				BusinessException.throwBusinessException(ErrorCode.Parameter_null, "dealOpId");
			}
			if(onlineTaskRequest.getParentTaskId() == null){
				BusinessException.throwBusinessException(ErrorCode.Parameter_null, "parentTaskId");
			}
			//创建手工用例类型子任务
			NaOnlineTaskDistribute subTask = new NaOnlineTaskDistribute();
			subTask.setTaskName(onlineTaskRequest.getTaskName());
			subTask.setParentTaskId(onlineTaskRequest.getParentTaskId());
			subTask.setDealOpId(onlineTaskRequest.getDealOpId());
			subTask.setTaskType(1L);
			subTask.setDealState(1L);
			subTask.setAssignDate(new Date());
			
			//创建自动化用例类型子任务--自动生成
			NaOnlineTaskDistribute subTaskAuto = new NaOnlineTaskDistribute();
			subTaskAuto.setTaskName(onlineTaskRequest.getTaskName()+"_自动化");
			subTaskAuto.setParentTaskId(onlineTaskRequest.getParentTaskId());
			subTaskAuto.setDealOpId(onlineTaskRequest.getDealOpId());
			subTaskAuto.setTaskType(2L);
			subTaskAuto.setDealState(1L);
			subTaskAuto.setAssignDate(new Date());
			
			NaOnlineTaskDistribute response = naOnlineTaskDistributeDao.findOne(onlineTaskRequest.getParentTaskId());
			if(response != null){
				subTask.setAssignId(response.getAssignId());
				subTask.setOnlinePlan(response.getOnlinePlan());
				
				subTaskAuto.setAssignId(response.getAssignId());
				subTaskAuto.setOnlinePlan(response.getOnlinePlan());
			}
			naOnlineTaskDistributeDao.save(subTask);
			
			//创建手工用例类型子任务结果
			NaOnlineTaskResult planResult = new NaOnlineTaskResult();
			planResult.setCreateDate(new Date());
			planResult.setOpId(onlineTaskRequest.getDealOpId());
			planResult.setAutoPlanId(onlineTaskRequest.getCollectId());
			planResult.setDealType((byte) 2);
			planResult.setState((byte) 0);
			planResult.setTaskId(subTask.getTaskId());
			naOnlineTaskResultDao.save(planResult);
			
			//创建自动化用例类型子任务结果
			NaOnlineTaskResult planResultAuto = new NaOnlineTaskResult();
			planResultAuto.setCreateDate(new Date());
			planResultAuto.setOpId(onlineTaskRequest.getDealOpId());
			planResultAuto.setAutoPlanId(onlineTaskRequest.getCollectId());
			planResultAuto.setDealType((byte) 1);
			planResultAuto.setState((byte) 0);
			
			//判断是否需要生成自动化子任务
			List<NaAutoCollGroupCase> list = naAutoCollGroupCaseDao.findByCollectIdAndElementType(onlineTaskRequest.getCollectId(), 2L);
			if(list != null){
				naOnlineTaskDistributeDao.save(subTaskAuto);
				planResultAuto.setTaskId(subTaskAuto.getTaskId());
				naOnlineTaskResultDao.save(planResultAuto);
			}
		}
	}

}

