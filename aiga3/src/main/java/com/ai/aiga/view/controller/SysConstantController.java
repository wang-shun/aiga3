package com.ai.aiga.view.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.cache.SysConstantCacheCmpt;
import com.ai.aiga.service.RoleSv;
import com.ai.aiga.service.SysConatantSv;
import com.ai.aiga.view.controller.role.dto.RoleRequest;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
public class SysConstantController  extends SysConstantCacheCmpt{
	
	@Autowired
	private SysConatantSv sysConstantSv;

	
	@RequestMapping(path = "/sys/organize/constants")
	public  @ResponseBody JsonBean  organizeType(String category){
		JsonBean bean = new JsonBean();
		bean.setData(sysConstantSv.findConstant(category));
		return bean;
	}
	

	
}
