package com.ai.aiga.service.workFlowNew;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;

import org.apache.commons.beanutils.BeanMap;
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
import com.ai.aiga.dao.NaChangePlanOnileDao;
import com.ai.aiga.dao.NaChangePrepareWorkDao;
import com.ai.aiga.dao.NaChangeResultValidateDao;
import com.ai.aiga.dao.NaChangeReviewDao;
import com.ai.aiga.dao.NaChangeRunStepDao;
import com.ai.aiga.dao.NaChangeServiceValidateDao;
import com.ai.aiga.dao.NaChangeTimePersonDao;
import com.ai.aiga.dao.NaChangeUpdateDao;
import com.ai.aiga.dao.NaInformationNoticeDao;
import com.ai.aiga.dao.NaQuantitativeRiskDao;
import com.ai.aiga.dao.NaRiskRatingScaleDao;
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
import com.ai.aiga.domain.NaChangeReview;
import com.ai.aiga.domain.NaChangeRunStep;
import com.ai.aiga.domain.NaChangeServiceValidate;
import com.ai.aiga.domain.NaChangeTimePerson;
import com.ai.aiga.domain.NaChangeUpdate;
import com.ai.aiga.domain.NaInformationNotice;
import com.ai.aiga.domain.NaQuantitativeRisk;
import com.ai.aiga.domain.NaRiskRatingScale;
import com.ai.aiga.domain.NaRollbackMethod;
import com.ai.aiga.domain.NaWarningShield;
import com.ai.aiga.exception.BusinessException;
import com.ai.aiga.exception.ErrorCode;
import com.ai.aiga.service.base.BaseService;
import com.ai.aiga.service.enums.WorkFlowNewEnum;
import com.ai.aiga.service.workFlowNew.dto.ChangeResultValidateRequest;
import com.ai.aiga.service.workFlowNew.dto.WarningShieldRequest;
import com.ai.aiga.service.workFlowNew.dto.ChangeReviewList;
import com.ai.aiga.service.workFlowNew.dto.FileList;
import com.ai.aiga.util.mapper.BeanMapper;
import com.ai.aiga.view.controller.workFlowNew.dto.ChangeConditionRequest;
import com.ai.aiga.view.controller.workFlowNew.dto.ChangeContentsRequest;
import com.ai.aiga.view.controller.workFlowNew.dto.ChangeDangurousEstimateRequest;
import com.ai.aiga.view.controller.workFlowNew.dto.ChangeGoalDeviceRequest;
import com.ai.aiga.view.controller.workFlowNew.dto.ChangeOperationManagerRequest;
import com.ai.aiga.view.controller.workFlowNew.dto.ChangePrepareWorkRequest;
import com.ai.aiga.view.controller.workFlowNew.dto.ChangeReviewRequest;
import com.ai.aiga.view.controller.workFlowNew.dto.ChangeReviewResultRequest;
import com.ai.aiga.view.controller.workFlowNew.dto.ChangeRunStepRequest;
import com.ai.aiga.view.controller.workFlowNew.dto.ChangeServiceValidateRequest;
import com.ai.aiga.view.controller.workFlowNew.dto.ChangeTimePersonRequest;
import com.ai.aiga.view.controller.workFlowNew.dto.ChangeUpdateRequest;
import com.ai.aiga.view.controller.workFlowNew.dto.InformationNoticeRequest;
import com.ai.aiga.view.controller.workFlowNew.dto.QuantitativeRiskRequest;
import com.ai.aiga.view.controller.workFlowNew.dto.RiskRatingScaleRequest;
import com.ai.aiga.view.controller.workFlowNew.dto.RollbackMethodRequest;
import com.ai.aiga.view.util.SessionMgrUtil;

/**
 * 变更评审sv
 * 
 * @author liuxx
 * @Date 2017-04-23
 */
@Service
@Transactional
public class ChangePlanReviewSv extends BaseService {

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
	
	@Autowired
	private NaChangeReviewDao naChangeReviewDao;
	
	@Autowired
	private NaQuantitativeRiskDao naQuantitativeRiskDao;
	
	@Autowired
	private NaRiskRatingScaleDao naRiskRatingScaleDao;
	
	@Autowired
	private NaChangePlanOnileDao naChangePlanOnileDao;

	/**
	 * 查询变更概况
	 * 
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Object findNaChangeCondition(ChangeReviewRequest request, int pageSize, int pageNumber) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();

		cons.add(new Condition("planId", request.getPlanId(), Condition.Type.EQ));
		if (request.getExt1() != null && !"".equals(request.getExt1())) {
			cons.add(new Condition("ext1", request.getExt1(), Condition.Type.EQ));
		}

		if (pageNumber <= 0) {
			pageNumber = 0;
		} else {
			pageNumber--;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);

		return naChangeConditionDao.search(cons, pageable);
	}

	/**
	 * 保存变更概况
	 * 
	 * @param request
	 */
	public void saveChangeCondition(List<ChangeConditionRequest> requests) {
		if (requests == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "requests");
		}
		for (ChangeConditionRequest request : requests) {
			NaChangeCondition condition = BeanMapper.map(request, NaChangeCondition.class);
			NaChangeCondition conditions = naChangeConditionDao.getOne(request.getId());
			condition.setPlanId(conditions.getPlanId());
			condition.setFileName(conditions.getFileName());
			condition.setExt1(conditions.getExt1());
			naChangeConditionDao.save(condition);
		}
	
	}

	/**
	 * 查询变更内容
	 * 
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Object  findNaChangeContents(ChangeReviewRequest request, int pageSize, int pageNumber) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();
		cons.add(new Condition("planId", request.getPlanId(), Condition.Type.EQ));
		if (request.getExt1() != null && !"".equals(request.getExt1())) {
			cons.add(new Condition("ext1", request.getExt1(), Condition.Type.EQ));
		}

		if (pageNumber <= 0) {
			pageNumber = 0;
		} else {
			pageNumber--;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);

		return naChangeContentsDao.search(cons, pageable);
	}

	/**
	 * 保存变更内容
	 * 
	 * @param request
	 */
	public void saveChangeContents(List<ChangeContentsRequest> requests) {
		if (requests == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		for (ChangeContentsRequest request : requests) {
			NaChangeContents contens = BeanMapper.map(request, NaChangeContents.class);
			NaChangeContents conditions = naChangeContentsDao.getOne(request.getId());
			contens.setPlanId(conditions.getPlanId());
			contens.setFileName(conditions.getFileName());
			contens.setExt1(conditions.getExt1());
			naChangeContentsDao.save(contens);
		}
		
	}

	/**
	 * 查询时间及人员
	 * 
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Object  findNaChangeTimePerson(ChangeReviewRequest request, int pageSize, int pageNumber) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();
		cons.add(new Condition("planId", request.getPlanId(), Condition.Type.EQ));
		if (request.getExt1() != null && !"".equals(request.getExt1())) {
			cons.add(new Condition("ext1", request.getExt1(), Condition.Type.EQ));
		}

		if (pageNumber <= 0) {
			pageNumber = 0;
		} else {
			pageNumber--;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);

		return naChangeTimePersonDao.search(cons, pageable);
	}

	/**
	 * 保存变更时间及人员
	 * 
	 * @param request
	 */
	public void saveNaChangeTimePerson(List<ChangeTimePersonRequest> requests) {
		if (requests == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		for (ChangeTimePersonRequest request : requests) {
			NaChangeTimePerson contens = BeanMapper.map(request, NaChangeTimePerson.class);
			NaChangeTimePerson conditions = naChangeTimePersonDao.getOne(request.getId());
			contens.setPlanId(conditions.getPlanId());
			contens.setFileName(conditions.getFileName());
			contens.setExt1(conditions.getExt1());
			naChangeTimePersonDao.save(contens);
		}
	
	}

	/**
	 * 查询配置是否更新
	 * 
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Object  findNaChangeUpdate(ChangeReviewRequest request, int pageSize, int pageNumber) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();
		cons.add(new Condition("planId", request.getPlanId(), Condition.Type.EQ));
		if (request.getExt1() != null && !"".equals(request.getExt1())) {
			cons.add(new Condition("ext1", request.getExt1(), Condition.Type.EQ));
		}

		if (pageNumber <= 0) {
			pageNumber = 0;
		} else {
			pageNumber--;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);

		return  naChangeUpdateDao.search(cons, pageable);
	}

	/**
	 * 保存变更配置
	 * 
	 * @param request
	 */
	public void saveNaChangeUpdate(List<ChangeUpdateRequest> requests) {
		if (requests == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		for (ChangeUpdateRequest request : requests) {
			NaChangeUpdate contens = BeanMapper.map(request, NaChangeUpdate.class);
			NaChangeUpdate conditions = naChangeUpdateDao.getOne(request.getId());
			contens.setPlanId(conditions.getPlanId());
			contens.setFileName(conditions.getFileName());
			contens.setExt1(conditions.getExt1());
			naChangeUpdateDao.save(contens);
		}
	
	}

	/**
	 * 查询变更目标设备
	 * 
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Object  findNaChangeGoalDevice(ChangeReviewRequest request, int pageSize, int pageNumber) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();
		cons.add(new Condition("planId", request.getPlanId(), Condition.Type.EQ));
		if (request.getExt1() != null && !"".equals(request.getExt1())) {
			cons.add(new Condition("ext1", request.getExt1(), Condition.Type.EQ));
		}

		if (pageNumber <= 0) {
			pageNumber = 0;
		} else {
			pageNumber--;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);

		return naChangeGoalDeviceDao.search(cons, pageable);
	}

	/**
	 * 保存变更目标设备
	 * 
	 * @param request
	 */
	public void saveNaChangeGoalDevice(List<ChangeGoalDeviceRequest> requests) {
		if (requests == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		for (ChangeGoalDeviceRequest request : requests) {
			NaChangeGoalDevice contens = BeanMapper.map(request, NaChangeGoalDevice.class);
			NaChangeGoalDevice conditions = naChangeGoalDeviceDao.getOne(request.getId());
			contens.setPlanId(conditions.getPlanId());
			contens.setFileName(conditions.getFileName());
			contens.setExt1(conditions.getExt1());
			naChangeGoalDeviceDao.save(contens);
		}
	
	}

	/**
	 * 查询告警屏蔽
	 * 
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Object findNaWarningShield(ChangeReviewRequest request, int pageSize, int pageNumber) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();
		cons.add(new Condition("planId", request.getPlanId(), Condition.Type.EQ));
		if (request.getExt1() != null && !"".equals(request.getExt1())) {
			cons.add(new Condition("ext1", request.getExt1(), Condition.Type.EQ));
		}

		if (pageNumber <= 0) {
			pageNumber = 0;
		} else {
			pageNumber--;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);

		return  naWarningShieldDao.search(cons, pageable);
	}

	/**
	 * 保存告警屏蔽
	 * 
	 * @param request
	 */
	public void saveNaWarningShield(List<WarningShieldRequest> requests) {
		if (requests == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		for (WarningShieldRequest request : requests) {
			NaWarningShield contens = BeanMapper.map(request, NaWarningShield.class);
			NaWarningShield conditions = naWarningShieldDao.getOne(request.getId());
			contens.setPlanId(conditions.getPlanId());
			contens.setFileName(conditions.getFileName());
			contens.setExt1(conditions.getExt1());
			naWarningShieldDao.save(contens);
		}
		
	}

	/**
	 * 查询变更准备工作
	 * 
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Object findNaChangePrepareWork(ChangeReviewRequest request, int pageSize,
			int pageNumber) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();
		cons.add(new Condition("planId", request.getPlanId(), Condition.Type.EQ));
		if (request.getExt1() != null && !"".equals(request.getExt1())) {
			cons.add(new Condition("ext1", request.getExt1(), Condition.Type.EQ));
		}

		if (pageNumber <= 0) {
			pageNumber = 0;
		} else {
			pageNumber--;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);

		return   naChangePrepareWorkDao.search(cons, pageable);
	}

	/**
	 * 保存变更准备工作
	 * 
	 * @param request
	 */
	public void saveNaChangePrepareWork(List<ChangePrepareWorkRequest> requests) {
		if (requests == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		for (ChangePrepareWorkRequest request : requests) {
			NaChangePrepareWork contens = BeanMapper.map(request, NaChangePrepareWork.class);
			NaChangePrepareWork conditions = naChangePrepareWorkDao.getOne(request.getId());
			contens.setPlanId(conditions.getPlanId());
			contens.setFileName(conditions.getFileName());
			contens.setExt1(conditions.getExt1());
			naChangePrepareWorkDao.save(contens);
		}
	
	}

	/**
	 * 查询变更运维管理
	 * 
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Object findNaChangeOperationManager(ChangeReviewRequest request, int pageSize,
			int pageNumber) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();
		cons.add(new Condition("planId", request.getPlanId(), Condition.Type.EQ));
		if (request.getExt1() != null && !"".equals(request.getExt1())) {
			cons.add(new Condition("ext1", request.getExt1(), Condition.Type.EQ));
		}

		if (pageNumber <= 0) {
			pageNumber = 0;
		} else {
			pageNumber--;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);

		return naChangeOperationManagerDao.search(cons, pageable);
	}

	/**
	 * 保存变更运维管理
	 * 
	 * @param request
	 */
	public void saveNaChangeOperationManager(List<ChangeOperationManagerRequest> requests) {
		if (requests == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		for (ChangeOperationManagerRequest request : requests) {
			NaChangeOperationManager contens = BeanMapper.map(request, NaChangeOperationManager.class);
			NaChangeOperationManager conditions = naChangeOperationManagerDao.getOne(request.getId());
			contens.setPlanId(conditions.getPlanId());
			contens.setFileName(conditions.getFileName());
			contens.setExt1(conditions.getExt1());
			naChangeOperationManagerDao.save(contens);
		}
	
	}

	/**
	 * 查询回退方法
	 * 
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Object findNaRollbackMethod(ChangeReviewRequest request, int pageSize, int pageNumber) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();
		cons.add(new Condition("planId", request.getPlanId(), Condition.Type.EQ));
		if (request.getExt1() != null && !"".equals(request.getExt1())) {
			cons.add(new Condition("ext1", request.getExt1(), Condition.Type.EQ));
		}

		if (pageNumber <= 0) {
			pageNumber = 0;
		} else {
			pageNumber--;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);

		return  naRollbackMethodDao.search(cons, pageable);
	}

	/**
	 * 保存回退方法
	 * 
	 * @param request
	 */
	public void saveNaRollbackMethod(List<RollbackMethodRequest> requests) {
		if (requests == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		for (RollbackMethodRequest request : requests) {
			NaRollbackMethod contens = BeanMapper.map(request, NaRollbackMethod.class);
			NaRollbackMethod conditions = naRollbackMethodDao.getOne(request.getId());
			contens.setPlanId(conditions.getPlanId());
			contens.setFileName(conditions.getFileName());
			contens.setExt1(conditions.getExt1());
			naRollbackMethodDao.save(contens);
		}
	
	}

	/**
	 * 查询变更实施步骤
	 * 
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Object  findNaChangeRunStep(ChangeReviewRequest request, int pageSize, int pageNumber) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();
		cons.add(new Condition("planId", request.getPlanId(), Condition.Type.EQ));
		if (request.getExt1() != null && !"".equals(request.getExt1())) {
			cons.add(new Condition("ext1", request.getExt1(), Condition.Type.EQ));
		}

		if (pageNumber <= 0) {
			pageNumber = 0;
		} else {
			pageNumber--;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);

		return  naChangeRunStepDao.search(cons, pageable);
	}

	/**
	 * 保存变更实施步骤
	 * 
	 * @param request
	 */
	public void saveNaChangeRunStep(List<ChangeRunStepRequest> requests) {
		if (requests == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		for (ChangeRunStepRequest request : requests) {
		
			NaChangeRunStep contens = BeanMapper.map(request, NaChangeRunStep.class);
			NaChangeRunStep conditions = naChangeRunStepDao.getOne(request.getId());
			contens.setPlanId(conditions.getPlanId());
			contens.setFileName(conditions.getFileName());
			contens.setExt1(conditions.getExt1());
			naChangeRunStepDao.save(contens);
		}
	
	}

	/**
	 * 查询信息通告
	 * 
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Object  findNaInformationNotice(ChangeReviewRequest request, int pageSize,
			int pageNumber) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();
		cons.add(new Condition("planId", request.getPlanId(), Condition.Type.EQ));
		if (request.getExt1() != null && !"".equals(request.getExt1())) {
			cons.add(new Condition("ext1", request.getExt1(), Condition.Type.EQ));
		}

		if (pageNumber <= 0) {
			pageNumber = 0;
		} else {
			pageNumber--;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);

		return  naInformationNoticeDao.search(cons, pageable);
	}

	/**
	 * 保存变更信息通告
	 * 
	 * @param request
	 */
	public void saveNaInformationNotice(List<InformationNoticeRequest> requests) {
		if (requests == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		for (InformationNoticeRequest request : requests) {
		
			NaInformationNotice contens = BeanMapper.map(request, NaInformationNotice.class);
			NaInformationNotice conditions = naInformationNoticeDao.getOne(request.getId());
			contens.setPlanId(conditions.getPlanId());
			contens.setFileName(conditions.getFileName());
			contens.setExt1(conditions.getExt1());
			naInformationNoticeDao.save(contens);
		}
		
	}

	/**
	 * 查询变更风险评估量化表
	 * 
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Object findNaChangeDangurousEstimate(ChangeReviewRequest request, int pageSize,
			int pageNumber) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();
		cons.add(new Condition("planId", request.getPlanId(), Condition.Type.EQ));
		if (request.getExt1() != null && !"".equals(request.getExt1())) {
			cons.add(new Condition("ext1", request.getExt1(), Condition.Type.EQ));
		}

		if (pageNumber <= 0) {
			pageNumber = 0;
		} else {
			pageNumber--;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);

		return naChangeDangurousEstimateDao.search(cons, pageable);
	}


	/**
	 * 保存变更风险评估量化表
	 * 
	 * @param request
	 */
	public void saveNaChangeDangurousEstimate(List<ChangeDangurousEstimateRequest> requests) {
		if (requests == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		for (ChangeDangurousEstimateRequest request : requests) {
			
			NaChangeDangurousEstimate contens = BeanMapper.map(request, NaChangeDangurousEstimate.class);
			NaChangeDangurousEstimate conditions = naChangeDangurousEstimateDao.getOne(request.getId());
			contens.setPlanId(conditions.getPlanId());
			contens.setFileName(conditions.getFileName());
			contens.setExt1(conditions.getExt1());
			naChangeDangurousEstimateDao.save(contens);
		}
		
	}

	
	
	/**
	 * 查询变更风险评估量得分
	 * 
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Object  findNaQuantitativeRisk(ChangeReviewRequest request, int pageSize,
			int pageNumber) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();
		cons.add(new Condition("planId", request.getPlanId(), Condition.Type.EQ));
		if (request.getExt1() != null && !"".equals(request.getExt1())) {
			cons.add(new Condition("ext1", request.getExt1(), Condition.Type.EQ));
		}

		if (pageNumber <= 0) {
			pageNumber = 0;
		} else {
			pageNumber--;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);

		return naQuantitativeRiskDao.search(cons, pageable);
	}


	/**
	 * 保存变更风险评估得分
	 * 
	 * @param request
	 */
	public void saveNaQuantitativeRisk(List<QuantitativeRiskRequest> requests) {
		if (requests == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		for (QuantitativeRiskRequest request : requests) {
			
			NaQuantitativeRisk contens = BeanMapper.map(request, NaQuantitativeRisk.class);
			NaQuantitativeRisk conditions = naQuantitativeRiskDao.getOne(request.getId());
			contens.setPlanId(conditions.getPlanId());
			contens.setFileName(conditions.getFileName());
			contens.setExt1(conditions.getExt1());
			naQuantitativeRiskDao.save(contens);
		}
	
	}

	
	
	/**
	 * 查询变更风险评估
	 * 
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Object  findNaRiskRatingScale(ChangeReviewRequest request, int pageSize,
			int pageNumber) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();
		cons.add(new Condition("planId", request.getPlanId(), Condition.Type.EQ));
		if (request.getExt1() != null && !"".equals(request.getExt1())) {
			cons.add(new Condition("ext1", request.getExt1(), Condition.Type.EQ));
		}

		if (pageNumber <= 0) {
			pageNumber = 0;
		} else {
			pageNumber--;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);

		return  naRiskRatingScaleDao.search(cons, pageable);
	}

	//
	/**
	 * 保存变更风险评估
	 * 
	 * @param request
	 */
	public void saveNaRiskRatingScale(List<RiskRatingScaleRequest> requests) {
		if (requests == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		for (RiskRatingScaleRequest request : requests) {
		
			NaRiskRatingScale contens = BeanMapper.map(request, NaRiskRatingScale.class);
			NaRiskRatingScale conditions = naRiskRatingScaleDao.getOne(request.getId());
			contens.setPlanId(conditions.getPlanId());
			contens.setFileName(conditions.getFileName());
			contens.setExt1(conditions.getExt1());
			naRiskRatingScaleDao.save(contens);
		}
	
	}

	
	
	
	/**
	 * 查询变更业务验证
	 * 
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Object  findNaChangeServiceValidate(ChangeReviewRequest request, int pageSize,
			int pageNumber) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();
		cons.add(new Condition("planId", request.getPlanId(), Condition.Type.EQ));
		if (request.getExt1() != null && !"".equals(request.getExt1())) {
			cons.add(new Condition("ext1", request.getExt1(), Condition.Type.EQ));
		}

		if (pageNumber <= 0) {
			pageNumber = 0;
		} else {
			pageNumber--;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);

		return  naChangeServiceValidateDao.search(cons, pageable);
	}

	/**
	 * 保存业务变更验证
	 * 
	 * @param request
	 */
	public void saveNaChangeServiceValidate(List<ChangeServiceValidateRequest> requests) {
		if (requests == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		for (ChangeServiceValidateRequest request : requests) {
			
			NaChangeServiceValidate contens = BeanMapper.map(request, NaChangeServiceValidate.class);
			NaChangeServiceValidate conditions = naChangeServiceValidateDao.getOne(request.getId());
			contens.setPlanId(conditions.getPlanId());
			contens.setFileName(conditions.getFileName());
			contens.setExt1(conditions.getExt1());
			naChangeServiceValidateDao.save(contens);
		}
	
	}

	/**
	 * 查询变更结果验证
	 * 
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Object  findNaChangeResultValidate(ChangeReviewRequest request, int pageSize,
			int pageNumber) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();
		cons.add(new Condition("planId", request.getPlanId(), Condition.Type.EQ));
		if (request.getExt1() != null && !"".equals(request.getExt1())) {
			cons.add(new Condition("ext1", request.getExt1(), Condition.Type.EQ));
		}

		if (pageNumber <= 0) {
			pageNumber = 0;
		} else {
			pageNumber--;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);

		return naChangeResultValidateDao.search(cons, pageable);
	}

	/**
	 * 保存变更结果验证
	 * 
	 * @param request
	 */
	public void saveNaChangeResultValidate(List<ChangeResultValidateRequest> requests) {
		if (requests == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		for (ChangeResultValidateRequest request : requests) {
		
			NaChangeResultValidate contens = BeanMapper.map(request, NaChangeResultValidate.class);
			
			NaChangeResultValidate conditions = naChangeResultValidateDao.getOne(request.getId());
			contens.setPlanId(conditions.getPlanId());
			contens.setFileName(conditions.getFileName());
			contens.setExt1(conditions.getExt1());
			naChangeResultValidateDao.save(contens);
		}
	
	}

	/**
	 * 查询评审信息
	 * 
	 * @param planId计划id
	 * @param type
	 *            1：查询回退的交付物 ，2查询全部交付物评审记录
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public Object  findchangeReviewList(Long planId, Long type, int pageSize, int pageNumber) {
		if (planId == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "planId");
		}
		StringBuilder s = new StringBuilder();
		List<ParameterCondition> param = new ArrayList<ParameterCondition>();
		s.append(
				"select b.review_id as id ,b.ext_2 as file_id, a.file_name, b.conclusion, b.review_result, b.reviewer, b.review_date, b.remark,b.PLAN_REVIEW_DATE,b.REVIEW_NUM");
		s.append(" from  NA_CHANGE_REVIEW b");
		s.append(" left join NA_FILE_UPLOAD  a");
		s.append("   on a.id = b.ext_2");
		s.append("  where a.file_type = 20");
		s.append("  and a.plan_id = :planId");
		param.add(new ParameterCondition("planId", planId));
		System.out.println("type"+type);
		if (type == 1L) {
			s.append(" and  b.conclusion is null ");
		}

		if (pageNumber <= 0) {
			pageNumber = 0;
		} else {
			pageNumber--;
		}

		if (pageSize <= 0) {
			pageSize = BusiConstant.PAGE_SIZE_DEFAULT;
		}

		Pageable pageable = new PageRequest(pageNumber, pageSize);

		return  naChangeResultValidateDao.searchByNativeSQL(s.toString(), param,
				ChangeReviewList.class, pageable);
	}
	
	
	
	/**
	 * 查询评审信息
	 * 
	 * @param planId计划id
	 * @param type
	 *            1：查询回退的交付物 ，2查询全部交付物评审记录
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public List<FileList>  findFileList(Long planId) {
		if (planId == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "planId");
		}
		StringBuilder s = new StringBuilder();
		List<ParameterCondition> param = new ArrayList<ParameterCondition>();
		s.append(
				"select a.id, a.file_name ");
		s.append(" from NA_FILE_UPLOAD a");
		s.append("  where a.file_type = 20");
		s.append("  and a.plan_id = :planId");
		param.add(new ParameterCondition("planId", planId));
		
		return  naChangeResultValidateDao.searchByNativeSQL(s.toString(), param,
				FileList.class);
	}
	
	
	
	
	/**
	 * 保存评审结论
	 * @param request
	 */
	public void saveChangeReviewResult(List<ChangeReviewResultRequest> requests){
		if(requests==null){	
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		for (ChangeReviewResultRequest request : requests) {
			NaChangeReview naChangeReview = naChangeReviewDao.findOne(request.getId());
			
			naChangeReview.setReviewResult(request.getReviewResult()==null?"":request.getReviewResult());
			if(request.getConclusion()!=null){
				naChangeReview.setConclusion(request.getConclusion());
			}
			naChangeReview.setRemark(request.getRemark()==null?"":request.getRemark());
			naChangeReview.setReviewer(String.valueOf(SessionMgrUtil.getStaff().getOpId()));
			naChangeReview.setReviewDate(new Date());
			naChangeReview.setReviewer(String.valueOf(SessionMgrUtil.getStaff().getStaffId()));
			naChangeReviewDao.save(naChangeReview);
			
			//评审通过
			if(request.getConclusion()==WorkFlowNewEnum.ReviewResult_Yes.getValue()){
				
//				NaChangeReview naChangeReviews = naChangeReviewDao.findByExt2(request.getFileId());
//				if(naChangeReview!=null){
//					naChangeReviewDao.delete(naChangeReviews.getReviewId());
//				}
			}else{
				//评审不通过
				NaChangeReview changeReview = new NaChangeReview() ;
				changeReview.setOnlinePlanId(naChangeReview.getOnlinePlanId());
				changeReview.setExt2(naChangeReview.getExt2());
				changeReview.setReviewNum(naChangeReview.getReviewNum()+1);
				naChangeReviewDao.save(changeReview);
			}
		}
	
	}

	
	/**
	 * 设置第N次评审时间
	 * @param planId
	 * @param reviewNum
	 * @param planReviewDate
	 */
	public void setReviewDate(Long planId, Long reviewNum ,String planReviewDate){
		if(planId==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "planId");
		}
		if(reviewNum<1){
			BusinessException.throwBusinessException("评审次数不符合实际情况");
		}
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(planReviewDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		naChangeReviewDao.setReviewDate(planId ,reviewNum ,date);
		naChangePlanOnileDao.updateFileUploadLastTime(planId  ,date);
	}
}
