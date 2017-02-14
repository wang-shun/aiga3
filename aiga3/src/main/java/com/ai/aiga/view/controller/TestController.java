package com.ai.aiga.view.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.service.AigaP2pFunctionPointService;
import com.ai.aiga.view.json.JsonBean;

@Controller
public class TestController {
	
	@Autowired
	private AigaP2pFunctionPointService aigaP2pFunctionPointService;
	
	@RequestMapping("/index")
	public String routes(){
		return "busi/db";
	}
	
	@RequestMapping("/test")
	public @ResponseBody JsonBean getDBConnection(){
		JsonBean jsonReturn = new JsonBean();
		jsonReturn.setBean(aigaP2pFunctionPointService.getAll());
		return jsonReturn;
	}
	
}
