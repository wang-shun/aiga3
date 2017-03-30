package com.ai.aiga.view.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.NaCaseTemplate;
import com.ai.aiga.domain.NaChangePlanOnile;
import com.ai.aiga.domain.NaCodePath;
import com.ai.aiga.domain.NaOnlineTaskDistribute;
import com.ai.aiga.service.ChangePlanRunSv;
import com.ai.aiga.view.json.base.JsonBean;

import springfox.documentation.spring.web.json.Json;

@Controller
public class ChangePlanRunController {
	
	@Autowired
	private ChangePlanRunSv changePlanRunSv;
	
	/**
	 * 变更计划列表*/
	@RequestMapping(path = "/accept/changePlan/list")
	public @ResponseBody JsonBean list(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			NaChangePlanOnile condition,
			String time1,
			String time2){
		JsonBean bean = new JsonBean();
		bean.setData(changePlanRunSv.list(condition, time1, time2, pageNumber, pageSize));
		return bean;
	}
	/**
	 * 变更计划启动时分派任务保存或修改*/
	@RequestMapping(path = "/accept/changePlanRun/save")
	public @ResponseBody JsonBean save(NaOnlineTaskDistribute naOnlineTaskDistribute){
		changePlanRunSv.save(naOnlineTaskDistribute);
		return JsonBean.success;
	}
	/**
	 * 启动变更计划*/
	@RequestMapping(path = "/accept/changePlanRun/changStart")
	public @ResponseBody JsonBean changStart(Long onlinePlan){
		changePlanRunSv.changStart(onlinePlan);
		return JsonBean.success;
	}
	/**
	 * 变更计划下父任务分派情况*/
	@RequestMapping(path = "/accept/changePlanRun/taskList")
	public @ResponseBody JsonBean taskList(Long onlinePlan){
		JsonBean bean = new JsonBean();
		bean.setData(changePlanRunSv.taskList(onlinePlan));
		return bean;
	}
	/**
	 * 删除已分派任务*/
	@RequestMapping(path = "/accept/changePlanRun/delete")
	public @ResponseBody JsonBean delete(Long taskId){
		changePlanRunSv.delete(taskId);
		return JsonBean.success;
	}
	/**
	 * 编译发布结果查看*/
	@RequestMapping(path = "/accept/changePlanRun/compileList")
	public @ResponseBody JsonBean compileList(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			NaCodePath condition){
		JsonBean bean = new JsonBean();
		bean.setData(changePlanRunSv.compileList(condition,pageNumber,pageSize));
		return bean;
	}
	/**
	 * 获取处理人接口*/
	@RequestMapping(path = "/accept/changePlanRun/createOpId")
	public @ResponseBody JsonBean createOpId(){
		JsonBean bean = new JsonBean();
		bean.setData(changePlanRunSv.createOpId());
		return bean;
	}
}
