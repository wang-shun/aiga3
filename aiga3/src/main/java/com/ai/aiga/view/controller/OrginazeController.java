package com.ai.aiga.view.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.service.OrganizeSv;
import com.ai.aiga.service.RoleSv;
import com.ai.aiga.service.SysConatantSv;
import com.ai.aiga.view.json.OrginazeRequest;
import com.ai.aiga.view.json.RoleRequest;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
public class OrginazeController {
	
	@Autowired
	private OrganizeSv organizeSv;

	
	@RequestMapping(path = "/sys/organize/list")
	public @ResponseBody JsonBean list(Long organizeId){
		JsonBean bean = new JsonBean();
		bean.setData(organizeSv.findOrganize(organizeId));
		return bean;
	}
	
	@RequestMapping(path = "/sys/organize/treeList")
	public  @ResponseBody JsonBean  tressList(){
		JsonBean bean = new JsonBean();
		bean.setData(organizeSv.findOrginazeTree());
		return bean;
	}
	
	
	@RequestMapping(path = "/sys/orginaze/save")
	public  @ResponseBody JsonBean  save(OrginazeRequest orginazeRequest){
		System.out.println("111"+orginazeRequest.getOrganizeName());
        organizeSv.saveOrginaze(orginazeRequest);
		return JsonBean.success;
	}
	
	@RequestMapping(path = "/sys/organize/update")
	public  @ResponseBody JsonBean  update(OrginazeRequest orginazeRequest){
        organizeSv.saveOrginaze(orginazeRequest);
		return JsonBean.success;
	}
	
	
	@RequestMapping(path = "/sys/organize/del")
	public  @ResponseBody JsonBean  delete(Long organizeId){
		System.out.println("organizeId"+organizeId);
        organizeSv.deleteOrginaze(organizeId);
		return JsonBean.success;
	}
}
