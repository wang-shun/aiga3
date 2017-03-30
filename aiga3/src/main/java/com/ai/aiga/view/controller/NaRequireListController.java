package com.ai.aiga.view.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.domain.NaRequireList;
import com.ai.aiga.service.NaRequireListSv;
import com.ai.aiga.view.json.RoleRequest;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
public class NaRequireListController {
	@Autowired
	private  NaRequireListSv naRequireListSv;
	@RequestMapping(path = "/sys/require/list")
	public @ResponseBody JsonBean list(){
		JsonBean bean = new JsonBean();
		bean.setData(naRequireListSv.selectList());
		return bean;
	}
	@RequestMapping(path = "/sys/require/name")
	public @ResponseBody JsonBean findone(
				@RequestParam String requireName){
		JsonBean bean = new JsonBean();
		bean.setData(naRequireListSv.selectname(requireName));
		return bean;
	}
	@RequestMapping(path = "/sys/require/save")
	public @ResponseBody JsonBean save(NaRequireList request){
		naRequireListSv.save(request);
		return JsonBean.success;
	}
}
