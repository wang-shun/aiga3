package com.ai.aiga.service.onlineProcess;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.NaChangePlanOnileDao;
import com.ai.aiga.dao.NaProcessNodeRecordDao;
import com.ai.aiga.domain.NaProcessNodeRecord;
import com.ai.aiga.domain.NaTeamInfo;
import com.ai.aiga.domain.SysRole;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.controller.onlineProcess.dto.NodeRecordRequest;
import com.ai.aiga.view.controller.role.dto.Role;
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
    
    public  List<SysRole>  findRole(Long staffId){
    	if (staffId==null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
    	List<Object[]> list = naProcessNodeRecordDao.findRole(staffId);
    	List<SysRole>  sys= new ArrayList<SysRole>(list.size());
    	for(int i=0;i<list.size();i++){
    		Object[] object = (Object[]) list.get(i);
    		SysRole sysRole=new SysRole();
    		sysRole.setCode(object[0].toString());
    		sysRole.setName(object[1].toString());
    		sysRole.setNotes(object[2].toString());
    		//sysRole.setState((Byte)object[3]);
    		sysRole.setDoneCode((Long)object[4]);
    		sysRole.setCreateDate((Date)object[5]);
    		sysRole.setDoneDate((Date)object[6]);
    		sysRole.setValidDate((Date)object[7]);
    		sysRole.setExpireDate((Date)object[8]);
    		sysRole.setOpId((Long)object[9]);
    		sysRole.setOrgId((Long)object[10]);
    		sys.add(sysRole);
    	}
		return sys;	
    }
}
