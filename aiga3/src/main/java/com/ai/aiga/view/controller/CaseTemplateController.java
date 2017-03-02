package com.ai.aiga.view.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.NaCaseTemplate;
import com.ai.aiga.service.CaseTemplateSv;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
public class CaseTemplateController {
	
	@Autowired
	private CaseTemplateSv caseTemplateSv;
	
	@RequestMapping(path = "/case/template/list" )
	public @ResponseBody JsonBean list(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			@RequestParam(required = false) NaCaseTemplate condition){
		JsonBean bean = new JsonBean();
		bean.setData(caseTemplateSv.listTmeplate(condition, pageNumber, pageSize));
		return bean;
	}
	
	
	
	

}
