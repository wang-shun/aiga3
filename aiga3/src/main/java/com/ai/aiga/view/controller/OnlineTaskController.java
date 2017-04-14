package com.ai.aiga.view.controller;

import java.util.List;

import org.apache.shiro.web.util.SavedRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.NaInterfaceList;
import com.ai.aiga.domain.NaOnlinePlanBug;
import com.ai.aiga.domain.NaOnlineTaskDistribute;
import com.ai.aiga.service.OnlineTaskSv;
import com.ai.aiga.service.PerformanceTaskSv;
import com.ai.aiga.view.json.OnlineTaskRequest;
import com.ai.aiga.view.json.TaskRequireRequest;
import com.ai.aiga.view.json.base.JsonBean;

import springfox.documentation.spring.web.json.Json;

/**
 * @ClassName: OnlineTaskController
 * @author: dongch
 * @date: 2017年4月5日 下午6:33:30
 * @Description:
 * 
 */
@Controller
public class OnlineTaskController {
	
	@Autowired
	private OnlineTaskSv onlineTaskSv;
	
	@Autowired
	private PerformanceTaskSv performanceTaskSv;
	
	@RequestMapping(path = "/accept/onlineTask/list")
	public @ResponseBody JsonBean list(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			NaOnlineTaskDistribute condition){
		JsonBean bean = new JsonBean();
		bean.setData(onlineTaskSv.list(condition, pageNumber, pageSize));
		return bean;
	}
	
	@RequestMapping(path = "/accept/onlineTask/childList")//子任务分派展示
	public @ResponseBody JsonBean childList(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			NaOnlineTaskDistribute condition){
		JsonBean bean = new JsonBean();
		bean.setData(onlineTaskSv.childList(condition, pageNumber, pageSize));
		return bean;
	}
	
	@RequestMapping(path = "/accept/onlineTask/save")
	public @ResponseBody JsonBean save(OnlineTaskRequest onlineTaskRequest){
		onlineTaskSv.save(onlineTaskRequest);
		return JsonBean.success;
		
	}
	
	@RequestMapping(path = "/accept/onlineTask/delete")
	public @ResponseBody JsonBean delete(Long taskId){
		onlineTaskSv.delete(taskId);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/accept/onlineTask/collect")
	public @ResponseBody JsonBean collect(){
		JsonBean bean = new JsonBean();
		bean.setData(onlineTaskSv.collect());
		return bean;
	}
	
	@RequestMapping(path = "/accept/onlineTask/dealOp")
	public @ResponseBody JsonBean dealOp(){
		JsonBean bean = new JsonBean();
		bean.setData(onlineTaskSv.dealOp());
		return bean;
	}
	
	@RequestMapping(path = "/accept/performanceTask/save")
	public @ResponseBody JsonBean save(NaOnlineTaskDistribute naOnlineTaskDistribute){
		performanceTaskSv.save(naOnlineTaskDistribute);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/accept/performanceTask/interList")
	public @ResponseBody JsonBean interList(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			NaInterfaceList condition,
			Long onlinePlan){
		JsonBean bean = new JsonBean();
		bean.setData(performanceTaskSv.interList(condition, onlinePlan, pageNumber, pageSize));
		return bean;
	}
	
	@RequestMapping(path = "/accept/performanceTask/taskRequireReal")
	public @ResponseBody JsonBean taskRequireReal(@RequestBody TaskRequireRequest request){
		performanceTaskSv.taskRequireReal(request);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/accept/performanceTask/taskRequireDel")
	public @ResponseBody JsonBean taskRequireDel(Long taskId, String ids){
		performanceTaskSv.taskRequireDel(taskId, ids);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/accept/performanceTask/taskRequireList")
	public @ResponseBody JsonBean taskRequireList(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			NaInterfaceList interfaceList,
			NaOnlineTaskDistribute distribute){
		JsonBean bean = new JsonBean();
		bean.setData(performanceTaskSv.taskRequireList(interfaceList, distribute, pageNumber, pageSize));
		return bean;
	}
	
	@RequestMapping(path = "/accept/performanceTask/perTaskDeal")
	public @ResponseBody JsonBean perTaskDeal(Long taskId, Long dealOpId){
		performanceTaskSv.perTaskDeal(taskId, dealOpId);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/accept/performanceTask/childList")
	public @ResponseBody JsonBean childList(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			Long taskId){
		JsonBean bean = new JsonBean();
		bean.setData(performanceTaskSv.childList(taskId, pageNumber, pageSize));
		return bean;
	}
}

