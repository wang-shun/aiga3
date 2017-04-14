package com.ai.aiga.service;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ai.aiga.service.enums.AutoRunEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.NaAutoEnvironmentDao;
import com.ai.aiga.dao.NaAutoMachineDao;
import com.ai.aiga.dao.NaAutoMachineEnvDao;
import com.ai.aiga.dao.jpa.Condition;
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
	private NaAutoEnvironmentDao naAutoEnvironmentDao ;
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
   public void deleteByMachineId(Long machineId){
	   if(machineId ==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
	   naAutoMachineDao.delete(machineId);
	   naAutoMachineEnvDao.deleteByMachineId(machineId);
	   
   }
   public void deleteByEnvId(Long envId){
	   if(envId ==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
	   naAutoEnvironmentDao.delete(envId);
	 
	   naAutoMachineEnvDao.deleteByEnvId(envId);
	   
   }
   public void delete(Long relaId){
	   if(relaId ==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
	   naAutoMachineEnvDao.delete(relaId);
	   
   }
   public NaAutoMachine findone(Long machineId){
	   if(machineId ==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "code");
		}
	   
	return naAutoMachineDao.findOne(machineId);
	   
   }
   public NaAutoMachineEnv findOneMachinEnv(Long relaId){
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
   public void  saveMachineEnv(Long machineId,Long envId){
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
			
				naAutoMachine.setRequestTime(new Date(System.currentTimeMillis()));
		
			
				naAutoMachine.setTaskId(request.getTaskId());
			
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
				naAutoMachineEnv.setEnvId(Long.valueOf((NumberUtils.toLong(envId[i]))));
				}
				
				naAutoMachineEnvDao.save(naAutoMachineEnv);
			}
		}

	   
   }
/*   public void saveEnv(List<NaAutoEnvironment> list,Long machineId) {
		if (list == null&&machineId==null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		
		for (int i = 0; i < list.size(); i++) {

			NaAutoEnvironment  naAutoEnvironment= list.get(i);

			if (naAutoEnvironment != null) {
				NaAutoMachineEnv naAutoMachineEnv=new NaAutoMachineEnv();
				naAutoMachineEnv.setEnvId(naAutoEnvironment.getEnvId());
				naAutoMachineEnv.setMachineId(machineId);
				naAutoMachineEnvDao.save(naAutoMachineEnv);

			}
		}
	}*/
   public void saveEnv(String list,Long machineId) {
		if (list == null&&machineId==null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		String[] split = list.split(",");
		for (int i = 0; i < split.length; i++) {

			

			
				NaAutoMachineEnv naAutoMachineEnv=new NaAutoMachineEnv();
				naAutoMachineEnv.setEnvId(Long.valueOf((NumberUtils.toLong(split[i]))));
				naAutoMachineEnv.setMachineId(machineId);
				naAutoMachineEnvDao.save(naAutoMachineEnv);

			
		}
	}
   public Object listMachine(int pageNumber, int pageSize ,NaAutoMachine condition ) throws ParseException {
		
		List<Condition> cons = new ArrayList<Condition>();
		
		if(condition != null){
			
			if(condition.getMachineName()!= null&&!condition.getMachineName().equals("")){
				cons.add(new Condition("machineName","%".concat( condition.getMachineName()).concat("%"), Condition.Type.LIKE));
			}
			if(condition.getMachineIp()!= null&&!condition.getMachineIp().equals("")){
				cons.add(new Condition("machineIp", condition.getMachineIp(), Condition.Type.EQ));
			}
			if(condition.getStatus()!= null&&!condition.getStatus().equals("")){
				cons.add(new Condition("status", condition.getStatus(), Condition.Type.EQ));
			}
			
		}
		
		
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return naAutoMachineDao.search(cons, pageable);
	}

	/**
	 * 根据机器IP修改机器状态为空闲
	 * @param machineIp
	 * @return
	 */
	public NaAutoMachine updateMachineStatusToFree(String machineIp){
   		if (StringUtils.isBlank(machineIp)) {
   		          BusinessException.throwBusinessException(ErrorCode.Parameter_null, "machineIp");
   		}
   		NaAutoMachine autoMachine=this.naAutoMachineDao.findByMachineIp(machineIp);
   		autoMachine.setStatus(AutoRunEnum.MachineStatus_free.getValue());
   		return this.naAutoMachineDao.save(autoMachine);
	}
  

}
