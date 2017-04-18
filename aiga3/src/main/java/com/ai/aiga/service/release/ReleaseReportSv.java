package com.ai.aiga.service.release;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.DbExecutionExceptionDao;
import com.ai.aiga.dao.OnlineSysReleaseDao;
import com.ai.aiga.dao.ReleaseReportDao;
import com.ai.aiga.dao.ScriptExecutionProcessDao;
import com.ai.aiga.dao.TestProcessDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.domain.NaChangePlanOnile;
import com.ai.aiga.domain.NaCodePath;
import com.ai.aiga.domain.NaDbExecutionException;
import com.ai.aiga.domain.NaDbScriptExecutionProcess;
import com.ai.aiga.domain.NaEmployeeInfo;
import com.ai.aiga.domain.NaOnlineSysRelease;
import com.ai.aiga.domain.NaReleaseReport;
import com.ai.aiga.domain.NaTestProcess;
import com.ai.aiga.domain.PlanDetailManifest;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.util.DateUtil;
import com.ai.aiga.util.mapper.BeanMapper;
import com.ai.aiga.view.controller.plan.dto.PlanDetailManifestExcel;
import com.ai.aiga.view.json.ExecutionExceptionExcel;
import com.ai.aiga.view.util.SessionMgrUtil;

/**
 * @ClassName: ReleaseReportSv
 * @author: liujinfang
 * @date: 2017年4月17日 下午1:03:29
 * @Description:
 * 
 */
@Service
@Transactional
public class ReleaseReportSv extends BaseService{
	@Autowired
	private  ReleaseReportDao releaseReportDao;
	
	@Autowired
	private   DbExecutionExceptionDao  dbExecutionExceptionDao;
	
	@Autowired
	private	ScriptExecutionProcessDao  scriptExecutionProcessDao;
	
	@Autowired
	private     OnlineSysReleaseDao  onlineSysReleaseDao;
	
	@Autowired
	private        TestProcessDao   testProcessDao;
	 public Object list(int pageNumber, int pageSize,NaReleaseReport condition,NaChangePlanOnile condition1) throws ParseException {
			List<String> list = new ArrayList<String>();
			list.add("taskId");
			list.add("taskName");
			list.add("taskType");
			list.add("dealOpId");
			list.add("assignId");
			list.add("assignDate");
			list.add("onlinePlan");
			list.add("onlinePlanName");
			list.add("planState");
			list.add("planDate");
			
			list.add("remark");
			list.add("isFinished");
			list.add("dealId");
			list.add("result");
			list.add("dealType");
			list.add("opId");
			list.add("ext2");
			list.add("applicationName");
			list.add("startTime");
			list.add("finishTime");
			list.add("duration");
			
			
			String sql = " SELECT c.TASK_ID,c.TASK_NAME,c.TASK_TYPE,c.DEAL_OP_ID,c.ASSIGN_ID, "
					+ "c.ASSIGN_DATE,c.ONLINE_PLAN,c.ONLINE_PLAN_NAME,c.PLAN_STATE,c.PLAN_DATE,"
					+ "c.REMARK,c.Is_Finished,e.deal_id, e.result,e.deal_type,e.op_id, "
					+ "e.ext2,  r.application_name,r.start_time, r.finish_time, r.duration  "
					+ "FROM (SELECT A.TASK_ID, A.TASK_NAME, A.TASK_TYPE,A.DEAL_OP_ID, A.ASSIGN_ID,  "
					+ "A.ASSIGN_DATE,B.ONLINE_PLAN, B.ONLINE_PLAN_NAME, B.PLAN_STATE, "
					+ "B.PLAN_DATE, B.RESULT,B.REMARK,B.Is_Finished FROM NA_ONLINE_TASK_DISTRIBUTE A,"
					+ "NA_CHANGE_PLAN_ONILE B  where A.ONLINE_PLAN = B.ONLINE_PLAN AND A.TASK_TYPE IN (9) "
					+ "AND (B.SIGN = 0 OR B.SIGN IS NULL)) C,"
					+ "na_online_task_result e,na_release_report r where c.task_id = e.task_id(+) "
					+ "and to_char(c.plan_date, 'YYYY-MM-DD HH24:MI:SS') = to_char(r.release_date(+), 'YYYY-MM-DD HH24:MI:SS') ";
			if(condition1.getPlanState()!=null){
				sql+=" and c.PLAN_STATE="+condition1.getPlanState();
			}
			if(StringUtils.isNotBlank(condition.getOnlinePlanName())){
				sql+=" and c.ONLINE_PLAN_NAME like '%"+condition.getOnlinePlanName()+"%'";
				
			}
			if(StringUtils.isNotBlank(condition.getTaskName())){
				sql+=" and c.TASK_NAME like '%"+condition.getTaskName()+"%'";
				
			}
			if(condition.getStartTime()!=null){
				sql+=" and r.start_time = to_date('"+condition.getStartTime()+"','YYYY-MM-DD HH24:MI:SS')";
				
			}
			if(condition.getFinishTime()!=null){
				sql+=" and r.finish_time = to_date('"+condition.getFinishTime()+"','YYYY-MM-DD HH24:MI:SS')";
				
				
			}
			if(StringUtils.isNotBlank(condition.getApplicationName())){
				sql+=" and  r.application_name like '%"+condition.getApplicationName()+"%'" ;
				
			}
			sql+=" order by c.PLAN_DATE desc";
			if (pageNumber < 0) {
				pageNumber = 0;
			}

			if (pageSize <= 0) {
				pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
			}

			Pageable pageable = new PageRequest(pageNumber, pageSize);

			return releaseReportDao.searchByNativeSQL(sql, pageable, list);
			
		}
	 /**
	  * 
	  * @ClassName: ReleaseReportSv :: saveExcel
	  * @author: liujinfang
	  * @date: 2017年4月17日 下午5:58:49
	  *
	  * @Description:
	  * @param planId
	  * @param list
	  */
	 public void saveExcel(Long planId, List<ExecutionExceptionExcel> list) {
			if(planId == null || planId < 0){
				BusinessException.throwBusinessException(ErrorCode.Parameter_null, "planId");
			}
			
			if(list == null || list.size() <= 0){
				BusinessException.throwBusinessException(ErrorCode.Parameter_null, "导入内容");
			}
			
			
			List<NaDbExecutionException> values = BeanMapper.mapList(list, ExecutionExceptionExcel.class, NaDbExecutionException.class);
			if(values != null){
				for(NaDbExecutionException v : values){
					v.setPlanId(planId);
				
				}
			}
			
			dbExecutionExceptionDao.save(values);
		}
	 
	 
	 public Object findDbExecutionException(int pageNumber, int pageSize, NaDbExecutionException condition) {
		   
			List<Condition> cons = new ArrayList<Condition>();
				
				if(condition != null){
					if(condition.getPlanId()!= null){
						cons.add(new Condition("planId", condition.getPlanId(), Condition.Type.EQ));
					}
				}
				
				if(pageNumber < 0){
					pageNumber = 0;
				}
				
				if(pageSize <= 0){
					pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
				}

				Pageable pageable = new PageRequest(pageNumber, pageSize);
				
				return dbExecutionExceptionDao.search(cons, pageable);
				
			}
	 
	 public Object findDbScriptExecutionProcess(int pageNumber, int pageSize, NaDbScriptExecutionProcess condition) {
		   
			List<Condition> cons = new ArrayList<Condition>();
				
				if(condition != null){
					if(condition.getPlanId()!= null){
						cons.add(new Condition("planId", condition.getPlanId(), Condition.Type.EQ));
					}
				}
				
				if(pageNumber < 0){
					pageNumber = 0;
				}
				
				if(pageSize <= 0){
					pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
				}

				Pageable pageable = new PageRequest(pageNumber, pageSize);
				
				return scriptExecutionProcessDao.search(cons, pageable);
				
			}
	 
	 public Object findOnlineSysRelease(int pageNumber, int pageSize, NaOnlineSysRelease condition) {
		   
			List<Condition> cons = new ArrayList<Condition>();
				
				if(condition != null){
					if(condition.getPlanId()!= null){
						cons.add(new Condition("planId", condition.getPlanId(), Condition.Type.EQ));
					}
				}
				
				if(pageNumber < 0){
					pageNumber = 0;
				}
				
				if(pageSize <= 0){
					pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
				}

				Pageable pageable = new PageRequest(pageNumber, pageSize);
				
				return onlineSysReleaseDao.search(cons, pageable);
				
			}
	 
	 
	 public Object findTestProcess(int pageNumber, int pageSize, NaTestProcess condition) {
		   
			List<Condition> cons = new ArrayList<Condition>();
				
				if(condition != null){
					if(condition.getPlanId()!= null){
						cons.add(new Condition("planId", condition.getPlanId(), Condition.Type.EQ));
					}
				}
				
				if(pageNumber < 0){
					pageNumber = 0;
				}
				
				if(pageSize <= 0){
					pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
				}

				Pageable pageable = new PageRequest(pageNumber, pageSize);
				
				return testProcessDao.search(cons, pageable);
				
			}
	 
}

