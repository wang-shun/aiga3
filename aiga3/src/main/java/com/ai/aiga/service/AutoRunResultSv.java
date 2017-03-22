package com.ai.aiga.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.NaAutoRunResultDao;
import com.ai.aiga.dao.NaAutoRunTaskReportDao;
import com.ai.aiga.dao.NaAutoTaskReportDetailDao;
import com.ai.aiga.domain.NaAutoRunResult;
import com.ai.aiga.domain.NaAutoRunTaskReport;
import com.ai.aiga.domain.NaAutoTaskReportDetail;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.view.json.AutoRunResultRequest;
import com.ai.aiga.view.json.NaAutoRunTaskReportResponse;
import com.ai.aiga.view.json.NaAutoTaskReportDetailRequest;
import com.ai.aiga.view.json.TaskRunResultRequest;

@Service
@Transactional
public class AutoRunResultSv {
	
	@Autowired
	private NaAutoRunResultDao naAutoRunResultDao;
	
	@Autowired
	private NaAutoRunTaskReportDao naAutoRunTaskReportDao;
	
	@Autowired
	private NaAutoTaskReportDetailDao naAutoTaskReportDetailDao;

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
		if(StringUtils.isBlank(naAutoRunResult.getGroupId().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "groupId");
		}
		if(StringUtils.isBlank(naAutoRunResult.getCollectId().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "collectId");
		}
		if(StringUtils.isBlank(naAutoRunResult.getResultType().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "resultType");
		}
		if(StringUtils.isBlank(naAutoRunResult.getRunType().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "runType");
		}
		if(StringUtils.isBlank(naAutoRunResult.getSortNumber().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "sortNumber");
		}
		if(StringUtils.isBlank(naAutoRunResult.getSortGroup().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "sortGroup");
		}
		if(StringUtils.isBlank(naAutoRunResult.getEnvironmentType().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "environmenttType");
		}
		if(StringUtils.isBlank(naAutoRunResult.getBeginTime().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "beginTime");
		}
		if(StringUtils.isBlank(naAutoRunResult.getEndTime().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "endTime");
		}
		if(StringUtils.isBlank(naAutoRunResult.getRunInfo())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "runInfo");
		}
		if(StringUtils.isBlank(naAutoRunResult.getRunLog())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "runLog");
		}
		if(StringUtils.isBlank(naAutoRunResult.getFailReason())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "failReason");
		}
		
		NaAutoRunResult result = new NaAutoRunResult();
		result.setTaskId(naAutoRunResult.getTaskId());
		result.setAutoId(naAutoRunResult.getTaskId());
		result.setGroupId(naAutoRunResult.getGroupId());
		result.setCollectId(naAutoRunResult.getCollectId());
		result.setResultType(naAutoRunResult.getResultType());
		result.setRunType(naAutoRunResult.getRunType());
		result.setSortNumber(naAutoRunResult.getSortNumber());
		result.setSortGroup(naAutoRunResult.getSortGroup());
		result.setEnvironmentType(naAutoRunResult.getEnvironmentType());
		result.setBeginTime(naAutoRunResult.getBeginTime());
		result.setEndTime(naAutoRunResult.getEndTime());
		result.setRunInfo(naAutoRunResult.getRunInfo());
		result.setRunLog(naAutoRunResult.getRunLog());
		result.setFailReason(naAutoRunResult.getFailReason());
		
		naAutoRunResultDao.save(result);
	}

	public Object caseByTaskList(AutoRunResultRequest condition,int pageNumber,int pageSize) {
		if(condition == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(condition.getTaskId() == null || condition.getTaskId() < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "resultId");
		}
		String sql = "select a.auto_id, b.auto_name, a.sort_number, a.sort_group, a.result_type,"
				+ " a.fail_reason from na_auto_run_result a, na_auto_case b where a.auto_id = b.auto_id "
				+ "and a.result_id = "+condition.getTaskId();
		if(StringUtils.isNoneBlank(condition.getAutoName())){
			sql += " and auto_name like '%"+condition.getAutoName()+"%'";
		}
		if(condition.getResultType() != null && condition.getResultType() > 0){
			sql += " and a.result_type = "+condition.getResultType() ;
		}
		List<String> list = new ArrayList<String>();
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

	public Map<String, String> runInfo(Long resultId, Long autoId) {
		if(resultId == null || resultId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "resultId");

		}
		if(autoId == null || autoId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "autoId");

		}
		Map<String, String> map = new HashMap<String, String>();
		String runInfo = naAutoRunResultDao.runInfo(resultId, autoId);
		map.put("runInfo", runInfo);
		return map;
	}

	public Object list(TaskRunResultRequest condition, int pageNumber, int pageSize) {
		String sql = "select distinct a.task_id, b.task_tag, b.task_name, a.result_type, b.machine_ip, c.machine_name, "
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
			if(condition.getTaskName() != null){
				sql += " and b.task_name like '%"+condition.getTaskName()+"%'";
			}
			if(condition.getTaskTag() != null){
				sql += " and b.task_tag ="+condition.getTaskTag();
			}
			if(condition.getResultType() != null){
				sql += " and a.result_type ="+condition.getResultType();
			}
			if(condition.getMachineIp() != null){
				sql += " and b.machine_ip ="+condition.getMachineIp();
			}
		}
		List<String> list = new ArrayList<String>();
		list.add("taskId");
		list.add("taskTag");
		list.add("taskName");
		list.add("resultType");
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
		NaAutoRunTaskReportResponse response = naAutoRunTaskReportDao.findByTaskId(taskId);
		return response;
	}

	public void reportSave(NaAutoRunTaskReport naAutoRunTaskReport) {
		if(naAutoRunTaskReport == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(StringUtils.isBlank(naAutoRunTaskReport.getTaskId().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskId");
		}
		if(StringUtils.isBlank(naAutoRunTaskReport.getReportName())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "reportName");
		}
		if(StringUtils.isBlank(naAutoRunTaskReport.getTotalCase().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "totalCase");
		}
		if(StringUtils.isBlank(naAutoRunTaskReport.getNoneRunCase().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "noneRunCase");
		}
		if(StringUtils.isBlank(naAutoRunTaskReport.getHasRunCase().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "hasRunCase");
		}
		if(StringUtils.isBlank(naAutoRunTaskReport.getSuccessCase().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "successCase");
		}
		if(StringUtils.isBlank(naAutoRunTaskReport.getFailCase().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "failCase");
		}
		if(StringUtils.isBlank(naAutoRunTaskReport.getBeginTime().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "beginTime");
		}
		if(StringUtils.isBlank(naAutoRunTaskReport.getEndTime().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "endTime");
		}
		if(StringUtils.isBlank(naAutoRunTaskReport.getSpendTime().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "spendTime");
		}
		if(StringUtils.isBlank(naAutoRunTaskReport.getCreatorId().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "creatorId");
		}
		if(StringUtils.isBlank(naAutoRunTaskReport.getSuccessRate())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "successRate");
		}
		
		NaAutoRunTaskReport report = new NaAutoRunTaskReport();
		report.setTaskId(naAutoRunTaskReport.getTaskId());
		report.setReportName(naAutoRunTaskReport.getReportName());
		report.setTotalCase(naAutoRunTaskReport.getTotalCase());
		report.setNoneRunCase(naAutoRunTaskReport.getNoneRunCase());
		report.setHasRunCase(naAutoRunTaskReport.getHasRunCase());
		report.setSuccessCase(naAutoRunTaskReport.getSuccessCase());
		report.setFailCase(naAutoRunTaskReport.getFailCase());
		report.setBeginTime(naAutoRunTaskReport.getBeginTime());
		report.setEndTime(naAutoRunTaskReport.getEndTime());
		report.setSpendTime(naAutoRunTaskReport.getSpendTime());
		report.setCreatorId(naAutoRunTaskReport.getCreatorId());
		report.setSuccessRate(naAutoRunTaskReport.getSuccessRate());
		report.setUpdateTime(new Date());
		naAutoRunTaskReportDao.save(report);
	}

	public Object taskDetail(Long taskId, int pageNumber, int pageSize) {
		if(taskId == null || taskId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskId");
		}
		String sql = "select b.report_id, a.task_id, a.auto_id, a.result_id, c.auto_name, c.creator_id, d.name as creator_name,"
				+ " (case when (a.result_type = 0) then '0' end) as is_success"
				+ " from na_auto_run_result a, na_auto_run_task_report b, na_auto_case c, aiga_staff d"
				+ " where a.task_id = b.task_id and a.auto_id = c.auto_id and c.creator_id = d.staff_id"
				+ " and a.task_id = "+taskId;
		List<String> list = new ArrayList<String>();
		list.add("reportId");
		list.add("taskId");
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

	public void reportDetailSave(List<NaAutoTaskReportDetailRequest> list) {
		if(list == null){
			BusinessException.throwBusinessException("报告明细对象集合为空！");
		}
		for(int i = 0; i < list.size(); i++){
			NaAutoTaskReportDetailRequest request = list.get(i);
			if(request != null){
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
			}
		}
		
	}

	public Map<String , String>  runLog(Long resultId, Long autoId) {
		if(resultId == null || resultId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "resultId");

		}
		if(autoId == null || autoId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "autoId");

		}
		Map<String, String> map = new HashMap<String, String>();
		String runLog = naAutoRunResultDao.runLog(resultId, autoId);
		map.put("runLog", runLog);
		return map;
	}

}
