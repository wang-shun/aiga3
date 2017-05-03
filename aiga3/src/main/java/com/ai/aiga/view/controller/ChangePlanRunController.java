package com.ai.aiga.view.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.NaAutoCollection;
import com.ai.aiga.domain.NaChangePlanOnile;
import com.ai.aiga.domain.NaCodePath;
import com.ai.aiga.domain.NaOnlineTaskDistribute;
import com.ai.aiga.service.ChangePlanRunSv;
import com.ai.aiga.service.OnlineTaskSv;
import com.ai.aiga.view.json.OnlineTaskRequest;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
public class ChangePlanRunController {
	
	@Autowired
	private ChangePlanRunSv changePlanRunSv;
	@Autowired
	private OnlineTaskSv onlineTaskSv;
	
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
		JsonBean bean = new JsonBean();
		
			 
		try {
			//判断是否是生产回归任务
			if(naOnlineTaskDistribute.getTaskType() == 4 ){
				//查询用例集
				List<NaAutoCollection> collections = onlineTaskSv.collect(2L);
				// 查出该任务类型下所有的用例集  按照计划_验收任务类型_用例集名称拼接	
				for(NaAutoCollection collect : collections){
					String collectName = collect.getCollectName();

					String taskName = naOnlineTaskDistribute.getTaskName();
					String sonTaskName = taskName+"_"+collectName;
					OnlineTaskRequest otr = new OnlineTaskRequest(sonTaskName,collect.getCollectId(),naOnlineTaskDistribute.getDealOpId(),naOnlineTaskDistribute.getTaskId());;
					onlineTaskSv.save(otr);
				}	
			}
		} catch (Exception e) {
			bean.fail("未知原因");
		}
		
		bean.setData(naOnlineTaskDistribute.getExt1());
		return bean;
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
	public @ResponseBody JsonBean taskList(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			Long onlinePlan,
			String type){
		JsonBean bean = new JsonBean();
		bean.setData(changePlanRunSv.taskList(onlinePlan, type, pageNumber, pageSize));
		return bean;
	}
	/**
	 * 删除已分派任务*/
	@RequestMapping(path = "/accept/changePlanRun/delete")
	public @ResponseBody JsonBean delete(String taskIds){
		changePlanRunSv.delete(taskIds);
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
	/**
	 * 环境验证用例执行结果查看*/
	@RequestMapping(path = "/accept/changePlanRun/caseResult")
	public @ResponseBody JsonBean caseResult(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			Long onlinePlan){
		JsonBean bean = new JsonBean();
		bean.setData(changePlanRunSv.caseResult(onlinePlan, pageNumber, pageSize));
		return bean;
	}
	
	/**
	 * 变更计划下拉框*/
	@RequestMapping(path = "/sys/cache/changePlan")
	public @ResponseBody JsonBean changePlan(){
		JsonBean bean = new JsonBean();
		bean.setData(changePlanRunSv.changePlan());
		return bean;
	}
}
