package com.ai.aiga.service;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.NaPlanCaseResultExtDao;
import com.ai.aiga.dao.NaPlanCaseResultExtSumDao;
import com.ai.aiga.domain.NaPlanCaseResultExpSum;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.view.json.PlanCaseResultExpRequest;
import com.ai.aiga.view.json.PlanCaseResultExpSumRequest;
import com.ai.process.jta.DatabaseContextHolder;
import com.ai.process.jta.service.AbstractJatService;

/**
 * 性能子任务处理
 * @author Liuxx
 *@date 2017-04-10
 */
@Service
@Transactional
public class PlanCaseResultExpSv  {

	@Autowired
	private NaPlanCaseResultExtDao extDao;
	
	@Autowired
	private NaPlanCaseResultExtSumDao sumDao;
	
	
	/**
	 *  保存性能测试执行结果汇总表的测试人员
	 * @param result
	 */
	public  void  saveOperatId(List<PlanCaseResultExpSumRequest> result){
		if(result==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
		}
		for (PlanCaseResultExpSumRequest data : result) {
			sumDao.saveOperatId(data.getOperatId(), data.getResultId());
		}
	}
	
	
	/**
	 * 保存明细表的备注字段
	 * @param result
	 */
	public  void  saveRemark(List<PlanCaseResultExpRequest> result){
		if(result==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_com_null);
		}
		for (PlanCaseResultExpRequest data : result) {
			extDao.saveRemark(data.getRemark(), data.getResultId());
		}
	}
	
	
	/**
	 * 查询当前性能测试子任务执行汇总结果
	 * @param taskId
	 * @return
	 */
	public List<NaPlanCaseResultExpSum> getNaPlanCaseResultExtSum(Long taskId){
		return sumDao.findBySubTaskId(taskId);
	}
	
	
	/**
	 * 查看当前性能测试子任务的执行结果
	 * @param taskId 子任务编号
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Object getNaPlanCaseResultExt(Long taskId, int pageNumber, int pageSize){
		if(taskId==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null+"taskId");
		}
		if(pageNumber<0){
			pageNumber=0;
		}
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}
		Pageable pageable = new PageRequest(pageNumber, pageSize);
		String querySql = 
				"select a.result_id, a.inter_id, a.infcode, a.infname, a.ver, to_char(a.exdate, 'yyyy-MM-dd'), to_char(a.exstarttime, 'yyyy-MM-dd'),\n" +
				"       to_char(a.exendtime, 'yyyy-MM-dd'), a.durtime,a.Isnew, a.curnum, a.scene, a.testtimes, a.rtmin, a.rtavg, a.Rtmax,\n" + 
				"       a.std_deviation, a.rt90_percent, a.lrpass, a.lrfail, a.lrstop, a.tpsmin, a.tpsavg, a.tpsmax,\n" + 
				"       a.throughput, a.cpuusagemax, a.memusagemax, a.functionextime, a.sqlextime,a.conclusion, a.failreason, a.infcallvolumes,\n" + 
				"       a.prodstd, a.accstd, a.testevn, a.remark\n" + 
				"   from na_plan_case_result_exp a\n" + 
				"  where a.sub_task_id = " + taskId;
			List<String>  resultList = new ArrayList<String>();
			resultList.add("resultId");
			resultList.add("interId");
			resultList.add("infCode");
			resultList.add("infName");
			resultList.add("ver");
			resultList.add("exdate");
			resultList.add("exStartTime");
			resultList.add("exEndTime");
			resultList.add("durtime");
			resultList.add("isNew");
			resultList.add("curnum");
			resultList.add("scene");
			resultList.add("testTimes");
			resultList.add("rtmin");
			resultList.add("rtavg");
			resultList.add("rtmax");
			resultList.add("stdDeviation");
			resultList.add("rt9Percent");
			resultList.add("lrpass");
			resultList.add("lrfail");
			resultList.add("lrstop");
			resultList.add("tpsmin");
			resultList.add("tpsavg");
			resultList.add("tpsmax");
			resultList.add("throughput");
			resultList.add("cpuusagemax");
			resultList.add("memusagemax");
			resultList.add("functionextime");
			resultList.add("sqlextime");
			resultList.add("conclusion");
			resultList.add("failreason");
			resultList.add("infcallvolumes");
			resultList.add("prodstd");
			resultList.add("accstd");
			resultList.add("testevn");
			resultList.add("remark");
		return extDao.searchByNativeSQL(querySql, pageable, resultList);
	}
	
	
  
	/**
	 * 删除性能测试子任务明细表记录
	 * @param resultId  主键
	 */
	public void deleteNaPlanCaseResultExt(String resultId, Long taskId){
		if(resultId==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null+"resultId");
		}
		String ids[]=resultId.split(",");
		//删除明细
		for (String id : ids) {
			extDao.delete(Long.parseLong(id));
		}
		//修改汇总表的数据
		String sumSql = 
				"select a.inter_id,\n" + 
				"       b.totalTime,\n" + 
				"       a.bfproddata,\n" + 
				"       a.conclusion\n" + 
				"  from na_plan_case_result_exp a,\n" + 
				"       (select t.sub_task_id as subTaskId,\n" + 
				"               t.inter_id as interId,\n" + 
				"               sum(nvl(durtime,0)) as totalTime,\n" + 
				"               max(nvl(testtimes, 0)) as maxTimes\n" + 
				"          from na_plan_case_result_exp t\n" + 
				"         group by t.sub_task_id, t.inter_id\n" + 
				"        having t.sub_task_id = " + taskId + ") b\n" + 
				" where a.sub_task_id = b.subTaskId\n" + 
				"   and a.inter_id = b.interId\n" + 
				"   and a.testtimes = b.maxTimes";
		List<Object>  results= extDao.searchformSQL(sumSql);
		List<NaPlanCaseResultExpSum>  sums = sumDao.findBySubTaskId(taskId);
		if(results!=null&&!results.isEmpty()){
			for (Object result : results) {
				Object[] data = (Object[])result;
				for(NaPlanCaseResultExpSum sum:sums ){
					if(sum.getInterId()==Long.parseLong(data[0].toString())){
						sum.setTotalTime(Long.parseLong(data[1].toString()));
						sum.setBfprodData(data[2].toString());
						sum.setRunResult(data[3].toString());
						sum.setCaseState(1L);
						sum.setDoneDate(new Date());
						sumDao.save(sum);
					}
				}
			}
		}
		 
		
	}
	
	
	public void copyDataFromCSHP03(Long  taskId){
		Connection con =null;
		//查询新炬数据库数据sql
		String querySql = 
				"select INFCODE,INFNAME,VER,TO_CHAR(EXDATE, 'yyyy-mm-dd'),TO_CHAR(EXSTARTTIME, 'yyyy-mm-dd HH24:mi:ss'),TO_CHAR(EXENDTIME, 'yyyy-mm-dd HH24:mi:ss'),DURTIME,ISNEW,CURNUM,SCENE,TESTTIMES,RTMIN,RTAVG,RTMAX,STD_DEVIATION,RT90_PERCENT,LRPASS,LRFAIL,LRSTOP,\n" +
				"       TPSMIN,TPSAVG,TPSMAX,THROUGHPUT,CPUUSAGEMAX,MEMUSAGEMAX,FUNCTIONEXTIME,SQLEXTIME,CONCLUSION,FAILREASON,BFPRODDATA,AFPRODDATA,ISMATCHING,INFCALLVOLUMES,\n" + 
				"       PRODSTD,ACCSTD,TESTEVN,REMARK,ONLINEDEMAND,ONLINEDEMANDNAME,TO_CHAR(IMPORTTIME, 'yyyy-mm-dd HH24:mi:ss')\n" + 
				" from SHSNC_XNCS.REPORT_RESULTS t where t.infcode is not null";
		//将数据移到历史表sql
		String insertSql = " insert into SHSNC_XNCS.REPORT_RESULTS_HIS select * from SHSNC_XNCS.REPORT_RESULTS";
		//数据删除sql
		String deleteSql = " delete from SHSNC_XNCS.REPORT_RESULTS";
		//更新任务子任务表处理状态字段
		String updateSql=" update na_online_task_distribute p set p.deal_state = (select case when count(*) = 0 then 2 else 1 end from na_online_task_result t where t.state <> 2 and task_id in (select task_id from na_online_task_distribute b where b.parent_task_id = (select a.parent_task_id from na_online_task_distribute a where a.task_id = " + taskId + "))) where p.task_id = (select o.parent_task_id from na_online_task_distribute o where o.task_id = " + taskId + ")";
		DatabaseContextHolder.setDBType(DatabaseContextHolder.DATASOURCE_CSHP03);
		
	}
	
	
}
