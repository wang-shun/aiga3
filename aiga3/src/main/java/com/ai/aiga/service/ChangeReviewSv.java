package com.ai.aiga.service;

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

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.ChangeReviewDao;
import com.ai.aiga.dao.CodePathDao;
import com.ai.aiga.dao.DatabaseConfiDao;
import com.ai.aiga.dao.DatabaseScriptListDao;
import com.ai.aiga.dao.DbScriptListDao;
import com.ai.aiga.dao.NaGroupAdjustListDao;
import com.ai.aiga.dao.NaGroupRequireListDao;
import com.ai.aiga.dao.NaHasDeployMenuListDao;
import com.ai.aiga.dao.NaHostConfigListDao;
import com.ai.aiga.dao.NaHostIpDao;
import com.ai.aiga.dao.NaProcessChangeListDao;
import com.ai.aiga.dao.NaRequireListDao;
import com.ai.aiga.dao.NaServiceChangeOnlineListDao;
import com.ai.aiga.dao.NaSystemArchitectureListDao;
import com.ai.aiga.dao.PlanDetailManifestDao;
import com.ai.aiga.dao.TestLeaveOverDao;
import com.ai.aiga.dao.TestSituationDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.domain.NaChangeList;
import com.ai.aiga.domain.NaChangeReview;
import com.ai.aiga.domain.NaCodePath;
import com.ai.aiga.domain.NaDatabaseConfiScript;
import com.ai.aiga.domain.NaDatabaseScriptList;
import com.ai.aiga.domain.NaDbScriptList;
import com.ai.aiga.domain.NaEmployeeInfo;
import com.ai.aiga.domain.NaHostIp;
import com.ai.aiga.domain.NaRequireList;
import com.ai.aiga.domain.NaTestLeaveOver;
import com.ai.aiga.domain.NaTestSituation;
import com.ai.aiga.domain.NaUiControl;
import com.ai.aiga.domain.PlanDetailManifest;
import com.ai.aiga.domain.SysRole;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;

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

   public  List<NaChangeReview> selectall(Long onlinePlan,String ext1){
	   if (onlinePlan==null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
	
	   return changeReviewDao.selectall(onlinePlan,ext1);
	   
   }
   
   public void save(NaChangeReview request){
	   
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
		   naChangeReview.setExt2(request.getExt2());
		   naChangeReview.setExt1(request.getExt1());
		   changeReviewDao.save(naChangeReview);
	 
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
		
		return codePathDao.search(cons, pageable);
		
	}
   //测试情况
   public Object list1(int pageNumber, int pageSize, NaTestSituation condition) throws ParseException {
	
		/*List<String> list = new ArrayList<String>();
		
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

		return planDetailManifestDao.searchByNativeSQL(sql, pageable, list);*/
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
		
		return testSituationDao.search(cons, pageable);
	   
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
				
				//NaTestSituation1.setSysName(NaTestSituation.getSysName());
				//NaTestSituation1.setSubSysName(NaTestSituation.getSubSysName());
				NaTestSituation1.setTestSituation(NaTestSituation.getTestSituation());
				NaTestSituation1.setExt1(NaTestSituation.getExt1());
				NaTestSituation1.setExt2(NaTestSituation.getExt2());
				/*NaTestSituation1.setExt3(NaTestSituation.getExt3());
				NaTestSituation1.setExt4(NaTestSituation.getExt4());*/
				
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
	 public void savehost(NaHostIp request){
		   
		   if(request == null){ 
				BusinessException.throwBusinessException(ErrorCode.Parameter_null);
			}
		   NaHostIp naHostIp=naHostIpDao.findOne(request.getId());
		   if(naHostIp == null){ 
				BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		   }
		   if(naHostIp != null){
			   
			   naHostIp.setIp(request.getIp());
			   naHostIp.setRemark(request.getRemark());
			   naHostIp.setHostName(request.getHostName());
			   naHostIpDao.save(naHostIp);
		 
		   }
	   }


}


