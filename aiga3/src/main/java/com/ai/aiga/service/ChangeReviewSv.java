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
import com.ai.aiga.dao.NaRequireListDao;
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
	private  ChangeReviewDao changeReviewDao;
	             
	@Autowired
	private   PlanDetailManifestDao planDetailManifestDao;
	
	@Autowired
	private CodePathDao codePathDao;
	
	@Autowired
	private     TestLeaveOverDao testLeaveOverDao;
	
	@Autowired
	private  NaRequireListDao naRequireListDao;
	
	
	@Autowired
	private    DatabaseConfiDao databaseConfiDao;
	
	@Autowired
	private    DatabaseScriptListDao databaseScriptListDao;
	
	@Autowired
	private 	DbScriptListDao dbScriptListDao;
	
	
	@Autowired
	private           TestSituationDao   testSituationDao;
   public  List<NaChangeReview> selectall(Long onlinePlan){
	   if (onlinePlan==null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null);
		}
	   return changeReviewDao.selectall(onlinePlan);
	   
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
		   naChangeReview.setOnlinePlanId(request.getOnlinePlanId());
		   naChangeReview.setRemark(request.getRemark());
		   naChangeReview.setReviewDate(new Date(System.currentTimeMillis()));
		   naChangeReview.setReviewer("张三");
		   if(StringUtils.isNotBlank(request.getReviewResult())){
		   naChangeReview.setReviewResult(request.getReviewResult());}
		   //操作
		   naChangeReview.setExt1(request.getExt1());
		   changeReviewDao.save(naChangeReview);
	 
	   }
   }
   public Object list(int pageNumber, int pageSize, PlanDetailManifest condition) throws ParseException {
		List<String> list = new ArrayList<String>();
		list.add("manifestId");
		list.add("sysName");
		list.add("subSysName");
		list.add("nu");
		
		String sql = "select manifest_id,sys_name, sub_sys_name, count(sub_sys_name) nu from plan_detail_manifest "
				+ "group by sys_name,sub_sys_name";
		
		if (condition.getPlanId()!=0) {
			sql += " and plan_id ="+ condition.getPlanId() ;
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
   public Object list1(int pageNumber, int pageSize, PlanDetailManifest condition) throws ParseException {
		List<String> list = new ArrayList<String>();
		list.add("sysName");
		list.add("subSysName");
		String sql = "select distinct sys_name, sub_sys_name from plan_detail_manifest ";
		if (condition.getPlanId()!=0) {
			sql += " and plan_id ="+ condition.getPlanId() ;
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
				NaTestSituation NaTestSituation1=new NaTestSituation();
				NaTestSituation1.setSysName(NaTestSituation.getSysName());
				NaTestSituation1.setSubSysName(NaTestSituation.getSubSysName());
				NaTestSituation1.setTestSituation(NaTestSituation.getTestSituation());
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
			if(condition.getPlanId().equals("")||condition.getPlanId()!=null){
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
			if(condition.getPlanId().equals("")||condition.getPlanId()!=null){
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
			if(condition.getPlanId().equals("")||condition.getPlanId()!=null){
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
			if(condition.getPlanId().equals("")||condition.getPlanId()!=null){
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
			if(condition.getPlanId().equals("")||condition.getPlanId()!=null){
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
	   


}

