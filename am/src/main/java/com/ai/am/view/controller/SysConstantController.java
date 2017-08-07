package com.ai.am.view.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.am.cache.SysConstantCacheCmpt;
import com.ai.am.service.RoleSv;
import com.ai.am.service.SysConatantSv;
import com.ai.am.view.controller.role.dto.RoleRequest;
import com.ai.am.view.json.base.JsonBean;

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
