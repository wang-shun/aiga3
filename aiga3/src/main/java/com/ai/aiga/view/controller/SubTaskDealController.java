package com.ai.aiga.view.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.NaPlanCaseResult;
import com.ai.aiga.service.SubTaskDealSv;
import com.ai.aiga.view.json.SubTaskRequest;
import com.ai.aiga.view.json.base.JsonBean;

/**
 * @ClassName: SubTaskDealController
 * @author: dongch
 * @date: 2017年4月6日 下午6:55:14
 * @Description:
 * 
 */
@Controller
public class SubTaskDealController {
	
	@Autowired
	private SubTaskDealSv subTaskDealSv;
	
	@RequestMapping(path = "/accept/subTask/list")
	public @ResponseBody JsonBean list(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			SubTaskRequest condition){
		JsonBean bean = new JsonBean();
		bean.setData(subTaskDealSv.list(condition, pageNumber, pageSize));
		return bean;
	}
	
	@RequestMapping(path = "/accept/subTask/caseResult")
	public @ResponseBody JsonBean caseResult(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			Long taskId){
		JsonBean bean = new JsonBean();
		bean.setData(subTaskDealSv.caseResult(taskId, pageNumber, pageSize));
		return bean;
	}
	
	@RequestMapping(path = "/accept/subTask/caseResultSave")
	public @ResponseBody JsonBean caseResultSave(@RequestBody List<NaPlanCaseResult> list){
		subTaskDealSv.caseResultSave(list);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/accept/subTask/autoResult")
	public @ResponseBody JsonBean autoResult(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			Long taskId){
		JsonBean bean = new JsonBean();
		bean.setData(subTaskDealSv.autoResult(taskId, pageNumber, pageSize));
		return bean;
		
	}
}

