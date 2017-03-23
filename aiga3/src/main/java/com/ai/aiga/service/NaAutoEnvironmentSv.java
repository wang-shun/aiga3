package com.ai.aiga.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.NaAutoEnvironmentDao;
import com.ai.aiga.dao.NaAutoMachineEnvDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.domain.NaAutoEnvironment;
import com.ai.aiga.domain.NaAutoMachineEnv;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.json.NaAutoEnvironmentRequest;

@Service
@Transactional
public class NaAutoEnvironmentSv extends BaseService{
	@Autowired
	private NaAutoMachineEnvDao naAutoMachineEnvDao;
	@Autowired
	private NaAutoEnvironmentDao naAutoEnvironmentDao ;
   public NaAutoEnvironment saveEnvironment(NaAutoEnvironmentRequest request){
	   if(request==null){
		   BusinessException.throwBusinessException(ErrorCode.Parameter_null);
	   }
	   if(StringUtils.isBlank(request.getEnvName())){
		   BusinessException.throwBusinessException(ErrorCode.Parameter_null,"envName");
   }
	   if(StringUtils.isBlank(request.getEnvType().toString())){
		   BusinessException.throwBusinessException(ErrorCode.Parameter_null,"envType");
   }
	   if(StringUtils.isBlank(request.getSysId().toString())){
		   BusinessException.throwBusinessException(ErrorCode.Parameter_null,"sysId");
   }
	   if(StringUtils.isBlank(request.getEnvUrl())){
		   BusinessException.throwBusinessException(ErrorCode.Parameter_null,"envUrl");
   }
	   if(StringUtils.isBlank(request.getSysAccount())){
		   BusinessException.throwBusinessException(ErrorCode.Parameter_null,"sysAccount");
   }
	   if(StringUtils.isBlank(request.getSysPassword())){
		   BusinessException.throwBusinessException(ErrorCode.Parameter_null,"sysPassword");
   }
	   if(StringUtils.isBlank(request.getSoId())){
		   BusinessException.throwBusinessException(ErrorCode.Parameter_null,"soId");
   }
	   if(StringUtils.isBlank(request.getSvnUrl())){
		   BusinessException.throwBusinessException(ErrorCode.Parameter_null,"svnUrl");
   }
	   if(StringUtils.isBlank(request.getSvnPassword())){
		   BusinessException.throwBusinessException(ErrorCode.Parameter_null,"svnPassword");
   }
	   if(StringUtils.isBlank(request.getSvnAccount())){
		   BusinessException.throwBusinessException(ErrorCode.Parameter_null,"svnAccount");
   }
	   if(StringUtils.isBlank(request.getDbPassword())){
		   BusinessException.throwBusinessException(ErrorCode.Parameter_null,"dbPassword");
   }
	   if(StringUtils.isBlank(request.getDbAccount())){
		   BusinessException.throwBusinessException(ErrorCode.Parameter_null,"dbAccount");
   }
	   if(StringUtils.isBlank(request.getCreatorId().toString())){
		   BusinessException.throwBusinessException(ErrorCode.Parameter_null,"creatorId");
   }
	   if(StringUtils.isBlank(request.getRunEnv().toString())){
		   BusinessException.throwBusinessException(ErrorCode.Parameter_null,"runEnv");
   }
	   if(StringUtils.isBlank(request.getUpdateTime().toString())){
		   BusinessException.throwBusinessException(ErrorCode.Parameter_null,"updateTime");
   }
	   if(StringUtils.isBlank(request.getRegionId().toString())){
		   BusinessException.throwBusinessException(ErrorCode.Parameter_null,"regionId");
   }
	   if(StringUtils.isBlank(request.getDatabase())){
		   BusinessException.throwBusinessException(ErrorCode.Parameter_null,"database");
   }
	   NaAutoEnvironment naAutoEnvironment =new NaAutoEnvironment();
	   naAutoEnvironment.setCreatorId(request.getCreatorId());
	   naAutoEnvironment.setDatabase(request.getDatabase());
	   naAutoEnvironment.setDbAccount(request.getDbAccount());
	   naAutoEnvironment.setDbPassword(request.getDbPassword());
	   naAutoEnvironment.setEnvName(request.getEnvName());
	   naAutoEnvironment.setEnvType(request.getEnvType());
	   naAutoEnvironment.setEnvUrl(request.getEnvUrl());
	   naAutoEnvironment.setRegionId(request.getRegionId());
	   naAutoEnvironment.setRunEnv(request.getRunEnv());
	   naAutoEnvironment.setSoId(request.getSoId());
	   naAutoEnvironment.setSvnAccount(request.getSvnAccount());
	   naAutoEnvironment.setSvnUrl(request.getSvnUrl());
	   naAutoEnvironment.setSvnPassword(request.getSvnPassword());
	   naAutoEnvironment.setUpdateTime(request.getUpdateTime());
	   naAutoEnvironment.setSysPassword(request.getSysPassword());
	   naAutoEnvironment.setSysId(request.getSysId());
	   naAutoEnvironment.setSysAccount(request.getSysAccount());
	   naAutoEnvironmentDao.save(naAutoEnvironment);
	   return naAutoEnvironment;
	   
}
   public void updateEnvironment(NaAutoEnvironmentRequest request){
	   if(request==null){
		   BusinessException.throwBusinessException(ErrorCode.Parameter_null);
	   }
	   NaAutoEnvironment naAutoEnvironment=naAutoEnvironmentDao.findOne(request.getEnvId());
	   if(naAutoEnvironment==null){		
		   BusinessException.throwBusinessException(ErrorCode.Parameter_null,"code");
		   }
	   
	   if(naAutoEnvironment!=null){
		   if(StringUtils.isNotBlank(request.getEnvName())){
			   naAutoEnvironment.setEnvName(request.getEnvName());
	   }
		   if(StringUtils.isNotBlank(request.getEnvType().toString())){
			   naAutoEnvironment.setEnvType(request.getEnvType());

	   }
		   if(StringUtils.isNotBlank(request.getSysId().toString())){
			   naAutoEnvironment.setSysId(request.getSysId());
	   }
		   if(StringUtils.isNotBlank(request.getEnvUrl())){
			   naAutoEnvironment.setEnvUrl(request.getEnvUrl());
	   }
		   if(StringUtils.isNotBlank(request.getSysAccount())){
			   naAutoEnvironment.setSysAccount(request.getSysAccount());
	   }
		   if(StringUtils.isNotBlank(request.getSysPassword())){
			   naAutoEnvironment.setSysPassword(request.getSysPassword());
	   }
		   if(StringUtils.isNotBlank(request.getSoId())){
			   naAutoEnvironment.setSoId(request.getSoId());
	   }
		   if(StringUtils.isNotBlank(request.getSvnUrl())){
			   naAutoEnvironment.setSvnUrl(request.getSvnUrl());
	   }
		   if(StringUtils.isNotBlank(request.getSvnPassword())){
			   naAutoEnvironment.setSvnPassword(request.getSvnPassword());
	   }
		   if(StringUtils.isNotBlank(request.getSvnAccount())){
			   naAutoEnvironment.setSvnAccount(request.getSvnAccount());
	   }
		   if(StringUtils.isNotBlank(request.getDbPassword())){
			   naAutoEnvironment.setDbPassword(request.getDbPassword());
	   }
		   if(StringUtils.isNotBlank(request.getDbAccount())){
			   naAutoEnvironment.setDbAccount(request.getDbAccount());  
	   }
		   if(StringUtils.isNotBlank(request.getCreatorId().toString())){
			   naAutoEnvironment.setCreatorId(request.getCreatorId());
	   }
		   if(StringUtils.isNotBlank(request.getDatabase())){
			   naAutoEnvironment.setDatabase(request.getDatabase());
	   }
		   if(StringUtils.isNotBlank(request.getRegionId().toString())){
			   naAutoEnvironment.setRegionId(request.getRegionId());
	   }
		   if(StringUtils.isNotBlank(request.getRunEnv().toString())){
			   naAutoEnvironment.setRunEnv(request.getRunEnv());
	   }
		   if(StringUtils.isNotBlank(request.getUpdateTime().toString()))
		   {
			   naAutoEnvironment.setUpdateTime(new Date(System.currentTimeMillis()));
			}
		  
		   naAutoEnvironmentDao.save(naAutoEnvironment);
	   }
}
   public void deleteEnvironment(BigDecimal envId) {
		
		if(envId == null ){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "envId");
		}
		naAutoEnvironmentDao.delete(envId);
	
	}
 public List<NaAutoEnvironment> findall(){
	 return   naAutoEnvironmentDao.findAll();
 }
   public NaAutoEnvironment findone(BigDecimal envId){
	   if(envId == null ){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "envId");
		}
	   return naAutoEnvironmentDao.findOne(envId);
   }
   public Object listEnvironment(int pageNumber, int pageSize ,NaAutoEnvironment condition ) throws ParseException {
		
		List<Condition> cons = new ArrayList<Condition>();
		
		if(condition != null){
			
			
			
			if(condition.getRunEnv()!= null){
				cons.add(new Condition("runEnv", condition.getRunEnv(), Condition.Type.EQ));
			}
			
			if(condition.getEnvName()!= null&&!condition.getEnvName().equals("")){
				cons.add(new Condition("envName","%".concat( condition.getEnvName()).concat("%"), Condition.Type.LIKE));
			}
			if(condition.getSysId()!= null){
				cons.add(new Condition("sysId", condition.getSysId(), Condition.Type.EQ));
			}
			
			
		}
		
		
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return naAutoEnvironmentDao.search(cons, pageable);
	}
   
   
   public void addMachineandEnv(NaAutoEnvironmentRequest  request,String machineIds){
	  
	   NaAutoEnvironment naAutoEnvironment=saveEnvironment(request);
	   if(naAutoEnvironment==null){
		   BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");   
	   }
	   if(naAutoEnvironment.getEnvId()==null)  { 
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
	   if(machineIds != null && !machineIds.equals("")){
			String[] machineId = machineIds.split(",");
			for(int i = 0; i < machineId.length; i++){
				NaAutoMachineEnv naAutoMachineEnv = new NaAutoMachineEnv();
				naAutoMachineEnv.setEnvId(naAutoEnvironment.getEnvId());
				if(NumberUtils.toLong(machineId[i])==0){
					BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
				}
				if(NumberUtils.toLong(machineId[i])!=0){
				naAutoMachineEnv.setMachineId(BigDecimal.valueOf((NumberUtils.toLong(machineId[i]))));
				}
				
				naAutoMachineEnvDao.save(naAutoMachineEnv);
			}
		}

	
	   
   }

}