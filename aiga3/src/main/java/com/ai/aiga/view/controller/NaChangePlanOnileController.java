package com.ai.aiga.view.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.service.NaChangePlanOnileSv;
import com.ai.aiga.view.json.ControlRequest;
import com.ai.aiga.view.json.NaChangePlanOnileRequest;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
public class NaChangePlanOnileController {
	@Autowired
	private NaChangePlanOnileSv naChangePlanOnileSv ;
	@RequestMapping(path = "/sys/changeplanonile/save")
	public @ResponseBody JsonBean save(NaChangePlanOnileRequest request){
		naChangePlanOnileSv.saveChangePlanOnile(request);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/sys/changeplanonile/update")
	public @ResponseBody JsonBean update(NaChangePlanOnileRequest request){
		naChangePlanOnileSv.updateChangePlanOnile(request);
		return JsonBean.success;
	}
	//取消
	@RequestMapping(path = "/sys/changeplanonile/del")
	public @ResponseBody JsonBean del(
			@RequestParam	Long onlinePlan){
		naChangePlanOnileSv.delectChangePlanOnile(onlinePlan);
		return JsonBean.success;
	}
	@RequestMapping(path = "/sys/changeplanonile/abandon")
	public @ResponseBody JsonBean abandon(
			@RequestParam Long onlinePlan){
		naChangePlanOnileSv.abandonChangePlanOnile(onlinePlan);
		return JsonBean.success;
	}
	//提交
	@RequestMapping(path = "/sys/changeplanonile/resultsave")
	public @ResponseBody JsonBean resultsave(NaChangePlanOnileRequest request){
		naChangePlanOnileSv.summaryChangePlanOnile(request);
		return JsonBean.success;
	}
	@RequestMapping(path = "/sys/changeplanonile/resultupdate")
	public @ResponseBody JsonBean resultupdate(@RequestParam String ext1,NaChangePlanOnileRequest request){
		naChangePlanOnileSv.select(ext1, request);
		return JsonBean.success;
	}
	@RequestMapping(path = "/sys/changeplanonile/findone")
	public @ResponseBody JsonBean findone(
				@RequestParam Long onlinePlan){
		JsonBean bean = new JsonBean();
		bean.setData(naChangePlanOnileSv.findOne(onlinePlan));
		return bean;
	}
}
