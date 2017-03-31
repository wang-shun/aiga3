package com.ai.aiga.view.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.NaRequireList;
import com.ai.aiga.domain.NaUiControl;
import com.ai.aiga.service.NaRequireListSv;

import com.ai.aiga.view.json.base.JsonBean;

@Controller
public class NaRequireListController {
	@Autowired
	private  NaRequireListSv naRequireListSv;
	//需求列表
	
	@RequestMapping(path = "/sys/require/list")
	public @ResponseBody JsonBean list(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			
			String requireName,String onlinePlan) throws ParseException {
		
		  JsonBean bean = new JsonBean();
		bean.setData(naRequireListSv.selectList(pageNumber, pageSize,requireName,onlinePlan));
		
		return bean;
	}

	//保存
	@RequestMapping(path = "/sys/require/save")
	public @ResponseBody JsonBean save(NaRequireList request){
		naRequireListSv.save(request);
		return JsonBean.success;
	}
}
