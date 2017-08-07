package com.ai.am.view.controller;


import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.am.constant.BusiConstant;
import com.ai.am.service.PlanCaseResultExpSv;
import com.ai.am.view.json.PlanCaseResultExpRequest;
import com.ai.am.view.json.PlanCaseResultExpSumRequest;
import com.ai.am.view.json.base.JsonBean;
import com.ai.am.view.util.SessionMgrUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @author liuxx
 *@date 2017-04-13
 */
@Api(value = "PlanCaseResultExpController", description="性能子任务处理操作接口"  )
@Controller
public class PlanCaseResultExPController {
	
	@Autowired
	private PlanCaseResultExpSv planCaseResultExpSv;
	
	
	
@RequestMapping(value="/sys/subTaskPlanExp/getNaPlanCaseResultExt", method=RequestMethod.POST)
@ApiOperation(value="查看测试结果" , notes="根据子任务ID查看测试结果明细,带分页")
@ApiParam(name="taskId" ,value="子任务id", required=true)
public @ResponseBody JsonBean getNaPlanCaseResultExt(Long taskId,
		@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
		@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize){
	JsonBean json = new JsonBean();
	json.setData(planCaseResultExpSv.getNaPlanCaseResultExt(taskId, pageNumber, pageSize));
	return json;
}


@RequestMapping(value="/sys/subTaskPlanExp/getNaPlanCaseResultExtSum", method=RequestMethod.POST)
@ApiOperation(value="查看汇总结果" , notes="根据子任务ID查看性能测试汇总结果")
@ApiParam(name="taskId" ,value="子任务id", required=true)
public @ResponseBody JsonBean getNaPlanCaseResultExtSum(Long taskId){
	JsonBean json = new JsonBean();
	json.setData(planCaseResultExpSv.getNaPlanCaseResultExtSum(taskId));
	return json;
}

	
@RequestMapping(value="/sys/subTaskPlanExp/saveOperatId", method=RequestMethod.POST)
@ApiOperation(value="保存测试人员" , notes="保存测试人员")
@ApiParam(name="request" ,value="id及测试人员编号", required=true)
public @ResponseBody JsonBean saveOperatId(@RequestBody List<PlanCaseResultExpSumRequest> request){
	planCaseResultExpSv.saveOperatId(request);
	return JsonBean.success;
}


@RequestMapping(value="/sys/subTaskPlanExp/saveRemark", method=RequestMethod.POST)
@ApiOperation(value="保存测试明细备注" , notes="保存测试明细备注")
@ApiParam(name="request" ,value="id及备注", required=true)
public @ResponseBody JsonBean saveRemark(@RequestBody List<PlanCaseResultExpRequest> request){
	planCaseResultExpSv.saveRemark(request);
	return JsonBean.success;
}



@RequestMapping(value="/sys/subTaskPlanExp/deleteNaPlanCaseResultExt", method=RequestMethod.POST)
@ApiOperation(value="保存测试明细备注" , notes="保存测试明细备注")
@ApiImplicitParams({
	@ApiImplicitParam(value="主键",name="resultId", paramType="query"),
	@ApiImplicitParam(value="子任务id",name="taskId", paramType="query")
})
public @ResponseBody JsonBean deleteNaPlanCaseResultExt(String resultIds, Long taskId){
	planCaseResultExpSv.deleteNaPlanCaseResultExt(resultIds,taskId);
	return JsonBean.success;
}


@RequestMapping(value="/sys/subTaskPlanExp/copyDataFromCSHP03", method=RequestMethod.POST)
@ApiOperation(value="同步结果" , notes="将数据从新炬库里面同步到入网验收平台")
@ApiImplicitParams({
	@ApiImplicitParam(value="子任务id",name="taskId", paramType="query")
})
public @ResponseBody JsonBean copyDataFromCSHP03( Long taskId){
	planCaseResultExpSv.copyDataFromCSHP03(taskId);
	return JsonBean.success;
}
}
