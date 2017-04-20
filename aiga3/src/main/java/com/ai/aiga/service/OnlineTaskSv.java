package com.ai.aiga.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.AigaBossTestResultDao;
import com.ai.aiga.dao.AigaStaffDao;
import com.ai.aiga.dao.NaAutoCollGroupCaseDao;
import com.ai.aiga.dao.NaAutoCollectionDao;
import com.ai.aiga.dao.NaAutoGroupCaseDao;
import com.ai.aiga.dao.NaOnlineTaskDistributeDao;
import com.ai.aiga.dao.NaOnlineTaskResultDao;
import com.ai.aiga.dao.NaPlanCaseResultDao;
import com.ai.aiga.domain.AigaStaff;
import com.ai.aiga.domain.NaAutoCollGroupCase;
import com.ai.aiga.domain.NaAutoCollection;
import com.ai.aiga.domain.NaAutoGroupCase;
import com.ai.aiga.domain.NaOnlineTaskDistribute;
import com.ai.aiga.domain.NaOnlineTaskResult;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.service.enums.CheckAcceptEnum;
import com.ai.aiga.view.json.DealOpResponse;
import com.ai.aiga.view.json.OnlineTaskRequest;
import com.huawei.msp.mmap.server.TaskMessageClient;

/**
 * @ClassName: OnlineTaskSv
 * @author: dongch
 * @date: 2017年4月5日 下午6:34:48
 * @Description:
 * 
 */
@Service
@Transactional
public class OnlineTaskSv extends BaseService{
	
	@Autowired
	private NaOnlineTaskDistributeDao naOnlineTaskDistributeDao;
	
	@Autowired
	private NaAutoCollGroupCaseDao naAutoCollGroupCaseDao;
	
	@Autowired
	private NaOnlineTaskResultDao naOnlineTaskResultDao;
	
	@Autowired
	private NaAutoCollectionDao naAutoCollectionDao;
	
	@Autowired
	private AigaStaffDao aigaStaffDao;
	
	@Autowired
	private NaPlanCaseResultDao naPlanCaseResultDao;
	
	@Autowired
	private NaAutoGroupCaseDao naAutoGroupCaseDao;
	
	@Autowired
	private AigaBossTestResultDao aigaBossTestResultDao;
	
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
		
		String sql = "select a.online_plan,"
						    +" a.online_plan_name,"
						      +"  a.task_id,"
						      +"   a.task_name,"
						      +"    a.task_type,"
						      +"     a.deal_state,"
						      +"  to_char(a.assign_date,'yyyy-MM-dd HH:MM:SS') as assign_date,"
						      +"   (select name from aiga_staff where staff_id = a.assign_id) as assign_name,"
						     +"    (select name from aiga_staff where staff_id = a.deal_op_id) as deal_name"
						     +"  from na_online_task_distribute a"
						    +"  where a.parent_task_id = 0";
		
		if(condition.getTaskType() != null){
			sql += " and a.task_type = "+condition.getTaskType();
		}else{
			sql += " and a.task_type < 4 ";
		}
		if(condition.getTaskName() != null && !condition.getTaskName().equals("")){
			sql += " and a.task_name like '%"+condition.getTaskName()+"%'";
		}
		if(condition.getOnlinePlan() != null){
			sql += " and a.online_plan = "+condition.getOnlinePlan();
		}
		if(condition.getDealState() != null){
			sql += " and a.deal_state = "+condition.getDealState();
		}
		sql += " group by a.online_plan,"
				+ " a.online_plan_name,"
				+ " a.task_id,"
				+ " a.task_name,"
				+ " a.task_type,"
				+ " a.deal_state,"
				+ " assign_date,"
				+ " a.assign_id,"
				+ "  a.deal_op_id";
		
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
		//return naOnlineTaskDistributeDao.searchByNativeSQL(sql, pageable);
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
		String sql = "select a.task_id, b.task_name, b.task_type, b.deal_state, c.collect_name, d.name as opName,"
				+ " a.auto_plan_id, b.deal_op_id, to_char(b.assign_date,'YYYY-MM-DD HH24:MI:SS'), to_char(b.create_date,'YYYY-MM-DD HH24:MI:SS')"
				+ "  from  na_online_task_distribute b left join na_online_task_result a on a.task_id = b.task_id"
				+ " left join na_auto_collection c on a.auto_plan_id = c.collect_id"
				+ " left join aiga_staff d on b.deal_op_id = d.staff_id "
				+ " where b.parent_task_id = "+condition.getTaskId();
				
		if(condition.getTaskName() != null){
			sql += " and b.task_name like '%"+condition.getTaskName()+"%'";
		}
		List<String> list = new ArrayList<String>();
		list.add("taskId");
		list.add("taskName");
		list.add("taskType");
		list.add("state");
		list.add("collectName");
		list.add("dealOpName");
		list.add("collectId");
		list.add("dealOpId");
		list.add("assignDate");
		list.add("createDate");
		
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
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskIds");
		}
		String taskId[] = taskIds.split(",");
		for (String id : taskId) {
			naOnlineTaskDistributeDao.delete(Long.parseLong(id));
			naPlanCaseResultDao.deleteBySubTaskId(Long.parseLong(id));
		}

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
			subTask.setTaskType(CheckAcceptEnum.SubTaskType_two.getValue());
			subTask.setDealState(CheckAcceptEnum.TaskStatus_new.getValue());
			subTask.setAssignDate(new Date());
			
			//创建自动化用例类型子任务--自动生成
			NaOnlineTaskDistribute subTaskAuto = new NaOnlineTaskDistribute();
			subTaskAuto.setTaskName(onlineTaskRequest.getTaskName()+"_自动化");
			subTaskAuto.setParentTaskId(onlineTaskRequest.getParentTaskId());
			subTaskAuto.setDealOpId(onlineTaskRequest.getDealOpId());
			subTaskAuto.setTaskType(CheckAcceptEnum.SubTaskType_three.getValue());
			subTaskAuto.setDealState(CheckAcceptEnum.TaskStatus_new.getValue());
			subTaskAuto.setAssignDate(new Date());
			//创建用例组类型子任务--自动生成
			NaOnlineTaskDistribute subTaskGroup = new NaOnlineTaskDistribute();
			subTaskGroup.setTaskName(onlineTaskRequest.getTaskName()+"_用例组");
			subTaskGroup.setParentTaskId(onlineTaskRequest.getParentTaskId());
			subTaskGroup.setDealOpId(onlineTaskRequest.getDealOpId());
			subTaskGroup.setTaskType(CheckAcceptEnum.SubTaskType_one.getValue());
			subTaskGroup.setDealState(CheckAcceptEnum.TaskStatus_new.getValue());
			subTaskGroup.setAssignDate(new Date());
			
			NaOnlineTaskDistribute response = naOnlineTaskDistributeDao.findOne(onlineTaskRequest.getParentTaskId());
			if(response != null){
				subTask.setAssignId(response.getAssignId());
				subTask.setOnlinePlan(response.getOnlinePlan());
				
				subTaskAuto.setAssignId(response.getAssignId());
				subTaskAuto.setOnlinePlan(response.getOnlinePlan());
				
				subTaskGroup.setAssignId(response.getAssignId());
				subTaskGroup.setOnlinePlan(response.getOnlinePlan());
			}
			naOnlineTaskDistributeDao.save(subTask);
			//发短信提醒处理人
			//sendMessageForCycle(subTask.getTaskId());
			
			//创建手工用例类型子任务结果
			NaOnlineTaskResult planResult = new NaOnlineTaskResult();
			planResult.setCreateDate(new Date());
			planResult.setOpId(onlineTaskRequest.getDealOpId());
			planResult.setAutoPlanId(onlineTaskRequest.getCollectId());
			planResult.setDealType(CheckAcceptEnum.SubTaskType_two.getMomo());//手工用例
			planResult.setState(CheckAcceptEnum.ResultStatus_new.getMomo());
			planResult.setTaskId(subTask.getTaskId());
			naOnlineTaskResultDao.save(planResult);
			
			//创建自动化用例类型子任务结果
			NaOnlineTaskResult planResultAuto = new NaOnlineTaskResult();
			planResultAuto.setCreateDate(new Date());
			planResultAuto.setOpId(onlineTaskRequest.getDealOpId());
			planResultAuto.setAutoPlanId(onlineTaskRequest.getCollectId());
			planResultAuto.setDealType(CheckAcceptEnum.SubTaskType_three.getMomo());//自动化用例
			planResultAuto.setState(CheckAcceptEnum.ResultStatus_new.getMomo());
			
			//创建用例组类型子任务结果
			NaOnlineTaskResult planResultGroup = new NaOnlineTaskResult();
			planResultGroup.setCreateDate(new Date());
			planResultGroup.setOpId(onlineTaskRequest.getDealOpId());
			planResultGroup.setAutoPlanId(onlineTaskRequest.getCollectId());
			planResultGroup.setDealType(CheckAcceptEnum.SubTaskType_one.getMomo());//用例组
			planResultGroup.setState(CheckAcceptEnum.ResultStatus_new.getMomo());//未处理
			
			//将选中用例集下手工用例关联到回归子任务处理结果表
			naPlanCaseResultDao.saveCaseResult(subTask.getTaskId(), onlineTaskRequest.getCollectId(), 1L);
			//判断是否需要生成自动化子任务
			List<NaAutoCollGroupCase> list = naAutoCollGroupCaseDao.findByCollectIdAndElementType(onlineTaskRequest.getCollectId(), 2L);
			//判断是否需要生成用例组子任务
			List<NaAutoCollGroupCase> listGroup = naAutoCollGroupCaseDao.findByCollectIdAndElementType(onlineTaskRequest.getCollectId(), 0L);
			
			if(list != null && list.size() > 0){
				naOnlineTaskDistributeDao.save(subTaskAuto);
				planResultAuto.setTaskId(subTaskAuto.getTaskId());
				naOnlineTaskResultDao.save(planResultAuto);
				//发短信
				//sendMessageForCycle(subTaskAuto.getTaskId());
				
				naPlanCaseResultDao.saveCaseResult(subTaskAuto.getTaskId(), onlineTaskRequest.getCollectId(), 2L);
			}
			if(listGroup != null && listGroup.size() > 0){
				naOnlineTaskDistributeDao.save(subTaskGroup);
				planResultGroup.setTaskId(subTaskGroup.getTaskId());
				naOnlineTaskResultDao.save(planResultGroup);
				//发短信
				//sendMessageForCycle(subTaskGroup.getTaskId());

				naPlanCaseResultDao.saveCaseResult(subTaskGroup.getTaskId(), onlineTaskRequest.getCollectId());

			}
			naOnlineTaskDistributeDao.updateParentTaskDealState(onlineTaskRequest.getParentTaskId());
		}
	}

	/**
	 * @ClassName: OnlineTaskSv :: sendMessageForCycle
	 * @author: dongch
	 * @date: 2017年4月10日 下午2:49:49
	 *
	 * @Description:
	 * @param taskId          
	 */
	private void sendMessageForCycle(Long taskId) {
		if(taskId == null || taskId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskId");
		}
		Object[] obj = naPlanCaseResultDao.message(taskId);
		StringBuilder contents = new StringBuilder();
		contents.append("AIGA_SMS~尊敬的:").append(obj[0].toString()).append(",").append(obj[3].toString())
		.append("在").append(obj[3].toString()).append("给您分派了").append(obj[2].toString())
		.append("子任务,请您及时处理！");
		TaskMessageClient.sendMessageForCycle(obj[1].toString(), contents.toString());
	}

	/**
	 * @ClassName: OnlineTaskSv :: collect
	 * @author: dongch
	 * @date: 2017年4月7日 上午11:28:28
	 *用例集下拉框
	 * @Description:
	 * @return          
	 */
	public List<NaAutoCollection> collect(Long caseType) {
		
		List<NaAutoCollection> list = naAutoCollectionDao.findByCaseType(caseType);
		return list;
	}

	/**
	 * @ClassName: OnlineTaskSv :: dealOp
	 * @author: dongch
	 * @date: 2017年4月7日 上午11:42:54
	 *
	 * @Description:
	 * @return          
	 */
	public List<DealOpResponse> dealOp() {
		List<AigaStaff> list = aigaStaffDao.findAll();
		List<DealOpResponse> responses = new ArrayList<DealOpResponse>(list.size());
		if(list != null && list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				DealOpResponse response = new DealOpResponse();
				response.setDealOpId(list.get(i).getStaffId());
				response.setDealOpName(list.get(i).getName());
				responses.add(response);
			}
		}
		
		return responses;
	}


	public Object getOtherTaskInfo(Long onlinePlan) {
		if(onlinePlan==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "onlinePlan");
		}		
		return naOnlineTaskDistributeDao.getOtherTaskInfo( onlinePlan);
	}
	
}

