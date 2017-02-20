package com.ai.aiga.view.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.service.FunctionSv;
import com.ai.aiga.service.RoleSv;
import com.ai.aiga.view.json.FunctionRequest;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
public class FunctionController {
	
	@Autowired
	private FunctionSv functionSv;
	
	@RequestMapping(path = "/sys/func/list" )
	public @ResponseBody JsonBean listxxx(
			@RequestParam(name="bcd", defaultValue="10") int abc){
		JsonBean bean = new JsonBean();
		bean.setBean(functionSv.findFunctions());
		return bean;
	}
	
	public @ResponseBody JsonBean save(FunctionRequest funcRequest){
		JsonBean bean = new JsonBean();
		functionSv.save(funcRequest);
		return bean;
	}
	


}
