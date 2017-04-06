package com.ai.aiga.view.controller;

import org.apache.shiro.web.util.SavedRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.NaOnlinePlanBug;
import com.ai.aiga.domain.NaOnlineTaskDistribute;
import com.ai.aiga.service.OnlineTaskSv;
import com.ai.aiga.view.json.OnlineTaskRequest;
import com.ai.aiga.view.json.base.JsonBean;

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
	
	@RequestMapping(path = "/accept/onlineTask/list")
	public @ResponseBody JsonBean list(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			NaOnlineTaskDistribute condition){
		JsonBean bean = new JsonBean();
		bean.setData(onlineTaskSv.list(condition, pageNumber, pageSize));
		return bean;
	}
	
	@RequestMapping(path = "/accept/onlineTask/childList")
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
	public @ResponseBody JsonBean delete(String taskIds){
		onlineTaskSv.delete(taskIds);
		return JsonBean.success;
	}
	
}

