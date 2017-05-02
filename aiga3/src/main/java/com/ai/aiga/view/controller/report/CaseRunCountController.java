package com.ai.aiga.view.controller.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
		
		caseRunCountSv.countAsync();
		//caseRunCountSv.caseCount();
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/accept/caseRunReport/staffCount")
	public @ResponseBody JsonBean staffCount(){
		caseRunCountSv.staffCount();
		return JsonBean.success;
	}
}

