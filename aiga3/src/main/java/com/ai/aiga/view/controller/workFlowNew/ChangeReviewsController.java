package com.ai.aiga.view.controller.workFlowNew;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.service.workFlowNew.ChangePlanReviewSv;
import com.ai.aiga.service.workFlowNew.dto.ChangeResultValidateRequest;
import com.ai.aiga.service.workFlowNew.dto.WarningShieldRequest;
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
import com.ai.aiga.view.json.base.JsonBean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @author liuxx
 *@date 2017-04-24
 */
@Api(value="changeReviewController", description="变更评审操作接口")
@Controller
public class ChangeReviewsController {
	
	
	@Autowired
	private ChangePlanReviewSv changeReviewSv;
	
	
	@RequestMapping(path = "/sys/review/findNaChangeCondition", method=RequestMethod.POST)
	@ApiOperation(value="查看变更概况" , notes="根据计划id查询变更概况,带分页")
	@ApiParam(name="request" ,value="查询条件，包括计划id和附件id", required=true)
	public @ResponseBody JsonBean findNaChangeCondition(ChangeReviewRequest request,
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize){
		JsonBean json = new JsonBean();
		json.setData(changeReviewSv.findNaChangeCondition(request,pageSize,pageNumber));
		return json;
	}
   
	@RequestMapping(path = "/sys/review/saveNaChangeCondition", method=RequestMethod.POST)
	@ApiOperation(value="保存变更概况" , notes="保存变更概况")
	@ApiParam(name="request" ,value="保存信息", required=true)
	public @ResponseBody JsonBean saveNaChangeCondition(@RequestBody ChangeConditionRequest request
			){
		changeReviewSv.saveChangeCondition(request);
		return JsonBean.success;
	}
	@RequestMapping(path = "/sys/review/findNaChangeContents", method=RequestMethod.POST)
	@ApiOperation(value="查看变更内容" , notes="根据计划id查询变更内容,带分页")
	@ApiParam(name="request" ,value="查询条件，包括计划id和附件id", required=true)
	public @ResponseBody JsonBean findNaChangeContents(ChangeReviewRequest request,
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize){
		JsonBean json = new JsonBean();
		json.setData(changeReviewSv.findNaChangeContents(request,pageSize,pageNumber));
		return json;
	}
	
	@RequestMapping(path = "/sys/review/saveNaChangeContents", method=RequestMethod.POST)
	@ApiOperation(value="保存变更内容" , notes="保存变更内容")
	@ApiParam(name="request" ,value="保存信息", required=true)
	public @ResponseBody JsonBean saveNaChangeContents(@RequestBody ChangeContentsRequest request
			){
		changeReviewSv.saveChangeContents(request);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/sys/review/findNaChangeTimePerson", method=RequestMethod.POST)
	@ApiOperation(value="查看变更时间及人员" , notes="根据计划id查询变更时间及人员,带分页")
	@ApiParam(name="request" ,value="查询条件，包括计划id和附件id", required=true)
	public @ResponseBody JsonBean findNaChangeTimePerson(ChangeReviewRequest request,
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize){
		JsonBean json = new JsonBean();
		json.setData(changeReviewSv.findNaChangeTimePerson(request,pageSize,pageNumber));
		return json;
	}
	
	@RequestMapping(path = "/sys/review/saveNaChangeTimePerson", method=RequestMethod.POST)
	@ApiOperation(value="保存变更时间及人员" , notes="保存变更时间及人员")
	@ApiParam(name="request" ,value="保存信息", required=true)
	public @ResponseBody JsonBean saveNaChangeTimePerson(@RequestBody ChangeTimePersonRequest request
			){
		changeReviewSv.saveNaChangeTimePerson(request);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/sys/review/findNaChangeUpdate", method=RequestMethod.POST)
	@ApiOperation(value="查看变更设备是否更新" , notes="根据计划id查询变更设备是否更新,带分页")
	@ApiParam(name="request" ,value="查询条件，包括计划id和附件id", required=true)
	public @ResponseBody JsonBean findNaChangeUpdate(ChangeReviewRequest request,
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize){
		JsonBean json = new JsonBean();
		json.setData(changeReviewSv.findNaChangeUpdate(request,pageSize,pageNumber));
		return json;
	}
	
	@RequestMapping(path = "/sys/review/saveNaChangeUpdate", method=RequestMethod.POST)
	@ApiOperation(value="保存变更设备是否更新" , notes="保存变更设备是否更新")
	@ApiParam(name="request" ,value="保存信息", required=true)
	public @ResponseBody JsonBean saveNaChangeUpdate(@RequestBody ChangeUpdateRequest request
			){
		changeReviewSv.saveNaChangeUpdate(request);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/sys/review/findNaChangeGoalDevice", method=RequestMethod.POST)
	@ApiOperation(value="查看变更目标设备" , notes="根据计划id查询变更目标设备,带分页")
	@ApiParam(name="request" ,value="查询条件，包括计划id和附件id", required=true)
	public @ResponseBody JsonBean findNaChangeGoalDevice(ChangeReviewRequest request,
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize){
		JsonBean json = new JsonBean();
		json.setData(changeReviewSv.findNaChangeGoalDevice(request,pageSize,pageNumber));
		return json;
	}
	@RequestMapping(path = "/sys/review/saveNaChangeGoalDevice", method=RequestMethod.POST)
	@ApiOperation(value="保存变更目标设备" , notes="保存变更目标设备")
	@ApiParam(name="request" ,value="保存信息", required=true)
	public @ResponseBody JsonBean saveNaChangeGoalDevice(@RequestBody ChangeGoalDeviceRequest request
			){
		changeReviewSv.saveNaChangeGoalDevice(request);
		return JsonBean.success;
	}
	
	
	@RequestMapping(path = "/sys/review/findNaWarningShield", method=RequestMethod.POST)
	@ApiOperation(value="查看告警屏蔽" , notes="根据计划id查询告警屏蔽,带分页")
	@ApiParam(name="request" ,value="查询条件，包括计划id和附件id", required=true)
	public @ResponseBody JsonBean findNaWarningShield(ChangeReviewRequest request,
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize){
		JsonBean json = new JsonBean();
		json.setData(changeReviewSv.findNaWarningShield(request,pageSize,pageNumber));
		return json;
	}
	@RequestMapping(path = "/sys/review/saveNaWarningShield", method=RequestMethod.POST)
	@ApiOperation(value="保存变更告警屏蔽" , notes="保存变更告警屏蔽")
	@ApiParam(name="request" ,value="保存信息", required=true)
	public @ResponseBody JsonBean saveNaWarningShield(@RequestBody WarningShieldRequest request
			){
		changeReviewSv.saveNaWarningShield(request);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/sys/review/findNaChangePrepareWork", method=RequestMethod.POST)
	@ApiOperation(value="查看变更准备工作" , notes="根据计划id查询准备工作,带分页")
	@ApiParam(name="request" ,value="查询条件，包括计划id和附件id", required=true)
	public @ResponseBody JsonBean findNaChangePrepareWork(ChangeReviewRequest request,
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize){
		JsonBean json = new JsonBean();
		json.setData(changeReviewSv.findNaChangePrepareWork(request,pageSize,pageNumber));
		return json;
	}
	@RequestMapping(path = "/sys/review/saveNaChangePrepareWork", method=RequestMethod.POST)
	@ApiOperation(value="保存变更准备工作" , notes="保存变更准备工作")
	@ApiParam(name="request" ,value="保存信息", required=true)
	public @ResponseBody JsonBean saveNaChangePrepareWork(@RequestBody ChangePrepareWorkRequest request
			){
		changeReviewSv.saveNaChangePrepareWork(request);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/sys/review/findNaChangeOperationManager", method=RequestMethod.POST)
	@ApiOperation(value="查看变更运维管理" , notes="根据计划id查询变更运维管理,带分页")
	@ApiParam(name="request" ,value="查询条件，包括计划id和附件id", required=true)
	public @ResponseBody JsonBean findNaChangeOperationManager(ChangeReviewRequest request,
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize){
		JsonBean json = new JsonBean();
		json.setData(changeReviewSv.findNaChangeOperationManager(request,pageSize,pageNumber));
		return json;
	}
	@RequestMapping(path = "/sys/review/saveNaChangeOperationManager", method=RequestMethod.POST)
	@ApiOperation(value="保存变更运维管理" , notes="保存变更运维管理")
	@ApiParam(name="request" ,value="保存信息", required=true)
	public @ResponseBody JsonBean saveNaChangeOperationManager(@RequestBody ChangeOperationManagerRequest request
			){
		changeReviewSv.saveNaChangeOperationManager(request);
		return JsonBean.success;
	}
	
	
	@RequestMapping(path = "/sys/review/findNaRollbackMethod", method=RequestMethod.POST)
	@ApiOperation(value="查看变更回退方案" , notes="根据计划id查询变更回退方案,带分页")
	@ApiParam(name="request" ,value="查询条件，包括计划id和附件id", required=true)
	public @ResponseBody JsonBean findNaRollbackMethod(ChangeReviewRequest request,
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize){
		JsonBean json = new JsonBean();
		json.setData(changeReviewSv.findNaRollbackMethod(request,pageSize,pageNumber));
		return json;
	}
	@RequestMapping(path = "/sys/review/saveNaRollbackMethod", method=RequestMethod.POST)
	@ApiOperation(value="保存变更回退方案" , notes="保存变更回退方案")
	@ApiParam(name="request" ,value="保存信息", required=true)
	public @ResponseBody JsonBean saveNaRollbackMethod(@RequestBody RollbackMethodRequest request
			){
		changeReviewSv.saveNaRollbackMethod(request);
		return JsonBean.success;
	}
	

	
	@RequestMapping(path = "/sys/review/findNaChangeRunStep", method=RequestMethod.POST)
	@ApiOperation(value="查看变更实施步骤" , notes="根据计划id查询变更更实施步骤,带分页")
	@ApiParam(name="request" ,value="查询条件，包括计划id和附件id", required=true)
	public @ResponseBody JsonBean findNaChangeRunStep(ChangeReviewRequest request,
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize){
		JsonBean json = new JsonBean();
		json.setData(changeReviewSv.findNaChangeRunStep(request,pageSize,pageNumber));
		return json;
	}
	
	@RequestMapping(path = "/sys/review/saveNaChangeRunStep", method=RequestMethod.POST)
	@ApiOperation(value="保存变更实施步骤" , notes="保存变更实施步骤")
	@ApiParam(name="request" ,value="保存信息", required=true)
	public @ResponseBody JsonBean saveNaChangeRunStep(@RequestBody ChangeRunStepRequest request
			){
		changeReviewSv.saveNaChangeRunStep(request);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/sys/review/findNaInformationNotice", method=RequestMethod.POST)
	@ApiOperation(value="查看变更信息通告" , notes="根据计划id查询变更信息通告,带分页")
	@ApiParam(name="request" ,value="查询条件，包括计划id和附件id", required=true)
	public @ResponseBody JsonBean findNaInformationNotice(ChangeReviewRequest request,
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize){
		JsonBean json = new JsonBean();
		json.setData(changeReviewSv.findNaInformationNotice(request,pageSize,pageNumber));
		return json;
	}
	
	@RequestMapping(path = "/sys/review/saveNaInformationNotice", method=RequestMethod.POST)
	@ApiOperation(value="保存变更信息通告" , notes="保存变更信息通告")
	@ApiParam(name="request" ,value="保存信息", required=true)
	public @ResponseBody JsonBean saveNaInformationNotice(@RequestBody InformationNoticeRequest request
			){
		changeReviewSv.saveNaInformationNotice(request);
		return JsonBean.success;
	}

	@RequestMapping(path = "/sys/review/findNaChangeDangurousEstimate", method=RequestMethod.POST)
	@ApiOperation(value="查看变更风险评估" , notes="根据计划id查询变更更风险评估,带分页")
	@ApiParam(name="request" ,value="查询条件，包括计划id和附件id", required=true)
	public @ResponseBody JsonBean findNaChangeDangurousEstimate(ChangeReviewRequest request,
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize){
		JsonBean json = new JsonBean();
		json.setData(changeReviewSv.findNaChangeDangurousEstimate(request,pageSize,pageNumber));
		return json;
	}
	
	@RequestMapping(path = "/sys/review/saveNaChangeDangurousEstimate", method=RequestMethod.POST)
	@ApiOperation(value="保存变更风险评估" , notes="保存变更风险评估")
	@ApiParam(name="request" ,value="保存信息", required=true)
	public @ResponseBody JsonBean saveNaChangeDangurousEstimate(@RequestBody ChangeDangurousEstimateRequest request
			){
		changeReviewSv.saveNaChangeDangurousEstimate(request);
		return JsonBean.success;
	}

	@RequestMapping(path = "/sys/review/findNaChangeResultValidate", method=RequestMethod.POST)
	@ApiOperation(value="查看变更结果验证" , notes="根据计划id查询变更结果验证,带分页")
	@ApiParam(name="request" ,value="查询条件，包括计划id和附件id", required=true)
	public @ResponseBody JsonBean findNaChangeResultValidate(ChangeReviewRequest request,
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize){
		JsonBean json = new JsonBean();
		json.setData(changeReviewSv.findNaChangeResultValidate(request,pageSize,pageNumber));
		return json;
	}
	@RequestMapping(path = "/sys/review/saveNaChangeResultValidate", method=RequestMethod.POST)
	@ApiOperation(value="保存变更结果验证" , notes="保存变更结果验证")
	@ApiParam(name="request" ,value="保存信息", required=true)
	public @ResponseBody JsonBean saveNaChangeResultValidate(@RequestBody ChangeResultValidateRequest request
			){
		changeReviewSv.saveNaChangeResultValidate(request);
		return JsonBean.success;
	}
	
	
	
	@RequestMapping(path = "/sys/review/findNaChangeServiceValidate", method=RequestMethod.POST)
	@ApiOperation(value="查看变更服务验证" , notes="根据计划id查询变更服务验证,带分页")
	@ApiParam(name="request" ,value="查询条件，包括计划id和附件id", required=true)
	public @ResponseBody JsonBean findNaChangeServiceValidate(ChangeReviewRequest request,
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize){
		JsonBean json = new JsonBean();
		json.setData(changeReviewSv.findNaChangeServiceValidate(request,pageSize,pageNumber));
		return json;
	}
	
	
	@RequestMapping(path = "/sys/review/saveNaChangeServiceValidate", method=RequestMethod.POST)
	@ApiOperation(value="保存变更服务验证" , notes="保存变更服务验证")
	@ApiParam(name="request" ,value="保存信息", required=true)
	public @ResponseBody JsonBean saveNaChangeServiceValidate(@RequestBody ChangeServiceValidateRequest request
			){
		changeReviewSv.saveNaChangeServiceValidate(request);
		return JsonBean.success;
	}
	
	
	
	@RequestMapping(path = "/sys/review/findNaRiskRatingScale", method=RequestMethod.POST)
	@ApiOperation(value="查看风险评估量化表" , notes="根据计划id查询风险评估量化表,带分页")
	@ApiParam(name="request" ,value="查询条件，包括计划id和附件id", required=true)
	public @ResponseBody JsonBean findNaRiskRatingScale(ChangeReviewRequest request,
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize){
		JsonBean json = new JsonBean();
		json.setData(changeReviewSv.findNaRiskRatingScale(request,pageSize,pageNumber));
		return json;
	}
	
	
	@RequestMapping(path = "/sys/review/saveNaRiskRatingScale", method=RequestMethod.POST)
	@ApiOperation(value="保存风险评估量化表" , notes="保存风险评估量化表")
	@ApiParam(name="request" ,value="保存信息", required=true)
	public @ResponseBody JsonBean saveNaRiskRatingScale(@RequestBody RiskRatingScaleRequest request
			){
		changeReviewSv.saveNaRiskRatingScale(request);
		return JsonBean.success;
	}
	
	
	
	@RequestMapping(path = "/sys/review/findNaQuantitativeRisk", method=RequestMethod.POST)
	@ApiOperation(value="查看变更服务验证" , notes="根据计划id查询变更服务验证,带分页")
	@ApiParam(name="request" ,value="查询条件，包括计划id和附件id", required=true)
	public @ResponseBody JsonBean findNaQuantitativeRisk(ChangeReviewRequest request,
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize){
		JsonBean json = new JsonBean();
		json.setData(changeReviewSv.findNaQuantitativeRisk(request,pageSize,pageNumber));
		return json;
	}
	
	
	@RequestMapping(path = "/sys/review/saveNaQuantitativeRisk", method=RequestMethod.POST)
	@ApiOperation(value="保存变更服务验证" , notes="保存变更服务验证")
	@ApiParam(name="request" ,value="保存信息", required=true)
	public @ResponseBody JsonBean saveNaQuantitativeRisk(@RequestBody QuantitativeRiskRequest request
			){
		changeReviewSv.saveNaQuantitativeRisk(request);
		return JsonBean.success;
	}
	
	
	
	
	@RequestMapping(path = "/sys/review/saveChangeReviewResult", method=RequestMethod.POST)
	@ApiOperation(value="保存评审结论" , notes="保存评审结论")
	public @ResponseBody JsonBean saveChangeReviewResult(@RequestBody ChangeReviewResultRequest request
			){
		changeReviewSv.saveChangeReviewResult(request);
		return JsonBean.success;
	}
	
	
	
	@RequestMapping(path = "/sys/review/setReviewDate", method=RequestMethod.POST)
	@ApiOperation(value="设置第N次评审时间" , notes="设置第N次评审时间")
	public @ResponseBody JsonBean  setReviewDate(Long planId, Long reviewNum ,Date planReviewDate){
		changeReviewSv.setReviewDate(planId, reviewNum, planReviewDate);
		return JsonBean.success;
	}

}
