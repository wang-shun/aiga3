package com.ai.aiga.view.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.service.CaseSv;
import com.ai.aiga.service.CaseTemplateSv;
import com.ai.aiga.view.json.TemplateRequest;
import com.ai.aiga.view.json.base.JsonBean;
import com.ai.aiga.view.util.MediaTypes;
import com.ai.aiga.view.util.WebValidUtil;

@Controller
public class CaseInstanceController {
	
	@Autowired
	private CaseTemplateSv caseTemplateSv;
	
	@Autowired
	private CaseSv caseSv;
	
	@RequestMapping(path = "/case/instance/list", produces=MediaTypes.JSON)
	public @ResponseBody JsonBean list(
			@RequestParam(value = "pageNumber", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			@RequestParam(value = "functionId", defaultValue = "0") int functionId,
			@RequestParam(value = "caseName", defaultValue = "") String caseName,
			@RequestParam(value = "important", defaultValue = "0") int important
			){
		JsonBean bean = new JsonBean();
		bean.setData(caseSv.listCase(functionId, caseName, important, pageNumber, pageSize));
		return bean;
	}
	
	@RequestMapping(path = "/case/instance/save" )
	public @ResponseBody JsonBean save(@Valid TemplateRequest request, BindingResult result){
		if(result.hasErrors()){
			return WebValidUtil.errorInfo(result);
		}
		caseTemplateSv.saveTmeplate(request);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/case/instance/update" )
	public @ResponseBody JsonBean update(@Valid TemplateRequest request, BindingResult result){
		if(result.hasErrors()){
			return WebValidUtil.errorInfo(result);
		}
		//caseTemplateSv.saveTmeplate(request);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/case/instance/del" )
	public @ResponseBody JsonBean del(@RequestParam List<Long> caseIds){
		caseSv.delCase(caseIds);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/case/instance/get" )
	public @ResponseBody JsonBean get(Long caseId){
		JsonBean bean = new JsonBean();
		bean.setData(caseTemplateSv.getTmeplate(caseId));
		return bean;
	}
	
	
	

}
