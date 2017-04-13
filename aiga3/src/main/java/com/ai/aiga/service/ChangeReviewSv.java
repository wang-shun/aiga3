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
		
		if (pageNumber < 0) {
			pageNumber = 0;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);

		return planDetailManifestDao.searchByNativeSQL(sql, pageable, list);
	}
   public List<NaCodePath> findCodePath() {
	   
		return  codePathDao.findAll();
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
   
   public Object  findPlanDetailManifest(PlanDetailManifest condition,int pageNumber, int pageSize){
		List<Condition> cons = new ArrayList<Condition>();
		
		
		
		if(pageNumber < 0){
			pageNumber = 0;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return planDetailManifestDao.search(cons, pageable);
		
   }	
		//return responses;
 
   public List<PlanDetailManifest> findPlanDetailManifest(){
	   
	   return planDetailManifestDao.findAll();
   }
   
 public List<NaTestLeaveOver> findTestLeaveOver(){
	   
	   return testLeaveOverDao.findAll();
   }
 
 public List<NaRequireList> findRequireList(){
	   
	   return naRequireListDao.findAll();
 }
 public List<NaDatabaseConfiScript> findDatabaseConfi(){
	   
	   return databaseConfiDao.findAll();
}
 
 public List<NaDatabaseScriptList> findDatabaseScriptList(){
	   
	   return databaseScriptListDao.findAll();
}
 
 public List<NaDbScriptList> findDbScriptList(){
	   
	   return dbScriptListDao.findAll();
}
 
}

