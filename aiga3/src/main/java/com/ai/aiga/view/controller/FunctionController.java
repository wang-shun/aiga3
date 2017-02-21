package com.ai.aiga.view.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.domain.AigaFunction;
import com.ai.aiga.service.FunctionSv;
import com.ai.aiga.service.RoleSv;
import com.ai.aiga.view.json.FunctionRequest;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
public class FunctionController {
	
	@Autowired
	private FunctionSv functionSv;
	
	@RequestMapping(path = "/sys/menu/list" )
	public @ResponseBody JsonBean list(){
		JsonBean bean = new JsonBean();
		bean.setBean(functionSv.findFunctions());
		return bean;
	}
	
	@RequestMapping(path = "/sys/menu/save")
	public @ResponseBody JsonBean save(FunctionRequest request){
		JsonBean bean = new JsonBean();
		functionSv.save(request);
		return bean;
	}
	
	@RequestMapping(path = "/sys/menu/update")
	public @ResponseBody JsonBean update(FunctionRequest request){
		JsonBean bean = new JsonBean();
		functionSv.update(request);
		return bean;
	}
	
	@RequestMapping(path = "/sys/menu/del")
	public @ResponseBody JsonBean update(@RequestParam Long funcId){
		functionSv.delete(funcId);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/sys/menu/get")
	public @ResponseBody JsonBean get(@RequestParam Long funcId){
		JsonBean bean = new JsonBean();
		bean.setBean(functionSv.findOne(funcId));
		return bean;
	}
	
	


}
