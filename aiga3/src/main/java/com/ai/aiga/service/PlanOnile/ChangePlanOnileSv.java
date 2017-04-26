package com.ai.aiga.service.PlanOnile;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.dao.AigaBossTestResultDao;
import com.ai.aiga.dao.CodePathDao;
import com.ai.aiga.dao.NaChangePlanOnileDao;
import com.ai.aiga.dao.NaFileUploadDao;
import com.ai.aiga.dao.NaHostConfigListDao;
import com.ai.aiga.dao.NaProcessChangeListDao;
import com.ai.aiga.dao.NaServiceChangeOnlineListDao;
import com.ai.aiga.dao.PlanDetailManifestDao;
import com.ai.aiga.domain.NaChangePlanOnile;
import com.ai.aiga.domain.NaCodePath;
import com.ai.aiga.domain.NaFileUpload;
import com.ai.aiga.domain.NaHostConfigList;
import com.ai.aiga.domain.NaProcessChangeList;
import com.ai.aiga.domain.NaServiceChangeOnlineList;
import com.ai.aiga.domain.PlanDetailManifest;
import com.ai.aiga.domain.SysRole;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;

import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.service.workFlowNew.dto.NaHostConfigListExcel;
import com.ai.aiga.service.workFlowNew.dto.NaProcessChangeListExcel;
import com.ai.aiga.service.workFlowNew.dto.NaServiceChangeOnlineListExcel;
import com.ai.aiga.util.DateUtil;
import com.ai.aiga.util.mapper.BeanMapper;
import com.ai.aiga.view.controller.plan.dto.PlanDetailManifestExcel;
import com.ai.aiga.view.controller.planOnline.dto.CodePathRequestExcel;
import com.ai.aiga.view.controller.planOnline.dto.NaChangePlanOnileRequest;
import com.ai.aiga.view.util.SessionMgrUtil;

@Service
@Transactional
public class ChangePlanOnileSv extends BaseService{
	@Autowired
	private NaChangePlanOnileDao  naChangePlanOnileDao ;
	
	@Autowired
	private PlanDetailManifestDao planDetailManifestDao;
	
	@Autowired
	private NaProcessChangeListDao naProcessChangeListDao;
	
	@Autowired
	private NaHostConfigListDao naHostConfigListDao;
	
	@Autowired
	private ChangePlanOnileSv   naChangePlanOnileSv;
	
	@Autowired
	private AigaBossTestResultDao aigaBossTestResultDao;
	
	@Autowired
	private NaServiceChangeOnlineListDao naServiceChangeOnlineListDao;
	
	@Autowired
	private NaFileUploadDao naFileUploadDao;
	
	@Autowired
	private       CodePathDao      codePathDao;
	
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
		//处理状态默认是1
		naChangePlanOnile.setPlanState(1L);
		naChangePlanOnile.setCreateOpId(request.getCreateOpId());
		naChangePlanOnileDao.save(naChangePlanOnile);
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
		
		NaChangePlanOnile naChangePlanOnile=new NaChangePlanOnile();
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
		naChangePlanOnileDao.save(naChangePlanOnile);
		return naChangePlanOnile;
			
	}
	
	
	
	public void  select( NaChangePlanOnileRequest request){
		//修改
		NaChangePlanOnile naChangePlanOnile = naChangePlanOnileDao.findOne(request.getOnlinePlan());
		
		if(!request.getExt3().equals("1")){
			naChangePlanOnile.setPlanState(3L);
			naChangePlanOnile.setDoneDate( new Date());
		}
		naChangePlanOnile.setExt2(request.getExt2());
		
		naChangePlanOnile.setResult(request.getResult());
		naChangePlanOnileDao.save(naChangePlanOnile);
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
	 * @ClassName: ChangePlanOnileSv :: saveExcelNaProcessChangeList
	 * @author: lh
	 * @date: 2017年4月26日 上午11:12:10
	 *
	 * @Description:晋城变更清单
	 * @param planId
	 * @param list
	 * @param fileName           
	 */
	public void saveExcelNaProcessChangeList(Long planId, List<NaProcessChangeListExcel> list,String fileName) {
		if(planId == null || planId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "planId");
		}
		
		if(list == null || list.size() <= 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "导入内容");
		}
		
		
		List<NaProcessChangeList> values = BeanMapper.mapList(list, NaProcessChangeListExcel.class, NaProcessChangeList.class);
		if(values != null){
			for(NaProcessChangeList v : values){
				v.setPlanId(planId);
				
			}
		}
		NaFileUpload fileEntity = new NaFileUpload(fileName,new Date());
		naProcessChangeListDao.save(values);
		naFileUploadDao.save(fileEntity);
		
	}
	
	/**
	 * @ClassName: ChangePlanOnileSv :: saveExcelNaServiceChangeOnlineList
	 * @author: lh
	 * @date: 2017年4月26日 上午11:29:12
	 *
	 * @Description:服务变更上线清单
	 * @param planId
	 * @param list
	 * @param fileName          
	 */
	public void saveExcelNaServiceChangeOnlineList(Long planId, List<NaServiceChangeOnlineListExcel> list,String fileName) {
		if(planId == null || planId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "planId");
		}
		
		if(list == null || list.size() <= 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "导入内容");
		}
		
		
		List<NaServiceChangeOnlineList> values = BeanMapper.mapList(list, NaServiceChangeOnlineListExcel.class, NaServiceChangeOnlineList.class);
		if(values != null){
			for(NaServiceChangeOnlineList v : values){
				v.setPlanId(planId);
				
			}
		}
		NaFileUpload fileEntity = new NaFileUpload(fileName,new Date());
		naServiceChangeOnlineListDao.save(values);
		naFileUploadDao.save(fileEntity);
		
	}
	
	
	/**
	 * @ClassName: ChangePlanOnileSv :: saveExcelNaHostConfigList
	 * @author: lh
	 * @date: 2017年4月26日 上午11:34:48
	 *
	 * @Description:主机配置
	 * @param planId
	 * @param list
	 * @param fileName          
	 */
	public void saveExcelNaHostConfigList(Long planId, List<NaHostConfigListExcel> list,String fileName) {
		if(planId == null || planId < 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "planId");
		}
		
		if(list == null || list.size() <= 0){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "导入内容");
		}
		
		
		List<NaHostConfigList> values = BeanMapper.mapList(list, NaHostConfigListExcel.class, NaHostConfigList.class);
		if(values != null){
			for(NaHostConfigList v : values){
				v.setPlanId(planId);
				
				
			}
		}
		naHostConfigListDao.save(values);
		NaFileUpload fileEntity = new NaFileUpload(fileName,new Date());
		
		naFileUploadDao.save(fileEntity);
		
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
		
		/**
		 * 
		 * @ClassName: NaChangePlanOnileSv :: saveCodeExcel
		 * @author: liujinfang
		 * @date: 2017年4月25日 下午3:10:26
		 *
		 * @Description:  上线系统模块解析导入
		 * @param planId
		 * @param list
		 */
		public void saveCodeExcel(Long planId, List<CodePathRequestExcel> list) {
			if(planId == null || planId < 0){
				BusinessException.throwBusinessException(ErrorCode.Parameter_null, "planId");
			}
			
			if(list == null || list.size() <= 0){
				BusinessException.throwBusinessException(ErrorCode.Parameter_null, "导入内容");
			}
			
			
			List<NaCodePath> values = BeanMapper.mapList(list, CodePathRequestExcel.class, NaCodePath.class);
			if(values != null){
				for(NaCodePath v : values){
					v.setPlanId(planId);
					
				}
			}
			
			codePathDao.save(values);
		}	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
}
