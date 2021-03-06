package com.ai.am.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.am.constant.BusiConstant;
import com.ai.am.dao.NaOnlineTaskDistributeDao;
import com.ai.am.dao.NaOnlineTaskResultDao;
import com.ai.am.dao.NaPlanCaseResultDao;
import com.ai.am.domain.NaPlanCaseResult;
import com.ai.am.exception.BusinessException;
import com.ai.am.exception.ErrorCode;
import com.ai.am.service.base.BaseService;
import com.ai.am.view.json.SubTaskRequest;

/**
 * @ClassName: SubTaskDealSv
 * @author: dongch
 * @date: 2017年4月6日 下午6:57:24
 * @Description:
 * 
 */
@Service
@Transactional
public class SubTaskDealSv extends BaseService{
	
	@Autowired
	private NaOnlineTaskDistributeDao naOnlineTaskDistributeDao;
	
	@Autowired
	private NaPlanCaseResultDao naPlanCaseResultDao;
	
	@Autowired
	private NaOnlineTaskResultDao  naOnlineTaskResultDao;
	/**
	 * @ClassName: SubTaskDealSv :: list
	 * @author: dongch
	 * @date: 2017年4月6日 下午7:12:45
	 *
	 * @Description:
	 * @param condition
	 * @param pageNumber
	 * @param pageSize
	 * @return          
	 */
	public Object list(SubTaskRequest condition, int pageNumber, int pageSize) {
		
		String sql = "select a.task_id, b.task_name as taskName, a.task_name as subTaskName, a.task_type, c.state,"
				+ " to_char(c.create_date,'yyyy-MM-dd HH24:MI:SS'),"
				+ " to_char(c.done_date,'yyyy-MM-dd HH24:MI:SS'),"
				+ " to_char(c.finish_date,'yyyy-MM-dd HH24:MI:SS'),"
				+ " b.online_plan_name, "
				+ " (select e.collect_name from na_auto_collection e where e.collect_id = c.auto_plan_id) as collectName,"
				+ " (select d.name from aiga_staff d where d.staff_id = a.assign_id) as assignName,"
				+ " (select d.name from aiga_staff d where d.staff_id = a.deal_op_id) as dealName"
				+ " from na_online_task_distribute a "
				+ "left join na_online_task_distribute b on  a.parent_task_id = b.task_id "
				+ "left join na_online_task_result c on a.task_id = c.task_id "
				+ "left join na_change_plan_onile d on a.online_plan = d.online_plan "
				+ " where a.parent_task_id <> 0 ";
		
		if(condition.getTaskType() != null){
			sql += " and b.task_type = "+condition.getTaskType();
		}
		if(condition.getOnlinePlan() != null){
			sql += " and a.online_plan = "+condition.getOnlinePlan();
		}
		if(condition.getTaskName() != null&&!"".equals(condition.getSubTaskName())){
			sql += " and b.task_name like '%"+condition.getTaskName()+"%'";
		}
		if(condition.getSubTaskName() != null&&!"".equals(condition.getSubTaskName()) ){
			sql += " and a.task_name like '%"+condition.getSubTaskName()+"%'";
		}
		
		if(condition.getDealState() != null){
			sql += " and c.state = "+condition.getDealState();
		}
		sql += " order by d.plan_date desc";
		 List<String> list = new ArrayList<String>();
		 list.add("taskId");
		 list.add("taskName");
		 list.add("subTaskName");
		 list.add("taskType");
		 list.add("state");
		 list.add("dealDate");
		 list.add("doneDate");
		 list.add("finishDate");
		 list.add("onlinePlanName");
		 list.add("collectName");
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
	}

	/**
	 * @ClassName: SubTaskDealSv :: caseResult
	 * @author: dongch
	 * @date: 2017年4月7日 上午10:07:10
	 *
	 * @Description:
	 * @param taskId
	 * @return          
	 */
	public Object caseResult(Long taskId, int pageNumber, int pageSize) {
		if(taskId == null || taskId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskId");
		}
		String sql = "select b.result_id, "
							+ "       b.case_state,"
							+ " 	       b.result,"
							+ "        b.bug,"
							+ " 	       a.test_name,"
							+ " 	       c.sys_name,"
							+ " 	       d.sys_name   as subSysName,"
							+ " 	       e.sys_name   as funName,"
							+ " 	       a.important,"
							+ " 	       dbms_lob.substr(a.test_desc),"
							+ " 	       dbms_lob.substr(a.pre_result)"
							+ " 	  from  na_plan_case_result b"
							+ " 	  left join na_test_case a "
							+ " 	    on a.test_id = b.case_id"
							+ " 	  left join aiga_system_folder c"
							+ " 	    on a.sys_id = c.sys_id"
							+ "   left join aiga_sub_sys_folder d"
							+ " 	    on  a.sys_sub_id = d.subsys_id "
							+ " 	  left join aiga_fun_folder e"
							+ " 	    on a.fun_id = e.fun_id"
							+ "    where b.sub_task_id = "+taskId;
		
		List<String> list = new ArrayList<String>();
		list.add("resultId");
		list.add("caseState");
		list.add("result");
		list.add("bug");
		list.add("testName");
		list.add("sysName");
		list.add("subSysName");
		list.add("funName");
		list.add("important");
		list.add("testDesc");
		list.add("preResult");
		
		
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
	 * @ClassName: SubTaskDealSv :: caseResultSave
	 * @author: dongch
	 * @date: 2017年4月7日 上午10:22:07
	 *
	 * @Description:
	 * @param list          
	 */
	public void caseResultSave(List<NaPlanCaseResult> list) {
		
		for(int i = 0; i < list.size(); i++){
			NaPlanCaseResult request = list.get(i);
			NaPlanCaseResult result = naPlanCaseResultDao.findOne(request.getResultId());
			if(request.getResult() != null){
				result.setResult(request.getResult());
			}
			if(request.getBug() != null){
				result.setBug(request.getBug());
			}
			//修改用例状态为已处理
			result.setCaseState((byte)1);
			naPlanCaseResultDao.save(result);
			//修改子任务状态是处理中/处理完
			Object  count = naPlanCaseResultDao.findCountBySubTaskId(result.getSubTaskId());
			if(!"0".equals(count.toString())){
				//处理中
				naOnlineTaskDistributeDao.updateSubTaskDealState(result.getSubTaskId(),2L);
				naOnlineTaskResultDao.updateParentTaskDealState(result.getSubTaskId(), 1L);
			}else{
				//处理完
				naOnlineTaskDistributeDao.updateSubTaskDealState(result.getSubTaskId(),3L);
				naOnlineTaskResultDao.updateParentTaskDealState(result.getSubTaskId(), 2L);
			}
			//修改父任务状态为处理完
			Object count2 = naPlanCaseResultDao.findCountSubTask(result.getSubTaskId());
			if("0".equals(count.toString())){
				naOnlineTaskDistributeDao.updateTaskDealState(result.getSubTaskId());
			}
			
		}

	}

	/**
	 * @ClassName: SubTaskDealSv :: autoResult
	 * @author: dongch
	 * @date: 2017年4月7日 下午1:15:04
	 *
	 * @Description:
	 * @param taskId
	 * @param pageNumber
	 * @param pageSize
	 * @return          
	 */
	public Object autoResult(Long taskId, int pageNumber, int pageSize) {
		if(taskId == null || taskId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskId");
		}
		String sql = " select b.result_id,"
				    	+"	      b.case_state, "
						 	+"	           b.result,"
						 	+"	           b.bug,"
							+"	            a.auto_name,"
							+"	            c.sys_name,"
							+"	            d.sys_name    as subSysName,"
							+"	            e.sys_name    as funName,"
							+"	            a.important,"
						 	+"	           b.auto_code,"
							+"	            b.auto_result"
						  	+"	     from na_plan_case_result b"
						   	+"	    left join na_auto_case a"
						    +"	     on a.auto_id = b.case_id"
						  	+"	     left join aiga_system_folder c"
						 	+"	        on a.sys_id = c.sys_id"
						  	+"	     left join aiga_sub_sys_folder d"
						   	+"	      on a.sys_sub_id = d.subsys_id"
						   	+"	    left join aiga_fun_folder e"
					    	+"	 	     on a.fun_id = e.fun_id"
						    +"	  where b.sub_task_id =  "+taskId;
		
		List<String> list = new ArrayList<String>();
		list.add("resultId");
		list.add("caseState");
		list.add("result");
		list.add("bug");
		list.add("autoName");
		list.add("sysName");
		list.add("subSysName");
		list.add("funName");
		list.add("important");
		list.add("autoCode");
		list.add("autoResult");
		
		
		 if(pageNumber < 0){
				pageNumber = 0;
			}
			
		 if(pageSize <= 0){
				pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
			}
		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return naOnlineTaskDistributeDao.searchByNativeSQL(sql, pageable, list);
	}

}

