package com.ai.aiga.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.ai.aiga.dao.NaAutoMachineDao;
import com.ai.aiga.dao.NaAutoMachineEnvDao;
import com.ai.aiga.domain.NaAutoEnvironment;
import com.ai.aiga.domain.NaAutoMachine;
import com.ai.aiga.domain.NaAutoMachineEnv;
import com.ai.aiga.domain.NaUiCompCtrl;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.json.NaAutoEnvironmentRequest;
import com.ai.aiga.view.json.NaAutoMachineEnvRequest;
import com.ai.aiga.view.json.NaAutoMachineRequest;
@Service
@Transactional
public class NaAutoMachineSv extends BaseService {
	@Autowired
    private NaAutoMachineDao naAutoMachineDao; 
	@Autowired
	private NaAutoMachineEnvDao naAutoMachineEnvDao;
	public NaAutoMachine saveMachine(NaAutoMachineRequest request){
		if(request==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		if(StringUtils.isBlank(request.getMachineIp())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "machineIp");
		}
		if(StringUtils.isBlank(request.getMachineName())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "machineName");
		}
		if(StringUtils.isBlank(request.getMachineAccount())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "machineAccount");
		}
		if(StringUtils.isBlank(request.getMachinePassword())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "machinePassword");
		}
		if(StringUtils.isBlank(request.getStatus().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "status");
		}
		if(StringUtils.isBlank(request.getRequestTime().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "requestTime");
		}
		if(StringUtils.isBlank(request.getTaskId().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "taskId");
		}
		NaAutoMachine naAutoMachine=new NaAutoMachine();
		naAutoMachine.setMachineAccount(request.getMachineAccount());
		naAutoMachine.setMachineIp(request.getMachineIp());
		naAutoMachine.setMachineName(request.getMachineName());
		naAutoMachine.setMachinePassword(request.getMachinePassword());
		naAutoMachine.setRequestTime(request.getRequestTime());
		naAutoMachine.setStatus(request.getStatus());
		naAutoMachine.setTaskId(request.getTaskId());
		naAutoMachineDao.save(naAutoMachine);
		return naAutoMachine;
		
	}
   public void deleteByMachineId(BigDecimal machineId){
	   if(machineId ==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
	   naAutoMachineDao.delete(machineId);
	   naAutoMachineEnvDao.deleteByMachineId(machineId);
	   
   }
   public void deleteByEnvId(BigDecimal envId){
	   if(envId ==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
	   naAutoMachineDao.delete(envId);
	   naAutoMachineEnvDao.deleteByEnvId(envId);
	   
   }
   public void delete(BigDecimal relaId){
	   if(relaId ==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
	   naAutoMachineEnvDao.delete(relaId);
	   
   }
   public NaAutoMachine findone(BigDecimal machineId){
	   if(machineId ==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
	   
	return naAutoMachineDao.findOne(machineId);
	   
   }
   public NaAutoMachineEnv findOneMachinEnv(BigDecimal relaId){
	   if(relaId ==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
	   return naAutoMachineEnvDao.findOne(relaId);
   }
   public List<NaAutoMachine> listMachine(){
	   return naAutoMachineDao.findAll();
   }
   public List<NaAutoMachineEnv> listMachineEnv(){
	   return naAutoMachineEnvDao.findAll();
   }
   public void  saveMachineEnv(BigDecimal machineId,BigDecimal envId){
	   if(machineId == null ){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(envId == null ){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		NaAutoMachineEnv naAutoMachineEnv=new NaAutoMachineEnv();
		naAutoMachineEnv.setMachineId(machineId);
		naAutoMachineEnv.setEnvId(envId);
		naAutoMachineEnvDao.save(naAutoMachineEnv);
	   
   }
   public void updateMachine (NaAutoMachineRequest request){
	   if(request==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
	   NaAutoMachine naAutoMachine=naAutoMachineDao.findOne(request.getMachineId());
	   if(naAutoMachine==null){
		   BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
	   }
	   if(naAutoMachine!=null){
		   if(StringUtils.isNotBlank(request.getMachineIp())){
			   naAutoMachine.setMachineIp(request.getMachineIp());
			}
			if(StringUtils.isNotBlank(request.getMachineName())){
				naAutoMachine.setMachineName(request.getMachineName());
			}
			if(StringUtils.isNotBlank(request.getMachineAccount())){
				naAutoMachine.setMachineAccount(request.getMachineAccount());

			}
			if(StringUtils.isNotBlank(request.getMachinePassword())){
				naAutoMachine.setMachinePassword(request.getMachinePassword());

			}
			if(StringUtils.isNotBlank(request.getStatus().toString())){
				naAutoMachine.setStatus(request.getStatus());

			}
			if(StringUtils.isNotBlank(request.getRequestTime().toString())){
				naAutoMachine.setRequestTime(request.getRequestTime());
			}
			if(StringUtils.isNotBlank(request.getTaskId().toString())){
				naAutoMachine.setTaskId(request.getTaskId());
			}
			
			naAutoMachineDao.save(naAutoMachine);
	   }
	   
   }
   public NaAutoMachineEnv saveMachineEnvall(NaAutoMachineEnvRequest request){
	   if(request==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
	   if(StringUtils.isBlank(request.getEnvId().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "envId");
		}
		if(StringUtils.isBlank(request.getMachineId().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "machineId");
		}
		NaAutoMachineEnv naAutoMachineEnv=new NaAutoMachineEnv();
		naAutoMachineEnv.setEnvId(request.getEnvId());
		naAutoMachineEnv.setMachineId(request.getMachineId());
		naAutoMachineEnv.setUpdateTime(request.getUpdateTime());
		naAutoMachineEnv.setCreatorId(request.getCreatorId());
		naAutoMachineEnvDao.save(naAutoMachineEnv);
	    return naAutoMachineEnv;
	   
   }
   public void updateMachineEnv(NaAutoMachineEnvRequest request){
	   if(request==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
	   NaAutoMachineEnv naAutoMachineEnv=naAutoMachineEnvDao.findOne(request.getRelaId());
	   if(naAutoMachineEnv==null){
		   BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
	   }
	   if(naAutoMachineEnv!=null){
		   if(StringUtils.isNotBlank(request.getEnvId().toString())){
			   naAutoMachineEnv.setEnvId(request.getEnvId());
			}
			if(StringUtils.isNotBlank(request.getMachineId().toString())){
				naAutoMachineEnv.setMachineId(request.getMachineId());
			}
			
			naAutoMachineEnv.setCreatorId(request.getCreatorId());
			naAutoMachineEnv.setUpdateTime(new Date(System.currentTimeMillis()));
			naAutoMachineEnvDao.save(naAutoMachineEnv);
	   }
   }
   public void saveMachineandEnv(NaAutoMachineRequest request,String envIds){
	   NaAutoMachine naAutoMachine=saveMachine(request);
	   if(naAutoMachine == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(naAutoMachine.getMachineId()==null)  { 
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
		if(envIds != null && !envIds.equals("")){
			String[] envId = envIds.split(",");
			for(int i = 0; i < envId.length; i++){
				NaAutoMachineEnv naAutoMachineEnv = new NaAutoMachineEnv();
				naAutoMachineEnv.setMachineId(naAutoMachine.getMachineId());
				if(NumberUtils.toLong(envId[i])==0){
					BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
				}
				if(NumberUtils.toLong(envId[i])!=0){
				naAutoMachineEnv.setEnvId(BigDecimal.valueOf((NumberUtils.toLong(envId[i]))));
				}
				
				naAutoMachineEnvDao.save(naAutoMachineEnv);
			}
		}

	   
   }
   
  

}
