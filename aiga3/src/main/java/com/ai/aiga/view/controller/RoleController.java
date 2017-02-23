package com.ai.aiga.view.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.service.RoleSv;
import com.ai.aiga.view.json.RoleRequest;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
public class RoleController {
	
	@Autowired
	private RoleSv roleSv;
	
	@RequestMapping(path = "/sys/role/list")
	public @ResponseBody JsonBean list(){
		JsonBean bean = new JsonBean();
		bean.setBean(roleSv.findRoles());
		return bean;
	}
	
	@RequestMapping(path = "/sys/role/save")
	public @ResponseBody JsonBean save(RoleRequest roleRequest){
		roleSv.saveRole(roleRequest);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/sys/role/update")
	public @ResponseBody JsonBean update(RoleRequest roleRequest){
		roleSv.updateRole(roleRequest);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/sys/role/del")
	public @ResponseBody JsonBean del(
				@RequestParam Long roleId){
		roleSv.deleteRole(roleId);
		return JsonBean.success;
	}
	
	
}
