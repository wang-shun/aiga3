package com.ai.aiga.service.report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.NaCaseContructionReportDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.domain.NaCaseConstructionReport;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.service.task.TaskSv;
import com.ai.aiga.view.json.report.CaseConstructionRequest;
import com.ai.process.task.quartz.CaseStatisticsJob;

/**
 * @ClassName: CaseConstructionSv
 * @author: dongch
 * @date: 2017年4月24日 下午3:24:28
 * @Description:
 * 
 */
@Service
@Transactional
public class CaseConstructionSv extends BaseService{
	
	@Autowired
	private TaskSv taskSv;
	
	@Autowired
	private NaCaseContructionReportDao naCaseContructionReportDao;
	
	protected Logger log = LoggerFactory.getLogger(getClass());
	
	/**
	 * @ClassName: CaseConstructionSv :: list
	 * @author: dongch
	 * @date: 2017年4月24日 下午3:27:07
	 *
	 * @Description:功能用例建设报表查询
	 * @param request
	 * @return          
	 */
	public Page<NaCaseConstructionReport> list(CaseConstructionRequest request, int pageNumber, int pageSize) {
		
		if(request.getType() == null || request.getType() < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "type");
		}
		List<Condition> cons = new ArrayList<Condition>();
		
		if(request.getSysId() != null && request.getType() == 1){
			cons.add(new Condition("sysId", request.getSysId(), Condition.Type.EQ));
		}
		
		if(request.getBusiId() != null && request.getType() == 2){
			cons.add(new Condition("sysId", request.getBusiId(), Condition.Type.EQ));
		}
		
		if(request.getGroupId() != null && request.getType() == 3){
			cons.add(new Condition("sysId", request.getSysId(), Condition.Type.EQ));
		}
		
		if(StringUtils.isNotBlank(request.getMonth()) && request.getType() == 1){
			cons.add(new Condition("statisticalMonth", request.getMonth(), Condition.Type.EQ));
		}
		
		if(StringUtils.isNotBlank(request.getMonthbusi()) && request.getType() == 2){
			cons.add(new Condition("statisticalMonth", request.getMonthbusi(), Condition.Type.EQ));
		}
		
		if(StringUtils.isNotBlank(request.getMonthgroup()) && request.getType() == 3){
			cons.add(new Condition("statisticalMonth", request.getMonthgroup(), Condition.Type.EQ));
		}
		
		cons.add(new Condition("reportType", request.getType(), Condition.Type.EQ));
		
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return naCaseContructionReportDao.search(cons, pageable);
	}
	
	public void countAsync(String month, String jobDetail) {
		
		//TODO 对month和jobDetail 进行验证 @dongch
		
		Map<String, String> params = new HashMap<String, String>();
		params.put(CaseStatisticsJob.KEY_MONTH, month);
		params.put(CaseStatisticsJob.KEY_TYPE, jobDetail);
		
		taskSv.addTask(CaseStatisticsJob.class, params);
	}

	/**
	 * @ClassName: CaseConstructionSv :: count
	 * @author: dongch
	 * @date: 2017年4月25日 上午11:38:23
	 *
	 * @Description:功能用例和人员日常建设统计
	 * @param month
	 * @param jobDetail          
	 */
	public void count(String month, String jobDetail) {
		
		String currentMonth = null;
		String lastMonth = null;
		
		if(StringUtils.isBlank(jobDetail)){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "jobDetail");
		}
		if(StringUtils.isNoneBlank(month) && !"undefind".equals(month)){
			currentMonth = month;
			lastMonth = String.valueOf(Integer.parseInt(month)-1);
		}else{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
			Calendar calendar = Calendar.getInstance();  
			calendar.setTime(new Date());  
			currentMonth = formatter.format(calendar.getTime());
			//取得上一个时间
			calendar.set(Calendar.MONDAY,calendar.get(Calendar.MONDAY)-1);  
			//取得上一个月的下一天
			calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)+1);  
			lastMonth = formatter.format(calendar.getTime());
		}
		
		if("jobStaff".equals(jobDetail)){
			executeJobStaff(currentMonth, lastMonth);
		}else{
			executeJob(jobDetail,currentMonth, lastMonth);
		}
	}

	/**
	 * @ClassName: CaseConstructionSv :: executeJob
	 * @author: dongch
	 * @date: 2017年4月25日 下午2:37:44
	 *
	 * @Description:功能用例建设统计
	 * @param jobDetail
	 * @param currentMonth
	 * @param lastMonth          
	 */
	private void executeJob(String jobDetail, String currentMonth, String lastMonth) {
		log.info(currentMonth+"当月用例建设情况报表进程--调度开始");
		if("job".equals(jobDetail)){
			collectSySData(currentMonth, lastMonth);
		}else if("jobb".equals(jobDetail)){
			collectSySData(currentMonth, lastMonth);
		}else if("jobu".equals(jobDetail)){
			collectUserData(currentMonth, lastMonth);
		}
		log.info(currentMonth+"当月用例建设情况报表进程--调度结束");
	}

	
	private void collectUserData(String currentMonth, String lastMonth) {
	}

	
	private void collectSySData(String currentMonth, String lastMonth) {
		String busiNum = "";  //核心业务覆盖数量
		String busiCover = "";  //核心业务覆盖率
		String funNum = "";  //核心功能点总数
		String funNumIncr = ""; //核心功能点增量
		String funCover = "";   //核心功能点覆盖率
		String funCoverIncr = ""; //核心功能点覆盖率增量
		String caseNumQRelease = "";           //入网验收用例数总数（准发布）
		String caseNumQIncrRelease = "";     //用例数总数增量（准发布）
		String autoCaseNumQRelease = "";           //自动化用例数（准发布）
		String autoCaseNumQIncrRelease = "";     //自动化用例数增量（准发布）
		String autoCaseCoverQRelease = "";   //自动化覆盖率（准发布）
		String autoCaseCoverIncrQRelease = "";   //自动化覆盖率增量
		String caseNumRelease = "";           //验证用例数（生产）
		String caseNumIncrRelease = "";     //生产用例数增量
		String autoCaseNumRelease = "";           //自动化用例数（生产）
		String autoCaseNumIncrRelease = "";     //自动化用例数增量（生产）
		String autoCaseCoverRelease = "";   //生产验证自动化覆盖率
		String autoCaseCoverIncrRelease = "";   //生产验证自动化覆盖率增量
		//上一个月数据
		String funNumOld = "";  //核心功能点总数
		String funCoverOld = "";   //核心功能点覆盖率
		String caseNumQReleaseOld = "";           //入网验收用例数总数（准发布）
		String autoCaseNumQReleaseOld = "";           //自动化用例数（准发布）
		String autoCaseCoverQReleaseOld = "";   //自动化覆盖率（准发布）
		String caseNumReleaseOld = "";           //验证用例数（生产）
		String autoCaseNumReleaseOld = "";           //自动化用例数（生产）
		String autoCaseCoverReleaseOld = "";   //生产验证自动化覆盖率
		Map<String,String[]> map = new HashMap<String,String[]>();
		Map<String,String[]> mapOld = null;
		//按系统分
		//取出上个月数据
		List<Object> list = naCaseContructionReportDao.findOld(lastMonth);
		mapOld = getOldData(list,8);
		String dayOrMonth = currentMonth.length()==6?"ADD_MONTHS(TRUNC(TO_DATE('"+currentMonth+"','YYYYMM'),'MM'),1)":"TRUNC(TO_DATE('"+(Integer.valueOf(currentMonth)+1)+"','YYYYMMDD'),'DD')";
		//插入当月数据
		//系统-核心功能点总数
		List<Object> funList = naCaseContructionReportDao.findSysFunCount();
		getData(funList,0,8,map);
		//核心功能点覆盖（挂用例的）
		List<Object> coverList = naCaseContructionReportDao.findSysFunCover();
		getData(coverList,1,8,map);
		//入网验收用例数总数（准发布）
		List<Object> caseList = naCaseContructionReportDao.findSysCaseCount(dayOrMonth);
		
	}

	
	
	private void getData(List<Object> list, int i, int size, Map<String, String[]> map) {
		
		if(list != null && !list.isEmpty()){
			for (Object objs : list) {
				String[] strs = null;
				Object[] obj = (Object[])objs;
				String key = String.valueOf(obj[0]);
				if(map.containsKey(key)){
					strs = map.get(key);
				}else{
					strs = new String[size];
					map.put(key, strs);
				}
				strs[i] = obj[1] != null ? (obj[1]).toString() : "";
			}
		}
	}

	private Map<String, String[]> getOldData(List<Object> list, int size) {
		
		if(list != null && !list.isEmpty()){
			Map<String,String[]> map = new HashMap<String,String[]>(list.size());
			for (Object objs : list) {
				Object[] obj = (Object[])objs;
				String[] strs = new String[size];
				for(int i=0; i < size; i++){
					strs[i] = obj[i + 1] != null ? (obj[i + 1]).toString() : "";
				}
				map.put(String.valueOf(obj[0]), strs);
			}
			return map;
		}
		
		return null;
	}

	/**
	 * @ClassName: CaseConstructionSv :: executeJobStaff
	 * @author: dongch
	 * @date: 2017年4月25日 下午2:33:10
	 *
	 * @Description:人员日常建设统计
	 * @param currentMonth
	 * @param lastMonth          
	 */
	private void executeJobStaff(String currentMonth, String lastMonth) {
		log.info(currentMonth+"当月人员日常建设情况报表进程--调度开始");
		
		log.info(currentMonth+"当月人员日常建设情况报表进程--调度结束");
	}

}

