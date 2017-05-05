package com.ai.aiga.service.onlineProcess;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.NaChangePlanOnileDao;
import com.ai.aiga.dao.NaProcessNodeRecordDao;
import com.ai.aiga.domain.NaProcessNodeRecord;
import com.ai.aiga.domain.NaTeamInfo;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.onlineProcess.dto.NodeRecordRequest;
import com.ai.aiga.view.controller.team.dto.TeamInfoRequest;

@Service
@Transactional
public class NodeRecordSv extends BaseService{
	
	@Autowired
	private   NaProcessNodeRecordDao   naProcessNodeRecordDao;
	
	@Autowired
	private NaChangePlanOnileDao NaChangePlanOnileDao;
	
	 public NaProcessNodeRecord saveChangeBegin(String onlinePlanName){
		if (onlinePlanName==null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		     String info="";
			 NaProcessNodeRecord nodeRecord=new NaProcessNodeRecord();
			 info=onlinePlanName+"开始节点";
			 nodeRecord.setProcessName(info);
			 nodeRecord.setTime(new  Date(System.currentTimeMillis()));
			 nodeRecord.setNode(1L);
			 nodeRecord.setType(3L);
			 return naProcessNodeRecordDao.save(nodeRecord);
			 
		 }
	
	
	
	
	public NaProcessNodeRecord saveChangeInteraction(String onlinePlanName){
		    String info="";
			 NaProcessNodeRecord nodeRecord=new NaProcessNodeRecord();
			 info=onlinePlanName+"交互物评审节点";
			 nodeRecord.setProcessName(info);
			 nodeRecord.setTime(new  Date(System.currentTimeMillis()));
			 nodeRecord.setNode(2L);
			 nodeRecord.setType(3L);
			 return	 naProcessNodeRecordDao.save(nodeRecord);
	}
	
	public NaProcessNodeRecord saveCompile(String onlinePlanName){
		    String info="";
			 NaProcessNodeRecord nodeRecord=new NaProcessNodeRecord();
			 info=onlinePlanName+"编译发布节点";
			 nodeRecord.setProcessName(info);
		     nodeRecord.setTime(new  Date(System.currentTimeMillis()));
			 nodeRecord.setType(3L);
			 nodeRecord.setNode(3L);
			 return naProcessNodeRecordDao.save(nodeRecord);
			 
	}
	public NaProcessNodeRecord saveCheck (String onlinePlanName){
		        String info="";
			 NaProcessNodeRecord nodeRecord=new NaProcessNodeRecord();
			 info=onlinePlanName+"验收情况节点";
			 nodeRecord.setProcessName(info);
			nodeRecord.setTime(new  Date(System.currentTimeMillis()));
			 nodeRecord.setNode(4L);
			 nodeRecord.setType(3L);
			 return naProcessNodeRecordDao.save(nodeRecord);
	}
	public NaProcessNodeRecord saveReview(String onlinePlanName){
		String info="";
			 NaProcessNodeRecord nodeRecord=new NaProcessNodeRecord();
			 info=onlinePlanName+"评审节点";
			 nodeRecord.setProcessName(info);
			 nodeRecord.setTime(new  Date(System.currentTimeMillis()));
			 nodeRecord.setNode(5L);
			 nodeRecord.setType(3L);
			 return naProcessNodeRecordDao.save(nodeRecord);
	}	
	public NaProcessNodeRecord saveRelease(String onlinePlanName){
		String info="";
			 NaProcessNodeRecord nodeRecord=new NaProcessNodeRecord();
			 info=onlinePlanName+"发布节点";
			 nodeRecord.setProcessName(info);
			 nodeRecord.setTime(new  Date(System.currentTimeMillis()));
			 nodeRecord.setNode(6L);
			 nodeRecord.setType(3L);
			 return naProcessNodeRecordDao.save(nodeRecord);
	}
	public NaProcessNodeRecord saveVerification(String onlinePlanName){
		String info="";
	NaProcessNodeRecord nodeRecord=new NaProcessNodeRecord();
			 info=onlinePlanName+"验证节点";
			 nodeRecord.setProcessName(info);
			 nodeRecord.setTime(new  Date(System.currentTimeMillis()));
			 nodeRecord.setNode(7L);
			 nodeRecord.setType(3L);
			 return naProcessNodeRecordDao.save(nodeRecord);
			 
	}
	public NaProcessNodeRecord saveSummary(String onlinePlanName){
		String info="";
			 NaProcessNodeRecord nodeRecord=new NaProcessNodeRecord();
			 info=onlinePlanName+"总结节点";
			 nodeRecord.setProcessName(info);
			 nodeRecord.setTime(new  Date());
			 nodeRecord.setNode(8L);
			 nodeRecord.setType(3L);
			 return naProcessNodeRecordDao.save(nodeRecord);
			 
		 }
	
		
		


      public void  update(Long onlinePlan ,Long node){
    	  
    	  if (onlinePlan==null&&node==null) {
    			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
    		}
    	  if(!NaChangePlanOnileDao.exists(onlinePlan)){
      		BusinessException.throwBusinessException("传入的计划id不存在！");
      	}
    	  naProcessNodeRecordDao.update(onlinePlan, node);
      }
      
      //提交
    public void  commit(Long onlinePlan ,Long node){
    	  
    	if (onlinePlan==null&&node==null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
    	
    	if(!NaChangePlanOnileDao.exists(onlinePlan)){
    		BusinessException.throwBusinessException("传入的计划id不存在！");
    	}
    	
    	
    	naProcessNodeRecordDao.commit(onlinePlan, node); 
      }
    
   /* public void  update(Long onlinePlan ,Long node){
  	  
  	  if (onlinePlan==null&&node==null) {
  			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
  		}
  	  if(!NaChangePlanOnileDao.exists(onlinePlan)){
    		BusinessException.throwBusinessException("传入的计划id不存在！");
    	}
  	 NaProcessNodeRecord findByOnlinePlanAndNode = naProcessNodeRecordDao.findByOnlinePlanAndNode(onlinePlan, node);
  	findByOnlinePlanAndNode.setTime(new Date());
  	findByOnlinePlanAndNode.setType(1L);
  	naProcessNodeRecordDao.save(findByOnlinePlanAndNode);
  	
    }*/
}
