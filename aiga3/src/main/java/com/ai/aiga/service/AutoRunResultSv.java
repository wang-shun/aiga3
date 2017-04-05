package com.ai.aiga.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ai.aiga.domain.*;
import com.ai.aiga.service.enums.AutoRunEnum;
import com.ai.aiga.util.DateUtil;
import com.ai.aiga.util.mapper.BeanMapper;
import com.ai.aiga.util.mapper.JsonUtil;
import com.ai.aiga.view.json.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.NaAutoRunResultDao;
import com.ai.aiga.dao.NaAutoRunTaskDao;
import com.ai.aiga.dao.NaAutoRunTaskReportDao;
import com.ai.aiga.dao.NaAutoTaskReportDetailDao;
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

	public void save(NaAutoRunResult naAutoRunResult) {
		
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
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "environmenttType");
		}
		naAutoRunResultDao.save(naAutoRunResult);
	}

	public Object caseByTaskList(AutoRunResultRequest condition,int pageNumber,int pageSize) {
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
	 * @param taskId
	 * @return
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
	 * @param taskId
	 * @return
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
	 * @param taskId
	 * @param resultType
	 * @return
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
	 * @param taskId
	 * @param runType
	 * @return
	 */
	public List<NaAutoRunResult> getListByTaskIdRunType(Long taskId,Long runType){
		if (taskId == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskId");
		}
		if (runType==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "runType");
		}
		List<NaAutoRunResult> resultList=this.naAutoRunResultDao.findByTaskIdAndRunType(taskId,runType);
		return resultList;
	}

	/**
	 * 根据任务ID预生成执行结果
	 * @param taskId
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
	 * 初始化结果表数据
	 * @param resultList
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
	 * 根据任务ID初始化结果表数据（任务下所有数据）
	 * @param taskId
	 */
	public void initResultAll(Long taskId){
		List<NaAutoRunResult> resultList=this.getListByTaskId(taskId);
		this.initResult(resultList);
	}

	/**
	 * 根据任务ID初始化结果表数据（初始化条件：结果状态为未成功）
	 */
	public void initResultByFail(Long taskId){
		List<NaAutoRunResult> resultList=this.getListByTaskIdResultTypeNot(taskId,AutoRunEnum.ResultType_success.getValue());
		this.initResult(resultList);
	}

	/**
	 * 根据任务ID初始化结果表数据（初始化条件：执行状态为执行中）
	 */
	public void initResultByExec(Long taskId){
		List<NaAutoRunResult> resultList=this.getListByTaskIdRunType(taskId,AutoRunEnum.RunStatus_running.getValue());
		this.initResult(resultList);
	}

	/**
	 * 根据任务ID删除
	 * @param taskId
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
	 * @param taskId
	 * @return
	 */
	public String getResultByTaskIdToJson(Long taskId){
		List<NaAutoRunResult> resultList = naAutoRunResultDao
				.findByTaskIdAndResultTypeOrderBySortGroupSortNumberAsc(taskId, 2L);
		List<String> results=new ArrayList<String>();
		for (NaAutoRunResult result : resultList) {
			NaAutoCase autoCase = this.autoCaseSv.findById(result.getAutoId());
			Map<String, String> map = new HashMap<String, String>();
			map.put("id",result.getResultId().toString());
			map.put("testcaseid", autoCase.getAutoName());
			map.put("datasetid",result.getTaskId()+"_"+result.getAutoId());
			map.put("sceneId",result.getEnvironmentType().toString());
			map.put("caseGroup",result.getSortGroup()==null?"0":result.getSortGroup().toString());
			results.add(JsonUtil.mapToJson(map));
		}
		//更新发送到云桌面的执行用例的runType为执行中
		naAutoRunResultDao.updateRunTypeByTaskIdAndResultType(taskId, AutoRunEnum.RunStatus_running.getValue(),AutoRunEnum.ResultType_none.getValue());
		return JsonUtil.listToJson(results);
	}

	public void saveAutoRunResult(String resultJson)throws Exception{
		List<AutoReportRequest> reportList=JsonUtil.jsonToList(JsonUtil.jsonToArray(resultJson)[0], AutoReportRequest.class);
		if (reportList == null || reportList.size()==0) {
			BusinessException.throwBusinessException("saveAutoRunResult report is null !");
		}
		AutoReportRequest report=reportList.get(0);
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
		}
		result.setBeginTime(DateUtil.getDate(report.getStarttime(),DateUtil.YYYYMMDDHHMMSS));//设置开始时间
		result.setEndTime(DateUtil.getDate(report.getFinishtime(),DateUtil.YYYYMMDDHHMMSS));//设置结束时间
		result.setRunType(AutoRunEnum.RunStatus_complete.getValue());//设置执行状态已完成
		this.save(result);//保存执行结果信息

		//查询用例是否执行完成
		List<NaAutoRunResult> resultList=naAutoRunResultDao.findByTaskIdAndRunTypeNot(result.getTaskId(),AutoRunEnum.RunStatus_complete.getValue());
		if (resultList.size() == 0) {
			//获取任务信息
			NaAutoRunTask autoRunTask=this.autoRunTaskSv.findById(result.getTaskId());
			autoRunTask.setEndRunTime(DateUtil.getCurrentTime());//结束时间
			autoRunTask.setSpendTime(DateUtil.getIntervalMinute(autoRunTask.getBeginRunTime(),autoRunTask.getEndRunTime()));//花费时间
			//查询未成功的用例
			resultList = naAutoRunResultDao.findByTaskIdAndResultTypeNot(autoRunTask.getTaskId(), AutoRunEnum.ResultType_success.getValue());
			if (resultList != null && resultList.size() > 0) {
				autoRunTask.setTaskResult(AutoRunEnum.TaskResult_fail.getValue());//有未成功的用例则失败
			} else {
				autoRunTask.setTaskResult(AutoRunEnum.TaskResult_success.getValue());//没有则成功
			}
			this.autoRunTaskSv.save(autoRunTask);
			//更新机器状态

		}



	}

}
