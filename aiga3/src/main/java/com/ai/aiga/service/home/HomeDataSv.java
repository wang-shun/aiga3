package com.ai.aiga.service.home;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.NaCaseImplReportDao;
import com.ai.aiga.dao.NaChangePlanOnileDao;
import com.ai.aiga.dao.NaHomewInfoDao;
import com.ai.aiga.dao.NaIndexAllocationDao;
import com.ai.aiga.dao.NaOnlineTaskDistributeDao;
import com.ai.aiga.dao.NaProcessNodeRecordDao;
import com.ai.aiga.dao.NaStaffKpiRelaDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.domain.NaCaseImplReport;
import com.ai.aiga.domain.NaChangePlanOnile;
import com.ai.aiga.domain.NaHomewInfo;
import com.ai.aiga.domain.NaIndexAllocation;
import com.ai.aiga.domain.NaOnlineTaskDistribute;
import com.ai.aiga.domain.NaProcessNodeRecord;
import com.ai.aiga.domain.NaStaffKpiRela;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.home.dto.CaseCountResponse;
import com.ai.aiga.service.home.dto.NetInfoResponse;
import com.ai.aiga.service.home.dto.ProcessNodeResponse;
import com.ai.aiga.util.DateUtil;
import com.ai.aiga.util.MathUtils;
import com.ai.aiga.view.util.SessionMgrUtil;

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
	private  NaProcessNodeRecordDao naProcessNodeRecordDao;

	@Autowired
	private NaChangePlanOnileDao naChangePlanOnileDao;

	@Autowired
	private NaStaffKpiRelaDao naStaffKpiRelaDao;

	@Autowired
	private NaIndexAllocationDao naIndexAllocationDao;

	@Autowired
	NaHomewInfoDao naHomewInfoDao;
	@Autowired
	private  NaOnlineTaskDistributeDao naOnlineTaskDistributeDao;

	/**
	 * @ClassName: HomeDataSv :: caseCount
	 * @author: dongch
	 * @date: 2017年5月3日 下午4:26:38
	 *
	 * @Description:首页用例数统计
	 * @return
	 */

	public List<NaIndexAllocation> kpiList() {
		//获取当前用户关联的指标，但是不包括指标的具体信息，只有id
		List<NaStaffKpiRela> relaList = naStaffKpiRelaDao.findByStaffIdAndState(SessionMgrUtil.getStaff().getStaffId(),
				1L);
		//初始化全部的指标默认不显示
		naIndexAllocationDao.update();
		//获取全部的指标的具体信息
		List<NaIndexAllocation> kpiList = naIndexAllocationDao.findAll();
		//如果当前用户有指标
		if (relaList != null && relaList.size() > 0) {
			for (NaStaffKpiRela rela : relaList) {
				for (int i = 0; i < kpiList.size(); i++) {
					NaIndexAllocation kpi = kpiList.get(i);
					//找到当前用户的指标id
					if (kpi.getKpiId() == rela.getKpiId()) {
						//将执行当前用户的指标的sql语句，语句返回的key=kpiName的map
						List<Map> list = naIndexAllocationDao.searchByNativeSQL(kpi.getKpiSql().toString());
						//将指标的value设置成sql返回的map的value值
						Map<String, Object> map = list.get(0);
						Object object = map.get(kpi.getKpiName());
						kpi.setValue(MathUtils.getBigDecimal(object));
						//设置当前用户的指标显示
						kpi.setIsShow(1L);
					}
				}

			}
		}else{   //如果当前用户没有指标，加载默认
			List<Condition> cons = new ArrayList<Condition>();
			cons.add(new Condition("isDefault", 1, Condition.Type.EQ));
			kpiList=naIndexAllocationDao.search(cons );
			for (int i = 0; i < kpiList.size(); i++) {
				NaIndexAllocation kpi = kpiList.get(i);
				List<Map> list = naIndexAllocationDao.searchByNativeSQL(kpi.getKpiSql().toString());
				Map<String, Object> map = list.get(0);
				Object object = map.get(kpi.getKpiName());
				kpi.setValue(MathUtils.getBigDecimal(object));
				kpi.setIsShow(1L);
			}
		}
		return kpiList;
	}

	/**
	 * @ClassName: HomeDataSv :: flowText
	 * @author: dongch
	 * @date: 2017年5月4日 上午11:16:06
	 *
	 * @Description:流程节点对应文字内容统计
	 * @param planDate
	 */
	public void flowText() {

		List<NaChangePlanOnile> onlinePlans = naChangePlanOnileDao.findByPlanState();
		if (onlinePlans != null && onlinePlans.size() > 0) {
			for (NaChangePlanOnile onlinePlan : onlinePlans) {
				Object reqCount = null;
				Object sysCount = null;
				NaProcessNodeRecord record = naProcessNodeRecordDao.findByPlanIdAndNode(onlinePlan.getOnlinePlan(), 1L);
				if (record != null) {
					// 变更开始节点
					sysCount	 = naProcessNodeRecordDao.system(onlinePlan.getOnlinePlan()) == null ? 0
							: naProcessNodeRecordDao.system(onlinePlan.getOnlinePlan());
					reqCount = naProcessNodeRecordDao.require(onlinePlan.getOnlinePlan()) == null ? 0
							: naProcessNodeRecordDao.require(onlinePlan.getOnlinePlan());
					record.setExt1("本次上线/变更共" +sysCount.toString() + "个系统，" + reqCount.toString()  + "个需求");
					naProcessNodeRecordDao.save(record);
				}
				record = naProcessNodeRecordDao.findByPlanIdAndNode(onlinePlan.getOnlinePlan(), 2L);
				if (record != null) {
					// 交付物评审节点
					this.UpadateOnlineDeliverableReview(onlinePlan, record);
				}
				// 计划上线编译发布节点
				record = naProcessNodeRecordDao.findByPlanIdAndNode(onlinePlan.getOnlinePlan(), 3L);
				if (record != null) {
					this.UpadateOnlineComplie(onlinePlan, record);
				}
				record = naProcessNodeRecordDao.findByPlanIdAndNode(onlinePlan.getOnlinePlan(), 4L);
				if (record != null) {
					this.UpadateOnlineTest(onlinePlan, record);
				}
				record = naProcessNodeRecordDao.findByPlanIdAndNode(onlinePlan.getOnlinePlan(), 5L);
				if (record != null) {
					this.UpadateOnlineChangeReview(onlinePlan, record);
				}
				record = naProcessNodeRecordDao.findByPlanIdAndNode(onlinePlan.getOnlinePlan(), 6L);
				if (record != null) {
					this.UpadateOnlineChangerRelsease(onlinePlan, record);
				}
				record = naProcessNodeRecordDao.findByPlanIdAndNode(onlinePlan.getOnlinePlan(), 7L);
				if (record != null) {
					this.UpadateOnlineSTest(onlinePlan, record);
				}
				// record =
				// naProcessNodeRecordDao.findByPlanIdAndNode(onlinePlan.getOnlinePlan(),
				// 8L);//上线总结节点

			}
		}

	}
	
	/**
	 * 交付评审
	 * @param onlinePlan
	 * @param record
	 */
	
	private  void  UpadateOnlineDeliverableReview(NaChangePlanOnile onlinePlan,NaProcessNodeRecord record){
		String deliverableCount = "";
		String requireCount = "";
		//查询当前任务是否已经在处理中了
		if(record.getType()==3 ||record.getType()==4){
			record.setExt1(" ");
		}else{
		deliverableCount =  naProcessNodeRecordDao.pack(onlinePlan.getOnlinePlan()).toString();
		requireCount = naProcessNodeRecordDao.file(onlinePlan.getOnlinePlan()) == null ? "0"
				: naProcessNodeRecordDao.file(onlinePlan.getOnlinePlan()).toString();
		record.setExt1("本次上线共收到" +requireCount.toString() + "个交付物，" + deliverableCount+ "个代码包");
		}
		naProcessNodeRecordDao.save(record);
	}
	

	/**
	 * 交付评审
	 * @param onlinePlan
	 * @param record
	 */
	
	private  void  UpadateOnlineChangerRelsease(NaChangePlanOnile onlinePlan,NaProcessNodeRecord record){
		String sysCount = "";
		String successedCount = "";
		String totalTime = "";
		//查询当前任务是否已经在处理中了
		if(record.getType()==3 ||record.getType()==4){
			record.setExt1(" ");
		}else{
			sysCount = naProcessNodeRecordDao.report(onlinePlan.getOnlinePlan())== null ?" 0":naProcessNodeRecordDao.report(onlinePlan.getOnlinePlan()).toString();
			totalTime = naProcessNodeRecordDao.reportTime(onlinePlan.getOnlinePlan()) == null ?" 0"
					: naProcessNodeRecordDao.reportTime(onlinePlan.getOnlinePlan()).toString();
			record.setExt1("本次上线发布" + sysCount + "个系统，已发布" + sysCount + "个"
					+"，总耗时" + sysCount + "小时");
		}
		naProcessNodeRecordDao.save(record);
	}
	
	
	
	/**
	 * 变更评审
	 * @param onlinePlan
	 * @param record
	 */
	
	private  void  UpadateOnlineChangeReview(NaChangePlanOnile onlinePlan,NaProcessNodeRecord record){
		String requCount = "";
		String successedCount = "";
		String unsuccees = "";
		//查询当前任务是否已经在处理中了
		if(record.getType()==3 ||record.getType()==4){
			record.setExt1(" ");
		}else{
			requCount = naProcessNodeRecordDao.reqNo(onlinePlan.getOnlinePlan())==null?"0":naProcessNodeRecordDao.reqNo(onlinePlan.getOnlinePlan()).toString();
			successedCount = naProcessNodeRecordDao.reqNoTwo(onlinePlan.getOnlinePlan(), 1L)==null?"0":naProcessNodeRecordDao.reqNoTwo(onlinePlan.getOnlinePlan(), 1L).toString();
			unsuccees = naProcessNodeRecordDao.reqNoTwo(onlinePlan.getOnlinePlan(), 2L)==null?"0":naProcessNodeRecordDao.reqNoTwo(onlinePlan.getOnlinePlan(), 2L).toString();
			record.setExt1("本次上线" + requCount+ "个需求，评审通过" + successedCount + "个，退回"
					+ unsuccees + "个");
		}
		naProcessNodeRecordDao.save(record);
	}
	
	
	/**
	 * 编译情况
	 * @param onlinePlan
	 * @param record
	 */
	private  void  UpadateOnlineComplie(NaChangePlanOnile onlinePlan,NaProcessNodeRecord record){
		String sysCount = "";
		String releaseCount = "";
		String failedCount = "";
		String timeAll = "";
		//查询当前任务是否已经在处理中了
		if(record.getType()==3||record.getType()==4){
			record.setExt1(" ");
		}else{
			sysCount = naProcessNodeRecordDao.sysSum(onlinePlan.getOnlinePlan())==null?"0": naProcessNodeRecordDao.sysSum(onlinePlan.getOnlinePlan()).toString();// 系统总数
			releaseCount = naProcessNodeRecordDao.sysNot(onlinePlan.getOnlinePlan())==null?"0":naProcessNodeRecordDao.sysNot(onlinePlan.getOnlinePlan()).toString();// 已发布
			failedCount = naProcessNodeRecordDao.sysFail(onlinePlan.getOnlinePlan())==null?"0":naProcessNodeRecordDao.sysFail(onlinePlan.getOnlinePlan()).toString();// 失败
			timeAll = naProcessNodeRecordDao.spendTime(onlinePlan.getOnlinePlan())==null?"0":naProcessNodeRecordDao.spendTime(onlinePlan.getOnlinePlan()).toString();
			record.setExt1("本次上线发布" + sysCount.toString()+ "个系统，已发布"
					+  releaseCount + "个，失败"
					+  failedCount.toString()+ "个,总耗时"
					+  timeAll.toString() + "小时");
		}
		naProcessNodeRecordDao.save(record);
	}
	
	
	private  void  UpadateOnlineTest(NaChangePlanOnile onlinePlan,NaProcessNodeRecord record){
		String caseCount= ""; 
		String qtTime = "";
		String htTime = "";
		String interfaceCount = "";//非功能接口数
		String fTime = ""; //非公能验收耗时
		Date finish;//非功能：子任务最晚完成时间；功能：最后一个用例有结果的时间
		Date create;//非功能：任务开始时间；功能：第一个自动化用例有结果的时间
		//查询当前任务是否已经在处理中了
		if(record.getType()==3 ||record.getType()==4){
			record.setExt1(" ");
		}else{
			// 验收情况节点
			StringBuilder content = new StringBuilder("");
			List<NaOnlineTaskDistribute>  tasks =naOnlineTaskDistributeDao.findByOnlinePlannAndParentTaskId(onlinePlan.getOnlinePlan());
			if(tasks.size()>0){
			content.append("本次上线");
			for (NaOnlineTaskDistribute task : tasks) {
		
				//前台
				if(task.getTaskType() ==1L ){
					
					caseCount = naProcessNodeRecordDao.caseCount(1L, onlinePlan.getOnlinePlan())==null?"0":naProcessNodeRecordDao.caseCount( 1L, onlinePlan.getOnlinePlan()).toString();// 任务类型、环境、计划Id

					finish = naProcessNodeRecordDao.funTimeFinish(1L,onlinePlan.getOnlinePlan());
					create = naProcessNodeRecordDao.funTimeCreate(1L,onlinePlan.getOnlinePlan());
					qtTime = timeConsum(create,finish);
					content.append("前台验收" + caseCount+ "用例, 耗时" + qtTime + "小时,");
				}else if(task.getTaskType() ==2L){
					
					finish = naProcessNodeRecordDao.funTimeFinish(2L,onlinePlan.getOnlinePlan());
					create = naProcessNodeRecordDao.funTimeCreate(2L,onlinePlan.getOnlinePlan());
					htTime = timeConsum(create,finish);
					content.append("后台功能验收耗时"		+ htTime+ "小时,");
					
				}else if(task.getTaskType() ==3L){
//					qtfCaseCountAuto = naProcessNodeRecordDao.auto(3L, 1L, onlinePlan.getOnlinePlan())==null?"0":naProcessNodeRecordDao.auto(3L, 1L, onlinePlan.getOnlinePlan()).toString();
//					qtfCaseCount = naProcessNodeRecordDao.test(3L, onlinePlan.getOnlinePlan())==null?"0":naProcessNodeRecordDao.test(3L, onlinePlan.getOnlinePlan()).toString();
//					fTotalCount = Integer.valueOf(qtfCaseCountAuto) + Integer.valueOf(qtfCaseCount);
					interfaceCount = naProcessNodeRecordDao.interFace(3L,onlinePlan.getOnlinePlan()) == null ? "0":naProcessNodeRecordDao.interFace(3L,onlinePlan.getOnlinePlan()).toString();
//					fTime = naProcessNodeRecordDao.time(3L, onlinePlan.getOnlinePlan()) == null ? "0"
//							: naProcessNodeRecordDao.time(3L, onlinePlan.getOnlinePlan()).toString();// 非功能验收耗时
					finish = naProcessNodeRecordDao.timeFinish(3L,onlinePlan.getOnlinePlan());
					create = naProcessNodeRecordDao.timeCreate(3L,onlinePlan.getOnlinePlan());
					fTime = timeConsum(create,finish);
					content.append("非功能验收" +interfaceCount + "接口, 耗时" + fTime+ "小时");
				}
			}
			}
			record.setExt1(content.toString());
		}
		naProcessNodeRecordDao.save(record);
	}
	
	//计算耗时
	private String timeConsum(Date create, Date finish) {
		// TODO Auto-generated method stub
		if(create != null && finish != null){
			long day = (finish.getTime() - create.getTime())/(60*60*1000);
			if (day < 0) {
				day = 0;
			}
			return String.valueOf(day);
		}else{
			return "0";
		}
		
		
	}

	private  void  UpadateOnlineSTest(NaChangePlanOnile onlinePlan,NaProcessNodeRecord record){
		//String qtCaseCount = "";
		String qtCaseCountAuto = "";
		//String failedCount = "";
		//String timeAll = "";
		//Integer totalCount = 0;
		String qtTime = "";
		String htTime = "";
		String qtfCaseCount = "";//非公能手工
		String qtfCaseCountAuto = "";//非公能自动化
		Integer fTotalCount = 0; //非公能用例总数
		String fTime = ""; //非公能验收耗时
		Date finish;//子任务最晚完成时间
		Date create;//任务开始时间
		//查询当前任务是否已经在处理中了
		if(record.getType()==3 || record.getType()==4 ){
			record.setExt1(" ");
		}else{
			// 验收情况节点
			StringBuilder content = new StringBuilder("");
			List<NaOnlineTaskDistribute>  tasks =naOnlineTaskDistributeDao.findByOnlinePlannAndParentTaskId(onlinePlan.getOnlinePlan());
			if(tasks.size()>0){
				content.append("本次上线");
				for (NaOnlineTaskDistribute task : tasks) {
	
					if(task.getTaskType() ==4L ){
					// 生产验证节点
					qtCaseCountAuto = naProcessNodeRecordDao.caseCount( 4L, onlinePlan.getOnlinePlan())==null?"0":naProcessNodeRecordDao.caseCount(4L, onlinePlan.getOnlinePlan()).toString();// 任务类型、环境、计划Id
	

					finish = naProcessNodeRecordDao.funTimeFinish(4L,onlinePlan.getOnlinePlan());
					create = naProcessNodeRecordDao.funTimeCreate(4L,onlinePlan.getOnlinePlan());
					qtTime = timeConsum(create,finish);
	
					content.append("前台验收" +qtCaseCountAuto + "用例，耗时" + qtTime + "小时,");
					}else if(task.getTaskType() ==5L ){
						htTime = naProcessNodeRecordDao.time2(onlinePlan.getOnlinePlan()) == null ? "0"
								: naProcessNodeRecordDao.time2(onlinePlan.getOnlinePlan()).toString();// 后台功能验收耗时
						content.append("后台功能验收耗时"		+ htTime+ "小时,");
					}

				//非公能统计，暂时不要
	//				count1 = naProcessNodeRecordDao.auto(3L, 3L, onlinePlan.getOnlinePlan());。。。。。。
	//				count2 = naProcessNodeRecordDao.test(3L, onlinePlan.getOnlinePlan());
	//				totalCount2 = Integer.valueOf(count1.toString()) + Integer.valueOf(count2.toString());
	//				time3 = naProcessNodeRecordDao.time(3L, onlinePlan.getOnlinePlan()) == null ? 0
	//						: naProcessNodeRecordDao.time(3L, onlinePlan.getOnlinePlan());// 非功能验收耗时);
					
				}
			}
			record.setExt1(content.toString());
		}
		naProcessNodeRecordDao.save(record);
	}

	public List<ProcessNodeResponse> flowList(String planDate) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		String currentMonth = "";
		if (planDate == null) {
			calendar.setTime(new Date());
			currentMonth = formatter.format(calendar.getTime());
		} else {
			currentMonth = planDate;
		}
		// 如果当前账期数据不为空，取当前账期数据
		List<Object> current = naProcessNodeRecordDao.current(currentMonth);
		System.out.println("count" + current.size());
		if (current == null || current.size() < 1) {
			// 查询距离最近的数据
			String[] date = naProcessNodeRecordDao.latest(planDate.substring(0, planDate.length() - 3) + "%", planDate);
			if (date.length > 0) {
				planDate = date[0];
			} else {
				// 查询最大账期数据
				String[] maxDate = naProcessNodeRecordDao.maxDate(planDate.substring(0, planDate.length() - 3) + "%");
				if (maxDate.length > 0) {
					planDate = maxDate[0];
				}
			}
		}

		List<ProcessNodeResponse> responses = new ArrayList<ProcessNodeResponse>();
		long[] list = naProcessNodeRecordDao.plan(planDate);
		for (int i = 0; i < list.length; i++) {
			ProcessNodeResponse response = new ProcessNodeResponse();
			List<NaProcessNodeRecord> records = naProcessNodeRecordDao.findByPlanId(list[i]);
			NaChangePlanOnile plan=naChangePlanOnileDao.findOne(list[i]);
			if(null==plan){
				BusinessException.throwBusinessException(ErrorCode.BAD_REQUEST, "根据计划编号查询不到计划");
			}
			response.setMaxNum(Long.valueOf(list.length));
			response.setActiveNum(1);
			response.setId(10000 + i);
			response.setFlowList(records);
			response.setPlanName(plan.getOnlinePlanName());
			responses.add(response);
		}
		return responses;

	}

	/**
	 * @ClassName: HomeDataSv :: informationNew
	 * @author: lh
	 * @date: 2017年6月8日 下午2:00:44
	 *
	 * @Description:
	 * @return
	 */
	public NetInfoResponse informationNew() {
		String[] month = new String[6];// 月份
		long[] onlinePlan = new long[6];// 上线变更数量
		long[] abNormal = new long[6];// 异常数量
		long[] fault = new long[6];// 缺陷数量
		String[] reSucRate = new String[6];// 前台成功率
		String[] esbSucRate = new String[6];// esb成功率
		String[] cbossSucRate = new String[6];// cboss成功率
		String sql = "select * from (select * from NA_HOMEW_INFO order by month desc ) where rownum < 7 ";
		List<NaHomewInfo> naHomewInfoes = naHomewInfoDao.searchByNativeSQL(sql, NaHomewInfo.class);
		NetInfoResponse netInfoResponse = new NetInfoResponse();
		int size = naHomewInfoes.size();
		for (int i = 0; i <size; i++) {
			if (naHomewInfoes.size()>i) {
				netInfoResponse.getMonth()[size-1-i] =  ((naHomewInfoes.get(i).getMonth().toString()!=null)?naHomewInfoes.get(i).getMonth().toString():"0");
				netInfoResponse.getOnlinePlan()[size-1-i] = naHomewInfoes.get(i).getOnlineplancount()!=null?naHomewInfoes.get(i).getOnlineplancount():0;
				netInfoResponse.getAbNormal()[size-1-i] = naHomewInfoes.get(i).getAbnormalcount()!=null?naHomewInfoes.get(i).getAbnormalcount():0;
				netInfoResponse.getFault()[size-1-i] = naHomewInfoes.get(i).getFaultcount()!=null?naHomewInfoes.get(i).getFaultcount():0;
				netInfoResponse.getEsbSucRate()[size-1-i] = (naHomewInfoes.get(i).getEsbsucrate()!=null?naHomewInfoes.get(i).getEsbsucrate():"0");
				netInfoResponse.getCbossSucRate()[size-1-i] =  (naHomewInfoes.get(i).getCbosssucrate()!=null?naHomewInfoes.get(i).getCbosssucrate():"0");
				netInfoResponse.getReSucRate()[size-1-i] = (naHomewInfoes.get(i).getResucrate()!=null?naHomewInfoes.get(i).getResucrate():"0");
			}
		}
		return netInfoResponse;
	}

	/**
	 * @ClassName: HomeDataSv :: information
	 * @author: dongch
	 * @date: 2017年5月5日 下午3:46:14
	 *
	 * @Description:近半年入网信息分析
	 * @return
	 */
	public NetInfoResponse information() {
		String[] month = new String[6];// 月份
		long[] onlinePlan = new long[6];// 上线变更数量
		long[] abNormal = new long[6];// 异常数量
		long[] fault = new long[6];// 缺陷数量
		String[] reSucRate = new String[6];// 前台成功率
		String[] esbSucRate = new String[6];// esb成功率
		String[] cbossSucRate = new String[6];// cboss成功率

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 0);
		cal.set(Calendar.DATE, 1);
		String date1 = sdf.format(cal.getTime());
		cal.add(Calendar.MONTH, +1);
		String date2 = sdf.format(cal.getTime());

		//
		NetInfoResponse response = new NetInfoResponse();

		long count = naProcessNodeRecordDao.countPlan(date1, date2);
		onlinePlan[5] = count;
		count = naProcessNodeRecordDao.countAbnormal(1L, date1, date2);
		abNormal[5] = count;
		count = naProcessNodeRecordDao.countAbnormal(2L, date1, date2);
		fault[5] = count;
		month[5] = date1.substring(5, 7);
		long count1 = naProcessNodeRecordDao.recase(1L, date1, date2);// 前台执行用例数
		long count2 = naProcessNodeRecordDao.reSucCase(1L, date1, date2);// 前台执行成功用例数
		DecimalFormat df = new DecimalFormat("0.00");
		if (count1 == 0) {
			reSucRate[5] = "0.00";
		} else {
			reSucRate[5] = df.format(count2 / count1 * 100);
		}
		long count3 = naProcessNodeRecordDao.cbossCase(date1, date2);// CBOSS执行用例数
		long count4 = naProcessNodeRecordDao.cbossSucCase(date1, date2);// CBOSS执行成功用例数
		if (count3 == 0) {
			cbossSucRate[5] = "0.00";
		} else {
			cbossSucRate[5] = df.format(count4 / count3 * 100);
		}
		long count5 = naProcessNodeRecordDao.esbCase(date1, date2);
		long count6 = naProcessNodeRecordDao.esbSucCase(date1, date2);
		if (count5 == 0) {
			esbSucRate[5] = "0.00";
		} else {
			esbSucRate[5] = df.format(count6 / count5 * 100);
		}

		cal.add(Calendar.MONTH, -1);
		date2 = sdf.format(cal.getTime());
		cal.add(Calendar.MONTH, -1);// 4
		date1 = sdf.format(cal.getTime());
		// NetInfoResponse response1 = new NetInfoResponse();
		count = naProcessNodeRecordDao.countPlan(date1, date2);
		onlinePlan[4] = count;
		count = naProcessNodeRecordDao.countAbnormal(1L, date1, date2);
		abNormal[4] = count;
		count = naProcessNodeRecordDao.countAbnormal(2L, date1, date2);
		fault[4] = count;
		month[4] = date1.substring(5, 7);
		count1 = naProcessNodeRecordDao.recase(1L, date1, date2);// 前台执行用例数
		count2 = naProcessNodeRecordDao.reSucCase(1L, date1, date2);// 前台执行成功用例数
		// DecimalFormat df = new DecimalFormat("0.00");
		if (count1 == 0) {
			reSucRate[4] = "0.00";
		} else {
			reSucRate[4] = df.format(count2 / count1 * 100);
		}
		count3 = naProcessNodeRecordDao.cbossCase(date1, date2);// CBOSS执行用例数
		count4 = naProcessNodeRecordDao.cbossSucCase(date1, date2);// CBOSS执行成功用例数
		if (count3 == 0) {
			cbossSucRate[4] = "0.00";
		} else {
			cbossSucRate[4] = df.format(count4 / count3 * 100);
		}
		count5 = naProcessNodeRecordDao.esbCase(date1, date2);
		count6 = naProcessNodeRecordDao.esbSucCase(date1, date2);
		if (count5 == 0) {
			esbSucRate[4] = "0.00";
		} else {
			esbSucRate[4] = df.format(count6 / count5 * 100);
		}

		for (int i = 4; i > 0; i--) {
			cal.add(Calendar.MONTH, -1);
			date2 = date1;
			date1 = sdf.format(cal.getTime());// 3
			// NetInfoResponse response2 = new NetInfoResponse();
			count = naProcessNodeRecordDao.countPlan(date1, date2);
			onlinePlan[(i - 1)] = count;
			count = naProcessNodeRecordDao.countAbnormal(1L, date1, date2);
			abNormal[(i - 1)] = count;
			count = naProcessNodeRecordDao.countAbnormal(2L, date1, date2);
			fault[(i - 1)] = count;
			month[(i - 1)] = date1.substring(5, 7);
			count1 = naProcessNodeRecordDao.recase(1L, date1, date2);// 前台执行用例数
			count2 = naProcessNodeRecordDao.reSucCase(1L, date1, date2);// 前台执行成功用例数
			// DecimalFormat df = new DecimalFormat("0.00");
			if (count1 == 0) {
				reSucRate[(i - 1)] = "0.00";
			} else {
				reSucRate[(i - 1)] = df.format(count2 / count1 * 100);
			}
			count3 = naProcessNodeRecordDao.cbossCase(date1, date2);// CBOSS执行用例数
			count4 = naProcessNodeRecordDao.cbossSucCase(date1, date2);// CBOSS执行成功用例数
			if (count3 == 0) {
				cbossSucRate[(i - 1)] = "0.00";
			} else {
				cbossSucRate[(i - 1)] = df.format(count4 / count3 * 100);
			}
			count5 = naProcessNodeRecordDao.esbCase(date1, date2);
			count6 = naProcessNodeRecordDao.esbSucCase(date1, date2);
			if (count5 == 0) {
				esbSucRate[(i - 1)] = "0.00";
			} else {
				esbSucRate[(i - 1)] = df.format(count6 / count5 * 100);
			}
		}
		response.setAbNormal(abNormal);
		response.setFault(fault);
		response.setMonth(month);
		response.setOnlinePlan(onlinePlan);
		response.setReSucRate(reSucRate);
		response.setEsbSucRate(esbSucRate);
		response.setCbossSucRate(cbossSucRate);
		return response;
	}
	/**

	 * @return 
	 * 
	 */
	public  NaHomewInfo addInfo() {
		// TODO Auto-generated method stub
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 0);
		cal.set(Calendar.DATE, 1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date1 = sdf.format(cal.getTime());
		cal.add(Calendar.MONTH, +1);
		String date2 = sdf.format(cal.getTime());
		NaHomewInfo naHomewInfo=new NaHomewInfo();
		List<NaHomewInfo> list = naHomewInfoDao.findByMonth(Long.valueOf(date1.substring(5, 7)));
		if(list != null && list.size() > 0){
			naHomewInfo = list.get(0);
		}
		
		naHomewInfo.setMonth(Long.valueOf(date1.substring(5, 7)));
		naHomewInfo.setOnlineplancount(naProcessNodeRecordDao.countPlan(date1, date2));
		naHomewInfo.setAbnormalcount(naProcessNodeRecordDao.countAbnormal(1L, date1, date2));
		naHomewInfo.setFaultcount(naProcessNodeRecordDao.countAbnormal(2L, date1, date2));
		long count1 = naProcessNodeRecordDao.recase(1L, date1, date2);// 前台执行用例数
		long count2 = naProcessNodeRecordDao.reSucCase(1L, date1, date2);// 前台执行成功用例数
		DecimalFormat df = new DecimalFormat("0.00");
		if (count1 == 0) {
			naHomewInfo.setResucrate(String.valueOf(0));
		} else {
			//reSucRate[5] = df.format(count2 / count1 * 100);
			naHomewInfo.setResucrate(df.format((double)count2 / count1 * 100));
		}
		//naHomewInfo.setResucrate(String.valueOf(naProcessNodeRecordDao.reSucCase(1L, date1, date2)));
		long count5 = naProcessNodeRecordDao.esbCase(date1, date2);
		long count6 = naProcessNodeRecordDao.esbSucCase(date1, date2);
		if (count5 == 0) {
			naHomewInfo.setEsbsucrate(String.valueOf(0));
		} else {
			//esbSucRate[(i - 1)] = df.format(count6 / count5 * 100);
			naHomewInfo.setEsbsucrate(df.format((double)count6 / count5 * 100));
		}
		//naHomewInfo.setEsbsucrate(String.valueOf(naProcessNodeRecordDao.esbSucCase(date1, date2)));
		long count3 = naProcessNodeRecordDao.cbossCase(date1, date2);// CBOSS执行用例数
		long count4 = naProcessNodeRecordDao.cbossSucCase(date1, date2);// CBOSS执行成功用例数
		if (count3 == 0) {
			naHomewInfo.setCbosssucrate(String.valueOf(0));
		} else {
			//cbossSucRate[(i - 1)] = df.format(count4 / count3 * 100);
			naHomewInfo.setCbosssucrate(df.format((double)count4 / count3 * 100));
		}
		//naHomewInfo.setCbosssucrate(String.valueOf(naProcessNodeRecordDao.cbossSucCase(date1, date2)));
		naHomewInfo.setCreateDate( DateUtil.getCurrentTime());
		return naHomewInfoDao.save(naHomewInfo);
	}

	/**
	 * @ClassName: HomeDataSv :: kpiSave
	 * @author: dongch
	 * @date: 2017年5月12日 下午3:17:58
	 *
	 * @Description:指标配置保存
	 * @param kpis
	 */
	public void kpiSave(String kpiIds) {
		if (kpiIds == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "kpiIds");
		}
		naStaffKpiRelaDao.deleteByStaffId(SessionMgrUtil.getStaff().getStaffId());
		String[] kpiId = kpiIds.split(",");
		for (int i = 0; i < kpiId.length; i++) {
			NaStaffKpiRela rela = new NaStaffKpiRela();
			rela.setKpiId(Long.valueOf(kpiId[i]).longValue());
			rela.setCreateDate(new Date());
			rela.setStaffId(SessionMgrUtil.getStaff().getStaffId());
			rela.setOpId(SessionMgrUtil.getStaff().getStaffId());
			rela.setState(1L);
			naStaffKpiRelaDao.save(rela);
		}
	}

	/**
	 * @ClassName: HomeDataSv :: kpi
	 * @author: dongch
	 * @date: 2017年5月12日 下午5:38:18
	 *
	 * @Description:
	 */
	public List<NaIndexAllocation> kpi() {

		List<NaIndexAllocation> list = naIndexAllocationDao.findAll();
		return list;
	}
}
