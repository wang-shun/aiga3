package com.ai.aiga.service.PlanOnile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.AigaBossTestResultDao;
import com.ai.aiga.dao.CodePathDao;
import com.ai.aiga.dao.DatabaseConfiDao;
import com.ai.aiga.dao.DatabaseScriptListDao;
import com.ai.aiga.dao.DbScriptListDao;
import com.ai.aiga.dao.NaChangePlanOnileDao;

import com.ai.aiga.dao.NaRequireListDao;

import com.ai.aiga.dao.NaFileUploadDao;
import com.ai.aiga.dao.NaGroupAdjustListDao;
import com.ai.aiga.dao.NaGroupRequireListDao;
import com.ai.aiga.dao.NaHasDeployMenuListDao;
import com.ai.aiga.dao.NaHostConfigListDao;
import com.ai.aiga.dao.NaProcessChangeListDao;
import com.ai.aiga.dao.NaServiceChangeOnlineListDao;

import com.ai.aiga.dao.PlanDetailManifestDao;

import com.ai.aiga.dao.TestLeaveOverDao;
import com.ai.aiga.dao.TestSituationDao;

import com.ai.aiga.dao.jpa.Condition;

import com.ai.aiga.domain.NaChangePlanOnile;
import com.ai.aiga.domain.NaCodePath;

import com.ai.aiga.domain.NaDatabaseConfiScript;
import com.ai.aiga.domain.NaDatabaseScriptList;
import com.ai.aiga.domain.NaDbScriptList;
import com.ai.aiga.domain.NaRequireList;
import com.ai.aiga.domain.NaTestLeaveOver;
import com.ai.aiga.domain.NaTestSituation;

import com.ai.aiga.domain.NaFileUpload;
import com.ai.aiga.domain.NaGroupAdjustList;
import com.ai.aiga.domain.NaGroupRequireList;
import com.ai.aiga.domain.NaHasDeployMenuList;
import com.ai.aiga.domain.NaHostConfigList;
import com.ai.aiga.domain.NaProcessChangeList;
import com.ai.aiga.domain.NaServiceChangeOnlineList;

import com.ai.aiga.domain.PlanDetailManifest;
import com.ai.aiga.domain.SysRole;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;

import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.service.enums.WorkFlowNewEnum;
import com.ai.aiga.service.onlineProcess.NodeRecordSv;
import com.ai.aiga.service.workFlowNew.dto.NaHostConfigListExcel;
import com.ai.aiga.service.workFlowNew.dto.NaProcessChangeListExcel;
import com.ai.aiga.service.workFlowNew.dto.NaServiceChangeOnlineListExcel;
import com.ai.aiga.util.DateUtil;
import com.ai.aiga.util.TimeUtil;
import com.ai.aiga.util.mapper.BeanMapper;
import com.ai.aiga.view.controller.plan.dto.PlanDetailManifestExcel;
import com.ai.aiga.view.controller.planOnline.dto.CodePathRequestExcel;
import com.ai.aiga.view.controller.planOnline.dto.DatabaseConfiScriptExcel;
import com.ai.aiga.view.controller.planOnline.dto.DatabaseScriptListExcel;
import com.ai.aiga.view.controller.planOnline.dto.NaChangePlanOnileRequest;
import com.ai.aiga.view.controller.planOnline.dto.NaDbScriptListExcel;
import com.ai.aiga.view.controller.planOnline.dto.NaGroupAdjustListExcel;
import com.ai.aiga.view.controller.planOnline.dto.NaGroupRequireListExcel;
import com.ai.aiga.view.controller.planOnline.dto.NaHasDeployMenuListExcel;
import com.ai.aiga.view.controller.planOnline.dto.RequireListExcel;
import com.ai.aiga.view.controller.planOnline.dto.TestLeaveOverExcel;
import com.ai.aiga.view.controller.planOnline.dto.TestLeaveOverRequest;
import com.ai.aiga.view.controller.planOnline.dto.TestSituationExcel;
import com.ai.aiga.view.util.SessionMgrUtil;
import com.huawei.msp.mmap.server.TaskMessageClient;

@Service
@Transactional
public class ChangePlanOnileSv extends BaseService{
	@Autowired
	private NaChangePlanOnileDao  naChangePlanOnileDao ;
	
	@Autowired
	private NaGroupRequireListDao naGroupRequireListDao;
	
	@Autowired
	private NaHasDeployMenuListDao naHasDeployMenuListDao;

	@Autowired
	private NaGroupAdjustListDao naGroupAdjustListDao;
	
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
	
	@Autowired
	private  TestLeaveOverDao  testLeaveOverDao;
	
	@Autowired
	private         NaRequireListDao     naRequireListDao;
	
	@Autowired
	private          TestSituationDao      testSituationDao;
	
	@Autowired
	private DatabaseConfiDao databaseConfiDao;

	@Autowired
	private DatabaseScriptListDao databaseScriptListDao;
	
	
	@Autowired
	private DbScriptListDao dbScriptListDao;
	
	@Autowired
	private  NodeRecordSv  nodeRecordSv;
	
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
		//如果是变更,设置交付物截至时间是计划变更的前一天
		if(WorkFlowNewEnum.CHANGE_PLAN_PLANCHANGE.getValue()==(long)request.getTypes()||WorkFlowNewEnum.CHANGE_PLAN_EMERGENTCHANGE.getValue()==(long)request.getTypes()){
			naChangePlanOnile.setFileUploadLastTime(TimeUtil.getDayBefore(request.getPlanDate(), 1));
		}
		//如果是上线，交付物截至时间是计划变更的前3天
		else {
			System.out.println("1111"+TimeUtil.getDayBefore(request.getPlanDate(), 3));
			naChangePlanOnile.setFileUploadLastTime(TimeUtil.getDayBefore(request.getPlanDate(), 3));
		}
		naChangePlanOnile.setCreateOpId(String.valueOf(SessionMgrUtil.getStaff().getStaffId()));
		naChangePlanOnileDao.save(naChangePlanOnile);
		sendMessageForCycle(naChangePlanOnile);
		
		/*if(WorkFlowNewEnum.CHANGE_PLAN_PLANONLINE.getValue()==(long)request.getTypes()||WorkFlowNewEnum.CHANGE_PLAN_EMERGENTONLINE.getValue()==(long)request.getTypes()){
			nodeRecordSv.saveChangeBegin(request.getOnlinePlanName());
		}*/
		nodeRecordSv.saveChangeBegin(request.getOnlinePlanName());
		return naChangePlanOnile;
			
	}

	
	/**
	 * @ClassName: ChangePlanOnileSv :: sendMessageForCycle
	 * @author: dongch
	 * @date: 2017年5月3日 下午2:30:57
	 *
	 * @Description:短信通知计划创建人
	 * @param naChangePlanOnile          
	 */
	public void sendMessageForCycle(NaChangePlanOnile naChangePlanOnile) {
		
		StringBuilder contents = new StringBuilder();
		contents.append("AIGA_SMS~尊敬的:").append(SessionMgrUtil.getStaff().getName()).append(",")
		.append(naChangePlanOnile.getOnlinePlanName()).append("计划已创建,").append("交付物上传截止时间：")
		.append(naChangePlanOnile.getFileUploadLastTime().toString());
		
		TaskMessageClient.sendMessageForCycle(SessionMgrUtil.getStaff().getBillId(), contents.toString());
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
		naChangePlanOnile.setFileUploadLastTime(request.getFileUploadLastTime());
		naChangePlanOnile.setPlanState(request.getPlanState());
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
		
		nodeRecordSv.update(request.getOnlinePlan(), node);
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
			naChangePlanOnile.setOnlinePlanName(request.getOnlinePlanName());
			naChangePlanOnile.setRemark(request.getRemark());
			naChangePlanOnile.setPlanDate(request.getPlanDate());
			naChangePlanOnile.setTypes(request.getTypes());
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
	 * @Description:计划上线清单
	 * @param l
	 * @param list          
	 */
	public void saveExcel(Long planId, List<PlanDetailManifestExcel> list,String fileName,Long fileType) {
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
		NaFileUpload fileEntity = new NaFileUpload(fileName,new Date(),fileType);
		planDetailManifestDao.save(values);
		naFileUploadDao.save(fileEntity);
		
	}
	
	

	/**
	 * @ClassName: ChangePlanOnileSv :: saveExcelNaProcessChangeList
	 * @author: lh
	 * @date: 2017年4月26日 上午11:12:10
	 *
	 * @Description:进程变更清单
	 * @param planId
	 * @param list
	 * @param fileName           
	 */
	public void saveExcelNaProcessChangeList(Long planId, List<NaProcessChangeListExcel> list,String fileName,Long fileType) {
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
		NaFileUpload fileEntity = new NaFileUpload(fileName,new Date(),fileType);
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
	public void saveExcelNaServiceChangeOnlineList(Long planId, List<NaServiceChangeOnlineListExcel> list,String fileName,Long fileType) {
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
		NaFileUpload fileEntity = new NaFileUpload(fileName,new Date(),fileType);
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
	public void saveExcelNaHostConfigList(Long planId, List<NaHostConfigListExcel> list,String fileName,Long fileType) {
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
		NaFileUpload fileEntity = new NaFileUpload(fileName,new Date(),fileType);
		
		naFileUploadDao.save(fileEntity);
		
	}
	
	
	public Object findNaFileUpload(Long planId,Long type,int pageNumber, int pageSize){
		List<Condition> cons = new ArrayList<Condition>();
		StringBuilder sql = new StringBuilder("select * from NA_FILE_UPLOAD where 1 = 1");
		if(planId!=null){
			sql = sql.append(" and planId = "+planId);
			
		}
		if(type!=null){
			sql = sql.append(" and file_Type like '"+type+"%'");
			
		}
		sql.append(" order by CREATE_TIME desc");
		if(pageNumber < 0){
			pageNumber = 0;
		}
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		return naFileUploadDao.searchByNativeSQL(sql.toString(), pageable);
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
		public void saveCodeExcel(Long planId, List<CodePathRequestExcel> list,String fileName,Long fileType) {
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
			NaFileUpload fileEntity = new NaFileUpload(fileName,new Date(),fileType);
			codePathDao.save(values);
			naFileUploadDao.save(fileEntity);
		}	
		
		/**
		 * 
		 * @ClassName: ChangePlanOnileSv :: saveCodeExcel
		 * @author: liujinfang
		 * @date: 2017年4月25日 下午4:11:08
		 *
		 * @Description:测试遗留情况解析
		 * @param planId
		 * @param list
		 */
		
		public void testLeaveOverExcel(Long planId, List<TestLeaveOverExcel> list,String fileName,Long fileType) {
			if(planId == null || planId < 0){
				BusinessException.throwBusinessException(ErrorCode.Parameter_null, "planId");
			}
			
			if(list == null || list.size() <= 0){
				BusinessException.throwBusinessException(ErrorCode.Parameter_null, "导入内容");
			}
			
			
			List<NaTestLeaveOver> values = BeanMapper.mapList(list, TestLeaveOverExcel.class, NaTestLeaveOver.class);
			if(values != null){
				for(NaTestLeaveOver v : values){
					v.setPlanId(planId);
					
				}
			}
			NaFileUpload fileEntity = new NaFileUpload(fileName,new Date(),fileType);
		
			naFileUploadDao.save(fileEntity);
			testLeaveOverDao.save(values);
		}	
		
		/**
		 * 
		 * @ClassName: ChangePlanOnileSv :: testLeaveOverExcel
		 * @author: liujinfang
		 * @date: 2017年4月25日 下午4:31:51
		 *
		 * @Description:测试情况解析
		 * @param planId
		 * @param list
		 */
		public void requireListExcel(Long planId, List<RequireListExcel> list,String fileName,Long fileType) {
			if(planId == null || planId < 0){
				BusinessException.throwBusinessException(ErrorCode.Parameter_null, "planId");
			}
			
			if(list == null || list.size() <= 0){
				BusinessException.throwBusinessException(ErrorCode.Parameter_null, "导入内容");
			}
			
			
			List<NaRequireList> values = BeanMapper.mapList(list, RequireListExcel.class, NaRequireList.class);
			if(values != null){
				for(NaRequireList v : values){
					v.setPlanId(planId);
					
				}
			}
			
			naRequireListDao.save(values);
			NaFileUpload fileEntity = new NaFileUpload(fileName,new Date(),fileType);
		
			naFileUploadDao.save(fileEntity);
		}	
		
		/**
		 * 
		 * @ClassName: ChangePlanOnileSv :: requireListExcel
		 * @author: liujinfang
		 * @date: 2017年4月25日 下午4:43:08
		 *
		 * @Description: 测试执行情况解析
		 * @param planId
		 * @param list
		 */
		public void testSituationExcel(Long planId, List<TestSituationExcel> list) {
			if(planId == null || planId < 0){
				BusinessException.throwBusinessException(ErrorCode.Parameter_null, "planId");
			}
			
			if(list == null || list.size() <= 0){
				BusinessException.throwBusinessException(ErrorCode.Parameter_null, "导入内容");
			}
			
			
			List<NaTestSituation> values = BeanMapper.mapList(list, TestSituationExcel.class, NaTestSituation.class);
			if(values != null){
				for(NaTestSituation v : values){
					v.setPlanId(planId);
					
				}
			}
			
			testSituationDao.save(values);
		}		
		/**
		 * 
		 * @ClassName: ChangePlanOnileSv :: naGroupAdjustListExcel
		 * @author: liujinfang
		 * @date: 2017年4月25日 下午5:10:38
		 *
		 * @Description:需联调需求解析
		 * @param planId
		 * @param list
		 */
		public void naGroupAdjustListExcel(Long planId, List<NaGroupAdjustListExcel> list,String fileName,Long fileType) {
			if(planId == null || planId < 0){
				BusinessException.throwBusinessException(ErrorCode.Parameter_null, "planId");
			}
			
			if(list == null || list.size() <= 0){
				BusinessException.throwBusinessException(ErrorCode.Parameter_null, "导入内容");
			}
			
			
			List<NaGroupAdjustList> values = BeanMapper.mapList(list, NaGroupAdjustListExcel.class, NaGroupAdjustList.class);
			if(values != null){
				for(NaGroupAdjustList v : values){
					v.setPlanId(planId);
					
				}
			}
			
			naGroupAdjustListDao.save(values);
			NaFileUpload fileEntity = new NaFileUpload(fileName,new Date(),fileType);
			
			naFileUploadDao.save(fileEntity);
            
		}			
		
		/**
		 * 
		 * @ClassName: ChangePlanOnileSv :: naGroupRequireList
		 * @author: liujinfang
		 * @date: 2017年4月25日 下午5:19:26
		 *
		 * @Description: 集团需求解析
		 * @param planId
		 * @param list
		 */
		public void naGroupRequireList(Long planId, List<NaGroupRequireListExcel> list,String fileName,Long fileType) {
			if(planId == null || planId < 0){
				BusinessException.throwBusinessException(ErrorCode.Parameter_null, "planId");
			}
			
			if(list == null || list.size() <= 0){
				BusinessException.throwBusinessException(ErrorCode.Parameter_null, "导入内容");
			}
			
			
			List<NaGroupRequireList> values = BeanMapper.mapList(list, NaGroupRequireListExcel.class, NaGroupRequireList.class);
			if(values != null){
				for(NaGroupRequireList v : values){
					v.setPlanId(planId);
					
				}
			}
			
			naGroupRequireListDao.save(values);
		NaFileUpload fileEntity = new NaFileUpload(fileName,new Date(),fileType);
				
		naFileUploadDao.save(fileEntity);
		}				
		
	/**
	 * 
	 * @ClassName: ChangePlanOnileSv :: naHasDeployMenuListExcel
	 * @author: liujinfang
	 * @date: 2017年4月25日 下午5:22:35
	 *
	 * @Description: 生产环境需配置菜单需求
	 * @param planId
	 * @param list
	 */
		public void naHasDeployMenuListExcel(Long planId, List<NaHasDeployMenuListExcel> list,String fileName,Long fileType) {
			if(planId == null || planId < 0){
				BusinessException.throwBusinessException(ErrorCode.Parameter_null, "planId");
			}
			
			if(list == null || list.size() <= 0){
				BusinessException.throwBusinessException(ErrorCode.Parameter_null, "导入内容");
			}
			
			
			List<NaHasDeployMenuList> values = BeanMapper.mapList(list, NaHasDeployMenuListExcel.class, NaHasDeployMenuList.class);
			if(values != null){
				for(NaHasDeployMenuList v : values){
					v.setPlanId(planId);
					
				}
			}
			
			naHasDeployMenuListDao.save(values);
			NaFileUpload fileEntity = new NaFileUpload(fileName,new Date(),fileType);
			
			naFileUploadDao.save(fileEntity);
		}				
		
}
