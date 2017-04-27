package com.ai.aiga.service.report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.ChangePlanOnileDao;
import com.ai.aiga.dao.OnlineSituationReportDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.domain.ChangePlanOnile;
import com.ai.aiga.domain.OnlineSituationReport;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.json.base.JsonBean;

/**
 * * @author lh
 * 
 * @date 创建时间：2017年4月26日 下午3:18:56
 * @Description:变更情况报表
 */
public class OnlineSituationReportSv extends BaseService {

	@Autowired
	private OnlineSituationReportDao onlineSituationReportDao;
	@Autowired
	private ChangePlanOnileDao changePlanOnileDao;

	/**
	 * @ClassName: CaseConstructionSv :: reportOnlineSituationReport
	 * @author: lh
	 * @date: 2017年4月26日 下午2:12:06
	 *
	 * @Description:查询变更情况报表
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Object findOnlineSituationReport(Long onlinePlan, String date, int pageNumber, int pageSize) {

		List<Condition> cons = new ArrayList<Condition>();
		StringBuilder sql = new StringBuilder("select * from ONLINE_SITUATION_REPORT a where 1=1");
		if (onlinePlan != null) {
			sql.append(" and ONLINE_PALN = " + onlinePlan);
		}
		if (date != null) {
			sql.append(" to_char( plan_date,'yyyymm') = " + date);
		}

		if (pageNumber < 0) {
			pageNumber = 0;
		}
		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return onlineSituationReportDao.searchByNativeSQL(sql.toString(), pageable);
	}

	/**
	 * @ClassName: OnlineSituationReportSv :: countOnlineSituationReport
	 * @author: lh
	 * @date: 2017年4月26日 下午3:26:37
	 *
	 * @Description:统计报表
	 * @return
	 */
	public void countOnlineSituationReport() {
		List<ChangePlanOnile> ChangePlanOnileList = changePlanOnileDao.findAll();
		List<OnlineSituationReport> onlineSituationReportList = new ArrayList<OnlineSituationReport>();
		for (ChangePlanOnile cpo : ChangePlanOnileList) {
			OnlineSituationReport osReport = new OnlineSituationReport();
			// 统计报表的计划编号
			osReport.setOnlinePlan(cpo.getOnlinePlan());
			// 统计报表的计划名称
			osReport.setOnlinePlanName(cpo.getOnlinePlanName());
			// 统计报表的计划类型
			osReport.setTypes(cpo.getTypes());
			// 统计报表的变更时间
			osReport.setPlanDate(cpo.getPlanDate());

			// 统计报表的月份(自动生成)
			osReport.setMonth(new SimpleDateFormat("yyyyMM").format(cpo.getPlanDate()));

			// 统计报表的上线申请是否及时
			osReport.setIsApplyTime(cpo.getExt2());

			// 统计报表的变更上线过程异常事件次数
			Integer exceptionNum = onlineSituationReportDao.countBug(cpo.getOnlinePlan());
			if (exceptionNum != null) {
				osReport.setExceptionNum(exceptionNum);
			}
			// 统计报表的变更上线过程故障事件次数
			Integer bugNum = onlineSituationReportDao.countFault(cpo.getOnlinePlan());
			if (bugNum != null) {
				osReport.setBugNum(bugNum);
			}

			// 缺陷总数统计
			Integer faultNum = onlineSituationReportDao.countQuexian(cpo.getOnlinePlan());
			if (faultNum != null) {
				osReport.setFaultNum(faultNum);
			}
			// 有效缺陷数
			Integer faultValidCount = onlineSituationReportDao.faultValidCount(cpo.getOnlinePlan());
			if (faultValidCount != null) {
				osReport.setFaultValidNum(faultValidCount);
			}

			// 无效缺陷数
			Integer faultInvalidCount = onlineSituationReportDao.faultInvalidCount(cpo.getOnlinePlan());
			if (faultInvalidCount != null) {
				osReport.setFaultValidNum(faultInvalidCount);
			}
			// 遗留问题数
			Integer remainNum = onlineSituationReportDao.remainNum(cpo.getOnlinePlan());
			if (remainNum != null) {
				osReport.setRemainNum(remainNum);
			}
			// 功能验收总耗时
			try {
				osReport.setFunCheckDuration(getDuration(1, cpo.getOnlinePlan()));
				 //性能验收总耗时
				osReport.setProCheckDuration(getDuration(2, cpo.getOnlinePlan()));
			} catch (Exception e) {
				BusinessException.throwBusinessException("计算功能验收总耗时报错");
			}
			
			 //BOSS验收总耗时
            String sqlBossCheck = "select total_time_count from AIGA_BOSS_TEST_RESULT where online_plan='"+cpo.getOnlinePlan()+"'";
            List<Map> bossCheckList = onlineSituationReportDao.searchByNativeSQL(sqlBossCheck);
            if(bossCheckList != null && bossCheckList.size() > 0){
            	if(bossCheckList.get(0)!=null&&!("").equals(bossCheckList.get(0))){
                    double bossCheck=Double.valueOf(String.valueOf(bossCheckList.get(0)));
                    osReport.setBossCheckDuration(getDateByHour(String.valueOf(bossCheck)));
            	}
            }else{
            	osReport.setBossCheckDuration("无");
            }
          //生产验证总耗时
            try {
				osReport.setProductCheckDuration(getDuration(4, cpo.getOnlinePlan()));
			} catch (Exception e) {
				BusinessException.throwBusinessException("生产验证总耗时报错");
			}
            
            //发布时长总耗时
            
            //判断该计划下是否有任务
            String sqlAllDurationS="select task_id from AIGA_ONLINE_TASK_DISTRIBUTE a where  a.task_type='9' and parent_task_id=0 and online_plan_id='"+cpo.getOnlinePlan()+"'";
            List<Map> AllDurationListS = onlineSituationReportDao.searchByNativeSQL(sqlAllDurationS);
            if(AllDurationListS != null && AllDurationListS.size() > 0){
            
                String sqlAllDuration = "select b.start_time,b.finish_time from CHANGE_PLAN_ONILE a,Aiga_Release_Report b where a.online_plan=b.online_plan_id and b.online_plan_id='"+cpo.getOnlinePlan()+"'";
                List<Map> allDurationList = onlineSituationReportDao.searchByNativeSQL(sqlAllDuration);
                if(allDurationList != null && allDurationList.size() > 0){
                    //时间差异
                    long allDurationTime=0;
                    for(Object o:allDurationList){
                        Object[] os=(Object[]) o;
                        if(os[1]!=null&&!("").equals(os[1])){
                        	allDurationTime+=((Date)os[1]).getTime()-((Date)os[0]).getTime();
                        }else{
                            osReport.setAllDuration("");
                            break;
                        }
                    }
                    osReport.setAllDuration(getDatePoor(allDurationTime));
                }else{
                    osReport.setAllDuration("");
                }
            }else{
            	osReport.setAllDuration("无");
            }
            
            //保存
            onlineSituationReportDao.save(osReport);
		}
	}

	// 统计耗时(功能验收和生产验收)
	@SuppressWarnings("unchecked")
	private String getDuration(int type, Long online_plan) throws Exception {
		String duration = "";

		// 判断该计划下是否有任务

		Integer funCheckDurationListS = onlineSituationReportDao.funCheckDurationListS(type, online_plan);

		Date s = null;
		if (funCheckDurationListS != null) {

			// 判断子任务是否完成
			String sqlFunCheckDurationC = "select task_id from AIGA_ONLINE_TASK_RESULT where task_id in( select task_id from AIGA_ONLINE_TASK_DISTRIBUTE where parent_task_id in(select task_id from AIGA_ONLINE_TASK_DISTRIBUTE a where a.task_type='"
					+ type + "' and parent_task_id=0 and online_plan_id='" + online_plan + "')) and state<>2";
			List<Map> funCheckDurationListC = onlineSituationReportDao.searchByNativeSQL(sqlFunCheckDurationC);
			// 没有未完成或处理中的子任务
			if (funCheckDurationListC == null || funCheckDurationListC.size() == 0) {

				// 开始时间(手工用例)
				Date sh = null;
				String sqlFunCheckDurationSH = "select min(done_date) from AIGA_PLAN_CASE_RESULT where Sub_Task_Id in (select task_id from AIGA_ONLINE_TASK_DISTRIBUTE where parent_task_id in(select task_id from AIGA_ONLINE_TASK_DISTRIBUTE a where a.task_type='"
						+ type + "' and parent_task_id=0 and online_plan_id='" + online_plan
						+ "') and task_type in (1,3))";
				List<Map> funCheckDurationListSH = onlineSituationReportDao.searchByNativeSQL(sqlFunCheckDurationSH);
				if (funCheckDurationListSH != null && funCheckDurationListSH.size() > 0) {
					sh = (Date) funCheckDurationListSH.get(0);
				}
				

				// 开始时间(自动化用例)
				Date sa = null;
				String sqlFunCheckDurationSA = "select EXT1 from AIGA_ONLINE_TASK_RESULT where task_id in( select task_id from AIGA_ONLINE_TASK_DISTRIBUTE where parent_task_id in(select task_id from AIGA_ONLINE_TASK_DISTRIBUTE a where a.task_type='"
						+ type + "' and parent_task_id=0 and online_plan_id='" + online_plan
						+ "') and task_type in (2,4)) and ext1 is not null";
				List<Map> funCheckDurationListSA = onlineSituationReportDao.searchByNativeSQL(sqlFunCheckDurationSA);
				if (funCheckDurationListSA != null && funCheckDurationListSA.size() > 0) {
					// 存放所有自动化任务对应的开始时间
					List<Date> listAutoStart = new ArrayList<Date>();
					for (Object o : funCheckDurationListSA) {
						Date sac = null;
						if (o != null && !("").equals(o)) {
							String autoTaskTag = String.valueOf(o);
							StringBuffer sb = new StringBuffer();
							for (String s1 : autoTaskTag.split(",")) {
								sb.append("'").append(s1).append("'").append(",");
							}
							autoTaskTag = sb.equals("") ? "" : sb.substring(0, sb.length() - 1);

							String sqlFunCheckDurationSAC = "select min(begin_run_time) from aiga_auto_run_task task where task_tag in ("
									+ autoTaskTag + ") and begin_run_time is not null";
							List<Map> funCheckDurationListSAC = onlineSituationReportDao
									.searchByNativeSQL(sqlFunCheckDurationSAC);
							if (funCheckDurationListSAC != null && funCheckDurationListSAC.size() > 0) {
								if (funCheckDurationListSAC.get(0) != null) {
									sac = (Date) funCheckDurationListSAC.get(0);
									listAutoStart.add(sac);
								}
							}
						}
					}
					// 从数组中取出最早的日期(排序)
					if (listAutoStart != null && listAutoStart.size() > 0) {
						sa = listAutoStart.get(0);
						for (Date d : listAutoStart) {
							if (d.before(sa)) {
								sa = d;
							}
						}
					}
				}
				
				// 计算两种用例最早开始时间
				if (sa != null && sh != null) {
					s = sh.before(sa) ? sh : sa;
				} else if (sa == null && sh != null) {
					s = sh;
				} else if (sa != null && sh == null) {
					s = sa;
				} else {
					duration = "";
				}

				

				// 结束时间
				Date f = null;
				String sqlFunCheckDurationF = "select max(finish_date) from AIGA_ONLINE_TASK_RESULT where task_id in (select task_id from AIGA_ONLINE_TASK_DISTRIBUTE where parent_task_id in(select task_id from AIGA_ONLINE_TASK_DISTRIBUTE a where a.task_type='"
						+ type + "' and parent_task_id=0 and online_plan_id='" + online_plan + "'))";
				List<Map> funCheckDurationListF = onlineSituationReportDao.searchByNativeSQL(sqlFunCheckDurationF);
				if (funCheckDurationListF != null && funCheckDurationListF.size() > 0) {
					f = (Date) funCheckDurationListF.get(0);
				}

				

				// 计算时间
				if (s != null && f != null) {
					duration = getDatePoor(f.getTime() - s.getTime());
					
				}
			} else {
				duration = "";
			}

		} else {
			duration = "无";
		}

		return duration;
	}


	// 计算相差天数
	private String getDatePoor(Long diff) {
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		long ns = 1000;
		// 计算差多少天
		// long day = diff / nd;
		// 计算差多少小时
		long hour = diff / nh;
		// 计算差多少分钟
		long min = diff % nh / nm;
		// 计算差多少秒//输出结果
		long sec = diff % nh % nm / ns;
		return hour + "时" + min + "分" + sec + "秒";
	}
	//小时转时分秒
    private String getDateByHour(String s){
    	
    	long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		long ns = 1000;
		
	    Double d=Double.valueOf(s);
	    Long hm=Math.round(d*nh);
	    
	    // 计算差多少小时
		long hour = hm / nh;
		// 计算差多少分钟
		long min = hm % nh / nm;
		// 计算差多少秒//输出结果
		long sec = hm % nh % nm / ns;
		return hour + "时" + min + "分" + sec + "秒";
    }
}
