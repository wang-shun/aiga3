package com.ai.aiga.service.home;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.NaCaseImplReportDao;
import com.ai.aiga.dao.NaChangePlanOnileDao;
import com.ai.aiga.dao.NaProcessNodeRecordDao;
import com.ai.aiga.domain.NaCaseImplReport;
import com.ai.aiga.domain.NaChangePlanOnile;
import com.ai.aiga.domain.NaProcessNodeRecord;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.home.dto.CaseCountResponse;
import com.ai.aiga.service.home.dto.NetInfoResponse;
import com.ai.aiga.service.home.dto.ProcessNodeResponse;

import io.swagger.models.properties.ObjectProperty;
import oracle.net.aso.a;

/**
 * @ClassName: HomeDataSv
 * @author: dongch
 * @date: 2017年5月3日 下午3:54:16
 * @Description:
 * 
 */
@Service
@Transactional
public class HomeDataSv {
	
	@Autowired
	private NaCaseImplReportDao naCaseImplReportDao;
	
	@Autowired
	private NaProcessNodeRecordDao naProcessNodeRecordDao;
	
	@Autowired
	private NaChangePlanOnileDao naChangePlanOnileDao;
	/**
	 * @ClassName: HomeDataSv :: caseCount
	 * @author: dongch
	 * @date: 2017年5月3日 下午4:26:38
	 *
	 * @Description:首页用例数统计
	 * @return          
	 */
	public List<CaseCountResponse> caseCount(String planDate) {
		
		List<Object> list = naCaseImplReportDao.findOnlinePlan(planDate);
		Object[] objects = (Object[])list.get(0);
		List<CaseCountResponse> responses = new ArrayList<CaseCountResponse>();
		NaCaseImplReport report = naCaseImplReportDao.findByOnlinePlanIdAndEnvironment(((BigDecimal)objects[0]).longValue(),2L);
		CaseCountResponse response = new CaseCountResponse();
		response.setCheckCaseTotalCount(report.getCaseTotalCount());
		response.setCheckSucRate(report.getFirSucRate());
		responses.add(response);
		report = naCaseImplReportDao.findByOnlinePlanIdAndEnvironment(((BigDecimal)objects[0]).longValue(),3L);
		response.setCheckCaseTotalCount(report.getCaseTotalCount());
		response.setCheckSucRate(report.getFirSucRate());
		responses.add(response);
		
		return responses;
	}

	/**
	 * @ClassName: HomeDataSv :: flowText
	 * @author: dongch
	 * @date: 2017年5月4日 上午11:16:06
	 *
	 * @Description:流程节点对应文字内容统计
	 * @param planDate          
	 */
	public void flowText(Long onlinePlan) {
		
		if(onlinePlan == null || onlinePlan < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "onlinePlan");
		}
		
		NaProcessNodeRecord record = naProcessNodeRecordDao.findByPlanIdAndNode(onlinePlan, 1L);
		//变更开始节点
		Object count1 = naProcessNodeRecordDao.system(onlinePlan);
		Object count2 = naProcessNodeRecordDao.require(onlinePlan);
		record.setExt1("本次上线/变更共"+count2.toString()+"个需求，"+count1.toString()+"个系统");
		//交付物评审节点
		NaChangePlanOnile online = naChangePlanOnileDao.findOne(onlinePlan);
		record = naProcessNodeRecordDao.findByPlanIdAndNode(onlinePlan, 2L);
		count1 = naProcessNodeRecordDao.pack(online.getPlanDate().toString());
		count2 = naProcessNodeRecordDao.file(onlinePlan);
		record.setExt1("本次上线共收到"+count2.toString()+"个交付物，"+count1.toString()+"个代码包");
		//验收情况节点
		record = naProcessNodeRecordDao.findByPlanIdAndNode(onlinePlan, 4L);
		count1 = naProcessNodeRecordDao.auto(1L,1L,onlinePlan);//任务类型、环境、计划Id
		count2 = naProcessNodeRecordDao.test(1L,onlinePlan);//任务类型、计划Id
		Integer totalCount = Integer.valueOf(count1.toString()) + Integer.valueOf(count2.toString());//前台功能验收用例数
		Object time1 = naProcessNodeRecordDao.time(1L, onlinePlan);//前台功能验收耗时
		Object time2 = naProcessNodeRecordDao.time2(onlinePlan);//后台功能验收耗时
		count1 = naProcessNodeRecordDao.auto(3L,1L,onlinePlan);
		count2 = naProcessNodeRecordDao.test(3L,onlinePlan);
		Integer totalCount2 = Integer.valueOf(count1.toString()) + Integer.valueOf(count2.toString());
		Object time3 = naProcessNodeRecordDao.time(3L, onlinePlan);//非功能验收耗时
		record.setExt1("本次上线前台验收"+totalCount.toString()+"用例，耗时"+time1.toString()+"小时，后台功能验收耗时"+time2.toString()+"小时，非功能验收"+totalCount2.toString()+"用例，耗时"+time3.toString()+"小时");
		//生产验证节点
		record = naProcessNodeRecordDao.findByPlanIdAndNode(onlinePlan, 7L);
		count1 = naProcessNodeRecordDao.auto(1L,3L,onlinePlan);//任务类型、环境、计划Id
		count2 = naProcessNodeRecordDao.test(1L,onlinePlan);//任务类型、计划Id
		totalCount = Integer.valueOf(count1.toString()) + Integer.valueOf(count2.toString());//前台功能验收用例数
		time1 = naProcessNodeRecordDao.time(1L, onlinePlan);//前台功能验收耗时
		time2 = naProcessNodeRecordDao.time2(onlinePlan);//后台功能验收耗时
		count1 = naProcessNodeRecordDao.auto(3L,3L,onlinePlan);
		count2 = naProcessNodeRecordDao.test(3L,onlinePlan);
		totalCount2 = Integer.valueOf(count1.toString()) + Integer.valueOf(count2.toString());
		time3 = naProcessNodeRecordDao.time(3L, onlinePlan);//非功能验收耗时
		record.setExt1("本次上线前台验收"+totalCount.toString()+"用例，耗时"+time1.toString()+"小时，后台功能验收耗时"+time2.toString()+"小时，非功能验收"+totalCount2.toString()+"用例，耗时"+time3.toString()+"小时");
		
	}

	
	public List<ProcessNodeResponse>  flowList(String planDate) {
		List<ProcessNodeResponse> responses = new ArrayList<ProcessNodeResponse>();
		long count = naProcessNodeRecordDao.findPlan(planDate);
		List<long[]> list = naProcessNodeRecordDao.plan(planDate);
		for(int i = 0; i < count; i++){
			long [] a = list.get(i);
			ProcessNodeResponse response = new ProcessNodeResponse();
			List<NaProcessNodeRecord> records = naProcessNodeRecordDao.findByPlanId(a[0]);
			response.setMaxNum(Long.valueOf(count));
			response.setActiveNum(i);
			response.setId(10000+i);
			response.setFlowLIst(records);
			
			responses.add(response);
		}
		return responses;
		
	}

	/**
	 * @ClassName: HomeDataSv :: information
	 * @author: dongch
	 * @date: 2017年5月5日 下午3:46:14
	 *
	 * @Description:近半年入网信息分析
	 * @return          
	 */
	public List<NetInfoResponse> information() {
		SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 0);
		cal.set(Calendar.DATE, 1);
		String date1 = sdf.format(cal.getTime());
		cal.add(Calendar.MONTH, +1);
		String date2 = sdf.format(cal.getTime());
		
		List<NetInfoResponse> list = new ArrayList<NetInfoResponse>();
		
		NetInfoResponse response = new NetInfoResponse();

		long count = naProcessNodeRecordDao.countPlan(date1, date2);
		response.setOnlinePlanCount(count);
		count= naProcessNodeRecordDao.countAbnormal(1L,date1, date2);
		response.setAbnormalCount(count);
		count = naProcessNodeRecordDao.countAbnormal(2L,date1, date2);
		response.setFaultCount(count);
		response.setMonth(date1.substring(5, 7));
		
		list.add(response);
		
		cal.add(Calendar.MONTH, -1);
		date2 = sdf.format(cal.getTime());
		cal.add(Calendar.MONTH, -1);//4
		date1 = sdf.format(cal.getTime());
		NetInfoResponse response1 = new NetInfoResponse();
		count = naProcessNodeRecordDao.countPlan(date1, date2);
		response1.setOnlinePlanCount(count);
		count = naProcessNodeRecordDao.countAbnormal(1L,date1, date2);
		response1.setAbnormalCount(count);
		count = naProcessNodeRecordDao.countAbnormal(2L,date1, date2);
		response1.setFaultCount(count);
		response1.setMonth(date1.substring(5, 7));
		
		list.add(response1);
		for(int i = 0; i < 4; i++){
			cal.add(Calendar.MONTH, -1);
			date2 = date1;
			date1 = sdf.format(cal.getTime());//3
			NetInfoResponse response2 = new NetInfoResponse();
			count = naProcessNodeRecordDao.countPlan(date1, date2);
			response2.setOnlinePlanCount(count);
			count = naProcessNodeRecordDao.countAbnormal(1L,date1, date2);
			response2.setAbnormalCount(count);
			count = naProcessNodeRecordDao.countAbnormal(2L,date1, date2);
			response2.setFaultCount(count);
			response2.setMonth(date1.substring(5, 7));
			
			list.add(response);
		}
		
		
		return list;
	}

}

