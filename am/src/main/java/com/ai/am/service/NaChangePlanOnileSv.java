package com.ai.am.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.am.dao.AigaBossTestResultDao;
import com.ai.am.dao.NaChangePlanOnileDao;
import com.ai.am.dao.PlanDetailManifestDao;
import com.ai.am.domain.NaChangePlanOnile;
import com.ai.am.domain.PlanDetailManifest;
import com.ai.am.domain.SysRole;
import com.ai.am.exception.BusinessException;
import com.ai.am.exception.ErrorCode;
import com.ai.am.service.base.BaseService;
import com.ai.am.service.enums.WorkFlowNewEnum;
import com.ai.am.service.onlineProcess.NodeRecordSv;
import com.ai.am.util.DateUtil;
import com.ai.am.util.TimeUtil;
import com.ai.am.util.mapper.BeanMapper;
import com.ai.am.view.controller.plan.dto.PlanDetailManifestExcel;
import com.ai.am.view.json.NaChangePlanOnileRequest;
import com.ai.am.view.util.SessionMgrUtil;

@Service
@Transactional
public class NaChangePlanOnileSv extends BaseService{
	@Autowired
	private NaChangePlanOnileDao  naChangePlanOnileDao ;
	
	@Autowired
	private PlanDetailManifestDao planDetailManifestDao;
	
	@Autowired
	private NaChangePlanOnileSv   naChangePlanOnileSv;
	
	@Autowired
	private AigaBossTestResultDao aigaBossTestResultDao;
	
	@Autowired
	private    NodeRecordSv    nodeRecordSv;
	
	public NaChangePlanOnile saveChangePlanOnile(NaChangePlanOnileRequest request){
		if(request == null){ 
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		if(StringUtils.isBlank(request.getOnlinePlanName())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "onlinePlanName");
		}
		if(StringUtils.isBlank(request.getTypes().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "types");
		}
		NaChangePlanOnile naChangePlanOnile=new NaChangePlanOnile();

		naChangePlanOnile.setOnlinePlanName(request.getOnlinePlanName());
		naChangePlanOnile.setPlanDate(request.getPlanDate());
		naChangePlanOnile.setTypes(request.getTypes());
		naChangePlanOnile.setCreateDate(new Date());
		naChangePlanOnile.setTimely(request.getTimely());
		naChangePlanOnile.setRemark(request.getRemark());
		naChangePlanOnile.setSign(Byte.parseByte("0"));
		//处理状态默认是1:新增
		naChangePlanOnile.setPlanState(1L);
		naChangePlanOnile.setCreateOpId(request.getCreateOpId());
		//如果是变更,设置交付物截至时间是计划变更的前一天
		if(WorkFlowNewEnum.CHANGE_PLAN_PLANCHANGE.getValue()==(long)request.getTypes()||WorkFlowNewEnum.CHANGE_PLAN_EMERGENTCHANGE.getValue()==(long)request.getTypes()){
			naChangePlanOnile.setFileUploadLastTime(TimeUtil.getDayBefore(request.getPlanDate(), 1));
		}
		//如果是上线，则前台设置
		else if(WorkFlowNewEnum.CHANGE_PLAN_PLANONLINE.getValue()==(long)request.getTypes()||WorkFlowNewEnum.CHANGE_PLAN_EMERGENTONLINE.getValue()==(long)request.getTypes()){
			naChangePlanOnile.setFileUploadLastTime(request.getFileUploadLastTime());
		}
		naChangePlanOnileDao.save(naChangePlanOnile);
		
		nodeRecordSv.saveChangeBegin(request.getOnlinePlanName());
		
		return naChangePlanOnile;
			
	}

	
	//修改
	public NaChangePlanOnile  summaryChangePlanOnile(NaChangePlanOnileRequest request){
		if(request == null){ 
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		
	
		if(StringUtils.isBlank(request.getResult().toString())){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "");
		}
		
		NaChangePlanOnile naChangePlanOnile=naChangePlanOnileDao.findOne(request.getOnlinePlan());
		naChangePlanOnile.setOnlinePlanName(request.getOnlinePlanName());
		naChangePlanOnile.setPlanDate(request.getPlanDate());
		naChangePlanOnile.setTypes(request.getTypes());
		naChangePlanOnile.setTimely(request.getTimely());
		naChangePlanOnile.setRemark(request.getRemark());
		naChangePlanOnile.setExt3(request.getExt3());
		naChangePlanOnile.setResult(request.getResult());
		naChangePlanOnile.setDoneDate(request.getDoneDate());
		naChangePlanOnile.setCreateDate(request.getCreateDate());
		naChangePlanOnile.setPlanState(request.getPlanState());
		naChangePlanOnile.setFileUploadLastTime(request.getFileUploadLastTime());
		naChangePlanOnileDao.save(naChangePlanOnile);
		return naChangePlanOnile;
			
	}
	
	
	
	public void  select( NaChangePlanOnileRequest request){
		Long node=8L;
		//修改
		NaChangePlanOnile naChangePlanOnile = naChangePlanOnileDao.findOne(request.getOnlinePlan());
		
		if(!request.getExt3().equals("1")){
			naChangePlanOnile.setPlanState(3L);
			naChangePlanOnile.setDoneDate( new Date());
			naChangePlanOnile.setExt2(request.getExt2());
			naChangePlanOnile.setResult(request.getResult());
			naChangePlanOnileDao.save(naChangePlanOnile);
			nodeRecordSv.commit(request.getOnlinePlan(), node);
		}else{
		naChangePlanOnile.setExt2(request.getExt2());
		
		naChangePlanOnile.setResult(request.getResult());
		naChangePlanOnileDao.save(naChangePlanOnile);
		
	      nodeRecordSv.update(request.getOnlinePlan(),node);
		
		
		}
		
		
		
		
	}
	
	
	
	public  NaChangePlanOnile findOne1(Long onlinePlan) {
		if(onlinePlan == null || onlinePlan < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "");
		}
		
		return naChangePlanOnileDao.findOne(onlinePlan);
	}
	public void updateChangePlanOnile(NaChangePlanOnileRequest request){
		if(request == null){ 
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
		NaChangePlanOnile naChangePlanOnile=naChangePlanOnileDao.findOne(request.getOnlinePlan());
		if(naChangePlanOnile == null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_invalid);
		}
		
		if(naChangePlanOnile != null){
			
			if(StringUtils.isBlank(request.getOnlinePlanName())){
				naChangePlanOnile.setOnlinePlanName(request.getOnlinePlanName());
			}
			naChangePlanOnile.setOnlinePlanName(request.getOnlinePlanName());

			naChangePlanOnile.setCreateDate(new Date(System.currentTimeMillis()));
			naChangePlanOnile.setDoneDate(request.getDoneDate());
			naChangePlanOnile.setTimely(request.getTimely());
			naChangePlanOnile.setRemark(request.getRemark());
			naChangePlanOnile.setPlanDate(request.getPlanDate());
			naChangePlanOnile.setPlanState(request.getPlanState());
			naChangePlanOnile.setResult(request.getResult());
			naChangePlanOnile.setPlanState(request.getPlanState());
			naChangePlanOnileDao.save(naChangePlanOnile);
		}
		
	}
    public void abandonChangePlanOnile1(Long onlinePlan){
		if(onlinePlan == null || onlinePlan < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "onlinePlan");
		}
		
		naChangePlanOnileDao.delete(onlinePlan);
	}
	public void abandonChangePlanOnile(Long onlinePlan){
		if(onlinePlan == null || onlinePlan < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "onlinePlan");
		}
		naChangePlanOnileDao.abandonChangePlanOnile(onlinePlan);
		}
	public void delectChangePlanOnile(Long onlinePlan){
		if(onlinePlan == null || onlinePlan < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "onlinePlan");
		}
		naChangePlanOnileDao.delectChangePlanOnile(onlinePlan);
	}
	
	
	/**
	 * @ClassName: NaChangePlanOnileSv :: saveExcel
	 * @author: taoyf
	 * @date: 2017年4月11日 下午4:05:15
	 *
	 * @Description:
	 * @param l
	 * @param list          
	 */
	public void saveExcel(Long planId, List<PlanDetailManifestExcel> list) {
		if(planId == null || planId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "planId");
		}
		
		if(list == null || list.size() <= 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "导入内容");
		}
		
		
		List<PlanDetailManifest> values = BeanMapper.mapList(list, PlanDetailManifestExcel.class, PlanDetailManifest.class);
		if(values != null){
			for(PlanDetailManifest v : values){
				v.setPlanId(planId);
				v.setCreatorId(SessionMgrUtil.getStaff().getOpId());
				v.setCreateTime(DateUtil.getCurrentTime());
			}
		}
		
		planDetailManifestDao.save(values);
	}
	
	
	/**
	 * 查询包含其他任务的计划
	 * @param type
	 * @return
	 */
		public Object getOtherPlan(Long type) {
			if(type==null){
				BusinessException.throwBusinessException(ErrorCode.Parameter_null, "type");
			}
			StringBuilder s = new StringBuilder();// 
			s.append("select distinct online_plan, online_plan_name from (");
			s.append("  select a.online_plan, a.online_plan_name   from na_change_plan_onile a left join na_online_task_distribute b   on a.online_plan = b.online_plan where b.parent_task_id = 0  and a.sign=0  and  a.plan_state in (1,2) and b.task_type = 2  ");
				if(type==0){
				//新增
					s.append(" and a.online_plan not in (select online_plan  from AIGA_BOSS_TEST_RESULT)");
			}
				s.append("order by a.plan_date desc )");
				
			return aigaBossTestResultDao.searchByNativeSQL(s.toString());
		}
}
