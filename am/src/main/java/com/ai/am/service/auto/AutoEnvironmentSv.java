package com.ai.am.service.auto;


import java.text.ParseException;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.am.constant.BusiConstant;
import com.ai.am.dao.NaAutoEnvironmentDao;
import com.ai.am.dao.NaAutoMachineDao;
import com.ai.am.dao.NaAutoMachineEnvDao;
import com.ai.am.dao.jpa.ParameterCondition;
import com.ai.am.domain.NaAutoEnvironment;
import com.ai.am.domain.NaAutoMachine;
import com.ai.am.domain.NaAutoMachineEnv;
import com.ai.am.exception.BusinessException;
import com.ai.am.exception.ErrorCode;
import com.ai.am.service.base.BaseService;
import com.ai.am.util.mapper.JsonUtil;
import com.ai.am.view.controller.auto.dto.NaAutoEnvironmentRequest;
import com.ai.am.view.controller.auto.dto.NaAutoMachineRequest;

@Service
@Transactional
public class AutoEnvironmentSv extends BaseService{
	@Autowired
	private NaAutoMachineEnvDao naAutoMachineEnvDao;
	@Autowired
	private NaAutoEnvironmentDao naAutoEnvironmentDao ;
	@Autowired
	private NaAutoMachineDao naAutoMachineDao;
	
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
	/*   if(StringUtils.isBlank(request.getSoId())){
		   BusinessException.throwBusinessException(ErrorCode.Parameter_null,"soId");
   }*/
	   /*if(StringUtils.isBlank(request.getSvnUrl())){
		   BusinessException.throwBusinessException(ErrorCode.Parameter_null,"svnUrl");
   }
	   if(StringUtils.isBlank(request.getSvnPassword())){
		   BusinessException.throwBusinessException(ErrorCode.Parameter_null,"svnPassword");
   }*/
	   /*if(StringUtils.isBlank(request.getSvnAccount())){
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
   }*/
	   if(StringUtils.isBlank(request.getRunEnv().toString())){
		   BusinessException.throwBusinessException(ErrorCode.Parameter_null,"runEnv");
   }
	  /* if(StringUtils.isBlank(request.getUpdateTime().toString())){
		   BusinessException.throwBusinessException(ErrorCode.Parameter_null,"updateTime");
   }*/
	   /*if(StringUtils.isBlank(request.getRegionId().toString())){
		   BusinessException.throwBusinessException(ErrorCode.Parameter_null,"regionId");
   }*/
	   /*if(StringUtils.isBlank(request.getDatabase())){
		   BusinessException.throwBusinessException(ErrorCode.Parameter_null,"database");
   }*/
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
		   
			   naAutoEnvironment.setSoId(request.getSoId());
	 
		   if(StringUtils.isNotBlank(request.getSvnUrl())){
			   naAutoEnvironment.setSvnUrl(request.getSvnUrl());
	   }
		 
			   naAutoEnvironment.setSvnPassword(request.getSvnPassword());
	
		 
			   naAutoEnvironment.setSvnAccount(request.getSvnAccount());
	
		  
			   naAutoEnvironment.setDbPassword(request.getDbPassword());
	 
		  
			   naAutoEnvironment.setDbAccount(request.getDbAccount());  
	
		  
			   naAutoEnvironment.setCreatorId(request.getCreatorId());

		  
			   naAutoEnvironment.setDatabase(request.getDatabase());
	 
		  
			   naAutoEnvironment.setRegionId(request.getRegionId());
	  
		   if(StringUtils.isNotBlank(request.getRunEnv().toString())){
			   naAutoEnvironment.setRunEnv(request.getRunEnv());
	   }
		  
			   naAutoEnvironment.setUpdateTime(new Date(System.currentTimeMillis()));
		
		  
		   naAutoEnvironmentDao.save(naAutoEnvironment);
	   }
}
   public void deleteEnvironment(Long envId) {
		
		if(envId == null ){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "envId");
		}
		naAutoEnvironmentDao.delete(envId);
	
	}
 public List<NaAutoEnvironment> findall(){
	 return   naAutoEnvironmentDao.findAll();
 }
   public NaAutoEnvironment findone(Long envId){
	   if(envId == null ){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "envId");
		}
	   return naAutoEnvironmentDao.findOne(envId);
   }

	public Object listEnvironment(int pageNumber, int pageSize, NaAutoEnvironment condition) throws ParseException {
		List<String> list = new ArrayList<String>();
		list.add("envId");
		list.add("sysId");
		list.add("envName");
		list.add("envUrl");
		list.add("sysAccount");
		list.add("sysPassword");
		list.add("database");
		list.add("dbAccount");
		list.add("dbPassword");
		list.add("regionId");
		list.add("soId");
		list.add("svnUrl");
		list.add("svnAccount");
		list.add("svnPassword");
		list.add("envType");
		list.add("runEnv");
		list.add("creatorId");
		list.add("envCode");
		list.add("sysName");
		String sql = "select a.ENV_ID,a.SYS_ID,a.ENV_NAME,a.ENV_URL,a.SYS_ACCOUNT,a.SYS_PASSWORD,"
				+ "a.DATABASE,a.DB_ACCOUNT,a.DB_PASSWORD,a.REGION_ID,a.SO_ID,"
				+ "a.SVN_URL,a.SVN_ACCOUNT,a.SVN_PASSWORD,a.ENV_TYPE,a.RUN_ENV,a.CREATOR_ID,"
				+ "a.ENV_CODE,"
				+ "b.SYS_NAME from NA_AUTO_ENVIRONMENT a,AIGA_SYSTEM_FOLDER b"
				+ " where a.SYS_ID=b.SYS_ID";
		if (condition.getRunEnv() != null) {
			sql += " and a.run_env =" + condition.getRunEnv();
		}

		if (condition.getEnvName() != null && !condition.getEnvName().equals("")) {
			sql += " and a.ENV_NAME like '%" + condition.getEnvName() + "%'";
		}
		if (condition.getSysId() != null) {
			sql += " and a.SYS_ID =" + condition.getSysId();
		}
		if (pageNumber < 0) {
			pageNumber = 0;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);

		return naAutoEnvironmentDao.searchByNativeSQL(sql, pageable, list);
	}
   
   
   
   public void saveMachine(String machines,Long envId) {
		if (machines == null&&envId==null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		String[] machine=machines.split(",");
		for (int i = 0; i < machine.length; i++) {
				NaAutoMachineEnv naAutoMachineEnv=new NaAutoMachineEnv();
				naAutoMachineEnv.setEnvId(envId);
				naAutoMachineEnv.setMachineId(Long.valueOf((NumberUtils.toLong(machine[i]))));
				naAutoMachineEnvDao.save(naAutoMachineEnv);

			
		}
	}
	/**
	 * 根据环境ID获取环境信息并返回JSON串（供云桌面使用）
	 * @param envId
	 * @return
	 */
   public String getEnvById(String envId){
	   if (StringUtils.isBlank(envId)) {
	             BusinessException.throwBusinessException(ErrorCode.Parameter_null, "envId");
	   }
	   NaAutoEnvironment environment = naAutoEnvironmentDao.findOne(new Long(envId));
	   Map<String, String> json = new HashMap<String, String>();
	   if(environment !=null){
		   json.put("id", envId);
		   json.put("name", environment.getEnvName());
		   Map<String, String> arguments = new HashMap<String, String>();
		   arguments.put("url", environment.getEnvUrl());
		   arguments.put("sysCode", environment.getSysAccount());
		   arguments.put("sysPassword", environment.getSysPassword());
		   arguments.put("ENVIRON", environment.getEnvCode());
	   }

	   return JsonUtil.mapToJson(json);
   }
 
   
   public Page<NaAutoMachineRequest> selectall(int pageNumber, int pageSize, Long envId) {
	   if (envId == null ) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "envId");
		}
		
		StringBuilder sb = new StringBuilder("select a.machine_id,a.machine_ip,a.machine_name,"
				+ "a.status,a.machine_account,a.machine_password ,a.request_time,"
				+ "a.task_id from na_auto_machine a ,Na_Auto_Machine_Env b "
				+ "where a.machine_id=b.machine_id and b.env_id=:envId");
		
		List<ParameterCondition> params = new ArrayList<ParameterCondition>();
		
			params.add(new ParameterCondition("envId", envId));
		
  

		if (pageNumber < 0) {
			pageNumber = 0;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);

		return naAutoMachineDao.searchByNativeSQL(sb.toString(), params, NaAutoMachineRequest.class, pageable);
	}

   
   

   
   public void  delrel(Long envId,String machineId){
	   if (envId==null&&machineId==null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
	   String[] split = machineId.split(",");
	   for (int i = 0; i < split.length; i++) {
          
		   naAutoMachineEnvDao.delrel(envId, Long.valueOf((NumberUtils.toLong(split[i]))));

		}
   }
   public void  delectrel(Long machineId,String envId){
	   if (envId==null&&machineId==null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
	   String[] split = envId.split(",");
	   for (int i = 0; i < split.length; i++) {
          
		   naAutoMachineEnvDao.delectrel(machineId, Long.valueOf((NumberUtils.toLong(split[i]))));

		}
   }
  
	public Object list(int pageNumber, int pageSize, NaAutoMachine condition,Long envId) throws ParseException {
		List<String> list = new ArrayList<String>();
		list.add("machineId");
		list.add("machineIp");
		list.add("machineName");
		list.add("status");
	
		String sql ="select machine_id,machine_ip,machine_name,status from na_auto_machine where machine_id not in (select distinct(a.machine_id) "
				+ "from na_auto_machine_env a where  a.env_id="+envId+")";
		
		
		if (pageNumber < 0) {
			pageNumber = 0;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);

		return naAutoMachineDao.searchByNativeSQL(sql, pageable, list);
		
	}
   
	 public Page<NaAutoEnvironment> select(int pageNumber, int pageSize, Long machineId) {
		   if (machineId == null ) {
				BusinessException.throwBusinessException(ErrorCode.Parameter_null, "machineId");
			}
			
			StringBuilder sb = new StringBuilder(" select a.* from Na_Auto_Environment a,Na_Auto_Machine_Env b where  "
					+ "b.env_Id=a.env_Id and b.machine_id=:machineId");
			
			List<ParameterCondition> params = new ArrayList<ParameterCondition>();
			
				params.add(new ParameterCondition("machineId", machineId));
			
	  

			if (pageNumber < 0) {
				pageNumber = 0;
			}

			if (pageSize <= 0) {
				pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
			}

			Pageable pageable = new PageRequest(pageNumber, pageSize);

			return naAutoEnvironmentDao.searchByNativeSQL(sb.toString(), params, NaAutoEnvironment.class, pageable);
		}

}