package com.ai.am.view.controller.auto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.am.constant.BusiConstant;
import com.ai.am.domain.NaAutoRunResult;
import com.ai.am.service.auto.AutoRunResultSv;
import com.ai.am.view.controller.auto.dto.AutoRunResultRequest;
import com.ai.am.view.controller.auto.dto.NaAutoTaskReportDetailRequest;
import com.ai.am.view.controller.auto.dto.TaskRunResultRequest;
import com.ai.am.view.json.base.JsonBean;

@Controller
public class AutoRunResultController {
	
	@Autowired
	private AutoRunResultSv autoRunResultSv;
	
	@RequestMapping(path = "/auto/autoRunResult/save")
	public @ResponseBody JsonBean save(NaAutoRunResult naAutoRunResult){
		autoRunResultSv.save(naAutoRunResult);
		return JsonBean.success;
	}
	
	/**
	 * 自动化任务下各用例的执行结果*/
	@RequestMapping(path = "/auto/autoRunResult/caseByTaskList")
	public @ResponseBody JsonBean caseByTaskList(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			AutoRunResultRequest condition
			){
		JsonBean bean = new JsonBean();
		bean.setData(autoRunResultSv.caseByTaskList(condition,pageNumber,pageSize));
		return bean;
	}
	
	/**
	 * 根据resultId 获取用例执行信息*/
	@RequestMapping(path = "/auto/autoRunResult/runInfo")
	public @ResponseBody JsonBean runInfo(Long resultId){
		JsonBean bean = new JsonBean();
		bean.setData(autoRunResultSv.runInfo(resultId));
		return bean;
	}
	/**
	 * 根据resultId 获取执行日志*/
	@RequestMapping(path = "/auto/autoRunResult/runLog")
	public @ResponseBody JsonBean runLog(Long resultId){
		JsonBean bean = new JsonBean();
		bean.setData(autoRunResultSv.runLog(resultId));
		return bean;
	}
	/**
	 * 自动化执行结果总体信息展示*/
	@RequestMapping(path = "/auto/autoRunResult/list")
	public @ResponseBody JsonBean list(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			TaskRunResultRequest condition
			){
		JsonBean bean = new JsonBean();
		bean.setData(autoRunResultSv.list(condition, pageNumber, pageSize));
		return bean;
	}
	/**
	 * 根据任务Id查询该任务执行结果
	 * */
	@RequestMapping(path = "/auto/autoRunResult/findOne")
	public @ResponseBody JsonBean findOne(Long taskId){
		JsonBean bean = new JsonBean();
		bean.setData(autoRunResultSv.findOne(taskId));
		return bean;
	}
	/**
	 * 保存报告*/
	@RequestMapping(path = "/auto/autoRunResult/reportSave")
	public @ResponseBody JsonBean reportSave(Long taskId){
		autoRunResultSv.reportSave(taskId);
		return JsonBean.success;
	}
	/**
	 * 生成报告时，该任务下报告明细展示*/
	@RequestMapping(path = "/auto/autoRunResult/taskDetail")
	public @ResponseBody JsonBean taskDetail(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			Long taskId
			){
		JsonBean bean = new JsonBean();
		bean.setData(autoRunResultSv.taskDetail(taskId, pageNumber, pageSize));
		return bean;
	}
	/**
	 * 报告明细保存*/
	@RequestMapping(path = "/auto/autoRunResult/reportDetailSave")
	public @ResponseBody JsonBean reportDetailSave(@RequestBody List<NaAutoTaskReportDetailRequest> list){
		autoRunResultSv.reportDetailSave(list);
		return JsonBean.success;
	}
	/**
	 * 报告明细保存后刷新列表接口*/
	@RequestMapping(path = "/auto/autoRunResult/reportDetailList")
	public @ResponseBody JsonBean reportDetailList(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			Long taskId
			){
		JsonBean bean = new JsonBean();
		bean.setData(autoRunResultSv.reportDetailList(taskId, pageNumber, pageSize));
		return bean;
	}
}