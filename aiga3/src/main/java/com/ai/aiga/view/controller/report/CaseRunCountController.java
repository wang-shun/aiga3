package com.ai.aiga.view.controller.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.service.report.CaseRunCountSv;
import com.ai.aiga.view.json.base.JsonBean;

/**
 * @ClassName: CaseRunCountController
 * @author: dongch
 * @date: 2017年4月27日 下午7:03:01
 * @Description:执行情况报表（用例执行，人员执行）
 * 
 */
@Controller
public class CaseRunCountController {
	
	@Autowired CaseRunCountSv caseRunCountSv;
	
	@RequestMapping(path = "/accept/caseRunReport/caseCount")
	public @ResponseBody JsonBean caseCount(){
		
		caseRunCountSv.countCase();
		//caseRunCountSv.caseCount();
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/accept/caseRunReport/staffCount")
	public @ResponseBody JsonBean staffCount(){
		
		caseRunCountSv.counStaff();
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/accept/caseRunReport/caseList")
	public @ResponseBody JsonBean caseList(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			Long onlinePlan){
		JsonBean bean = new JsonBean();
		bean.setData(caseRunCountSv.caseList(onlinePlan, pageNumber, pageSize));
		return bean;
	}
	
	@RequestMapping(path = "/accept/caseRunReport/staffList")
	public @ResponseBody JsonBean staffList(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			Long onlinePlan,
			String perName){
		JsonBean bean = new JsonBean();
		bean.setData(caseRunCountSv.staffList(onlinePlan, perName, pageNumber, pageSize));
		return bean;
	}
}

