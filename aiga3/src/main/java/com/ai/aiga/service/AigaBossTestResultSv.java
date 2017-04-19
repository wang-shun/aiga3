package com.ai.aiga.service;



import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.AigaBossTestResultDao;
import com.ai.aiga.domain.AigaBossTestResult;

import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.util.mapper.BeanMapper;
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
	 * @throws Exception 
	 */
	public Page<Map> findAigaBossTestResult(NaOtherTaskRequest request,int pageNumber,int pageSize){
		if(request==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if(request.getTaskType()==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "TaskType");
		}
		String sql = "select a.result_id, b.online_plan_name, "
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
							   +"     on a.task_id = c.task_id  where b.sign<> 1 and a.type="+request.getTaskType();
				if(request.getOnlinePlan()!=null){
					sql += " and a.online_plan ="+request.getOnlinePlan();
				}
				if(request.getTaskId()!=null){
					sql += " and a.task_id ="+request.getOnlinePlan();
				}
				if(request.getSubTaskName()!=null&&!"".equals(request.getSubTaskName())){
					sql += " and a.boss_name like '%"+request.getSubTaskName()+"%'";
				}
				if(request.getDealState()!=null){
					sql += " and c.deal_state  = "+request.getDealState();
				}
				if(pageNumber < 0){
					pageNumber = 0;
				}
				
				if(pageSize <= 0){
					pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
				}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return aigaBossTestResultDao.searchByNativeSQL(sql, pageable);
	}
	
	
	
	/**
	 * 保存
	 * @param request
	 */
	public  void  saveOtherTestResult(BossTestResultRequest request){
		if(request==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if(request.getOnlinePlan()==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "OnlinePlan");
		}
		if(request.getTaskId()==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "TaskId");
		}
		if(request.getBossName()==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "BossName");
		}
		AigaBossTestResult result= BeanMapper.map(request,AigaBossTestResult.class);
		//新增
		if(request.getResultId()==null){
			/*计算第一次计划执行时间到报告填写耗时*/
			List totalList=aigaBossTestResultDao.searchformSQL("select nvl(round((sysdate-min(create_time))*24,2),0) from aiga_product_plan_info where plan_id in (select plan_id from aiga_product_plan_info where planTask_id='"+request.getPlanId()+"') group by plan_id");
			Double totalTime=0.0D;
 			if(totalList!=null&&totalList.size()>0){
 				totalTime=Double.parseDouble(totalList.get(0).toString());
 				result.setTotalTimeCount(totalTime);
 			}
 			/*计算计划发起到结束执行时间(取最后一次计划执行结束时间减去第一次计划执行开始时间)*/
 			List execList=aigaBossTestResultDao.searchformSQL("select nvl(round((max(end_date)-min(create_time))*24,2),0) from aiga_product_plan_info where plan_id in (select plan_id from aiga_product_plan_info where planTask_id='"+request.getPlanId()+"') group by plan_id");
	 		Double execTime=0.0D;
	 		if(execList!=null&&execList.size()>0){
	 			execTime=Double.parseDouble(execList.get(0).toString());
	 			result.setExecTimeCount(execTime);
 			}
	 		/*计算总共执行了几次计划*/
	 		Integer execCount=this.aigaBossTestResultDao.searchformSQL("select * from aiga_product_plan_info where plan_id in (select plan_id from aiga_product_plan_info where planTask_id='"+request.getPlanId()+"') ").size();
	 		result.setExecCount(execCount);
	 		/*计算分析时长，报告填写时间-最后一次计划执行结束时间*/
	 		List analyList=this.aigaBossTestResultDao.searchformSQL("select nvl(round((sysdate-max(end_date))*24,2),0) from aiga_product_plan_info where plan_id in (select plan_id from aiga_product_plan_info where planTask_id='"+request.getPlanId()+"') group by plan_id");
	 		Double analyTime=0.0D;
	 		if(analyList!=null&&analyList.size()>0){
	 			analyTime=Double.parseDouble(analyList.get(0).toString());
	 			result.setAnalyTimeCount(analyTime);
 			}
		}
		aigaBossTestResultDao.save(result);
	}
	
	
	
	
	/**
	 * 删除
	 * @param resultIds
	 */
	public void deleteAigaBossTestResult(Long resultId){
		if(resultId==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "resultId");
		}
		aigaBossTestResultDao.delete(resultId);
	}



	

	
	
	/**
	 * 根据类型查询boss任务/开通任务。。。。。
	 * @param type
	 * @return
	 */
	public Object getOtherFlowName(Long type) {
		if(type==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "onlinePlan");
		}
		StringBuilder s = new StringBuilder();
		s.append(" select planTask_name,planTask_id from aiga_product_plan_info and type =  "+type);
		s.append(" order by create_time desc");
			
		return aigaBossTestResultDao.searchByNativeSQL(s.toString());
	}
}
