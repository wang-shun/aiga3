package com.ai.am.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.am.constant.BusiConstant;
import com.ai.am.dao.ChangeReviewDao;
import com.ai.am.dao.CodePathDao;
import com.ai.am.dao.DatabaseConfiDao;
import com.ai.am.dao.DatabaseScriptListDao;
import com.ai.am.dao.DbScriptListDao;
import com.ai.am.dao.NaGroupAdjustListDao;
import com.ai.am.dao.NaGroupRequireListDao;
import com.ai.am.dao.NaHasDeployMenuListDao;
import com.ai.am.dao.NaHostConfigListDao;
import com.ai.am.dao.NaHostIpDao;
import com.ai.am.dao.NaProcessChangeListDao;
import com.ai.am.dao.NaRequireListDao;
import com.ai.am.dao.NaServiceChangeOnlineListDao;
import com.ai.am.dao.NaSystemArchitectureListDao;
import com.ai.am.dao.PlanDetailManifestDao;
import com.ai.am.dao.TestLeaveOverDao;
import com.ai.am.dao.TestSituationDao;
import com.ai.am.dao.jpa.Condition;
import com.ai.am.domain.NaChangeList;
import com.ai.am.domain.NaChangeReview;
import com.ai.am.domain.NaCodePath;
import com.ai.am.domain.NaDatabaseConfiScript;
import com.ai.am.domain.NaDatabaseScriptList;
import com.ai.am.domain.NaDbScriptList;
import com.ai.am.domain.NaEmployeeInfo;
import com.ai.am.domain.NaHostIp;
import com.ai.am.domain.NaRequireList;
import com.ai.am.domain.NaTestLeaveOver;
import com.ai.am.domain.NaTestSituation;
import com.ai.am.domain.NaUiControl;
import com.ai.am.domain.PlanDetailManifest;
import com.ai.am.domain.SysRole;
import com.ai.am.exception.BusinessException;
import com.ai.am.exception.ErrorCode;
import com.ai.am.service.base.BaseService;
import com.ai.am.service.onlineProcess.NodeRecordSv;

/**
 * @ClassName: ChangeReviewSv
 * @author: liujinfang
 * @date: 2017年4月11日 下午3:22:37
 * @Description:
 * 
 */
@Service
@Transactional
public class ChangeReviewSv extends BaseService{
	@Autowired
	private ChangeReviewDao changeReviewDao;

	@Autowired
	private PlanDetailManifestDao planDetailManifestDao;

	@Autowired
	private CodePathDao codePathDao;

	@Autowired
	private TestLeaveOverDao testLeaveOverDao;

	@Autowired
	private NaRequireListDao naRequireListDao;

	@Autowired
	private DatabaseConfiDao databaseConfiDao;

	@Autowired
	private DatabaseScriptListDao databaseScriptListDao;

	@Autowired
	private DbScriptListDao dbScriptListDao;

	@Autowired
	private NaHostConfigListDao naHostConfigListDao;

	@Autowired
	private NaGroupAdjustListDao naGroupAdjustListDao;

	@Autowired
	private NaGroupRequireListDao naGroupRequireListDao;

	@Autowired
	private NaHasDeployMenuListDao naHasDeployMenuListDao;

	@Autowired
	private NaProcessChangeListDao naProcessChangeListDao;

	@Autowired
	private NaServiceChangeOnlineListDao naServiceChangeOnlineListDao;

	@Autowired
	private NaSystemArchitectureListDao naSystemArchitectureListDao;

	@Autowired
	private TestSituationDao testSituationDao;

	@Autowired
	private NaHostIpDao naHostIpDao;
	
	@Autowired
	private   NodeRecordSv   nodeRecordSv;

   public  NaChangeReview selectall(Long onlinePlan,String ext1){
	   if (onlinePlan==null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
	
	   return changeReviewDao.selectall(onlinePlan,ext1);
	   
   }
   /**
 * @ClassName: ChangeReviewSv :: selectall
 * @author: lh
 * @date: 2017年5月3日 上午10:25:06
 *
 * @Description:  返回结论列表  不按ext1查询
 * @param onlinePlan
 * @param ext1
 * @return          
 */
public  NaChangeReview selectReview(Long onlinePlan){
	   if (onlinePlan==null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null+"onlinePlan");
		}
	
	   return changeReviewDao.selectReview(onlinePlan);
	   
   }
   public void save(NaChangeReview request){
	   Long node=2L;
	   
	   if(request == null){ 
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
	 
	   NaChangeReview naChangeReview=changeReviewDao.findOne(request.getReviewId());
	   if(naChangeReview == null){ 
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
	   }
	   if(naChangeReview != null){
		   if(request.getConclusion()!=null){
			   naChangeReview.setConclusion(request.getConclusion());
			}
		   if(request.getRemark()!=null){
		   naChangeReview.setRemark(request.getRemark());
		   }
		   
		   naChangeReview.setReviewDate(new Date(System.currentTimeMillis()));
		   naChangeReview.setReviewer("张三");
		   
		   if(StringUtils.isNotBlank(request.getReviewResult())){
		   naChangeReview.setReviewResult(request.getReviewResult());}
		   
		   naChangeReview.setExt3(request.getExt3());
		   
		   
		   changeReviewDao.save(naChangeReview);
		   nodeRecordSv.update(request.getOnlinePlanId(), node);
		   
	 
	   }
   }
   public Object list(int pageNumber, int pageSize, PlanDetailManifest condition) throws ParseException {
		List<String> list = new ArrayList<String>();
		
		list.add("sysName");
		list.add("subSysName");
		list.add("nu");
		
		String sql = "select sys_name, sub_sys_name, count(sub_sys_name) nu from plan_detail_manifest ";
				
		
		if (condition.getPlanId()!=0) {
			sql += " where  plan_id ="+ condition.getPlanId() ;
		}
		sql+=" group by sys_name,sub_sys_name";
		if (pageNumber < 0) {
			pageNumber = 0;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);

		return planDetailManifestDao.searchByNativeSQL(sql, pageable, list);
	}
   public Object findCodePath(int pageNumber, int pageSize, NaCodePath condition) {
	   
	List<Condition> cons = new ArrayList<Condition>();
		
		if(condition != null){
			if(condition.getPlanDate()!=null){
				cons.add(new Condition("planDate", condition.getPlanDate(), Condition.Type.EQ));
			}
		}
		
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return codePathDao.search(cons, pageable);
		
	}
   //测试情况
   public Object list1(int pageNumber, int pageSize, NaTestSituation condition) throws ParseException {
	
		List<String> list = new ArrayList<String>();
		
		list.add("sysName");
		list.add("subSysName");
		list.add("testSituation");
		list.add("testId");
		String sql = " select distinct A.sys_name,A.sub_sys_name, B.TEST_SITUATION ,B.Test_Id from plan_detail_manifest A  LEFT JOIN NA_TEST_SITUATION  B ON A.SYS_NAME= B.SYS_NAME AND A.SUB_SYS_NAME = B.SUB_SYS_NAME  ";
		if (condition.getPlanId()!=0) {
			sql += " where A.plan_id ="+ condition.getPlanId() ;
		}
		if (pageNumber < 0) {
			pageNumber = 0;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);

		return planDetailManifestDao.searchByNativeSQL(sql, pageable, list);
	  
	   
	}
   
   
   public Object listTest(int pageNumber, int pageSize, NaTestSituation condition) throws ParseException {
		
		
	   List<String> list = new ArrayList<String>();
		
		
		list.add("ext1");
		list.add("testSituation");
		list.add("testId");
		String sql = " select t.ext_1,t.test_situation,t.test_id from na_test_situation t where t.ext_1 is not null  ";
		if (condition.getPlanId()!=0) {
			sql += " and t.plan_id ="+ condition.getPlanId() ;
		}
		if (pageNumber < 0) {
			pageNumber = 0;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);

		return planDetailManifestDao.searchByNativeSQL(sql, pageable, list);
	   
	}
   //保存测试情况
   public void saveTestSituation(List<NaTestSituation> list){
	   if(list == null){ 
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
	   for (int i = 0; i < list.size(); i++) {

		   NaTestSituation NaTestSituation = list.get(i);

			if (NaTestSituation != null) {
				NaTestSituation NaTestSituation1 =testSituationDao.findOne(NaTestSituation.getTestId());
				
				
				
				/*if(StringUtils.isNotBlank(NaTestSituation.getExt2())){
				NaTestSituation1.setExt2(NaTestSituation.getExt2());
				}*/
				
				if(StringUtils.isNotBlank(NaTestSituation.getTestSituation())){
					NaTestSituation1.setTestSituation(NaTestSituation.getTestSituation());
					}
				
				testSituationDao.save(NaTestSituation1);
					
			}
		}
	   
   }
   public void saveCodePath(List<NaCodePath> list){
	   if(list == null){ 
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
	   for (int i = 0; i < list.size(); i++) {

		   NaCodePath naCodePath = list.get(i);

			if (naCodePath != null) {
				
			NaCodePath naCodePath1=codePathDao.findOne(naCodePath.getId());
				naCodePath1.setResult(naCodePath.getResult());
				
			codePathDao.save(naCodePath1);
				

			}
		}
	   
	   
   }
   
   public Object  findPlanDetailManifest(int pageNumber, int pageSize,PlanDetailManifest condition){
		List<Condition> cons = new ArrayList<Condition>();
		
		if(condition != null){
			if(condition.getPlanId()!= 0){
				cons.add(new Condition("planId", condition.getPlanId(), Condition.Type.EQ));
			}
		}
		
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return planDetailManifestDao.search(cons, pageable);
		
   }	
 
 
 public Object findTestLeaveOver(int pageNumber, int pageSize,NaTestLeaveOver condition){
	   
	 List<Condition> cons = new ArrayList<Condition>();
		
	 if(condition != null){
			if(condition.getPlanId()!=null||condition.getPlanId().equals("")){
				cons.add(new Condition("planId", condition.getPlanId(), Condition.Type.EQ));
			}
		}
		
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return testLeaveOverDao.search(cons, pageable);
		
   }
 public Object findRequireList(int pageNumber, int pageSize,NaRequireList condition){
	   
	 List<Condition> cons = new ArrayList<Condition>();
		
	 if(condition != null){
			if(condition.getPlanId()!=null||condition.getPlanId().equals("")){
				cons.add(new Condition("planId", condition.getPlanId(), Condition.Type.EQ));
			}
		}
		
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return naRequireListDao.search(cons, pageable);
		
   }
 public Object findDatabaseScriptList(int pageNumber, int pageSize,NaDatabaseScriptList condition){
	   
	 List<Condition> cons = new ArrayList<Condition>();
		
	 if(condition != null){
			if(condition.getPlanId()!=null||condition.getPlanId().equals("")){
				cons.add(new Condition("planId", condition.getPlanId(), Condition.Type.EQ));
			}
		}
		
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return databaseScriptListDao.search(cons, pageable);
		
   }

 

 public Object findDatabaseConfi(int pageNumber, int pageSize,NaDatabaseConfiScript condition){
	   
	 List<Condition> cons = new ArrayList<Condition>();
		
	 if(condition != null){
			if(condition.getPlanId()!=null||condition.getPlanId().equals("")){
				cons.add(new Condition("planId", condition.getPlanId(), Condition.Type.EQ));
			}
		}
		
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return databaseConfiDao.search(cons, pageable);
		
   }
 public Object findDbScriptList(int pageNumber, int pageSize,NaDbScriptList condition){
	 
	 List<Condition> cons = new ArrayList<Condition>();
		
	 if(condition != null){
			if(condition.getPlanId()!=null||condition.getPlanId().equals("")){
				cons.add(new Condition("planId", condition.getPlanId(), Condition.Type.EQ));
			}
		}
		
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return dbScriptListDao.search(cons, pageable);
		
   }
	   


 
 /**
  * 根据计划查询组织配置清单
  * @param planId计划id
  * @param pageNumber
  * @param pageSize
  * @return
  */
 public Object  findNaHostConfigListByPlanId(Long planId,int pageNumber, int pageSize){
	 if(planId==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null+"计划id为空");
	 }
		List<Condition> cons = new ArrayList<Condition>();
       cons.add(new Condition("planId",planId,Condition.Type.EQ));
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return naHostConfigListDao.search(cons, pageable);
		
 	}	
 
 
 /**
  * 根据计划查询需要联调需求清单
  * @param planId计划id
  * @param pageNumber
  * @param pageSize
  * @return
  */
 public Object  findNaGroupAdjustListByPlanId(Long planId,int pageNumber, int pageSize){
	 if(planId==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null+"计划id为空");
	 }
		List<Condition> cons = new ArrayList<Condition>();
       cons.add(new Condition("planId",planId,Condition.Type.EQ));
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return naGroupAdjustListDao.search(cons, pageable);
		
}	
 
 
 /**
  * 根据计划查询集团需求清单
  * @param planId计划id
  * @param pageNumber
  * @param pageSize
  * @return
  */
 public Object  findNaGroupRequireListByPlanId(Long planId,int pageNumber, int pageSize){
	 if(planId==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null+"计划id为空");
	 }
		List<Condition> cons = new ArrayList<Condition>();
       cons.add(new Condition("planId",planId,Condition.Type.EQ));
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return naGroupRequireListDao.search(cons, pageable);
		
 }	
 
 
 /**
  * 根据计划查询生产环境需要配置菜单需求清单
  * @param planId计划id
  * @param pageNumber
  * @param pageSize
  * @return
  */
 public Object  findNaHasDeployMenuListByPlanId(Long planId,int pageNumber, int pageSize){
	 if(planId==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null+"计划id为空");
	 }
		List<Condition> cons = new ArrayList<Condition>();
       cons.add(new Condition("planId",planId,Condition.Type.EQ));
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return naHasDeployMenuListDao.search(cons, pageable);
		
  }	
 
	 /**
	  * 根据计划查询进程变更清单
	  * @param planId计划id
	  * @param pageNumber
	  * @param pageSize
	  * @return
	  */
	 public Object  findNaProcessChangeListByPlanId(Long planId,int pageNumber, int pageSize){
		 if(planId==null){
				BusinessException.throwBusinessException(ErrorCode.Parameter_null+"计划id为空");
		 }
			List<Condition> cons = new ArrayList<Condition>();
	       cons.add(new Condition("planId",planId,Condition.Type.EQ));
			if(pageNumber < 0){
				pageNumber = 0;
			}
			
			if(pageSize <= 0){
				pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
			}
	
			Pageable pageable = new PageRequest(pageNumber, pageSize);
			
			return naProcessChangeListDao.search(cons, pageable);
			
	 }	
	 
	 
	 /**
	  * 根据计划查询服务进程上线清单
	  * @param planId计划id
	  * @param pageNumber
	  * @param pageSize
	  * @return
	  */
	 public Object  findNaServiceChangeOnlineListByPlanId(Long planId,int pageNumber, int pageSize){
		 if(planId==null){
				BusinessException.throwBusinessException(ErrorCode.Parameter_null+"计划id为空");
		 }
			List<Condition> cons = new ArrayList<Condition>();
	       cons.add(new Condition("planId",planId,Condition.Type.EQ));
			if(pageNumber < 0){
				pageNumber = 0;
			}
			
			if(pageSize <= 0){
				pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
			}
	
			Pageable pageable = new PageRequest(pageNumber, pageSize);
			
			return naServiceChangeOnlineListDao.search(cons, pageable);
			
	 }	
	 
	 
	 /**
	  * 根据计划查询系统架构清单
	  * @param planId计划id
	  * @param pageNumber
	  * @param pageSize
	  * @return
	  */
	 public Object  findNaSystemArchitectureListByPlanId(Long planId,int pageNumber, int pageSize){
		 if(planId==null){
				BusinessException.throwBusinessException(ErrorCode.Parameter_null+"计划id为空");
		 }
			List<Condition> cons = new ArrayList<Condition>();
	       cons.add(new Condition("planId",planId,Condition.Type.EQ));
			if(pageNumber < 0){
				pageNumber = 0;
			}
			
			if(pageSize <= 0){
				pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
			}
	
			Pageable pageable = new PageRequest(pageNumber, pageSize);
			
			return naSystemArchitectureListDao.search(cons, pageable);
	 }
	 
    //主机
	 public Object host(int pageNumber, int pageSize, NaHostIp condition) throws ParseException {
			
			List<String> list = new ArrayList<String>();
			
			list.add("sysName");
			list.add("modelName");
			list.add("ip");
			list.add("hostName");
			list.add("remark");
			list.add("id");
			String sql = " select  A.sys_name,A.model_name, B.ip ,B.host_name,B.remark,B.id from na_code_path A  LEFT JOIN NA_HOST_IP  "
					+ "B ON A.SYS_NAME= B.SYS_NAME AND A.model_name = B.model_name ";
			if (condition.getPlanId()!=null) {
				sql += " where A.plan_id ="+ condition.getPlanId() ;
			}
			if (pageNumber < 0) {
				pageNumber = 0;
			}

			if (pageSize <= 0) {
				pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
			}

			Pageable pageable = new PageRequest(pageNumber, pageSize);

			return naHostIpDao.searchByNativeSQL(sql, pageable, list);



	 }
	 public void savehost(List<NaHostIp> request ){
		   
		   if(request == null){ 
				BusinessException.throwBusinessException(ErrorCode.Parameter_null);
			}
		   for(int i = 0; i < request.size(); i++){
			   NaHostIp hostIp = request.get(i);
			   if(hostIp.getId() == null){ 
				   naHostIpDao.save(request);
			   }else{
				   NaHostIp naHostIp=naHostIpDao.findOne(hostIp.getId());
				   naHostIp.setIp(hostIp.getIp());
				   naHostIp.setRemark(hostIp.getRemark());
				   naHostIp.setHostName(hostIp.getHostName());
				   naHostIpDao.save(naHostIp);
			   }
		   }
		  
	   }

}


