package com.ai.aiga.view.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.NaOnlinePlanBug;
import com.ai.aiga.service.OnlinePlanBugSv;
import com.ai.aiga.view.json.base.JsonBean;
import com.fasterxml.jackson.annotation.JsonFormat.Value;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName: OnlinePlanBugController
 * @author: dongch
 * @date: 2017年4月5日 上午10:28:42
 * @Description:
 * 
 */
@Api(value = "OnlinePlanBugController", description = "故障&异常接口")
@Controller
public class OnlinePlanBugController {
	
	@Autowired
	private OnlinePlanBugSv onlinePlanBugSv;
	
	@ApiOperation(value = "故障&异常查询接口", notes = "", response = NaOnlinePlanBug.class)
	@RequestMapping(path = "/accept/onlinePlanBug/list", method = RequestMethod.POST)
	public @ResponseBody JsonBean list(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			NaOnlinePlanBug condition){
		JsonBean bean = new JsonBean();
		bean.setData(onlinePlanBugSv.list(condition, pageNumber, pageSize));
		return bean;
	}
	
	@RequestMapping(path = "/accept/onlinePlanBug/save", method = RequestMethod.POST)
	public @ResponseBody JsonBean save(NaOnlinePlanBug naOnlinePlanBug){
		onlinePlanBugSv.save(naOnlinePlanBug);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/accept/onlinePlanBug/delete", method = RequestMethod.POST)
	public @ResponseBody JsonBean delete(String bugIds){
		onlinePlanBugSv.delete(bugIds);
		return JsonBean.success;
	}
}

