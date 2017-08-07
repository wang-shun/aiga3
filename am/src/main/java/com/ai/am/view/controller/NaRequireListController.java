package com.ai.am.view.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.am.constant.BusiConstant;
import com.ai.am.domain.NaRequireList;
import com.ai.am.service.NaRequireListSv;
import com.ai.am.view.json.base.JsonBean;

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
	public @ResponseBody JsonBean Save(@RequestBody List<NaRequireList> saveState){
		naRequireListSv.save(saveState);
		return JsonBean.success;
	}
}
