package com.ai.aiga.service.report;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.NaCaseImplReportDao;
import com.ai.aiga.dao.NaChangePlanOnileDao;
import com.ai.aiga.domain.NaCaseImplReport;
import com.ai.aiga.domain.NaChangePlanOnile;
import com.ai.aiga.service.base.BaseService;

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
	private NaCaseImplReportDao naCaseImplReportDao;
	
	@Autowired
	private NaChangePlanOnileDao naChangePlanOnileDao;
	
	public void caseCount(){
		
		naCaseImplReportDao.delete();
		List<NaChangePlanOnile> onlinePlans = naChangePlanOnileDao.findBySign();
		if(onlinePlans != null && onlinePlans.size() > 0){
			for(int i = 0; i < onlinePlans.size(); i++){
				NaChangePlanOnile onlinePlan = onlinePlans.get(i);
				List<NaCaseImplReport> reportList1 = new ArrayList<NaCaseImplReport>();//准发布
				List<NaCaseImplReport> reportList2 = new ArrayList<NaCaseImplReport>();//生产
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
			}
		}
	}
		

}

