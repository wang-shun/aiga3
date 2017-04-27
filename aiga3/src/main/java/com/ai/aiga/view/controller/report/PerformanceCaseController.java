package com.ai.aiga.view.controller.report;
/** * @author  lh 
    * @date 创建时间：2017年4月27日 上午10:22:56
    */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.service.report.PerformanceCaseSv;
import com.ai.aiga.view.json.base.JsonBean;

import springfox.documentation.spring.web.json.Json;

public class PerformanceCaseController {
	private PerformanceCaseSv performanceCaseSv;
	
	@RequestMapping(path = "/accept/performanceCase/findReport")
	public @ResponseBody JsonBean findReport(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize){
		Object performanceCaseList =  performanceCaseSv.list(pageNumber, pageSize);
		JsonBean jsonBean = new JsonBean();
		jsonBean.setData(performanceCaseList);
		return jsonBean;
	}
	
	@RequestMapping(path = "/accept/performanceCase/count")
	public @ResponseBody JsonBean countReport(){
		performanceCaseSv.countAsync();
		return JsonBean.success;
	}
	
}
