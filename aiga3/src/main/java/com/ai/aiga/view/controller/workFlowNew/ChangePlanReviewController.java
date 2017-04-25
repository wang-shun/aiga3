package com.ai.aiga.view.controller.workFlowNew;


import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.AigaFunFolder;
import com.ai.aiga.domain.AigaSystemFolder;
import com.ai.aiga.service.AigaFunFolderSv;
import com.ai.aiga.service.AigaSystemFolderSv;
import com.ai.aiga.service.ChangeReviewSv;
import com.ai.aiga.service.workFlowNew.ChangePlanReviewSv;
import com.ai.aiga.view.controller.workFlowNew.dto.ChangeReviewRequest;
import com.ai.aiga.view.json.AigaFunFolderRequest;
import com.ai.aiga.view.json.AigaSystemFolderRequest;
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
public class ChangePlanReviewController {
	
	
	@Autowired
	private ChangePlanReviewSv changeReviewSv;
	
	
	@RequestMapping(path = "/sys/review/finNaChangeCondition", method=RequestMethod.POST)
	@ApiOperation(value="查看变更概况" , notes="根据计划id查询变更概况,带分页")
	@ApiParam(name="request" ,value="查询条件，包括计划id和附件id", required=true)
	public @ResponseBody JsonBean finNaChangeCondition(ChangeReviewRequest request,
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize){
		JsonBean json = new JsonBean();
		json.setData(changeReviewSv.finNaChangeCondition(request,pageSize,pageNumber));
		return json;
	}
   
	
	@RequestMapping(path = "/sys/review/finNaChangeContents", method=RequestMethod.POST)
	@ApiOperation(value="查看变更内容" , notes="根据计划id查询变更内容,带分页")
	@ApiParam(name="request" ,value="查询条件，包括计划id和附件id", required=true)
	public @ResponseBody JsonBean finNaChangeContents(ChangeReviewRequest request,
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize){
		JsonBean json = new JsonBean();
		json.setData(changeReviewSv.finNaChangeContents(request,pageSize,pageNumber));
		return json;
	}
	
	
	
	@RequestMapping(path = "/sys/review/findNaChangeTimePerson", method=RequestMethod.POST)
	@ApiOperation(value="查看变更时间及内容" , notes="根据计划id查询变更时间及内容,带分页")
	@ApiParam(name="request" ,value="查询条件，包括计划id和附件id", required=true)
	public @ResponseBody JsonBean findNaChangeTimePerson(ChangeReviewRequest request,
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize){
		JsonBean json = new JsonBean();
		json.setData(changeReviewSv.findNaChangeTimePerson(request,pageSize,pageNumber));
		return json;
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
}
