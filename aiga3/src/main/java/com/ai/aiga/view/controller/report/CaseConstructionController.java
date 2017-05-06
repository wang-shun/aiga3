package com.ai.aiga.view.controller.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.service.report.CaseConstructionSv;
import com.ai.aiga.view.controller.report.dto.CaseConstructionRequest;
import com.ai.aiga.view.controller.report.dto.StaffConstructionRequest;
import com.ai.aiga.view.json.base.JsonBean;


/**
 * @ClassName: CaseConstructionController
 * @author: dongch
 * @date: 2017年4月24日 下午2:43:14
 * @Description:功能用例和人员日常建设报表
 * 
 */
@Controller
public class CaseConstructionController {
	
	@Autowired
	private CaseConstructionSv caseConstructionSv;
	
	@RequestMapping(path = "/accept/caseConstruction/list")
	public @ResponseBody JsonBean list(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			CaseConstructionRequest request){
		JsonBean bean = new JsonBean();
		bean.setData(caseConstructionSv.list(request, pageNumber, pageSize));
		return bean;
	}
	
	@RequestMapping(path = "/accept/staffConstruction/list")
	public @ResponseBody JsonBean list(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			StaffConstructionRequest request){
		JsonBean bean = new JsonBean();
		bean.setData(caseConstructionSv.list(request, pageNumber, pageSize));
		return bean;
		
	}
	
	@RequestMapping(path = "/accept/caseConstruction/count")
	public @ResponseBody JsonBean count(String month, String jobDetail){
		//caseConstructionSv.count(month, jobDetail);
		caseConstructionSv.countAsync(month, jobDetail);
		return JsonBean.success;
	}
}

