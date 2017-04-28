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
import com.ai.aiga.dao.NaAutoCaseResultFlowDao;
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
import com.ai.aiga.service.enums.CheckAcceptEnum;
import com.ai.aiga.view.json.NaOnlineTaskDistributeResponse;
import com.ai.aiga.view.util.SessionMgrUtil;
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
	
	@Autowired
	private NaAutoCaseResultFlowDao naAutoCaseResultFlowDao;

	public Object list(NaChangePlanOnile condition, String time1, String time2, int pageNumber, int pageSize) {
		
		String sql = "select a.online_plan, a.online_plan_name, a.plan_state, b.name as creator_name, to_char(a.create_date,'YYYY-MM-DD HH24:MI:SS'), a.types,"
				+ " to_char(a.done_date,'YYYY-MM-DD HH24:MI:SS'), to_char(a.plan_date,'YYYY-MM-DD HH24:MI:SS'), a.timely, a.result, a.remark, is_finished, a.auto_run_result, a.file_upload_last_time from "
				+ "  na_change_plan_onile a left join aiga_staff b  on a.create_op_id = b.staff_id  where  a.sign = 0 ";
		
		if(condition != null){
			if(condition.getOnlinePlan() != null){
				sql += " and a.online_plan = "+condition.getOnlinePlan();
			}
			if(condition.getPlanState() != null ){
				sql += " and a.plan_state = "+condition.getPlanState();
			}
			if(condition.getTypes() != null){
				sql += " and a.types = "+condition.getTypes();
			}
			if(StringUtils.isNoneBlank(time1)){
				sql += " and a.plan_date > to_date('"+time1+"','YYYY-MM-DD HH24:MI:SS')";
			}
			if(StringUtils.isNotBlank(time2)){
				sql += " and a.plan_date < to_date('"+time2+"','YYYY-MM-DD HH24:MI:SS')";
			}
			sql += "  order by a.plan_date desc ";
		}
		List<String> list = new ArrayList<String>();
		list.add("onlinePlan");
		list.add("onlinePlanName");
		list.add("planState");
		list.add("createName");
		list.add("createDate");
		list.add("types");
		list.add("doneDate");
		list.add("planDate");
		list.add("timely");
		list.add("result");
		list.add("remark");
		list.add("isFinished");
		list.add("autoRunResult");
		list.add("fileUploadLastTime");
		
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
		}else if(naOnlineTaskDistribute.getTaskType() == 3){
			info = "非功能验收";
		}else if(naOnlineTaskDistribute.getTaskType() == 4){
			info = "生产回归";
		}else if(naOnlineTaskDistribute.getTaskType() == 9){
			info = "发布任务分派";
		}else{
			info = "部署监控";
		}
		if(naOnlineTaskDistribute.getTaskId() == null || naOnlineTaskDistribute.getTaskId().equals("")){
			naOnlineTaskDistribute.setParentTaskId(0L);
			naOnlineTaskDistribute.setTaskName(naOnlineTaskDistribute.getOnlinePlanName()+"_"+info);
			naOnlineTaskDistribute.setAssignId(SessionMgrUtil.getStaff().getStaffId());
			naOnlineTaskDistribute.setAssignDate(new Date());
			naOnlineTaskDistribute.setDealState(CheckAcceptEnum.TaskStatus_new.getValue());//未分派
			naOnlineTaskDistribute.setCreateDate(new Date());
			naOnlineTaskDistributeDao.save(naOnlineTaskDistribute);
			if(naOnlineTaskDistribute.getDealOpId() != null){
				sendMessageForCycle(naOnlineTaskDistribute.getTaskId(), info);
			}
		}else{
			NaOnlineTaskDistribute distribute = naOnlineTaskDistributeDao.findOne(naOnlineTaskDistribute.getTaskId());
			distribute.setTaskName(naOnlineTaskDistribute.getOnlinePlanName()+"_"+info);
			distribute.setTaskType(naOnlineTaskDistribute.getTaskType());
			if(naOnlineTaskDistribute.getDealOpId() != null){
				distribute.setDealOpId(naOnlineTaskDistribute.getDealOpId());
			}
			distribute.setAssignId(1L);
			distribute.setAssignDate(new Date());
			naOnlineTaskDistributeDao.save(distribute);
			if(distribute.getDealOpId() != null){
				sendMessageForCycle(distribute.getTaskId(), info);
			}
		}
		//把计划状态改为处理中
		naChangePlanOnileDao.updatePlanState(naOnlineTaskDistribute.getOnlinePlan());
	}

	public void sendMessageForCycle(Long taskId, String info) {
		
		List<Object[]> list = naOnlineTaskDistributeDao.messageInfo(taskId);
		Object[] object = list.get(0);
		StringBuilder contents = new StringBuilder();
		contents.append("AIGA_SMS~尊敬的:").append(object[1].toString()).append(",").append(object[0].toString())
		.append("在").append(object[2].toString()).append("给您分派了").append(info)
		.append("任务,请您及时处理！");
		TaskMessageClient.sendMessageForCycle(object[3].toString(), contents.toString());
		//TaskMessageClient.sendMessageForCycle("13567177436", "666");
	}

	public Object taskList(Long onlinePlan, String type, int pageNumber, int pageSize) {
		if(onlinePlan == null || onlinePlan < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "onlinePlan");
		}
		String sql = "select a.task_id, a.task_name, a.task_type, a.deal_state, a.deal_op_id, b.name as creator_name from "
				+ "na_online_task_distribute a left join aiga_staff b on a.deal_op_id = b.staff_id "
				 +"  where a.online_plan = "+onlinePlan
				 + " and a.parent_task_id= 0 ";
		if(type != null && type.equals("1")){
			sql += " and a.task_type > 3";
		}else{
			sql += " and a.task_type < 4";
		}
		
		List<String> list = new ArrayList<String>();
		list.add("taskId");
		list.add("taskName");
		list.add("taskType");
		list.add("dealState");
		list.add("dealOpId");
		list.add("dealOpName");
		
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return naOnlineTaskDistributeDao.searchByNativeSQL(sql, pageable, list);
	}

	public void delete(String taskIds) {
		
		if(taskIds == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskIds");
		}
		String[] taskId = taskIds.split(",");
		for(int i = 0; i < taskId.length; i++){
			naOnlineTaskDistributeDao.delete(Long.valueOf(taskId[i]).longValue());
			naOnlineTaskDistributeDao.deleteByParentTaskId(Long.valueOf(taskId[i]).longValue());
		}
		
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

	public Object caseResult(Long onlinePlan, int pageNumber, int pageSize ) {
		if(onlinePlan == null || onlinePlan < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "onlinePlan");
		}
		
		String sql = "select a.case_id, b.auto_name, a.resulr, c.sys_name, d.sys_name as sub_sys_name, e.sys_name as fun_name "
				+ " from na_auto_case_result_flow a "
				+ " left join na_auto_case b on a.case_id = b.auto_id "
				+ " left join aiga_system_folder c on b.sys_id = c.sys_id"
				+ " left join  aiga_sub_sys_folder d on b.sys_sub_id = d.subsys_id"
				+ " left join aiga_fun_folder e on b.fun_id = e.fun_id"
				+ " and a.plan_id = "+onlinePlan;
		
		List<String> list = new ArrayList<String>();
		list.add("caseId");
		list.add("caseName");
		list.add("resulr");
		list.add("sysName");
		list.add("subSysName");
		list.add("funName");
		
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return naAutoCaseResultFlowDao.searchByNativeSQL(sql, pageable, list);
	}

	/**
	 * @ClassName: ChangePlanRunSv :: changePlan
	 * @author: dongch
	 * @date: 2017年4月21日 下午1:40:31
	 *变更计划下拉框
	 * @Description:
	 * @return          
	 */
	public List<NaChangePlanOnile> changePlan() {
		List<NaChangePlanOnile> list = naChangePlanOnileDao.findBySign();
		return list;
	}
}
