package com.ai.aiga.service.workFlowNew;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.dao.NaChangeConditionDao;
import com.ai.aiga.dao.NaChangeContentsDao;
import com.ai.aiga.dao.NaChangeDangurousEstimateDao;
import com.ai.aiga.dao.NaChangeGoalDeviceDao;
import com.ai.aiga.dao.NaChangeOperationManagerDao;
import com.ai.aiga.dao.NaChangePrepareWorkDao;
import com.ai.aiga.dao.NaChangeResultValidateDao;
import com.ai.aiga.dao.NaChangeRunStepDao;
import com.ai.aiga.dao.NaChangeServiceValidateDao;
import com.ai.aiga.dao.NaChangeTimePersonDao;
import com.ai.aiga.dao.NaChangeUpdateDao;
import com.ai.aiga.dao.NaInformationNoticeDao;
import com.ai.aiga.dao.NaRollbackMethodDao;
import com.ai.aiga.dao.NaWarningShieldDao;
import com.ai.aiga.dao.jpa.Condition;
import com.ai.aiga.dao.jpa.ParameterCondition;
import com.ai.aiga.domain.NaChangeCondition;
import com.ai.aiga.domain.NaChangeContents;
import com.ai.aiga.domain.NaChangeDangurousEstimate;
import com.ai.aiga.domain.NaChangeGoalDevice;
import com.ai.aiga.domain.NaChangeOperationManager;
import com.ai.aiga.domain.NaChangePrepareWork;
import com.ai.aiga.domain.NaChangeResultValidate;
import com.ai.aiga.domain.NaChangeRunStep;
import com.ai.aiga.domain.NaChangeServiceValidate;
import com.ai.aiga.domain.NaChangeTimePerson;
import com.ai.aiga.domain.NaChangeUpdate;
import com.ai.aiga.domain.NaInformationNotice;
import com.ai.aiga.domain.NaRollbackMethod;
import com.ai.aiga.domain.NaWarningShield;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.service.workFlowNew.dto.changeReviewList;
import com.ai.aiga.view.controller.workFlowNew.dto.ChangeReviewRequest;

/**
 * 变更评审sv
 * @author liuxx
 *@Date 2017-04-23
 */
@Service
@Transactional
public class ChangePlanReviewSv  extends BaseService{
	
	@Autowired
	private NaChangeConditionDao naChangeConditionDao;
	
	@Autowired
	private NaChangeContentsDao naChangeContentsDao;
	
	@Autowired
	private NaChangeTimePersonDao naChangeTimePersonDao;
	
	@Autowired
	private NaChangeGoalDeviceDao naChangeGoalDeviceDao;
	
	@Autowired
	private NaChangeUpdateDao naChangeUpdateDao;
	
	@Autowired
	private NaWarningShieldDao naWarningShieldDao;
	
	@Autowired
	private NaChangePrepareWorkDao naChangePrepareWorkDao;
	
	@Autowired
	private NaChangeRunStepDao naChangeRunStepDao;
	
	@Autowired
	private NaChangeOperationManagerDao naChangeOperationManagerDao;
	
	@Autowired
	private NaRollbackMethodDao naRollbackMethodDao;
	
	@Autowired
	private NaInformationNoticeDao naInformationNoticeDao;
	
	
	@Autowired
	private NaChangeDangurousEstimateDao naChangeDangurousEstimateDao;
	
	@Autowired
	private NaChangeServiceValidateDao naChangeServiceValidateDao;
	
	@Autowired
	private NaChangeResultValidateDao naChangeResultValidateDao;
	
	
	public List<NaChangeCondition> finNaChangeCondition(ChangeReviewRequest request,int pageSize,int pageNumber){
		if(request==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if(request.getPlanId()==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();
		
		if(request.getExt1()!=null&&!"".equals(request.getExt1())){
			cons.add(new Condition("ext1",request.getExt1() , Condition.Type.EQ));
		}
		
		if(pageNumber <= 0){
			pageNumber = 0;
		}else{
			pageNumber--;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return (List<NaChangeCondition>) naChangeConditionDao.search(cons, pageable);
	}
	
	
	public List<NaChangeContents> finNaChangeContents(ChangeReviewRequest request,int pageSize,int pageNumber){
		if(request==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if(request.getPlanId()==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();
		
		if(request.getExt1()!=null&&!"".equals(request.getExt1())){
			cons.add(new Condition("ext1",request.getExt1() , Condition.Type.EQ));
		}
		
		if(pageNumber <= 0){
			pageNumber = 0;
		}else{
			pageNumber--;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return (List<NaChangeContents>) naChangeContentsDao.search(cons, pageable);
	}
	
	
	public List<NaChangeTimePerson> findNaChangeTimePerson(ChangeReviewRequest request,int pageSize,int pageNumber){
		if(request==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if(request.getPlanId()==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();
		
		if(request.getExt1()!=null&&!"".equals(request.getExt1())){
			cons.add(new Condition("ext1",request.getExt1() , Condition.Type.EQ));
		}
		
		if(pageNumber <= 0){
			pageNumber = 0;
		}else{
			pageNumber--;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return (List<NaChangeTimePerson>) naChangeTimePersonDao.search(cons, pageable);
	}
	
	
	public List<NaChangeTimePerson> finNaChangeTimePerson(ChangeReviewRequest request,int pageSize,int pageNumber){
		if(request==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if(request.getPlanId()==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();
		
		if(request.getExt1()!=null&&!"".equals(request.getExt1())){
			cons.add(new Condition("ext1",request.getExt1() , Condition.Type.EQ));
		}
		
		if(pageNumber <= 0){
			pageNumber = 0;
		}else{
			pageNumber--;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return (List<NaChangeTimePerson>) naChangeTimePersonDao.search(cons, pageable);
	}
	
	
	public List<NaChangeUpdate> findNaChangeUpdate(ChangeReviewRequest request,int pageSize,int pageNumber){
		if(request==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if(request.getPlanId()==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();
		
		if(request.getExt1()!=null&&!"".equals(request.getExt1())){
			cons.add(new Condition("ext1",request.getExt1() , Condition.Type.EQ));
		}
		
		if(pageNumber <= 0){
			pageNumber = 0;
		}else{
			pageNumber--;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return (List<NaChangeUpdate>) naChangeUpdateDao.search(cons, pageable);
	}
	
	
	public List<NaChangeGoalDevice> findNaChangeGoalDevice(ChangeReviewRequest request,int pageSize,int pageNumber){
		if(request==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if(request.getPlanId()==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();
		
		if(request.getExt1()!=null&&!"".equals(request.getExt1())){
			cons.add(new Condition("ext1",request.getExt1() , Condition.Type.EQ));
		}
		
		if(pageNumber <= 0){
			pageNumber = 0;
		}else{
			pageNumber--;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return (List<NaChangeGoalDevice>) naChangeGoalDeviceDao.search(cons, pageable);
	}
	
	
	public List<NaWarningShield> findNaWarningShield(ChangeReviewRequest request,int pageSize,int pageNumber){
		if(request==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if(request.getPlanId()==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();
		
		if(request.getExt1()!=null&&!"".equals(request.getExt1())){
			cons.add(new Condition("ext1",request.getExt1() , Condition.Type.EQ));
		}
		
		if(pageNumber <= 0){
			pageNumber = 0;
		}else{
			pageNumber--;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return (List<NaWarningShield>) naWarningShieldDao.search(cons, pageable);
	}
	
	
	public List<NaChangePrepareWork> findNaChangePrepareWork(ChangeReviewRequest request,int pageSize,int pageNumber){
		if(request==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if(request.getPlanId()==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();
		
		if(request.getExt1()!=null&&!"".equals(request.getExt1())){
			cons.add(new Condition("ext1",request.getExt1() , Condition.Type.EQ));
		}
		
		if(pageNumber <= 0){
			pageNumber = 0;
		}else{
			pageNumber--;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return (List<NaChangePrepareWork>) naChangePrepareWorkDao.search(cons, pageable);
	}
	
	
	public List<NaChangeOperationManager> findNaChangeOperationManager(ChangeReviewRequest request,int pageSize,int pageNumber){
		if(request==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if(request.getPlanId()==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();
		
		if(request.getExt1()!=null&&!"".equals(request.getExt1())){
			cons.add(new Condition("ext1",request.getExt1() , Condition.Type.EQ));
		}
		
		if(pageNumber <= 0){
			pageNumber = 0;
		}else{
			pageNumber--;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return (List<NaChangeOperationManager>) naChangeOperationManagerDao.search(cons, pageable);
	}
	
	

	public List<NaRollbackMethod> findNaRollbackMethod(ChangeReviewRequest request,int pageSize,int pageNumber){
		if(request==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if(request.getPlanId()==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();
		
		if(request.getExt1()!=null&&!"".equals(request.getExt1())){
			cons.add(new Condition("ext1",request.getExt1() , Condition.Type.EQ));
		}
		
		if(pageNumber <= 0){
			pageNumber = 0;
		}else{
			pageNumber--;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return (List<NaRollbackMethod>) naRollbackMethodDao.search(cons, pageable);
	}
	//
	
	public List<NaChangeRunStep> findNaChangeRunStep(ChangeReviewRequest request,int pageSize,int pageNumber){
		if(request==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if(request.getPlanId()==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();
		
		if(request.getExt1()!=null&&!"".equals(request.getExt1())){
			cons.add(new Condition("ext1",request.getExt1() , Condition.Type.EQ));
		}
		
		if(pageNumber <= 0){
			pageNumber = 0;
		}else{
			pageNumber--;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return (List<NaChangeRunStep>) naChangeRunStepDao.search(cons, pageable);
	}
	
	
	public List<NaInformationNotice> findNaInformationNotice(ChangeReviewRequest request,int pageSize,int pageNumber){
		if(request==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if(request.getPlanId()==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();
		
		if(request.getExt1()!=null&&!"".equals(request.getExt1())){
			cons.add(new Condition("ext1",request.getExt1() , Condition.Type.EQ));
		}
		
		if(pageNumber <= 0){
			pageNumber = 0;
		}else{
			pageNumber--;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return (List<NaInformationNotice>) naInformationNoticeDao.search(cons, pageable);
	}
	
	
	
	public List<NaChangeDangurousEstimate> findNaChangeDangurousEstimate(ChangeReviewRequest request,int pageSize,int pageNumber){
		if(request==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if(request.getPlanId()==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();
		
		if(request.getExt1()!=null&&!"".equals(request.getExt1())){
			cons.add(new Condition("ext1",request.getExt1() , Condition.Type.EQ));
		}
		
		if(pageNumber <= 0){
			pageNumber = 0;
		}else{
			pageNumber--;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return (List<NaChangeDangurousEstimate>) naChangeDangurousEstimateDao.search(cons, pageable);
	}
	
	//
	
	public List<NaChangeServiceValidate> findNaChangeServiceValidate(ChangeReviewRequest request,int pageSize,int pageNumber){
		if(request==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if(request.getPlanId()==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();
		
		if(request.getExt1()!=null&&!"".equals(request.getExt1())){
			cons.add(new Condition("ext1",request.getExt1() , Condition.Type.EQ));
		}
		
		if(pageNumber <= 0){
			pageNumber = 0;
		}else{
			pageNumber--;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return (List<NaChangeServiceValidate>) naChangeServiceValidateDao.search(cons, pageable);
	}
	
	

	public List<NaChangeResultValidate> findNaChangeResultValidate(ChangeReviewRequest request,int pageSize,int pageNumber){
		if(request==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if(request.getPlanId()==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();
		
		if(request.getExt1()!=null&&!"".equals(request.getExt1())){
			cons.add(new Condition("ext1",request.getExt1() , Condition.Type.EQ));
		}
		
		if(pageNumber <= 0){
			pageNumber = 0;
		}else{
			pageNumber--;
		}
		
		if(pageSize <= 0){
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return (List<NaChangeResultValidate>) naChangeResultValidateDao.search(cons, pageable);
	}
	
	
/**
 * 查询评审信息
 * @param planId计划id
 * @param type 1：查询回退的交付物 ，2查询全部交付物评审记录
 * @param pageSize
 * @param pageNumber
 * @return
 */
	public List<changeReviewList> findchangeReviewList(Long planId,Long type,int pageSize,int pageNumber){
		if(planId==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "planId");
		}
		StringBuilder s = new StringBuilder();
		List<ParameterCondition> param = new ArrayList<ParameterCondition>();
			s.append("select b.id,b.ext_2 as file_id, a.file_name, b.conclusion, b.review_result, b.reviewer, b.review_date, b.remark");
			s.append("from NA_FILE_UPLOAD a");
			s.append(" left join NA_CHANGE_REVIEW b");
			s.append("   on a.id = b.ext_2");
			s.append(" where a.file_type = 20");
			s.append(" and a.plan_id = :planId");
			param.add(new ParameterCondition("planId", planId));
			if(type==1){
			 s.append(" and  b.conclusion is null ");
			}
			
			if(pageNumber <= 0){
				pageNumber = 0;
			}else{
				pageNumber--;
			}
			
			if(pageSize <= 0){
				pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
			}

			Pageable pageable = new PageRequest(pageNumber, pageSize);
		
		return (List<changeReviewList>) naChangeResultValidateDao.searchByNativeSQL(s.toString(), param,changeReviewList.class, pageable);
	}
	
	
//	public saveChangeReview()
}
