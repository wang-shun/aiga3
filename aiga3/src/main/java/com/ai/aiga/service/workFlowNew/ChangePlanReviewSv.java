package com.ai.aiga.service.workFlowNew;

import java.util.ArrayList;
import java.util.Date;
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
import com.ai.aiga.service.workFlowNew.dto.changeReviewList;
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

	/**
	 * 查询变更概况
	 * 
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public List<NaChangeCondition> findNaChangeCondition(ChangeReviewRequest request, int pageSize, int pageNumber) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();

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

		return (List<NaChangeCondition>) naChangeConditionDao.search(cons, pageable);
	}

	/**
	 * 保存变更概况
	 * 
	 * @param request
	 */
	public void saveChangeCondition(ChangeConditionRequest request) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		if (request.getExt1() == null || "".equals(request.getExt1())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "Ext1");
		}
		if (request.getFileName() == null || "".equals(request.getFileName())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "getFileName");
		}
		NaChangeCondition condition = BeanMapper.map(request, NaChangeCondition.class);
		naChangeConditionDao.save(condition);
	}

	/**
	 * 查询变更内容
	 * 
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public List<NaChangeContents> findNaChangeContents(ChangeReviewRequest request, int pageSize, int pageNumber) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();

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

		return (List<NaChangeContents>) naChangeContentsDao.search(cons, pageable);
	}

	/**
	 * 保存变更内容
	 * 
	 * @param request
	 */
	public void saveChangeContents(ChangeContentsRequest request) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		if (request.getExt1() == null || "".equals(request.getExt1())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "Ext1");
		}
		if (request.getFileName() == null || "".equals(request.getFileName())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "getFileName");
		}
		NaChangeContents contens = BeanMapper.map(request, NaChangeContents.class);
		naChangeContentsDao.save(contens);
	}

	/**
	 * 查询时间及人员
	 * 
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public List<NaChangeTimePerson> findNaChangeTimePerson(ChangeReviewRequest request, int pageSize, int pageNumber) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();

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

		return (List<NaChangeTimePerson>) naChangeTimePersonDao.search(cons, pageable);
	}

	/**
	 * 保存变更时间及人员
	 * 
	 * @param request
	 */
	public void saveNaChangeTimePerson(ChangeTimePersonRequest request) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		if (request.getExt1() == null || "".equals(request.getExt1())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "Ext1");
		}
		if (request.getFileName() == null || "".equals(request.getFileName())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "getFileName");
		}
		NaChangeTimePerson contens = BeanMapper.map(request, NaChangeTimePerson.class);
		naChangeTimePersonDao.save(contens);
	}

	/**
	 * 查询配置是否更新
	 * 
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public List<NaChangeUpdate> findNaChangeUpdate(ChangeReviewRequest request, int pageSize, int pageNumber) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();

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

		return (List<NaChangeUpdate>) naChangeUpdateDao.search(cons, pageable);
	}

	/**
	 * 保存变更配置
	 * 
	 * @param request
	 */
	public void saveNaChangeUpdate(ChangeUpdateRequest request) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		if (request.getExt1() == null || "".equals(request.getExt1())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "Ext1");
		}
		if (request.getFileName() == null || "".equals(request.getFileName())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "getFileName");
		}
		NaChangeUpdate contens = BeanMapper.map(request, NaChangeUpdate.class);
		naChangeUpdateDao.save(contens);
	}

	/**
	 * 查询变更目标设备
	 * 
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public List<NaChangeGoalDevice> findNaChangeGoalDevice(ChangeReviewRequest request, int pageSize, int pageNumber) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();

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

		return (List<NaChangeGoalDevice>) naChangeGoalDeviceDao.search(cons, pageable);
	}

	/**
	 * 保存变更目标设备
	 * 
	 * @param request
	 */
	public void saveNaChangeGoalDevice(ChangeGoalDeviceRequest request) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		if (request.getExt1() == null || "".equals(request.getExt1())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "Ext1");
		}
		if (request.getFileName() == null || "".equals(request.getFileName())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "getFileName");
		}
		if (request.getSearchCode() == null || "".equals(request.getSearchCode())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "getSearchCode");
		}
		NaChangeGoalDevice contens = BeanMapper.map(request, NaChangeGoalDevice.class);
		naChangeGoalDeviceDao.save(contens);
	}

	/**
	 * 查询告警屏蔽
	 * 
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public List<NaWarningShield> findNaWarningShield(ChangeReviewRequest request, int pageSize, int pageNumber) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();

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

		return (List<NaWarningShield>) naWarningShieldDao.search(cons, pageable);
	}

	/**
	 * 保存告警屏蔽
	 * 
	 * @param request
	 */
	public void saveNaWarningShield(WarningShieldRequest request) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		if (request.getExt1() == null || "".equals(request.getExt1())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "Ext1");
		}
		if (request.getFileName() == null || "".equals(request.getFileName())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "getFileName");
		}
		NaWarningShield contens = BeanMapper.map(request, NaWarningShield.class);
		naWarningShieldDao.save(contens);
	}

	/**
	 * 查询变更准备工作
	 * 
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public List<NaChangePrepareWork> findNaChangePrepareWork(ChangeReviewRequest request, int pageSize,
			int pageNumber) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();

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

		return (List<NaChangePrepareWork>) naChangePrepareWorkDao.search(cons, pageable);
	}

	/**
	 * 保存变更准备工作
	 * 
	 * @param request
	 */
	public void saveNaChangePrepareWork(ChangePrepareWorkRequest request) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		if (request.getExt1() == null || "".equals(request.getExt1())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "Ext1");
		}
		if (request.getFileName() == null || "".equals(request.getFileName())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "getFileName");
		}
		NaChangePrepareWork contens = BeanMapper.map(request, NaChangePrepareWork.class);
		naChangePrepareWorkDao.save(contens);
	}

	/**
	 * 查询变更运维管理
	 * 
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public List<NaChangeOperationManager> findNaChangeOperationManager(ChangeReviewRequest request, int pageSize,
			int pageNumber) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();

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

		return (List<NaChangeOperationManager>) naChangeOperationManagerDao.search(cons, pageable);
	}

	/**
	 * 保存变更运维管理
	 * 
	 * @param request
	 */
	public void saveNaChangeOperationManager(ChangeOperationManagerRequest request) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		if (request.getExt1() == null || "".equals(request.getExt1())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "Ext1");
		}
		if (request.getFileName() == null || "".equals(request.getFileName())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "getFileName");
		}
		NaChangeOperationManager contens = BeanMapper.map(request, NaChangeOperationManager.class);
		naChangeOperationManagerDao.save(contens);
	}

	/**
	 * 查询回退方法
	 * 
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public List<NaRollbackMethod> findNaRollbackMethod(ChangeReviewRequest request, int pageSize, int pageNumber) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();

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

		return (List<NaRollbackMethod>) naRollbackMethodDao.search(cons, pageable);
	}

	/**
	 * 保存回退方法
	 * 
	 * @param request
	 */
	public void saveNaRollbackMethod(RollbackMethodRequest request) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		if (request.getExt1() == null || "".equals(request.getExt1())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "Ext1");
		}
		if (request.getFileName() == null || "".equals(request.getFileName())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "getFileName");
		}
		NaRollbackMethod contens = BeanMapper.map(request, NaRollbackMethod.class);
		naRollbackMethodDao.save(contens);
	}

	/**
	 * 查询变更实施步骤
	 * 
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public List<NaChangeRunStep> findNaChangeRunStep(ChangeReviewRequest request, int pageSize, int pageNumber) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();

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

		return (List<NaChangeRunStep>) naChangeRunStepDao.search(cons, pageable);
	}

	/**
	 * 保存变更实施步骤
	 * 
	 * @param request
	 */
	public void saveNaChangeRunStep(ChangeRunStepRequest request) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		if (request.getExt1() == null || "".equals(request.getExt1())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "Ext1");
		}
		if (request.getFileName() == null || "".equals(request.getFileName())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "getFileName");
		}
		NaChangeRunStep contens = BeanMapper.map(request, NaChangeRunStep.class);
		naChangeRunStepDao.save(contens);
	}

	/**
	 * 查询信息通告
	 * 
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public List<NaInformationNotice> findNaInformationNotice(ChangeReviewRequest request, int pageSize,
			int pageNumber) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();

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

		return (List<NaInformationNotice>) naInformationNoticeDao.search(cons, pageable);
	}

	/**
	 * 保存变更信息通告
	 * 
	 * @param request
	 */
	public void saveNaInformationNotice(InformationNoticeRequest request) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		if (request.getExt1() == null || "".equals(request.getExt1())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "Ext1");
		}
		if (request.getFileName() == null || "".equals(request.getFileName())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "getFileName");
		}
		NaInformationNotice contens = BeanMapper.map(request, NaInformationNotice.class);
		naInformationNoticeDao.save(contens);
	}

	/**
	 * 查询变更风险评估量化表
	 * 
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public List<NaChangeDangurousEstimate> findNaChangeDangurousEstimate(ChangeReviewRequest request, int pageSize,
			int pageNumber) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();

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

		return (List<NaChangeDangurousEstimate>) naChangeDangurousEstimateDao.search(cons, pageable);
	}


	/**
	 * 保存变更风险评估量化表
	 * 
	 * @param request
	 */
	public void saveNaChangeDangurousEstimate(ChangeDangurousEstimateRequest request) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		if (request.getExt1() == null || "".equals(request.getExt1())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "Ext1");
		}
		if (request.getFileName() == null || "".equals(request.getFileName())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "getFileName");
		}
		NaChangeDangurousEstimate contens = BeanMapper.map(request, NaChangeDangurousEstimate.class);
		naChangeDangurousEstimateDao.save(contens);
	}

	
	
	/**
	 * 查询变更风险评估量得分
	 * 
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public List<NaQuantitativeRisk> findNaQuantitativeRisk(ChangeReviewRequest request, int pageSize,
			int pageNumber) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();

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

		return (List<NaQuantitativeRisk>) naQuantitativeRiskDao.search(cons, pageable);
	}


	/**
	 * 保存变更风险评估得分
	 * 
	 * @param request
	 */
	public void saveNaQuantitativeRisk(QuantitativeRiskRequest request) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		if (request.getExt1() == null || "".equals(request.getExt1())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "Ext1");
		}
		if (request.getFileName() == null || "".equals(request.getFileName())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "getFileName");
		}
		NaQuantitativeRisk contens = BeanMapper.map(request, NaQuantitativeRisk.class);
		naQuantitativeRiskDao.save(contens);
	}

	
	
	/**
	 * 查询变更风险评估
	 * 
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public List<NaRiskRatingScale> findNaRiskRatingScale(ChangeReviewRequest request, int pageSize,
			int pageNumber) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();

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

		return (List<NaRiskRatingScale>) naRiskRatingScaleDao.search(cons, pageable);
	}

	//
	/**
	 * 保存变更风险评估
	 * 
	 * @param request
	 */
	public void saveNaRiskRatingScale(RiskRatingScaleRequest request) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		if (request.getExt1() == null || "".equals(request.getExt1())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "Ext1");
		}
		if (request.getFileName() == null || "".equals(request.getFileName())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "getFileName");
		}
		NaRiskRatingScale contens = BeanMapper.map(request, NaRiskRatingScale.class);
		naRiskRatingScaleDao.save(contens);
	}

	
	
	
	/**
	 * 查询变更业务验证
	 * 
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public List<NaChangeServiceValidate> findNaChangeServiceValidate(ChangeReviewRequest request, int pageSize,
			int pageNumber) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();

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

		return (List<NaChangeServiceValidate>) naChangeServiceValidateDao.search(cons, pageable);
	}

	/**
	 * 保存业务变更验证
	 * 
	 * @param request
	 */
	public void saveNaChangeServiceValidate(ChangeServiceValidateRequest request) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		if (request.getExt1() == null || "".equals(request.getExt1())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "Ext1");
		}
		if (request.getFileName() == null || "".equals(request.getFileName())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "getFileName");
		}
		NaChangeServiceValidate contens = BeanMapper.map(request, NaChangeServiceValidate.class);
		naChangeServiceValidateDao.save(contens);
	}

	/**
	 * 查询变更结果验证
	 * 
	 * @param request
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	public List<NaChangeResultValidate> findNaChangeResultValidate(ChangeReviewRequest request, int pageSize,
			int pageNumber) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		List<Condition> cons = new ArrayList<Condition>();

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

		return (List<NaChangeResultValidate>) naChangeResultValidateDao.search(cons, pageable);
	}

	/**
	 * 保存变更结果验证
	 * 
	 * @param request
	 */
	public void saveNaChangeResultValidate(ChangeResultValidateRequest request) {
		if (request == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		if (request.getPlanId() == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "PlanId");
		}
		if (request.getExt1() == null || "".equals(request.getExt1())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "Ext1");
		}
		if (request.getFileName() == null || "".equals(request.getFileName())) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "getFileName");
		}
		NaChangeResultValidate contens = BeanMapper.map(request, NaChangeResultValidate.class);
		naChangeResultValidateDao.save(contens);
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
	public List<changeReviewList> findchangeReviewList(Long planId, Long type, int pageSize, int pageNumber) {
		if (planId == null) {
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "planId");
		}
		StringBuilder s = new StringBuilder();
		List<ParameterCondition> param = new ArrayList<ParameterCondition>();
		s.append(
				"select b.id,b.ext_2 as file_id, a.file_name, b.conclusion, b.review_result, b.reviewer, b.review_date, b.remark");
		s.append("from NA_FILE_UPLOAD a");
		s.append(" left join NA_CHANGE_REVIEW b");
		s.append("   on a.id = b.ext_2");
		s.append(" where a.file_type = 20");
		s.append(" and a.plan_id = :planId");
		param.add(new ParameterCondition("planId", planId));
		if (type == 1) {
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

		return (List<changeReviewList>) naChangeResultValidateDao.searchByNativeSQL(s.toString(), param,
				changeReviewList.class, pageable);
	}
	
	
	/**
	 * 保存评审结论
	 * @param request
	 */
	public void saveChangeReviewResult(ChangeReviewResultRequest request){
		if(request==null){	
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "request");
		}
		NaChangeReview naChangeReview = naChangeReviewDao.findOne(request.getReviewId());
		NaChangeReview naChangeReviewNew = naChangeReview;
		naChangeReview.setConclusion(request.getConclusion());
		naChangeReview.setReviewResult(request.getReviewResult());
		naChangeReview.setRemark(request.getRemark());
		naChangeReview.setReviewer(String.valueOf(SessionMgrUtil.getStaff().getOpId()));
		naChangeReview.setReviewDate(new Date());
		naChangeReviewDao.save(naChangeReview);
		
		//评审通过
		if(request.getConclusion()==WorkFlowNewEnum.ReviewResult_Yes.getValue()){
			
			NaChangeReview naChangeReviews = naChangeReviewDao.findByExt1(request.getExt1());
			if(naChangeReview!=null){
				naChangeReviewDao.delete(naChangeReviews.getReviewId());
			}
		}else{
			//评审不通过
			naChangeReviewDao.save(naChangeReviewNew);
		}
	}

	
	/**
	 * 设置第N次评审时间
	 * @param planId
	 * @param reviewNum
	 * @param planReviewDate
	 */
	public void setReviewDate(Long planId, Long reviewNum ,Date planReviewDate){
		if(planId==null){
			BusinessException.throwBusinessException(ErrorCode.Parameter_null, "planId");
		}
		naChangeReviewDao.setReviewDate(planId ,reviewNum ,planReviewDate);
	}
}
