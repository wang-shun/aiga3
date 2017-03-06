package com.ai.aiga.view.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.NaCaseTemplate;
import com.ai.aiga.service.CaseTemplateSv;
import com.ai.aiga.view.json.TemplateRequest;
import com.ai.aiga.view.json.base.JsonBean;
import com.ai.aiga.view.util.WebValidUtil;

@Controller
public class CaseTemplateController {
	
	@Autowired
	private CaseTemplateSv caseTemplateSv;
	
	@RequestMapping(path = "/case/template/list" )
	public @ResponseBody JsonBean list(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			NaCaseTemplate condition){
		JsonBean bean = new JsonBean();
		bean.setData(caseTemplateSv.listTmeplate(condition, pageNumber, pageSize));
		return bean;
	}
	
	@RequestMapping(path = "/case/template/save" )
	public @ResponseBody JsonBean save(@Valid TemplateRequest request, BindingResult result){
		if(result.hasErrors()){
			return WebValidUtil.errorInfo(result);
		}
		caseTemplateSv.saveTmeplate(request);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/case/template/update" )
	public @ResponseBody JsonBean update(@Valid TemplateRequest request, BindingResult result){
		if(result.hasErrors()){
			return WebValidUtil.errorInfo(result);
		}
		//caseTemplateSv.saveTmeplate(request);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/case/template/del" )
	public @ResponseBody JsonBean del(Long caseId){
		caseTemplateSv.delTmeplate(caseId);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/case/template/get" )
	public @ResponseBody JsonBean get(Long caseId){
		JsonBean bean = new JsonBean();
		bean.setData(caseTemplateSv.getTmeplate(caseId));
		return bean;
	}
	
	
	
	
	
	

}
