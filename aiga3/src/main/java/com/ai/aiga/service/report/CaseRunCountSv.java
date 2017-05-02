package com.ai.aiga.service.report;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.NaAutoRunResultDao;
import com.ai.aiga.dao.NaAutoRunTaskDao;
import com.ai.aiga.dao.NaCaseImplReportDao;
import com.ai.aiga.dao.NaChangePlanOnileDao;
import com.ai.aiga.dao.NaPerExecReportDao;
import com.ai.aiga.domain.NaAutoRunResult;
import com.ai.aiga.domain.NaAutoRunTask;
import com.ai.aiga.domain.NaCaseImplReport;
import com.ai.aiga.domain.NaChangePlanOnile;
import com.ai.aiga.domain.NaPerExecReport;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.service.task.TaskSv;
import com.ai.process.task.quartz.CaseRunJob;
import com.ai.process.task.quartz.CaseStatisticsJob;

/**
 * @ClassName: CaseRunCountSv
 * @author: dongch
 * @date: 2017年4月27日 下午7:06:07
 * @Description:z执行情况（用例，人员）
 * 
 */
@Service
@Transactional
public class CaseRunCountSv extends BaseService{
	
	@Autowired
	private TaskSv taskSv;
	
	@Autowired
	private NaCaseImplReportDao naCaseImplReportDao;
	
	@Autowired
	private NaChangePlanOnileDao naChangePlanOnileDao;
	
	@Autowired
	private NaPerExecReportDao naPerExecReportDao;
	
	@Autowired
	private NaAutoRunTaskDao naAutoRunTaskDao;
	
	@Autowired
	private NaAutoRunResultDao naAutoRunResultDao;
	
	public void countAsync() {
		
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("key", "value");
		taskSv.addTask(CaseRunJob.class, params);
	}

	public void caseCount(){
		
		naCaseImplReportDao.delete();
		List<NaChangePlanOnile> onlinePlans = naChangePlanOnileDao.findBySign();
		List<NaCaseImplReport> reportList = new ArrayList<NaCaseImplReport>();
		if(onlinePlans != null && onlinePlans.size() > 0){
			for(int i = 0; i < onlinePlans.size(); i++){
				NaChangePlanOnile onlinePlan = onlinePlans.get(i);
				List<NaCaseImplReport> reportList1 = new ArrayList<NaCaseImplReport>();//准发布
				List<NaCaseImplReport> reportList2 = new ArrayList<NaCaseImplReport>();//生产
				//准发布
				List<Object> listTest = naCaseImplReportDao.testCount(onlinePlan.getOnlinePlan());
				for(Object object:listTest){ 
					NaCaseImplReport report = new NaCaseImplReport();
                    Object[] obj = (Object[])object;
                    int totalCount = Integer.parseInt(String.valueOf(obj[0]));
                    String sysName = String.valueOf(obj[1]);
                    report.setOnlinePlanId(onlinePlan.getOnlinePlan());
                    report.setOnlinePlanName(onlinePlan.getOnlinePlanName());
                    report.setPlanDate(onlinePlan.getPlanDate());
                    report.setSysName(sysName);
                    report.setEnvironment("准发布");
                    report.setCaseTotalCount(totalCount);
                    report.setManualCaseCount(totalCount);
                    report.setFirSucRate("0.00%");
                    report.setAutoCaseCount(0);
                    reportList1.add(report);
                }
				List<Object> listAuto = naCaseImplReportDao.autoCount(onlinePlan.getOnlinePlan(), 2L);
				for(Object objectAuto:listAuto){
                    Object[] objAuto = (Object[])objectAuto;
                    int totalCount = Integer.parseInt(String.valueOf(objAuto[0]));
                    String sysName = String.valueOf(objAuto[1]);
                    boolean flag = true;
                    for(int j = 0;j < reportList1.size();j++){
                        if(reportList1.get(j).getSysName().equals(sysName)){
                            flag = false;
                            reportList1.get(j).setAutoCaseCount(totalCount);
                            reportList1.get(j).setCaseTotalCount(reportList1.get(j).getManualCaseCount()+totalCount);
                            break;
                        }
                    }
                    if(flag){
                    	NaCaseImplReport report = new NaCaseImplReport();
                        report.setOnlinePlanId(onlinePlan.getOnlinePlan());
                        report.setOnlinePlanName(onlinePlan.getOnlinePlanName());
                        report.setPlanDate(onlinePlan.getPlanDate());
                        report.setSysName(sysName);
                        report.setEnvironment("准发布");
                        report.setCaseTotalCount(totalCount);
                        report.setManualCaseCount(0);
                        report.setAutoCaseCount(totalCount);
                        report.setFirSucRate("0.00%");
                        reportList1.add(report);
                    }
                }
				List<Object> listTask = naCaseImplReportDao.runFinishCount(onlinePlan.getOnlinePlan(), 2L);
				for(int j = 0;j < reportList1.size();j++){
                    for(Object objectTask:listTask){
                        Object[] objAuto = (Object[])objectTask;
                        int totalCount = Integer.parseInt(String.valueOf(objAuto[0]));
                        String sysName = String.valueOf(objAuto[1]);
                        if(reportList1.get(j).getSysName().equals(sysName)){
                            DecimalFormat df = new DecimalFormat("0.00");
                            reportList1.get(j).setFirSucRate(df.format((double)totalCount/reportList1.get(j).getAutoCaseCount() * 100)+"%");
                            break;
                        }
                    }
                }
				//生产
				for(Object object:listTest){ 
					NaCaseImplReport report = new NaCaseImplReport();
                    Object[] obj = (Object[])object;
                    int totalCount = Integer.parseInt(String.valueOf(obj[0]));
                    String sysName = String.valueOf(obj[1]);
                    report.setOnlinePlanId(onlinePlan.getOnlinePlan());
                    report.setOnlinePlanName(onlinePlan.getOnlinePlanName());
                    report.setPlanDate(onlinePlan.getPlanDate());
                    report.setSysName(sysName);
                    report.setEnvironment("生产");
                    report.setCaseTotalCount(totalCount);
                    report.setManualCaseCount(totalCount);
                    report.setFirSucRate("0.00%");
                    report.setAutoCaseCount(0);
                    reportList2.add(report);
                }
				listAuto = naCaseImplReportDao.autoCount(onlinePlan.getOnlinePlan(), 3L);
				for(Object objectAuto:listAuto){
                    Object[] objAuto = (Object[])objectAuto;
                    int totalCount = Integer.parseInt(String.valueOf(objAuto[0]));
                    String sysName = String.valueOf(objAuto[1]);
                    boolean flag = true;
                    for(int j = 0;j < reportList2.size();j++){
                        if(reportList2.get(j).getSysName().equals(sysName)){
                            flag = false;
                            reportList2.get(j).setAutoCaseCount(totalCount);
                            reportList2.get(j).setCaseTotalCount(reportList2.get(j).getManualCaseCount()+totalCount);
                            break;
                        }
                    }
                    if(flag){
                    	NaCaseImplReport report = new NaCaseImplReport();
                        report.setOnlinePlanId(onlinePlan.getOnlinePlan());
                        report.setOnlinePlanName(onlinePlan.getOnlinePlanName());
                        report.setPlanDate(onlinePlan.getPlanDate());
                        report.setSysName(sysName);
                        report.setEnvironment("生产");
                        report.setCaseTotalCount(totalCount);
                        report.setManualCaseCount(0);
                        report.setAutoCaseCount(totalCount);
                        report.setFirSucRate("0.00%");
                        reportList2.add(report);
                    }
                }
				listTask = naCaseImplReportDao.runFinishCount(onlinePlan.getOnlinePlan(), 3L);
				for(int j = 0;j < reportList1.size();j++){
                    for(Object objectTask:listTask){
                        Object[] objAuto = (Object[])objectTask;
                        int totalCount = Integer.parseInt(String.valueOf(objAuto[0]));
                        String sysName = String.valueOf(objAuto[1]);
                        if(reportList2.get(j).getSysName().equals(sysName)){
                            DecimalFormat df = new DecimalFormat("0.00");
                            reportList2.get(j).setFirSucRate(df.format((double)totalCount/reportList2.get(j).getAutoCaseCount() * 100)+"%");
                            break;
                        }
                    }
                }
				reportList.addAll(reportList1);
				reportList.addAll(reportList2);
			}
		}
		for(int k = 0; k < reportList.size(); k++){
			NaCaseImplReport naCaseImplReport = reportList.get(k);
			naCaseImplReportDao.save(naCaseImplReport);
		}
	}

	/**
	 * @ClassName: CaseRunCountSv :: staffCount
	 * @author: dongch
	 * @date: 2017年4月28日 下午3:24:23
	 *
	 * @Description:人员执行情况          
	 */
	public void staffCount() {
		
		naPerExecReportDao.delete();
		
		List<NaChangePlanOnile> onlinePlans = naChangePlanOnileDao.findBySign();
		List<NaPerExecReport> reportList = new ArrayList<NaPerExecReport>();
		if(onlinePlans != null && onlinePlans.size() > 0){
			for(int i = 0; i < onlinePlans.size(); i++){
				NaChangePlanOnile onlinePlan = onlinePlans.get(i);
				List<NaPerExecReport> list = new ArrayList<NaPerExecReport>();
				//手工用例
				List<Object> testList = naPerExecReportDao.testCount(onlinePlan.getOnlinePlan());
				for(Object object:testList){ 
                    Object[] obj = (Object[])object;
                    int totalCount = Integer.parseInt(String.valueOf(obj[0]));
                    String perName = String.valueOf(obj[1]);
                    String perSysId = String.valueOf(obj[2]);
                    NaPerExecReport report = new NaPerExecReport();
                    report.setPerName(perName);
                    report.setPerSysId(perSysId);
                    report.setOnlinePlanId(onlinePlan.getOnlinePlan());
                    report.setOnlinePlanName(onlinePlan.getOnlinePlanName());
                    report.setManualCaseCount(totalCount);
                    report.setAutoCaseCount(0);
                    report.setBugCount(0);
                    list.add(report);
                }
				//自动化用例
				List<Object> autoList1 = naPerExecReportDao.autoCount(onlinePlan.getOnlinePlan(), 2L);
				for(Object object:autoList1){
                    Object[] obj = (Object[])object;
                    int totalCount = Integer.parseInt(String.valueOf(obj[0]));
                    String perName = String.valueOf(obj[1]);
                    String perSysId = String.valueOf(obj[2]);
                    boolean flag = true;
                    for(int j = 0;j < list.size();j++){
                        if(list.get(j).getPerName().equals(perName) && list.get(j).getPerSysId().equals(perSysId)){
                            flag = false;
                            list.get(j).setAutoCaseCount(totalCount);
                            break;
                        }
                    }
                    if(flag){
                    	NaPerExecReport report = new NaPerExecReport();
                        report.setPerName(perName);
                        report.setPerSysId(perSysId);
                        report.setOnlinePlanId(onlinePlan.getOnlinePlan());
                        report.setOnlinePlanName(onlinePlan.getOnlinePlanName());
                        report.setManualCaseCount(0);
                        report.setAutoCaseCount(totalCount);
                        report.setBugCount(0);
                        list.add(report);
                    }
                }
				List<Object> autoList2 = naPerExecReportDao.autoCount(onlinePlan.getOnlinePlan(), 3L);
				 for(Object object:autoList2){
                     Object[] obj = (Object[])object;
                     int totalCount = Integer.parseInt(String.valueOf(obj[0]));
                     String perName = String.valueOf(obj[1]);
                     String perSysId = String.valueOf(obj[2]);
                     boolean flag = true;
                     for(int j = 0;j < list.size();j++){
                         if(list.get(j).getPerName().equals(perName) && list.get(j).getPerSysId().equals(perSysId)){
                             flag = false;
                             list.get(j).setAutoCaseCount(list.get(j).getAutoCaseCount() + totalCount);
                             break;
                         }
                     }
                     if(flag){
                    	 NaPerExecReport report = new NaPerExecReport();
                         report.setPerName(perName);
                         report.setPerSysId(perSysId);
                         report.setOnlinePlanId(onlinePlan.getOnlinePlan());
                         report.setOnlinePlanName(onlinePlan.getOnlinePlanName());
                         report.setManualCaseCount(0);
                         report.setAutoCaseCount(totalCount);
                         report.setBugCount(0);
                         list.add(report);
                     }
                 }
				//自动化用例执行成功率
				List<Object> autoTask1 = naPerExecReportDao.autoRunCount(onlinePlan.getOnlinePlan(), 2L);
				List<Object> autoTask2 = naPerExecReportDao.autoRunCount(onlinePlan.getOnlinePlan(), 3L);
				 for(int j = 0;j < list.size();j++){
                     if(list.get(j).getAutoCaseCount() == 0){
                         list.get(j).setExecSuccRate("0.00%");
                     }else{
                         long succCount = 0;
                         for(Object object:autoTask1){
                             Object[] obj = (Object[])object;
                             int totalCount = Integer.parseInt(String.valueOf(obj[0]));
                             String perName = String.valueOf(obj[1]);
                             String perSysId = String.valueOf(obj[2]);
                             if(list.get(j).getPerName().equals(perName) && list.get(j).getPerSysId().equals(perSysId)){
                                 succCount += totalCount;
                             }
                         }
                         for(Object object:autoTask1){
                             Object[] obj = (Object[])object;
                             int totalCount = Integer.parseInt(String.valueOf(obj[0]));
                             String perName = String.valueOf(obj[1]);
                             String perSysId = String.valueOf(obj[2]);
                             if(list.get(j).getPerName().equals(perName) && list.get(j).getPerSysId().equals(perSysId)){
                                 succCount += totalCount;
                             }
                         }
                         DecimalFormat df = new DecimalFormat("0.00");
                         list.get(j).setExecSuccRate(df.format((double)succCount/list.get(j).getAutoCaseCount() * 100) + "%");
                     }
                 }
				 //自动化用例耗时（总耗时，最长耗时）
				 List<Object> listTask = naPerExecReportDao.autoTime(onlinePlan.getOnlinePlan());
				 for(int j = 0;j < list.size();j++){
                     long totalTime = 0;//执行自动化用例总耗时
                     long maxTime = 0;//执行自动化用例最大耗时
                     for(Object object:listTask){
                         Object[] obj = (Object[])object;
                         String taskTag = String.valueOf(obj[0]);//自动化任务编号
                         String perName = String.valueOf(obj[1]);//执行人员名称
                         String perSysId = String.valueOf(obj[2]);//执行人员系统ID
                         if(list.get(j).getPerName().equals(perName) && list.get(j).getPerSysId().equals(perSysId)){
                             String[] tags = taskTag.split(",");
                             for(int k = 0;k < tags.length;k++){
                            	 List<NaAutoRunTask> task = naAutoRunTaskDao.findByTaskTag(tags[k]);
                                 if(task != null && !task.isEmpty()){
                                     Integer taskId = Integer.parseInt(String.valueOf(task.get(0)));
                                     	List<NaAutoRunResult> resultList = naAutoRunResultDao.findByTaskId(task.get(0).getTaskId());
                                     if(resultList != null && !resultList.isEmpty()){
                                         long time = 0;
                                         for(int n = 0;n < resultList.size();n++){
                                             NaAutoRunResult result = resultList.get(n);
                                             if(result.getResultType() == 3 && result.getEndTime() != null && result.getBeginTime() != null && (result.getEndTime().getTime() - result.getBeginTime().getTime()) > 0){
                                                 totalTime += (result.getEndTime().getTime() - result.getBeginTime().getTime());
                                                 time += (result.getEndTime().getTime() - result.getBeginTime().getTime());
                                             }
                                         }
                                         if(time > maxTime){
                                             maxTime = time;
                                         }
                                     }
                                 }
                             }
                         }
                     }
                     if(totalTime > 0){
                         list.get(j).setExecAutoTime(totalTime/(60*60*1000) + "时" + (totalTime%(60*60*1000))/(60*1000) + "分" + totalTime%(60*60*1000)%(60*1000)/1000 + "秒");
                     }
                     if(maxTime > 0){
                         list.get(j).setExecMaxTime(maxTime/(60*60*1000) + "时" + (maxTime%(60*60*1000))/(60*1000) + "分" + maxTime%(60*60*1000)%(60*1000)/1000 + "秒");
                     }
                 }
				 //手工用例执行时长
				 List<Object> listCase = naPerExecReportDao.testTime(onlinePlan.getOnlinePlan());
				 for(int j = 0;j < list.size();j++){
                     for(Object object:listCase){
                         Object[] obj = (Object[])object;
                         long time = Long.parseLong(String.valueOf(obj[0]));
                         String perName = String.valueOf(obj[1]);//执行人员名称
                         String perSysId = String.valueOf(obj[2]);//执行人员系统ID
                         if(list.get(j).getPerName().equals(perName) && list.get(j).getPerSysId().equals(perSysId)){
                             list.get(j).setExecManualTime(time/(60*60*1000) + "时" + (time%(60*60*1000))/(60*1000) + "分" + time%(60*60*1000)%(60*1000)/1000 + "秒");
                             break;
                         }
                     }
                 }
				 //缺陷个数
				 List<Object> listFault = naPerExecReportDao.faultCount(onlinePlan.getOnlinePlan());
				 for(Object object:listFault){
                     Object[] obj = (Object[])object;
                     int totalCount = Integer.parseInt(String.valueOf(obj[0]));
                     String perName = String.valueOf(obj[1]);
                     String perSysId = String.valueOf(obj[2]);
                     for(int j = 0;j < list.size();j++){
                         if(list.get(j).getPerName().equals(perName) && list.get(j).getPerSysId().equals(perSysId)){
                             list.get(j).setBugCount(totalCount);
                             break;
                         }
                     }
                 }
				 reportList.addAll(list);
			}
			
		}
		for(int k = 0; k < reportList.size(); k++){
			NaPerExecReport perExecReport = reportList.get(k);
			naPerExecReportDao.save(perExecReport);
		}
	}
		

}

