package com.ai.am.service.release;

import java.text.ParseException;
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

import com.ai.am.constant.BusiConstant;
import com.ai.am.dao.DbExecutionExceptionDao;
import com.ai.am.dao.NaOnlineGeneralStepsDao;
import com.ai.am.dao.OnlineSysReleaseDao;
import com.ai.am.dao.OnlineSystemReleaseStageDao;
import com.ai.am.dao.ReleaseMessageDao;
import com.ai.am.dao.ReleaseReportDao;
import com.ai.am.dao.ScriptExecutionProcessDao;
import com.ai.am.dao.StaffArrangeDao;
import com.ai.am.dao.TestProcessDao;
import com.ai.am.dao.jpa.Condition;
import com.ai.am.dao.jpa.ParameterCondition;
import com.ai.am.domain.NaChangePlanOnile;
import com.ai.am.domain.NaChangeReview;
import com.ai.am.domain.NaCodePath;
import com.ai.am.domain.NaDbExecutionException;
import com.ai.am.domain.NaDbScriptExecutionProcess;
import com.ai.am.domain.NaEmployeeInfo;
import com.ai.am.domain.NaOnlineGeneralSteps;
import com.ai.am.domain.NaOnlineStaffArrange;
import com.ai.am.domain.NaOnlineSysRelease;
import com.ai.am.domain.NaOnlineSystemReleaseStage;
import com.ai.am.domain.NaReleaseMessage;
import com.ai.am.domain.NaReleaseReport;
import com.ai.am.domain.NaTestProcess;
import com.ai.am.domain.PlanDetailManifest;
import com.ai.am.exception.BusinessException;
import com.ai.am.exception.ErrorCode;
import com.ai.am.service.TestProcessExcel;
import com.ai.am.service.base.BaseService;
import com.ai.am.service.onlineProcess.NodeRecordSv;
import com.ai.am.service.team.dto.Teaminfo;
import com.ai.am.util.DateUtil;
import com.ai.am.util.mapper.BeanMapper;
import com.ai.am.view.controller.plan.dto.PlanDetailManifestExcel;
import com.ai.am.view.controller.release.dto.DbExecutionExceptionRequest;
import com.ai.am.view.controller.release.dto.NaReleaseReportRequest;
import com.ai.am.view.controller.team.dto.TeamInfoRequest;
import com.ai.am.view.json.ExecutionExceptionExcel;
import com.ai.am.view.json.OnlineSysReleaseExcel;
import com.ai.am.view.json.OnlineSysReleaseStageExcel;
import com.ai.am.view.json.ScriptExecutionProcessExcel;
import com.ai.am.view.util.SessionMgrUtil;

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
	private ReleaseReportDao releaseReportDao;

	@Autowired
	private DbExecutionExceptionDao dbExecutionExceptionDao;

	@Autowired
	private ScriptExecutionProcessDao scriptExecutionProcessDao;

	@Autowired
	private OnlineSysReleaseDao onlineSysReleaseDao;

	@Autowired
	private TestProcessDao testProcessDao;

	@Autowired
	private ReleaseMessageDao releaseMessageDao;
	
	@Autowired
	private OnlineSystemReleaseStageDao   onlineSystemReleaseStageDao;
	 
	@Autowired
	private StaffArrangeDao staffArrangeDao;
	
	@Autowired
	private	NaOnlineGeneralStepsDao naOnlineGeneralStepsDao;
	
	@Autowired
	private  NodeRecordSv nodeRecordSv;

	public Object list(int pageNumber, int pageSize, NaReleaseReportRequest condition, NaChangePlanOnile condition1)
			throws ParseException {
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
	
		
		
		if(condition1.getOnlinePlan()!=null&&!condition1.getOnlinePlan().equals("")){
			sql +=" and c.ONLINE_PLAN="+ condition1.getOnlinePlan();
		}
		
		if (StringUtils.isNotBlank(condition.getTaskName())) {
			sql += " and c.TASK_NAME like '%" + condition.getTaskName() + "%'";

		}
		if (condition.getStartTime() != null&&!condition.getStartTime().equals("")) {
			sql += " and r.start_time > to_date('" + condition.getStartTime() + "','YYYY-MM-DD HH24:MI:SS')";
			
		}
		if (condition.getFinishTime() != null&&!condition.getFinishTime().equals("")) {
			sql += " and r.finish_time < to_date('" + condition.getFinishTime() + "','YYYY-MM-DD HH24:MI:SS')";

		}
	
		
		
		sql += " order by c.PLAN_DATE desc";
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
	public void saveExecutionExceptionExcel(Long planId, List<ExecutionExceptionExcel> list) {
		if (planId == null || planId < 0) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "planId");
		}

		if (list == null || list.size() <= 0) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "导入内容");
		}

		List<NaDbExecutionException> values = BeanMapper.mapList(list, ExecutionExceptionExcel.class,
				NaDbExecutionException.class);
		if (values != null) {
			for (NaDbExecutionException v : values) {
				v.setPlanId(planId);

			}
		}

		dbExecutionExceptionDao.save(values);
	}

	public Object findDbExecutionException(int pageNumber, int pageSize, DbExecutionExceptionRequest condition) {

		List<Condition> cons = new ArrayList<Condition>();

		if (condition != null) {
			if (condition.getPlanId() != null) {
				cons.add(new Condition("planId", condition.getPlanId(), Condition.Type.EQ));
			}
		}

		if (pageNumber < 0) {
			pageNumber = 0;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);

		return dbExecutionExceptionDao.search(cons, pageable);

	}
	
	
	public void saveDbScriptExecutionProcessExcel(Long planId, List<ScriptExecutionProcessExcel> list) {
		if (planId == null || planId < 0) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "planId");
		}

		if (list == null || list.size() <= 0) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "导入内容");
		}

		List<NaDbScriptExecutionProcess>  values = BeanMapper.mapList(list, ScriptExecutionProcessExcel.class,
				NaDbScriptExecutionProcess.class);
		if (values != null) {
			for (NaDbScriptExecutionProcess v : values) {
				v.setPlanId(planId);

			}
		}

		scriptExecutionProcessDao.save(values);
	}

	public void saveOnlineSysReleaseExcel(Long planId, List<OnlineSysReleaseExcel> list) {
		if (planId == null || planId < 0) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "planId");
		}

		if (list == null || list.size() <= 0) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "导入内容");
		}

		List<NaOnlineSysRelease>  values = BeanMapper.mapList(list, OnlineSysReleaseExcel.class,
				NaOnlineSysRelease.class);
		if (values != null) {
			for (NaOnlineSysRelease v : values) {
				v.setPlanId(planId);

			}
		}

		onlineSysReleaseDao.save(values);
	}
	
	
	public Object findDbScriptExecutionProcess(int pageNumber, int pageSize, NaDbScriptExecutionProcess condition) {

		List<Condition> cons = new ArrayList<Condition>();

		if (condition != null) {
			if (condition.getPlanId() != null) {
				cons.add(new Condition("planId", condition.getPlanId(), Condition.Type.EQ));
			}
		}

		if (pageNumber < 0) {
			pageNumber = 0;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);

		return scriptExecutionProcessDao.search(cons, pageable);

	}

	public Object findOnlineSysRelease(int pageNumber, int pageSize, NaOnlineSysRelease condition) {

		List<Condition> cons = new ArrayList<Condition>();

		if (condition != null) {
			if (condition.getPlanId() != null) {
				cons.add(new Condition("planId", condition.getPlanId(), Condition.Type.EQ));
			}
		}

		if (pageNumber < 0) {
			pageNumber = 0;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);

		return onlineSysReleaseDao.search(cons, pageable);

	}
	public void saveTestProcessExcel(Long planId, List<TestProcessExcel> list) {
		if (planId == null || planId < 0) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "planId");
		}

		if (list == null || list.size() <= 0) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "导入内容");
		}

		List<NaTestProcess>  values = BeanMapper.mapList(list, TestProcessExcel.class,
				NaTestProcess.class);
		if (values != null) {
			for (NaTestProcess v : values) {
				v.setPlanId(planId);

			}
		}

		testProcessDao.save(values);
	}
	public Object findTestProcess(int pageNumber, int pageSize, NaTestProcess condition) {

		List<Condition> cons = new ArrayList<Condition>();

		if (condition != null) {
			if (condition.getPlanId() != null) {
				cons.add(new Condition("planId", condition.getPlanId(), Condition.Type.EQ));
			}
		}

		if (pageNumber < 0) {
			pageNumber = 0;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);

		return testProcessDao.search(cons, pageable);

	}
	
	public Object findReleaseMessage(int pageNumber, int pageSize, NaReleaseMessage condition) {

		List<Condition> cons = new ArrayList<Condition>();

		if (condition != null) {
			if (condition.getPlanId() != null) {
				cons.add(new Condition("planId", condition.getPlanId(), Condition.Type.EQ));
			}
		}

		if (pageNumber < 0) {
			pageNumber = 0;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);

		return releaseMessageDao.search(cons, pageable);

	}
	public NaReleaseMessage saveReleaseMessage(NaReleaseMessage request){
		Long node=6L;
		 if(request == null){ 
				BusinessException.throwBusinessException(ErrorCode.Parameter_null);
			}
		 NaReleaseMessage naReleaseMessage=new NaReleaseMessage();
		  
			   naReleaseMessage.setNewOnlineSituation(request.getNewOnlineSituation());
			   naReleaseMessage.setTestSituation(request.getTestSituation());
			   naReleaseMessage.setItem(request.getItem());
			   naReleaseMessage.setDbScriptSituation(request.getDbScriptSituation());
			   naReleaseMessage.setVersionSituation(request.getVersionSituation());
			   
			   nodeRecordSv.update(request.getPlanId(), node);
			   return releaseMessageDao.save(naReleaseMessage);
		  
		
	} 
	
	public Object findOnlineSysReleaseStage(int pageNumber, int pageSize, NaOnlineSystemReleaseStage condition) {

		List<Condition> cons = new ArrayList<Condition>();

		if (condition != null) {
			if (condition.getPlanId() != null) {
				cons.add(new Condition("planId", condition.getPlanId(), Condition.Type.EQ));
			}
		}

		if (pageNumber < 0) {
			pageNumber = 0;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);

		return onlineSystemReleaseStageDao.search(cons, pageable);

	}
	
	public void saveOnlineSysReleaseStageExcel(Long planId, List<OnlineSysReleaseStageExcel> list) {
		if (planId == null || planId < 0) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "planId");
		}

		if (list == null || list.size() <= 0) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "导入内容");
		}

		List<NaOnlineSystemReleaseStage>  values = BeanMapper.mapList(list, OnlineSysReleaseStageExcel.class,
				NaOnlineSystemReleaseStage.class);
		if (values != null) {
			for (NaOnlineSystemReleaseStage v : values) {
				v.setPlanId(planId);

			}
		}

		onlineSystemReleaseStageDao.save(values);
	}
	
	public Object findStaffArrange(int pageNumber, int pageSize, NaOnlineStaffArrange condition) {

		List<Condition> cons = new ArrayList<Condition>();

		if (condition != null) {
			if (condition.getPlanId() != null) {
				cons.add(new Condition("planId", condition.getPlanId(), Condition.Type.EQ));
			}
			if (condition.getType()!= null) {
				cons.add(new Condition("type", condition.getType(), Condition.Type.EQ));
			}
		}

		if (pageNumber < 0) {
			pageNumber = 0;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);

		return staffArrangeDao.search(cons, pageable);

	}
	public Object findOnlineGeneralSteps (int pageNumber, int pageSize, NaOnlineGeneralSteps condition) {

		List<Condition> cons = new ArrayList<Condition>();

		if (condition != null) {
			if (condition.getPlanId() != null) {
				cons.add(new Condition("planId", condition.getPlanId(), Condition.Type.EQ));
			}
			
		}

		if (pageNumber < 0) {
			pageNumber = 0;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);

		return naOnlineGeneralStepsDao.search(cons, pageable);

	}
	
	
}

