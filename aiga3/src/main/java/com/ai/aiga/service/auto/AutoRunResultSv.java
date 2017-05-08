package com.ai.aiga.service.auto;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.ai.aiga.dao.*;
import com.ai.aiga.domain.*;
import com.ai.aiga.service.enums.AutoRunEnum;
import com.ai.aiga.service.enums.GeneralEnum;
import com.ai.aiga.util.DateUtil;
import com.ai.aiga.util.mapper.BeanMapper;
import com.ai.aiga.util.mapper.JsonUtil;
import com.ai.aiga.view.controller.auto.dto.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;

import javax.persistence.EntityManager;

@Service
@Transactional
public class AutoRunResultSv {
	
	@Autowired
	private NaAutoRunResultDao naAutoRunResultDao;
	
	@Autowired
	private NaAutoRunTaskReportDao naAutoRunTaskReportDao;
	
	@Autowired
	private NaAutoTaskReportDetailDao naAutoTaskReportDetailDao;
	
	@Autowired
	private NaAutoRunTaskDao naAutoRunTaskDao;

	@Autowired
	private AutoRunTaskCaseSv autoRunTaskCaseSv;

	@Autowired
	private AutoCaseSv autoCaseSv;
	
	@Autowired
	private EntityManager entityManager;

	@Autowired
	private AutoRunTaskSv autoRunTaskSv;

	@Autowired
	private AutoMachineSv autoMachineSv;
	
	@Autowired
	private AutoDistributeMachineSv autoDistributeMachineSv;
	
	public NaAutoRunResult save(NaAutoRunResult naAutoRunResult) {
		
		if(naAutoRunResult == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(StringUtils.isBlank(naAutoRunResult.getTaskId().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskId");
		}
		if(StringUtils.isBlank(naAutoRunResult.getAutoId().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "autoId");
		}
		if(StringUtils.isBlank(naAutoRunResult.getEnvironmentType().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "environmentType");
		}
		return naAutoRunResultDao.save(naAutoRunResult);
	}

	public Object caseByTaskList(AutoRunResultRequest condition, int pageNumber, int pageSize) {
		if(condition == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(condition.getTaskId() == null || condition.getTaskId() < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "resultId");
		}
		String sql = "select a.result_id, a.auto_id, b.auto_name, a.sort_number, a.sort_group, a.result_type,"
				+ " a.fail_reason from na_auto_run_result a, na_auto_case b where a.auto_id = b.auto_id "
				+ "and a.task_id = "+condition.getTaskId();
		if(StringUtils.isNoneBlank(condition.getAutoName())){
			sql += " and auto_name like '%"+condition.getAutoName()+"%'";
		}
		if(condition.getResultType() != null && !condition.getResultType().equals("")){
			sql += " and a.result_type = "+condition.getResultType() ;
		}
		List<String> list = new ArrayList<String>();
		list.add("resultId");
		list.add("autoId");
		list.add("autoName");
		list.add("sortNumber");
		list.add("sortGroup");
		list.add("resultType");
		list.add("failReason");
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}
		Pageable pageable = new PageRequest(pageNumber, pageSize);
	    return naAutoRunResultDao.searchByNativeSQL(sql, pageable, list);
		
	}

	public Map<String, String> runInfo(Long resultId) {
		if(resultId == null || resultId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "resultId");

		}
		NaAutoRunResult result = naAutoRunResultDao.findOne(resultId);
		Map<String, String> map = new HashMap<String, String>();
		if(result != null){
			String runInfo = result.getRunInfo();
			map.put("runInfo", runInfo);	
		}
		return map;
		
	}

	public Object list(TaskRunResultRequest condition, int pageNumber, int pageSize) {
		String sql = "select distinct a.task_id, b.task_tag, b.task_name, b.task_result, b.machine_ip, c.machine_name, "
				+ "d.name as creator_name ,t.auto_group, t.total_case, t.none_run_case, t.has_run_case,"
				+ " t.success_case, t.fail_case, b.begin_run_time, b.end_run_time "
				+ " from na_auto_run_result a, na_auto_run_task b, na_auto_machine c, aiga_staff d,"
				+ " (select task_id,count(group_id) as auto_group,count(auto_id) as total_case,"
				+ " count(case when(result_type = 2) then result_type end) as none_run_case,"
				+ " count(case when(result_type != 2) then result_type end) as has_run_case,"
				+ " count(case when(result_type = 0) then result_type end) as success_case,"
				+ " count(case when(result_type = 1) then result_type end) as fail_case"
				+ " from na_auto_run_result group by task_id) t"
				+ " where a.task_id = b.task_id and b.machine_ip = c.machine_ip and b.creator_id = d.staff_id and a.task_id = t.task_id";
		if(condition != null){
			if(condition.getTaskName() != null && !condition.getTaskName().equals("")){
				sql += " and b.task_name like '%"+condition.getTaskName()+"%'";
			}
			if(condition.getTaskTag() != null && !condition.getTaskTag().equals("")){
				sql += " and b.task_tag like '%"+condition.getTaskTag()+"%'";
			}
			if(condition.getTaskResult() != null && !condition.getTaskResult().equals("")){
				sql += " and b.task_result ="+condition.getTaskResult();
			}
			if(condition.getMachineIp() != null && !condition.getMachineIp().equals("")){
				sql += " and b.machine_ip = '"+condition.getMachineIp()+"'";
			}
			if(condition.getTaskId() != null && !condition.getTaskId().equals("")){
				sql +=" and a.task_id = "+condition.getTaskId();
			}
			
		}
		List<String> list = new ArrayList<String>();
		list.add("taskId");
		list.add("taskTag");
		list.add("taskName");
		list.add("taskResult");
		list.add("machineIp");
		list.add("machineName");
		list.add("creatorName");
		list.add("autoGroup");
		list.add("totalCase");
		list.add("noneRunCase");
		list.add("hasRunCase");
		list.add("successCase");
		list.add("failCase");
		list.add("beginTime");
		list.add("endTime");
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}
		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return naAutoRunResultDao.searchByNativeSQL(sql, pageable, list);
	}

	public NaAutoRunTaskReportResponse findOne(Long taskId) {
		if(taskId == null || taskId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskId");
		}
		List<Object[]> list= naAutoRunTaskReportDao.findResultByTaskId(taskId);
		Object[] object = (Object[]) list.get(0);
		NaAutoRunTaskReportResponse response = new NaAutoRunTaskReportResponse();
		NaAutoRunTaskReport report = naAutoRunTaskReportDao.findByTaskId(taskId);
		if(report != null && !report.equals("")){
			response.setReportId(report.getReportId());
		}else{
			response.setReportId(Long.valueOf(0));
		}
		if(object != null && object.length > 0){
			response.setTaskId(((BigDecimal)object[0]).longValue());
			response.setTotalCase(((BigDecimal)object[1]).longValue());
			response.setNoneRunCase(((BigDecimal)object[2]).longValue());
			response.setHasRunCase(((BigDecimal)object[3]).longValue());
			response.setSuccessCase(((BigDecimal)object[4]).longValue());
			response.setFailCase(((BigDecimal)object[5]).longValue());
			response.setCreatorId(((BigDecimal)object[6]).longValue());
			//response.setBeginRunTime((Date) object[6]);
			//response.setEndRunTime((Date) object[7]);
			//response.setSpendTime(((BigDecimal) object[5]).longValue());
		}
		
		return response;
	}

	public void reportSave(Long taskId) {
		if(taskId == null || taskId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskId");
		}
		List<Object[]> list= naAutoRunTaskReportDao.findResultByTaskId(taskId);
		Object[] object = (Object[]) list.get(0);
		NaAutoRunTaskReport report = naAutoRunTaskReportDao.findByTaskId(taskId);
		if(report != null){
			report.setBeginTime((Date) object[7]);
			report.setEndTime((Date) object[8]);
			report.setSpendTime(((BigDecimal) object[9]).longValue());
			report.setTotalCase(((BigDecimal) object[1]).longValue());
			report.setNoneRunCase(((BigDecimal) object[2]).longValue());
			report.setHasRunCase(((BigDecimal) object[3]).longValue());
			report.setSuccessCase(((BigDecimal) object[4]).longValue());
			report.setFailCase(((BigDecimal) object[5]).longValue());
			DecimalFormat  df  = new DecimalFormat("######0.00");   
			report.setSuccessRate(df.format(((BigDecimal)object[4]).doubleValue()*100/((BigDecimal)object[1]).doubleValue())+"%");
			report.setUpdateTime(new Date());
			naAutoRunTaskReportDao.save(report);
		}else{
			NaAutoRunTaskReport naAutoRunTaskReport = new NaAutoRunTaskReport();
			naAutoRunTaskReport.setTaskId(taskId);
			naAutoRunTaskReport.setCreatorId(((BigDecimal) object[6]).longValue());
			NaAutoRunTask task = naAutoRunTaskDao.findOne(taskId);
			naAutoRunTaskReport.setReportName(task.getTaskName()+"报告");
			naAutoRunTaskReport.setBeginTime((Date) object[7]);
			naAutoRunTaskReport.setEndTime((Date) object[8]);
			naAutoRunTaskReport.setSpendTime(((BigDecimal) object[9]).longValue());
			naAutoRunTaskReport.setTotalCase(((BigDecimal) object[1]).longValue());
			naAutoRunTaskReport.setNoneRunCase(((BigDecimal) object[2]).longValue());
			naAutoRunTaskReport.setHasRunCase(((BigDecimal) object[3]).longValue());
			naAutoRunTaskReport.setSuccessCase(((BigDecimal) object[4]).longValue());
			naAutoRunTaskReport.setFailCase(((BigDecimal) object[5]).longValue());
			DecimalFormat  df  = new DecimalFormat("#.00");   
			naAutoRunTaskReport.setSuccessRate(df.format(((BigDecimal)object[4]).doubleValue()*100/((BigDecimal)object[1]).doubleValue())+"%");
			naAutoRunTaskReport.setUpdateTime(new Date());
			naAutoRunTaskReportDao.save(naAutoRunTaskReport);
		}
		
	}

	public Object taskDetail(Long taskId, int pageNumber, int pageSize) {
		if(taskId == null || taskId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskId");
		}
		List<NaAutoTaskReportDetail> arraylist = naAutoTaskReportDetailDao.findByTaskId(taskId);
		if(arraylist != null && arraylist.size() > 0){
			Object object = reportDetailList(taskId, pageNumber,pageSize);
			return object;
		}else{
			String sql = "select b.report_id, a.task_id, e.task_name, a.auto_id, a.result_id, c.auto_name, c.creator_id, d.name as creator_name,"
					+ " (case when (a.result_type = 0) then 'Y' "
					+ " when (a.result_type = 1) then 'N' end) as is_success"
					+ " from na_auto_run_result a, na_auto_run_task_report b, na_auto_case c, aiga_staff d, na_auto_run_task e"
					+ " where a.task_id = b.task_id and a.auto_id = c.auto_id and c.creator_id = d.staff_id and a.task_id = e.task_id"
					+ " and a.task_id = "+taskId;
			List<String> list = new ArrayList<String>();
			list.add("reportId");
			list.add("taskId");
			list.add("taskName");
			list.add("autoId");
			list.add("resultId");
			list.add("autoName");
			list.add("creatorId");
			list.add("creatorName");
			list.add("isSuccess");
			if(pageNumber < 0){
				pageNumber = 0;
			}
			
			if(pageSize <= 0){
				pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
			}
			Pageable pageable = new PageRequest(pageNumber, pageSize);
			return naAutoTaskReportDetailDao.searchByNativeSQL(sql, pageable, list);
		}
		
	}

	public void reportDetailSave(List<NaAutoTaskReportDetailRequest> list) {
		if(list == null){
			BusinessException.throwBusinessException("报告明细对象集合为空！");
		}
		for(int i = 0; i < list.size(); i++){
			NaAutoTaskReportDetailRequest request = list.get(i);
			if(request != null){
				if(request.getDetailId() == null || request.getDetailId().equals("")){
					NaAutoTaskReportDetail naAutoTaskReportDetail = new NaAutoTaskReportDetail();
					naAutoTaskReportDetail.setAutoId(request.getAutoId());
					naAutoTaskReportDetail.setReportId(request.getReportId());
					naAutoTaskReportDetail.setResultId(request.getResultId());
					if(request.getBugStaff() != null){
						naAutoTaskReportDetail.setBugStaff(request.getBugStaff());
					}
					naAutoTaskReportDetail.setCreatorId(request.getCreatorId());
					naAutoTaskReportDetail.setIsSuccess(request.getIsSuccess());
					if(request.getFailReason() != null){
						naAutoTaskReportDetail.setFailReason(request.getFailReason());
					}
					if(request.getIsBug() != null){
						naAutoTaskReportDetail.setIsBug(request.getIsBug());
					}
					naAutoTaskReportDetail.setTaskId(request.getTaskId());
					naAutoTaskReportDetail.setUpdateTime(new Date());
					naAutoTaskReportDetailDao.save(naAutoTaskReportDetail);
				}else{
					NaAutoTaskReportDetail naAutoTaskReportDetail = naAutoTaskReportDetailDao.findOne(request.getDetailId());
					naAutoTaskReportDetail.setAutoId(request.getAutoId());
					naAutoTaskReportDetail.setReportId(request.getReportId());
					naAutoTaskReportDetail.setResultId(request.getResultId());
					if(request.getBugStaff() != null){
						naAutoTaskReportDetail.setBugStaff(request.getBugStaff());
					}
					naAutoTaskReportDetail.setCreatorId(request.getCreatorId());
					naAutoTaskReportDetail.setIsSuccess(request.getIsSuccess());
					if(request.getFailReason() != null){
						naAutoTaskReportDetail.setFailReason(request.getFailReason());
					}
					if(request.getIsBug() != null){
						naAutoTaskReportDetail.setIsBug(request.getIsBug());
					}
					naAutoTaskReportDetail.setTaskId(request.getTaskId());
					naAutoTaskReportDetail.setUpdateTime(new Date());
					naAutoTaskReportDetailDao.save(naAutoTaskReportDetail);
				}
				
			}
		}
		
	}

	public Map<String , String>  runLog(Long resultId) {
		if(resultId == null || resultId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "resultId");

		}
		Map<String, String> map = new HashMap<String, String>();
		NaAutoRunResult result = naAutoRunResultDao.findOne(resultId);
		if(result != null){
			String runLog = result.getRunLog();
			map.put("runLog", runLog);
		}
		return map;
	}

	/**
	 * 根据任务ID查询结果信息
	 * @param taskId 任务ID
	 * @return NaAutoRunResult集合
	 */
	public List<NaAutoRunResult> getListByTaskId(Long taskId){
		if (taskId == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskId");
		}
		List<NaAutoRunResult> resultList=this.naAutoRunResultDao.findByTaskId(taskId);
		if (resultList == null || resultList.size()==0) {
			BusinessException.throwBusinessException("could not found the task result! please make sure the taskId:"+taskId);
		}
		return resultList;
	}

	/**
	 * 根据任务ID和结果类型查询结果信息 (结果类型按 not 查询)
	 * @param taskId 任务ID
	 * @return NaAutoRunResult集合
	 */
	public List<NaAutoRunResult> getListByTaskIdResultTypeNot(Long taskId,Long resultType){
		if (taskId == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskId");
		}
		if (resultType==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "resultType");
		}
		List<NaAutoRunResult> resultList=this.naAutoRunResultDao.findByTaskIdAndResultTypeNot(taskId,resultType);
		if (resultList == null || resultList.size()==0) {
			BusinessException.throwBusinessException("could not found the task result! please make sure the taskId:"+taskId +"and resultType not :"+resultType);
		}
		return resultList;
	}

	/**
	 *根据任务ID和结果类型查询结果信息
	 * @param taskId 任务ID
	 * @param resultType 结果类型
	 * @return NaAutoRunResult集合
	 */
	public List<NaAutoRunResult> getListByTaskIdResultType(Long taskId,Long resultType){
		if (taskId == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskId");
		}
		if (resultType==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "resultType");
		}
		List<NaAutoRunResult> resultList=this.naAutoRunResultDao.findByTaskIdAndResultType(taskId,resultType);
		if (resultList == null || resultList.size()==0) {
			BusinessException.throwBusinessException("could not found the task result! please make sure the taskId:"+taskId +"and resultType:"+resultType);
		}
		return resultList;
	}

	/**
	 * 根据任务ID和执行状态查询结果信息
	 * @param taskId 任务ID
	 * @param runType 执行状态
	 * @return NaAutoRunResult集合
	 */
	public List<NaAutoRunResult> getListByTaskIdRunType(Long taskId,Long runType){
		if (taskId == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskId");
		}
		if (runType==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "runType");
		}
		return this.naAutoRunResultDao.findByTaskIdAndRunType(taskId,runType);
	}

	/**
	 * 根据任务ID预生成执行结果
	 * @param taskId 任务ID
	 */
	public void createResultByTaskId(Long taskId){
		if (taskId == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskId");
		}
		List<NaAutoRunTaskCase> caseList=autoRunTaskCaseSv.getListByTaskId(taskId);
		if (caseList == null || caseList.size()==0) {
			BusinessException.throwBusinessException("could not found the taskCase! please sure......");
		}
		for (NaAutoRunTaskCase taskCase:caseList) {
			NaAutoRunResult result= BeanMapper.map(taskCase,NaAutoRunResult.class);
			result.setResultType(AutoRunEnum.ResultType_none.getValue());//默认未执行
			result.setRunType(AutoRunEnum.RunStatus_none.getValue());//默认未执行
			entityManager.persist(result);
		}
		entityManager.flush();
		entityManager.clear();
	}

	/**
	 * 根据任务ID初始化结果表数据（任务下所有数据）
	 * @param taskId 任务ID
	 */
	public void initResultAll(Long taskId){
		List<NaAutoRunResult> resultList=this.getListByTaskId(taskId);
		this.initResult(resultList);
	}

	/**
	 * 根据任务ID初始化结果表数据（初始化条件：结果状态为未成功）
	 * @param taskId 任务ID
	 */
	public void initResultByFail(Long taskId){
		List<NaAutoRunResult> resultList=this.getListByTaskIdResultTypeNot(taskId,AutoRunEnum.ResultType_success.getValue());
		this.initResult(resultList);
	}

	

	/**
	 * 根据任务ID初始化结果表数据（初始化条件：执行状态为执行中）
	 * @param taskId 任务ID
	 */
	public void initResultByExec(Long taskId){
		List<NaAutoRunResult> resultList=this.getListByTaskIdRunType(taskId,AutoRunEnum.RunStatus_running.getValue());
		this.initResult(resultList);
	}

	/**
	 * * 根据任务ID删除
	 * @param taskId 任务ID
	 * @return 删除结果状态
	 */
	public int deleteByTaskId(Long taskId){
		if (taskId == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskId");
		}
		return naAutoRunResultDao.deleteByTaskId(taskId);
	}
	
	public Object reportDetailList(Long taskId, int pageNumber, int pageSize) {
		if(taskId == null || taskId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskId");
		}
		String sql = "select a.detail_id, a.report_id, a.task_id, a.auto_id, a.result_id, a.is_success, a.is_bug, a.fail_reason,"
				+ " a.bug_staff, a.creator_id, b.auto_name, c.task_name, d.name as creator_name from"
				+ " na_auto_task_report_detail a, na_auto_case b, na_auto_run_task c, aiga_staff d"
				+ " where a.auto_id = b.auto_id and a.task_id = c.task_id and a.creator_id = d.staff_id and a.task_id = "+taskId+ " and a.task_id = "+taskId;
		
		List<String> list = new ArrayList<String>();
		list.add("detailId");
		list.add("reportId");
		list.add("taskId");
		list.add("autoId");
		list.add("resultId");
		list.add("isSuccess");
		list.add("isBug");
		list.add("failReason");
		list.add("bugStaff");
		list.add("creatorId");
		list.add("autoName");
		list.add("taskName");
		list.add("creatorName");
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}
		Pageable pageable = new PageRequest(pageNumber, pageSize);

		return naAutoTaskReportDetailDao.searchByNativeSQL(sql, pageable, list);
	}


	/**
	 * 根据任务ID返回任务关联用例的JSON数据
	 * @param taskId 任务ID
	 * @return JSON串
	 */
	public String getResultByTaskIdToJson(Long taskId,String machineIp){
		NaAutoRunTask autoRunTask=this.autoRunTaskSv.findById(taskId);
		String result;
		//分布式获取
		if(autoRunTask.getRunType().equals(AutoRunEnum.RunType_distributed.getValue())){
			result=this.getResultDistributeToJson(taskId,machineIp);
		}else{
			result=this.getResultImmediatelyToJson(taskId);
		}
		return result;
	}

	/**
	 * 获取云桌面返回的结果日志，并根据日志、任务、用例信息执行后续步骤
	 * @param resultJson 云桌面返回信息
	 * @throws Exception JSON转换异常信息
	 */
	public void fetchResultLog(String resultJson)throws Exception{
		List<AutoReportRequest> reportList=JsonUtil.jsonToList(JsonUtil.jsonToArray(resultJson)[0], AutoReportRequest.class);
		if (reportList == null || reportList.size()==0) {
			BusinessException.throwBusinessException("saveAutoRunResult report is null !");
		}
		AutoReportRequest report=reportList.get(0);
		//解析日志，保存用例执行结果
		NaAutoRunResult result=this.parseReportSaveResult(report);
		final Long taskId=result.getTaskId();
		String machineIp=report.getMachineIp();
		//是否最后一个用例
		boolean isFinalNum = report.getFinalNum().equals(AutoRunEnum.FinalNum_yes.getValue());
		NaAutoRunTask autoRunTask = this.autoRunTaskSv.findById(taskId);
		//是否分布式
		boolean isDistribute = autoRunTask.getRunType().equals(AutoRunEnum.RunType_distributed.getValue());
		List<NaAutoRunResult> resultList=naAutoRunResultDao.findByTaskIdAndRunTypeNot(taskId,AutoRunEnum.RunStatus_complete.getValue());
		//是否全部执行完成
		boolean isComplete = resultList != null && resultList.size() == 0;
		//是否轮循
		boolean isCycle=autoRunTask.getCycleType().equals(AutoRunEnum.CycleType_cycle.getValue());
		// 用例全部执行完成
		if (isComplete) {
			//是否停止任务
			boolean isStop=autoRunTask.getStopFlag().equals(GeneralEnum.Logic_yes.getValue());
			//是否轮循结束
			boolean isEnd=false;
			//轮循执行
			if(isCycle){
				autoRunTask.setRunTimes(autoRunTask.getRunTimes()+1);
				this.autoRunTaskSv.save(autoRunTask);
				isEnd=autoRunTask.getEndTimes()<=autoRunTask.getRunTimes();
			}
			//非轮循 或 轮循结束 或 停止任务
			if (!isCycle || isEnd || isStop) {
				//更新任务信息
				this.autoRunTaskSv.taskComplete(taskId);
				//更新机器状态
				this.autoMachineSv.updateMachineStatusToFree(machineIp);
				if (isDistribute) {
					//更新任务与机器关系表状态为空闲
					this.autoDistributeMachineSv.updateMachineStatus(taskId, machineIp, AutoRunEnum.MachineStatus_free.getValue());
				}
			}else{//轮循未结束
				Long intervalTime=autoRunTask.getIntervalTime();//轮循间隔时间
				//延迟执行
				this.autoRunTaskSv.scheduleExecTask(taskId,intervalTime,TimeUnit.SECONDS);
			}
		}
		//用例未执行完，但是最后一个用例且为分布式
		if (!isComplete && isFinalNum && isDistribute) {
			resultList = naAutoRunResultDao.findByTaskIdAndRunType(taskId, AutoRunEnum.RunStatus_none.getValue());
			//是否还有未执行用例
			boolean isNone=resultList != null && resultList.size() > 0;
			if (isNone) {
				//访问云桌面
				this.autoRunTaskSv.accessProxy(machineIp, taskId.toString(), this.autoRunTaskCaseSv.getEnvByTaskId(taskId));
			}else{
				//更新机器状态
				this.autoMachineSv.updateMachineStatusToFree(machineIp);
				//更新任务与机器关系表状态为空闲
				this.autoDistributeMachineSv.updateMachineStatus(taskId, machineIp, AutoRunEnum.MachineStatus_free.getValue());
			}
		}
	}

	/**
	 * 解析云桌面返回日志，并保存用例执行结果信息
	 * @param report 云桌面返回信息
	 * @return NaAutoRunResult
	 */
	private NaAutoRunResult parseReportSaveResult(AutoReportRequest report){
		Long resultType=report.getResult();//获取结果状态
		Long resultId=report.getPlanid();//获取结果唯一主键
		NaAutoRunResult result=naAutoRunResultDao.findOne(resultId);
		if (result == null) {
			BusinessException.throwBusinessException("saveAutoRunResult autoRunResult could not found! please make sure the resultId:"+resultId);
		}
		//设置结果类型
		if (resultType == 0) {//成功
			result.setResultType(AutoRunEnum.ResultType_success.getValue());
		}else
		if(resultType>10L){//中断
			result.setResultType(AutoRunEnum.ResultType_interrupt.getValue());
		}else{//失败
			result.setResultType(AutoRunEnum.ResultType_fail.getValue());
			//失败上传图片
			
			//发送邮件
			
			//发送短信
		}
		result.setRunInfo(report.getReport());//用例执行日志
		result.setRunLog(report.getRunLog());//ruby执行日志
		result.setBeginTime(DateUtil.getDate(report.getStarttime(),DateUtil.YYYYMMDDHHMMSS));//设置开始时间
		result.setEndTime(DateUtil.getDate(report.getFinishtime(),DateUtil.YYYYMMDDHHMMSS));//设置结束时间
		result.setRunType(AutoRunEnum.RunStatus_complete.getValue());//设置执行状态已完成
		return this.save(result);//保存执行结果信息
	}

	/**
	 * 初始化结果表数据
	 * @param resultList NaAutoRunResult集合
	 */
	private void initResult(List<NaAutoRunResult> resultList){
		if(resultList!=null&&resultList.size()>0) {
			for (NaAutoRunResult result : resultList) {
				result.setResultType( AutoRunEnum.ResultType_none.getValue());//默认未执行
				result.setRunType(AutoRunEnum.RunStatus_none.getValue());//默认未执行
				result.setBeginTime(null);
				result.setEndTime(null);
				result.setFailReason(null);
				result.setRunInfo(null);
				result.setRunLog(null);
				entityManager.persist(result);
			}
			entityManager.flush();
			entityManager.clear();
		}
	}

	/**
	 * （立即执行）返回用例执行结果信息
	 * @param taskId 任务ID
	 * @return JSON
	 */
	private String getResultImmediatelyToJson(Long taskId){
		List<NaAutoRunResult> resultList = naAutoRunResultDao
				.findByTaskIdAndResultTypeOrderBySortGroupAscSortNumberAsc(taskId, 2L);
		List<String> results=new ArrayList<String>();
		for (NaAutoRunResult result : resultList) {
			results.add(this.getSingleResultToJson(result));
		}
		return JsonUtil.listToJson(results);
	}

	/**
	 * （分布式执行）返回用例执行结果信息(防止分布式获取用例结果信息冲突，需加同步锁)
	 * @param taskId 任务ID
	 * @param machineIp 机器IP               
	 * @return JSON
	 */
	private synchronized String getResultDistributeToJson(Long taskId,String machineIp){
		NaAutoRunTask autoRunTask=this.autoRunTaskSv.findById(taskId);
		List<NaAutoRunResult> resultList = this.naAutoRunResultDao.findByTaskIdAndRunTypeAndSortGroupOrderBySortNumberAsc(taskId, AutoRunEnum.RunStatus_none.getValue(), 0L);
		List<String> results=new ArrayList<String>();
		//先分配执行未分组用例，最后在按照组顺序分配执行
		if (resultList != null && resultList.size() > 0) {
			//分配执行用例数量不得大于每台机器可执行数量
			for (int i = 0; i < autoRunTask.getDistributeNum() || i < resultList.size(); i++) {
				NaAutoRunResult result=resultList.get(i);
				results.add(this.getSingleResultToJson(result));
			}
		} else {//获取分组用例
			List<Object> minGroup=this.naAutoRunResultDao.findMinGroupNoneExecByTaskId(taskId);
			if (minGroup != null && minGroup.size() > 0) {
				Long sortGroup=Long.parseLong(minGroup.get(0).toString());
				//同一组用例需在同一机器上执行，所以每组用例对应一个机器执行且不受可分布式执行数量上限限制
				resultList = this.naAutoRunResultDao.findByTaskIdAndRunTypeAndSortGroupOrderBySortNumberAsc(taskId, AutoRunEnum.RunStatus_none.getValue(), sortGroup);
				for(NaAutoRunResult result:resultList){
					results.add(this.getSingleResultToJson(result));
				}
			}else{
				//如果没有用例可执行，则更新机器状态为休闲
				this.autoMachineSv.updateMachineStatusToFree(machineIp);
				//更新任务与机器关系表状态为空闲
				this.autoDistributeMachineSv.updateMachineStatus(taskId,machineIp,AutoRunEnum.MachineStatus_free.getValue());
			}
		}
		return JsonUtil.listToJson(results);
	}

	/**
	 * 将用例执行结果信息转化为云桌面接收的json串信息
	 * @param result NaAutoRunResult
	 * @return JSON
	 */
	private String getSingleResultToJson(NaAutoRunResult result){
		NaAutoCase autoCase = this.autoCaseSv.findById(result.getAutoId());
		Map<String, String> map = new HashMap<String, String>();
		map.put("id",result.getResultId().toString());
		map.put("testcaseid", autoCase.getAutoName());
		map.put("datasetid",result.getTaskId()+"_"+result.getAutoId());
		map.put("sceneId",result.getEnvironmentType().toString());
		map.put("caseGroup",result.getSortGroup()==null?"0":result.getSortGroup().toString());
		//更新执行结果用例状态为执行中
		result.setRunType(AutoRunEnum.RunStatus_running.getValue());
		this.save(result);
		return JsonUtil.mapToJson(map);
	}
}
