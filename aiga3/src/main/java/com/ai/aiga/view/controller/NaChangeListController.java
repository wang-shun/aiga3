package com.ai.aiga.view.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.aiga.constant.BusiConstant;
import com.ai.aiga.domain.NaChangeList;
import com.ai.aiga.domain.NaRequireList;
import com.ai.aiga.domain.NaUiControl;
import com.ai.aiga.service.NaChangeListSv;
import com.ai.aiga.service.NaRequireListSv;
import com.ai.aiga.view.json.base.JsonBean;

@Controller
public class NaChangeListController {
	@Autowired
	private  NaChangeListSv  naChangeListSv;
	
	@RequestMapping(path = "/sys/change/list")
	public @ResponseBody JsonBean list(
			@RequestParam(value = "page", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = BusiConstant.PAGE_DEFAULT + "") int pageSize,
			
			String changeName,String onlinePlan) throws ParseException {
		
		  JsonBean bean = new JsonBean();
		bean.setData(naChangeListSv.selectList(pageNumber, pageSize,changeName,onlinePlan));
		
		return bean;
	}
	
	
	
	
	@RequestMapping(path = "/sys/change/save")
	public @ResponseBody JsonBean save(NaChangeList request){
		naChangeListSv.save(request);
		return JsonBean.success;
	}
}
