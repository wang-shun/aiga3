package com.ai.aiga.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.AigaBossTestResultDao;
import com.ai.aiga.domain.AigaBossTestResult;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.json.BossTestResultRequest;
import com.ai.aiga.view.json.NaOtherTaskRequest;


@Service
@Transactional
public class AigaBossTestResultSv  extends BaseService{
	@Autowired
	private AigaBossTestResultDao aigaBossTestResultDao;

	
	/**
	 * 查询
	 * @param request
	 * @return
	 */
	public List<AigaBossTestResult> findAigaBossTestResult(NaOtherTaskRequest request){
		if(request==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		String sql = "select b.online_plan_name, "
							  +"     a.online_date,"
							  +"     c.task_name,"
							  +"     a.test_remark,"
							  +"     a.bug_count,"
							   +"    a.bug_remark,"
							   +"    a.reason,"
							   +"     a.if_solve,"
							   +"     a.bug_status,"
							   +"     a.online_condition,"
							   +"     a.remark,"
							    +"     a.solution,"
							    +"     a.exec_time_count,"
							    +"     a.total_time_count,"
							    +"     a.boss_name,"
							   +"      a.task_id,"
							   +"      a.exec_count,"
							   +"      a.analy_time_count"
							   +"    	from AIGA_BOSS_TEST_RESULT a"
							   +"    	left join na_change_plan_onile b"
							   +"     on a.online_plan = b.online_plan"
							   +"   	left join na_online_task_distribute c"
							   +"     on a.task_id = c.task_id and a.type="+request.getType();

		//aigaBossTestResultDao.searchByNativeSQLS(nativeSQL, pageable)
		return null;
	}
	
	
	
	/**
	 * 保存
	 * @param request
	 */
	public  void  saveAigaBossTestResult(BossTestResultRequest request){
		if(request==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		
	}
	
	
	
	
	/**
	 * 删除
	 * @param resultIds
	 */
	public void deleteAigaBossTestResult(String resultIds){
		if(resultIds==null|"".equals(resultIds)){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "resultIds");
		}
		String[] ids = resultIds.split(",");
		List<Long> list = new ArrayList<Long>();
		for (String resultId : ids) {
			list.add(Long.parseLong(resultId));
		}
		aigaBossTestResultDao.deleteByIds(list);
	}
}
