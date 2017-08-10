package com.ai.process.task.quartz;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.ai.aiga.dao.NaCodePathCompileResultDao;
import com.ai.aiga.dao.NaCodePathComplieDao;
import com.ai.aiga.dao.NaSystemInterfaceAddressDao;
import com.ai.aiga.domain.NaCodePathComplie;
import com.ai.aiga.domain.NaSystemInterfaceAddress;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.service.onlineProcess.NodeRecordSv;
import com.ai.aiga.service.report.CaseRunCountSv;
import com.ai.aiga.service.workFlowNew.ReviewPlanSv;
import com.ai.aiga.util.HttpUtil;
import com.ai.aiga.util.ReaderXmlForDOM4J;
import com.ai.aiga.util.spring.ApplicationContextUtil;
import com.ai.process.container.quartz.QuartzHelper;

@DisallowConcurrentExecution
public class ComplieResultJob implements Job{

	private static Logger log = LoggerFactory.getLogger(ComplieResultJob.class);
    private static final String  PLAN_Id  = "planId"; 
    private static final String  RUN_ORDER  = "order"; 
    private static final String  URL  = "url"; 
    private static final String  EVENTMENTS  = "everment"; 
    private static final String  NAME  = "name"; 
  private static final String  BmcPathBefore  ="https://10.70.102.60:9843";
    private static final String  BmcPathAfter  ="?username=rwys&password=2wsx!QAZ&role=rwys";
    
	
  //private static final String  BmcPathBefore  ="https://20.26.3.225:9843";
   //private static final String  BmcPathAfter  ="?username=BLAdmin&password=lingtong&role=BLAdmins&";
    

	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		JobDataMap dataMap = context.getMergedJobDataMap();
	    
		ReviewPlanSv sv = ApplicationContextUtil.getBean(ReviewPlanSv.class);
		NodeRecordSv nodeRecordSv = ApplicationContextUtil.getBean(NodeRecordSv.class);
		NaSystemInterfaceAddressDao dao = ApplicationContextUtil.getBean(NaSystemInterfaceAddressDao.class);
		NaCodePathComplieDao complineDao = ApplicationContextUtil.getBean(NaCodePathComplieDao.class);//
		NaCodePathCompileResultDao naCodePathCompileResultDao = ApplicationContextUtil.getBean(NaCodePathCompileResultDao.class);
		
		String planId = (String) QuartzHelper.getValue(dataMap, PLAN_Id);
		String runOrder = (String) QuartzHelper.getValue(dataMap, RUN_ORDER);
		String url = (String) QuartzHelper.getValue(dataMap, URL);
		String name = (String) QuartzHelper.getValue(dataMap, NAME);
		String everment = (String) QuartzHelper.getValue(dataMap, EVENTMENTS);
		
		String result  = "";
		 result =	sv.getNaCodePathComplieResultFromBMC(url,Long.valueOf(planId) ,Long.valueOf(runOrder),name,everment);
		
		if(result.contains("success")){
			
			result = result.substring(result.length()-1, result.length());
			Long order = Long.parseLong(result);
			order=order+1;
			String value = "";
			if(order <6 ){
				NaSystemInterfaceAddress address = dao.findBySysNameAndServiceTypeAndExt2AndRunOrder("BMC", "COMPILE", name,order,everment);
				if(address != null){
					log.info("**********************BMC接口地址"+address.getInterAddress() + "?"
							+ address.getParamers()+"*********************");
					
					String info = HttpUtil.sendPostWithoutSSL(address.getInterAddress() + "?"
							+ address.getParamers(), "POST");
					
					log.info("**********************BMC返回信息"+info+"*********************");
					//解析BMC报文
					Document doc = ReaderXmlForDOM4J.getDocument(info);
					 value  = ReaderXmlForDOM4J.getTargetElement(doc.getRootElement(), "OperationResult", "value");
					
					log.info("**********************解析后地址"+value+"*********************");
					if(StringUtils.isBlank(value)){
						BusinessException.throwBusinessException("BMC传递的作业状态查询接口有问题");
					}
					NaCodePathComplie compline = complineDao.findByplanIdAndSysNameAndExt3(Long.valueOf(planId), name,everment);
					if(compline !=null){
						//更新.
						sv.updateCompileValue(BmcPathBefore+value+BmcPathAfter,Long.valueOf(planId),compline.getComplineNum(), name,order);
					QuartzHelper.stopJobScheduler(context);	
					 sv.getNaCodePathComplieResult(BmcPathBefore+value+BmcPathAfter, Long.valueOf(planId),order,name,everment); //编译
					}
				}
			}else if(order == 6){
				sv.validateEnvironment("", Long.valueOf(planId));
			}
			else{
				sv.checkComplineIsFinished(Long.valueOf(planId) );
			}
		}
		
		
		
	}

}

