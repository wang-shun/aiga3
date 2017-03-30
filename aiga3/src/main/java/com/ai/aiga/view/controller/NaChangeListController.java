package com.ai.aiga.view.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.domain.NaChangeList;
import com.ai.aiga.domain.NaRequireList;
import com.ai.aiga.service.NaChangeListSv;
import com.ai.aiga.service.NaRequireListSv;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
public class NaChangeListController {
	@Autowired
	private  NaChangeListSv  naChangeListSv;
	
	@RequestMapping(path = "/sys/change/list")
	public @ResponseBody JsonBean list(){
		JsonBean bean = new JsonBean();
		bean.setData(naChangeListSv.selectList());
		return bean;
	}
	@RequestMapping(path = "/sys/change/name")
	public @ResponseBody JsonBean findone(
				@RequestParam String changeName){
		JsonBean bean = new JsonBean();
		bean.setData(naChangeListSv.selectname(changeName));
		return bean;
	}
	@RequestMapping(path = "/sys/change/save")
	public @ResponseBody JsonBean save(NaChangeList request){
		naChangeListSv.save(request);
		return JsonBean.success;
	}
}
