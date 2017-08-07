package com.ai.am.service.auto;


import java.text.ParseException;
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

import com.ai.am.constant.BusiConstant;
import com.ai.am.dao.NaAutoEnvironmentDao;
import com.ai.am.dao.NaAutoMachineDao;
import com.ai.am.dao.NaAutoMachineEnvDao;
import com.ai.am.dao.jpa.Condition;
import com.ai.am.domain.NaAutoEnvironment;
import com.ai.am.domain.NaAutoMachine;
import com.ai.am.domain.NaAutoMachineEnv;
import com.ai.am.exception.BusinessException;
import com.ai.am.exception.ErrorCode;
import com.ai.am.service.base.BaseService;
import com.ai.am.service.enums.AutoRunEnum;
import com.ai.am.util.DateUtil;
import com.ai.am.view.controller.auto.dto.NaAutoMachineEnvRequest;
import com.ai.am.view.controller.auto.dto.NaAutoMachineRequest;
@Service
@Transactional
public class AutoMachineSv extends BaseService {
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

	public Object list(int pageNumber, int pageSize, NaAutoEnvironment condition,Long machineId) throws ParseException {
		List<String> list = new ArrayList<String>();
		list.add("envId");
		list.add("sysName");
		list.add("envName");
		list.add("envUrl");

		String sql ="select c.env_id,b.sys_name,c.env_name,c.env_url from na_auto_environment c,AIGA_SYSTEM_FOLDER b where env_id not in  "
				+ "(select distinct(a.env_id) from na_auto_machine_env a where a.machine_id="+machineId+") and c.SYS_ID=b.SYS_ID";


		if (pageNumber < 0) {
			pageNumber = 0;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);

		return naAutoEnvironmentDao.searchByNativeSQL(sql, pageable, list);

	}
	
	/**
	 * 根据机器IP修改机器状态为空闲
	 * @param machineIp 机器IP
	 * @return NaAutoMachine
	 */
	public NaAutoMachine updateMachineStatusToFree(String machineIp){
		return this.updateMachineStatus(machineIp,AutoRunEnum.MachineStatus_free.getValue());
	}

	/**
	 * 根据机器IP修改机器状态为占用
	 * @param machineIp 机器IP
	 * @return NaAutoMachine
	 */
	public NaAutoMachine updateMachineStatusToOn(String machineIp){
		return this.updateMachineStatus(machineIp,AutoRunEnum.MachineStatus_on.getValue());
	}

	/**
	 * 根据机器IP修改机器状态为离线
	 * @param machineIp
	 */
	public NaAutoMachine updateMachineStatusToOff(String machineIp){
		return this.updateMachineStatus(machineIp,AutoRunEnum.MachineStatus_off.getValue());
	}

	/**
	 * 根据机器响应时间修改机器状态
	 */
	public void updateMachineStatusByRequestTime(){
		List<NaAutoMachine> list=this.naAutoMachineDao.findAll();
		for (NaAutoMachine machine:list){
			Date requestTime=machine.getRequestTime();
			Date validateTime= new Date(System.currentTimeMillis()-1000*60*10);
			//是否超时
			boolean isTimeOut = requestTime != null && requestTime.before(validateTime);
			//是否离线
			boolean isOff = machine.getStatus().equals(AutoRunEnum.MachineStatus_off.getValue());
			//如果超时且不是离线，则修改机器状态为离线
			if (isTimeOut && !isOff) {
				this.updateMachineStatusToOff(machine.getMachineIp());
			}
			//如果未超时且是离线，则修改机器状态为空闲(未防止多线程处理出现将占用中变成空闲，故此代码注释)
			/*if (!isTimeOut && isOff) {
				this.updateMachineStatusToFree(machine.getMachineIp());
			}*/
		}
	}

	/**
	 * 实时更新机器响应时间和状态
	 * @param machineIp
	 */
	public void updateRequestTimeByMachineIp(String machineIp){
		NaAutoMachine autoMachine=this.naAutoMachineDao.findByMachineIp(machineIp);
		autoMachine.setRequestTime(DateUtil.getCurrentTime());
		boolean isOff=autoMachine.getStatus().equals(AutoRunEnum.MachineStatus_off.getValue());
		//如果该机器离线，则改为空闲
		if (isOff) {
			autoMachine.setStatus(AutoRunEnum.MachineStatus_free.getValue());
		}
		this.naAutoMachineDao.save(autoMachine);
	}

	/**
	 * 根据机器IP、状态修改
	 * @param machineIp
	 * @param status
	 * @return
	 */
	private NaAutoMachine updateMachineStatus(String machineIp,Long status){
		if (StringUtils.isBlank(machineIp)) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "machineIp");
		}
		NaAutoMachine autoMachine=this.naAutoMachineDao.findByMachineIp(machineIp);
		autoMachine.setStatus(status);
		return this.naAutoMachineDao.save(autoMachine);
	}
}
