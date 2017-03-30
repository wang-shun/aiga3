package com.ai.aiga.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.AigaStaffDao;
import com.ai.aiga.dao.NaChangePlanOnileDao;
import com.ai.aiga.dao.NaCodePathDao;
import com.ai.aiga.dao.NaOnlineTaskDistributeDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.domain.AigaStaff;
import com.ai.aiga.domain.NaChangePlanOnile;
import com.ai.aiga.domain.NaCodePath;
import com.ai.aiga.domain.NaOnlineTaskDistribute;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.json.NaOnlineTaskDistributeResponse;
import com.huawei.msp.mmap.server.TaskMessageClient;

@Service
@Transactional
public class ChangePlanRunSv extends BaseService{

	@Autowired
	private NaOnlineTaskDistributeDao naOnlineTaskDistributeDao;
	
	@Autowired
	private NaChangePlanOnileDao naChangePlanOnileDao;
	
	@Autowired
	private NaCodePathDao naCodePathDao;
	
	@Autowired
	private AigaStaffDao aigaStaffDao;

	public Object list(NaChangePlanOnile condition, String time1, String time2, int pageNumber, int pageSize) {
		
		String sql = "select a.online_plan, a.online_plan_name, a.plan_state, b.name as creator_name, a.create_date, a.types,"
				+ " a.done_date, a.plan_date, a.timely, a.result, a.remark, is_finished, a.auto_run_result from "
				+ "na_change_plan_onile a, aiga_staff b where a.create_op_id = b.staff_id and sign = 0 ";
		
		if(condition != null){
			if(condition.getOnlinePlan() != null){
				sql += " and a.online_plan = "+condition.getOnlinePlan();
			}
			if(condition.getPlanState() != null ){
				sql += " and a.plan_state = "+condition.getPlanState();
			}
			if(StringUtils.isNoneBlank(time1)){
				sql += " and a.create_date > to_date('"+time1+"','YYYY-MM-DD HH24:MI:SS')";
			}
			if(StringUtils.isNotBlank(time2)){
				sql += " and a.create_date < to_date('"+time2+"','YYYY-MM-DD HH24:MI:SS')";
			}
		}
		List<String> list = new ArrayList<String>();
		list.add("onlinePlan");
		list.add("onlinePlanName");
		list.add("planState");
		list.add("creatorName");
		list.add("creatorDate");
		list.add("types");
		list.add("doneDate");
		list.add("planDate");
		list.add("timely");
		list.add("result");
		list.add("remark");
		list.add("isFinished");
		list.add("autoRunResult");
		
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}
		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return naChangePlanOnileDao.searchByNativeSQL(sql, pageable, list);
	}

	public void changStart(Long onlinePlan) {
		if(onlinePlan == null || onlinePlan < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "onlinePlan");
		}
		
		//启动时把计划状态改为处理中
		naChangePlanOnileDao.updatePlanState(onlinePlan);
	}

	public void save(NaOnlineTaskDistribute naOnlineTaskDistribute) {
		if(naOnlineTaskDistribute == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(naOnlineTaskDistribute.getOnlinePlan() == null || naOnlineTaskDistribute.getOnlinePlan() < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "onlinePlan");
		}
		if(naOnlineTaskDistribute.getTaskType() == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskType");
		}
		if(StringUtils.isBlank(naOnlineTaskDistribute.getOnlinePlanName())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "onlinePlanName");
		}
		String info = "";
		if(naOnlineTaskDistribute.getTaskType() == 1){
			info = "前台功能验收";
		}else if(naOnlineTaskDistribute.getTaskType() == 2){
			info = "后台功能验收";
		}else{
			info = "非功能验收";
		}
		if(naOnlineTaskDistribute.getTaskId() == null || naOnlineTaskDistribute.getTaskId().equals("")){
			naOnlineTaskDistribute.setParentTaskId(0L);
			naOnlineTaskDistribute.setTaskName(naOnlineTaskDistribute.getOnlinePlanName()+"_"+info);
			//naOnlineTaskDistribute.setAssignId();;
			naOnlineTaskDistribute.setAssignDate(new Date());
			naOnlineTaskDistribute.setDealState(1L);
			naOnlineTaskDistributeDao.save(naOnlineTaskDistribute);
//			if(naOnlineTaskDistribute.getDealOpId() != null){
//				sendMessageForCycle(naOnlineTaskDistribute.getTaskId(), info);
//			}
		}else{
			NaOnlineTaskDistribute distribute = naOnlineTaskDistributeDao.findOne(naOnlineTaskDistribute.getTaskId());
			distribute.setTaskName(naOnlineTaskDistribute.getOnlinePlanName()+"_"+info);
			distribute.setTaskType(naOnlineTaskDistribute.getTaskType());
			if(naOnlineTaskDistribute.getDealOpId() != null){
				distribute.setDealOpId(naOnlineTaskDistribute.getDealOpId());
			}
			//distribute.setAssignId(assignId);
			distribute.setAssignDate(new Date());
			naOnlineTaskDistributeDao.save(distribute);
//			if(distribute.getDealOpId() != null){
//				sendMessageForCycle(distribute.getTaskId(), info);
//			}
		}
		//把计划状态改为处理中
		naChangePlanOnileDao.updatePlanState(naOnlineTaskDistribute.getOnlinePlan());
	}

	public void sendMessageForCycle(Long taskId, String info) {
		
		List<Object[]> list = naOnlineTaskDistributeDao.messageInfo(taskId);
		Object[] object = list.get(0);
		StringBuilder contents = new StringBuilder();
		contents.append("尊敬的:").append(object[0].toString()).append(",").append(object[1].toString())
		.append("在").append(object[2].toString()).append("给您分派了").append(info)
		.append("任务,请您及时处理！");
		TaskMessageClient.sendMessageForCycle(object[3].toString(), contents.toString());
		//TaskMessageClient.sendMessageForCycle("13567177436", "666");
	}

	public List<NaOnlineTaskDistributeResponse> taskList(Long onlinePlan) {
		if(onlinePlan == null || onlinePlan < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "onlinePlan");
		}
		List<Object[]> list = naOnlineTaskDistributeDao.findByOnlinePlan(onlinePlan);
		List<NaOnlineTaskDistributeResponse> responses = new ArrayList<NaOnlineTaskDistributeResponse>(list.size());
		if(list != null && list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				NaOnlineTaskDistributeResponse distribute = new NaOnlineTaskDistributeResponse();
				Object[] object = list.get(i);
				distribute.setTaskId(((BigDecimal) object[0]).longValue());
				distribute.setTaskName(object[1].toString());
				distribute.setTaskType(((BigDecimal) object[2]).longValue());
				distribute.setDealState(((BigDecimal) object[3]).longValue());
				distribute.setDealName(object[4].toString());
				responses.add(distribute);
			}
		}
		return responses;
	}

	public void delete(Long taskId) {
		
		if(taskId == null || taskId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskId");
		}
		naOnlineTaskDistributeDao.delete(taskId);
	}

	public Page<NaCodePath> compileList(NaCodePath condition, int pageNumber, int pageSize) {
		
		List<Condition> cons = new ArrayList<Condition>();
		
		cons.add(new Condition("planDate", condition.getPlanDate(), Condition.Type.EQ));
		
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return naCodePathDao.search(cons, pageable);
	}

	public List<AigaStaff> createOpId() {
		List<AigaStaff> list = aigaStaffDao.findAll();
		return list;
	}
}
