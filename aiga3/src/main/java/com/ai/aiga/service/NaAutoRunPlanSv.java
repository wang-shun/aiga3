package com.ai.aiga.service;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.NaAutoCaseDao;
import com.ai.aiga.dao.NaAutoCollGroupCaseDao;
import com.ai.aiga.dao.NaAutoRunPlanCaseDao;
import com.ai.aiga.dao.NaAutoRunPlanDao;
import com.ai.aiga.domain.NaAutoCase;
import com.ai.aiga.domain.NaAutoCollGroupCase;
import com.ai.aiga.domain.NaAutoRunPlan;
import com.ai.aiga.domain.NaAutoRunPlanCase;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.view.json.NaAutoRunPlanRequest;
import com.ai.aiga.view.json.PlanQueryUnconnectCaseRequest;

@Service
@Transactional

public class NaAutoRunPlanSv extends BaseService{
	@Autowired 
	private NaAutoRunPlanDao  naAutoRunPlanDao;
	
	@Autowired 
	private NaAutoRunPlanCaseDao naAutoRunPlanCaseDao;
	@Autowired
	private NaAutoCaseDao naAutoCaseDao;
	@Autowired
	private NaAutoCollGroupCaseDao naAutoCollGroupCaseDao;

	/**
	 * 保存自动化计划
	 * @param naAutoRunPlan
	 * @throws Exception 
	 */
	public void save(NaAutoRunPlanRequest  naAutoRunPlan) throws Exception{
		if(naAutoRunPlan==null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null,"NaAutoRunPlanRequest");
		}
		//计划编号不为空
		if(StringUtils.isBlank(naAutoRunPlan.getPlanTag())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null,"planTag");
		}
		//计划名称不为空
		if(StringUtils.isBlank(naAutoRunPlan.getPlanName())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null,"planName");
		}
		//计划轮询方式
		if(naAutoRunPlan.getCycleType()==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null,"cycleType");
		}
		//计划默认执行机
		if(StringUtils.isBlank(naAutoRunPlan.getMachineIp())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null,"machineIp");
		}
		//执行方式
		if(naAutoRunPlan.getRunType()==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null,"runType");
		}
		NaAutoRunPlan naAutoRunPlans = new NaAutoRunPlan();
		naAutoRunPlans.setMachineIp(naAutoRunPlan.getMachineIp());
		naAutoRunPlans.setPlanTag(naAutoRunPlan.getPlanTag());
		naAutoRunPlans.setRunType(naAutoRunPlan.getRunType());
		naAutoRunPlans.setPlanName(naAutoRunPlan.getPlanName());
		naAutoRunPlans.setCycleType(naAutoRunPlan.getCycleType());
		//如果planId存在就是修改，否则就是新增
		if(naAutoRunPlan.getPlanId()!=null){
			naAutoRunPlans.setPlanId(naAutoRunPlan.getPlanId());
			naAutoRunPlans.setUpdateTime(new Date());
			naAutoRunPlans.setCreateTime(new SimpleDateFormat().parse(naAutoRunPlan.getCreateTime()));
			naAutoRunPlan.setCreatorId(naAutoRunPlan.getCreatorId());
		}else{
			naAutoRunPlans.setCreateTime(new Date());
		}
		naAutoRunPlanDao.save(naAutoRunPlans);
	}
	
	/**
	 * 删除自动化计划
	 * @param planIds
	 */
	public void delete(String planIds){
		if(StringUtils.isBlank(planIds)){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null,"planIds");
		}
		String[] planId = planIds.split(",");
		for (String p : planId) {
			//删除自动化计划表
			naAutoRunPlanDao.delete(Long.parseLong(p));
			//删除自动化计划和用例关系表
			naAutoRunPlanDao.deleteNaAutoPunPlanCaseByPlanId(Long.parseLong(p));
		}
	}
	
	/**
	 * 关联自动化用例
	 * @param planId
	 * @param caseIds
	 */
	public void connectCase(Long planId ,String caseIds){
		String[] caseId = caseIds.split(",");
		for (String id : caseId) {
				//查询当前计划是否已经关联该用例
			List<NaAutoRunPlanCase>  naAutoRunPlanCases= naAutoRunPlanCaseDao.findByPlanIdAndAutoId(planId, Long.parseLong(id));
			if(naAutoRunPlanCases!=null&&naAutoRunPlanCases.size()>0){
				System.out.println("当前计划已经关联过该用例，无需再次关联");
			}else{
				//查询当前用例的信息
				NaAutoCase  naAutoCase = naAutoCaseDao.findByAutoId(Long.parseLong(id));
				NaAutoRunPlanCase  naAutoRunPlanCase = new NaAutoRunPlanCase();
				naAutoRunPlanCase.setAutoId(Long.parseLong(id));
				naAutoRunPlanCase.setPlanId(planId);
				naAutoRunPlanCase.setEnvironmentType(naAutoCase.getEnvironmentType());
				naAutoRunPlanCaseDao.save(naAutoRunPlanCase);
			}
		}		
	}
	
	
	/**
	 * 关联自动化用组
	 * @param planId当前计划id
	 * @param groupIds 要关联的用例组id
	 */
	public void connectGroup(Long planId ,String groupIds){
		String[] groupId = groupIds.split(",");
		for (String id : groupId) {
					//查询当前计划是否已经关联用例组
				List<NaAutoRunPlanCase>  naAutoRunPlanCases= naAutoRunPlanCaseDao.findByPlanIdAndGroupId(planId, Long.parseLong(id));
				if(naAutoRunPlanCases!=null&&naAutoRunPlanCases.size()>0){
					System.out.println("当前计划已经关联过该用例组，无需再次关联");
				}else{
					//查询当前用例组包含的用例的信息
					String sql = "    select b.auto_id, b.Environment_type,a.Group_order  \n"
										+" 	From na_auto_group_case a , na_auto_case b \n"
										+" 	 Where a.auto_id = b.auto_id(+)  \n"
										+" 	And a.group_id= "+Long.parseLong(id);
					System.out.println("查询用例信息"+sql);
					List caseInfos = naAutoRunPlanCaseDao.searchformSQL(sql);
					if(caseInfos!=null&&caseInfos.size()>0){
						//查询当前计划下组顺序
					Object count = naAutoRunPlanCaseDao.findCountByPlanIdAndGroupId(planId, Long.parseLong(id));
						for (int i=0;i<caseInfos.size();i++) {
							Object[] caseInfo = (Object[])caseInfos.get(i);
							NaAutoRunPlanCase  naAutoRunPlanCase = new NaAutoRunPlanCase();
							naAutoRunPlanCase.setAutoId(Long.parseLong(caseInfo[0].toString()));
							naAutoRunPlanCase.setPlanId(planId);
							naAutoRunPlanCase.setEnvironmentType(Long.parseLong(caseInfo[1].toString()));
							naAutoRunPlanCase.setGroupId(Long.parseLong(id));
							naAutoRunPlanCase.setSortGroup(Long.parseLong(caseInfo[2].toString()));
							naAutoRunPlanCase.setSortNumber(Long.parseLong(count.toString())+i+1);
							naAutoRunPlanCaseDao.save(naAutoRunPlanCase);
						}	
					}	
				}
		}		
	}
	

	
	/**
	 * 关联自动化用集
	 * @param planId 当前计划编号
	 * @param caseIds 用例集id
	 */
	public void connectCollect(Long planId ,String collectIds){
		String[] collectId = collectIds.split(",");
	   for (String id : collectId) {
		//查询当前用例集里面的自动化用例信息和用例集信息
		   List<NaAutoCollGroupCase> listCollectInfo =  naAutoCollGroupCaseDao.findByCollectId(Long.parseLong(id));
		   for (NaAutoCollGroupCase naAutoCollGroupCase : listCollectInfo) {
			   //如果是自动化用例(手工用例不做处理)
			if(naAutoCollGroupCase.getElementType()==2){
				connectCase(planId,String.valueOf(naAutoCollGroupCase.getElementId()));
			}
			//如果是用例组
			else if(naAutoCollGroupCase.getElementType()==0){
				connectGroup(planId,String.valueOf(naAutoCollGroupCase.getElementId()));
			}
		}
   	 }	
	}
	
	
	

/**
 * 
 * @param planId 当前计划id
 * @return
 */
		public NaAutoRunPlan queryByPlanId(Long planId ){
	    if(planId==null){
	    	BusinessException.throwBusinessException(ErrorCode.Parameter_null,"planId");
	    }
			return   naAutoRunPlanDao.findByPlanId(planId);
		}
		
	
	/**
	 * 查询自动化自计划
	 * @param condition 查询条件
	 * @param page
	 * @param pageSize
	 * @return
	 */
		public Object query(NaAutoRunPlanRequest condition ,int page,int pageSize){
		String sql = "select  a.plan_id,a.plan_tag,a.plan_name,b.name,to_char(a.create_time,'yyyy-mm-dd hh24:mi:ss') ,to_char(a.update_time,'yyyy-mm-dd hh24:mi:ss'),a.cycle_type,a.machine_ip,a.run_type from na_auto_run_plan a ,aiga_staff b where a.creator_id = b.staff_id(+) ";
		if(!StringUtils.isBlank(condition.getPlanName())){
			sql += " and a.plan_name like '%"+condition.getPlanName()+"%'";
		}
		if(condition.getRunType()!=null){
			sql += " and a.run_type = "+condition.getRunType();
		}
		if(!StringUtils.isBlank(condition.getCreateTime())){
			sql += " and to_char(a.create_time,'yyyy-mm-dd') > '"+condition.getCreateTime()+"'";
		}
		if(!StringUtils.isBlank(condition.getUpdateTime())){
			sql += " and to_char(a.create_time,'yyyy-mm-dd') < '"+condition.getUpdateTime()+"'";
		}
		System.out.println("查询自动化计划sql"+sql);
		List results = new ArrayList<String>();
		results.add("planId");
		results.add("planTag");
		results.add("planName");
		results.add("creatorId");
		results.add("createTime");
		results.add("updateTime");
		results.add("cycleType");
		results.add("machineIp");
		results.add("runType");
		if (page < 0) {
			page = 0;
		}
		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}
		Pageable pageable = new PageRequest(page, pageSize);
		return   naAutoRunPlanDao.searchByNativeSQL(sql, pageable, results);
		}

/**
 * 查询已经关联用例
 * @param planId  当前计划id
 * @param caseName 用例名称
 * @param page
 * @param pageSize
 */
	public Object queryConnectCase(Long  planId ,String caseName,int page,int pageSize){
		StringBuilder s = new StringBuilder();
		s.append("  Select a.auto_id, \n");
		s.append("  a.test_id, \n");
		s.append("   a.Temp_id, \n");
		s.append("   a.Case_type, \n");
		s.append("   a.auto_name, \n");
		s.append("   b.sys_name         as sys_id, \n");
		s.append("   c.sys_name         as sub_sys_id,\n");
		s.append("    d.sys_name           as func_id,\n");
		s.append("    e.busi_name        as busi_id,\n");
		s.append("	    a.important,\n");
		s.append("	    a.Environment_type \n");
		s.append("  From na_auto_case a \n");
		s.append("  left join aiga_system_folder b\n");
		s.append(" on a.sys_id = b.sys_id\n");
		s.append("  left join aiga_sub_sys_folder c \n");
		s.append("  on a.Sys_sub_id = c.subsys_id \n ");
		s.append("  left join aiga_fun_folder d\n ");
		s.append("  on a. Fun_id = d.fun_id\n ");
		s.append("  left join na_business e\n ");
		s.append("    on a. busi_id = e.busi_id\n ");
		s.append(" Where exists\n ");
		s.append(" (select * from na_auto_run_plan_case f where a.auto_id = f.auto_id and f.plan_id="+planId+") \n ");
		s.append("   and a.Has_auto = 1\n ");
		s.append("   and a.status = 1   ");
	if(!StringUtils.isBlank(caseName)){
		s.append("  and a.auto_name like '%"+caseName+"%'");
	}
	System.out.println("已关联的用例sql"+s.toString());
	List results = new ArrayList<String>();
	results.add("autoId");
	results.add("testId");
	results.add("tempId");
	results.add("caseType");
	results.add("autoName");
	results.add("sysId");
	results.add("sysSubId");
	results.add("funcId");
	results.add("busiId");
	results.add("important");
	results.add("environmentType");
	if (page < 0) {
		page = 0;
	}
	if (pageSize <= 0) {
		pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
	}
	Pageable pageable = new PageRequest(page, pageSize);
   return naAutoRunPlanCaseDao.searchByNativeSQL(s.toString(), pageable, results);
	}
	
	
	/**
	 * 查询当前计划未关联的用例
	 * @param request  查询字段
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public Object queryUnconnectCase(PlanQueryUnconnectCaseRequest request,int page,int pageSize){
	if(request==null){
		BusinessException.throwBusinessException(ErrorCode.Parameter_null,"request");
	}
	//当前计划id不能为空
	if(StringUtils.isBlank(String.valueOf(request.getPlanId()))){
		BusinessException.throwBusinessException(ErrorCode.Parameter_null,"planId");
	}
	//未关联的用例信息
	String sql = " Select a.auto_id, a.test_id,a.Temp_id,a.Case_type,a.auto_name,a. Environment_type,b. Group_order \n"
						+"	 From na_auto_group_case a , na_auto_case b  \n"
						+"  Where a.auto_id = b.auto_id \n"
						+"  And a.plan_id = \n "
						+" and Has_auto =1 \n"
						+" and status =1 ";
	if(!StringUtils.isBlank(request.getCaseName())){
		sql += " and auto_name like '%"+request.getCaseName()+"%'";
	}
	if(!StringUtils.isBlank(String.valueOf(request.getSysName()))){
		sql += " and  sys_id="+request.getSysName();
	}
	if(StringUtils.isBlank(String.valueOf(request.getSubSysName()))){
		sql += " and sys_sub_id = "+request.getSubSysName();
	}
	if(StringUtils.isBlank(String.valueOf(request.getFuncName()))){
		sql += " and fun_id = "+request.getSubSysName();
	}
	System.out.println("未关联的用例sql"+sql);
	List results = new ArrayList<String>();
	results.add("autoId");
	results.add("testId");
	results.add("tempId");
	results.add("caseType");
	results.add("autoName");
	results.add("sysId");
	results.add("sysSubId");
	results.add("busiId");
	results.add("funcId");
	results.add("important");
	results.add("environmentType");
	results.add("autoDesc");
	if (page < 0) {
		page = 0;
	}
	if (pageSize <= 0) {
		pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
	}
	Pageable pageable = new PageRequest(page, pageSize);
   return naAutoRunPlanCaseDao.searchByNativeSQL(sql, pageable, results);
	}
	
	
	
	/**
	 * 删除当前计划关联的用例
	 * @param planId 当前计划id
	 * @param caseIds 要删除的用例id
	 */
	public void  deleteConnectCase(Long planId,  String caseIds){
		if(StringUtils.isBlank(String.valueOf(planId))){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null,"planId");
		}
		if(StringUtils.isBlank(caseIds)){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null,"caseIds");
		}
		naAutoRunPlanCaseDao.deleteByPlanIdAndCaseId(planId, caseIds);
	}
	
	

	/**
	 * 删除当前计划关联的用例
	 * @param planId 当前计划id
	 * @param groupIds 要删除的用例组id
	 */
	public void  deleteConnectGroup(Long planId ,  String groupIds){
		if(StringUtils.isBlank(String.valueOf(planId))){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null,"planId");
		}
		if(StringUtils.isBlank(groupIds)){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null,"groupIds");
		}
		naAutoRunPlanCaseDao.deleteByPlanIdAndGroupId(planId, groupIds);
	}
	
	


/**
 * 查询当前计划未关联的用例组信息
 * @param planId 当前计划id
 * @param groupName 用例组名称
 * @return
 */
	public Object  queryConnectGroup( Long planId,String groupName,int page,int pageSize){
		String sql = "select a.group_id,a.group_name,a.creator_id,a.update_id,a.update_time from na_auto_group a where   exists (select * from  na_auto_run_plan_case b where a.group_id = b.group_id)";
		if(!StringUtils.isBlank(groupName)){
			sql += " and a.group_name like '%"+groupName+"%'";
		}
		List result = new ArrayList<String>();
		result.add("groupId");
		result.add("groupName");
		result.add("creatorId");
		result.add("updateId");
		result.add("updateTime");
		if(page<0){
			page = 0;
		}
		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}
		Pageable pageable = new PageRequest(page,pageSize);
	return 	naAutoRunPlanCaseDao.searchByNativeSQL(sql, pageable, result);
	}
	
	
	
	/**
	 *删除当前计划关联的用例集
	 * @param planId 当前计划id
	 * @param collectIds 删除的用例集Id
	 */
	public void  deleteConnectCollect(Long planId,  String collectIds){
		if(StringUtils.isBlank(String.valueOf(planId))){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null,"planId");
		}
		if(StringUtils.isBlank(collectIds)){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null,"collectIds");
		}
		naAutoRunPlanCaseDao.deleteByPlanIdAndCollcetId(planId, collectIds);
	}
	
	
	/**
	 * 查询当前计划已关联的用例集信息
	 * @param planId 当前计划id
	 * @param groupName 用例集名称
	 * @return
	 */
		public Object  queryConnectCollect(Long  planId , String collectName,int page,int pageSize){
			if(StringUtils.isBlank(String.valueOf(planId))){
				BusinessException.throwBusinessException(ErrorCode.Parameter_null,"planId");
			}
			String sql = "select a.COLLECT_ID,a.COLLECT_NAME,a.OP_ID,a.CREATE_DATE,a.REPAIRS_ID, CASE_TYPE from na_auto_collection  a where  exists (select * from  na_auto_run_plan_case b where a.collect_id = b.collect_id)";
			if(!StringUtils.isBlank(collectName)){
				sql += " and a.collect_name like '%"+collectName+"%'";
			}
			List result = new ArrayList<String>();
			result.add("collectId");
			result.add("collectName");
			result.add("opId");
			result.add("createDate");
			result.add("repairsId");
			result.add("caseType");
			if(page<0){
				page = 0;
			}
			if (pageSize <= 0) {
				pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
			}
			Pageable pageable = new PageRequest(page,pageSize);
		return 	naAutoRunPlanCaseDao.searchByNativeSQL(sql, pageable, result);
		}
		

		

			
	
}